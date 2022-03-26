package ch.unibas.dmi.dbis.cs108.multiplayer.protocol;

public interface ProtocolDecoder {

    public NTtBFormatMsg decodeMsg(String msg);
}
