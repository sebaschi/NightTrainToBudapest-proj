package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
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
  public static String format(String msg) {
    switch (msg) {
      case "Vote on who to ghostify!":
        msg = Protocol.serverRequestsGhostVote;
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

  /**
   * TODO(Seraina): Handle NPC's Maybe do that in Passenger send methode!
   * Send a message "GVOTR" to a passenger urging them to vote for a human to infect
   * Currently only handles only Players, so send a message to corresponding client
   * @param passenger the passenger the message is meant to, should be a Ghost
   */
  public void sendVoteRequestGhosts(Passenger passenger){
    passenger.getClientHandler().sendMsgToClient("GVOTR");
  }


  /**
   * TODO(Seraina): Handle NPC's
   * Send a message "HVOTR" to a passenger urging them to vote for sm to kick off the train.
   * Currently only handles only Players, so send a message to corresponding client
   * @param passenger the passenger the message is meant to, can be either human or ghost
   */
  public void sendVoteRequestHumans(Passenger passenger){
    passenger.getClientHandler().sendMsgToClient("HVOTR");
  }

  public static void main(String[] args) {
    ServerGameInfoHandler s = new ServerGameInfoHandler();
    s.format("jhbvdwfzu");
  }

}
