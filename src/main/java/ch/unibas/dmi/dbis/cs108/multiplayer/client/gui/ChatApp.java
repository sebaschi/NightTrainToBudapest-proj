package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ClientModel;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.ListOfLobbiesController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.LoungeSceneViewController;
import java.net.URL;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
  private static GameController gameController;
  private ClientModel cModel;
  private GameController gameC;
  private static LoungeSceneViewController loungeSceneViewController;
  private LoungeSceneViewController lSVController;
  private static ListOfLobbiesController listController;
  private ListOfLobbiesController listOfLobbiesController;

  public Node chat;
  public Node game;
  public Node backgroundDay;
  public Node lobbyList;

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

  public void setGameC(GameController gameC) {
    this.gameC = gameC;
  }

  /**
   * are u sure this is the field you want to get?
   * @param lSVController a LoungeSceneViewController
   */
  public void setlSVController(
      LoungeSceneViewController lSVController) {
    this.lSVController = lSVController;
  }

  /**
   * are u sure this is the field you want to get?
   * @return the clientModel of this chatApp
   */
  public ClientModel getcModel() {
    return cModel;
  }

  public static void setGameController(
      GameController gameController) {
    ChatApp.gameController = gameController;
  }

  /**
   * needs to be called, if the gameController needs to be accessed from outside the application thread
   * @return the relevant GameController
   */
  public GameController getGameController() {
    return gameController;
  }

  public GameController getGameC() {
    return gameC;
  }

  public static void setClientModel(ClientModel clientM) {
    clientModel = clientM;
  }

  public static ClientModel getClientModel() {
    return clientModel;
  }

  /**
   * needs to be called, if the chatController needs to be accessed from outside the application thread
   * @return the relevant ChatController
   */
  public ChatController getChatController() {
    return chatController;
  }

  /**
   * needs to be called, if the LoungeSceneViewController needs to be accessed from outside the application thread
   * @return the relevant LoungeSceneViewController
   */
  public LoungeSceneViewController getLoungeSceneViewController() {
    return loungeSceneViewController;
  }

  /**
   * Sure this is the field u want? not the static one?
   * @return a LoungeSceneViewController
   */
  public LoungeSceneViewController getlSVController() {
    return lSVController;
  }


  public static void setLoungeSceneViewController(LoungeSceneViewController controller) {
    loungeSceneViewController = controller;
  }

  public void setListOfLobbiesController(
      ListOfLobbiesController listOfLobbiesController) {
    this.listOfLobbiesController = listOfLobbiesController;
  }

  public static void setListController(ListOfLobbiesController controller) {
    listController = controller;
  }

  public static ListOfLobbiesController getListController() {
    return listController;
  }

  public ListOfLobbiesController getListOfLobbiesController() {
    return listOfLobbiesController;
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
    this.setGameC(gameController);
    gameC.setClient(cModel);
    gameC.setGameStateModel(GameController.getGameStateModel());
    Sprites.setNightSprites(GameController.getGameStateModel().getPassengerTrainClone()[1], GameController.getGameStateModel().getKickedOff());
    Sprites.setDaySprites(GameController.getGameStateModel().getPassengerTrainClone()[1], GameController.getGameStateModel().getKickedOff());
    SpritesDay.setBells();
    SpritesDay.setWheels();
    try {
      URL chatResource = ChatApp.class.getResource("chat/ChatView.fxml");
      URL gameResource = ChatApp.class.getResource("game/GameDayAll.fxml");
      URL bgDayResource = ChatApp.class.getResource("TrainAnimationViewDay.fxml");
      URL listForLobbiesResource = ChatApp.class.getResource("lounge/ListOfLobbiesScrollPane.fxml");
      this.chat = FXMLLoader.load(Objects.requireNonNull(chatResource));
      this.game = FXMLLoader.load(Objects.requireNonNull(gameResource));
      this.backgroundDay = FXMLLoader.load(Objects.requireNonNull(bgDayResource));
      this.lobbyList = FXMLLoader.load(Objects.requireNonNull(listForLobbiesResource));
      LOGGER.debug(bgDayResource);
    } catch (Exception e) {
      LOGGER.warn("There was an Exception while loading");
      e.printStackTrace();
    }
    URL loungeResource = ChatApp.class.getResource(
        "lounge/LoungeSceneView.fxml");
    try {
      Parent lounge = FXMLLoader.load(
          Objects.requireNonNull(loungeResource));
      this.setlSVController(loungeSceneViewController);
      lSVController.setChatApp(this);
      listOfLobbiesController = listController;
      listOfLobbiesController.setChatApp(this);
      // TODO bin chatController.getChatPaneRoot() border to root border for rezising
      Scene scene = new Scene(lounge);
      scene.setRoot(lounge);
      primaryStage.setScene(scene);
    } catch (Exception e) {
      e.printStackTrace();
    }
    primaryStage.setResizable(false);

    primaryStage.setTitle("Night Train To Budapest");
    primaryStage.setResizable(true);
    primaryStage.setMaximized(true);
    primaryStage.show();
      }

  public static void main(String[] args) {
    launch(args);
  }

}
