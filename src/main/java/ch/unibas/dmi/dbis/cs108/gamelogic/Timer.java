package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that handles all timed events in the game, such as vote times. This class essentially
 * is used to pause the main game thread for a given time / until a given event.
 */
public class Timer {
  public static final Logger LOGGER = LogManager.getLogger(Timer.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * The maximum length of the ghost vote in the night, in seconds
   */
  public static final int ghostVoteTime = 30;

  /**
   * The length of time in seconds after the ghost vote during which the ghosts visually walk to /
   * from their victim and the timespan within which humans will hear a noise. After this, the day starts.
   */
  public static final int ghostAfterVoteTime = 4;
  /**
   * The maximum length of the human vote in the day, in seconds
   */
  public static final int humanVoteTime = 60;

  /**
   * The length of time in seconds after the human vote, as the 'winner' of the vote is announced,
   * before the night begins
   */
  public static final int humanAfterVoteTime = 5;

  /**
   * The checking interval in seconds
   */
  public static final int interval = 1;

  /**
   * The minimal vote time, in seconds
   */
  public static final int minVoteTime = 5;

  /**
   * The timer for the ghost vote. Checks every {@code interval} seconds if every ghost has already voted.
   * If all have voted or if the {@code ghostVoteTime} value is reached, the timer ends
   * @param game the game this Timer has been called in
   */
  public static void ghostVoteTimer(Game game) {
    int counter = 0;
    while(counter < ghostVoteTime) {
      if(haveAllGhostsVoted(game) && counter > minVoteTime) { //if all ghost have voted
        return;
      }
      try {
        Thread.sleep(interval*1000);
      } catch (InterruptedException e) {
        LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
      }
      counter += (interval);
    }
  }

  public static void humanVoteTimer(Game game) {
    int counter = 0;
    while (counter < humanVoteTime) {
      if (haveAllHumansVoted(game) && counter > minVoteTime) return;
      try {
        Thread.sleep(interval*1000);
      } catch (InterruptedException e) {
        LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
      }
      counter += interval;
    }
  }

  public static void ghostAfterVoteTimer() {
    try {
      Thread.sleep(ghostAfterVoteTime *1000);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }
  }

  /**
   * Checks if all ghosts in the game have already voted, returns true if so
   * @param game the Game the ghosts live in
   * @return true if all Ghosts have voted and false if at least 1 didn't
   */
  public static boolean haveAllGhostsVoted(Game game) {
    int nrOfGhosts = 0;
    int j = 0; //counter
    boolean[] positionOfGhosts = game.gameState.getPositionOfGhosts();
    boolean[] whoHasVoted = game.getGameState().getClientVoteData().getHasVoted();
    for (boolean positionOfGhost : positionOfGhosts) { //determines how many ghosts are in the game
      if (positionOfGhost) {
        nrOfGhosts++;
      }
    }
    for(int i = 0; i < positionOfGhosts.length; i++) {
      if (positionOfGhosts[i]) {
        if(whoHasVoted[i]) {
          j++;
        }
      }
    }
    return nrOfGhosts == j;
  }

  /**
   * Checks if all humans have already voted, returns true if so, else returns false
   * @param game the game this is called in
   * @return returns true if all humans have voted
   */
  public static boolean haveAllHumansVoted(Game game) {
    boolean[] whoHasVoted = game.getGameState().getClientVoteData().getHasVoted();
    Passenger[] passengerArray = game.getGameState().getPassengerTrain();
    for(int i = 0; i < whoHasVoted.length; i++) {
      if(!passengerArray[i].getIsGhost() && ! passengerArray[i].getKickedOff() && !whoHasVoted[i]) {
        return false;
      }
    }
    return true;
  }

}
