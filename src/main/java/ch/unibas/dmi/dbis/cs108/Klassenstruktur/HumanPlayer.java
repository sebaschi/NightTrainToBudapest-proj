package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Server.ClientHandler;

import java.net.Socket;

public class HumanPlayer extends Human{
    /**
     * Creates a new GhostPlayer. Should be used at game start or if a HumanPlayer is turned into a ghost.
     * @param position position on the train
     * @param name name. if null, then a default name is used.
     */
    public HumanPlayer(int position, String name, ClientHandler clientHandler, boolean isOG) {
        this.position = position;
        this.clientHandler = clientHandler;
        isGhost = false;
        isPlayer = true;
        kickedOff = false;
        if (name == null) {
            this.name = "Player Nr. " + position;
        } else this.name = name;
    }


}
