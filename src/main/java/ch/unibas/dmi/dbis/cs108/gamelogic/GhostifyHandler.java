package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

public class GhostifyHandler {
  /**
   * Changes passenger at position x to ghost and returns this ghost. Monitors the times the ghost method is being
   * called. If it's being called for the first time, the ghostified player is being set as the original ghost.
   *
   * @param p Passenger to be ghostified
   */
  private static int ghostifyCallCounter = -1;

  public GhostPlayer ghost(Passenger p, Game game) {
    p.setGhost();
    GhostPlayer g;
    ghostifyCallCounter++;
    if (ghostifyCallCounter == 0) {
      g = new GhostPlayer(p.getPosition(), p.getName(), p.getClientHandler(), true);
    } else {
      g = new GhostPlayer(p.getPosition(), p.getName(), p.getClientHandler(), false);
    }
    game.gameFunctions.passengerTrain[g.getPosition()] = g;
    return g;
  }
}
