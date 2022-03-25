package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class defines what type the ClientMsgDecoder returns after decoding the message.
 * This is done so the output can be split into a response to the client and action
 * in the game logik.
 * commands should map to methods(maybe classes)
 * parameters map to method parameters
 *
 */

public class NTtBFormatMsg {

    private String msgToClient;
    private NightTrainProtocol.NTtBCommands command;
    private final Queue<String> parameters; //TODO maybe use array?

    public NTtBFormatMsg(String msgToClient, NightTrainProtocol.NTtBCommands command, Queue<String> parameters) {
        this.msgToClient = msgToClient;
        this.command = command;
        this.parameters = parameters;
    }

    public NTtBFormatMsg() {
        this.msgToClient = "";
        this.command = null;
        this.parameters = new LinkedList<>();
    }

    public String getMessage() {
        return msgToClient;
    }

    public NightTrainProtocol.NTtBCommands getCommand() {
        return command;
    }

    public Queue<String> getParameters() {
        return parameters;
    }

    public void setMsgToClient(String msgToClient) {
        this.msgToClient = msgToClient;
    }

    public void setCommand(NightTrainProtocol.NTtBCommands command) {
        this.command = command;
    }
}
