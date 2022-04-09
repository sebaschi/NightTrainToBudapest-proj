package ch.unibas.dmi.dbis.cs108.multiplayer.server;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientVoteData;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.GameState;
import ch.unibas.dmi.dbis.cs108.gamelogic.TrainOverflow;
import ch.unibas.dmi.dbis.cs108.gamelogic.VoteHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;

public class JServerProtocolParser {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * jsdcjkhcsdjksdacjkn
   */
  public static final String CHATA = "CHATA";


  /**
   * Used by the server (i.e. ClientHandler{@link ClientHandler}) to parse an incoming protocol
   * message. For documentation on the individual Protocol messages, view the Protocol.java class or
   * hover over the commands (e.g. Protocol.chatMsgToAll) with your mouse in this class.
   *
   * @param msg the encoded message that needs to be parsed
   * @param h   this ClientHandler (required so this method can access the ClientHandler's methods)
   */
  public static void parse(String msg, ClientHandler h) {
    //LOGGER.debug("got message: " + msg + ".");
    String header = "";             //"header" is the first 5 characters, i.e. the protocol part
    try {
      header = msg.substring(0, 5);
      if(!header.equals(Protocol.pingBack) &&!header.equals(Protocol.pingFromClient)) { //for debuging without constant pings
        LOGGER.debug("got message: " + msg + ".");
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
    }
    switch (header) {
      case Protocol.chatMsgToAll:
        h.broadcastChatMessage(msg.substring(6));
        break;
      case Protocol.clientLogin:
        h.setLoggedIn(true);
        try {
          h.setUsernameOnLogin(msg.substring(6));
        } catch (Exception e) {
          h.setUsernameOnLogin("U.N. Owen");
        }
        break;
      case Protocol.nameChange:
        h.changeUsername(msg.substring(6));
        break;
      case Protocol.pingFromClient:
        h.sendMsgToClient(Protocol.pingBack);
        break;
      case Protocol.pingBack:
        h.serverPinger.setGotPingBack(true);
        break;
      case Protocol.clientQuitRequest:
        h.removeClientOnLogout();
        break;
      case Protocol.createNewGame:
        // TODO add h.openLobby(h) method
        LOGGER.debug(Protocol.createNewGame
            + " command reached in JServerProtocolParser. Command issued by: "
            + h.getClientUserName());
        break;
      case Protocol.listLobbies:
        //TODO: add action
        LOGGER.debug(Protocol.listLobbies + " command received from: " + h.getClientUserName());
        break;
      case Protocol.votedFor:
        LOGGER.debug("Made it here");
        msg = msg.substring(6);
        h.decodeVote(msg);
        break;
      case Protocol.startANewGame:
        h.startNewGame();
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
