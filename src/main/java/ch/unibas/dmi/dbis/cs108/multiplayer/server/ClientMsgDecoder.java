package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NTtBFormatMsg;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NightTrainProtocol;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.NoLegalProtocolCommandStringFoundException;
import ch.unibas.dmi.dbis.cs108.multiplayer.protocol.ProtocolDecoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClientMsgDecoder implements ProtocolDecoder {
    private NightTrainProtocol protocol;

    @Override
    //TODO this method IS NOT FINNISHED. @return is not correct as of now!
    public NTtBFormatMsg decodeMsg(String msg) {
        List<String> msgTokens = tokenizeMsg(msg);  //List where we'll put the string tokens seperated by $.
        String cmd; //The command token
        NightTrainProtocol.NTtBCommands cmdObject;
        Queue<String> parameters;
        NTtBFormatMsg util = new NTtBFormatMsg();
        cmd = serverResponseBuilder(msgTokens);
        cmdObject = getCommandConstant(cmd);
        util.setCommand(cmdObject);
        try{
            cmd = getCommandStringToken(msgTokens);
        } catch (NoCommandTokenException e) {
            //TODO: decide what to do here. How can we catch this smartly and where do we send it?
            System.out.println(("ClientMsgDecoder cannot find a command token"));
            e.printStackTrace(System.out);
            return new NTtBFormatMsg("ERROR$NoCommandTokenException caught!", null, null);
        }

        return null;
    }

    /*
     * Builds the servers response message
     * to client
     */
    private String serverResponseBuilder(List<String> msgTokens){
        StringBuilder sb = new StringBuilder();
        //assumes not empty list!
        NightTrainProtocol.NTtBCommands cmd = getCommandConstant(msgTokens.get(0));
        sb.append("SERVER: ");
        sb.append("Command *" + cmd.toString() + "* recieved!");

        return sb.toString();
    }

    //Uses the NightTrainProtocol classes utility method
    private boolean isLegalCmdString(String cmd) {
        return protocol.isLegalCmdString(cmd);
    }
    private String getCommandStringToken(List<String> msgTokens) throws NoCommandTokenException {
        return msgTokens.get(0);
    }
    private NightTrainProtocol.NTtBCommands getCommandConstant(String stringToken) {
        try{
            return protocol.getCmdEnumObject(stringToken);
        }catch (NoLegalProtocolCommandStringFoundException e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            return NightTrainProtocol.NTtBCommands.SEROR;
        }

    }

    //Creates tokens from the clientMsg and puts them in a list
    private List<String> tokenizeMsg(String msg) {
        return null;
    }

    /*
     * This method should implement the initiation
     * of server agency according to client msg
     */
    private Queue<String> serverActionBuilder() {
        return new LinkedList<String>();
    }
}
