package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.ClientListItem;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientModel {

  public static final Logger LOGGER = LogManager.getLogger(ClientModel.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private String username;
  private Client client;
  private String incomingChatMsg;

  private ObservableList<SimpleStringProperty> allClients;
  private ObservableMap<Integer, SimpleStringProperty> idToNameMap;


  private HashSet<String> clientsOnServer;

  public ClientModel(String username, Client client) {
    this.username = username;
    this.client = client;
    this.allClients = FXCollections.observableArrayList();
    this.idToNameMap = FXCollections.observableHashMap();
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

  public ObservableList<SimpleStringProperty> getAllClients() {
    return allClients;
  }

  public void addClientToList(SimpleStringProperty nameAndId) {
    if(!allClients.contains(nameAndId))
    this.allClients.add(nameAndId);
  }

  public void removeClientFromList(String id){
    Iterator<SimpleStringProperty> it = allClients.iterator();
    while(it.hasNext()){
      String uid = it.next().getValue();
      if(uid.equals(id)){
        it.remove();
        break;
      }
    }
  }
}
