package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sends a ping to the client ("SPING") every 2 seconds and checks if it has gotten a pingback. The
 * actual logging of the pingback (via the gotPingBack boolean) has to be done elsewhere, depends on
 * how the server receives and parses messages.
 */
public class ServerPinger implements Runnable {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private boolean gotPingBack;    //should be set to true (via setGotPingBack) as soon as the server gets a pingback.
  private boolean isConnected;    //set to true unless the ServerPinger detects a connection loss.
  BufferedWriter out;             //the output of this client through which the pings are sent
  ClientHandler c;
  private Socket socket;

  /**
   * @param socket the socket the ClientHandler is connected to; used to end the thread if the
   *               connection is closed.
   */
  public ServerPinger(Socket socket, ClientHandler c) {
    gotPingBack = false;
    isConnected = true;
    this.socket = socket;
    this.c = c;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(2000);
      while (socket.isConnected() && !socket.isClosed()) {
        gotPingBack = false;
        c.sendMsgToClient(Protocol.pingFromServer);
        Thread.sleep(4000);
        if (gotPingBack) {
          if (!isConnected) {         //if !isConnected, then the connection had been lost before.
            isConnected = true;
            System.out.println("Connection to user " + c.getClientUserName() + " regained!");
          }
        } else {
          if (isConnected) {
            isConnected = false;
            System.out.println("Lost connection to user " + c.getClientUserName() + ". Waiting to reconnect...");
          }
        }
      }
      isConnected = false;        //in case the socket accidentally disconnects (can this happen?)
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void setGotPingBack(boolean gotPingBack) {
    this.gotPingBack = gotPingBack;
  }

  public boolean isConnected() {
    return isConnected;
  }


}
