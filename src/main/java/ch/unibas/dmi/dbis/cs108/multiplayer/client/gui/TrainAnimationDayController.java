package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
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
    shadowTrain.setImage(Sprites.getShadow());
    wagonBGImageView.setImage(Sprites.getSecondWagon());
    wagonFloorImageView.setImage(Sprites.getEmptyWagon());
    wagonFullImageView.setImage(Sprites.getFullWagon());
    lokiImageView.setImage(Sprites.getLoki());
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

  /**
   * Adds the gameView to the existing LobbyView
   */
  public void addGameView(ChatApp c) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          TrainAnimationDayController controller = new TrainAnimationDayController();
          LOGGER.debug(gamePane);
          LOGGER.debug(loki);
          LOGGER.debug(cApp);
          gameAnchorPane.getChildren().add(c.game);
          wagonFullImageView.setVisible(false);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized");
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Removes the GameView again - needed when a game is over or a lobby is left
   */
  public void removeGameView(ChatApp c) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          gameAnchorPane.getChildren().clear();
          wagonFullImageView.setVisible(true);
        } catch (Exception e) {
          LOGGER.debug("Not yet initialized");
        }
      }
    });
  }
}
