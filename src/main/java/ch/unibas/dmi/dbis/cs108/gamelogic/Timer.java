package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that handles all timed events in the game, such as vote times
 */
public class Timer {
  public static final Logger LOGGER = LogManager.getLogger(Timer.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * The maximum length of the ghost vote in the night, in seconds
   */
  public static final int ghostVote = 30;
  /**
   * The maximum length of the human vote in the day, in seconds
   */
  public static final int humanVote = 60;

  /**
   * The checking intervall in seconds
   */
  public static final int intervall = 1;

  /**
   * The timer for the ghost vote. Checks every {@code intervall} seconds if every ghost has already voted.
   * If all have voted or if the {@code ghostVote} value is reached, the timer ends
   * @param game the game this Timer has been called in
   */
  public static void ghostVoteTimer(Game game) {
    int counter = 0;
    while(counter < ghostVote) {
      if(haveAllGhostsVoted(game)) { //if all ghost have voted
        return;
      }
      try {
        Thread.sleep(intervall*1000);
      } catch (InterruptedException e) {
        LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
      }
      counter = counter + (intervall*1000);
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


}
