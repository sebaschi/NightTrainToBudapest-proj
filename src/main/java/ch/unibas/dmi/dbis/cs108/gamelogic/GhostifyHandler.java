package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

public class GhostifyHandler {
    /**
     * Changes passenger at position x to ghost
     * @param p
     * Passenger to be ghostified
     */
    public void ghostify(Passenger p) {
        p.setGhost();
    }
}
