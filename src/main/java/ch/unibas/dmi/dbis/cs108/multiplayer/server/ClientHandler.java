package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
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
  private ClientMsgDecoder clientMsgDecoder = new ClientMsgDecoder();

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

  public ClientMsgDecoder getClientMsgDecoder() {
    return clientMsgDecoder;
  }

  //Setters


  @Override
  /**
   * The main logic of the client handler.
   * Since every client is put on a string this is where
   * most interactions between client and server are held
   */
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

  public void broadcastMessage(String msg) {
    for (ClientHandler client : connectedClients) {
      try {
        if (!client.clientUserName.equals((clientUserName))) {
          client.out.write(msg);
        } else {
          client.out.write("Message: **" + msg + "** sent!");
        }
        client.out.newLine();
        client.out.flush();
      } catch (IOException e) {
        e.printStackTrace();
        closeEverything(socket, in, out);
      }
    }
  }

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
