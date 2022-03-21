package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

import java.net.Socket;

public class HumanPlayer extends Human{
    /**
     * Creates a new GhostPlayer. Should be used at game start or if a HumanPlayer is turned into a ghost.
     * @param position position on the train
     * @param name name. if null, then a default name is used.
     * @param sock the socket for the player.
     */
    public HumanPlayer(int position, String name, Socket sock, boolean isOG) {
        this.position = position;
        this.sock = sock;
        isGhost = false;
        isPlayer = true;
        kickedOff = false;
        if (name == null) {
            this.name = "Player Nr. " + position;
        } else this.name = name;
    }


}
