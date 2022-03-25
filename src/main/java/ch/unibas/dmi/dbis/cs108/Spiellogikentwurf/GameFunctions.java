package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

import ch.unibas.dmi.dbis.cs108.Klassenstruktur.Human;
import ch.unibas.dmi.dbis.cs108.Klassenstruktur.Passenger;

public class GameFunctions {

  /**
   * Can be extended for optional Game-settings
   **/
  int nrOfPlayers; //sets the length of the train
  int nrOfGhosts; // sets how many Ghosts we start witch
  int nrOfUsers; // safes how many clients are active in this Game
  Train train; // safes who sits where
  Passenger[] passengerTrain;

  /**
   * Constructs a GameFunctions instance where nrOfPlayers >= nrOfUsers. Fills passengerTrain with
   * only humans
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   * @throws TrainOverflow if nrOfPlayers < nrOfUsers
   */
  GameFunctions(int nrOfPlayers, int nrOfGhosts, int nrOfUsers)
      throws TrainOverflow { //ToDo: where will Exception be handled?
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.train = new Train(nrOfPlayers, nrOfUsers);
    Passenger[] passengerTrain = new Passenger[nrOfPlayers]; //Creates an array with Passengers with correlation positions (Train)
    for (int i = 0; i < nrOfPlayers; i++) {
      Human h = new Human();
      h.setPosition(train.orderOfTrain[i]);
      passengerTrain[i] = h;
    }
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

  public void ghostyfy(int position) {

  }

  public Passenger voteEval() {
    return new Passenger();
  }


}
