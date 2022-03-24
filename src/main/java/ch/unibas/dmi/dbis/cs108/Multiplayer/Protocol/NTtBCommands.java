package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;



public enum NTtBCommands {
    /**
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
    CRTGM, CHATA, CHATW, CHATG, LEAVG, JOING, VOTEG, QUITS, LISTP, CUSRN
}
