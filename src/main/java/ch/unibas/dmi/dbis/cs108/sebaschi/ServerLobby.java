package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is just everyone on the server, which games are open to join, who is in session. I.E.
 * the context of the game just after joining the server, before starting a game and entering into a
 * lobby.
 */
public class ServerLobby {

  private static List<Lobby> openLobbies;
  private static List<ClientHandler> allClients;

  static {
    openLobbies = new ArrayList<>();
    allClients = new ArrayList<>();
  }

  public static List<Lobby> getOpenLobbies() {
    return openLobbies;
  }
}
