package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
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
    p.setGhost();
    Ghost g;
    g = new Ghost(game);
    g.setGhost();
    g.setPosition(p.getPosition());
    game.gameFunctions.passengerTrain[g.getPosition()] = g;
    LOGGER.info("Passenger at position " + p.getPosition() + "has been ghostified");
    return g;
  }
}
