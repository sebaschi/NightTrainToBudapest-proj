package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

  private Client client;

  private SimpleBooleanProperty whisperTargetChosen;

  public ChatController(Client client) {
    this.client = client;
    whisperTargetChosen = new SimpleBooleanProperty();
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
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        vBoxChatMessages.setMaxHeight(newValue.doubleValue());
      }
    });

    sendButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String msg = chatMsgField.getText();
        if (!msg.isEmpty()) {

        }
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
  public Client getClient() {
    return client;
  }

  /**
   * @param client who's gui controller this should be
   */
  public void setClient(Client client) {
    this.client = client;
  }
}
