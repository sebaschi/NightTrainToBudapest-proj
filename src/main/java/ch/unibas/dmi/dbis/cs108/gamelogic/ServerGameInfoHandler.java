package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
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
  public static String format(String msg, Passenger p, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.ghostVoteRequest:
        msg = Protocol.serverRequestsGhostVote + "$" + p.getPosition() +"$" + game.gameState.toString();
        break;
      case ClientGameInfoHandler.humanVoteRequest:
        msg = Protocol.serverRequestsHumanVote + "$" + p.getPosition() +"$"+ game.gameState.humanToString();
        break;
      default:
        msg = Protocol.printToClientConsole + "$"+ msg;
    }
    LOGGER.debug(msg);
    return msg;
  }

  /**
   * decides which action an GhostNpc needs to take, based on a message
   * @param npc the GhostNpc needing to do smt
   * @param msg the msg containing the information on what to do
   * @param game the game the GhostNpc lives in (in gameState.passengerTrain)
   */
  public static void ghostNpcParser(GhostNPC npc, String msg, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.noiseNotification:
        //TODO(Seraina & Alex): noise handling
        game.getClientHandler().broadcastChatMessage(ClientGameInfoHandler.noiseNotification);
        break;
      case ClientGameInfoHandler.ghostVoteRequest:
        npc.vote(game);
    }


  }

  /**
   * decides which action an HumanNpc needs to take, based on a message
   * @param npc the HumanNpc needing to do smt
   * @param msg the msg containing the information on what to do
   * @param game the game the HumanNpc lives in (in gameState.passengerTrain)
   */
  public static void humanNpcParser(HumanNPC npc, String msg, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.noiseNotification:
        game.getClientHandler().broadcastChatMessage(ClientGameInfoHandler.noiseNotification);
        break;
      case ClientGameInfoHandler.humanVoteRequest:
        npc.vote();
    }


  }


}
