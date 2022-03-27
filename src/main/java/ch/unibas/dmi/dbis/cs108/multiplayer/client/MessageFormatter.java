package ch.unibas.dmi.dbis.cs108.multiplayer.client;

public class MessageFormatter {

  /**
   * Takes a given Message and reformats it to where the JServerProtocolParser.parse() method can
   * handle it. May need to be redesigned one the games uses a GUI
   *
   * @param msg the Messaged to be reformatted
   * @return the reformatted message in the form HEADR$msg
   */

  public static String formatMsg(String msg) {
    String header = ""; //header is first two characters
    StringBuilder stringBuilder = new StringBuilder();
    String s; // just a friendly helper to save message in
    try {
      header = msg.substring(0, 2);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    switch (header) {
      case "/c":
        stringBuilder.append("CHATA$");
        s = msg.substring(3);
        break;
      case "/q":
        stringBuilder.append("QUITS$");
        s = msg.substring(3);
        break;
      case "/n":
        stringBuilder.append("NAMEC$");
        s = msg.substring(3);
        break;
      default:
        s = msg;
    }
    stringBuilder.append(s);
    return stringBuilder.toString();

  }


}
