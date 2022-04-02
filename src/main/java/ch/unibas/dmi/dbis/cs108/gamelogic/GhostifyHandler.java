package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.GhostPlayer;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

public class GhostifyHandler {
  /**
   * Changes passenger at position x to ghost. Monitors the times the ghostify method is being
   * called. If it's being called for the first time, the ghostified player is being set as the
   * original ghost.
   *
   * @param p Passenger to be ghostified
   */
  private static int ghostifyCallCounter = 0;

  public void ghostify(Passenger p) {
    p.setGhost();
    if (ghostifyCallCounter == 0) {
      GhostPlayer g = new GhostPlayer(p.getPosition(), p.getName(), p.getClientHandler(), true);
    } else {
      GhostPlayer g = new GhostPlayer(p.getPosition(), p.getName(), p.getClientHandler(), false);
    }
    ghostifyCallCounter++;
  }
}
