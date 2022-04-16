package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;

public class ClientModel {

  private String username;
  private Client client;
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
