package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BGAnimation extends Transition {
  ImageView imageView;
  private static final Image bgFull = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/BG_small.jpg");
  int index;
  int lastIndex;

  public BGAnimation(Duration duration, ImageView imageView) {
    index = 0;
    lastIndex = 1034;
    this.imageView = imageView;
    imageView.setFitHeight(1950);
    imageView.setFitWidth(6667.968);
    imageView.setImage(bgFull);
    setCycleDuration(duration);
    setInterpolator(Interpolator.DISCRETE);

  }

  @Override
  protected void interpolate(double frac) {
    if(index == lastIndex) {
      index = 0;
      imageView.setX(0);
      imageView.setY(0);
    }
    imageView.setX(index * -5);
    imageView.setY(index * -1.07);
    index++;

  }
}
