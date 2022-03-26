package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NoLegalProtocolCommandStringFoundException;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.ProtocolDecoder;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Decodes the correctly formatted String
 * containing command and parameters.
 * For reasons of seperation of work
 * this class only tokenizes the string
 * and acknowledges to the client that smth was recieved.
 * Actual method calls, change of state, method calles etc.
 * are delegated to{@link ch.unibas.dmi.dbis.cs108.multiplayer.server.cmd.methods.CommandExecuter}
 * from within {@link ClientHandler}.
 */

public class ClientMsgDecoder implements ProtocolDecoder {

  private NightTrainProtocol protocol;

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
    parameters = new String[msgTokens.length-1];
    cmdObject = getCommandConstant(msgTokens[0]);
    return new NTtBFormatMsg(ackMsg, cmdObject, parameters);
  }

  /*
   * Builds the servers response message
   * to client
   */
  private String serverResponseBuilder(String[] msgTokens) {
    StringBuilder sb = new StringBuilder();
    //assumes not empty list!
    NightTrainProtocol.NTtBCommands cmd = getCommandConstant(msgTokens[0]);
    sb.append("SERVER: ");
    sb.append("Command *" + cmd.toString() + "* recieved!");

    return sb.toString();
  }

  //Uses the NightTrainProtocol classes utility method
  private boolean isLegalCmdString(String cmd) {
    return protocol.isLegalCmdString(cmd);
  }

  private String getCommandStringToken(String[] msgTokens) throws NoCommandTokenException {
    return msgTokens[0];
  }

  private NightTrainProtocol.NTtBCommands getCommandConstant(String stringToken) {
    try {
      return protocol.getCmdEnumObject(stringToken);
    } catch (NoLegalProtocolCommandStringFoundException e) {
      e.printStackTrace();
      e.getMessage();
    } finally {
      return NightTrainProtocol.NTtBCommands.SEROR;
    }

  }

  //Creates tokens from the clientMsg and puts them in a list
  //TODO what side effects could be here?
  private String[] tokenizeMsg(String msg) {
    return msg.split("$");
  }
}
