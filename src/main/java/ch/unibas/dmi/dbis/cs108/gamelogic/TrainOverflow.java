package ch.unibas.dmi.dbis.cs108.gamelogic;

public class TrainOverflow extends Exception {

  private static final String message = "Too many users are logged on";

  @Override
  public String getMessage() {
    return message;
  }
}