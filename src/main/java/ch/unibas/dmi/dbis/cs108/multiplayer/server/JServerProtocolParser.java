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
        try {
      header = msg.substring(0, 5);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Received unknown command");
    }
    switch (header) {
      case "CHATA":
        //sends chat message to all connected clients
        h.broadcastMessage(msg.substring(6));
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
        h.closeEverything(h.getSocket(), h.getIn(), h.getOut());
        break;
      default:
        System.out.println("Received unknown command");
    }
  }
}
