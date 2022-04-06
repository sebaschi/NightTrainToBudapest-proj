package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JClientProtocolParser {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Used by the client to parse an incoming protocol message.
   *
   * @param msg the encoded message that needs to be parsed
   * @param c   this Client(required so this method can access the Client's methods)
   */
  public static void parse(String msg, Client c) {
    //LOGGER.debug("got message: " + msg + ".");
    String header = "";             //"header" is the first 5 characters, i.e. the protocol part
    try {
      header = msg.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
      e.printStackTrace();
    }
    switch (header) {
      case "SPING":
        //sends a pingback to the server
        c.sendMsgToServer("PINGB");
        break;
      case "PINGB":
        //registers pingback from server
        c.clientPinger.setGotPingBack(true);
        break;
      case "CHATM":
        /* prints out incoming chat messages / announcements into the user's console.
         * any string that follows CHATM$ is printed as is, so the message that follows
         * already has to be formatted the way it should be shown to the client.
         */
        System.out.println(msg.substring(6));
        break;
      case "QUITC":
        c.closeEverything();
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
