package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import ch.unibas.dmi.dbis.cs108.sebaschi.CentralServerData;
import ch.unibas.dmi.dbis.cs108.sebaschi.Lobby;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler implements Runnable {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  CentralServerData serverData;   //todo: does this really need to be instantiated?

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
  public static HashSet<ClientHandler> ghostClients = new HashSet<>();

  /**
   * Implements the login logic in client-server architecture.
   *
   * @param ip     the ip of the client, used for re-connection.
   * @param socket the socket on which to make the connection.
   */
  public ClientHandler(Socket socket, InetAddress ip, CentralServerData serverData) {
    try {
      this.serverData = serverData;
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

  public static HashSet<ClientHandler> getConnectedClients() {
    return connectedClients;
  }

  public static HashSet<ClientHandler> getGhostClients() {
    return ghostClients;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  //Setters:
  public String getClientUserName() {
    return clientUserName;
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
    LOGGER.debug(this.getClientUserName() + " reached end of run()");
  }


  /**
   * Lets the client change their username, if the username is already taken, a similar option is
   * chosen.
   *
   * @param newName The desired new name to replace the old one with.
   */
  public void changeUsername(String newName) {
    String helper = this.getClientUserName();
    this.clientUserName = nameDuplicateChecker.checkName(newName);
    broadcastAnnouncementToAll(helper + " has changed their nickname to " + clientUserName);
  }

  /**
   * Sets the client's username on login, if the username is already taken, a similar option is
   * chosen. Functionally, the only difference between this method and changeUsername is that it
   * doesn't print out the name change.
   *
   * @param name The desired name.
   */
  public void setUsernameOnLogin(String name) {
    this.clientUserName = nameDuplicateChecker.checkName(name);
    broadcastAnnouncementToAll(clientUserName + " has joined the Server");

    /*    The following lines could be un-commented to provide Lobby information on login
    sendAnnouncementToClient("Welcome, " + clientUserName + "!");
    sendAnnouncementToClient("Here are the currently open Lobbies:");
    listLobbies();
     */
  }

  /**
   * Returns the Lobby this ClientHandler is in. If this ClientHandler is not in a Lobby,
   * it returns null.
   * @return
   */
  public Lobby getLobby() {
    try {
      Lobby l = Lobby.getLobbyFromID(Lobby.clientIsInLobby(this));
      return l;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Broadcasts a chat Message to all clients in the same lobby in the form "Username: @msg"
   * If this client isn't in a lobby, it instead defers the message to broadcastChatMessageToAll
   * @param msg the Message to be broadcast
   */
  public void broadcastChatMessageToLobby(String msg) {
    Lobby l = getLobby();
    if (l != null) {
      for (ClientHandler client : l.getLobbyClients()) {
        client.sendMsgToClient(Protocol.printToClientChat + "$" + clientUserName + ": " + msg);
      }
    } else {
      LOGGER.debug("Could not send chat message; probably client isn't in a lobby."
          + "Will broadcast across all lobbies now.");
      broadcastChatMessageToAll(msg);
    }
  }

  /**
   * Broadcasts a chat Message to all clients across all lobbies & clients who are not in a lobby
   * in the form "Username: @msg"
   * @param msg the Message to be broadcast
   */
  public void broadcastChatMessageToAll(String msg) {
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient(Protocol.printToClientChat + "$" + clientUserName + ": " + msg);
    }
  }

  /**
   * Broadcasts a non-chat Message to all active clients. This can be used for server messages /
   * announcements rather than chat messages. The message will be printed to the user exactly as it
   * is given to this method. Unlike eg. broadcastChatMessageToLobby, it will also be printed onto the server
   * console.
   * @param msg the Message to be broadcast. Does not have to be protocol-formatted, this method will take care of that.
   */
  public static void broadcastAnnouncementToAll(String msg) {
    System.out.println(msg);
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient(Protocol.printToClientConsole + "$" + msg);
    }
  }

  /**
   * Broadcasts a non-chat Message to all clients in the same lobby. This can be used for server messages /
   * announcements rather than chat messages. The message will be printed to the user exactly as it
   * is given to this method. The announcement will not be printed on the server console.
   *
   * @param msg the Message to be broadcast. Does not have to be protocol-formatted, this method will take care of that.
   */
  public void broadcastAnnouncementToLobby(String msg) {
    Lobby l = getLobby();
    if (l != null) {
      //System.out.println(msg);    we can-comment this if you want lobby-announcements to print on the server console as well.
      for (ClientHandler client : l.getLobbyClients()) {
        client.sendMsgToClient(Protocol.printToClientConsole + "$" + msg);
      }
    } else {
      LOGGER.debug("Could not send announcements; probably client isn't in a lobby."
          + "Will broadcast across all lobbies now.");
      broadcastAnnouncementToAll(msg);
    }
  }

  /**
   * Sends a given message to client. The message has to already be protocol-formatted. ALL
   * communication with the client has to happen via this method!
   *
   * @param msg the given message. Should already be protocol-formatted.
   */
  public void sendMsgToClient(String msg) {
    try {
      //if(!msg.equals("SPING"))LOGGER.debug("Message sent to client: " + msg);
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      //e.printStackTrace();
      LOGGER.debug("unable to send msg: " + msg);
      removeClientOnConnectionLoss();
    }
  }

  public void sendAnnouncementToClient(String msg) {
    sendMsgToClient(Protocol.printToClientConsole + "$" + msg);
  }

  /**
   * Removes & disconnects the client. To be used if a severe connection loss is detected (i.e. if
   * trying to send / receive a message throws an exception, not just if ping-pong detects a
   * connection loss). This is very similar to removeClientOnLogout(), however
   * removeClientOnLogout() should only be used for regular quitting, since removeClientOnLogout()
   * cannot handle a client with connection loss.
   */
  public void removeClientOnConnectionLoss() {
    connectedClients.remove(this);
    disconnectClient();
    serverData.removeClientFromSetOfAllClients(this);   //todo: delete?
    serverData.removeClientFromLobby(this);             //todo: do this via Lobby class directly.
    broadcastAnnouncementToAll(getClientUserName() + " has left the server due to a connection loss.");
    disconnectedClients.add(this);
  }

  /**
   * Does exactly what it says on the tin, closes all connections of Client to Server and removes
   * the client. To be used if the client requests logout or in other "regular" situations. Use
   * removeClientOnConnectionLoss() if the client has to be removed due to a connection loss.
   */
  public void removeClientOnLogout() {
    broadcastAnnouncementToAll(getClientUserName() + " has left the server.");
    sendMsgToClient(Protocol.serverConfirmQuit);
    connectedClients.remove(this);
    serverData.removeClientFromSetOfAllClients(this);    //todo: delete?
    serverData.removeClientFromLobby(this);              //todo: do this via Lobby class directly.
    disconnectClient();
  }

  /**
   * Invoked by Protocol.createNewLobby. Creates a new lobby with the ClientHandler as admin and
   * adds the lobby to the server data.
   */
  public void createNewLobby() {
    if (Lobby.clientIsInLobby(this) == -1) {
      Lobby newGame = new Lobby(this);
      serverData.addLobbyToListOfAllLobbies(newGame);
    } else {
      sendAnnouncementToClient("You are already in lobby nr. " + Lobby.clientIsInLobby(this));
    }
  }

  /**
   * The client wants to join the lobby with the index i.
   * //todo: needs more doc.
   * @param i
   */
  public void joinLobby(int i) {
    Lobby l = Lobby.getLobbyFromID(i);
    if (l != null) {
      l.addPlayer(this);
    } else {
      sendAnnouncementToClient("Invalid Lobby nr.");
      sendAnnouncementToClient("use LISTL to list lobbies");
    }

  }

  public void leaveLobby() {
    for (Lobby l : Lobby.lobbies) {
      boolean b = l.removePlayer(this);
      if (b) broadcastAnnouncementToAll(this.getClientUserName() + " has left lobby nr. " + l.getLobbyID());
    }
  }


  /**
   * Lists all lobbies and their members, along with players outside lobbies
   * to this clientHandler's client as an announcement.
   */
  public void listLobbies() {
    if (Lobby.lobbies.isEmpty()) {
      sendAnnouncementToClient("No open Lobbies.");
    } else {
      for (Lobby l : Lobby.lobbies) {
        sendAnnouncementToClient("Lobby nr. " + l.getLobbyID() + ":");
        for (ClientHandler c : l.getLobbyClients()) {
          if (c.equals(l.getAdmin())) {
            sendAnnouncementToClient("  -" + c.getClientUserName() + " (admin)");
          } else {
            sendAnnouncementToClient("  -" + c.getClientUserName());
          }
        }
      }
    }
    boolean helper = false;           //used to print "Clients not in lobbies" only once, if needed.
    for (ClientHandler c: connectedClients) {
      if (Lobby.clientIsInLobby(c) == -1) {
        if (!helper) {
          helper = true;
          sendAnnouncementToClient("Clients not in lobbies:");
        }
        sendAnnouncementToClient("  -" + c.getClientUserName());
      }
    }
    if (!helper) {
      sendAnnouncementToClient("No clients outside of lobbies");
    }
  }

  public void listPlayersInLobby() {
    Lobby l = getLobby();
    if (l != null) {
      sendAnnouncementToClient("Players in lobby nr. " + l.getLobbyID() + ":");
      for (ClientHandler c : l.getLobbyClients()) {
        if (c.equals(l.getAdmin())) {
          sendAnnouncementToClient("  -" + c.getClientUserName() + " (admin)");
        } else {
          sendAnnouncementToClient("  -" + c.getClientUserName());
        }
      }
    } else {
      sendAnnouncementToClient("You are not in a Lobby.");
    }
  }

  /**
   * Closes the client's socket, in, and out. and removes from global list of clients.
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
