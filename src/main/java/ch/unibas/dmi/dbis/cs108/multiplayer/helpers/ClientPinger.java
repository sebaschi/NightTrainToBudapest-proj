package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sends a ping to the server ("CPING") every 2 seconds and checks if it has gotten a pingback. The
 * actual logging of the pingback (via the gotPingBack boolean) has to be done elsewhere, depends on
 * how the client receives and parses messages.
 */
public class ClientPinger implements Runnable {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private boolean gotPingBack;    //should be set to true when client gets a pingback.
  private boolean isConnected;    //set to true unless the ClientPinger detects a connection loss.
  private final Client client;
  private final Socket socket;

  /**
   * @param socket the socket the Client is connected to which is used to end the thread if the
   *               connection is lost.
   */
  public ClientPinger(Client client, Socket socket) {
    gotPingBack = false;
    isConnected = true;
    this.client = client;
    this.socket = socket;
  }

  @Override
  public void run() {
    Thread.currentThread().setPriority(10);
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    while (socket.isConnected() && !socket.isClosed()) {
      gotPingBack = false;
      client.sendMsgToServer(Protocol.pingFromClient);
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      if (gotPingBack) {
        if (!isConnected) {         //if !isConnected, then the connection had been lost before.
          isConnected = true;
          System.out.println("Connection regained!");
        }
      } else {
        if (isConnected) {
          isConnected = false;
          System.out.println("Lost connection. Waiting to reconnect...");
        }
      }
    }
    isConnected = false;
  }

  public void setGotPingBack(boolean gotPingBack) {
    this.gotPingBack = gotPingBack;
  }

  public boolean isConnected() {
    return isConnected;
  }


}
