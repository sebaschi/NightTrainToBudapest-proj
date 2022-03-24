package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NTtBCommands;

import java.util.HashMap;

public class InputToProtocolMap extends HashMap<String, NTtBCommands> {
    public InputToProtocolMap(){
        super();
        this.put("chat", NTtBCommands.CHATA);
        this.put("cn", NTtBCommands.CUSRN);
        this.put("list", NTtBCommands.LISTP);
        this.put("exit", NTtBCommands.LEAVG);
        //TODO extend according to extended function
    }
}
