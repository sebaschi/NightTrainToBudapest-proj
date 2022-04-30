package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GUI;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GameStateModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ClientPinger;


import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Client {

  public static final Logger LOGGER = LogManager.getLogger(Client.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private Socket socket;
  private BufferedReader in;
  private BufferedWriter out;
  public ClientPinger clientPinger;

  private ChatApp chatApp;
  private GUI chatGui;
  private GameStateModel gameStateModel;
  private GameController gameController;

  /**
   * Saves the position of the client, gets refreshed everytime the client gets a vote request.
   */
  int position = Integer.MAX_VALUE;


  public Client(Socket socket, String username) {
    try {
      this.socket = socket;
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));

      //sending the initial name to server.
      String systemName;
      if (username == null) {
        try {
          systemName = System.getProperty("user.name");
        } catch (Exception e) {
          systemName = "U.N. Owen";
        }
        if (systemName == null) {
          systemName = "U.N. Owen";
        }
      } else {
        systemName = username;
      }
      sendMsgToServer(Protocol.clientLogin + "$" + systemName);
      this.chatApp = new ChatApp(new ClientModel(systemName, this));
      this.chatGui = new GUI(this.chatApp);
      clientPinger = new ClientPinger(this, this.socket);
      this.gameStateModel = new GameStateModel();
      this.gameController = new GameController(ChatApp.getClientModel(),gameStateModel);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void changeUsername(String newName) {
    ChatController.getClient().setUsername(newName);
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
              String formattedMSG = MessageFormatter.formatMsg(msg, position);
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
   *
   * @param msg the message containing the position
   */
  public void positionSetter(String msg) {

    LOGGER.info("Im in thread:" + Thread.currentThread());
    int msgIndex = msg.indexOf('$');
    String pos = msg.substring(0, msgIndex);
    try {
      position = Integer.parseInt(pos);
    } catch (NumberFormatException e) {
      LOGGER.warn("Position got scrabbled on the way here");
    }
    String justMsg = msg.substring(msgIndex + 1);

    System.out.println(justMsg);
    System.out.println("Please enter your vote");

    //LOGGER.debug("just checked next line");
  }

  /**
   * Extracts infromation about names and positions and roles from string and adds it to
   * the GameStateModel
   * @param msg
   */
  public void gameStateModelSetter(String msg) {


  }


  /**
   * Starts a thread which listens for incoming chat messages / other messages that the user has to
   * see
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
              //LOGGER.debug("chatMSG recieved from Server: " + chatMsg);
              parse(chatMsg);
            } else {
              System.out.println("chatMsg is null");
              throw new IOException();
            }
          } catch (IOException e) {
            //e.printStackTrace();
            LOGGER.warn("Exception while trying to read message: " + e.getMessage());
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
   *
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

  /**
   * The main Method used for testing in IDE
   * @param args not used in this main
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String hostname;
    int port = 1873;
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
      socket = new Socket(hostname, 1873);
      Client client = new Client(socket, null);
      client.chatListener();
      Thread cP = new Thread(client.clientPinger);
      cP.start();
      client.userInputListener();     //this one blocks.
      //Start the GUI
      GUI gui = new GUI(client.chatApp);
      Thread guiThread = new Thread(gui);
      guiThread.start();
      LOGGER.info("7");
    } catch (UnknownHostException e) {
      System.out.println("Invalid host IP");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * The main-method used for the jar build of this project
   * @param address the IP address of the Server (can be localhost)
   * @param port the port for the connection
   * @param username the username of this client
   */
  public static void main(InetAddress address, int port, String username) {
    Scanner sc = new Scanner(System.in);
    Socket socket;
    try {
      socket = new Socket(address, port);
      Client client = new Client(socket, username);
      client.chatListener();
      Thread cP = new Thread(client.clientPinger);
      cP.start();
      client.userInputListener();     //this one blocks.
      LOGGER.info("7.1");
      Thread guiThread = new Thread(client.chatGui);
      LOGGER.info("8");
      guiThread.start();
      LOGGER.info("9");
    } catch (UnknownHostException e) {
      System.out.println("Invalid host IP");
    } catch (IOException e) {
      e.printStackTrace();
    }
    LOGGER.info("10");
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

  public int getPosition() {
    return position;
  }

  public void sendToChat(String substring) {
    chatApp.getChatController().addChatMsgToView(substring);
  }

  /**
   * funnels a message to the gui, where depending on the parameter different functions/controls/methods
   * of the gui are targeted. The data contains more information the gui needs
   * @param parameter a string according to {@link GuiParameters} and {@link ClientGameInfoHandler} can be empty
   * @param data some information in a string, separators can be $ or :
   *TODO(Seraina&Sebi): evtl. auslagern?
   */
  public void sendToGUI(String parameter, String data) {
    try {
      switch (parameter) {
        case ClientGameInfoHandler.itsNightTime: //ClientGameInfoHandler
          gameStateModel.setDayClone(false);
          break;
        case ClientGameInfoHandler.itsDayTime: //ClientGameInfoHandler
          gameStateModel.setDayClone(true);
          break;
        case GuiParameters.updateGameState:
          gameStateModel.setGSFromString(data);
          gameController.updateRoomLabels();
          break;
        case GuiParameters.noiseHeardAtPosition:
          try {
            int position = Integer.parseInt(data);
            determineNoiseDisplay(position);
          } catch (Exception e) {
            LOGGER.warn("Not a position given for noise");
          }
          break;
        case GuiParameters.listOfLobbies:
          //TODO
          break;
        case GuiParameters.listOfPLayers:
          //TODO
          break;
        case GuiParameters.viewChangeToGame:
          //TODO
          break;
        case GuiParameters.viewChangeToStart:
          //TODO
          break;
        case GuiParameters.viewChangeToLobby:
          //TODO
          break;
        default:
          notificationTextDisplay(data);
        //TODO(Sebi,Seraina): should the gameController be in the Application just like the ChatController?
      }
    } catch (Exception e) {
      LOGGER.warn("Communication with GUI currently not possible: " + e.getMessage());

    }

  }

  /**
   * Starts a new thread, thad adds a message to notificationText in the gameController,
   * waits 3 seconds and deletes it again.
   * @param data the message to be added
   */
  public void notificationTextDisplay(String data) {
    new Thread(() -> {
      gameController.addMessageToNotificationText(data);
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      gameController.clearNotificationText();
    }).start();

  }

  public void determineNoiseDisplay(int position) {
    switch (position) {
      case 0:
        gameController.noiseDisplay0();
      case 1:
        gameController.noiseDisplay1();
      case 2:
        gameController.noiseDisplay2();
      case 3:
        gameController.noiseDisplay3();
      case 4:
        gameController.noiseDisplay4();
      case 5:
        gameController.noiseDisplay5();
    }
  }


}
