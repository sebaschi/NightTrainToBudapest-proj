package ch.unibas.dmi.dbis.cs108.gamelogic;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.HashSet;
import org.apache.logging.log4j.*;

public class Game implements Runnable {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Can be extended for optional Game-settings
   **/
  protected final int nrOfPlayers; //sets the length of the train
  protected final int nrOfGhosts; // sets how many Ghosts we start witch
  protected int nrOfUsers; // safes how many clients are active in this Game
  protected GameState gameState;
  protected boolean isDay = false; //false means it is night, it is night by default
  protected VoteHandler voteHandler = new VoteHandler();
  private ClientHandler clientHandler;
  //TODO: Figure out where Day/Night game state is saved maybe think about a game state class or smt.
  /**
   * Constructs a Game instance where:
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   */
  public Game(ClientHandler clientHandler, int nrOfPlayers, int nrOfGhosts, int nrOfUsers)
      throws TrainOverflow { //ToDo: Who handles Exception how and where
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.gameState = new GameState(nrOfPlayers, nrOfGhosts, nrOfUsers);
    this.clientHandler = clientHandler;
    }

  public GameState getGameState() {
    return gameState;
  }

  public int getNrOfGhosts() {
    return nrOfGhosts;
  }

  public int getNrOfPlayers() {
    return nrOfPlayers;
  }

  public int getNrOfUsers() {
    return nrOfUsers;
  }

  public boolean getIsDay() {return isDay;}

  public void setDay(boolean day) {
    isDay = day;
  }

  /**
   * Starts a new game, creates a passenger array and saves it in gameState, sets the OG
   * currently at gameState.train[3] fills the passengerTrain moving from left to rigth in the
   * gameState.train array it connects clientHandlers witch the passengers in those positions
   * (Players) and fills the rest with NPC's
   * TODO: set ghost in a random position(i), gameState.train[i] so that a lone player can also start as a Ghost maybe use Train class
   */
  @Override
  public void run() {
    LOGGER.info("the run-method has been called");
    int i = 0;
    HashSet<ClientHandler> clients = ClientHandler.getConnectedClients();
    String gameOverCheck = "";
    int[] order = gameState.getTrain().orderOfTrain;
    Passenger[] passengerTrain = gameState.getPassengerTrain();


    LOGGER.info(gameState.toString());
    for (ClientHandler client : clients) {
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
    while (i < order.length) {
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
    while (true) { //ToDo: was ist die Abbruchbedingung? VoteHandler muss das schicken.
      if (!isDay) {
        LOGGER.info("NIGHT");
        voteHandler.ghostVote(gameState.getPassengerTrain(), this);
        setDay(true);
      } else {
        LOGGER.info("DAY");
        gameOverCheck = voteHandler.humanVote(gameState.getPassengerTrain(), this);
        setDay(false);
      }
      if (gameOverCheck.equals(ClientGameInfoHandler.gameOverGhostsWin) || gameOverCheck.equals(
          ClientGameInfoHandler.gameOverHumansWin)) {
        clientHandler.broadcastAnnouncement(gameOverCheck);
        return;
      }
    }

  }

}



