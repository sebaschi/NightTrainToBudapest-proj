package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Sound;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.ChatApp;
import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GUI implements Runnable {

  public static final Logger LOGGER = LogManager.getLogger(GUI.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private ChatApp chatApp;
  //private LoungeApp loungeApp;

  public GUI(ChatApp chatApp) {
    this.chatApp = chatApp;
  }

  /*public GUI(LoungeApp loungeApp) {
    this.loungeApp = loungeApp;
  }*/

  /*public GUI(ChatApp chatApp,
      LoungeApp loungeApp) {
    this.chatApp = chatApp;
    this.loungeApp = loungeApp;
  }*/

  /**
   * When an object implementing interface {@code Runnable} is used to create a thread, starting the
   * thread causes the object's {@code run} method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method {@code run} is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */

  @Override
  public void run() {
    LOGGER.info("here");
    //Application.launch(this.chatApp.getClass());
    Sound.startPlayingBackgroundSounds();
    Application.launch(this.chatApp.getClass());
  }
}
