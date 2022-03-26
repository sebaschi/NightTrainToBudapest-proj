package ch.unibas.dmi.dbis.cs108.multiplayer.client;

public interface ProtocolParser {
    /**
     * Takes a String from client input and parses into
     * server readable message.
     * @param msg the message to be parsed
     * @return a String message formatted for the specific protocol
     */
    String parseMsg(String msg) throws Exception;
}
