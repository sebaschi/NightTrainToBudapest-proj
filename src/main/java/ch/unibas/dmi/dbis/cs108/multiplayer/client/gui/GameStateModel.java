package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

/**
 * A Class that saves the current game State of the Game the client is in. Should get updated regularly
 * The gui gets its game information from here
 */
public class GameStateModel {
  private int nrOfPlayers; //sets the length of the train
  /**
   * true if it is the day
   */
  private boolean isDayClone;

  /**
   * can take the values h/g/s for human/ghost/spectator. Safes the role the client this GamesStateModel
   * lives on currently has
   */
  private String yourRole;

  /**
   * A primitive clone of the passengerTrain in the GameState of the server.
   * in passengerTrainClone[0] the names of the passengers are stored, in passengerTrainClone[1] the roles
   * (human/ghost/spectator). The indices of the array correspond with the positions
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
      role = "";
    }
    kickedOff = new boolean[nrOfPlayers];
    isDayClone = false;
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
    if(yourRole.equals("h") || yourRole.equals("g") || yourRole.equals("s")) {
      this.yourRole = yourRole;
    }
  }

  public String getYourRole() {
    return yourRole;
  }

  public int getNrOfPlayers() {
    return nrOfPlayers;
  }

  public void setDayClone(boolean dayClone) {
    isDayClone = dayClone;
  }

  public boolean getDayClone() {
    return isDayClone;
  }
}
