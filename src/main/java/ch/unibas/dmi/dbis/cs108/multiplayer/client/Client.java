package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GUI;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GameStateModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.LoungeSceneViewController;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ClientPinger;


import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;

import com.google.inject.Guice;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
  private ClientModel clientModel;
  private GameStateModel gameStateModel;
  private GameController gameController;
  private LoungeSceneViewController loungeSceneViewController;

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
      clientPinger = new ClientPinger(this, this.socket);
      this.gameStateModel = new GameStateModel();
      this.chatApp = new ChatApp(new ClientModel(systemName, this));
      ChatApp.setGameController(new GameController(ChatApp.getClientModel(), gameStateModel));
      this.chatGui = new GUI(this.chatApp);
      this.gameController = new GameController(ChatApp.getClientModel(), gameStateModel);
      this.loungeSceneViewController = new LoungeSceneViewController();
      LoungeSceneViewController.setClient(ChatApp.getClientModel());
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
   * Extracts infromation about names and positions and roles from string and adds it to the
   * GameStateModel
   *
   * @param msg
   */
  public void gameStateModelSetter(String msg) {

  }

  public void setPosition(int position) {
    this.position = position;
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
   *
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
   *
   * @param address  the IP address of the Server (can be localhost)
   * @param port     the port for the connection
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
   * funnels a message to the gui, where depending on the parameter different
   * functions/controls/methods of the gui are targeted. The data contains more information the gui
   * needs
   *
   * @param parameter a string according to {@link GuiParameters} and {@link ClientGameInfoHandler}
   *                  can be empty
   * @param data      some information in a string, separators can be $ or :
   *                                   TODO(Seraina&Sebi): evtl. auslagern?
   */
  public void sendToGUI(String parameter, String data) {
    try {
      if(!parameter.equals(GuiParameters.updateGameState)) {
        LOGGER.debug("GUI: PARAMETER:" + parameter + ", DATA: " + data);
      }
      switch (parameter) {
        case GuiParameters.night: //ClientGameInfoHandler
          gameStateModel.setDayClone(false);
          chatApp.getGameController().setNoiseButtonInvisible();
          break;
        case GuiParameters.day: //ClientGameInfoHandler
          gameStateModel.setDayClone(true);
          chatApp.getGameController().setNoiseButtonVisible();
          break;
        case GuiParameters.updateGameState:
          gameStateModel.setGSFromString(data);
          chatApp.getGameController().updateRoomLabels();
          break;
        case GuiParameters.noiseHeardAtPosition:
          try {
            int position = Integer.parseInt(data);
            determineNoiseDisplay(position);
          } catch (Exception e) {
            LOGGER.warn("Not a position given for noise " +e.getMessage());
          }
          break;
        case GuiParameters.listOfLobbies:
          //updateListOfLobbies(data); (commented out due to compiling error)
          //TODO
          break;
        case GuiParameters.VoteIsOver:
          chatApp.getGameController().clearAllNoiseDisplay();
          break;
        case GuiParameters.listOfPLayers:
          updateListOfClients(data);
          //TODO
          break;
        case GuiParameters.getMembersInLobby:
          updateLobbyMembers(data);
          break;
        //case GuiParameters.viewChangeToGame: (commented out due to compiling error)
        //TODO
        //break; (commented out due to compiling error)
        //case GuiParameters.viewChangeToStart: (commented out due to compiling error)
        //TODO
        //break; (commented out due to compiling error)
        //case GuiParameters.viewChangeToLobby: (commented out due to compiling error)
        //TODO
        //break; (commented out due to compiling error)
        case GuiParameters.addNewMemberToLobby:
          addPlayerToLobby(data);
          break;
        case GuiParameters.newLobbyCreated:
          makeNewLobby(data);
          break;
        default:
          notificationTextDisplay(data);
          //TODO(Sebi,Seraina): should the gameController be in the Application just like the ChatController?
      }
    } catch (Exception e) {
      LOGGER.warn("Communication with GUI currently not possible: " + e.getMessage());

    }

  }

  private void makeNewLobby(String data) {
    String[] params = data.split(":");
    loungeSceneViewController.newLobby(params[0], params[1]);
  }

  private void addPlayerToLobby(String data) {
    String[] params = data.split(":");
    loungeSceneViewController.addPlayerToLobby(params[0], params[1]);
  }

  private void updateLobbyMembers(String data) {
    String[] dataArr = data.split(":");
    String lobbyID = dataArr[0];
    String adminName = dataArr[1];
  }

  private void updateListOfLobbies(String data) {
    String[] arr = data.split(":");
    ObservableList<SimpleStringProperty> list = new SimpleListProperty<>();
    int n = arr.length;
    for (int i = 0; i < n; i = i + 2) {
      list.add(new SimpleStringProperty(arr[i]));
      //ChatController.getClient().addLobbyToList(new SimpleStringProperty(arr[i]));
    }
    //TODO
  }

  private void updateListOfClients(String data) {
    String[] arr = data.split(":");
    ObservableList<SimpleStringProperty> list = new SimpleListProperty<>();
    int n = arr.length;
    for (int i = 0; i < n; i = i + 1) {
      list.add(new SimpleStringProperty(arr[i]));
    }
    loungeSceneViewController.updateClientListView(list);
  }

  /**
   * Starts a new thread, thad adds a message to notificationText in the gameController, waits 3
   * seconds and deletes it again.
   *
   * @param data the message to be added
   */
  public void notificationTextDisplay(String data) {
    new Thread(() -> {
      try {
        chatApp.getGameController().addMessageToNotificationText(data);
        Thread.sleep(5000);
        chatApp.getGameController().clearNotificationText();
      } catch (InterruptedException e) {
        LOGGER.warn(e.getMessage());
      }
    }).start();

  }

  public void determineNoiseDisplay(int position) {
    LOGGER.debug(position);
    switch (position) {
      case 0:
        chatApp.getGameController().noiseDisplay0();
        break;
      case 1:
        chatApp.getGameController().noiseDisplay1();
        break;
      case 2:
        chatApp.getGameController().noiseDisplay2();
        break;
      case 3:
        chatApp.getGameController().noiseDisplay3();
        break;
      case 4:
        chatApp.getGameController().noiseDisplay4();
        break;
      case 5:
        chatApp.getGameController().noiseDisplay5();
        break;
    }
  }


}
