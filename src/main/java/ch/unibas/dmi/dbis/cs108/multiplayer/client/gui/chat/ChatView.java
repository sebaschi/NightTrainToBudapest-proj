package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javax.print.attribute.standard.OrientationRequested;

/**
 * This is the view of the client chat gui.
 */
public class ChatView extends Node implements NodeWithChildren, ChildNode {


  private Pane root;

  public void createNodeHierarchy(){
    Button send = new SendButton();
    AnchorPane whereTheSendFieldLives = new AnchorPane();
    whereTheSendFieldLives.getChildren().add(send);

    OutMsgTargetChooserNode chooseTarget = new OutMsgTargetChooserNode();
    AnchorPane whereTheTargetFieldLives = new AnchorPane();
    whereTheTargetFieldLives.getChildren().add(chooseTarget.getChildren());

    TextArea clientOutgoingChatMsg = new TextArea();
    AnchorPane whereOutTextLives = new AnchorPane();
    whereOutTextLives.getChildren().add(clientOutgoingChatMsg);


    TextArea target = new TextArea();


    SplitPane inputOutputSeperation = new SplitPane();
    SplitPane sendAndToggleSeperation = new SplitPane();
    HBox buttonAndTextSeperation = new HBox();



    sendAndToggleSeperation.setOrientation(Orientation.HORIZONTAL);
    sendAndToggleSeperation.getItems().add(whereTheSendFieldLives);
    sendAndToggleSeperation.getItems().add(whereTheTargetFieldLives);
    /*
    buttonAndTextSeperation.a
    buttonAndTextSeperation.getItems().add(sendAndToggleSeperation);
    buttonAndTextSeperation.getItems().add()
        */

    inputOutputSeperation.setOrientation(Orientation.HORIZONTAL);
    inputOutputSeperation.getItems().add(sendAndToggleSeperation);

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
  public Node getChildren() {
    //TODO implement
    return NodeWithChildren.super.getChildren();
  }
}
