package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientListItem {

  private SimpleStringProperty name;
  private final int id;

  private static int uid = 0;
  public ClientListItem(String name, int id) {
    this.name = new SimpleStringProperty(name);
    this.id = id;
  }

  public ClientListItem(String name) {
    this(name, uid++);
  }

  @Override
  public String toString(){
    return name + " ID: " + id;
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public int getId() {
    return id;
  }

  public int clientID() {
    return id;
  }

}
