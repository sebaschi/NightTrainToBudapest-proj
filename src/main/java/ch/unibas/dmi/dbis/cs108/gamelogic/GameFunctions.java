package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Human;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameFunctions {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Can be extended for optional Game-settings
   **/
  protected int nrOfPlayers; //sets the length of the train
  protected int nrOfGhosts; // sets how many Ghosts we start witch
  protected int nrOfUsers; // safes how many clients are active in this Game
  protected Train train; // safes who sits where
  protected Passenger[] passengerTrain;
  protected Game game;

  /**
   * Constructs a GameFunctions instance where nrOfPlayers >= nrOfUsers. Fills passengerTrain with
   * only humans
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   * @throws TrainOverflow if nrOfPlayers < nrOfUsers
   */
  GameFunctions(int nrOfPlayers, int nrOfGhosts, int nrOfUsers, Game game)
      throws TrainOverflow { //ToDo: where will Exception be handled?
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.train = new Train(nrOfPlayers, nrOfUsers);
    Passenger[] passengerTrain = new Passenger[nrOfPlayers]; //Creates an array with Passengers with correlation positions (Train)
    for (int i = 0; i < nrOfPlayers; i++) {
      if (i == 3) {
        Ghost g = new Ghost(game);
        g.setPosition(train.orderOfTrain[i]);
        g.setIsOG(true);
        passengerTrain[train.orderOfTrain[i]] = g;
      } else {
        Human h = new Human(game);
        h.setPosition(train.orderOfTrain[i]);
        passengerTrain[train.orderOfTrain[i]] = h;
      }
    }

    this.passengerTrain = passengerTrain;
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

  public Passenger[] getPassengerTrain() {
    return passengerTrain;
  }
}
