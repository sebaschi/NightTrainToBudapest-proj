package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.utils;

import static ch.unibas.dmi.dbis.cs108.multiplayer.helpers.Protocol.whisper;
import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Provides utilities to configure Labels for the ChatView {@code ChatView.fxml}
 * within {@link ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatController}
 */
public class ChatLabelConfigurator {

  public static Label configure(String cmd, String msg, TextField whisperTargetSelectField, ClientModel client) {
    Label l;
    if (cmd.startsWith(whisper)) {
      //t = new Text("You whispered to " + whisperTargetSelectField.getText() + ": " + msg);
      l = new Label("You whispered to " + whisperTargetSelectField.getText() + ": " + msg);
      l.setBackground(Background.fill(Color.LAVENDERBLUSH));
    } else {
      //t = new Text(client.getUsername() + " (you): " + msg);
      l = new Label(client.getUsername() + " (you): " + msg);
      l.setBackground(Background.fill(Color.LAVENDER));
      l.setAlignment(Pos.CENTER_RIGHT);
      l.setWrapText(true);
      l.setMaxHeight(Double.MAX_VALUE);
      l.setPrefWidth(1150);
      l.setScaleShape(false);
    }
    return l;
  }

}
