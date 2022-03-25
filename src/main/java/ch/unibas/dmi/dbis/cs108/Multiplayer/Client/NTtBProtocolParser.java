package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implements a protocol parser for the NTtB protocoll,
 * that transforms client input
 * into a server readable format.
 */
public class NTtBProtocolParser implements ProtocolParser {
    //TODO Possibly bad name, rename to clientMsgParser?
    public final Client caller;
    public static InputToProtocolMap legalCommands = new InputToProtocolMap();


    public NTtBProtocolParser(Client caller) {
        this.caller = caller;
    }
    @Override
    public String parseMsg(String msg) {
        Scanner sc = new Scanner(msg);
        String parsedMsg;

        ArrayList<String> input = new ArrayList<>();

        while(sc.hasNext()){
            input.add(sc.next());
        }

        try {
             parsedMsg = buildProtocolMsg(input);
        } catch (EmptyClientInputException e) {
            return e.getExceptionMsg();
            //TODO Where do we log this?
        }

        return parsedMsg;
    }


    private String buildProtocolMsg(ArrayList<String> input) throws EmptyClientInputException{
        //TODO
        if(emptyClientInput(input)){
            throw new EmptyClientInputException(caller);
        }
        StringBuilder s = new StringBuilder(); //friendly little helper
        s.append(legalCommands.get(input.get(0)));
        if (containsParameters(input)) {
            int size = input.size();
            for(int i = 1; i < size; i++) {
                s.append("$");
                s.append(input.get(i).toLowerCase()); //parameters are always lower case (is that good?)
            }
        }
        return s.toString();
    }

    /**
     * Checks if input has parameters
     *
     * if the list size is smaller than 2, i.e.
     * not larger than 1, the input only contains
     * a command.
     *
     * @param input the tokenized input string.
     * @return true if input list is larger than 2.
     */
    private boolean containsParameters(ArrayList<String> input) {
        return input.size() > 1;
    }

    /**
     * checks if client input is empty
     * @param clientInput the clients input.
     * @return true if client didn't send any input besides whitespace
     */
    private boolean emptyClientInput(ArrayList<String> clientInput) {
        return clientInput.isEmpty();
    }
}
