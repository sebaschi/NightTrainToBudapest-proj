package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;

/**
 * This class is where the Protocol commands are saved as strings. The idea is that every class that
 * uses protocol messages does not directly use e.g. "CHATA" in the code but rather uses
 * Protocol.chatMsgToAll. This improves legibility as well as allowing for quick modification of
 * Protocol messages if needed. Most importantly, the Protocol commands can be documented in this
 * class, and then in every other class that uses protocol messages, documentation for a command can
 * be viewed by hovering over the specific variable, rather than having to document everywhere.
 */
public class Protocol {

  //GENERAL PROTOCOL RULES:
  /**
   * Protocol messages should always start with five characters (these are the
   * Strings listed here). If additional information is necessary, it should be
   * given after a dollar sign, as such: {@code PRTCL$information}.
   * It should be noted that the server simply ignores the 6th character, however
   * for clarity's sake it should always be a $. If more than one piece of information needs to be sent, the separate
   * pieces of information should also be delimited with a dollar sign (see whisper for an example). In that case, it
   * is very important that it is a $ and nothing else. Also, in such cases, we need to make sure the pieces of
   * information between the $ do not contain $ themselves (for example, usernames cannot contain $).
   */


  //BIDIRECTIONAL COMMANDS:

  /**
   * Ping-back message from client to server / server to client. To be sent upon receiving "CPING" /
   * "SPING". The other party then registers this in its ClientPinger / ServerPinger thread.
   */
  public static final String pingBack = "PINGB";


  //CLIENT TO SERVER COMMANDS:

  /**
   * When the server receives this, it broadcasts a chat message to all clients. The message has to
   * be given in the protocol message after {@code CHATA$}, for example the protocol message {@code
   * CHATA$Hello everybody!}, if sent from the user named Poirot, will print {@code Poirot: Hello
   * everybody!} to every connected client's chat console (note the absence / presence of spaces).
   */
  public static final String chatMsgToAll = "CHATA";

  /**
   * When the server receives this, it broadcasts a chat message to all clients in the same Lobby.
   * The message has to be given in the protocol message after {@code CHATL$}, for example the protocol message {@code
   * CHATL$Hello everybody!}, if sent from the user named Poirot, will print {@code Poirot: Hello
   * everybody!} to the chat console of every client in the lobby (note the absence / presence of spaces).
   * If the client is not in a lobby, the chat message will be sent to everyone not in a lobby.
   */
  public static final String chatMsgToLobby = "CHATL";

  /**
   * The message sent by the client on login to set their name. For example, {@code LOGIN$Poirot}
   * will use the clientHandler.setUsernameOnLogin() method to set this client's username to Poirot,
   * and broadcast the announcement: {@code "Poirot has joined the Server"}. Also, it will set this
   * clientHandler's loggedIn boolean to true, which could be used later to refuse access to users
   * who haven't formally logged in using this command //todo: shun non-logged-in users
   */
  public static final String clientLogin = "LOGIN";

  /**
   * Client requests their name to be changed to whatever follows {@code NAMEC$}. For example,
   * {@code NAMEC$Poirot} means the client wants to change their name to Poirot. However,
   * the server will first pass this name through nameDuplicateChecker.checkName() to adjust it
   * as needed (remove : and $ and add suffix in case of duplicate name.)
   */
  public static final String nameChange = "NAMEC";

  /**
   * Client sends ping message to server. If the client doesn't recieve a pingback from the
   * server, it shows a disconnection message and sets clientPinger.isConnected to false,
   * which can be implemented somehow later. As soon as a pingback message is received,
   * isConnected is set to true again and a reconnection message is printed.
   */
  public static final String pingFromClient = "CPING";

  /**
   * The client requests to quit. Once the server receives this message, it will send
   * a serverConfirmQuit message to the client to confirm the quitting, then close the
   * socket associated with that client and remove the clientHandler from the list of
   * clientHandlers.
   */
  public static final String clientQuitRequest = "QUITR";

