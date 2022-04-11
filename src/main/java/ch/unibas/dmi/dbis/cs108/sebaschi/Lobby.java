package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Use: If a client sends a CRTGM command the server should create a lobby with the client as admin.
 * In this state, up to 5 other clients (so 6 in total) are able to join this lobby. Once the admin
 * feels like it, he can start a game.
 * TODO: is all data in here or should GameSessionData be used to collect all relevant data?
 */
public class Lobby {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  public static HashSet<Lobby> lobbies = new HashSet<>();

  private static final int MAX_NO_OF_CLIENTS = 6;

  //TODO
  CentralServerData serverData;

  /**
   * The Person who created the game and can configure it and decide to start once enough lobbyClients
   * have entered the lobby.
   */
  private final ClientHandler admin;

  /**
   * Everyone who's in the lobby.
   */
  private HashSet<ClientHandler> lobbyClients = new HashSet<>(6);


  private int numberOfPlayersInLobby;

  private final int lobbyID;


  /**
   * Constructor. Sets the admin to who created the lobby. Adds the admin to the list of lobbyClients.
   * Increases the number of lobbyClients from 0 to 1.
   *
   * @param admin the Client who called CRTGM
   */
  public Lobby(ClientHandler admin) {
    this.admin = admin;
    this.lobbyClients.add(admin);
    this.numberOfPlayersInLobby = 1;
    lobbies.add(this);
    this.lobbyID = lobbies.size();
    admin.broadcastAnnouncement("New Lobby created by " + admin.getClientUserName() +
        ". This lobby's ID:  " + this.lobbyID);
  }

  /**
   * Getter
   *
   * @return the admin of the lobby.
   */
  public ClientHandler getAdmin() {
    return this.admin;
  }

  /**
   * getter for the lobby ID
   *
   * @return lobbyID as set in constructor based on number of lobbies.
   */
  public int getLobbyID() {
    return this.lobbyID;
  }

  /**
   * Returns the list containing lobbyClients currently in the lobby
   *
   * @return list of lobbyClients
   */
  public HashSet<ClientHandler> getLobbyClients() {
    return this.lobbyClients;
  }

  /**
   * Builds a message for the LISTL command.
   *
   * @return a string formatted for the clients convenients.
   */
  public String getIdAndAdminAndFormat() {
    StringBuilder response = new StringBuilder();
    response.append("Lobby ID: ");
    response.append(this.lobbyID);
    response.append(" Admin Username: ");
    response.append(getAdmin().getClientUserName());
    response.append(System.lineSeparator());
    LOGGER.info(response.toString());
    return response.toString();
  }

  /**
   * Returns the lobby with the desired LobbyID.
   * For example, getLobbyFromID(5) returns the lobby whose LobbyID is 5.
   * If no such lobby exists, it returns null.
   */
  public static Lobby getLobbyFromID(int i) {
    for (Lobby l: lobbies) {
      if (l.getLobbyID() == i) {
        return l;
      }
    }
    return null;
  }

  public static boolean clientIsInALobby(ClientHandler h) {
    for (Lobby l: lobbies) {
      for (ClientHandler clientHandler: l.getLobbyClients()) {
        if (h.equals(clientHandler)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Adds a player to the lobby.
   * TODO: add an appropriate response. Currently hardcoded.
   * TODO: Does this method need to be implemented somewhere else, e.g. in the ClientHandler?
   * //todo: Client can only join one Lobby.
   * @param player who wants to join the lobby.
   */
  public synchronized void addPlayer(ClientHandler player) {
    if (lobbyClients.size() < MAX_NO_OF_CLIENTS) {
      lobbyClients.add(player);
      numberOfPlayersInLobby++;
      LOGGER.debug(player.getClientUserName() + " has been added to Lobby with ID: " + lobbyID
          + ". Current number of lobbyClients in this lobby: " + lobbyClients.size());
    } else {
      LOGGER.debug(
          player.getClientUserName() + " could not be added to lobby. Number of lobbyClients in lobby: "
              + numberOfPlayersInLobby);
      //TODO: does this have to be formatted in any way to conform to protocol?
      player.sendMsgToClient(Protocol.printToClientConsole +
          "$The lobby is full. Please try joining a different lobby or create a new game");
    }
  }

  /**
   * Does what is says on the box. Needs to be called when a client disconnects for some reason and
   * cannot reconnect. I.E. when the server closes the connection to that client, the client should
   * be removed from the list.
   *
   * @param player that is to be removed
   * @return true if a player was found and removed. Used for debugging.
   */
  public synchronized boolean removePlayer(ClientHandler player) {
    return this.getLobbyClients().remove(player);
  }

  public void broadcastToLounge(String msg) {
    for (ClientHandler lounger : this.getLobbyClients()) {

    }
  }

}
