package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This Class Represents an Object containing different Maps, Lists and Sets wherein a server object
 * can find all needed data. An instance of this object can also be passed to other class-objects if
 * they need the same data. This Class is used to query for information in collections.
 */
public class CentralServerData {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  //TODO which datastructures should be used here?
  private Set<ClientHandler> clientsOnServer;
  private List<Lobby> allLobbies;
  private Map<Integer, Lobby> lobbyIDMap;

  public CentralServerData() {
    clientsOnServer = new HashSet<>();
    allLobbies = new ArrayList<>();
    lobbyIDMap = new HashMap<>();
  }

  //Getters

  /**
   * Getter for set of all clients.
   *
   * @return the set of all clients.
   */
  public Set<ClientHandler> getClientsOnServer() {
    return clientsOnServer;
  }

  /**
   * Used to add the client to the set of all clients on server.
   *
   * @param client
   */
  public synchronized void addClientToSetOfAllClients(ClientHandler client) {
    this.getClientsOnServer().add(client);
  }

  /**
   * Remove a client from the set of clients. Used in ClientHandler.disconnectClient().
   *
   * @param client to be removed.
   */
  public synchronized void removeClientFromSetOfAllClients(ClientHandler client) {
    //TODO implement or make sure something equivalent is implemented somewhere else
    this.getClientsOnServer().remove(client);
    LOGGER.debug(client.getClientUserName() + " removed from CentralServerData list of clients.");
  }

  /**
   * Getter for List of all lobbies.
   *
   * @return a list of all lobbies
   */
  public List<Lobby> getAllLobbies() {
    return allLobbies;
  }

  /**
   * Does exactly what it says on the box.
   *
   * @param lobby
   */
  public synchronized void addLobbyToListOfAllLobbies(Lobby lobby) {
    allLobbies.add(lobby);
  }

  /**
   * Mapping from an Integer that repesents a LobbyID to the lobby should be set in {@link Lobby}
   * and is then used by clients to join a lobby.
   *
   * @return a mapping from Integer to Lobby.
   */
  public Map<Integer, Lobby> getLobbyIDMap() {
    return lobbyIDMap;
  }
}
