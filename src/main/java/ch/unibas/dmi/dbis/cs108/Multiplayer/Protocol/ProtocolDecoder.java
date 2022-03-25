package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

public interface ProtocolDecoder {

    public NTtBFormatMsg decodeMsg(String msg);
}
