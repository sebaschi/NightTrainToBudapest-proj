package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.LobbyUpdater;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListOfLobbiesController implements Initializable {
  public static final Logger LOGGER = LogManager.getLogger(ListOfLobbiesController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  @FXML
  private ScrollPane backDropScrolePane;
  @FXML
  private AnchorPane scollingAnchorPane;
  @FXML
  private VBox LobbyListVBox;

  private ChatApp chatApp; //TODO: VeryImportant to set this one right!
  private HashSet<TreeView> treeViews = new HashSet<TreeView>();

  public void setChatApp(ChatApp chatApp) {
    this.chatApp = chatApp;
  }


  public void updateList() {
    clearVBox();
    for (LobbyModel lobby : LobbyDisplayHandler.getLobbies()) {
      newTreeView(lobby.getId(), lobby.getAdmin(), chatApp.getcModel().getUsername(), lobby.getMembers());
    }
  }

  /**
   * Creates one new TreeView for one Lobby
   * @param lobbyId the id of the lobby
   * @param admin the admin of the lobby
   * @param userName the username of the client
   */
  public void newTreeView(int lobbyId, String admin, String userName, HashSet<String> members) {
    try {
      Button button = new Button();
      if (admin.equals(userName)) { // the client of this user is the admin of this lobby
        button.setOnAction(event -> startGame());
        button.setText("Start");
      } else {
        button.setOnAction(event -> joinALobby(lobbyId));
        button.setText("Join");
      }
      HBox rootHBox = new HBox();
      rootHBox.setPrefWidth(195);
      rootHBox.setMaxHeight(20);
      Label adminLabel = new Label(lobbyId + " " + admin);
      adminLabel.setTextFill(Color.WHITE);
      rootHBox.getChildren().add(adminLabel);
      rootHBox.getChildren().add(button);
      TreeItem<HBox> root = new TreeItem<HBox>(rootHBox);
      root.setExpanded(true);
      for (String member : members) {
        HBox memberBox = new HBox();
        memberBox.setPrefWidth(195);
        memberBox.setMaxHeight(20);
        memberBox.setPrefHeight(USE_COMPUTED_SIZE);
        Label memberLabel = new Label("- " + member);
        memberLabel.setTextFill(Color.WHITE);
        memberBox.getChildren().add(memberLabel);
        root.getChildren().add(new TreeItem<HBox>(memberBox));
      }
      TreeView<HBox> treeView = new TreeView<>(root);
      treeView.setVisible(true);
      treeView.setPrefWidth(195);
      treeView.setPrefHeight(USE_COMPUTED_SIZE);
      treeView.setMaxHeight(80);
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          LobbyListVBox.getChildren().add(treeView);
          LobbyListVBox.setVisible(true);
          //LobbyListVBox.setBackground(Background.fill(Color.DARKBLUE));
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public void clearVBox() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        LobbyListVBox.getChildren().clear();
      }
    });
  }

  public void startGame() {
    chatApp.getcModel().getClient().sendMsgToServer(Protocol.startANewGame); //TODO: Very important to get right!
  }

  public void joinALobby(int id) {
    chatApp.getcModel().getClient().sendMsgToServer(Protocol.joinLobby + "$" + id);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ChatApp.setListController(this);
  }
}
