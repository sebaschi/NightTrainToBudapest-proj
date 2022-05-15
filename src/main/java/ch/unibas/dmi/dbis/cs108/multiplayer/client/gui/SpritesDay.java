package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import javafx.scene.image.Image;

public class SpritesDay {
  private static final String path = "ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/Day/";

  public static final Image bg = new Image(path + "BG_small.jpg");
  public static final Image shadow = new Image(path + "Shadow_Below_Train.png");
  public static final Image secondWagon = new Image(path + "Second_Wagon.png");
  public static final Image emptyWagon = new Image(path + "Empty_Wagon.png");
  public static final Image room0 = new Image(path + "Room1.png");
  public static final Image room1 = new Image(path + "Room2.png");
  public static final Image room2 = new Image(path + "Room3.png");
  public static final Image room3 = new Image(path + "Room4.png");
  public static final Image room4 = new Image(path + "Room5.png");
  public static final Image room5 = new Image(path + "Room6.png");
  public static final Image room0Spectator = new Image(path + "Room1_Spectator.png");
  public static final Image room1Spectator = new Image(path + "Room2_Spectator.png");
  public static final Image room2Spectator = new Image(path + "Room3_Spectator.png");
  public static final Image room3Spectator = new Image(path + "Room4_Spectator.png");
  public static final Image room4Spectator = new Image(path + "Room5_Spectator.png");
  public static final Image room5Spectator = new Image(path + "Room6_Spectator.png");
  public static final Image emptyWagonWall = new Image(path + "Empty_Wagon_Wall.png");
  public static final Image fullWagon = new Image(path + "Full_Wagon.png");
  public static final Image loki = new Image(path + "Loki.png");
  public static final Image[] wheels = new Image[26];
  public static final Image[] bells = new Image[17];
  public static final Image fg = new Image(path + "Foreground_small.png");
  public static final Image crop = new Image("ch/unibas/dmi/dbis/cs108/multiplayer/client/gui/game/background_crop.png");

  public static void setWheels() {
    try {
      for (int i = 1; i <= 26; i++) {
        String url;
        if (i < 10) {
          url =
              path + "Wheels/Image000" + i + ".png";
        } else {
          url = path + "Wheels/Image00" + i + ".png";
        }
        wheels[i-1] = new Image(url);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void setBells() {
    try {
      for (int i = 1; i <= 17; i++) {
        String url;
        if (i < 10) {
          url =
              path + "Bell/Image000" + i + ".png";
        } else {
          url = path + "Bell/Image00" + i + ".png";
        }
        bells[i-1] = new Image(url);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
