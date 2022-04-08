package ch.unibas.dmi.dbis.cs108.gamelogic;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.*;

public class Game {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Can be extended for optional Game-settings
   **/
  protected int nrOfPlayers; //sets the length of the train
  protected int nrOfGhosts; // sets how many Ghosts we start witch
  protected int nrOfUsers; // safes how many clients are active in this Game
  protected GameFunctions gameFunctions;
  protected boolean isDay; //false means it is night, it is night by default
  protected VoteHandler voteHandler;
  //TODO: Figure out where Day/Night game state is saved maybe think about a game state class or smt.
  /**
   * Constructs a Game instance where:
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   */
  public Game(int nrOfPlayers, int nrOfGhosts, int nrOfUsers)
      throws TrainOverflow { //ToDo: Who handles Exception how and where
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.gameFunctions = new GameFunctions(nrOfPlayers, nrOfGhosts, nrOfUsers, this);
    }

  public GameFunctions getGameFunctions() {
    return gameFunctions;
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

  public void setDay(boolean day) {
    isDay = day;
  }

  public void run(ClientHandler clientHandler) {
        int i = 0;
        String gameOverCheck = "";
        while (true) { //ToDo: was ist die Abbruchbedingung? VoteHandler muss das schicken.
          if (!isDay) {
            voteHandler.ghostVote(gameFunctions.getPassengerTrain(), this);
            setDay(true);
          } else {
            gameOverCheck = voteHandler.humanVote(gameFunctions.getPassengerTrain(), this);
          }
          if (gameOverCheck.equals("Game over: ghosts win!") || gameOverCheck.equals("Game over: humans win!")){
            clientHandler.broadcastAnnouncement(gameOverCheck);
            return;
          }
        }

  }

  public static void main(String[] args) {

    try {

      Game game1 = new Game(6, 1, 1);


    } catch (TrainOverflow e) {
      System.out.println(e.getMessage());
    }

  }


}
