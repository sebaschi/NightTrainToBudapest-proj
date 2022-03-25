package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

import java.util.HashMap;
import java.util.HashSet;

/*
The NightTrainProtocol implements the Communication-Protocol of the
"Night Train to Budapest"" game. It acts as an Interface between Client and server. All Client Messages are
piped through this protocol, in order for the Server to execute the correct action. It is used by the ClientHandler
for this purpose.
 */

public class NightTrainProtocol {
    public static HashMap<String, NTtBCommands> stringNTtBCommandsHashMap = new HashMap<>();
    public static ProtocolValidator protocolValidator;
    public static HashSet<String> legalStrings;
    public enum NTtBCommands {
        //Client Commands
        CRTGM, CHATA, CHATW, CHATG, LEAVG, JOING, VOTEG, QUITS, LISTP, CUSRN,
        //Server Responses
        MSGRS;

        //Allowes to associate strings with the enum objects. the enum fields are easier for switch statements.

    }
}
