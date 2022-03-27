package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;

public class JClientProtocolParser {

  /**
   * Used by the client to parse an incoming protocol message.
   * @param msg the encoded message that needs to be parsed
   * @param c this Client(required so this method can access the Client's methods)
   */
  public static void parse(String msg, Client c) {
    String header = "";                   //"header" is the first 5 characters.
    try {
      header = msg.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    System.out.println(header);
    switch (header) {
      case "SPING":
        c.sendMsgToServer("PINGB");
        System.out.println("got ping!");   //todo:delete
        break;
      case "PINGB":
        c.clientPinger.setGotPingBack(true);
        System.out.println("got pingback!");    //todo: delete
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
