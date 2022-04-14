package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Represents the button in the chat to send a chat message.
 */
public class SendButton extends Button implements UINode {


  public SendButton() {
    super("Send");
  }

  @Override
  public void listen() {

  }
}
