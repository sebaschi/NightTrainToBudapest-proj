package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JClientProtocolParser {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  /**
   * Used by the client to parse an incoming protocol message.
   * For documentation on the individual Protocol messages, view the Protocol.java
   * class or hover over the commands (e.g. Protocol.chatMsgToAll) with your mouse
   * in this class.
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
      case Protocol.pingFromServer:

        c.sendMsgToServer(Protocol.pingBack);
        break;
      case Protocol.pingBack:
        c.clientPinger.setGotPingBack(true);
        break;
      case Protocol.printToClientConsole:
        System.out.println(msg.substring(6));
        break;
      case Protocol.serverConfirmQuit:
        c.disconnectFromServer();
        break;
      case Protocol.serverRequestsGhostVote:
        LOGGER.debug("Ghost received Vote request");
        System.out.println("Ghost Vote:");
        c.positionSetter(msg.substring(6));
        //TODO(Seraina): How can be enforced, that clients won't vote otherwise? Trigger a methode here that listens to input
        break;
      case Protocol.serverRequestsHumanVote:
        LOGGER.debug("Human received Vote request");
        System.out.println("Human Vote:");
        c.positionSetter(msg.substring(6));
        //TODO(Seraina): How can be enforced, that clients won't vote otherwise? Trigger a methode here that listens to input
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
