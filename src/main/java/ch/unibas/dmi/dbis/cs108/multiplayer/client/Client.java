package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ClientPinger;


import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Client {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private Socket socket;
  private BufferedReader in;
  private BufferedWriter out;
  public ClientPinger clientPinger;

  public Client(Socket socket) {
    try {
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));

      //sending the initial name to server.
      String systemName;
      try {
        systemName = System.getProperty("user.name");
      } catch (Exception e) {
        systemName = "U.N. Owen";
      }
      if (systemName == null) systemName = "U.N. Owen";
      sendMsgToServer(Protocol.clientLogin + "$" + systemName);

      clientPinger = new ClientPinger(this, this.socket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a message to the Server in a formatted way COMND$msg
   */
  public void userInputListener() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        //Scanner sc = new Scanner(System.in);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        while (socket.isConnected() && !socket.isClosed()) {
          try {
            if (bfr.ready()) {
              String msg = bfr.readLine();
              String formattedMSG = MessageFormatter.formatMsg(msg);
              sendMsgToServer(formattedMSG);
            }
            Thread.sleep(5);
          } catch (IOException | InterruptedException e) {
            e.printStackTrace();
          }
          //LOGGER.debug("just checked next line");
        }
        //LOGGER.debug("userInputListener is done");
      }
    }).start();
  }

  /**
   * Tells user to enter a position to vote for passenger at that position
   */

  public void voteGetter() {
    //TODO(Seraina): implement

  }


  /**
   * Starts a thread which listens for incoming chat messages / other messages that the user
   * has to see
   */
  public void chatListener() {
    new Thread(new Runnable() {
      @Override
      public void run() {

        String chatMsg;

        while (socket.isConnected() && !socket.isClosed()) {
          try {
            chatMsg = in.readLine();     //todo: maybe if
            if (chatMsg != null) {
              parse(chatMsg);
            } else { System.out.println("chatMsg is null"); throw new IOException();}
          } catch (IOException e) {
            //e.printStackTrace();
            LOGGER.debug("Exception while trying to read message: " + e.getMessage());
            disconnectFromServer();
          }

        }
        //LOGGER.debug("chatListener is done");
      }
    }).start();
  }

  /**
   * Sends a message to the server, as is. The message has to already be protocol-formatted. ALL
   * communication with the server has to happen via this method!
   *
   * @param msg the message sent. Should already be protocol-formatted.
   */
  public void sendMsgToServer(String msg) {
    try {
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      //e.printStackTrace();
      LOGGER.debug("unable to send msg: " + msg);
      disconnectFromServer();
    }

  }

  /**
   * parses a received message according to the client protocol.
   * @param msg the message to be parsed.
   */
  public void parse(String msg) {
    JClientProtocolParser.parse(msg, this);
  }

  public void disconnectFromServer() {
    try {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      if (socket != null) {
        socket.close();
        //LOGGER.debug("closed the socket!");
      }
      System.out.println("Disconnected from server.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String hostname;
    int port = 42069;               //can be set via argument later if needed.
    if (args.length < 1) {
      System.out.println("Enter the host's IP address (or type l for localhost)");
      hostname = sc.next();
      if (Objects.equals(hostname, "l")) {
        hostname = "localhost";
      }
    } else {
      hostname = args[0];
    }
    Socket socket;
    try {
      socket = new Socket(hostname, 42069);
      Client client = new Client(socket);
      client.chatListener();
      Thread cP = new Thread(client.clientPinger);
      cP.start();
      client.userInputListener();     //this one blocks.
    } catch (UnknownHostException e) {
      System.out.println("Invalid host IP");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public Socket getSocket() {
    return socket;
  }

  public BufferedReader getIn() {
    return in;
  }

  public BufferedWriter getOut() {
    return out;
  }
}
