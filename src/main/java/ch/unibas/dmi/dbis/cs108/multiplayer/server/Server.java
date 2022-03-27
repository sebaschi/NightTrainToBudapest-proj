package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class Server {

  private static final int gamePort = 42069;
  private HashSet<ClientHandler> connectedClients = new HashSet<>();
  private ServerSocket serverSocket;
  Scanner sc = new Scanner(System.in);

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public void startServer() {
    try {
      System.out.println("Port 42069 is open on " + this.serverSocket.getInetAddress());
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        ClientHandler nextClient = new ClientHandler(socket);
        Thread th = new Thread(nextClient);
        connectedClients.add(nextClient);
        th.start();
        // close socket + remove client if client is disconnected
        if (socket.getInputStream().read() == -1) {
          System.out.println("client disconnected. closing socket");
          socket.close();
          connectedClients.remove(nextClient);
        }

        // close socket + remove client if client is disconnected
        if (socket.getInputStream().read() == -1) {
          System.out.println("client disconnected. closing socket");
          socket.close();
          connectedClients.remove(nextClient);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();

    }
  }

  public void closeServerSocket() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(gamePort);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Server server = new Server(serverSocket);
    server.startServer();
  }

  public static void broadcast(String msg) {
    //TODO
  }
}
