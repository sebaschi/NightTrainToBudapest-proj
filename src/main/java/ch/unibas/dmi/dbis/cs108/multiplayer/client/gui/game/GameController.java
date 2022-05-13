package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import static javafx.scene.AccessibleRole.PARENT;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GameStateModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameController implements Initializable {

  public static final Logger LOGGER = LogManager.getLogger(GameController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private static ClientModel client;

  private static GameStateModel gameStateModel;

  public GameController() {
    super();
  }

  //TODO(Seraina, Sebi): Same issue as ChatController? do with setters?
  public GameController(ClientModel c, GameStateModel g) {
    client = c;
    gameStateModel = g;
  }

  public void setClient(ClientModel c) {
    client = c;
  }

  public static ClientModel getClient() {
    return client;
  }

  public static GameStateModel getGameStateModel() {
    return gameStateModel;
  }

  @FXML
  private ImageView room0ImageView;
  @FXML
  private ImageView room1ImageView;
  @FXML
  private ImageView room2ImageView;
  @FXML
  private ImageView room3ImageView;
  @FXML
  private ImageView room4ImageView;
  @FXML
  private ImageView room5ImageView;
  @FXML
  private AnchorPane gameBG;
  @FXML
  private Group roomButtonGroupDay;
  @FXML
  private Button buttonRoom0;
  @FXML
  private Button buttonRoom1;
  @FXML
  private Button buttonRoom2;
  @FXML
  private Button buttonRoom3;
  @FXML
  private Button buttonRoom4;
  @FXML
  private Button buttonRoom5;

  @FXML
  private HBox roomLables;
  @FXML
  private TextFlow lableRoom0;
  @FXML
  private TextFlow lableRoom1;
  @FXML
  private TextFlow lableRoom2;
  @FXML
  private TextFlow lableRoom3;
  @FXML
  private TextFlow lableRoom4;
  @FXML
  private TextFlow lableRoom5;
  @FXML
  private HBox notificationHBox;
  @FXML
  private ImageView noiseImage0;
  @FXML
  private ImageView noiseImage1;
  @FXML
  private ImageView noiseImage2;
  @FXML
  private ImageView noiseImage3;
  @FXML
  private ImageView noiseImage4;
  @FXML
  private ImageView noiseImage5;
  @FXML
  private Button noiseButton;
  @FXML
  public TextFlow notificationText;
  @FXML
  private AnchorPane chatAreaGame;


  public void addToChatArea(Node n) {
    chatAreaGame.getChildren().add(n);
  }

  public AnchorPane getChatAreaGame() {
    return chatAreaGame;
  }

  public void setVoteButtonVisibilityDay(GameStateModel g){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        LOGGER.debug(buttonRoom0);
        if (g.getYourRole().equals("h")) {
          try {
            buttonRoom0.setVisible(true);
            buttonRoom1.setVisible(true);
            buttonRoom2.setVisible(true);
            buttonRoom3.setVisible(true);
            buttonRoom4.setVisible(true);
            buttonRoom5.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else {
          try {
            buttonRoom0.setVisible(false);
            buttonRoom1.setVisible(false);
            buttonRoom2.setVisible(false);
            buttonRoom3.setVisible(false);
            buttonRoom4.setVisible(false);
            buttonRoom5.setVisible(false);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  public void setVoteButtonVisibilityNight(GameStateModel g){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        LOGGER.debug(buttonRoom0);
        if (g.getYourRole().equals("g")) {
          try {
            buttonRoom0.setVisible(true);
            buttonRoom1.setVisible(true);
            buttonRoom2.setVisible(true);
            buttonRoom3.setVisible(true);
            buttonRoom4.setVisible(true);
            buttonRoom5.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else {
          try {
            buttonRoom0.setVisible(false);
            buttonRoom1.setVisible(false);
            buttonRoom2.setVisible(false);
            buttonRoom3.setVisible(false);
            buttonRoom4.setVisible(false);
            buttonRoom5.setVisible(false);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
  }


  /**
   * If button 0 is clicked, send the vote message 0 to the server
   */
  public void sendVote0() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 0);
  }

  public void moveRoom0Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room0ImageView.setY(-20);
      }
    });
  }

  public void moveRoom0Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room0ImageView.setY(0);
      }
    });
  }

  /**
   * If button 1 is clicked, send the vote message 0 to the server
   */
  public void sendVote1() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 1);
  }
  public void moveRoom1Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room1ImageView.setY(-20);
      }
    });
  }

  public void moveRoom1Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room1ImageView.setY(0);
      }
    });
  }

  /**
   * If button 2 is clicked, send the vote message 0 to the server
   */
  public void sendVote2() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 2);
  }

  public void moveRoom2Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room2ImageView.setY(-20);
      }
    });
  }

  public void moveRoom2Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room2ImageView.setY(0);
      }
    });
  }

  /**
   * If button 3 is clicked, send the vote message 0 to the server
   */
  public void sendVote3() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 3);
  }
  public void moveRoom3Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room3ImageView.setY(-20);
      }
    });
  }

  public void moveRoom3Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room3ImageView.setY(0);
      }
    });
  }

  /**
   * If button 4 is clicked, send the vote message 0 to the server
   */
  public void sendVote4() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 4);
  }
  public void moveRoom4Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room4ImageView.setY(-20);
      }
    });
  }

  public void moveRoom4Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room4ImageView.setY(0);
      }
    });
  }

  /**
   * If button 5 is clicked, send the vote message 0 to the server
   */
  public void sendVote5() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 5);
  }
  public void moveRoom5Up() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room5ImageView.setY(-20);
      }
    });
  }

  public void moveRoom5Down() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        room5ImageView.setY(0);
      }
    });
  }

  /**
   * Sends a noise message, to the server, should be a gui message?
   */
  public void noise() {
    LOGGER.info("Do you even get here");
    LOGGER.info(client.getClient());
    LOGGER.info(client.getClient().getPosition());
    if (client.getClient() == null) {
      LOGGER.info("But why???");
    }
    client.getClient().sendMsgToServer(
        Protocol.sendMessageToAllClients + "$" + Protocol.printToGUI + "$"
            + GuiParameters.noiseHeardAtPosition + "$"
            + client.getClient().getPosition()); //TODO: Test!!
  }

  public void setNoiseButtonInvisible() {
    noiseButton.setVisible(false);
  }

  public void setNoiseButtonVisible() {
    noiseButton.setVisible(true);
  }

  /**
   * Takes a given message and displays it in the notificationText Flow in the game Scene
   *
   * @param msg the message to be displayed
   */
  public void addMessageToNotificationText(String msg) {
    LOGGER.trace("addMessage " + msg);
    Text notification = new Text(System.lineSeparator() + msg);
    notification.setFill(Color.BLACK);
    notification.setStyle("-fx-font: 50 arial;");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          notificationText.getChildren().add(notification);
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });

    //TODO: Wait for a certain time, then clear all again
  }

  /**
   * Clears all children from notificationText TextFlow
   */
  public void clearNotificationText() {
    LOGGER.trace("clear notify");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          notificationText.getChildren().remove(0);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized");
        }
      }
    });

  }

  /**
   * Updates the labels of the rooms accordingly to the datastructures in GameStateModel
   * TODO(Seraina): use a method to shorten, its madness
   */
  public void updateRoomLabels() {
    LOGGER.debug("roomlables update");
    String[] names = gameStateModel.getPassengerTrainClone()[0];
    String[] roles = gameStateModel.getPassengerTrainClone()[1];
    boolean[] kickedOff = gameStateModel.getKickedOff();
    Text name0 = new Text(names[0]);
    name0.setStyle("-fx-font: 25 arial;");
    name0.setFill(Color.WHITE);
    Text name1 = new Text(names[1]);
    name1.setStyle("-fx-font: 25 arial;");
    name1.setFill(Color.WHITE);
    Text name2 = new Text(names[2]);
    name2.setStyle("-fx-font: 25 arial;");
    name2.setFill(Color.WHITE);
    Text name3 = new Text(names[3]);
    name3.setStyle("-fx-font: 25 arial;");
    name3.setFill(Color.WHITE);
    Text name4 = new Text(names[4]);
    name4.setStyle("-fx-font: 25 arial;");
    name4.setFill(Color.WHITE);
    Text name5 = new Text(names[5]);
    name5.setStyle("-fx-font: 25 arial;");
    name5.setFill(Color.WHITE);
    Text role0;
    if (kickedOff[0]) {
      role0 = new Text("\nkicked off");
    } else {
      role0 = new Text("\n" + roles[0]);
    }
    role0.setStyle("-fx-font: 25 arial;");
    role0.setFill(Color.WHITE);
    Text role1;
    if (kickedOff[1]) {
      role1 = new Text("\nkicked off");
    } else {
      role1 = new Text("\n" + roles[1]);
    }
    role1.setStyle("-fx-font: 25 arial;");
    role1.setFill(Color.WHITE);
    Text role2;
    if (kickedOff[2]) {
      role2 = new Text("\nkicked off");
    } else {
      role2 = new Text("\n" + roles[2]);
    }
    role2.setStyle("-fx-font: 25 arial;");
    role2.setFill(Color.WHITE);
    Text role3;
    if (kickedOff[3]) {
      role3 = new Text("\nkicked off");
    } else {
      role3 = new Text("\n" + roles[3]);
    }
    role3.setStyle("-fx-font: 25 arial;");
    role3.setFill(Color.WHITE);
    Text role4;
    if (kickedOff[4]) {
      role4 = new Text("\nkicked off");
    } else {
      role4 = new Text("\n" + roles[4]);
    }
    role4.setStyle("-fx-font: 25 arial;");
    role4.setFill(Color.WHITE);
    Text role5;
    if (kickedOff[5]) {
      role5 = new Text("\nkicked off");
    } else {
      role5 = new Text("\n" + roles[5]);
    }
    role5.setStyle("-fx-font: 25 arial;");
    role5.setFill(Color.WHITE);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          lableRoom0.getChildren().clear();
          lableRoom0.getChildren().add(name0);
          lableRoom0.getChildren().add(role0);
          lableRoom1.getChildren().clear();
          lableRoom1.getChildren().add(name1);
          lableRoom1.getChildren().add(role1);
          lableRoom2.getChildren().clear();
          lableRoom2.getChildren().add(name2);
          lableRoom2.getChildren().add(role2);
          lableRoom3.getChildren().clear();
          lableRoom3.getChildren().add(name3);
          lableRoom3.getChildren().add(role3);
          lableRoom4.getChildren().clear();
          lableRoom4.getChildren().add(name4);
          lableRoom4.getChildren().add(role4);
          lableRoom5.getChildren().clear();
          lableRoom5.getChildren().add(name5);
          lableRoom5.getChildren().add(role5);
        } catch (Exception e) {
          LOGGER.trace("Not yet initialized");
        }
      }
    });
  }

  /**
   * loads the notification Bell from resource
   * @return the Image node containing the BellImage
   */
  public Image loadBellImage() {
    Image bell = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/DayOpen/bell.png");
    return bell;
  }

  /**
   * Adds an image of a bell on top of button0
   */
  public void noiseDisplay0() {
    LOGGER.debug("noise0 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[0]) {
            noiseImage0.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });

  }

  /**
   * Adds an image of a bell on top of button1
   */
  public void noiseDisplay1() {
    LOGGER.debug("noise1 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[1]) {
            noiseImage1.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });
  }

  /**
   * Adds an image of a bell on top of button2
   */
  public void noiseDisplay2() {
    LOGGER.debug("noise2 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[2]) {
            noiseImage2.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
          ;
        }
      }
    });
  }

  /**
   * Adds an image of a bell on top of button3
   */
  public void noiseDisplay3() {
    LOGGER.debug("noise3 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[3]) {
            noiseImage3.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });
  }

  /**
   * Adds an image of a bell on top of button4
   */
  public void noiseDisplay4() {
    LOGGER.debug("noise4 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[4]) {
            noiseImage4.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });
  }

  /**
   * Adds an image of a bell on top of button5
   */
  public void noiseDisplay5() {
    LOGGER.debug("noise5 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[5]) {
            noiseImage5.setImage(loadBellImage());
          }
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });
  }

  /**
   * Clears all bells from the view
   */
  public void clearAllNoiseDisplay() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          noiseImage0.setImage(null);
          noiseImage1.setImage(null);
          noiseImage2.setImage(null);
          noiseImage3.setImage(null);
          noiseImage4.setImage(null);
          noiseImage5.setImage(null);
        } catch (Exception e) {
          LOGGER.debug(e.getMessage());
        }
      }
    });
  }

  public void setGameStateModel(
      GameStateModel gameStateModel) {
    GameController.gameStateModel = gameStateModel;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ChatApp.setGameController(this);
  }
}
