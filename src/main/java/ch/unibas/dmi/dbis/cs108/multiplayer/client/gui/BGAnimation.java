package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BGAnimation extends Transition {
  ImageView imageView;
  int index;
  int lastIndex;

  public BGAnimation(Duration duration, ImageView imageView) {
    index = 0;
    lastIndex = 1034;
    this.imageView = imageView;
    imageView.setFitHeight(1950);
    imageView.setFitWidth(6667.968);
    imageView.setImage(Sprites.getBg());
    setCycleDuration(duration);
    setInterpolator(Interpolator.DISCRETE);

  }

  @Override
  protected void interpolate(double frac) {
    imageView.setImage(Sprites.getBg());
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
