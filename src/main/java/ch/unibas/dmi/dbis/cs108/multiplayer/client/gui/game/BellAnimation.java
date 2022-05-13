package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BellAnimation extends Transition {
  ImageView imageView;
  Image[] bells;
  int index;

  public BellAnimation(ImageView imageView, Image[] bells) {
    setCycleCount(17);
    index = 0;
    this.imageView = imageView;
    this.bells = bells;
    setCycleDuration(new Duration(59));
    setInterpolator(Interpolator.DISCRETE);
  }


  @Override
  protected void interpolate(double frac) {
    imageView.setImage(bells[index]);
    index++;
  }
}
