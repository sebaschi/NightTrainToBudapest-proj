package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Human;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Can be extended for optional Game-settings
   **/
  public final int nrOfPlayers; //sets the length of the train
  public final int nrOfGhosts; // sets how many Ghosts we start witch
  public final int nrOfUsers; // safes how many clients are active in this Game
  public final Train train; // safes who sits where
  /**
   * contains all Passengers on train, needs to be updated
   */
  private Passenger[] passengerTrain;
  private ClientVoteData clientVoteData;



  /**
   * Constructs a GameState instance where nrOfPlayers >= nrOfUsers. Fills passengerTrain with
   * only humans
   *
   * @param nrOfPlayers is the length of the Train
   * @param nrOfGhosts  is the number of OG Ghosts you want to start with  and
   * @param nrOfUsers   is the number of active users at the time (non NPCs)
   * @throws TrainOverflow if nrOfPlayers < nrOfUsers
   */
  GameState(int nrOfPlayers, int nrOfGhosts, int nrOfUsers)
      throws TrainOverflow { //ToDo: where will Exception be handled?
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.train = new Train(nrOfPlayers, nrOfUsers);
    clientVoteData = new ClientVoteData();
    Passenger[] passengerTrain = new Passenger[nrOfPlayers]; //Creates an array with Passengers with correlation positions (Train)
    for (int i = 0; i < nrOfPlayers; i++) {
      if (i == 3) {
        Ghost g = new Ghost();
        g.setPosition(train.orderOfTrain[i]);
        g.setGhost();
        g.setIsOG(true);
        passengerTrain[train.orderOfTrain[i]] = g;
      } else {
        Human h = new Human();
        h.setPosition(train.orderOfTrain[i]);
        passengerTrain[train.orderOfTrain[i]] = h;
      }
    }

    this.passengerTrain = passengerTrain;
  }

  public Passenger[] getPassengerTrain() {
    return passengerTrain;
  }

  public Train getTrain() {
    return train;
  }



  /**
   * Takes a given Passenger and puts it into the passengerTrain at a certain position
   * @param passenger the new passenger being put into the train
   * @param position the position of the passenger
   */
  public void addNewPassenger(Passenger passenger, int position) {
    passengerTrain[position] = passenger;

  }

  /**
   * Converts the data in this passengerTrain into a human-readable string,
   * where one can see who is a ghost and who is a human, who is a player and who an NPC
   * @return a String that displays passengerTrain
   */
  public String toString() {
    Passenger[] array = passengerTrain;
    StringBuilder stringBuilder = new StringBuilder();
    String[] print = new String[6];
    for (int i = 0; i < array.length; i++) {
      if (array[i].getKickedOff()) {
        print[i] = "| kicked off: " + array[i].getPosition() + "|";
      } else {
        if (array[i].getIsPlayer()) {
          if (array[i].getIsGhost()) {
            print[i] = "| ghostPlayer: " + array[i].getPosition() + "|";
          } else {
            print[i] = "| humanPlayer: " + array[i].getPosition() + "|";
          }
        } else {
          if (array[i].getIsGhost()) {
            print[i] = "| ghostNPC: " + array[i].getPosition() + "|";
          } else {
            print[i] = "| humanNPC: " + array[i].getPosition() + "|";
          }
        }
      }
    }

    for (int i = 0; i < array.length; i++) {
      stringBuilder.append(print[i]);
    }
    return stringBuilder.toString();
  }

  /**
   * Converts the data in this passengerTrain into a human-readable string, but it is anonymised for
   * human players, so it is not obvious who is a human and who a ghost, only names and positions
   * are displayed
   * @return the String displaying an anonymised passengerTrain
   */
  public String humanToString() {
    Passenger[] array = passengerTrain;
    StringBuilder stringBuilder = new StringBuilder();
    String[] print = new String[6];
    for (int i = 0; i < array.length; i++) {
      print[i] = "| " + array[i].getName() + ": " + array[i].getPosition() + "|";
    }

    for (int i = 0; i < array.length; i++) {
      stringBuilder.append(print[i]);
    }
    return stringBuilder.toString();
  }

}
