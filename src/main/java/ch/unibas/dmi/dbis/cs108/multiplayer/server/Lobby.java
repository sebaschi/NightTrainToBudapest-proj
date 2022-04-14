package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;

import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Use: If a client sends a CRTLB command the server should create a lobby with the client as admin.
 * In this state, up to 5 other clients (so 6 in total) are able to join this lobby. Once the admin
 * feels like it, they can start a game.
 */
public class Lobby {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  public static HashSet<Lobby> lobbies = new HashSet<>();

  private static final int MAX_NO_OF_CLIENTS = 6;


  /**
   * The Person who created the game and can configure it and decide to start once enough lobbyClients
   * have entered the lobby.
   */
  private final ClientHandler admin;

  /**
   * Everyone who's in the lobby.
   */
  private HashSet<ClientHandler> lobbyClients = new HashSet<>(6);

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
    lobbies.add(this);
    int helper = 1;
    while (getLobbyFromID(helper) != null) {
      helper++;
    }
    this.lobbyID = helper;
    ClientHandler.broadcastAnnouncementToAll("New Lobby created by " + admin.getClientUserName() +
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
   * @return lobbyID as set in constructor.
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

  /**
   * Returns the ID of the lobby that the client is in. If the client is not in any
   * lobby, it returns -1.
   */
  public static int clientIsInLobby(ClientHandler h) {
    for (Lobby l: lobbies) {
      for (ClientHandler clientHandler: l.getLobbyClients()) {
        if (h.equals(clientHandler)) {
          return l.getLobbyID();
        }
      }
    }
    return -1;
  }

  /**
   * Adds a player to the lobby. Returns true if successful.
   * TODO: add an appropriate response. Currently hardcoded.
   * @param client who wants to join the lobby.
   */
  public synchronized boolean addPlayer(ClientHandler client) {
    if (lobbyClients.size() < MAX_NO_OF_CLIENTS) {
      //todo: check that game hasn't started yet
      if (clientIsInLobby(client) == -1) {
        lobbyClients.add(client);
        ClientHandler.broadcastAnnouncementToAll(client.getClientUserName() + " has joined lobby " + this.getLobbyID());
        //LOGGER.debug(client.getClientUserName() + " has been added to Lobby with ID: " + lobbyID
        //    + ". Current number of lobbyClients in this lobby: " + lobbyClients.size());
        return true;
      } else {
        client.sendAnnouncementToClient("You are already in lobby nr. " + clientIsInLobby(client));
      }
    } else {
      client.sendAnnouncementToClient("This lobby is full. Please try joining a different lobby or create a new lobby");
    }
    return false;
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
    //if the player who leaves the lobby is the admin, the lobby is closed.
    if (player.equals(getAdmin())) {
      ClientHandler.broadcastAnnouncementToAll(player.getClientUserName() + " has closed lobby nr. " + this.getLobbyID());
      closeLobby();
    } else if (this.getLobbyClients().remove(player)){
      ClientHandler.broadcastAnnouncementToAll(player.getClientUserName() + " has left lobby nr. " + this.getLobbyID());
      return true;
    }
    return false;
  }

  /**
   * Closes the lobby.
   *
   */
  public void closeLobby() {
    lobbies.remove(this);
    ClientHandler.broadcastAnnouncementToAll("Lobby nr. " + this.getLobbyID() + " has been closed.");
    /*
    Todo: theoretically, this is enough to close a lobby.
     ClientHandlers dont have to manually be removed from the lobby
     since if the lobby is removed from the lobbies
     hash set then e.g. clientIsInLobby will not be able to tell that these clients
     are theoretically still in this lobby. However, in the future we might implement
     removing the clients anyways.
     */
  }

}
