package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ClientPinger;

import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

  private Socket socket;
  private BufferedReader in;
  private BufferedWriter out;
  public String userName;
  public ClientPinger clientPinger;

  public Client(Socket socket, String userName) {
    try {
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));

      String randomUserName = NameGenerator.randomName();
      //TODO hide connecting logik(next 4 lines)
      this.userName = userName;
      this.out.write(getUsername());
      this.out.newLine();
      this.out.flush();
      clientPinger = new ClientPinger(this.out, this.socket);
    } catch (IOException e) {
      e.printStackTrace();
      closeEverything(socket, in, out);
    }
  }

  /**
   *
   */

  public void sendMessage() {
    try {
      Scanner sc = new Scanner(System.in);
      while (socket.isConnected()) {
        String msg = sc.nextLine();
        String formattedMSG = MessageFormatter.formatMsg(msg);
        out.write(formattedMSG);
        out.newLine();
        out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      closeEverything(socket, in, out);
    }
  }



  /**
   * Starts a thread which listens for incoming messages
   */
  public void chatListener() {
        /*TODO: what type of decoding has to be done
          TODO how shall input be logged?
         */
    new Thread(new Runnable() {
      @Override
      public void run() {

        String chatMsg;

        while (socket.isConnected()) {
          try {
            chatMsg = in.readLine();
            parse(chatMsg);
          } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
          }

        }
      }
    }).start();
  }

  /**
   * Sends a message to the server, as is.
   * @param msg the message sent. Should already be protocol-formatted.
   */
  public void sendMsgToServer(String msg) {
    try {
      out.write(msg);
      out.newLine();
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * parses a received message according to the client protocol.
   * @param msg the message to be parsed.
   */
  public void parse(String msg) {
    JClientProtocolParser.parse(msg, this);
  }

  public void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
    //TODO Correctly closing a clients connection
    //TODO the server should be notified in a way so he can handle it cleanly
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

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String hostname;
    int port = 42069;               //can be set via argument later if needed.
    if (args.length < 1) {
      System.out.println("Enter the host's IP address (or type localhost)");
      hostname = sc.next();
    } else {
      hostname = args[0];
    }
    System.out.println("Choose a nickname: ");
    String username = sc.next();
    Socket socket;
    try {
      socket = new Socket(hostname, 42069);
      Client client = new Client(socket, username);
      client.chatListener();
      Thread cP = new Thread(client.clientPinger);
      cP.start();
      client.sendMessage();     //this one blocks.
    } catch (UnknownHostException e) {
      System.out.println("Invalid host IP");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public String getUsername() {
    return userName;
  }
}
