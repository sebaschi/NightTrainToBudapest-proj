package ch.unibas.dmi.dbis.cs108.multiplayer.server.cmd.methods;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol.NTtBCommands;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.io.IOException;

/**
 * This Class implements actually acting on the clients messages.
 */
public class CommandExecuter {

  private ClientHandler caller;
  private static String msgPrefix = "SERVER: ";

  public void execute(NTtBFormatMsg msg) {
    switch (msg.getCommand()) {
      case CRTGM:
        break;
      case CHATA:
        broadcastClientMsg(msg.getParameters());
        break;
      case CHATG:
        //TODO
        break;
      case LEAVG:
        //TODO
        break;
      case JOING:
        //TODO
        break;
      case VOTEG:
        //TODO
        break;
      case QUITS:
        quitServer();
        break;
      case CHATW:
        wisper(msg.getParameters());
        break;
      case LISTP:
        //TODO
        break;
      case CUSRN:
        changeNickname(msg.getParameters());
        break;
      case CPING:
        pongS();
        break;
      case MSGRS:
        //TODO
        break;
      case SEROR:
        //TODO
        break;
      case SPING:
        pongC();
        break;
      default:
        this.noCommand();
        break;
    }
  }

  private void wisper(String[] parameters) {
    //TODO
  }

  private void changeNickname(String[] parameters) {
    //TODO
  }

  private void quitServer() {
    //TODO
  }

  private void pongC() {
    //TODO
  }

  private void pongS() {
    //TODO
  }

  private void noCommand() {
    try {
      caller.getOut().write(msgPrefix + String.valueOf(NTtBCommands.NOCMD));
    } catch (IOException e) {
      System.out.println("IOException in noCommand() in CommandExecuter.java");
      e.printStackTrace();
    }
  }

  /**
   * boradcast chat message to everyone
   *
   * @param parameters should only have one entry i.e. parameters.length == 1 should be true;
   */
  private static void broadcastClientMsg(String[] parameters) {
    try {
      for (ClientHandler clients : ClientHandler.connectedClients) {
        clients.getOut().write(parameters[0]);
      }
    } catch (IOException e) {
      System.out.println("IOEXCEPTION in CommandExecuter.java at broadcastClientMsg");
    }

  }

}
