package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class ChatView  {

  private Label send = new Label("Send");

  private Pane root;

  private Pane createNodeHierarchy(){
    Pane p = new Pane();
    return p;
  }
}
