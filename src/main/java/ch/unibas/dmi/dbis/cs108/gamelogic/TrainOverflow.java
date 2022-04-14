package ch.unibas.dmi.dbis.cs108.gamelogic;

/**
 * An exception that is thrown, if for some reason to many clients want to start a game
 */
public class TrainOverflow extends Exception {

  private static final String message = "Too many users are logged on";

  @Override
  public String getMessage() {
    return message;
  }
}
