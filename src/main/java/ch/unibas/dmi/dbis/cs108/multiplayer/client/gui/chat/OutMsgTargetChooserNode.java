package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class OutMsgTargetChooserNode extends ToggleGroup implements NodeWithChildren {

  private Pane root;
  private ObservableList<Node> targets;

  @Override
  public void create() {

  }

  @Override
  public Node getChildren() {
    NodeWithChildren.super.getChildren();
  }

  @Override
  public void createNodeHierarchy() {
      this.root = new HBox();
      for(Node n : targets)
        root.getChildren().add(n);

  }
}
