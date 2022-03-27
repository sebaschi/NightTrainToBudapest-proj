package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;


import org.apache.logging.log4j.*;

public class Game {

  /**
   * Can be extended for optional Game-settings
   **/
  protected int nrOfPlayers; //sets the length of the train
  protected int nrOfGhosts; // sets how many Ghosts we start witch
  protected int nrOfUsers; // safes how many clients are active in this Game
  protected GameFunctions gameFunctions;
  /**
   * Constructs a Game instance where:
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   */
  Game(int nrOfPlayers, int nrOfGhosts, int nrOfUsers)
      throws TrainOverflow { //ToDo: Who handles Exception how and where
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.gameFunctions = new GameFunctions(nrOfPlayers, nrOfGhosts, nrOfUsers);
    }


  public static void main(String[] args) {

    try {

      Game game1 = new Game(6, 1, 1);

    } catch (TrainOverflow e) {
      System.out.println(e.getMessage());
    }

  }


}
