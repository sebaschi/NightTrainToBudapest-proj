package ch.unibas.dmi.dbis.cs108.multiplayer.server;


import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JServerProtocolParser {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * jsdcjkhcsdjksdacjkn
   */
  public static final String CHATA = "CHATA";

  /**
   * Used by the server (i.e. ClientHandler) to parse an incoming protocol message.
   *
   * @param msg the encoded message that needs to be parsed
   * @param h   this ClientHandler (required so this method can access the ClientHandler's methods)
   */
  public static void parse(String msg, ClientHandler h) {
    //LOGGER.debug("got message: " + msg + ".");
    String header = "";             //"header" is the first 5 characters, i.e. the protocol part
        try {
      header = msg.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
    }
    switch (header) {
      case CHATA:
        //sends chat message to all connected clients
        h.broadcastChatMessage(msg.substring(6));
        break;
      case "LOGON":
        //sets name to whatever follows LOGON$
        try {
          h.setUsernameOnLogin(msg.substring(6));
        } catch (Exception e) {
          h.setUsernameOnLogin("A Mysterious Passenger");
        }
        break;
      case "NAMEC":
        //changes name to whatever follows NAMEC$. If the new name is already in use, it will append
        //random numbers to the name.
        h.changeUsername(msg.substring(6));
        break;
      case "CPING":
        //sends a pingback to the client
        h.sendMsgToClient("PINGB");
        break;
      case "PINGB":
        //registers pingback from client
        h.serverPinger.setGotPingBack(true);
        break;
      case "QUITS":
        //safely disconnects the user
        h.removeClientOnLogout();
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
