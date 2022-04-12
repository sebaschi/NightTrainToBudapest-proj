package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * This is the view of the client chat gui.
 */
public class ChatView extends Node implements NodeWithChildren, ChildNode {


  private Pane root;

  public void createNodeHierarchy(){
    Button send = new SendButton();
    OutMsgTargetChooserNode chooseTarget = new OutMsgTargetChooserNode();
    TextArea clientOutgoingChatMsg = new TextArea();
  }

  @Override
  public Pane getRootPane() {
    return root;
  }

  @Override
  public ChildNode getInstance() {
    return this;
  }

  @Override
  public void create() {

  }

  @Override
  public void getChildren() {
    //TODO implement
    NodeWithChildren.super.getChildren();
  }
}
