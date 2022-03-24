package ch.unibas.dmi.dbis.cs108.Multiplayer.Client;

public class EmptyClientInputException extends Exception {
    String exceptionMsg;
    Client whoDunIt;
    public EmptyClientInputException(Client whoDunIt) {
        this.whoDunIt = whoDunIt;
        this.exceptionMsg = whoDunIt.getUsername() + " tried to send an empty message";
    }

    public String getExceptionMsg(){
        return exceptionMsg;
    }

}
