package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.Node;

/**
 * Any class that represents a JavaFX node and has children should implement this interface
 */
public interface NodeWithChildren {

  void create();

  public default Node getChildren(){};

  void createNodeHierarchy();
}
