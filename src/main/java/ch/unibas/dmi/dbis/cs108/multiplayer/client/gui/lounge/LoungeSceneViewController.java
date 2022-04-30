package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.ChangeNameButtonPressedEventHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.LeaveServerButtonPressedEventHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
  private ListView LobbyListView;
  @FXML
  private ListView ClientListView;
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

  public LoungeSceneViewController() {
    super();

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
    this.protocol = new Protocol();
    ChangeNameButton.setOnAction(event -> changeName());
    LeaveServerButton.setOnAction(new LeaveServerButtonPressedEventHandler());
    newGameButton.setOnAction(event -> newGame());

    ClientListView.setItems(client.getAllClients());
    LobbyListView.setPlaceholder(new Text("No open lobbies!"));
  }

  public void updateLobbyListView() {
    //TODO
  }

  public void updateClientListView(ObservableList<SimpleStringProperty> names) {
    this.ClientListView.setItems(names);
  }

  /**
   * Adds a lobby to the view
   * @param lobbyID
   * @param admin
   * @param players
   */
  public void addLobby(String lobbyID, String admin, String players) {
    TitledPane lobbyObject = new TitledPane();
    lobbyObject.setId(lobbyID+admin);
    lobbyObject.textProperty().setValue("Lobby Nr: " + lobbyID + " Admin: " + admin);

    ObservableList<SimpleStringProperty> listOfPlayersInLobby = new SimpleListProperty<>();

    String[] playersArr = players.split(":");
    int noOfPlayers = playersArr.length;
    for(int i = 0; i < noOfPlayers; i++){
      listOfPlayersInLobby.add(new SimpleStringProperty(playersArr[i]));
    }
    ListView view = new ListView(listOfPlayersInLobby);
    lobbyObject.contentProperty().set(view);
    LobbyListView.getItems().add(lobbyObject);
  }

  public void addClientToList(String s) {
    ClientListView.getItems().add(new SimpleStringProperty(s));
  }

  public void newGame() {
    client.getClient().sendMsgToServer(Protocol.createNewLobby);
  }

  public void changeName() {
    TextField name = new TextField("Enter new name!");
    this.NTtBToolBar.getItems().add(name);
    name.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        client.getClient().sendMsgToServer(Protocol.nameChange + "$" + name.getText());
        NTtBToolBar.getItems().remove(NTtBToolBar.getItems().size());
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
}
