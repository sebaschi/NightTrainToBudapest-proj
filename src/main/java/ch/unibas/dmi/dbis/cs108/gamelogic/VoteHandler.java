package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;

/**
 * Handles the event of voting for humans and ghosts. Differentiates between day and night (human
 * vote / ghost vote) and handles votes accordingly. - Sends voting request to passengers that need
 * to be concerned - collects voting results - calculates who was voted for - decides consequence of
 * vote: - Is it OG ghost: humans win - Is it last human: ghosts win - Is it just a human: message
 * "x is a human" - Is it a peasant ghost -> kickoff
 *
 * <p>(All messages going to Clients are handled via ServerGameInfoHandler)
 *
 * <p>TODO: Think about if the timer needs to be implemented here or in the Game class
 */
public class VoteHandler {
  public static void ghostVote(Passenger[] passengers) {
    // Walk through entire train, ask ghosts to ghostify and humans to wait
    // TODO: Messages in for-loop should be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send("Vote on who to ghostify!");
      } else {
        passenger.send("Please wait, ghosts are active");
      }
    }
    for (Passenger passenger : passengers) {
      // TODO (Alex): Count received votes and deal with results
      if (passenger.getHasVoted()) {
      }
    }
  }
}
