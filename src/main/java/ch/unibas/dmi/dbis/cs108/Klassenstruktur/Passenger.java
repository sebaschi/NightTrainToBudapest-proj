package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

import java.net.Socket;

public class Passenger {
    protected int position;               //the player's Cabin number (1 to 6)
    protected String name;                //the player's Name
    protected Boolean isGhost;            //boolean regarding if the player is a ghost. Could probably be removed since ghost is a subclass but I'm keeping it in.
    protected Boolean isPlayer;           //same here
    protected Boolean kickedOff;          //true if the player has been voted off.
    protected Socket sock;                //the socket for the client associated with this Passenger, for NPCs, this can be null.
    //todo: maybe this should be a thread or some class instead of a socket? depends on client-server structure...


    /**
     * Sends a protocol message to the respective player.
     * @param msg the message that is sent to this player.
    **/
    public void send(String msg) {
        //todo: send protocol message to the respective client OR process messages for NPCS
    }

    /**
     * sets the name of this passenger.
     * @param name the new name for this passenger.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the kickedOff status of this Passenger
     * @param kickedOff should be set to true if the passenger has been kicked off.
     */
    public void setKickedOff(boolean kickedOff) {
        this.kickedOff = kickedOff;
    }

    public int getPosition() {
        return position;
    }


    public String getName() {
        return name;
    }

    public Boolean getIsGhost() {
        return isGhost;
    }

    public Boolean getKickedOff() {
        return kickedOff;
    }

    public Boolean getIsPlayer() {
        return isPlayer;
    }
}
