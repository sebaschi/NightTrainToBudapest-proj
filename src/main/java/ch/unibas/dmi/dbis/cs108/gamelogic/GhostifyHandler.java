package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Spectator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostifyHandler {
  public static final Logger LOGGER = LogManager.getLogger(GhostifyHandler.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  /**
   * Changes passenger at position x to ghost and returns this ghost. Monitors the times the ghost method is being
   * called. If it's being called for the first time, the ghostified player is being set as the original ghost.
   *
   * @param p Passenger to be ghostified
   */

  public static Ghost ghost(Passenger p, Game game) {
    LOGGER.debug("Passenger Position " + p.getPosition());
    Ghost g;
    if (p.getIsPlayer()) {
      p.setGhost();
      GhostPlayer ghostPlayer;
      ghostPlayer = new GhostPlayer(p.getPosition(), p.getName(), p.getClientHandler(),p.getIsOG());
      ghostPlayer.setGhost();
      ghostPlayer.setPosition(p.getPosition());
      g = ghostPlayer;
    } else {
      p.setGhost();
      GhostNPC ghostNPC;
      ghostNPC = new GhostNPC(p.getPosition(), p.getName(),p.getIsOG());
      ghostNPC.setGhost();
      ghostNPC.setPosition(p.getPosition());
      g = ghostNPC;

    }
    game.gameState.addNewPassenger(g, g.getPosition());
    LOGGER.info("Passenger at position " + p.getPosition() + " has been ghostified");
    return g;
  }

  /**
   * Handles a Kick Off event. If the passenger to be kicked off is a Player, the Player will be converted
   * into a Spectator to watch the game.
   * @param passenger to be kicked off the train
   * @param game the game the train and passenger are in
   * @return either a new spectator or the oly passenger now with kickeckedOff true
   */
  public static Passenger kickOff (Passenger passenger, Game game) {
    if (passenger.getIsPlayer()) {
      Spectator spectator = new Spectator(passenger.getPosition(), passenger.getName());
      spectator.setClientHandler(passenger.getClientHandler());
      spectator.setPlayer(true);
      game.gameState.addNewPassenger(spectator, passenger.getPosition());
      return spectator;
    } else {
      passenger.setKickedOff(true);
      return passenger;
    }
  }
}
