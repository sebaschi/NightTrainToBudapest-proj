package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

/**
 * This class contains all parameters for the PTGUI protocol message
 */
public class GuiParameters {

  /**
   * Tells GUI to update the gameStateModel, in the form {@code UPDATE$name:role:kickedOff$name:role:kickedOff}
   * ... usw.
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
   * Tells Gui, that the passenger at position {@code position} has heard some noise Form: {@code
   * NOISE$position$}
   */
  public static final String noiseHeardAtPosition = "NOISE";

  /**
   * Tells Gui, that the start view should be displayed
   */
  public static final String viewChangeToStart = "VCSTART";

  /**
   * Tells Gui, that the lobby view should be displayed
   */
  public static final String viewChangeToLobby = "VCLOBBY";

  /**
   * Tells Gui, that the game view should be displayed
   */
  public static final String viewChangeToGame = "VCGAME";


  /**
   * Tells Gui, who the members of a specified Lobby are. Form: {@code LMEMBS$<lobbyID>:<ADMIN
   * NAME>:<member names>:<..>}
   */
  public static final String getMembersInLobby = "LMEMBS";


  /**
   * Informs the GUI, that a vote is over
   */
  public static final String VoteIsOver = "VOTEOVER";

  /**
   * Informes Gui, that its the night
   */
  public static final String night = "NIGHT";
  /**
   * Informes Gui, that its the day
   */
  public static final String day = "DAY";

  public static final String newLobbyCreated = "NLOBBY";

  /**
   * Tells Gui, to add a player to a lobby. Form: {@code NMEMB$<LobbyIS>:<PlayerNamse>}
   */
  public static final String addNewMemberToLobby = "NMEMB";

  /**
   * Indicates a player changed their username. Form: {@code NCHANG$<oldName>:<newName>}
   */
  public static final String nameChanged = "NCHANG";

  /**
   * Indicates a player has joined the server. Form: {@code NPLOS$<playerName>}
   */
  public static final String newPlayerOnServer = "NPLOS";

  /**
   * Tells gui to remove a certain player from the list of clients based on user name. Form: {@code
   * RMVLST$<playerName>}
   */
  public static final String removePlayerFromList = "RMVLST";

  /**
   * Tells Gui to update its HighScore TextFlow according to the following data
   */
  public static final String updateHighScore = "HISCR";

  /**
   * Tells Gui to add a String to printLobby TextFlow - a provisory solution in case ListeView won't
   * pan out
   */
  public static final String updatePrintLobby = "PRLOBB";

  /**
   * Tells gui to remove a lobby from view. Form: {@code RMVLBY$<LobbyID>}
   */
  public static final String removeLobby = "RMVLBY";
  /**
   * Tells the GUI at which position you are sitting {@code POSITION$integer}
   */
  public static final String yourPosition = "POSITION";
}
