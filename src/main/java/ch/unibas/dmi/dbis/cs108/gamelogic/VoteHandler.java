package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles the event of voting for humans and ghosts. Differentiates between day and night (human
 * vote / ghost vote) and handles votes accordingly. - Sends voting request to passengers that need
 * to be concerned - collects voting results - calculates who was voted for - decides consequence of
 * vote: - Is it OG ghost: humans win - Is it last human: ghosts win - Is it just a human: message
 * "x is a human" - Is it a peasant ghost - kickoff
 *
 * <p>(All messages going to Clients are handled via ServerGameInfoHandler)
 *
 */
public class VoteHandler {
  public static final Logger LOGGER = LogManager.getLogger(VoteHandler.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Handles the ghost vote during nighttime: passengers who are ghosts are being asked on who to
   * ghostify, others are waiting. Results are being collected and the player with most votes is
   * being ghostified.
   *
   * @param passengers: passengers on the train
   * @param game the game the votehandler is in
   * @return returns a gameover message
   */

  public String ghostVote(Passenger[] passengers, Game game) {
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

    try { // waits 30 seconds before votes get collected
      Thread.sleep(10*1000);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }

    int currentMax = ghostVoteEvaluation(passengers, votesForPlayers, game.getGameState().getClientVoteData(), game);

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

    for(Passenger passenger : passengers) {
      if(passenger.getIsGhost() || passenger.getIsSpectator()) {
        passenger.send(passengers[ghostPosition].getName() + ClientGameInfoHandler.gotGhostyfied, game);
      }
    }
    Passenger g = GhostifyHandler.ghost(passengers[ghostPosition], game);
    passengers[ghostPosition] = g;
    if (!passengers[ghostPosition].getIsSpectator()) {
      passengers[ghostPosition].send(
          ClientGameInfoHandler.youGotGhostyfied, game);
    }
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }

    /* notify passengers the ghosts passed by - for each ghost that ghostified a player, an instance of NoiseHandler
    is being created and the array containing the information about the amount of times each passenger heard a ghost
    walk by is being updated. Finally, each passenger receives information about how often he heard something during
    this night. The player who's just been ghostified is ignored since he didn't participate in this night's
    ghostification. */

    int[] noiseAmount = new int[6];
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != ghostPosition) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers, passengers[i], g, noiseAmount, game);
      }
    }
    for (int i = 0; i < passengers.length; i++) {
      if (!passengers[i].getIsGhost() && noiseAmount[i] != 0) { // passenger is human and someone walked by him
        passengers[i].send(ClientGameInfoHandler.noiseNotification + noiseAmount[i] + " time(s)", game);
      }
    }

    // no humans left in the game --> everyone has been ghostified, ghosts win
    int humanCounter = 0;
    for(Passenger passenger : passengers) {
      if(!passenger.getIsGhost()) { //if it is a human
        humanCounter++;
      }
    }

    if (humanCounter == 0) {
      return ClientGameInfoHandler.gameOverGhostsWin;
    }

    LOGGER.info(game.getGameState().toString());
    // set hasVoted to false for all passengers for future voting
    for (Passenger passenger : passengers) {
      passenger.setHasVoted(false);
      passenger.setVote(Integer.MAX_VALUE);
    }
    return "";
  }

  /**
   * Handles the human vote during daytime. Asks human players to vote for a ghost to kick out while
   * ghosts are waiting. Votes are being collected, vote results are being handled in three possible
   * ways: if passenger who was voted for is a human, continue with next ghost vote; if it's a
   * normal ghost, kick him off; if it's the OG ghost, end game, humans win.
   * @return Returns an empty String by default, returns a complex string when game is over:
   * "Game over: ghosts win!" or "Game over: humans win!"
   * @param passengers train passengers
   * @param game the game the Votehandler is in
   */
  public String humanVote(Passenger[] passengers, Game game) {
    LOGGER.info(game.getGameState().toString());

    // array to collect votes for all players during voting, i.e. votes for player 1 are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask humans to vote and ghosts to wait
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send(ClientGameInfoHandler.itsDayTime, game);
      } else {
        passenger.send(ClientGameInfoHandler.humanVoteRequest, game);
      }
    }

    try { // waits 60 seconds before votes get collected
      Thread.sleep(10*1000);
    } catch (InterruptedException e) {
      LOGGER.warn("Thread " + Thread.currentThread() + " was interrupted");
    }

    int currentMax = humanVoteEvaluation(passengers, votesForPlayers, game.getGameState().getClientVoteData(), game);

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
            ClientGameInfoHandler.humansVotedFor + voteIndex + ClientGameInfoHandler.isAHuman, game);
      }
    }
    if (passengers[voteIndex].getIsGhost()) { // if player is a ghost
      if (passengers[voteIndex].getIsOG()) { // if ghost is OG --> end game, humans win
        System.out.println(ClientGameInfoHandler.gameOverHumansWin);
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
        passengers[voteIndex] = GhostifyHandler.kickOff(passengers[voteIndex], game);
        passengers[voteIndex].send(ClientGameInfoHandler.youGotKickedOff, game);
        for (Passenger passenger : passengers) {
          passenger.send(passengers[voteIndex].getName() + ClientGameInfoHandler.gotKickedOff, game);
        }
      }
    }
    LOGGER.info(game.getGameState().toString());
    // set hasVoted to false for all passengers for future voting
    for (Passenger passenger : passengers) {
      passenger.setHasVoted(false);
      passenger.setVote(Integer.MAX_VALUE);
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

  /**
   * Collecting the votes of Ghosts- distribute them among the vote counters for all players. Note: each voting collects
   * votes for all players even though some might not be concerned (i.e. ghosts during ghost vote). Those players
   * will then get 0 votes so it dosen't matter. Returns the max amount of votes a player received.
   * @param passengers train passengers
   * @param votesForPlayers array collecting the votes each player received during a voting
   * @param data deals with Client votes
   * @param game current game instance
   */
  int ghostVoteEvaluation(Passenger[] passengers, int[] votesForPlayers, ClientVoteData data, Game game) {
    for (Passenger passenger : passengers) {
      passenger.getVoteFromGameState(data, game);
      if (passenger.getHasVoted() && passenger.getIsGhost() && !passenger.getKickedOff()) {
        for (int i = 0; i < votesForPlayers.length; i++) {
          if (passenger.getVote() == i) {
            votesForPlayers[i]++;
          }
        }
      }
    }
    /* count the votes - determine which player has the most votes by going through the
    votesForPlayers array */
    int currentMax = 0;
    for (int votesForPlayer : votesForPlayers) {
      if (votesForPlayer > currentMax) {
        currentMax = votesForPlayer;
      }
    }
    return currentMax;
  }

  /**
   * Collecting the votes of Humans - distribute them among the vote counters for all players. Note: each voting collects
   * votes for all players even though some might not be concerned (i.e. ghosts during ghost vote). Those players
   * will then get 0 votes so it dosen't matter. Returns the max amount of votes a player received.
   * @param passengers train passengers
   * @param votesForPlayers array collecting the votes each player received during a voting
   * @param data deals with Client votes
   * @param game current game instance
   */
  int humanVoteEvaluation(Passenger[] passengers, int[] votesForPlayers, ClientVoteData data, Game game) {
    for (Passenger passenger : passengers) {
      passenger.getVoteFromGameState(data, game);
      if (passenger.getHasVoted() && !passenger.getIsGhost() && !passenger.getKickedOff()) {
        for (int i = 0; i < votesForPlayers.length; i++) {
          if (passenger.getVote() == i) {
            votesForPlayers[i]++;
          }
        }
      }
    }
    /* count the votes - determine which player has the most votes by going through the
    votesForPlayers array */
    int currentMax = 0;
    for (int votesForPlayer : votesForPlayers) {
      if (votesForPlayer > currentMax) {
        currentMax = votesForPlayer;
      }
    }
    return currentMax;
  }
}
