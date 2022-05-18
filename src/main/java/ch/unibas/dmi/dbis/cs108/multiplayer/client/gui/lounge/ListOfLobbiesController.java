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
  private static boolean gameOngoing = false;

  public void setChatApp(ChatApp chatApp) {
    this.chatApp = chatApp;
  }

  public static void setGameOngoing(boolean gameOngoing) {
    ListOfLobbiesController.gameOngoing = gameOngoing;
  }

  public static boolean isGameOngoing() {
    return gameOngoing;
  }

  public void updateList() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        clearVBox();
        for (LobbyModel lobby : LobbyDisplayHandler.getLobbies()) {
          newTreeView(lobby.getId(), lobby.getAdmin(), lobby.isLobbyIsOpen(), chatApp.getcModel().getUsername(), lobby.getMembers());
        }
      }
    }).start();
  }

  /**
   * Creates one new TreeView for one Lobby
   * @param lobbyId the id of the lobby
   * @param admin the admin of the lobby
   * @param userName the username of the client
   * @param isOpen the status if lobby is open or closed
   * @param members A hashset containing all non admin member of this lobby
   */
  public void newTreeView(int lobbyId, String admin, boolean isOpen, String userName, HashSet<String> members) {
    try {
      Button button = new Button();
      if (admin.equals(userName)) { // the client of this user is the admin of this lobby
        button.setOnAction(event -> startGame());
        button.setText("Start");
      } else if (isOpen){
        button.setOnAction(event -> joinALobby(lobbyId));
        button.setText("Join");
      } else {
        button.setVisible(false);
      }
      HBox rootHBox = new HBox();
      rootHBox.setPrefWidth(300);
      rootHBox.setMaxHeight(45);
      String statusLobby;
      if (isOpen) {
        statusLobby = " (open)";
      } else {
        statusLobby = " (closed)";
      }
      Label adminLabel = new Label("  Lobby " + lobbyId + ": " + admin + statusLobby);
      adminLabel.setTextFill(Color.WHITE);
      try {
        rootHBox.getChildren().add(button);
      }  catch (Exception e) {
        LOGGER.warn(e.getMessage());
      }
      rootHBox.getChildren().add(adminLabel);
      TreeItem<HBox> root = new TreeItem<HBox>(rootHBox);
      root.setExpanded(true);
      int i = 1;
      for (String member : members) {
        HBox memberBox = new HBox();
        memberBox.setPrefWidth(300);
        memberBox.setMaxHeight(45);
        memberBox.setPrefHeight(USE_COMPUTED_SIZE);
        Label memberLabel = new Label("- " + member);
        memberLabel.setTextFill(Color.WHITE);
        memberBox.getChildren().add(memberLabel);
        root.getChildren().add(new TreeItem<HBox>(memberBox));
        i++;
      }
      TreeView<HBox> treeView = new TreeView<>(root);
      treeView.setVisible(true);
      treeView.setPrefWidth(300);
      treeView.setMinHeight(i*45 + 10);
      treeView.setPrefHeight(i*45 + 10);
      treeView.setMaxHeight(i*45 + 10);
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          LobbyListVBox.getChildren().add(treeView);
          LobbyListVBox.setVisible(true);
          //LobbyListVBox.setBackground(Background.fill(Color.DARKBLUE));
        }
      });
    } catch (Exception e) {
      LOGGER.warn(e.getMessage());
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
