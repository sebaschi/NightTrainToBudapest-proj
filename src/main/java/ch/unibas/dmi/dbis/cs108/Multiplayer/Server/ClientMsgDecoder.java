package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.ProtocolDecoder;

import java.util.List;
import java.util.Scanner;

public class ClientMsgDecoder implements ProtocolDecoder {
    Scanner sc = new Scanner();

    @Override
    public String decodeMsg(String msg) {
        List<String> msgTokens = tokenizeMsg(msg);
        return null;
    }

    /*
     * Builds the servers response message
     * to client
     */
    private String serverResponseBuilder(List<String> msgTokens){
        return null;
    }

    private String getCommand(List<String> msgTokens) throws NoCommandTokenException {
        return msgTokens.get(0);
    }

    //Creates tokens from the clientMsg and puts them in a list
    private List<String> tokenizeMsg(String msg) {
        return null;
    }

    /*
     * This method should implement the initiation
     * of server agency according to client msg
     */
    private @interface serverActionBuilder {
        //TODO implement what should happen server side
    }
}
