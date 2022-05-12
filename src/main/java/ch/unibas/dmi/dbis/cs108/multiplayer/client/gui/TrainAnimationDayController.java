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

  public TrainAnimationDayController(){
    super();
    LOGGER.debug("Empty TrainAnimationDayController() constructor was called");
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setChatApp(cApp);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Animation wheels = new WheelsAnimation(Duration.millis(866.666), wheelsImageView);
        wheels.setCycleCount(Animation.INDEFINITE);
        wheels.play();
      }
    });
  }
}
