package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * The Lobby one is in after a client sends the CRTGM command. THe Server
 */
public class Lobby {

  /**
   * The Person who created the game and can configure it and decide to start once enough players
   * have entered the lobby.
   */
  private final ClientHandler admin;

  /**
   * Everyone who's in the lobby.
   */
  private List<ClientHandler> players = new ArrayList<>();

  private static final int MAX_NO_OF_CLIENTS = 6;







  /**
   * The admin has to be set in the constructor. The admin is final.
   * Every Lobby needs and admin, so no other constructors are needed.
   * @param admin the Client who called CRTGM
   */
  public Lobby(ClientHandler admin) {
    this.admin = admin;
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
   * @param player who wants to join the lobby.
   */
  public void addPlayer(ClientHandler player) {
    players.add(player);
  }

}
