package ch.unibas.dmi.dbis.cs108.multiplayer.helpers;

/**
 * This class is where the Protocol commands are saved as strings.
 * The idea is that every class that uses protocol messages does not
 * directly use e.g. "CHATA" in the code but rather uses
 * Protocol.chatMsgToAll. This improves legibility as well as
 * allowing for quick modification of Protocol messages if needed.
 * Most importantly, the Protocol commands can be documented in this
 * class, and then in every other class that uses protocol messages,
 * documentation for a command can be viewed by hovering over the
 * specific variable, rather than having to document everywhere.
 */
public class Protocol {

  //BIDIRECTIONAL COMMANDS:

  /**
   * Ping-back message from client to server / server to client.
   * To be sent upon receiving "CPING" / "SPING".
   * The other party then registers this in its ClientPinger / ServerPinger
   * thread.
   */
  public static final String pingBack = "PINGB";


  //CLIENT TO SERVER COMMANDS:

  /**
   * When the server receives this, it broadcasts a chat message
   * to all clients. The message has to be given in the protocol
   * message after {@code CHATA$}, for example the protocol message
   * {@code CHATA$Hello everybody!}, if sent from the user named Poirot,
   * will print {@code Poirot: Hello everybody!} to every connected
   * client's chat console (note the absence / presence of spaces).
   */
  public static final String chatMsgToAll = "CHATA";

  /**
   * The message sent by the client on login to set their name. For example,
   * {@code LOGIN$Poirot} will use the clientHandler.setUsernameOnLogin() method
   * to set this client's username to Poirot, and broadcast the announcement:
   * {@code "Poirot has joined the Server"}. Also, it will set this clientHandler's
   * loggedIn boolean to true, which could be used later to refuse access to users
   * who haven't formally logged in using this command => //todo: shun non-logged-in users
   *
   */
  public static final String clientLogin = "LOGIN";

  /**
   * todo:doc
   *
   */
  public static final String nameChange = "NAMEC";

  /**
   * todo:doc
   */
  public static final String pingFromClient = "CPING";

  /**
   * todo: doc
   */
  public static final String clientQuitRequest = "QUITS";

}
