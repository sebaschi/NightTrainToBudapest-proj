package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;


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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatController implements Initializable {
  public static final Logger LOGGER = LogManager.getLogger(ChatController.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

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

  private static ClientModel client;

  private SimpleBooleanProperty whisperTargetChosen;
  private String cmd;

  private static final String whisper = Protocol.whisper;
  private static final String chatToAll = Protocol.chatMsgToAll;
  private static final String chatToLobby = Protocol.chatMsgToLobby;

  public ChatController() { //TODO: why does this get called
    super();
    whisperTargetChosen = new SimpleBooleanProperty();
    cmd = "";
    LOGGER.info("ChatController empty constructor used");
  }
  public ChatController(ClientModel c) {
    client = c;
    whisperTargetChosen = new SimpleBooleanProperty();
    cmd = "";
    LOGGER.info("ChatController single parameter constructor used");
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
    setClient(ChatApp.getClientModel());
    ChatApp.setChatController(this);
    vBoxChatMessages.getChildren().addListener(new ListChangeListener<Node>() {
      @Override
      public void onChanged(Change<? extends Node> c) {
        vBoxChatMessages.setMaxHeight(vBoxChatMessages.getMaxHeight() * 2);
      }
    });

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

    /**
     * Initialize what heppens when the sen button is pressed
     */
    sendButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String msg = chatMsgField.getText();
        if (!msg.isEmpty()) {
          client.getClient().sendMsgToServer(cmd.toString() + msg);
          LOGGER.info("Message trying to send is: " + cmd.toString() + msg);
          Label l = new Label(client.getUsername() + " (you): " + msg);
          l.setBackground(Background.fill(Color.LAVENDER));
          vBoxChatMessages.getChildren().add(l);
          chatMsgField.clear();
        } else {
          LOGGER.debug("Trying to send an empty message.");
        }
      }
    });

    /**
     * Initialize the change of the TextArea field holding potential chat messages
     */
    chatMsgField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        chatMsgField.setText(newValue);
      }
    });

    //Bind the the fact if the whisper field contains a name to a boolean
    whisperTargetChosen.bind(whisperTargetSelectField.textProperty().isEmpty());

    /**
     * change the chat command based on the whisper text field.
     */
    whisperTargetChosen.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        //is true if {@code whisperTargetSelectedField} has content
        if (!newValue) {
          cmd = whisper + "$" + whisperTargetSelectField.getText() + "$";
        } else {
          cmd = chatToLobby + "$";
        }
      }
    });

    //Possibly now the whisperTargetChosenProperty is obsolete
    whisperTargetSelectField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        whisperTargetSelectField.setText(newValue);
        if (newValue.isEmpty()) {
          cmd = chatToLobby + "$";
        } else {
          cmd = whisper + "$" + whisperTargetSelectField.getText() + "$";
        }
      }
    });
  }

  /**
   * @return the client who's chat controller this is
   */
  public static ClientModel getClient() {
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

  /**
   * The client calls this method to foreward a chat message to the chat gui
   *
   * @param msg the message to be displayed
   */
  public void addChatMsgToView(String msg) {
    Label l = new Label(msg);
    l.setBackground(Background.fill(Color.LIGHTSKYBLUE));
    l.setTextFill(Color.BLACK);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        vBoxChatMessages.getChildren().add(l);
      }
    });
  }

}
