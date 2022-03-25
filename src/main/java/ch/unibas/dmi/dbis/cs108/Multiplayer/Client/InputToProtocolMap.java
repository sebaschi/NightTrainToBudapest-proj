package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NightTrainProtocol;

import java.util.HashMap;

public class InputToProtocolMap extends HashMap<String, NightTrainProtocol.NTtBCommands> {
    public InputToProtocolMap(){
        super();
        this.put("chat", NightTrainProtocol.NTtBCommands.CHATA);
        this.put("cn", NightTrainProtocol.NTtBCommands.CUSRN);
        this.put("list", NightTrainProtocol.NTtBCommands.LISTP);
        this.put("exit", NightTrainProtocol.NTtBCommands.LEAVG);
        //TODO extend according to extended function
    }
}
