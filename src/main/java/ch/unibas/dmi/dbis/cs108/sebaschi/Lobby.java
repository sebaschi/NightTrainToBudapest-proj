package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Lobby one is in after a client sends the CRTGM command. THe Server
 */
public class Lobby {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private static final int MAX_NO_OF_CLIENTS = 6;
  private static int lobbies;

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
   *
   * @param player who wants to join the lobby.
   */
  public void addPlayer(ClientHandler player) {
    if (players.size() != 6) {
      players.add(player);
      numberOfPlayersInLobby++;
      LOGGER.debug(player.getClientUserName() + " has been added to Lobby with ID: " + lobbyID
          + ". Current number of players in this lobby: " + players.size());
    } else {

    }
  }

}
