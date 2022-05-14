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
    setCycleCount(16);
    index = 0;
    this.imageView = imageView;
    this.bells = bells;
    setCycleDuration(new Duration(566.66));
    setInterpolator(Interpolator.DISCRETE);
  }


  @Override
  protected void interpolate(double frac) {
    if(index < 17) {
      imageView.setImage(bells[index]);
    }
    index++;
  }
}
