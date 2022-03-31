package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
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
  private Socket socket;

  /**
   * @param socket the socket the ClientHandler is connected to; used to end the thread if the
   *               connection is lost.
   *
   * @param out    the output through which the pings are sent.
   */
  public ServerPinger(BufferedWriter out, Socket socket) {
    gotPingBack = false;
    isConnected = true;
    this.out = out;
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(2000);
      while (socket.isConnected()) {
        gotPingBack = false;
        out.write("SPING");
        out.newLine();
        out.flush();
        Thread.sleep(4000);
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
      isConnected = false;        //in case the socket accidentally disconnects (can this happen?)
    } catch (InterruptedException | IOException e) {
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
