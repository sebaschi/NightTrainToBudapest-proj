package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;

/**
 * The Lobby one is in after a client sends the CRTGM command. THe Server
 */
public class Lobby {

  /**
   * The Person who created the game and can configure it and decide to start once enough players
   * have entered the lobby.
   */
  ClientHandler admin;

}
