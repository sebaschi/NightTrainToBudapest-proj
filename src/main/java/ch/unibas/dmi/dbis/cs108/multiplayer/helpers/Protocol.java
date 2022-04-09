package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

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
   * for clarity's sake it should always be a $.
   * The client does not need to send who they are, since the server will receive
   * any client message on its dedicated clientHandler thread.
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
   * The message sent by the client on login to set their name. For example, {@code LOGIN$Poirot}
   * will use the clientHandler.setUsernameOnLogin() method to set this client's username to Poirot,
   * and broadcast the announcement: {@code "Poirot has joined the Server"}. Also, it will set this
   * clientHandler's loggedIn boolean to true, which could be used later to refuse access to users
   * who haven't formally logged in using this command => //todo: shun non-logged-in users
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
   * TODO: enable for client
   * TODO: add sever response
   * Client sends this message when he wants to create a new game.
   * Client issues this command in {@link ch.unibas.dmi.dbis.cs108.multiplayer.client.MessageFormatter}
   * using "/g".
   * First a lobby {@link ch.unibas.dmi.dbis.cs108.sebaschi.Lobby} is created of which the requesting client is the admin of.
   */
  public static final String createNewGame = "CRTGM";

  /**
   * TODO: implement in {@link ch.unibas.dmi.dbis.cs108.multiplayer.client.MessageFormatter}
   * TODO: imlement in {@link ch.unibas.dmi.dbis.cs108.multiplayer.server.JServerProtocolParser}
   * TODO: add the Servers reaction, i.e. sending a list of lobbies.
   * Represents a clients' request for a list of lobbies
   */
  public static final String listLobbies = "LISTL";

  /**
   * A Client decides to start the game.
   */
  public static final String startANewGame = "STGAM";

  /**
   * Client informs server that they have voted and delivers this vote in the form of "CVOTE$position$vote"
    */
  public static final String votedFor = "CVOTE";


  //SERVER TO CLIENT COMMANDS

  /**
   * Server sends ping message to client. If the server doesn't recieve a pingback from the
   * client, it shows a disconnection message and sets serverPinger.isConnected to false,
   * which can be implemented somehow later. As soon as a pingback message is received,
   * isConnected is set to true again and a reconnection message is printed.
   */
  public static final String pingFromServer = "SPING";

  /**
   * prints out incoming chat messages / announcements into the user's console. any string that
   * follows CHATM$ is printed as is, so the message that follows already has to be formatted the
   * way it should be shown to the client.
   */
  public static final String printToClientConsole = "CHATM";

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




}
