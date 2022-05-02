package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.LobbyListView;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.JServerProtocolParser;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoungeSceneViewController implements Initializable {

  public static final Logger LOGGER = LogManager.getLogger(LoungeSceneViewController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  @FXML
  private TextFlow highScore;
  @FXML
  public TextFlow lobbyPrint;
  @FXML
  private SplitPane chatSplitPane;
  @FXML
  private AnchorPane chatAnchorPane;
  @FXML
  private AnchorPane otherNotificationAnchorPane;
  @FXML
  public Button highScoreButton;
  @FXML
  private Button leaveLobbyButton;
  @FXML
  private Button lobbyPrintButton;
  @FXML
  private Button startGame;
  @FXML
  private Button newGameButton;
  @FXML
  private AnchorPane gameAnchorPane;
  @FXML
  public ListView<LobbyListItem> LobbyListView;
  @FXML
  public ListView<ClientListItem> ClientListView;
  @FXML
  private Button ChangeNameButton;
  @FXML
  private Button LeaveServerButton;
  @FXML
  private AnchorPane ChatArea;
  @FXML
  private BorderPane LoungeSceneBorderPane;
  @FXML
  private ToolBar NTtBToolBar;

  public static ListView<LobbyListItem> lListView;
  public static ListView<ClientListItem> cListView;

  public static ClientModel client;
  private static ChatApp chatApp;
  private ChatApp cApp;

  ObservableList<ClientListItem> clients = FXCollections.observableArrayList();
  ObservableList<LobbyListItem> lobbies = FXCollections.observableArrayList();

  private ObservableMap<String, ObservableList<String>> lobbyToMemberssMap;
  private HashMap<String, String> clientToLobbyMap;
  private HashMap<String, LobbyListItem> lobbyIDtoLobbyMop;

  public LoungeSceneViewController() {
    super();
    lobbyToMemberssMap = FXCollections.observableHashMap();
    lobbyIDtoLobbyMop = new HashMap<>();
  }

  public void setChatApp(ChatApp chatApp) {
    LoungeSceneViewController.chatApp = chatApp;
  }

  public void setcApp(ChatApp cApp) {
    this.cApp = cApp;
  }

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or {@code
   *                  null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ChatApp.setLoungeSceneViewController(this);
    setcApp(chatApp);
    ChangeNameButton.setOnAction(event -> changeName());
    LeaveServerButton.setOnAction(event -> leaveServer());
    newGameButton.setOnAction(event -> newGame());
    LobbyListView.setVisible(true);
    lListView = LobbyListView;
    cListView = ClientListView;
    LOGGER.debug("Lobby in initialize" + LobbyListView);
    ClientListView.setVisible(true);
    ClientListView.setItems(clients);
    addChatView();

    ClientListView.setItems(clients);
    ClientListView.setCellFactory(param -> {
      ListCell<ClientListItem> cell = new ListCell<>() {
        Label name = new Label();
        Label id = new Label();
        HBox nameAndId = new HBox(name, id);

        {
          nameAndId.setAlignment(Pos.CENTER_LEFT);
        }

        /**
         * The updateItem method should not be called by developers, but it is the
         * best method for developers to override to allow for them to customise the
         * visuals of the cell. To clarify, developers should never call this method
         * in their code (they should leave it up to the UI control, such as the
         * {@link ListView} control) to call this method. However, the purpose of
         * having the updateItem method is so that developers, when specifying
         * custom cell factories (again, like the ListView {@link
         * ListView#cellFactoryProperty() cell factory}), the updateItem method can
         * be overridden to allow for complete customisation of the cell.
         *
         * <p>It is <strong>very important</strong> that subclasses
         * of Cell override the updateItem method properly, as failure to do so will
         * lead to issues such as blank cells or cells with unexpected content
         * appearing within them. Here is an example of how to properly override the
         * updateItem method:
         *
         * <pre>
         * protected void updateItem(T item, boolean empty) {
         *     super.updateItem(item, empty);
         *
         *     if (empty || item == null) {
         *         setText(null);
         *         setGraphic(null);
         *     } else {
         *         setText(item.toString());
         *     }
         * }
         * </pre>
         *
         * <p>Note in this code sample two important points:
         * <ol>
         *     <li>We call the super.updateItem(T, boolean) method. If this is not
         *     done, the item and empty properties are not correctly set, and you are
         *     likely to end up with graphical issues.</li>
         *     <li>We test for the <code>empty</code> condition, and if true, we
         *     set the text and graphic properties to null. If we do not do this,
         *     it is almost guaranteed that end users will see graphical artifacts
         *     in cells unexpectedly.</li>
         * </ol>
         *  @param item The new item for the cell.
         *
         * @param empty whether or not this cell represents data from the list. If
         *              it is empty, then it does not represent any domain data, but
         *              is a cell
         */
        @Override
        protected void updateItem(ClientListItem item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
            setText(null);
            setGraphic(null);
          } else {
            LOGGER.debug("In updateItem(item, empty) Method. Else branch -> nonnull item");
            name.setText(item.getName());
            name.setTextFill(Color.BLACK);
            id.setText(String.valueOf(item.getId()));
            id.setTextFill(Color.BLACK);
            setGraphic(nameAndId);
          }
        }
      };
      return cell;
    });

    LobbyListView.setItems(lobbies);
    LOGGER.debug("In Initialize 2 LobbyListView" + LobbyListView);
    LobbyListView.setCellFactory(param -> {
      ListCell<LobbyListItem> cell = new ListCell<>() {
        Label lobbyID = new Label();
        Label adminName = new Label();
        Label lobbyIsOpen = new Label();
        Label noOfPlayersInLobby = new Label();
        Button startOrJoin = new Button();
        HBox head = new HBox(lobbyID, adminName, noOfPlayersInLobby, lobbyIsOpen, startOrJoin);
        VBox playerList = new VBox();
        TitledPane headParent = new TitledPane(head.toString(), playerList);

        {
          head.setAlignment(Pos.CENTER_LEFT);
          head.setSpacing(5);
          playerList.setAlignment(Pos.CENTER_LEFT);
          headParent.setCollapsible(true);
        }

        /**
         * The updateItem method should not be called by developers, but it is the
         * best method for developers to override to allow for them to customise the
         * visuals of the cell. To clarify, developers should never call this method
         * in their code (they should leave it up to the UI control, such as the
         * {@link ListView} control) to call this method. However, the purpose of
         * having the updateItem method is so that developers, when specifying
         * custom cell factories (again, like the ListView {@link
         * ListView#cellFactoryProperty() cell factory}), the updateItem method can
         * be overridden to allow for complete customisation of the cell.
         *
         * <p>It is <strong>very important</strong> that subclasses
         * of Cell override the updateItem method properly, as failure to do so will
         * lead to issues such as blank cells or cells with unexpected content
         * appearing within them. Here is an example of how to properly override the
         * updateItem method:
         *
         * <pre>
         * protected void updateItem(T item, boolean empty) {
         *     super.updateItem(item, empty);
         *
         *     if (empty || item == null) {
         *         setText(null);
         *         setGraphic(null);
         *     } else {
         *         setText(item.toString());
         *     }
         * }
         * </pre>
         *
         * <p>Note in this code sample two important points:
         * <ol>
         *     <li>We call the super.updateItem(T, boolean) method. If this is not
         *     done, the item and empty properties are not correctly set, and you are
         *     likely to end up with graphical issues.</li>
         *     <li>We test for the <code>empty</code> condition, and if true, we
         *     set the text and graphic properties to null. If we do not do this,
         *     it is almost guaranteed that end users will see graphical artifacts
         *     in cells unexpectedly.</li>
         * </ol>
         *  @param item The new item for the cell.
         *
         * @param empty whether or not this cell represents data from the list. If
         *              it is empty, then it does not represent any domain data, but
         *              is a cell
         */
        @Override
        protected void updateItem(LobbyListItem item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
            setText(null);
            setGraphic((null));
          } else {
            LOGGER.debug("In ELSE part of LobbyView Update item()");
            lobbyID.setText(item.getLobbyID());
            adminName.setText(item.getAdminName());
            startOrJoin.setOnAction(event -> {
              if (item.isOwnedByClient()) {
                startGame();
              } else {
                joinGame(item.lobbyIDProperty().getValue());
              }
            });
            startOrJoin.setText(item.isOwnedByClient() ? "Start" : "Join");
            lobbyID.setTextFill(Color.BLACK);
            adminName.setTextFill(Color.BLACK);
            startOrJoin.setTextFill(Color.BLACK);
            setGraphic(head);
          }
        }
      };
      return cell;
    });
    LOGGER.debug("In Initialize 3 LobbyListView" + LobbyListView);
    LobbyListView.setPlaceholder(new Text("No open lobbies!"));
    LobbyListView.setVisible(true);
  }

  /**
   * Adds the gameView to the existing LobbyView
   */
  public void addGameView() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          newGameButton.setVisible(false);
          startGame.setVisible(false);
          gameAnchorPane.getChildren().add(chatApp.game);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized");
        }
      }
    });
  }

  /**
   * Removes the GameView again - needed when a game is over or a lobby is left
   */
  public void removeGameView() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          newGameButton.setVisible(true);
          startGame.setVisible(true);
          gameAnchorPane.getChildren().clear();
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized");
        }
      }
    });
  }

  /**
   * Adds the ChatView to the LobbyView, should be done right in the initialisation
   */
  public void addChatView() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          chatAnchorPane.getChildren().add(chatApp.chat);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized: chatAnchorPane");
        }
      }
    });
  }

  /**
   * Adds players to a lobby "NMEMB" {@link ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   *
   * @param lobbyID the Id the Player belongs to
   * @param player  the player to be added
   */
  public void addPlayerToLobby(String lobbyID, String player) {
    LOGGER.debug("Lobby ID: " + lobbyID + " player: " + player);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Iterator<ClientListItem> itr = clients.iterator();
        while (itr.hasNext()) {
          ClientListItem cl = itr.next();
          if (cl.getName().equals(player)) {
            LobbyListItem li = lobbyIDtoLobbyMop.get(lobbyID);
            li.getClientsInLobby().add(cl);
          }
        }

      }
    });
  }

  /**
   * Used when a new lobby shall be added to the view. "NLOBBY" {@link
   * ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   *
   * @param lobbyID   the ID of the new lobby
   * @param adminName the name of the Lobby admin
   */
  public void newLobby(String lobbyID, String adminName) {
    LobbyListView = lListView;
    LOGGER.debug("In newLobby()0  LobbyListView" + lListView);
    LOGGER.debug("New lobby with ID " + lobbyID + " and admin " + adminName);
    SimpleStringProperty id = new SimpleStringProperty(lobbyID);
    SimpleStringProperty admin = new SimpleStringProperty((adminName));
    LOGGER.debug("In newLobby()1  LobbyListView" + LobbyListView);
    boolean ownedByClient = false;
    if (adminName.equals(client.getUsername())) {
      LOGGER.debug("Client is admin. Name: " + adminName);
      ownedByClient = true;
    } else {
      LOGGER.debug("Different admin case. ADMIN Name: " + adminName);
    }
    LobbyListItem item = new LobbyListItem(id, admin, new SimpleBooleanProperty(ownedByClient),
        new SimpleBooleanProperty(true), new SimpleIntegerProperty(0));
    lobbyIDtoLobbyMop.put(lobbyID, item);
    LOGGER.debug("In newLobby()2  LobbyListView" + LobbyListView);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lobbies.add(item);
        LobbyListView.getItems().add(item);
        LOGGER.debug("within newLobby() run() thread");
        LOGGER.debug(item.toString());
        LOGGER.debug("In newLobby() run() " + LobbyListView);
      }
    });
    LOGGER.debug("newLobby() in LoungeSceneViewController seems to have reached end.");
    LOGGER.debug(lobbies.toString());
    LOGGER.debug("In newLobby()3  LobbyListView" + LobbyListView);
  }

  /**
   * Send the joinLobby Protocol message
   *
   * @param lobbyID the Lobby to be joinded
   */
  public void joinGame(String lobbyID) {
    client.getClient().sendMsgToServer(Protocol.joinLobby + "$" + lobbyID);
  }

  /**
   * Sends the startNewGame Protocol message
   */
  public void startGame() {
    client.getClient().sendMsgToServer(Protocol.startANewGame);
    //addGameView();
  }

  /**
   * Sends the leaveLobby protocol message
   */
  public void leaveLobby() {
    client.getClient().sendMsgToServer(Protocol.leaveLobby);
    removeGameView();
  }

  /**
   * Sends the Quit protocol message
   */
  public void leaveServer() {
    client.getClient().sendMsgToServer(Protocol.clientQuitRequest);
  }

  /**
   * Used to add a new player to the list of players. "NPLOS" {@link ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   *
   * @param s the information corresponding to to the client in String from
   */
  public void addClientToList(String s) {
    ClientListItem cl = new ClientListItem(s);
    ClientListView = cListView;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        clients.add(cl);
        LOGGER.debug("in addClientToList() in run()");
        LOGGER.debug(cl.toString() + " in run()");
      }
    });

  }

  /**
   * Sould remove a client of a certain name from the ListView
   *
   * @param name the name of the client to be removed
   */
  public void removeClientFromList(String name) {
    Iterator<ClientListItem> it = clients.iterator();
    while (it.hasNext()) {
      String uid = it.next().getName();
      if (uid.equals(name)) {
        it.remove();
        break;
      }
    }
  }

  public void removeClientFromLobby(String s) {
    //todo
  }

  /**
   * Sends the create New Lobby Protocol message
   */
  public void newGame() {
    client.getClient().sendMsgToServer(Protocol.createNewLobby);
  }

  /**
   * Sends the nameChange command, taking the new Name from the TextFlied
   */
  public void changeName() {
    TextField name = new TextField();
    name.setPromptText("Enter new Nickname!");
    this.NTtBToolBar.getItems().add(name);
    name.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        client.getClient().sendMsgToServer(Protocol.nameChange + "$" + name.getText());
        NTtBToolBar.getItems().remove(name);
      }
    });
  }

  /**
   * Utility to set the client model for this class
   *
   * @param client, the client model
   */
  public static void setClient(ClientModel client) {
    LoungeSceneViewController.client = client;
  }

  /**
   * Sends the highScore request message
   */
  public void sendHIghScore() {
    client.getClient().sendMsgToServer(Protocol.highScoreList);
  }

  /**
   * Sends the listLobbies protocol message
   */
  public void sendLilstle() {
    client.getClient().sendMsgToServer(Protocol.listLobbies);
  }

  /**
   * Adds a String to the highScore Text Flow
   *
   * @param data the String to be added
   */
  public void addHighScore(String data) {
    String[] arguments = data.split("/n");
    LOGGER.debug(arguments.length);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        highScore.getChildren().clear();
        for (String argument : arguments) {
          LOGGER.debug("HighScore " + argument);
          Text text = new Text(argument + System.lineSeparator());
          text.setFill(Color.BLACK);
          highScore.getChildren().add(text);
        }
      }
    });
  }

  /**
   * Adds a String to the lobbyPrint TextFlow
   *
   * @param data the String to be added
   */
  public void addLobbyPrint(String data) {
    String[] arguments = data.split("/n");
    LOGGER.debug(arguments.length);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        for (String argument : arguments) {
          LOGGER.debug("HighScore " + argument);
          Text text = new Text(argument + System.lineSeparator());
          text.setFill(Color.BLACK);
          lobbyPrint.getChildren().add(text);
        }
      }
    });
  }

  /**
   * Clears the lobbyPrint TextFlow
   */
  public void clearLobbyPrint() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lobbyPrint.getChildren().clear();
      }
    });
  }

  /**
   * Should remove the lobby from the lobby list view
   *
   * @param data to be removed
   */
  public void removeLobbyFromView(String data) {
    Iterator<LobbyListItem> itr = lobbies.iterator();
    while (itr.hasNext()) {
      LobbyListItem item = itr.next();
      if (item.getLobbyID().equals(data)) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            itr.remove();
            LOGGER.debug(
                "Made it into removeLobbyFromView if clause for lobby w/ ID: " + item.getLobbyID()
                    + " for data passed: " + data);
          }
        });

      }
    }
  }
}

