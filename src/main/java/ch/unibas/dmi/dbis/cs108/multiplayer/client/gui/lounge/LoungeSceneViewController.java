package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.ChangeNameButtonPressedEventHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.events.LeaveServerButtonPressedEventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LoungeSceneViewController implements Initializable {


  public Button newGameButton;
  @FXML
  private TreeView LobbyTreeView;
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


  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or {@code
   *                  null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ChangeNameButton.setOnAction(new ChangeNameButtonPressedEventHandler());
    LeaveServerButton.setOnAction(new LeaveServerButtonPressedEventHandler());

    ClientListView.setItems(client.getAllClients());
  }

  public void updateLobbyListView(){

  }

  public void updateClientListView(){

  }

  public void addLobby(LobbyListItem lobby) {
    //TODO fix/complete following code: LobbyTreeView. (commented out due to compiling error)
  }

  public void addClientToList(ClientListItem player) {
    ListCell<StringProperty> playerCell = new ListCell<>();
  }

  /**
   * Utility to set the client model for this class
   * @param client, the client model
   */
  public static void setClient(ClientModel client) {
    LoungeSceneViewController.client = client;
  }
}
