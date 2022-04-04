package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  /**
   * TODO(Alex): Documentation
   * @param passengers
   */
  public void ghostVote(Passenger[] passengers, Game game) {


    // array to collect votes for all players during voting, i.e. votes for player 1 (passengers[0]) are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask ghosts to ghostify and humans to wait
    // TODO(Seraina): Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        LOGGER.info("Send msg to Ghost in Position: " + passenger);
        passenger.send("Vote on who to ghostify!");
      } else {
        passenger.send("Please wait, ghosts are active"); //TODO(Seraina): make sure whatever clients send in this time, except chat is ignored
        LOGGER.info("Send msg to Human in Position: " + passenger);
      }
    }

    for (Passenger passenger : passengers) {
      // collecting the votes - distribute them among the vote counters for all players
      // Note: Each voting collects votes for all players even though some might not be concerned
      // (i.e. ghosts during ghost vote). Those players will then get 0 votes so it doesn't matter.
      // TODO: Perhaps the vote results should be handled by ClientGameInfoHandler
      if (passenger.getHasVoted()) {
        for (int i = 0; i < votesForPlayers.length; i++) {
          if (passenger.getVote() == i) {
            votesForPlayers[i]++;
          }
        }
      }
    }

    // count the votes - determine which player has the most votes by going through the
    // votesForPlayers array
    int currentMax = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] > currentMax) {
        currentMax = votesForPlayers[i];
      }
    }
    LOGGER.info("Most votes" + currentMax);

    // ghostify the player with most votes
    int ghostPosition = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player has most votes
        ghostPosition = i;
        LOGGER.info("Most votes for Passenger" + i);

      }
    }
    GhostifyHandler gh = new GhostifyHandler();
    Ghost g = gh.ghost(passengers[ghostPosition],game);
    passengers[ghostPosition] = g;
    passengers[ghostPosition].send(
        "You are now a ghost!"); // TODO: ServerGameInfoHandler might deal with this one
  }

  /**
   * TODO(Alex): Documentation
   * @param passengers
   */
  public void humanVote(Passenger[] passengers) {
    // very similar to ghostVote, differs mostly in the way votes are handled

    // array to collect votes for all players during voting, i.e. votes for player 1 are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask humans to vote and ghosts to wait
    // TODO: Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send("Please wait, humans are active");
      } else {
        passenger.send("Vote for a ghost to kick off!");
      }
    }

    for (Passenger passenger : passengers) {
      // collecting the votes - distribute them among the vote counters for all players
      // TODO: Perhaps the vote results should be handled by ClientGameInfoHandler
      if (passenger.getHasVoted()) {
        for (int i = 0; i < votesForPlayers.length; i++) {
          if (passenger.getVote() == i) {
            votesForPlayers[i]++;
          }
        }
      }
    }

    // count the votes - determine which player has the most votes by going through the
    // votesForPlayers array
    int currentMax = 0;
    for (int votesForPlayer : votesForPlayers) {
      if (votesForPlayer > currentMax) {
        currentMax = votesForPlayer;
      }
    }
    // deal with voting results
    int voteIndex = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player has most votes
        voteIndex = i;

      }
    }
    if (!passengers[voteIndex].getIsGhost()) { // if player with most votes is human, notify everyone about it
      for (Passenger passenger : passengers) {
        passenger.send(
            "You voted for a human!"); // TODO: ServerGameInfoHandler might be better to use here
      }
    }
    if (passengers[voteIndex].getIsGhost()) { // if player is a ghost
      if (passengers[voteIndex].i
    }
  }
}
