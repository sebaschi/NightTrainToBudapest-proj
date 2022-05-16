package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.BGAnimation;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.TrainAnimationDayController;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoungeSceneViewController implements Initializable {

  public static final Logger LOGGER = LogManager.getLogger(LoungeSceneViewController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  @FXML
  private AnchorPane listLobbyAnchorPane;
  @FXML
  private AnchorPane buttonPane;
  @FXML
  private AnchorPane buttonLobbyPane;
  @FXML
  private AnchorPane backGroundAnimationPane;

  @FXML
  private AnchorPane backGroundAnchorPane;
  @FXML
  private AnchorPane gameDisplayAnchorPane;
  @FXML
  private TextFlow highScore;
  @FXML
  private SplitPane chatSplitPane;
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
  private Button ChangeNameButton;
  @FXML
  private Button LeaveServerButton;
  @FXML
  private AnchorPane ChatArea;
  @FXML
  private BorderPane LoungeSceneBorderPane;
  @FXML
  private ToolBar NTtBToolBar;


  public static ClientModel client;
  private static ChatApp chatApp;
  private ChatApp cApp;
  private static TrainAnimationDayController trainAnimationDayController;


  public LoungeSceneViewController() {
    super();
  }

  public void setChatApp(ChatApp chatApp) {
    LoungeSceneViewController.chatApp = chatApp;
  }

  public void setcApp(ChatApp cApp) {
    this.cApp = cApp;
  }

  public static void setTrainAnimationDayController(
      TrainAnimationDayController trainAnimationDayController) {
    LoungeSceneViewController.trainAnimationDayController = trainAnimationDayController;
  }

  public static TrainAnimationDayController getTrainAnimationDayController() {
    return trainAnimationDayController;
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
    addChatView();
    addBackgroundDay();
    addListOfLobbiesView();
    LOGGER.debug("cApp = " + cApp);
    LOGGER.debug("chatApp = " + chatApp);
    TrainAnimationDayController.setcApp(cApp);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        ImageView bgAnimationView = new ImageView();
        bgAnimationView.setFitHeight(1950);
        bgAnimationView.setFitWidth(6667.968);
      }
    });
  }

  /**
   * Adds the new LobbyListView
   */
  public void addListOfLobbiesView() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          listLobbyAnchorPane.getChildren().add(chatApp.lobbyList);
        } catch (Exception e) {
          LOGGER.warn(e.getMessage());
        }
      }
    });
  }

  /**
   * Adds the gameView to the existing LobbyView
   */
  public void addGameView() {

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          LOGGER.debug(" in GameView()" + chatApp);
          buttonLobbyPane.setVisible(false);
          gameDisplayAnchorPane.getChildren().add(chatApp.game);
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
          trainAnimationDayController.showFullWagon();
          buttonLobbyPane.setVisible(true);
          gameDisplayAnchorPane.getChildren().clear();
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
          ChatArea.getChildren().add(chatApp.chat);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized: chatAnchorPane");
        }
      }
    });
  }

  public void addBackgroundDay() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          LOGGER.debug("in addBackgroundDay() run()");
          backGroundAnimationPane.getChildren().add(chatApp.backgroundDay);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
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

}

