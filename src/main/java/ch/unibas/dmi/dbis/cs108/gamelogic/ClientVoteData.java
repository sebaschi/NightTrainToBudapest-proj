package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Data structure that is used to store clientVotes in an array, where the index correponds to the
 * position in the train of the client.
 */

public class ClientVoteData {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private int[] vote; //saves vote of clientHandler for later transmission to passenger, by default MAX_VALUE, index corresponds to Passenger position
  private boolean[] hasVoted; //saves hasVoted status of clientHandler for later transmission to passenger, by default false, index corresponds to Passenger position

  public ClientVoteData() {
    int[] h = new int[6];
    Arrays.fill(h,Integer.MAX_VALUE);
    this.vote = h;
    this.hasVoted = new boolean[6];
  }

  public int[] getVote() {
    return vote;
  }

  public boolean[] getHasVoted() {
    return hasVoted;
  }

  /**
   * Sets a vote value at the right position in the vote array
   * @param position the index of the array
   * @param vote the vote value
   */
  public void setVote(int position, int vote) {
    if (vote == Integer.MAX_VALUE) {
      setHasVoted(position, false);
    }
    try {
      this.vote[position] = vote;
    } catch (IndexOutOfBoundsException e) {
      LOGGER.info("Position is: " + position);
    }
  }

  /**
   * Sets true or false at the right position in the hasVoted array
   * @param position the index of the array
   * @param hasVoted the vote state value
   */
  public void setHasVoted(int position, boolean hasVoted) {
    this.hasVoted[position] = hasVoted;
  }
}
