package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import java.net.URL;
import javafx.scene.image.Image;

/**
 * This class contains all Sprites as images to be accessed by other classes, this way the loading only happens once and the handling
 * of day to night sprites changes can be simplified
 */
public class Sprites {

  private static Image bg;
  private static Image shadow;
  private static Image secondWagon;
  private static Image emptyWagon;
  private static Image[] rooms = new Image[6];
  private static Image emptyWagonWall;
  private static Image fullWagon;
  private static Image loki;
  private static Image[] wheels = new Image[26];
  private static Image[] bells = new Image[17];
  private static Image fg;
  private static Image crop;

  public static Image getBg() {
    return bg;
  }

  public static Image getShadow() {
    return shadow;
  }

  public static Image getSecondWagon() {
    return secondWagon;
  }

  public static Image getEmptyWagon() {
    return emptyWagon;
  }

  public static Image[] getRooms() {
    return rooms;
  }

  public static Image getARoom(int position) {
    return rooms[position];
  }

  public static Image getEmptyWagonWall() {
    return emptyWagonWall;
  }

  public static Image getFullWagon() {
    return fullWagon;
  }

  public static Image getLoki() {
    return loki;
  }

  public static Image[] getWheels() {
    return wheels;
  }

  public static Image[] getBells() {
    return bells;
  }

  public static Image getFg() {
    return fg;
  }

  public static Image getCrop() {
    return crop;
  }

  /**
   * Sets all Images of this class to the Day Version
   * @param roles a String containing s/h/g
   * @param kickedOff a boolean containing true for every kicked off passenger
   */
  public static void setDaySprites(String[] roles, boolean[] kickedOff) {
    try {
      bg = SpritesDay.bg;
      shadow = SpritesDay.shadow;
      secondWagon = SpritesDay.secondWagon;
      emptyWagon = SpritesDay.emptyWagon;
      updateDayRoomSprites(roles,kickedOff);
      emptyWagonWall = SpritesDay.emptyWagonWall;
      fullWagon = SpritesDay.fullWagon;
      loki = SpritesDay.loki;
      wheels = SpritesDay.wheels;
      bells = SpritesDay.bells;
      fg = SpritesDay.fg;
      crop = SpritesDay.crop;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
  public static void updateDayRoomSprites(String[] roles, boolean[] kickedOff) {
    for (int i = 0; i < roles.length; i++) {
      rooms[i] = getRoomDay(i, roles[i], kickedOff);
    }
  }

  /**
   * Sets all Images of this class to the Night version, takes into account which room is a ghost
   * Room and which isn't
   *
   * @param roles a String array containing the roles of the Passengers (g/h/s) from left to right
   * @param kickedOff a boolean  array containing the kickedOff value for each passenger
   */
  public static void setNightSprites(String[] roles, boolean[] kickedOff) {
    bg = SpritesNight.bg;
    shadow = SpritesNight.shadow;
    secondWagon = SpritesNight.secondWagon;
    emptyWagon = SpritesNight.emptyWagon;
    updateNightRoomSprites(roles,kickedOff);
    emptyWagonWall = SpritesNight.emptyWagonWall;
    fullWagon = SpritesNight.fullWagon;
    loki = SpritesNight.loki;
    wheels = SpritesNight.wheels;
    bells = SpritesNight.bells;
    fg = SpritesNight.fg;
    crop = SpritesNight.crop;
  }

  public static void updateNightRoomSprites(String[] roles, boolean[] kickedOff) {
    for (int i = 0; i < roles.length; i++) {
      rooms[i] = getRoomNight(i, roles[i], kickedOff);
    }
  }

  /**
   * Returns a room Image from SpritesDay, corresponding to the position and the role of the passenger in that room
   * @param position the position of the room integer 0-5
   * @param suffix the role of the passenger eiter (h/s)
   * @param kickedOff a boolean  array containing the kickedOff value for each passenger
   * @return the Image if parameters are passable otherwise null (ie position = 6)
   */
  public static Image getRoomDay(int position, String suffix, boolean[] kickedOff) {
    switch (position) {
      case 0:
        if (kickedOff[0]) {
          return SpritesDay.room0Spectator;
        }
        return SpritesDay.room0;
      case 1:
        if (kickedOff[1]) {
          return SpritesDay.room1Spectator;
        }
        return SpritesDay.room1;
      case 2:
        if (kickedOff[2]) {
          return SpritesDay.room2Spectator;
        }
        return SpritesDay.room2;
      case 3:
        if (kickedOff[3]) {
          return SpritesDay.room3Spectator;
        }
        return SpritesDay.room3;
      case 4:
        if (kickedOff[4]) {
          return SpritesDay.room4Spectator;
        }
        return SpritesDay.room4;
      case 5:
        if (kickedOff[5]) {
          return SpritesDay.room5Spectator;
        }
        return SpritesDay.room5;
      default:
        return null;
    }
  }

  /**
   * Returns a room Image from SpritesNight, corresponding to the position and the role of the passenger in that room
   * @param position the position of the room integer 0-5
   * @param suffix the role of the passenger eiter (h/s/g)
   * @param kickedOff a boolean array containing if passenger at position index is kicked off
   * @return the Image if parameters are passable otherwise null (ie position = 6)
   */
  public static Image getRoomNight(int position, String suffix, boolean[] kickedOff) {
    switch (position) {
      case 0:
        if (kickedOff[0]) {
          return SpritesNight.room0Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room0LightsOn;
        }
        return SpritesNight.room0;
      case 1:
        if (kickedOff[1]) {
          return SpritesNight.room1Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room1LightsOn;
        }
        return SpritesNight.room1;
      case 2:
        if (kickedOff[2]) {
          return SpritesNight.room2Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room2LightsOn;
        }
        return SpritesNight.room2;
      case 3:
        if (kickedOff[3]) {
          return SpritesNight.room3Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room3LightsOn;
        }
        return SpritesNight.room3;
      case 4:
        if (kickedOff[4]) {
          return SpritesNight.room4Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room4LightsOn;
        }
        return SpritesNight.room4;
      case 5:
        if (kickedOff[5]) {
          return SpritesNight.room5Spectator;
        } else if ("g".equals(suffix)) {
          return SpritesNight.room5LightsOn;
        }
        return SpritesNight.room5;
      default:
        return null;
    }
  }
}
