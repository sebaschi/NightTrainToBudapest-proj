package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.GuiParameters;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.ServerPinger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class creates a thread that constantly sends a String to the Clients containing the Lobbies
 */
public class LobbyUpdater implements Runnable{
  public static final Logger LOGGER = LogManager.getLogger(LobbyUpdater.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        LOGGER.warn(e.getMessage());
      }
      String lobbiesAsString = Lobby.lobbiesToString();
      for (ClientHandler client : ClientHandler.getConnectedClients()) {
        if (!Lobby.lobbies.isEmpty()) {
          client.sendMsgToClient(
              Protocol.printToGUI + "$" + GuiParameters.updateLobbyString + "$" + lobbiesAsString);
        }
      }
    }
  }
}
