package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
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

  private static ClientVoteData clientVoteData = new ClientVoteData();

  public static ClientVoteData getClientVoteData() {
    return clientVoteData;
  }



  public static void setClientVoteData(ClientVoteData clientVoteData) {
    clientVoteData = clientVoteData;
  }




  /**
   * Handles the ghost vote during nighttime: passengers who are ghosts are being asked on who to
   * ghostify, others are waiting. Results are being collected and the player with most votes is
   * being ghostified.
   *
   * @param passengers: passengers on the train
   */

  public void ghostVote(Passenger[] passengers, Game game) {
    LOGGER.debug("ghostVote has been called");
    LOGGER.info(game.getGameState().toString());

    // array to collect votes for all players during voting, i.e. votes for player 1 (passengers[0])
    // are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask ghosts to ghostify and humans to wait
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {

        passenger.send(ClientGameInfoHandler.ghostVoteRequest, game);
      } else {
        passenger.send(
            ClientGameInfoHandler.itsNightTime, game);
                                               // this time, except chat is ignored

      }
    }

    try { // waits 20 seconds before votes get collected
      Thread.sleep(30*1000);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }


    for (Passenger passenger : passengers) { //TODO: could be outsourced to a method to increase readability
      // collecting the votes - distribute them among the vote counters for all players
      // Note: Each voting collects votes for all players even though some might not be concerned
      // (i.e. ghosts during ghost vote). Those players will then get 0 votes so it doesn't matter.
      // TODO: Perhaps the vote results should be handled by ClientGameInfoHandler
      passenger.getVoteFromGameState(clientVoteData, game);
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
    LOGGER.debug("Most votes: " + currentMax + " vote");

    // ghostify the player with most votes
    int ghostPosition = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player at position i has most votes
        ghostPosition = i;
        LOGGER.debug("Most votes for Passenger " + i);

      }
    }
    LOGGER.info("Most votes for: " + ghostPosition);
    GhostifyHandler gh = new GhostifyHandler();
    Ghost g = gh.ghost(passengers[ghostPosition], game);
    passengers[ghostPosition] = g;
    passengers[ghostPosition].send(
        ClientGameInfoHandler.youGotGhostyfied, game); // TODO: ServerGameInfoHandler might deal with this one
    try { // waits 20 seconds before votes get collected
      Thread.sleep(10);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }

    /* notify passengers the ghosts passed by - for each ghost that ghostified a player, an instance of NoiseHandler
    is being created and the passengers this ghost passed by are being notified. The player who's just been ghostified
     is ignored since he didn't participate in this night's ghostification. */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != ghostPosition) {
        NoiseHandler n = new NoiseHandler();
        n.noiseNotifier(passengers, passengers[i], g, game);
      }
    }

    LOGGER.info(game.getGameState().toString());
    // set hasVoted to false for all passengers for future votings
    for (Passenger passenger : passengers) {
      passenger.setHasVoted(false);
    }
  }

  /**
   * Handles the human vote during daytime. Asks human players to vote for a ghost to kick out while
   * ghosts are waiting. Votes are being collected, vote results are being handled in three possible
   * ways: if passenger who was voted for is a human, continue with next ghost vote; if it's a
   * normal ghost, kick him off; if it's the OG ghost, end game, humans win.
   * @return Returns an empty String by default, returns a complex string when game is over:
   * "Game over: ghosts win!" or "Game over: humans win!"
   * @param passengers: train passengers
   */
  public String humanVote(Passenger[] passengers, Game game) {
    LOGGER.info(game.getGameState().toString());

    // array to collect votes for all players during voting, i.e. votes for player 1 are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask humans to vote and ghosts to wait
    // TODO: Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send(ClientGameInfoHandler.itsDayTime, game);
      } else {
        passenger.send(ClientGameInfoHandler.humanVoteRequest , game);
      }
    }

    try { // waits 20 seconds before votes get collected
      Thread.sleep(20*1000);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }

    for (Passenger passenger : passengers) {
      passenger.getVoteFromGameState(clientVoteData, game);
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
    LOGGER.info("Player " + voteIndex + " has the most votes");
    if (!passengers[voteIndex]
        .getIsGhost()) { // if player with most votes is human, notify everyone about it
      for (Passenger passenger : passengers) {
        passenger.send(
            ClientGameInfoHandler.humansVotedFor + voteIndex + ClientGameInfoHandler.isAHuman, game); // TODO: ServerGameInfoHandler might be better to use here
      }
    }
    if (passengers[voteIndex].getIsGhost()) { // if player is a ghost
      if (passengers[voteIndex].getIsOG()) { // if ghost is OG --> end game, humans win
        System.out.println(ClientGameInfoHandler.gameOverHumansWin); // TODO: correctly handle end of game
        return ClientGameInfoHandler.gameOverHumansWin;
      } else {
        /* Special case: if ghost is not OG and if only one human is left (--> last human didn't vote for OG ghost),
        ghosts win.
         */
        int humans = 0; // variable to count the amount of humans left in the game
        for (Passenger passenger : passengers) {
          if (!passenger.getIsGhost()) {
            humans++;
          }
        }
        if (humans == 1) {
          System.out.println(ClientGameInfoHandler.gameOverGhostsWin);
          return ClientGameInfoHandler.gameOverGhostsWin;
        }
        // Usual case: there is more than one human left and a normal ghost has been voted for -->
        // kick this ghost off
        passengers[voteIndex].setKickedOff(true);
        for (Passenger passenger : passengers) {
          passenger.send("Player " + voteIndex + ClientGameInfoHandler.gotKickedOff, game);
        }
      }
    }
    LOGGER.info(game.getGameState().toString());
    // set hasVoted to false for all passengers for future voting
    for (Passenger passenger : passengers) {
      passenger.setHasVoted(false);
    }
    return "";
  }

  /**
   * Just a print Method for testing the VoteHandler
   * @param array the Passenger array to be visualized
   */
  static void print(Passenger[] array) {
    System.out.println();
    String[] print = new String[6];
    for (int i = 0; i < array.length; i++) {
      if (array[i].getKickedOff()) {
        print[i] = "| kicked off " + array[i].getPosition() + "|";
      } else {
        if (array[i].getIsGhost()) {
          print[i] = "| ghost " + array[i].getPosition() + "|";
        } else {
          print[i] = "| human " + array[i].getPosition() + "|";
        }
      }
    }

    for (int i = 0; i < array.length; i++) {
      System.out.print(print[i]);
    }
    System.out.println();

  }

}
