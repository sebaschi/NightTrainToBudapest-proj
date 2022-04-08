package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles all communications Server to Client concerning game state or game state related requests
 * - Needs a possibility to only send to Ghosts
 * - and only humans
 * TODO: Figure out what format the messages have, consider protocol add what is needed dont forget about parsing
 */

public class ServerGameInfoHandler {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Gets a string msg from somewhere and formats it into protocol messages
   * @param msg the message to be formatted
   * @return a message in a protocol format
   */
  public static String format(String msg, Game game) {
    switch (msg) {
      case "Vote on who to ghostify!":
        msg = Protocol.serverRequestsGhostVote + "$" + game.gameState.toString();
        break;
      case "Vote for a ghost to kick off!":
        msg = Protocol.serverRequestsHumanVote;
        break;
      default:
        msg = Protocol.printToClientConsole + "$" + msg;
    }
    LOGGER.info(msg);
    return msg;
  }

  public static void ghostNpcParser(GhostNPC npc, String msg, Game game) {
    switch (msg) {
      case "noise":
        npc.noise();
        break;
      case "Vote on who to ghostify!":
        npc.vote(game);
    }


  }

  public static void humanNpcParser(HumanNPC npc, String msg, Game game) {
    switch (msg) {
      case "noise":
        npc.noise();
        break;
      case "Vote for a ghost to kick off!":
        npc.vote();
    }


  }


}
