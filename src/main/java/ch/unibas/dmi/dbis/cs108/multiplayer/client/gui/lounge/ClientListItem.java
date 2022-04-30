package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientListItem {

  private SimpleStringProperty name;
  private final SimpleIntegerProperty id;

  public ClientListItem(SimpleStringProperty name, SimpleIntegerProperty id) {
    this.name = name;
    this.id = id;
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
    return id.get();
  }

  public SimpleIntegerProperty idProperty() {
    return id;
  }
}
