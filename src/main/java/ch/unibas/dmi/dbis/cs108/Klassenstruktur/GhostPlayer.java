package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

import java.net.Socket;

public class GhostPlayer extends Ghost{

    /**
     * Creates a new GhostPlayer. Should be used at game start or if a HumanPlayer is turned into a ghost.
     * @param position position on the train
     * @param name name. if null, then a default name is used.
     * @param sock the socket for the player.
     * @param isOG true if the ghost is the original ghost.
     */
    public GhostPlayer(int position, String name, Socket sock, boolean isOG) {
        this.position = position;
        this.sock = sock;
        this.isOG = isOG;
        isGhost = true;
        isPlayer = true;
        kickedOff = false;
        if (name == null) {
            this.name = "Human Nr. " + position;
        } else this.name = name;
    }

    public void send(String msg) {
        //todo: pass message along to client.
    }
}
