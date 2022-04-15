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

  public static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
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
   *
   * @param ip     the ip of the client, used for re-connection.
   * @param socket the socket on which to make the connection.
   */
  public ClientHandler(Socket socket, InetAddress ip) {
    try {
      this.ip = ip;
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
   *
   * @return the HashSet of Connected Clients
   */
  public static HashSet<ClientHandler> getConnectedClients() {
    return connectedClients;
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
    LOGGER.debug(this.getClientUserName() + " reached end of run()");
  }



  /**
   * Lets the client change their username, if the username is already taken, a similar option is
   * chosen.
   * @param newName The desired new name to replace the old one with.
   */
  public void changeUsername(String newName) {
    String helper = this.getClientUserName();
    this.clientUserName = nameDuplicateChecker.checkName(newName);
    broadcastAnnouncementToAll(helper + " has changed their nickname to " + clientUserName);
    try {
      getLobby().getGame().getGameState().changeUsername(helper,newName);
    } catch (NullPointerException e) {
    }
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
   */
  public Lobby getLobby() {
    try {
      return Lobby.getLobbyFromID(Lobby.clientIsInLobby(this));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Broadcasts a chat Message to all clients in the same lobby in the form "Username: @msg"
   * If this client isn't in a lobby, it instead sends the message to everyone not in a lobby
   * @param msg the Message to be broadcast
   */
  public void broadcastChatMessageToLobby(String msg) {
    Lobby l = getLobby();
    if (l != null) {
      for (ClientHandler client : l.getLobbyClients()) {
        client.sendMsgToClient(Protocol.printToClientChat + "$" + clientUserName + ": " + msg);
      }
    } else {
      //send msg to all clients who are not in a lobby.
      for (ClientHandler client: connectedClients) {
        if (Lobby.clientIsInLobby(client) == -1) {
          client.sendMsgToClient(Protocol.printToClientChat + "$" + clientUserName + ": " + msg);
        }
      }
    }
  }

  /**
   * Broadcasts a pseudo chat Message from a NPC to all active clients in the corresponding lobby
   *
   * @param msg the Message to be broadcast
   */
  public void broadcastNpcChatMessageToLobby(String msg) {
    Lobby l = getLobby();
    if (l != null) {
      for (ClientHandler client : l.getLobbyClients()) {
        client.sendMsgToClient(Protocol.printToClientChat + "$" + msg);
      }
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
   * If this clienthandler is not in a lobby, it will instead broadcast to all clients.
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
   * Sends a message only to the specified user, as well as sending a confirmation to the user who sent the message
   * that it has been sent. Syntax:
   * @param target MUST NOT BE NULL!
   */
  public void whisper(String msg, ClientHandler target) {
    target.sendMsgToClient(Protocol.printToClientChat + "$" + this.getClientUserName() + " whispers: " + msg);
    sendMsgToClient(Protocol.printToClientChat + "$You whispered to " + target.getClientUserName() + ": " + msg);
  }

  /**
   * Sends a given message to this client. The message has to already be protocol-formatted. ALL
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
      LOGGER.warn("unable to send msg: " + msg);
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
      VoteHandler.getClientVoteData().setHasVoted(position,true); //TODO: move clientVoteData to gamestate
    }
  }

  /**
   * Initializes a new Game instance and starts its run method in a new thread.
   * Puts the game in the corresponding lobby. Only the admin of this lobby can start a new
   * game.
   */
  public void startNewGame() {
    try {
      Lobby l = getLobby();
      if (l.getLobbyIsOpen()) {
        if (l.getAdmin().equals(this)) {
          Game game = new Game(6, 1, l.getLobbyClients().size(), l);
          l.setGame(game);
          Thread t = new Thread(game);
          t.start();
          l.addGameToRunningGames(game);
          l.setLobbyIsOpen(false);
        } else {
          sendAnnouncementToClient("Only the admin can start the game");
        }
      } else {
        sendAnnouncementToClient("The game has already started");
      }
    } catch (TrainOverflow e) {
      LOGGER.warn(e.getMessage());
    } catch (NullPointerException e) {
      LOGGER.warn("Client is not in a lobby");
    }
  }

  /**
   * Sends an announcement to just this client. Essentially the same as broadcastAnnouncementToAll except
   * it only sends an announcement to just this client instead of everyone.
   * Can be used for private non-chat messages (e.g. "You are now a ghost").
   */
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
    leaveLobby();
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
    leaveLobby();
    disconnectClient();
  }

  /**
   * Invoked by Protocol.createNewLobby. Creates a new lobby with the ClientHandler as admin and
   * adds the lobby to the server data.
   */
  public void createNewLobby() {
    if (Lobby.clientIsInLobby(this) == -1) {
      Lobby newGame = new Lobby(this);
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
      if (l.getLobbyIsOpen()) {
        l.addPlayer(this);
      } else {
        sendAnnouncementToClient("The game in Lobby " + l.getLobbyID() + " has already started");
      }
    } else {
      sendAnnouncementToClient("Invalid Lobby nr.");
      sendAnnouncementToClient("use LISTL to list lobbies");
    }

  }

  /**
   * If the client is in a Lobby, they leave it. Otherwise, this method does nothing.
   */
  public void leaveLobby() {
    Lobby l = Lobby.getLobbyFromID(Lobby.clientIsInLobby(this));
    if (l != null) {
      l.removePlayer(this);
      Game game = l.getGame();
      if(game != null) {
        l.getGame().getGameState().handleClientDisconnect(this);
      }
    }
  }


  /**
   * Lists all lobbies and their members, along with players outside lobbies
   * to this clientHandler's client as an announcement.
   */
  public void listLobbies() {
    if (Lobby.lobbies.isEmpty()) {
      sendAnnouncementToClient("No Lobbies.");
    } else {
      for (Lobby l : Lobby.lobbies) {
        String lobbyStatus = "closed";
        if(l.getLobbyIsOpen()) {
          lobbyStatus = "open";
        }
        sendAnnouncementToClient("Lobby nr. " + l.getLobbyID() + ": (" + lobbyStatus + ")");
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

  /**
   * Lists all players in the client's lobby. If the client is not in a Lobby, it will say
   * "You are not in a lobby."
   */
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
      sendAnnouncementToClient("You are not in a lobby.");
    }
  }

  /**
   * Lists all Games currenty running and already finished and displays it to the client handled by this
   */
  public void listGames() {
    if (Lobby.runningGames.isEmpty() && Lobby.finishedGames.isEmpty()) {
      sendAnnouncementToClient("No Games");
    } else {
      sendAnnouncementToClient("Running Games:");
      try {
        for (Game runningGame : Lobby.runningGames) {
          sendAnnouncementToClient("  - " + runningGame.getName() + ", Lobby" + runningGame.getLobby().getLobbyID());
        }
      } catch (Exception e) {
        sendAnnouncementToClient("  - No running Games");
      }
      sendAnnouncementToClient("Finished Games");
      try {
        for (Game finishedGame : Lobby.finishedGames) {
          sendAnnouncementToClient("  - " + finishedGame.getName() + ", Lobby" + finishedGame.getLobby().getLobbyID());
        }
      } catch (Exception e) {
        sendAnnouncementToClient("  - No finished Games");
      }
    }
  }

  /**
   * Closes the client's socket, in, and out. and removes from global list of clients.
   */
  public void disconnectClient() {
    Lobby l = getLobby();
    if (l != null) {
      Game g = l.getGame();
      if(g != null)
      l.getGame().getGameState().handleClientDisconnect(this);
    }
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
