package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Sends a ping to the server ("CPING") every 2 seconds and checks if it has gotten a pingback. The
 * actual logging of the pingback (via the gotPingBack boolean) has to be done elsewhere, depends on
 * how the client receives and parses messages.
 */
public class ClientPinger implements Runnable {

  private boolean gotPingBack;    //should be set to true when client gets a pingback.
  private boolean isConnected;    //set to true unless the ClientPinger detects a connection loss.
  BufferedWriter out;             //the output of this client through which the pings are sent
  private Socket socket;

  /**
   * @param socket the socket the Client is connected to which is used to end the thread if the
   *               connection is lost.
   *
   * @param out    the output through which the pings are sent.
   */
  public ClientPinger(BufferedWriter out, Socket socket) {
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
        out.write("CPING");
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
      isConnected = false;         //in case the socket accidentally disconnects (can this happen?)
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
