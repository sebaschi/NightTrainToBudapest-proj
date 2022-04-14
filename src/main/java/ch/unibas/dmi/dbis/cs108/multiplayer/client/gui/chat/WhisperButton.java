package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;

/**
 * Represents the toggle for a whisper chat.
 */
public class WhisperButton implements ControlWrapper {

  private static RadioButton whisper = new RadioButton("Whisper");
  @Override
  public Control getControl() {
    return null;
  }
}
