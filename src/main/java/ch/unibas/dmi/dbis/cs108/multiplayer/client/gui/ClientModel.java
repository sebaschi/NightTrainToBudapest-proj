package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientModel {

  public static final Logger LOGGER = LogManager.getLogger(ClientModel.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private String username;
  private Client client;
  private String incomingChatMsg;

  public ClientModel(String username, Client client) {
    this.username = username;
    this.client = client;
  }

  //private Number;

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
