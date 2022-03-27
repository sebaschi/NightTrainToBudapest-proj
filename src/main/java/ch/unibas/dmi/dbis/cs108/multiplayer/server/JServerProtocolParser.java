package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.jonasStuff.ServerProtocol;


public class JServerProtocolParser {

  /**
   * Used by the server (i.e. ClientHandler) to parse an incoming protocol message.
   * @param msg the encoded message that needs to be parsed
   * @param h this ClientHandler (required so this method can access the ClientHandler's methods)
   */
  public static void parse(String msg, ClientHandler h) {
    String header = "";
    try {
      header = msg.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    System.out.println(header);
    switch (header) {
      case "CHATA":
        h.broadcastMessage(msg.substring(6));
        return;
      case "CPING":
        //todo: pingback
        return;
      case "PINGB":
        //todo: register pingback
        return;
      default:
        System.out.println("Received unknown command");
    }
  }
}
