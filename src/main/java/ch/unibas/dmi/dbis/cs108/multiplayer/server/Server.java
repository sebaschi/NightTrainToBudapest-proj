package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;

import ch.unibas.dmi.dbis.cs108.highscore.OgGhostHighScore;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

  public static final Logger LOGGER = LogManager.getLogger(Server.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private static int gamePort;
  private HashSet<ClientHandler> connectedClients = new HashSet<>();
  private ServerSocket serverSocket;
  Scanner sc = new Scanner(System.in);

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  /**
   * Starts up a Server that opens Port 42069 either located wia IP address or localhost
   */
  public void startServer() {
    try {
      System.out.println("Port " + gamePort + " is open.");
      OgGhostHighScore.main(null);
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        ClientHandler nextClient = new ClientHandler(socket, socket.getInetAddress());
        Thread th = new Thread(nextClient);
        connectedClients.add(nextClient); // will leave be for now
        th.start();
      }
    } catch (IOException e) {
      e.printStackTrace();

    }
  }

  /**
   * closes the Server socket of this server
   */
  public void closeServerSocket() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The main method used for testing
   * @param args not used in this method
   */
  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    gamePort = 1873;
    try {
      serverSocket = new ServerSocket(gamePort);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Server server = new Server(serverSocket);
    server.startServer();
  }

  /**
   * The main method of the Server that is used for the jar build of this project
   * @param port the port the server will open on
   */
  public static void main(int port) {
    gamePort = port;
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(gamePort);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Server server = new Server(serverSocket);
    server.startServer();
  }

}
