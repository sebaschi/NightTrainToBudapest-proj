package ch.unibas.dmi.dbis.cs108.multiplayer.server;


public class JServerProtocolParser {

  /**
   * Used by the server (i.e. ClientHandler) to parse an incoming protocol message.
   *
   * @param msg the encoded message that needs to be parsed
   * @param h   this ClientHandler (required so this method can access the ClientHandler's methods)
   */
  public static void parse(String msg, ClientHandler h) {
    String header = "";             //"header" is the first 5 characters, i.e. the protocol part
    String formattedMSG = MessageFormatter.formatMsg(msg);
    try {
      header = formattedMSG.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
    }
    //System.out.println(header); helpful for debugging
    switch (header) {
      case "CHATA":
        h.broadcastMessage(formattedMSG.substring(6));
        break;
      case "CPING":
        h.sendMsgToClient("PINGB");
        break;
      case "PINGB":
        h.serverPinger.setGotPingBack(true);
        break;
      case "QUITS":
        h.closeEverything(h.getSocket(), h.getIn(), h.getOut());
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
