package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BellAnimation extends Transition {
  public static final Logger LOGGER = LogManager.getLogger(BellAnimation.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  ImageView imageView;
  Image[] bells;
  int index;

  public BellAnimation(ImageView imageView, Image[] bells) {
    setCycleCount(16);
    index = 0;
    this.imageView = imageView;
    this.bells = bells;
    setCycleDuration(new Duration(566.66));
    setInterpolator(Interpolator.DISCRETE);
  }


  @Override
  protected void interpolate(double frac) {
    try {
      if (index < 17) {
        imageView.setImage(bells[index]);
      }
      index++;
    } catch (Exception e) {
      LOGGER.warn(e.getMessage());
    }
  }
}
