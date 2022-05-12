package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import javafx.animation.Animation;
import javafx.animation.Timeline.*;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class animates the wheels from the train from sprites
 */

public class WheelsAnimation extends Transition {
  public static final Logger LOGGER = LogManager.getLogger(WheelsAnimation.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  ImageView imageView;
  private static final Image[] wheels = new Image[26];
  private int index;


  WheelsAnimation(Duration duration, ImageView imageView) {
    this.imageView = imageView;
    setCycleDuration(duration);
    setInterpolator(Interpolator.DISCRETE);
    index = 1;
    wheels[0] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0001.png");
    wheels[1] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0002.png");
    wheels[2] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0003.png");
    wheels[3] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0004.png");
    wheels[4] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0005.png");
    wheels[5] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0006.png");
    wheels[6] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0007.png");
    wheels[7] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0008.png");
    wheels[8] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0009.png");
    wheels[9] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0010.png");
    wheels[10] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0011.png");
    wheels[11] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0012.png");
    wheels[12] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0013.png");
    wheels[13] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0014.png");
    wheels[14] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0015.png");
    wheels[15] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0016.png");
    wheels[16] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0017.png");
    wheels[17] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0018.png");
    wheels[18] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0019.png");
    wheels[19] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0020.png");
    wheels[20] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0021.png");
    wheels[21] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0022.png");
    wheels[22] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0023.png");
    wheels[23] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0024.png");
    wheels[24] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0025.png");
    wheels[25] = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/Wheels/Image0026.png");
  }


  @Override
  protected void interpolate(double frac) {
    if(index == 25) index = 0;
    imageView.setImage(wheels[index]);
    index++;
  }
}


















