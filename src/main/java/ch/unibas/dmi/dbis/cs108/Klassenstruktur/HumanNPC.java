package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

public class HumanNPC extends Human {
    /**
     * Creates a new GhostNPC. Should be used at game start or if a HumanNPC is turned into a ghost.
     * @param position position on the train
     * @param name player name. if null, then a default name is used.
     *
     */
    public HumanNPC(int position, String name) {
        this.position = position;
        this.clientHandler = null;
        isGhost = false;
        isPlayer = false;
        kickedOff = false;
        if (name == null) {
            this.name = "Robot Nr. " + position;
        } else this.name = name;
    }
}
