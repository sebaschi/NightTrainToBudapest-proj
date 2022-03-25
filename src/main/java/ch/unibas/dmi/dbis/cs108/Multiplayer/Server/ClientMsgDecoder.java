package ch.unibas.dmi.dbis.cs108.Multiplayer.Server;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.ProtocolDecoder;

import java.util.List;
import java.util.Scanner;

public class ClientMsgDecoder implements ProtocolDecoder {


    @Override
    public String decodeMsg(String msg) {
        List<String> msgTokens = tokenizeMsg(msg);  //List where we'll put the string tokens seperated by $.
        String cmd; //The command token
        try{
            cmd = getCommand(msgTokens);
        } catch (NoCommandTokenException e) {
            //TODO: decide what to do here. How can we catch this smartly and where do we send it?
            System.out.println(("ClientMsgDecoder cannot find a command token"));
            e.printStackTrace(System.out);
            return"ERROR$command token not found"; //TODO This is a very unelegant solution.
        }

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
