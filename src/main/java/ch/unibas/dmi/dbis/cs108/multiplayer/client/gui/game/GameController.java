package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import static javafx.scene.AccessibleRole.PARENT;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.Sound;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GameStateModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.Sprites;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.SpritesDay;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.TrainAnimationDayController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.LoungeSceneViewController;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
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
  static boolean justRangBell = false; //used to track if the bell has been rung recently
  static final int minimumBellTime = 1000; //minimal time that has to pass between bells, in ms
  static boolean playingDayNoises = true; //true if playing day noises, false if playing night noises

  private static ClientModel client;

  private static GameStateModel gameStateModel;

  Image[] bells = Sprites.getBells();

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

  public void updateGameSprites(TrainAnimationDayController trainAnimation){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try{
          if(gameStateModel.getDayClone()) {
            if (!playingDayNoises) {
              Sound.startDaynoises();
              Sound.musicday();
              Sound.stopNightnoises();
              playingDayNoises = true;
            }
            Sprites.updateDayRoomSprites(gameStateModel.getPassengerTrainClone()[1], gameStateModel.getKickedOff());
          } else {
            if (playingDayNoises) {
              Sound.startNightnoises();
              Sound.stopmusicday();
              Sound.stopDaynoises();
              playingDayNoises = false;
            }
            Sprites.updateNightRoomSprites(gameStateModel.getPassengerTrainClone()[1], gameStateModel.getKickedOff());
          }
          /*room0ImageView.setImage(Sprites.getARoom(0));
          room1ImageView.setImage(Sprites.getARoom(1));
          room2ImageView.setImage(Sprites.getARoom(2));
          room3ImageView.setImage(Sprites.getARoom(3));
          room4ImageView.setImage(Sprites.getARoom(4));
          room5ImageView.setImage(Sprites.getARoom(5));*/
          trainAnimation.updateSprites();
        } catch (Exception e) {
          LOGGER.info(e.getMessage());
        }
      }
    });
  }

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
        if (g.getYourRoleFromPosition(client.getClient().getPosition()).equals("")) { //human
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

        } else { //ghost
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
        if (g.getYourRoleFromPosition(client.getClient().getPosition()).equals("g")) {//ghost
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
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom0Up();
  }

  public void moveRoom0Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom0Down();
  }

  /**
   * If button 1 is clicked, send the vote message 0 to the server
   */
  public void sendVote1() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 1);
  }
  public void moveRoom1Up() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom1Up();
  }

  public void moveRoom1Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom1Down();
  }

  /**
   * If button 2 is clicked, send the vote message 0 to the server
   */
  public void sendVote2() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 2);
  }

  public void moveRoom2Up() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom2Up();
  }

  public void moveRoom2Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom2Down();
  }

  /**
   * If button 3 is clicked, send the vote message 0 to the server
   */
  public void sendVote3() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 3);
  }
  public void moveRoom3Up() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom3Up();
  }

  public void moveRoom3Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom3Down();
  }

  /**
   * If button 4 is clicked, send the vote message 0 to the server
   */
  public void sendVote4() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 4);
  }
  public void moveRoom4Up() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom4Up();
  }

  public void moveRoom4Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom4Down();
  }

  /**
   * If button 5 is clicked, send the vote message 0 to the server
   */
  public void sendVote5() {
    client.getClient()
        .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + "$" + 5);
  }
  public void moveRoom5Up() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom5Up();
  }

  public void moveRoom5Down() {
    LoungeSceneViewController.getTrainAnimationDayController().moveRoom5Down();
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
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        noiseButton.setVisible(false);
      }
    });
  }

  public void setNoiseButtonVisible() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        noiseButton.setVisible(true);
      }
    });
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
    try {
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
    } catch (Exception e) {
      LOGGER.warn(e.getMessage());
    }

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
   * Adds an image of a bell on top of button0
   */
  public void noiseDisplay0() {
    LOGGER.debug("noise0 called");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          if(!gameStateModel.getKickedOff()[0]) {
            Animation bell = new BellAnimation(noiseImage5, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));

            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Animation bell = new BellAnimation(noiseImage4, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Animation bell = new BellAnimation(noiseImage3, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));

            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Animation bell = new BellAnimation(noiseImage2, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));

            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Animation bell = new BellAnimation(noiseImage1, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));

            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
            Animation bell = new BellAnimation(noiseImage0, bells);
            //wait until it's day:
            while (!getGameStateModel().getDayClone()) {
              Thread.sleep(100);
            }
            Thread.sleep(500);
            //just so the alarm isn't rung exactly when the day starts, add random delay
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
            bell.play();
            ringBellSound();
          }
        } catch (Exception e) {
          e.printStackTrace();
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
    noiseButton.toFront();
    ChatApp.setGameController(this);
  }

  /**
   * plays bell sound, but only if it hasn't been played recently, to avoid artefacts due to
   * overlapping sounds
   */
  public static void ringBellSound() {
    if (!justRangBell) {
      justRangBell = true;
      Sound.bell();
      try {
        System.out.println(justRangBell);
        Thread.sleep(minimumBellTime);
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              Thread.sleep(minimumBellTime);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            justRangBell = false;
          }
        }).start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
