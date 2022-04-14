package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostNPC;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostifyHandler {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  /**
   * Changes passenger at position x to ghost and returns this ghost. Monitors the times the ghost method is being
   * called. If it's being called for the first time, the ghostified player is being set as the original ghost.
   *
   * @param p Passenger to be ghostified
   */

  public Ghost ghost(Passenger p, Game game) { //TODO: Adjust for not only players but also npcs
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
}
