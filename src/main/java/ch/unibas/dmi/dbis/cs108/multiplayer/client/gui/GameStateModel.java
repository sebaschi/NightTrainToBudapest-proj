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
   * A primitive clone of the passengerTrain in the GameState of the server.
   * in passengerTrainClone[0] the names of the passengers are stored, in passengerTrainClone[1] the roles
   * (human/ghost/spectator). The indices of the array correspond with the positions
   */
  private String[][] passengerTrainClone;

  /**
   * Constructs a GamesStateModel with the passengerTrainClone length at nrOfPlayers
   * @param nrOfPlayers the amount of different objects to be saved
   */
  public void GameStateModel(int nrOfPlayers) {
    this.nrOfPlayers = nrOfPlayers;
    passengerTrainClone  = new String[2][nrOfPlayers];
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
}
