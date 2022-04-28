package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import javafx.event.EventHandler;
import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameController {

 private static ClientModel client;

 @FXML
 private AnchorPane gameBG;
 @FXML
  private Group roomButtonGroupDay;
 @FXML
  private Button buttonRoom0;
 @FXML
  private Button buttonRoom1;
 @FXML
  private Button buttonRoom2;
 @FXML
  private Button buttonRoom3;
 @FXML
  private Button buttonRoom4;
 @FXML
  private Button buttonRoom5;

 @FXML
 private HBox roomLables;
 @FXML
 private TextFlow lableRoom0;
 @FXML
 private TextFlow lableRoom1;
 @FXML
 private TextFlow lableRoom2;
 @FXML
 private TextFlow lableRoom3;
 @FXML
 private TextFlow lableRoom4;
 @FXML
 private TextFlow lableRoom5;
 @FXML
 private Button noiseButton;
 @FXML
 private TextFlow notificationText;

 /**
  * If button 0 is clicked, send the vote message 0 to the server
  */
 public void sendVote0() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 0);
 }

 /**
  * If button 1 is clicked, send the vote message 0 to the server
  */
 public void sendVote1() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 1);
 }

 /**
  * If button 2 is clicked, send the vote message 0 to the server
  */
 public void sendVote2() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 2);
 }

 /**
  * If button 3 is clicked, send the vote message 0 to the server
  */
 public void sendVote3() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 3);
 }

 /**
  * If button 4 is clicked, send the vote message 0 to the server
  */
 public void sendVote4() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 4);
 }

 /**
  * If button 5 is clicked, send the vote message 0 to the server
  */
 public void sendVote5() {
  client.getClient()
      .sendMsgToServer(Protocol.votedFor + "$" + client.getClient().getPosition() + 5);
 }

 /**
  * Sends a noise message, to the server, should be a gui message?
  */
 public void noise() {
  client.getClient().sendMsgToServer("noise"); //TODO: Add message
 }

 /**
  * Takes a given message and displays it in the notificationText Flow in the game Scene
  * @param msg the message to be displayed
  */
 public void addMessageToNotificationText(String msg) {
  Text notification = new Text(msg);
  notificationText.getChildren().clear();
  notificationText.getChildren().add(notification);
  //TODO: Wait for a certain time, then clear all again
 }

 /**
  * Adds a msg to the room Lable at the specified position
  * @param names a String array containing all the names
  */
 public void addRoomLabels(String[] names) {

 }




}
