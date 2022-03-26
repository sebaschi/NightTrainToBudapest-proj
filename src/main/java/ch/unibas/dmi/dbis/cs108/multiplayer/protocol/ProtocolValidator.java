package ch.unibas.dmi.dbis.cs108.multiplayer.protocol;

import java.util.EnumSet;
import java.util.HashSet;
//TODO Possibly redundant!!

public class ProtocolValidator {

    //TODO String or NTtBCommands HashSet?
    public static HashSet<NightTrainProtocol.NTtBCommands> legalCommands = initializeLegalCommands();

    //Initialize the legalCommands set with the protocol values.
    private static HashSet<NightTrainProtocol.NTtBCommands> initializeLegalCommands(){
        EnumSet<NightTrainProtocol.NTtBCommands> enumVals = EnumSet.allOf(NightTrainProtocol.NTtBCommands.class);
        return new HashSet<>(enumVals);
    }

    public boolean validateCommand(String s) {
        //TODO implement if needed
        return false;
    }

}
