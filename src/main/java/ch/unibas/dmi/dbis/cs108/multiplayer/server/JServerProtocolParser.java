package ch.unibas.dmi.dbis.cs108.multiplayer.server;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;

public class JServerProtocolParser {

  public static final Logger LOGGER = LogManager.getLogger(JServerProtocolParser.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


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
      if (!header.equals(Protocol.pingBack) && !header.equals(
          Protocol.pingFromClient)) { //for debuging without constant pings
        LOGGER.debug("got message: " + msg + ".");
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
    }
    switch (header) {
      case Protocol.chatMsgToAll:
        h.broadcastChatMessageToAll(msg.substring(6));
        break;
      case Protocol.chatMsgToLobby:
        h.broadcastChatMessageToLobby(msg.substring(6));
        break;
      case Protocol.whisper:
        //find ClientHandler
        try {
          ClientHandler target = null;
          String[] targetAndMsg = h.decodeWhisper(msg.substring(6));
          String targetName = targetAndMsg[0];
          String chatMsg = targetAndMsg[1];
          for (ClientHandler c : ClientHandler.getConnectedClients()) {
            if (c.getClientUserName().equals(targetName)) {
              target = c;
            }
          }
          assert target != null;
          h.whisper(chatMsg, target);
        } catch (Exception ignored) {
          h.sendMsgToClient(Protocol.printToClientChat + "$Something went wrong while whispering.");
        }
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
      case Protocol.joinLobby:
        try {
          int i = Integer.parseInt(msg.substring(6, 7));
          h.joinLobby(i);
        } catch (Exception e) {
          h.sendMsgToClient(Protocol.printToClientConsole
              + "$Invalid input. Please use JOINL$1 to join Lobby 1, for example.");
        }
        break;
      case Protocol.createNewLobby:
        h.createNewLobby();
        break;
      case Protocol.listLobbies:
        h.listLobbies();
        break;
      case Protocol.listPlayersInLobby:
        h.listPlayersInLobby();
        break;
      case Protocol.leaveLobby:
        h.leaveLobby();
        break;
      case Protocol.votedFor:
        LOGGER.debug("Made it here");
        msg = msg.substring(6);
        h.decodeVote(msg);
        break;
      case Protocol.startANewGame:
        h.startNewGame();
        break;
      case Protocol.listGames:
        h.listGames();
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
