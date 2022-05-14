package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Class that saves the current game State of the Game the client is in. Should get updated regularly
 * The gui gets its game information from here
 */
public class GameStateModel {
  public static final Logger LOGGER = LogManager.getLogger(GameStateModel.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private int nrOfPlayers; //sets the length of the train
  /**
   * true if it is the day
   */
  private boolean isDayClone;

  /**
   * true if the game is over
   */
  private boolean gameOver = false;

  /**
   * can take the values h/g/s for human/ghost/spectator. Safes the role the client this GamesStateModel
   * lives on currently has
   */
  private String yourRole; //TODO: Maybe add a GUI field to show this in

  private int yourPosition;

  /**
   * A primitive clone of the passengerTrain in the GameState of the server.
   * in passengerTrainClone[0] the names of the passengers are stored, in passengerTrainClone[1] the roles
   * (h/g/s). The indices of the array correspond with the positions
   */
  private String[][] passengerTrainClone;

  private boolean[] kickedOff;

  /**
   * Constructs a GamesStateModel with the passengerTrainClone
   */
  public GameStateModel() {
    this.nrOfPlayers = 6;
    passengerTrainClone  = new String[2][nrOfPlayers];
    for(String role : passengerTrainClone[1]) {
      role = "h";
    }
    yourRole = "h";
    kickedOff = new boolean[nrOfPlayers];
    isDayClone = false;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public boolean isGameOver() {
    return gameOver;
  }


  /**
   * Updates the passengerTrainClone
   * @param names an array of the names of the players
   * @param roles an array of the roles, should be in the form g/h/s
   */
  public void setPassengerTrainClone(String[] names, String[] roles) {
    passengerTrainClone[0] = names;
    passengerTrainClone[1] = roles;

  }

  public String[][] getPassengerTrainClone() {
    return passengerTrainClone;
  }

  /**
   * Sets your current role to the specified role, must be h for human, g for ghost or s for spectator
   * @param yourRole the role to set this role to
   */
  public void setYourRole(String yourRole) {
    try {
      if (yourRole.equals("h") || yourRole.equals("g") || yourRole.equals("s")) {
        this.yourRole = yourRole;
      } else {
        this.yourRole = "h";
      }
    } catch (Exception e) {
      LOGGER.warn("YourRole:" + e.getMessage());
    }
  }


  public String getYourRole() {
    return yourRole;
  }

  public String getYourRoleFromPosition(int position) {
    try {
      return passengerTrainClone[1][position];
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
    }
    return "";
  }

  public int getNrOfPlayers() {
    return
        nrOfPlayers;
  }

  public void setDayClone(boolean dayClone) {
    isDayClone = dayClone;
  }

  public boolean getDayClone() {
    return isDayClone;
  }

  public void setKickedOff(boolean[] kickedOff) {
    this.kickedOff = kickedOff;
  }

  public boolean[] getKickedOff() {
    return kickedOff;
  }

  /**
   * Extracts information of a String and fills the information into the passengerTrainClone and
   * kickedOff fields of this gameState
   * @param data the data in the form: {@code name:role:kickedOff$name:role:kickedOff$}..usw.
   */
  public void setGSFromString(String data) {
    try {
      String[] names = new String[6];
      String[] roles = new String[6];
      boolean[] kickedOff = new boolean[6];
      int i = 0;
      while (!data.isEmpty()) {
        int index = data.indexOf('$');
        String left = data.substring(0, index);
        data = data.substring(index + 1);
        int j = left.indexOf(':');
        names[i] = left.substring(0, j);
        String right = left.substring(j + 1);
        j = right.indexOf(':');
        roles[i] = right.substring(0, j);
        kickedOff[i] = Boolean.parseBoolean(right.substring(j + 1));
        i++;
      }
      setPassengerTrainClone(names, roles);
      setKickedOff(kickedOff);
    } catch (Exception e) {
      LOGGER.warn("data has wrong format");
    }
  }

  /**
   * Gets an integer and extracts the string from the passengerTrain[1] string at the index of the integer
   * @param position the index of the array
   */
  public void setRoleFromPosition(int position) {
    String role = "h";
    try {
      role = passengerTrainClone[1][position];
      LOGGER.debug("-----------------------Role in Set role:" + role);
    } catch (Exception e) {
      LOGGER.warn("Not an integer between 0-5");
    }
    setYourRole(role);
  }
}
