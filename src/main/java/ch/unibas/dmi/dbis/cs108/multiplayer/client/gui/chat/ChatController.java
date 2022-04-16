package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ChatController implements Initializable {

  @FXML
  private SplitPane chatPaneRoot;
  @FXML
  private VBox vBoxChatMessages;
  @FXML
  private Button sendButton;
  @FXML
  private TextField whisperTargetSelectField;
  @FXML
  private TextArea chatMsgField;

  private ClientModel client;

  private SimpleBooleanProperty whisperTargetChosen;
  private SimpleStringProperty cmd;

  private static final String whisper = Protocol.whisper;
  private static final String chatToAll = Protocol.chatMsgToAll;
  private static final String chatToLobby = Protocol.chatMsgToLobby;



  public ChatController(ClientModel client) {
    this.client = client;
    whisperTargetChosen = new SimpleBooleanProperty();
    cmd = new SimpleStringProperty();

  }


  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or {@code
   *                  null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {



    vBoxChatMessages.heightProperty().addListener(new ChangeListener<Number>() {
      /**
       * TODO: implement
       * Adjust the hight when new messages come in.
       * @param observable
       * @param oldValue
       * @param newValue
       */
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        vBoxChatMessages.setMaxHeight(newValue.doubleValue());
      }
    });

    sendButton.setOnAction(new EventHandler<ActionEvent>() {
      /**
       * control what to do when the "SendButton" is pressed
       * @param event
       */
      @Override
      public void handle(ActionEvent event) {
        String msg = chatMsgField.getText();
        if (!msg.isEmpty()) {
          client.getClient().sendMsgToServer(cmd.toString() + msg);
          Label l = new Label(client.getUsername() + " (you): " + msg);
          l.setBackground(Background.fill(Color.LAVENDER));
          vBoxChatMessages.getChildren().add(l);
          chatMsgField.clear();
        }
      }
    });
    chatMsgField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        chatMsgField.setText(newValue);
      }
    });

    whisperTargetChosen.bind(whisperTargetSelectField.textProperty().isEmpty());

    whisperTargetSelectField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        whisperTargetSelectField.setText(newValue);
      }
    });
  }

  /**
   * @return the client who's chat controller this is
   */
  public ClientModel getClient() {
    return client;
  }

  /**
   * @param client who's gui controller this should be
   */
  public void setClient(ClientModel client) {
    this.client = client;
  }

  public SplitPane getChatPaneRoot() {
    return chatPaneRoot;
  }
}
