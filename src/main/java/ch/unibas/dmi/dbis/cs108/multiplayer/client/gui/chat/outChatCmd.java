package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class outChatCmd implements ChangeListener {

  private String cmd;
  private static final Protocol prtcl = new Protocol();
  private static final String whisper = Protocol.whisper;
  private static final String chatToAll = Protocol.chatMsgToAll;
  private static final String chatToLobby = Protocol.chatMsgToLobby

  public outChatCmd(String cmd, String parameters) {
    this.cmd = cmd;
    this.parameters = parameters;
  }

  public String getCmd() {
    return cmd;
  }

  public String getParameters() {
    return parameters;
  }

  /**
   * Called when the value of an {@link ObservableValue} changes.
   * <p>
   * In general, it is considered bad practice to modify the observed value in this method.
   *
   * @param observable The {@code ObservableValue} which value changed
   * @param oldValue   The old value
   * @param newValue
   */
  @Override
  public void changed(ObservableValue observable, Object oldValue, Object newValue) {

  }
}
