package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.TrainOverflow;
import ch.unibas.dmi.dbis.cs108.gamelogic.VoteHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler implements Runnable {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private String clientUserName;
  private BufferedWriter out;
  private BufferedReader in;
  private Socket socket;
  private InetAddress ip;


  /**
   * notes if the client has formally logged in yet. If connecting through the normal Client class,
   * the client is logged in automatically, if connecting though some external application, the
   * client has to use the {@code Protocol.clientLogin} command.
   */
  private boolean loggedIn;

  private Scanner sc;
  public ServerPinger serverPinger;
  public static HashSet<ClientHandler> connectedClients = new HashSet<>();
  public static HashSet<ClientHandler> disconnectedClients = new HashSet<>(); //todo: implement re-connection
  public static HashSet<ClientHandler> lobby = new HashSet<>();
  public static HashSet<ClientHandler> ghostClients = new HashSet<>();

  /**
   * Implements the login logic in client-server architecture.
   * @param ip the ip of the client, used for re-connection.
   * @param socket the socket on which to make the connection.
   */
  public ClientHandler(Socket socket, InetAddress ip) {
    try {
      this.ip = ip;
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
      this.loggedIn = false;
      this.clientUserName = nameDuplicateChecker.checkName("U.N. Owen");
      connectedClients.add(this);
      serverPinger = new ServerPinger(socket, this);
      Thread sP = new Thread(serverPinger);
      sP.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Getters:
  public BufferedWriter getOut() {
    return out;
  }

  public BufferedReader getIn() {
    return in;
  }

  public Socket getSocket() {
    return socket;
  }

  /**
   * Needed to fill a train with client TODO: how do lobbies fit here?
   * @return the HashSet of Connected Clients
   */
  public static HashSet<ClientHandler> getConnectedClients() {
    return connectedClients;
  }

  public static HashSet<ClientHandler> getLobby() {
    return lobby;
  }

  public static HashSet<ClientHandler> getGhostClients() {
    return ghostClients;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public String getClientUserName() {
    return clientUserName;
  }
  //Setters:

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }


  @Override
  public void run() {
    String msg;
    while (socket.isConnected() && !socket.isClosed()) {
      try {
        msg = in.readLine();
        JServerProtocolParser.parse(msg, this);
      } catch (IOException e) {
        //e.printStackTrace();
        LOGGER.debug("Exception while trying to read message");
        removeClientOnConnectionLoss();
        break;
      }
    }
  }



  /**
   * Lets the client change their username, if the username is already taken, a similar
   * option is chosen.
   *
   * @param newName The desired new name to replace the old one with.
   */
  public void changeUsername(String newName) {
    String helper = this.getClientUserName();
    this.clientUserName = nameDuplicateChecker.checkName(newName);
    broadcastAnnouncement(helper + " has changed their nickname to " + clientUserName);
  }

  /**
   * Sets the client's username on login, if the username is already taken, a similar
   * option is chosen. Functionally, the only difference between this method and changeUsername
   * is that it doesn't print out the name change.
   *
   * @param name The desired name.
   */
  public void setUsernameOnLogin(String name) {
    this.clientUserName = nameDuplicateChecker.checkName(name);
    broadcastAnnouncement(  clientUserName + " has joined the Server");
  }

  /**
   * Broadcasts a chat Message to all active clients in the form "Username: @msg"
   *
   * @param msg the Message to be broadcast
   */
  public void broadcastChatMessage(String msg) {
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient(Protocol.printToClientConsole + "$" + clientUserName + ": " + msg);
    }
  }

  /**
   * Broadcasts a non-chat Message to all active clients. This can be used for server
   * messages / announcements rather than chat messages. The message will be printed to the user
   * exactly as it is given to this method. Unlike broadcastChatMessage, it will also be printed
   * onto the server console.
   *
   * @param msg the Message to be broadcast
   */
  public void broadcastAnnouncement(String msg) {
    System.out.println(msg);
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient(Protocol.printToClientConsole + "$" + msg);
    }
  }

  /** Sends a given message to client. The message has to already be protocol-formatted. ALL
   * communication with the client has to happen via this method!
   * @param msg the given message. Should already be protocol-formatted.
   */
  public void sendMsgToClient(String msg) {
    try {
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      //e.printStackTrace();
      LOGGER.debug("unable to send msg: " + msg);
      removeClientOnConnectionLoss();
    }
  }

  /**
   * Takes a msg of the form position$vote and extracts vote and position from it and saves it
   * in VoteHandler.getClientVoteData
    * @param msg the messaged to decode
   */
  public void decodeVote(String msg){
    int msgIndex = msg.indexOf('$');
    int vote = Integer.MAX_VALUE;
    int position = 0;
    LOGGER.debug("Message is " + msg);
    try {
      position = Integer.parseInt(msg.substring(0,msgIndex));
      vote = Integer.parseInt(msg.substring(msgIndex + 1));
      LOGGER.debug("Vote is:" + vote);
    } catch (Exception e) {
      LOGGER.warn("Invalid vote " + e.getMessage());
    }
    LOGGER.debug("Vote is:" + vote);
    if(vote != Integer.MAX_VALUE) { //gets MAX_VALUE when the vote wasn't valid
      VoteHandler.getClientVoteData().setVote(position,vote);
      LOGGER.debug("Player vote: " + vote);
      VoteHandler.getClientVoteData().setHasVoted(position,true);
    }
  }

  /**
   * Initializes a new Game instance and starts its run method in a new thread
   */
  public void startNewGame() {
    try {
      Game game = new Game(this,6,1, ClientHandler.getConnectedClients().size());
      Thread t = new Thread(game);
      t.start();
    } catch (TrainOverflow e) {
      LOGGER.warn(e.getMessage());
    }
  }

  /**
   * Removes & disconnects the client. To be used if a severe connection loss is detected (i.e. if trying to
   * send / receive a message throws an exception, not just if ping-pong detects a connection loss).
   * This is very similar to removeClientOnLogout(), however removeClientOnLogout() should only be used for
   * regular quitting, since removeClientOnLogout() cannot handle a client with connection loss.
   */
  public void removeClientOnConnectionLoss() {
    connectedClients.remove(this);
    disconnectClient();
    broadcastAnnouncement(getClientUserName() + " has left the server due to a connection loss.");
    disconnectedClients.add(this);
  }

  /**
   * Does exactly what it says on the tin, closes all connections of Client to Server and removes
   * the client. To be used if the client requests logout or in other "regular" situations. Use
   * removeClientOnConnectionLoss() if the client has to be removed due to a connection loss.
   */
  public void removeClientOnLogout() {
    broadcastAnnouncement(getClientUserName() + " has left the server.");
    sendMsgToClient(Protocol.serverConfirmQuit); //todo: protocol
    connectedClients.remove(this);
    disconnectClient();
  }

  /**
   * Closes the client's socket, in, and out.
   */
  public void disconnectClient() {
    socket = this.getSocket();
    in = this.getIn();
    out = this.getOut();
    try {
      Thread.sleep(100);
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }


}
