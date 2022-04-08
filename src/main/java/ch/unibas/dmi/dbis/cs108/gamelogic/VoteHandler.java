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
   * Handles the ghost vote during nighttime: passengers who are ghosts are being asked on who to
   * ghostify, others are waiting. Results are being collected and the player with most votes is
   * being ghostified.
   *
   * @param passengers: passengers on the train
   */
  public void ghostVote(Passenger[] passengers, Game game) {

    // array to collect votes for all players during voting, i.e. votes for player 1 (passengers[0])
    // are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask ghosts to ghostify and humans to wait
    // TODO(Seraina): Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {

        passenger.send("Vote on who to ghostify!", game);
      } else {
        passenger.send(
            "Please wait, ghosts are active", game); // TODO(Seraina): make sure whatever clients send in
                                               // this time, except chat is ignored

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
            LOGGER.info(passengers[i] + " has received the most votes");
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
    LOGGER.info("Most votes: " + currentMax + " vote");

    // ghostify the player with most votes
    int ghostPosition = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player at position i has most votes
        ghostPosition = i;
        LOGGER.info("Most votes for Passenger " + i);

      }
    }
    LOGGER.debug("ghostPosition: " + ghostPosition);
    GhostifyHandler gh = new GhostifyHandler();
    Ghost g = gh.ghost(passengers[ghostPosition], game);
    passengers[ghostPosition] = g;
    passengers[ghostPosition].send(
        "You are now a ghost!", game); // TODO: ServerGameInfoHandler might deal with this one

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


    // array to collect votes for all players during voting, i.e. votes for player 1 are saved in
    // votesForPlayers[0]
    int[] votesForPlayers = new int[6];

    // Walk through entire train, ask humans to vote and ghosts to wait
    // TODO: Messages in for-loop should probably be handled by ServerGameInfoHandler
    for (Passenger passenger : passengers) {
      if (passenger.getIsGhost()) {
        passenger.send("Please wait, humans are active", game);
      } else {
        passenger.send("Vote for a ghost to kick off!", game);
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
        LOGGER.info("Max amount of votes: " + currentMax);
      }
    }
    // deal with voting results
    int voteIndex = 0;
    for (int i = 0; i < votesForPlayers.length; i++) {
      if (votesForPlayers[i] == currentMax) { // if player has most votes
        voteIndex = i;
        LOGGER.info("Player " + voteIndex + " has the most votes");
      }
    }
    if (!passengers[voteIndex]
        .getIsGhost()) { // if player with most votes is human, notify everyone about it
      for (Passenger passenger : passengers) {
        passenger.send(
            "You voted for a human!", game); // TODO: ServerGameInfoHandler might be better to use here
      }
    }
    if (passengers[voteIndex].getIsGhost()) { // if player is a ghost
      if (passengers[voteIndex].getIsOG()) { // if ghost is OG --> end game, humans win
        System.out.println("Game over: humans win!"); // TODO: correctly handle end of game
        return "Game over: humans win!";
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
          System.out.println("Game over: ghosts win!");
          return "Game over: ghosts win!";
        }
        // Usual case: there is more than one human left and a normal ghost has been voted for -->
        // kick this ghost off
        passengers[voteIndex].setKickedOff(true);
        for (Passenger passenger : passengers) {
          passenger.send("Player " + voteIndex + " has been kicked off!", game);
        }
      }
    }
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

  public static void main(String[] args) {
    try {
      Game game = new Game(6,1, 6);
      VoteHandler voteHandler = new VoteHandler();

      Passenger[] testArray = game.gameState.getPassengerTrain();
      Passenger ghost = new Ghost();
      testArray[3] = ghost;
      testArray[3].setGhost();
      testArray[3].setIsOg();
      testArray[3].setPosition(3);
      print(testArray);
      LOGGER.info("NIGHT");
      voteHandler.ghostVote(testArray,game);
      print(testArray);

      LOGGER.info("Day");
      voteHandler.humanVote(testArray, game);
      print(testArray);

      LOGGER.info("NIGHT");
      voteHandler.ghostVote(testArray,game);
      print(testArray);

      LOGGER.info("Day");
      voteHandler.humanVote(testArray, game);
      print(testArray);
    } catch (TrainOverflow e) {
      LOGGER.warn(e.getMessage());
    }



  }
}
