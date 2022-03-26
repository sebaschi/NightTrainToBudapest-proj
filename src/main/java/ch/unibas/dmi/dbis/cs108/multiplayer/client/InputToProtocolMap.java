package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NoLegalProtocolCommandStringFoundException;

import java.util.HashMap;
import java.util.HashSet;

public class InputToProtocolMap {

    private static final HashMap<String, NightTrainProtocol.NTtBCommands> encoding;
    private static final HashSet<String> legalClientInput;

    static {
        //First add all legal commands to a map
        HashMap<String, NightTrainProtocol.NTtBCommands> builder = new HashMap<>();
        builder.put("chat", NightTrainProtocol.NTtBCommands.CHATA);
        builder.put("cn", NightTrainProtocol.NTtBCommands.CUSRN);
        builder.put("list", NightTrainProtocol.NTtBCommands.LISTP);
        builder.put("exit", NightTrainProtocol.NTtBCommands.LEAVG);
        //TODO extend according to extended function
        //Initialize static final map and set
        legalClientInput = new HashSet<>(builder.keySet());
        encoding = new HashMap<>(builder);
    }

    public static String encode(String toEncode) throws NoLegalProtocolCommandStringFoundException {
        if (legalClientInput.contains(toEncode)) {
            return encoding.get(toEncode).toString();
        } else {
            throw new NoLegalProtocolCommandStringFoundException();
        }
    }


}
