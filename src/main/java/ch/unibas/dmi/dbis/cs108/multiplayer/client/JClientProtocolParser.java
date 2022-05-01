package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.io.OutputStreamWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JClientProtocolParser {

  public static final Logger LOGGER = LogManager.getLogger(JClientProtocolParser.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  /**
   * Used by the client to parse an incoming protocol message. For documentation on the individual
   * Protocol messages, view the Protocol.java class or hover over the commands (e.g.
   * Protocol.chatMsgToAll) with your mouse in this class.
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
        LOGGER.debug(msg);
        System.out.println(msg.substring(6));
        if (!msg.substring(6).equals("Your vote was invalid")) {
          c.notificationTextDisplay(msg.substring(6));
        }
        break;
      case Protocol.printToClientChat:
        //todo: handle chat separately from console.
        c.sendToChat(msg.substring(6));
        break;
      case Protocol.serverConfirmQuit:
        c.disconnectFromServer();
        break;
      case Protocol.serverRequestsGhostVote:
        LOGGER.debug("Ghost received Vote request");
        c.positionSetter(msg.substring(6));
        break;
      case Protocol.serverRequestsHumanVote:
        LOGGER.debug("Human received Vote request");
        System.out.println("Human Vote:");
        c.positionSetter(msg.substring(6));
        break;
      case Protocol.changedUserName:
        c.changeUsername(msg.substring(6));
        break;
      case Protocol.printToGUI:
        LOGGER.info("First line of printToGui case!");
        String substring = msg.substring(6);
        LOGGER.debug("Following parameters where recieved: " + substring);
        int index = substring.indexOf("$");
        LOGGER.debug("Index of $: " + index);
        String parameter = "";
        String data = substring;
        try {
          parameter = substring.substring(0, index);
          data = substring.substring(index + 1);
          LOGGER.debug("Parameter: " + parameter + ". Data: " + data);
        } catch (Exception e) {
          LOGGER.warn("No parameter in PTGUI");
        }
        c.sendToGUI(parameter, data);
        break;
      case Protocol.positionOfClient:
        try {
          int position = Integer.parseInt(msg.substring(6));
          GameController.getClient().getClient().setPosition(position);
        } catch (Exception e) {
          LOGGER.warn(msg.substring(6));
        }
        break;
      default:
        System.out.println("Received unknown command: " + msg);
    }
  }
}
