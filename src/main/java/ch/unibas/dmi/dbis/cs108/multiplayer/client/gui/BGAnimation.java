package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BGAnimation extends Transition {
  ImageView imageView;
  private static final Image bgFull = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/BG_small.jpg");

  BGAnimation(Duration duration, ImageView imageView) {
    this.imageView = imageView;
    setCycleDuration(duration);
    setInterpolator(Interpolator.DISCRETE);

  }

  @Override
  protected void interpolate(double frac) {

  }
}
