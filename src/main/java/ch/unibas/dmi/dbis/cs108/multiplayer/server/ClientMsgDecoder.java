package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol.NTtBCommands;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NoLegalProtocolCommandStringFoundException;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.ProtocolDecoder;


/**
 * Decodes the correctly formatted String containing command and parameters. For reasons of
 * seperation of work this class only tokenizes the string and acknowledges to the client that smth
 * was recieved. Actual method calls, change of state, method calles etc. are delegated to{@link
 * ch.unibas.dmi.dbis.cs108.multiplayer.server.cmd.methods.CommandExecuter} from within {@link
 * ClientHandler}.
 */

public class ClientMsgDecoder implements ProtocolDecoder {

  /**
   * The point of contact for the ClientHandler who calls this method to convert a String in to
   * usable, tokanized format defined by {@link NTtBFormatMsg}.
   *
   * @param msg coming from the client handlers input reader.
   * @return {@link NTtBFormatMsg}
   */
  @Override
  public NTtBFormatMsg decodeMsg(String msg) {
    //Declare needed variables
    String[] msgTokens;  //List where we'll put the string tokens seperated by $.
    String ackMsg; //The command token
    String[] parameters;
    NightTrainProtocol.NTtBCommands cmdObject;
    //Initalize fields for return object
    msgTokens = tokenizeMsg(msg);
    ackMsg = serverResponseBuilder(msgTokens);
    parameters = new String[msgTokens.length - 1];
    cmdObject = getCommandConstant(msgTokens[0]);
    return new NTtBFormatMsg(ackMsg, cmdObject, parameters);
  }

  /**
   * Constructs the server acknowledgement response witch is instantly sent back to the client. The
   * response is merely that a command was recieved and which one.
   * <p>
   * If garbage was recieved a SEROR will be appended. It is assumed that the msgTokens array is not
   * empty.
   *
   * @param msgTokens an array containing the command String
   * @return a String containing the immediate response of the server to the client.
   */
  private String serverResponseBuilder(String[] msgTokens) {
    StringBuilder sb = new StringBuilder();
    //assumes non-empty array!
    sb.append("SERVER: ");
    NightTrainProtocol.NTtBCommands cmd = getCommandConstant(msgTokens[0]);

    if (cmd.equals(NTtBCommands.SEROR)) {
      return cmd + "invalid input";
    }

    sb.append("Command *").append(cmd).append("* recieved!");

    return sb.toString();
  }


  /**
   * Turns the string into a command object. If no matching protocol command was found, it returns
   * an SEROR type.
   *
   * @param stringToken String that should match the String representation of a NTtBCommands,java
   *                    field.
   * @return type {@link ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol.NTtBCommands}
   * object
   */
  private NightTrainProtocol.NTtBCommands getCommandConstant(String stringToken) {
    try {
      return NightTrainProtocol.getCmdEnumObject(stringToken);
    } catch (NoLegalProtocolCommandStringFoundException e) {
      return NightTrainProtocol.NTtBCommands.SEROR;
    }
  }

  //TODO What happens if there is no delimeter?

  /**
   * Splits the input string along the delimiter "$".
   *
   * @param msg Clients input
   * @return an array of String objects containing the command and parameters of the message.
   */
  private String[] tokenizeMsg(String msg) {
    return msg.split("$");
  }
}
