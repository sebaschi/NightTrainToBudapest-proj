package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.sebaschi.CentralServerData;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  private static CentralServerData allData = new CentralServerData();

  private static final int gamePort = 42069;
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
      System.out.println("Port 42069 is open.");
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        ClientHandler nextClient = new ClientHandler(socket, socket.getInetAddress(), allData);
        Thread th = new Thread(nextClient);
        connectedClients.add(nextClient); // will leave be for now
        allData.addClientToSetOfAllClients(nextClient);
        th.start();
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

}
