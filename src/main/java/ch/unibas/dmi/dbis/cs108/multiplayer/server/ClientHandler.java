package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import java.io.*;
import java.net.Socket;
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
  private
  Scanner sc;
  public ServerPinger serverPinger;
  public static HashSet<ClientHandler> connectedClients = new HashSet<>();
  public static HashSet<ClientHandler> lobby = new HashSet<>();
  public static HashSet<ClientHandler> ghostClients = new HashSet<>();

  /**
   * Implements the login logic in client-server architecture.
   *
   * @param socket the socket on which to make the connection.
   */
  public ClientHandler(Socket socket) {
    try {
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
      this.clientUserName = "Mysterious Passenger";    //todo: duplicate handling for this
      /*
      // todo: duplicate handling more elegantly
      if (AllClientNames.allNames("").contains(clientUserName)) {
        clientUserName = NameGenerator.randomName(clientUserName);
      }
      // add username to list of all client names for future duplicate checking
      AllClientNames.allNames(clientUserName);

       */
      connectedClients.add(this);
      serverPinger = new ServerPinger(out, socket);
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

  public static HashSet<ClientHandler> getConnectedClients() {
    return connectedClients;
  }

  public static HashSet<ClientHandler> getLobby() {
    return lobby;
  }

  public static HashSet<ClientHandler> getGhostClients() {
    return ghostClients;
  }

  //Setters


  @Override
  /**
   * The main logic of the client handler.
   * Since every client is put on a string this is where
   * most interactions between client and server are held
   **/
  public void run() {
    String msg;
    while (socket.isConnected() && !socket.isClosed()) {
      try {
        msg = in.readLine();      //todo: here is where the server throws an exception when the client quits
        JServerProtocolParser.parse(msg, this);
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }

  public String getClientUserName() {
    return clientUserName;
  }

  /**
   * Lets the client change their username, if the username is already taken, a similar
   * option is chosen.
   *
   * @param newName The desired new name to replace the old one with.
   */
  public void changeUsername(String newName) {
    String helper = this.getClientUserName();
    this.clientUserName = nameDuplicateChecker.singularName(newName);
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
    this.clientUserName = nameDuplicateChecker.singularName(name);
    broadcastAnnouncement(  clientUserName + " has joined the Server");
  }

  /**
   * Broadcasts a Message to all active clients in the form "Username: @msg"
   *
   * @param msg the Message to be broadcast
   */
  public void broadcastChatMessage(String msg) {
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient("CHATM$" + clientUserName + ": " + msg);
    }
  }

  /**
   * Broadcasts a non-chat Message to all active clients. This can be used for server
   * messages / announcements rather than chat messages. The message will be printed to the user ex-
   * actly as it is given to this method. Unlike broadcastChatMessage, it will also be printed onto
   * the server console.
   *
   * @param msg the Message to be broadcast
   */
  public void broadcastAnnouncement(String msg) {
    System.out.println(msg);
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient("CHATM$" + msg);
    }
  }

  /** Sends a given message to client
   * todo: check for exception if out is closed.
   * @param msg the given message
   */
  public void sendMsgToClient(String msg) {
    try {
      //todo: socket closed handling
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Does what it sounds like
   */
  public void removeClientHandler() {
    broadcastChatMessage("SERVER: " + clientUserName + " has left the server");
    connectedClients.remove(this);
  }

  /**
   * Does exactly what it says on the tin, closes all connections of Client to Server.
   */
  public void disconnectClient() {
    sendMsgToClient("QUITC");
    removeClientHandler();
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
