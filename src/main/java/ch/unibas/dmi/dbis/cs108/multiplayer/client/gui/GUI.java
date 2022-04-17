package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatApp;
import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GUI implements Runnable{
  public static final Logger LOGGER = LogManager.getLogger(GUI.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  ChatApp chatApp;
  private String name;
  public GUI (ChatApp chatApp) {
    this.chatApp = chatApp;
  }

  public void setName(String name) {
    this.name = name;
  }

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
    //String name =
    Application.launch(this.chatApp.getClass());
  }
}
