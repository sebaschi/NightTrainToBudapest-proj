package ch.unibas.dmi.dbis.cs108.gamelogic;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.highscore.OgGhostHighScore;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;
import java.util.HashSet;
import org.apache.logging.log4j.*;

public class Game implements Runnable {
  public static final Logger LOGGER = LogManager.getLogger(Game.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Can be extended for optional Game-settings
   **/

  protected GameState gameState;
  protected boolean isDay = false; //false means it is night, it is night by default
  protected VoteHandler voteHandler = new VoteHandler();
  private Lobby lobby;
  private boolean isOngoing = true;
  private String name;
  private static int nameCounter = 0;
  /**
   * Constructs a Game instance where:
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   * @param lobby the lobby the game is in
   * @throws TrainOverflow when there are to many users
   */
  public Game(int nrOfPlayers, int nrOfGhosts, int nrOfUsers, Lobby lobby)
      throws TrainOverflow {
    this.gameState = new GameState(nrOfPlayers, nrOfGhosts, nrOfUsers);
    this.lobby = lobby;
    nameCounter++;
    this.name = "Game" + nameCounter;
    }

  public GameState getGameState() {
    return gameState;
  }

  public Lobby getLobby() {
    return lobby;
  }

  public boolean getIsDay() {return isDay; }

  public String getName() {return name; }

  public void setDay(boolean day) {
    isDay = day;
  }

  public void setOngoing(boolean ongoing) {
    isOngoing = ongoing;
  }

  /**
   * Returns this game's OG ghost as a Passenger object
   */
  Passenger getOgGhost(){
    int[] order = gameState.getTrain().getOrderOfTrain();
    Passenger[] passengerTrain = gameState.getPassengerTrain();
    for (int i = 0; i < 6; i++) {
      if (passengerTrain[i].getIsOG()) {
        return passengerTrain[i];
      }
    }
    return null;
  }

  /**
   * Starts a new game, creates a passenger array and saves it in gameState, sets the OG
   * currently at gameState.train[3] fills the passengerTrain moving from left to rigth in the
   * gameState.train array it connects clientHandlers witch the passengers in those positions
   * (Players) and fills the rest with NPC's
   */
  @Override
  public void run() {
    LOGGER.info("the run-method has been called");
    int i = 0;
    HashSet<ClientHandler> lobbyClients = lobby.getLobbyClients();
    String gameOverCheck = "";
    int[] order = gameState.getTrain().getOrderOfTrain();
    Passenger[] passengerTrain = gameState.getPassengerTrain();


    LOGGER.info(gameState.toString());
    for (ClientHandler client : lobbyClients) {//begins filling the train with clients
      int index = order[i];
      if (passengerTrain[index].getIsGhost()) { //if there is a ghost
        GhostPlayer ghostPlayer = new GhostPlayer(passengerTrain[index].getPosition(),
            client.getClientUserName(), client, passengerTrain[index].getIsOG());
        gameState.getPassengerTrain()[index] = ghostPlayer;
      } else {
        HumanPlayer humanPlayer = new HumanPlayer(passengerTrain[index].getPosition(),
            client.getClientUserName(), client, passengerTrain[index].getIsOG());
        gameState.getPassengerTrain()[index] = humanPlayer;
      }
      i++;
    }
    while (i < order.length) {//fills the rest of the train with npcs
      int index = order[i];
      if (passengerTrain[index].getIsGhost()) { //if they are a ghost
        GhostNPC ghostNPC = new GhostNPC(passengerTrain[index].getPosition(), "NPC" + passengerTrain[index].getPosition(),passengerTrain[index].getIsOG());
        gameState.getPassengerTrain()[index] = ghostNPC;
      } else {
        //ToDo: give NPC nice usernames
        HumanNPC humanNPC = new HumanNPC(passengerTrain[index].getPosition(), "NPC" + passengerTrain[index].getPosition());
        gameState.getPassengerTrain()[index] = humanNPC;
      }
      i++;
    }
    LOGGER.info(gameState.toString());

    i = 0;
    while (isOngoing) {//game cycle
      if (!isDay) {
        LOGGER.info("NIGHT");
        gameOverCheck = voteHandler.ghostVote(gameState.getPassengerTrain(), this);
        setDay(true);
      } else {
        LOGGER.info("DAY");
        gameOverCheck = voteHandler.humanVote(gameState.getPassengerTrain(), this);
        setDay(false);
      }
      if (gameOverCheck.equals(ClientGameInfoHandler.gameOverGhostsWin) || gameOverCheck.equals(
          ClientGameInfoHandler.gameOverHumansWin)) {
        if (gameOverCheck.equals(ClientGameInfoHandler.gameOverGhostsWin) && getOgGhost().getIsPlayer()) {
          OgGhostHighScore.addOgGhostWinner(getOgGhost().getName());
        }
        lobby.getAdmin().broadcastAnnouncementToLobby(gameOverCheck);
        lobby.removeGameFromRunningGames(this);
        lobby.addGameToFinishedGames(this);
        return;
      }
    }

  }

}



