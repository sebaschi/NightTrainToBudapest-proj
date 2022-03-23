package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

public abstract class NTtBFormatMsg extends ProtocolMessage{

    private String message;

    public NTtBFormatMsg(String msg) {
        this.message = msg;
    }

    public boolean isCorrectlyFormatted(String msg){ return false;}
    public String getMessage(){return this.message;}
}
