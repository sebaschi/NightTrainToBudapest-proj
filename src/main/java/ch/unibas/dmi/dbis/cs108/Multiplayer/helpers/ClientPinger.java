package ch.unibas.dmi.dbis.cs108.Multiplayer.helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Sends a ping to the server ("CPING") every 2 seconds and checks if it has gotten a pingback.
 * The actual logging of the pingback (via the gotPingBack boolean) has to be done elsewhere,
 * depends on how the client receives and parses messages.
 */
public class ClientPinger implements Runnable{
    private boolean gotPingBack;    //should be set to true (via setGotPingBack) as soon as the client gets a pingback.
    private boolean isConnected;    //set to true unless the ClientPinger detects a connection loss.
    BufferedWriter out;             //the output of this client through which the pings are sent
    private Socket socket;

    /**
     * @param socket the socket the Client is connected to which is used to end the thread if the connection is lost.
     * @param out the output through which the pings are sent.
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
            while (socket.isConnected()) {
                out.write("CPING");
                Thread.sleep(2000);
                if (!gotPingBack) isConnected = false;
            }
            isConnected = false;                 //in case the socket accidentally disconnects (can this happen?)
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
