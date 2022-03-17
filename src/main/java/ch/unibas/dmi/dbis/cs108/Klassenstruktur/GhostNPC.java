package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

public class GhostNPC extends Ghost{

    public GhostNPC(int position, String name) {
        this.position = position;
        isGhost = true;
        isPlayerCharacter = false;
        kickedOff = false;
        if (name == null) {
            this.name = "Robot Nr. " + position;
        } else this.name = name;
    }
}