  /**
   * Client sends this message when they want to create a new lobby (and automatically join it).
   * Client issues this command in {@link ch.unibas.dmi.dbis.cs108.multiplayer.client.MessageFormatter}
   * using "/g".
   * First a lobby {@link Lobby} is created of which the requesting client is the admin of.
   */
  public static final String createNewLobby = "CRTLB";

  /**
   * Represents a clients' request for a list of lobbies, plus what players are in them.
   */
  public static final String listLobbies = "LISTL";

  /**
   * Represents a clients' request for a list of all players within the lobby.
   */
  public static final String listPlayersInLobby = "LISTP";

  /**
   * Client requests to join the Lobby with the given number, for example,
   * {@code JOINL$2} means the client wants to join lobby 2.
   */
  public static final String joinLobby = "JOINL";

  /**
   * Client requests to leave whatever lobby they're in.
   */
  public static final String leaveLobby = "LEAVL";

  /**
   * Whisper chat. Syntax: {@code WHISP$recipient's username$message}
   */
  public static final String whisper = "WHISP";


  /**
   * A Client (lobby admin) decides to start the game. The game is started in the lobby the message came from.
   * Only one game can be started per lobby at a time.
   */
  public static final String startANewGame = "STGAM";

  /**
   * Client request to see a list of all games, ongoing and finished.
   */

  public static final String listGames = "LISTG";

  /**
   * Client informs server that they have voted and delivers this vote in the form of "CVOTE$position$vote"
    */
  public static final String votedFor = "CVOTE";

  /**
   * Client requests high score list.
   */
  public static final String highScoreList = "HSCOR";

  /**
   * The client requests that a message in {@code STACL$msg} is sent to all clients but only the message
   * without a specific Server message to be added.
   */
  public static final String sendMessageToAllClients = "STACL";




  //SERVER TO CLIENT COMMANDS

  /**
   * Server sends ping message to client. If the server doesn't recieve a pingback from the
   * client, it shows a disconnection message and sets serverPinger.isConnected to false,
   * which can be implemented somehow later. As soon as a pingback message is received,
   * isConnected is set to true again and a reconnection message is printed.
   */
  public static final String pingFromServer = "SPING";

  /**
   * prints out incoming announcements into the user's console. any string that
   * follows CONSM$ is printed as is, so the message that follows already has to be formatted the
   * way it should be shown to the client.
   */
  public static final String printToClientConsole = "CONSM";

  /**
   * prints out incoming chat messages into the user's chat. any string that
   * follows CHATM$ is printed as is, so the message that follows already has to be formatted the
   * way it should be shown to the client.
   */
  public static final String printToClientChat = "CHATM";


  /**
   * Server confirms the client's quit request, meaning that the client can now close its
   * sockets and quit.
   */
  public static final String serverConfirmQuit = "QUITC";

  /**
   * The server requests the client (who should be a ghost) to vote on the victim. in the format GVOTR$string
   * the current train will be shown as a string to the client
   */
  public static final String serverRequestsGhostVote = "GVOTR";

  /**
   * The server requests the client (who should be a human) to vote on who is a ghost /
   * who should be kicked off the train.
   */
  public static final String serverRequestsHumanVote = "HVOTR";

  /**
   * Informs Client that their username has been changed. Syntax {@code CHNAM$newname}
   */
  public static final String changedUserName = "CHNAM";

  /**
   * Handles all information that the gui of the client needs. The Form is {@code PTGUI$parameters$msg}
   * where the parameter tells the gui to do different things according to {@link GuiParameters} and the message
   * contains a certain information i.e. who is where in the train
   */
  public static final String printToGUI = "PTGUI";

  /**
   * Sends an information to client at which position in the train from the game (0 to 5) they sit, as soon as the game starts
   * {@code POSOF$position}
   */
  public static final String positionOfClient = "POSOF";


}
