package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageFormatter {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Takes a given client input and reformats it to where the JServerProtocolParser.parse() method can
   * handle it (see Protocol.java). May need to be redesigned once the game uses a GUI.
   *
   * @param msg the Messaged to be reformatted
   * @return the reformatted message in the form HEADR$msg
   */

  public static String formatMsg(String msg) {
    String header = ""; //header is first two characters
    StringBuilder stringBuilder = new StringBuilder();
    String s = ""; // just a friendly helper to save message in
    try {
      header = msg.substring(0, 2);
    } catch (IndexOutOfBoundsException e) {
      header = "";
    }
    switch (header) {
      case "/c":
        stringBuilder.append(Protocol.chatMsgToLobby + "$");
        try {
          s = msg.substring(3);
        } catch (Exception e) {
          System.out.println("You didn't even write a chat line, you silly billy!");
        }
        break;
      case "/b":
        stringBuilder.append(Protocol.chatMsgToAll + "$");
        try {
          s = msg.substring(3);
        } catch (Exception e) {
          System.out.println("You didn't even write a chat line, you silly billy!");
        }
        break;
      case "/q":
        stringBuilder.append(Protocol.clientQuitRequest + "$");
        s = "";
        break;
      case "/n":
        stringBuilder.append(Protocol.nameChange + "$");
        try {
          s = msg.substring(3);
        } catch (Exception e) {
          s = "U.N. Owen";
        }
        break;
      case "/g":
        stringBuilder.append(Protocol.createNewLobby + "$");
        s = ""; //command has no parameters
        break;
      case "/l":
        //LISTL command
        stringBuilder.append(Protocol.listLobbies + "$");
        s = ""; //Command has no parameters
        break;
      case "/j":
        stringBuilder.append(Protocol.joinLobby + "$");
        try {
          s = msg.substring(3);
        } catch (Exception ignored) {
        }
        break;
      default:
        s = msg;
    }
    stringBuilder.append(s);
    return stringBuilder.toString();

  }


}
