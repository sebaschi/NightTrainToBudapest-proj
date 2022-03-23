package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NTtBFormatMsg;

public interface ProtocolParser {

    public NTtBFormatMsg parseMsg(String msg);
}
