package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
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
  public void ghostVote(Passenger[] passengers) {

    // array to collect votes for all players during voting, i.e. votes for player 1 are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask ghosts to ghostify and humans to wait
    // TODO: Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send("Vote on who to ghostify!");
      } else {
        passenger.send("Please wait, ghosts are active");
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

    // ghostify the player with most votes
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player has most votes
        GhostifyHandler gh = new GhostifyHandler();
        gh.ghostify(passengers[i]);
        passengers[i].send(
            "You are now a ghost!"); // TODO: ServerGameInfoHandler might deal with this one
      }
    }
  }

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
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player has most votes
        if (!passengers[i].getIsGhost()) { // if player with most votes is human, notify everyone about it
          for (Passenger passenger : passengers) {
            passenger.send(
                    "You voted for a human!"); // TODO: ServerGameInfoHandler might be better to use here
          }
        }
        if (passengers[i].getIsGhost()) { // if player is a ghost
          // Now the case "ghost is og" and the case "ghost is not og" need to be handled
          /* TODO: I don't know how to get the information about a ghost being the OG because I'm accessing the players
              via the Passenger class which can't use the getIsOG method from the Ghost class. I (Alex) will try to
              solve this issue but if anyone can help please do!
          */
        }
      }
    }
  }
}
