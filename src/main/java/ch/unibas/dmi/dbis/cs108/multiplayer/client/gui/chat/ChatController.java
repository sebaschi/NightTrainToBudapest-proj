package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatController implements Initializable {

  @FXML
  private VBox vBoxChatMessages;

  @FXML
  private Button sendButton;
  @FXML
  private TextField whisperTargetSelectField;
  @FXML
  private TextArea chatMsgField;



  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or {@code
   *                  null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
