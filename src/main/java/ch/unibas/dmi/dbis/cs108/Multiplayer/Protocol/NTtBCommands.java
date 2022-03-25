package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;


import java.util.HashMap;

public enum NTtBCommands {
    /**
     * Client commands:
     * CRTGM: Create a new game
     * CHATA: chat to all
     * CHATW: whisper chat
     * CHATG: ghost chat
     * LEAVG: leave a game
     * JOING: join a game
     * VOTEG: ghost voting who to infect
     * VOTEH: humans voting whos the ghost
     * QUITS: quit server/ leave servr
     * LISTP: list players/clients in session with the Server
     */



    //Client Commands
    CRTGM, CHATA, CHATW, CHATG, LEAVG, JOING, VOTEG, QUITS, LISTP, CUSRN,

    /**
     * MSGRS: "Message recieved": Paramaters: a string detailing to the client that and what the server recieved as command.
     */
    //Server Responses
    MSGRS;

    //Allowes to associate strings with the enum objects. the enum fields are easier for switch statements.
    private HashMap<String,NTtBCommands> stringNTtBCommandsHashMap = new HashMap<>();

    private NTtBCommands(){
        for(NTtBCommands cmd : NTtBCommands.values()){
            stringNTtBCommandsHashMap.put(cmd.name(), cmd);
        }
    }

    public boolean isLegal(String s){
        return false;
    }
}
