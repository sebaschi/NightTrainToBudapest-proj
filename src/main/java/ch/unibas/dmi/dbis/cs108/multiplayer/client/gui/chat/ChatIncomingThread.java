package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * To get the chat messages to the chat window.
 */
public class ChatIncomingThread implements Runnable {

  BufferedReader inputReader;
  ChatApp chatApp;
  public ChatIncomingThread(BufferedReader inputReader, ChatApp chatApp) {
    this.inputReader = inputReader;
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

  }
}
