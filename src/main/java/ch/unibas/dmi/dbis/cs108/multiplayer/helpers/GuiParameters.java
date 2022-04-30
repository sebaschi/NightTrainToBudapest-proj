package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

/**
 * This class contains all parameters for the PTGUI protocol message
 */
public class GuiParameters {

  /**
   * Tells GUI to update the gameStateModel, in the form {@code UPDATE$name:role:kickedOff$name:role:kickedOff} ... usw.
   */
  public static final String updateGameState = "UPDATE";
  /**
   * Tells Gui, that the following statement after $, is a String containing the listOfLobbies
   */
  public static final String listOfLobbies = "LOBBIES";

  /**
   * Tells Gui, that what follows is a list of players (per Lobby?)
   */
  public static final String listOfPLayers = "PLAYERS";

  /**
   * Tells Gui, that the passenger at position {@code position} has heard some noise
   * Form: {@code NOISE$position$}
   */
  public static final String noiseHeardAtPosition = "NOISE";


  /**
   * Tells, Gui, who the members of a specified Lobby are.
   * Form: {@code LMEMBS$<lobbyID>$<member names>$..}
   */
  public static String viewChangeToLobby = "LMEMBS";
}
