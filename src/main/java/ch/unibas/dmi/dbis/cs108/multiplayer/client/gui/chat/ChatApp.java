package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
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

  public ChatApp(ClientModel clientM) {
    clientModel = clientM;
    chatController = new ChatController(clientM);
  }

  /**
   * Sets the ChatController for the Application, needs to be static, but only one application can
   * be launched per programm
   *
   * @param chatC the ChatController to be linked to this chatApp
   */
  public static void setChatController(ChatController chatC) {
    chatController = chatC;
  }

  /**
   * Sets the non-static ClientModel field of this class
   *
   * @param cModel the non static ClientModel to be added
   */
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

  public ChatController getChatController() {
    return chatController;
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
    this.setcModel(clientModel);
    URL resource = ChatApp.class.getResource(
        "splitPaneChatView.fxml");
    if (resource == null) {
      System.out.println("File wasnt found");
    }
    //ChatApp chatApp = new ChatApp(new ClientModel());
    try {
      Parent root = FXMLLoader.load(
          Objects.requireNonNull(ChatApp.class.getResource(
              "splitPaneChatView.fxml")));
      // TODO bin chatController.getChatPaneRoot() border to root border for rezising
      Scene scene = new Scene(root);
      scene.setRoot(root);
      primaryStage.setScene(scene);
    } catch (Exception e) {
      e.printStackTrace();
    }
    primaryStage.setResizable(true);
    primaryStage.setTitle("Chat");
    primaryStage.show();


  }

  public static void main(String[] args) {
    launch(args);
  }

}
