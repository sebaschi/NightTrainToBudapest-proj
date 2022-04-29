package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.GameStateModel;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

 private static GameStateModel gameStateModel;



 //TODO(Seraina, Sebi): Same issue as ChatController? do with setters?
 public GameController(ClientModel c, GameStateModel g) {
  client = c;
  gameStateModel = g;
 }

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
 private HBox notificationHBox;
 @FXML
 private ImageView noiseImage0;
 @FXML
 private ImageView noiseImage1;
 @FXML
 private ImageView noiseImage2;
 @FXML
 private ImageView noiseImage3;
 @FXML
 private ImageView noiseImage4;
 @FXML
 private ImageView noiseImage5;
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
  client.getClient().sendMsgToServer("noise"); //TODO: Add message that server understands
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
  * Updates the labels of the rooms accordingly to the datastructures in GameStateModel
  */
 public void updateRoomLabels() {
  String[] names = gameStateModel.getPassengerTrainClone()[0];
  String[] roles = gameStateModel.getPassengerTrainClone()[1];
  Text name0 = new Text(names[0]);
  Text name1 = new Text(names[1]);
  Text name2 = new Text(names[2]);
  Text name3 = new Text(names[3]);
  Text name4 = new Text(names[4]);
  Text name5 = new Text(names[5]);
  Text role0 = new Text(roles[0]);
  Text role1 = new Text(roles[1]);
  Text role2 = new Text(roles[2]);
  Text role3 = new Text(roles[3]);
  Text role4 = new Text(roles[4]);
  Text role5 = new Text(roles[5]);

  lableRoom0.getChildren().clear();
  lableRoom0.getChildren().add(name0);
  lableRoom0.getChildren().add(role0);
  lableRoom1.getChildren().clear();
  lableRoom1.getChildren().add(name1);
  lableRoom1.getChildren().add(role1);
  lableRoom2.getChildren().clear();
  lableRoom2.getChildren().add(name2);
  lableRoom2.getChildren().add(role2);
  lableRoom3.getChildren().clear();
  lableRoom3.getChildren().add(name3);
  lableRoom3.getChildren().add(role3);
  lableRoom4.getChildren().clear();
  lableRoom4.getChildren().add(name4);
  lableRoom4.getChildren().add(role4);
  lableRoom5.getChildren().clear();
  lableRoom5.getChildren().add(name5);
  lableRoom5.getChildren().add(role5);
 }

 /**
  * Adds an image of a bell on top of button0
  */
 public void noiseDisplay0(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }

 /**
  * Adds an image of a bell on top of button1
  */
 public void noiseDisplay1(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }

 /**
  * Adds an image of a bell on top of button2
  */
 public void noiseDisplay2(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }

 /**
  * Adds an image of a bell on top of button3
  */
 public void noiseDisplay3(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }

 /**
  * Adds an image of a bell on top of button4
  */
 public void noiseDisplay4(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }

 /**
  * Adds an image of a bell on top of button5
  */
 public void noiseDisplay5(){
  Image bell = new Image("ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.DayOpen.bell.png");
  noiseImage0.setImage(bell);
 }



 public void setGameStateModel(
     GameStateModel gameStateModel) {
  GameController.gameStateModel = gameStateModel;
 }
}
