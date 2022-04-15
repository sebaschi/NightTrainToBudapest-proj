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

  public static String formatMsg(String msg, int position) {
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
        stringBuilder.append(Protocol.clientQuitRequest);
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
        stringBuilder.append(Protocol.createNewLobby);
        break;
      case "/l":
        stringBuilder.append(Protocol.listLobbies);
        break;
      case "/p":
        stringBuilder.append(Protocol.listPlayersInLobby);
        break;
      case "/z":
        stringBuilder.append(Protocol.listGames);
        break;
      case "/j":
        stringBuilder.append(Protocol.joinLobby + "$");
        try {
          s = msg.substring(3);
        } catch (Exception ignored) {
        }
        break;
      case "/w":
        stringBuilder.append(Protocol.whisper + "$");
        try {
          s = msg.substring(3);
        } catch (Exception ignored) {
        }
        break;
      case "/e":
        stringBuilder.append(Protocol.leaveLobby);
        break;
      case "/v":
        try {
          s = msg.substring(3);
          LOGGER.debug("substring: " + s);
        } catch (Exception e) {
          System.out.println("invalid vote");
        }
        stringBuilder.append(Protocol.votedFor + "$" + position + "$");
        break;
      case "/s":
        stringBuilder.append(Protocol.startANewGame);
        break;
      default:
        s = msg;
    }
    stringBuilder.append(s);
    LOGGER.debug(stringBuilder.toString());
    return stringBuilder.toString();

  }


}
