package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * If this is toggled than the client chat is operating in whisper mode.
 */

public abstract class ChatTargetToggle extends Node implements Toggle{

  BooleanProperty isToggled;
  ObjectProperty<ToggleGroup> myFriends;

  /**
   * Returns The {@link ToggleGroup} to which this {@code Toggle} belongs.
   *
   * @return The {@link ToggleGroup} to which this {@code Toggle} belongs.
   */
  @Override
  public ToggleGroup getToggleGroup() {
    return myFriends.get();
  }

  /**
   * Sets the {@link ToggleGroup} to which this {@code Toggle} belongs.
   *
   * @param toggleGroup The new {@link ToggleGroup}.
   */
  @Override
  public void setToggleGroup(ToggleGroup toggleGroup) {
      myFriends.bindBidirectional((Property<ToggleGroup>) toggleGroup);
  }

  /**
   * The {@link ToggleGroup} to which this {@code Toggle} belongs.
   *
   * @return the toggle group property
   */
  @Override
  public ObjectProperty<ToggleGroup> toggleGroupProperty() {
    return myFriends;
  }

  /**
   * Indicates whether this {@code Toggle} is selected.
   *
   * @return {@code true} if this {@code Toggle} is selected.
   */
  @Override
  public boolean isSelected() {
    return isToggled.get();
  }

  /**
   * Sets this {@code Toggle} as selected or unselected.
   *
   * @param selected {@code true} to make this {@code Toggle} selected.
   */
  @Override
  public void setSelected(boolean selected) {
    this.isToggled.set(selected);
  }

  /**
   * The selected state for this {@code Toggle}.
   *
   * @return the selected property
   */
  @Override
  public BooleanProperty selectedProperty() {
    return isToggled;
  }
}
