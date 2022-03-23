package ch.unibas.dmi.dbis.cs108.Multiplayer.Protocol;

public class NTtBParameter implements NTtBInputType {
    String parameterValue;

    public NTtBParameter(String parameterValue) {
        this.parameterValue = parameterValue;
    }
    @Override
    public String getValue() {
        return parameterValue;
    }

    @Override
    public void setValue() {
    //Possibly do not need
    }
}
