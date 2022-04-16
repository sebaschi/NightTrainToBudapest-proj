package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class for all Players that have been kicked off, where they can observe everything
 */
public class Spectator extends Passenger{
  public static final Logger LOGGER = LogManager.getLogger(Spectator.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  public Spectator(int position, String name) {
    this.position = position;
    this.name = name;
    isGhost = false;
    isPlayer = true;
    kickedOff = true;
    isSpectator = true;
  }


  @Override
  public void send(String msg, Game game) {
    clientHandler.sendMsgToClient(ServerGameInfoHandler.spectatorFormat(msg, this, game));
  }
}
