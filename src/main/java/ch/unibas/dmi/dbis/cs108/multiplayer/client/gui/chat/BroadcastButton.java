package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Represents toggling to broadcast to everyone
 */
public class BroadcastButton extends ChatTargetToggle implements Toggle {

  Label l = new Label("Broadcast");

}
