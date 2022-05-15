package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.LoungeSceneViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainAnimationDayController implements Initializable {
  public static final Logger LOGGER = LogManager.getLogger(TrainAnimationDayController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  @FXML
  private ImageView backgroundCropView;
  @FXML
  private ImageView wagonWallImageView;
  @FXML
  private ImageView foreGroundAnimationImageView1;
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
  private ImageView backGroundAnimationImageView;
  @FXML
  private ImageView wheelsImageView;
  @FXML
  private ImageView shadowTrain;
  @FXML
  private ImageView wagonBGImageView;
  @FXML
  private ImageView wagonFloorImageView;
  @FXML
  private AnchorPane wheelsAnchorPane;
  @FXML
  private AnchorPane gameAnchorPane;
  @FXML
  private ImageView wagonFullImageView;
  @FXML
  private ImageView lokiImageView;


  private ChatApp chatApp;
  private static ChatApp cApp;
  private static AnchorPane gamePane;
  private static ImageView loki;

  public TrainAnimationDayController(){
    super();
    LOGGER.debug("Empty TrainAnimationDayController() constructor was called");
  }

  public void updateSprites(){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try{
          shadowTrain.setImage(Sprites.getShadow());
          wagonBGImageView.setImage(Sprites.getSecondWagon());
          wagonFloorImageView.setImage(Sprites.getEmptyWagon());
          wagonFullImageView.setImage(Sprites.getFullWagon());
          lokiImageView.setImage(Sprites.getLoki());
          if(GameController.getGameStateModel().getDayClone()) {
            Sprites.updateDayRoomSprites(GameController.getGameStateModel().getPassengerTrainClone()[1], GameController.getGameStateModel().getKickedOff());
          } else {
            Sprites.updateNightRoomSprites(GameController.getGameStateModel().getPassengerTrainClone()[1], GameController.getGameStateModel().getKickedOff());
          }
          room0ImageView.setImage(Sprites.getARoom(0));
          room1ImageView.setImage(Sprites.getARoom(1));
          room2ImageView.setImage(Sprites.getARoom(2));
          room3ImageView.setImage(Sprites.getARoom(3));
          room4ImageView.setImage(Sprites.getARoom(4));
          room5ImageView.setImage(Sprites.getARoom(5));
        } catch (Exception e) {
          LOGGER.info(e.getMessage());
        }
      }
    });
  }

  public ChatApp getChatApp() {
    return chatApp;
  }

  public void setChatApp(ChatApp chatApp) {
    this.chatApp = chatApp;
  }

  public static void setcApp(ChatApp cApp) {
    TrainAnimationDayController.cApp = cApp;
  }

  public AnchorPane getGameAnchorPane() {
    return gameAnchorPane;
  }

  public ImageView getWagonFullImageView() {
    return wagonFullImageView;
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

  
  public void showFullWagon() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        wagonFullImageView.setVisible(true);
      }
    });
  }
  
  public void dontShowFullWagon() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        wagonFullImageView.setVisible(false);
      }
    });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    LoungeSceneViewController.setTrainAnimationDayController(this);
    LOGGER.debug(cApp);
    setChatApp(cApp);
    LOGGER.debug(gameAnchorPane);
    gamePane = gameAnchorPane;
    loki = lokiImageView;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Animation wheels = new WheelsAnimation(Duration.millis(866.666), wheelsImageView);
        wheels.setCycleCount(Animation.INDEFINITE);
        wheels.play();
        Animation backGround = new BGAnimation(Duration.millis(17), backGroundAnimationImageView, foreGroundAnimationImageView1, backgroundCropView);
        backGround.setCycleCount(Animation.INDEFINITE);
        backGround.play();
      }
    });
  }
}
