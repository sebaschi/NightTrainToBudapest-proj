package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat.ChatApp;
import javafx.application.Application;

public class GUI implements Runnable{

  ChatApp chatApp;
  public GUI (ChatApp chatApp) {
    this.chatApp = chatApp;
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
    Application.launch(this.chatApp.getClass());
  }
}
