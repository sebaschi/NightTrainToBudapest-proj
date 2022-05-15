package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BGAnimation extends Transition {
  private ImageView bgView;
  private ImageView fgView;
  private int index;
  private int lastIndex;

  public BGAnimation(Duration duration, ImageView bgView, ImageView fgView) {
    index = 0;
    lastIndex = 1034;
    this.bgView = bgView;
    this.fgView = fgView;
    bgView.setFitHeight(1950);
    bgView.setFitWidth(6667.968);
    bgView.setImage(Sprites.getBg());
    fgView.setFitHeight(1950);
    fgView.setFitWidth(6667.968);
    fgView.setImage(Sprites.getFg());

    setCycleDuration(duration);
    setInterpolator(Interpolator.DISCRETE);

  }

  @Override
  protected void interpolate(double frac) {
    bgView.setImage(Sprites.getBg());
    fgView.setImage(Sprites.getFg());
    if(index == lastIndex) {
      index = 0;
      bgView.setX(0);
      fgView.setX(0);
      bgView.setY(0);
      fgView.setY(0);
    }
    bgView.setX(index * -5);
    fgView.setX(index * -5);
    bgView.setY(index * -1.07);
    fgView.setY(index * -1.07);
    index++;

  }
}
