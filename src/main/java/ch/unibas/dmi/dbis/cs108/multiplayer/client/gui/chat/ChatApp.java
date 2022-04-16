package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChatApp extends Application {
  ClientModel clientModel;
  private ChatController chatController;


  public ChatApp(ClientModel clientModel) {
    this.clientModel = clientModel;
    this.chatController = new ChatController(clientModel);
  }

  /**
   * The main entry point for all JavaFX applications. The start method is called after the init
   * method has returned, and after the system is ready for the application to begin running.
   *
   * <p>
   * NOTE: This method is called on the JavaFX Application Thread.
   * </p>
   *
   * @param primaryStage the primary stage for this application, onto which the application scene
   *                     can be set. Applications may create other stages, if needed, but they will
   *                     not be primary stages.
   * @throws Exception if something goes wrong
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    //ChatApp chatApp = new ChatApp(new ClientModel());
    Parent root = FXMLLoader.load(getClass().getResource("splitPaneChatView.fxml"));
    // TODO bin chatController.getChatPaneRoot() border to root border for rezising
    Scene scene = new Scene(root);
    primaryStage.setResizable(true);
    primaryStage.setTitle("Chat");
    primaryStage.setScene(scene);
    primaryStage.show();


  }

  public static void main(String[] args) {
    launch(args);
  }

  public ChatController getChatController() {
    return chatController;
  }
}
