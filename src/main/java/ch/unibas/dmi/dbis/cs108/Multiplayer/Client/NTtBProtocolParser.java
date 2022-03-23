package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NTtBCommands;
import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NTtBFormatMsg;
import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.NTtBParameter;
import ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol.ProtocolParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class NTtBProtocolParser implements ProtocolParser {
    //TODO Possibly bad name, rename to clientMsgParser?
    public static HashMap<String, NTtBCommands> inputMapper;

    public NTtBProtocolParser(){
        this.inputMapper = new HashMap<>();
        this.inputMapper.put("chat",NTtBCommands.CHATG);
        //TODO by far not done!
    }
    @Override
    public NTtBFormatMsg parseMsg(String msg) {
        Scanner sc = new Scanner(msg);
        ArrayList<String> input = new ArrayList<>();
        while(sc.hasNext()){
            input.add(sc.next());
        }
        buildProtocolMsg(input);
        return null;
        //TODO needs to be finnished
    }

    private String buildProtocolMsg(ArrayList<String> input) {
        //TODO
        String cmd = parseCmd(input.get(0));
        input.remove(0);
        return "";
    }

    private String parseCmd(String s){
        //TODO
        return "";
    }
}
