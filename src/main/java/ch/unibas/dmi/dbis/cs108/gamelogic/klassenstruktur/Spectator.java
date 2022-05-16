package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
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

  /**
   * Sends a message to the Client assosiated with this Spectator via ServerGameInfoHandler.spectatorFormat
   * @param msg the message that is sent to this player.
   * @param game the game the Passenger lives on
   */
  @Override
  public void send(String msg, Game game) {

    if (msg.equals(ClientGameInfoHandler.noiseNotification)) {
      clientHandler.sendMsgToClient(Protocol.noiseNotificationProtocol);
    } else {
      clientHandler.sendMsgToClient(ServerGameInfoHandler.spectatorFormat(msg, game));
    }
  }
}
