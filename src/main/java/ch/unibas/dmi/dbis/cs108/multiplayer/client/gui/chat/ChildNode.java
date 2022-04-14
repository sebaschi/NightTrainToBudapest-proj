package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.layout.Pane;

public interface ChildNode {

  public Pane getRootPane();
  public ChildNode getInstance();

}
