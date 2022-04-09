package ch.unibas.dmi.dbis.cs108.gamelogic;

import java.util.Arrays;

public class ClientVoteData {

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

  public void setVote(int position, int vote) {
    this.vote[position] = vote;
  }

  public void setHasVoted(int position, boolean hasVoted) {
    this.hasVoted[position] = hasVoted;
  }
}
