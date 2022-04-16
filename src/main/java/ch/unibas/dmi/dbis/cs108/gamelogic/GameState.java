package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Human;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameState {
  public static final Logger LOGGER = LogManager.getLogger(GameState.class);
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
  /**
   * Saves ClientVoteData, might not be used
   */
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
      throws TrainOverflow {
    this.nrOfPlayers = nrOfPlayers;
    this.nrOfGhosts = nrOfGhosts;
    this.nrOfUsers = nrOfUsers;
    this.train = new Train(nrOfPlayers, nrOfUsers);
    clientVoteData = new ClientVoteData();
    Passenger[] passengerTrain = new Passenger[nrOfPlayers]; //Creates an array with Passengers with correlation positions (Train)
    for (int i = 0; i < nrOfPlayers; i++) {
      if (i == train.getPositionOfGhost()) {
        LOGGER.info("OG position: " + train.getOrderOfTrain()[i]);
        Ghost g = new Ghost();
        g.setPosition(train.getOrderOfTrain()[i]);
        g.setGhost();
        g.setIsOG(true);
        passengerTrain[train.getOrderOfTrain()[i]] = g;
      } else {
        Human h = new Human();
        h.setPosition(train.getOrderOfTrain()[i]);
        passengerTrain[train.getOrderOfTrain()[i]] = h;
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

  public int getNrOfUsers() {
    return nrOfUsers;
  }

  public int getNrOfPlayers() {
    return nrOfPlayers;
  }

  public int getNrOfGhosts() {
    return nrOfGhosts;
  }

  public  ClientVoteData getClientVoteData() {
    return clientVoteData;
  }

  /**
   * Changes the name of the passenger in the Array that has the oldName
   * @param oldName the old Name of the Passenger to be name-changed
   * @param newName the new name for the Passenger
   */
  public void changeUsername(String oldName, String newName){
    for (Passenger passenger : passengerTrain) {
      String name = passenger.getName();
      if (name.equals(oldName)) {
        passenger.setName(newName);
      }
    }
  }

  /**
   * Replaces a disconnected Clients with an NPC
   * @param disconnectedClient the ClientHandler handling the disconnected client
   */
  public void handleClientDisconnect(ClientHandler disconnectedClient) {
    for(Passenger passenger : passengerTrain) {
      if (passenger.getIsPlayer()) {
        if (passenger.getClientHandler().equals(disconnectedClient)) {
          String name = passenger.getName() + "-NPC";
          if (passenger.getIsGhost()) {
            GhostNPC ghostNPC = new GhostNPC(passenger.getPosition(),name, passenger.getIsOG());
            addNewPassenger(ghostNPC, passenger.getPosition());
          } else { //is a human
            HumanNPC humanNPC = new HumanNPC(passenger.getPosition(),name);
            addNewPassenger(humanNPC, passenger.getPosition());
          }
        }
      }
    }

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
        print[i] = "| " + array[i].getName() + ", kicked off: " + array[i].getPosition() + " |";
      } else {
        if (array[i].getIsPlayer()) {
          if (array[i].getIsGhost()) {
            print[i] = "| " + array[i].getName() + "(ghostPlayer): " + array[i].getPosition() + " |";
          } else {
            print[i] = "| " + array[i].getName() + "(humanPlayer): " + array[i].getPosition() + " |";
          }
        } else {
          if (array[i].getIsGhost()) {
            print[i] = "| " + array[i].getName() + "(ghostNPC): " + array[i].getPosition() + " |";
          } else {
            print[i] = "| " + array[i].getName() + "(humanNPC): " + array[i].getPosition() + " |";
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
      print[i] = "| " + array[i].getName() + ": " + array[i].getPosition() + " |";
    }

    for (int i = 0; i < array.length; i++) {
      stringBuilder.append(print[i]);
    }
    return stringBuilder.toString();
  }

}
