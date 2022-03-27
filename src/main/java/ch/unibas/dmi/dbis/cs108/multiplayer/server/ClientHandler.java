package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientHandler implements Runnable {

  private String clientUserName;
  private BufferedWriter out;
  private BufferedReader in;
  private Socket socket;
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
      this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
      this.clientUserName = in.readLine();
      // duplicate handling: if username already taken, assign random name to client
      if (AllClientNames.allNames("").contains(clientUserName)) {
        clientUserName = NameGenerator.randomName(clientUserName);
      }
      // add username to list of all client names for future duplicate checking
      AllClientNames.allNames(clientUserName);
      connectedClients.add(this);
      serverPinger = new ServerPinger(out, socket);
      Thread sP = new Thread(serverPinger);
      sP.start();
      broadcastMessage("SERVER: " + clientUserName + " has joined the Server");
    } catch (IOException e) {
      e.printStackTrace();
      closeEverything(socket, in, out);
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
    while (socket.isConnected()) {
      try {
        msg = in.readLine();
        JServerProtocolParser.parse(msg, this);
      } catch (IOException e) {
        e.printStackTrace();
        closeEverything(socket, in, out);
        break;
      }
    }
  }

  public String getClientUserName() {
    return clientUserName;
  }

  /**
   * Lets the client change their respective username, if the username is already taken, a similar option is chosen
   * @param newName The desired new name to replace the old one with.
   */
  public void changeUsername(String newName) {
    if (AllClientNames.allNames("").contains(newName)) {
      newName = NameGenerator.randomName(newName);
    }
    String h = this.clientUserName; //just a friendly little helper
    this.clientUserName = newName;
    AllClientNames.allNames(newName);
    broadcastMessage(h +" have changed their nickname to " + clientUserName);
  }

  /**
   * Broadcasts a Message to all active clients in the form "Username: msg"
   * @param msg the Message to be broadcasted
   */

  public void broadcastMessage(String msg) {
    for (ClientHandler client : connectedClients) {
      client.sendMsgToClient("CHATM:" + clientUserName + ": \"" + msg + "\"");
    }
  }

  //TODO: Documentation
  /**
   *
   * @param msg
   */

  public void sendMsgToClient(String msg) {
    try {
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void removeClientHandler() {
    connectedClients.remove(this);
    broadcastMessage("SERVER: " + clientUserName + " has left the server");
  }

  public void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
    removeClientHandler();
    try {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void decodeMsg(String msg) {

  }
}
