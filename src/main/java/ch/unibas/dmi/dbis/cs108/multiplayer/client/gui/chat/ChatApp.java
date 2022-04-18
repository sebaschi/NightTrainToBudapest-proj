package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import java.net.URL;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatApp extends Application {

  public static final Logger LOGGER = LogManager.getLogger(ChatApp.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private static ClientModel clientModel;
  private static ChatController chatController;
  private ClientModel cModel;

  public ChatApp() {
    super();
    LOGGER.info("Empty ChatApp constructor got called: ");

  }

  public ChatApp(ClientModel clientModel) {
    this.clientModel = clientModel;
    this.chatController = new ChatController(clientModel);
  }

  public static void setChatController(
      ChatController chatC) {
    chatController = chatC;
  }

  public void setcModel(ClientModel cModel) {
    this.cModel = cModel;
  }

  public ClientModel getcModel() {
    return cModel;
  }

  public static void setClientModel(ClientModel clientM) {
    clientModel = clientM;
  }

  public static ClientModel getClientModel() {
    return clientModel;
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
    LOGGER.info("made it here");
    this.setcModel(clientModel);
    URL resource = ChatApp.class.getResource(
        "splitPaneChatView.fxml");
    LOGGER.info("1");
    if (resource == null) {
      System.out.println("File wasnt found");
    }
    //ChatApp chatApp = new ChatApp(new ClientModel());
    try {
      Parent root = FXMLLoader.load(
          Objects.requireNonNull(ChatApp.class.getResource(
              "splitPaneChatView.fxml")));
      LOGGER.info("2");
      // TODO bin chatController.getChatPaneRoot() border to root border for resizing
      Scene scene = new Scene(root);
      LOGGER.info("3");
      scene.setRoot(root);
      LOGGER.info("4");
      primaryStage.setScene(scene);
      LOGGER.info("5");
    } catch (Exception e) {
      e.printStackTrace();
    }
    primaryStage.setResizable(true);
    LOGGER.info("6");
    primaryStage.setTitle("Chat");
    LOGGER.info("7");
    primaryStage.show();
    LOGGER.info("8");


  }

  public static void main(String[] args) {
    launch(args);
  }

  public ChatController getChatController() {
    return chatController;
  }
}
