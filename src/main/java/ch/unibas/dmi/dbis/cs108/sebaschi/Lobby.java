package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.ArrayList;
import java.util.List;
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

  private static final int MAX_NO_OF_CLIENTS = 6;
  public static int lobbies;

  //TODO
  CentralServerData serverData;

  /**
   * The Person who created the game and can configure it and decide to start once enough players
   * have entered the lobby.
   */
  private final ClientHandler admin;

  /**
   * Everyone who's in the lobby.
   */
  private List<ClientHandler> players = new ArrayList<>(6);


  private int numberOfPlayersInLobby;
  //TODO maybe it makes sense to have LobbyID Class?
  private final int lobbyID = lobbies++;


  static {
    lobbies = 0;
  }


  /**
   * Constructor. Sets the admin to who created the lobby. Adds the admin to the list of players.
   * Increases the number of players from 0 to 1.
   *
   * @param admin the Client who called CRTGM
   */
  public Lobby(ClientHandler admin) {
    this.admin = admin;
    this.players.add(admin);
    this.numberOfPlayersInLobby = 1;
    lobbies++;
    LOGGER.debug("New Lobby created by " + admin.getClientUserName() + ". This lobby's ID:  "
        + this.lobbyID);
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
   * Adds a player to the lobby.
   * TODO: ad an appropriate response. Currently hardcoded.
   * TODO: Does this method need to implemented somewhere else, e.g. in the ClientHandler?
   * @param player who wants to join the lobby.
   */
  public void addPlayer(ClientHandler player) {
    if (players.size()  <= MAX_NO_OF_CLIENTS) {
      players.add(player);
      numberOfPlayersInLobby++;
      LOGGER.debug(player.getClientUserName() + " has been added to Lobby with ID: " + lobbyID
          + ". Current number of players in this lobby: " + players.size());
    } else {
      LOGGER.debug(
          player.getClientUserName() + " could not be added to lobby. No. of players in lobby: "
              + numberOfPlayersInLobby);
      //TODO: does this have to be formatted in any way to conform to protocol?
      player.sendMsgToClient(Protocol.printToClientConsole +
          "$The lobby is full. Please try joining a different lobby or create a new game");
    }
  }

}
