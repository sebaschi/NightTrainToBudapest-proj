package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

/**
 * Handles all communication Client to Server concerning games tate updates i.e. client a has voted
 * Maybe unnecessary, everything that is needed might already be implemented in ClientHandler.
 * We might only need to extend the protocol and its parser.
 */

public class ClientGameInfoHandler {

  /**
   * All messages that are used in VoteHandler
   * TODO(Seraina&Alex): Adjust strings to be more meaningful
   */
  //relevant:
  public static final String ghostVoteRequest = "Vote on who to ghostify!";
  public static final String humanVoteRequest = "Vote for a ghost to kick off!";
  public static final String noiseNotification = "noise";
  public static final String gameOverHumansWin = "Game over: humans win!";
  public static final String gameOverGhostsWin = "Game over: ghosts win!";

  //just messages
  public static final String itsNightTime = "Please wait, ghosts are active";
  public static final String youGotGhostyfied = "You are now a ghost!";
  public static final String itsDayTime = "Please wait, humans are active";
  public static final String humansVotedFor = "Humans voted for:";
  public static final String isAHuman = "but they're a human!";
  public static final String gotKickedOff = "is a Ghost and got kicked off";


}
