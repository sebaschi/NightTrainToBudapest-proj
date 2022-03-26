package ch.unibas.dmi.dbis.cs108.multiplayer.protocol;

import java.util.HashMap;
import java.util.HashSet;

/*
The NightTrainProtocol implements the Communication-Protocol of the
"Night Train to Budapest"" game. It acts as an Interface between Client and server. All Client Messages are
piped through this protocol, in order for the Server to execute the correct action. It is used by the ClientHandler
for this purpose.
 */

public class NightTrainProtocol {
    //TODO: initialite the fields

    private static HashMap<String, NTtBCommands> stringNTtBCommandsHashMap = initializeMapping();
    private static ProtocolValidator protocolValidator;
    private static HashSet<String> legalStrings = new HashSet<>(stringNTtBCommandsHashMap.keySet());

    public enum NTtBCommands {
        //Client Commands
        CRTGM, CHATA, CHATW, CHATG, LEAVG, JOING, VOTEG, QUITS, LISTP, CUSRN,CPING,
        //Server Responses
        MSGRS, SEROR, SPING;
    }

    private static HashMap<String, NTtBCommands> initializeMapping(){
        HashMap<String, NTtBCommands> map = new HashMap<>();
        for(NTtBCommands cmd: NTtBCommands.values()) {
            map.put(cmd.toString(), cmd);
        }
        return map;
    }

    //getters & setters

    public static HashMap<String, NTtBCommands> getStringNTtBCommandsHashMap() {
        return stringNTtBCommandsHashMap;
    }

    public static HashSet<String> getLegalStrings() {
        return legalStrings;
    }

    //Utility Methods:
    /**
     * Validates a given string is a valid representation
     * of a protocol command
     * @param cmd, the string command to be validated
     * @return true if <code>cmd</code> is a valid command
     */
    public boolean isLegalCmdString(String cmd) {
        return legalStrings.contains(cmd);
    }

    public NTtBCommands getCmdEnumObject(String cmd) throws NoLegalProtocolCommandStringFoundException {
        if(isLegalCmdString(cmd)){
            return stringNTtBCommandsHashMap.get(cmd);
        } else {
            throw new NoLegalProtocolCommandStringFoundException();
        }

    }

    //TODO analyize what methods are needed
}
