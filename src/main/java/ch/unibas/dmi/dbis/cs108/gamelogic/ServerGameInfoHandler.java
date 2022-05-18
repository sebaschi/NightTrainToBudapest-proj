package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.HumanNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles all communications Server to Client concerning game state or game state related requests
 * - Needs a possibility to only send to Ghosts - and only humans
 */

public class ServerGameInfoHandler {

  public static final Logger LOGGER = LogManager.getLogger(ServerGameInfoHandler.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Gets a string msg from somewhere and formats it into protocol messages
   *
   * @param msg       the message to be formatted
   * @param passenger the passenger getting the message
   * @param game      the game in wich the passenger lives
   * @return a message in a protocol format
   */
  public static String format(String msg, Passenger passenger, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.ghostVoteRequest:
        msg = Protocol.serverRequestsGhostVote + "$" + passenger.getPosition() + "$";
        passenger.getClientHandler().sendMsgToClient(Protocol.printToClientConsole + "$" + ClientGameInfoHandler.ghostVoteRequest);
        break;
      case ClientGameInfoHandler.humanVoteRequest:
        msg = Protocol.serverRequestsHumanVote + "$" + passenger.getPosition() + "$";
        passenger.getClientHandler().sendMsgToClient(Protocol.printToClientConsole + "$" + ClientGameInfoHandler.humanVoteRequest);
        break;
      default:
        if(!msg.contains("$")) {
          msg = Protocol.printToClientConsole + "$" + msg;
        } else {
          msg = msg;
        }
    }
    LOGGER.debug(msg);
    return msg;
  }

  /**
   * //TODO(Seraina): Smart implementation that sends all relevant things to spectator, so they
   * won't get bored Formartiert Nachrichten die für einen Spectator gedacht sind.
   *
   * @param msg       the message to be formatted
   * @param game      the game in wich the passenger lives
   * @return a message in a protocol format
   */
  public static String spectatorFormat(String msg, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.ghostVoteRequest:
      case ClientGameInfoHandler.itsNightTime:
        msg = Protocol.printToClientConsole + "$Ghosts are voting";
        break;
      case ClientGameInfoHandler.humanVoteRequest:
      case ClientGameInfoHandler.itsDayTime:
        msg = Protocol.printToClientConsole + "$Humans are voting";
        break;
      case GuiParameters.updateGameState:
        msg = Protocol.printToGUI + "$" + GuiParameters.updateGameState + game.getGameState().toGhostString();
            break;
      default:
        if(!msg.contains("$")) {
          msg = Protocol.printToClientConsole + "$" + msg;
        } else {
          msg = msg;
        }
    }
    LOGGER.debug(msg);
    return msg;
  }

  /**
   * Chooses for an NPC what they want to say, so they don't sound the same all the time
   *
   * @return a String saying that sm heard sm noise
   */
  public static String noiseRandomizer() {
    String a = "I heard some noise tonight";
    String b = "noise";
    String c = "I heard smt strange tonight";
    String d = "Me, noise!";
    String e = "Uuuuh, spoky sounds";
    int number = (int) (Math.random() * 5);
    switch (number) {
      case 0:
        return a;
      case 1:
        return d;
      case 2:
        return c;
      case 3:
        return e;
      default:
        return b;
    }
  }

  /**
   * decides which action an GhostNpc needs to take, based on a message
   *
   * @param npc  the GhostNpc needing to do smt
   * @param msg  the msg containing the information on what to do
   * @param game the game the GhostNpc lives in (in gameState.passengerTrain)
   */
  public static void ghostNpcParser(GhostNPC npc, String msg, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.noiseNotification + 1 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 2 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 3 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 4 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 5 + " time(s)":
      case ClientGameInfoHandler.noiseNotification:

        if(!npc.getKickedOff()) {
          if (Math.random() < GhostNPC.probabilityToRingAlarmIfHeardNoise) {
            game.getLobby().getAdmin().sendMsgToClientsInLobby(
                    Protocol.printToGUI + "$" + GuiParameters.noiseHeardAtPosition
                            + "$" + npc.getPosition());
          }
        }
        break;
      case ClientGameInfoHandler.ghostVoteRequest:
        npc.vote(game);
    }
  }

  /**
   * decides which action an HumanNpc needs to take, based on a message
   *
   * @param npc  the HumanNpc needing to do smt
   * @param msg  the msg containing the information on what to do
   * @param game the game the HumanNpc lives in (in gameState.passengerTrain)
   */
  public static void humanNpcParser(HumanNPC npc, String msg, Game game) {
    switch (msg) {
      case ClientGameInfoHandler.noiseNotification + 1 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 2 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 3 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 4 + " time(s)":
      case ClientGameInfoHandler.noiseNotification + 5 + " time(s)":
      case ClientGameInfoHandler.noiseNotification:       //new case where times are not noted.
        if(!npc.getKickedOff()) {
          if (Math.random() < HumanNPC.probabilityToRingAlarmIfHeardNoise) {
            game.getLobby().getAdmin().sendMsgToClientsInLobby(
                    Protocol.printToGUI + "$" + GuiParameters.noiseHeardAtPosition
                            + "$" + npc.getPosition());
          }

        }
        break;
      case ClientGameInfoHandler.humanVoteRequest:
        npc.vote(game);
    }
  }

}
