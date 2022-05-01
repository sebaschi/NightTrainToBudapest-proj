package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.ChangeNameButtonPressedEventHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.LeaveServerButtonPressedEventHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoungeSceneViewController implements Initializable {

  Protocol protocol;

  public Button newGameButton;
  @FXML
  private ListView<HBox> LobbyListView;
  @FXML
  private ListView<SimpleStringProperty> ClientListView;
  @FXML
  private Button ChangeNameButton;
  @FXML
  private Button LeaveServerButton;
  @FXML
  private AnchorPane ChatArea;
  @FXML
  private HBox ChatAreaHBox;
  @FXML
  private BorderPane LoungeSceneBorderPane;
  @FXML
  private ToolBar NTtBToolBar;

  public static ClientModel client;
  public static ChatApp chatApp;

  private ObservableMap<String, ObservableList<String>> lobbyToMemberssMap;
  private HashMap<String, String> clientToLobbyMap;

  public LoungeSceneViewController() {
    super();
    lobbyToMemberssMap = FXCollections.observableHashMap();
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
    this.protocol = new Protocol();
    ChangeNameButton.setOnAction(event -> changeName());
    LeaveServerButton.setOnAction(new LeaveServerButtonPressedEventHandler());
    newGameButton.setOnAction(event -> newGame());
    LobbyListView.setVisible(true);
    ClientListView.setVisible(true);
    ClientListView.setItems(client.getAllClients());
    LobbyListView.setPlaceholder(new Text("No open lobbies!"));
    client.getAllClients().addListener(new ListChangeListener<SimpleStringProperty>() {
      @Override
      public void onChanged(Change<? extends SimpleStringProperty> c) {
        List<SimpleStringProperty> removed = (List<SimpleStringProperty>) c.getRemoved();
        for(SimpleStringProperty player: removed) {

        }
      }
    });
  }

  public void updateClientListView(ObservableList<SimpleStringProperty> names) {
    ObservableList<SimpleStringProperty> clientsLeft = ClientListView.getItems();
    clientsLeft.removeAll(names);
    this.ClientListView.setItems(names);
    for (SimpleStringProperty gone : clientsLeft) {
      //TODO
    }
  }

  /**
   * Adds players to a lobby
   * "NMEMB" {@link ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   * @param lobbyID
   * @param player
   */
  public void addPlayerToLobby(String lobbyID, String player) {
    ObservableList<String> members = lobbyToMemberssMap.get(lobbyID);
    members.add(player);
  }

  /**
   * Used when a new lobby shall be added to the view.
   * "NLOBBY" {@link ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   * @param lobbyID
   * @param adminName
   */
  public void newLobby(String lobbyID, String adminName) {
    SimpleStringProperty id = new SimpleStringProperty(lobbyID);
    SimpleStringProperty admin = new SimpleStringProperty((adminName));
    boolean ownedByClient = false;
    Button startOrJoin;
    if (adminName.equals(client.getUsername())) {
      ownedByClient = true;
      startOrJoin = new Button("Start");
      startOrJoin.setOnAction(event -> startGame());
    } else {
      startOrJoin = new Button("Join");
      startOrJoin.setOnAction(event -> joinGame(lobbyID));
    }
    HBox lobby = new HBox();
    Label idLabel = new Label();
    Label adminLabel = new Label();
    idLabel.setText(lobbyID);
    adminLabel.setText(adminName);
    startOrJoin.setVisible(true);
    lobby.getChildren().add(idLabel);
    lobby.getChildren().add(adminLabel);
    lobby.getChildren().add(startOrJoin);
    ListView<String> members = new ListView<>();
    members.setId("membersOfLobby");
    if (ownedByClient) {
      members.getItems().add("(you are admin) " + adminName);
    } else {
      members.getItems().add("(admin)" + adminName);
      members.getItems().add(client.getUsername());
    }
    lobby.setId(lobbyID);
    lobbyToMemberssMap.put(lobbyID, members.getItems());
    lobby.setVisible(true);
    LobbyListView.getItems().add(lobby);
  }

  private void joinGame(String lobbyID) {
    client.getClient().sendMsgToServer(Protocol.joinLobby + "$" + lobbyID);
  }

  private void startGame() {
    client.getClient().sendMsgToServer(Protocol.startANewGame);
  }

  ;

  /**
   * Used to add a new player to the list of players.
   * "NPLOS" {@link ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters}
   * @param s
   */
  public void addClientToList(String s) {
    ClientListView.getItems().add(new SimpleStringProperty(s));
  }

  public void newGame() {
    client.getClient().sendMsgToServer(Protocol.createNewLobby);
  }


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

  public void removePlayer(String id) {
    Iterator<SimpleStringProperty> it = client.getAllClients().iterator();
    while (it.hasNext()) {
      String uid = it.next().getValue();
      if (uid.equals(id)) {
        it.remove();
        break;
      }
    }
  }

  /**
   * Utility to set the client model for this class
   *
   * @param client, the client model
   */
  public static void setClient(ClientModel client) {
    LoungeSceneViewController.client = client;
  }

  public HBox getChatAreaHBox() {
    return ChatAreaHBox;
  }

  public void setChatAreaHBox(HBox chatAreaHBox) {
    ChatAreaHBox = chatAreaHBox;
  }
}
