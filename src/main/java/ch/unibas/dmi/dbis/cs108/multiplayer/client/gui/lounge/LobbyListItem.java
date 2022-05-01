package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;

public class LobbyListItem {

  private final SimpleStringProperty lobbyID;
  private final SimpleStringProperty adminName;
  private ObservableList<SimpleStringProperty> clientsInLobby;

  private SimpleBooleanProperty ownedByClient;
  private SimpleBooleanProperty isOpen;

  private final int MAX_CAPACITY = 6;
  private SimpleIntegerProperty noOfPlayersInLobby;


  public LobbyListItem(SimpleStringProperty lobbyID, SimpleStringProperty adminName,
      SimpleBooleanProperty ownedByClient, SimpleBooleanProperty isOpen,
      SimpleIntegerProperty noOfPlayersInLobby) {
    this.lobbyID = lobbyID;
    this.adminName = adminName;
    this.clientsInLobby = clientsInLobby;
    this.ownedByClient = ownedByClient;
    this.isOpen = isOpen;
    this.noOfPlayersInLobby = noOfPlayersInLobby;
  }

  public String getLobbyID() {
    return lobbyID.get();
  }

  public SimpleStringProperty lobbyIDProperty() {
    return lobbyID;
  }

  public void setLobbyID(String lobbyID) {
    this.lobbyID.set(lobbyID);
  }

  public String getAdminName() {
    return adminName.get();
  }

  public SimpleStringProperty adminNameProperty() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName.set(adminName);
  }

  public ObservableList<SimpleStringProperty> getClientsInLobby() {
    return clientsInLobby;
  }

  public void setClientsInLobby(ObservableList<SimpleStringProperty> clientsInLobby) {
    this.clientsInLobby = clientsInLobby;
  }

  public boolean isOwnedByClient() {
    return ownedByClient.get();
  }

  public SimpleBooleanProperty ownedByClientProperty() {
    return ownedByClient;
  }

  public void setOwnedByClient(boolean ownedByClient) {
    this.ownedByClient.set(ownedByClient);
  }

  public boolean isIsOpen() {
    return isOpen.get();
  }

  public SimpleBooleanProperty isOpenProperty() {
    return isOpen;
  }

  public void setIsOpen(boolean isOpen) {
    this.isOpen.set(isOpen);
  }

  public int getMAX_CAPACITY() {
    return MAX_CAPACITY;
  }

  public int getNoOfPlayersInLobby() {
    return noOfPlayersInLobby.get();
  }

  public SimpleIntegerProperty noOfPlayersInLobbyProperty() {
    return noOfPlayersInLobby;
  }

  public void setNoOfPlayersInLobby(int noOfPlayersInLobby) {
    this.noOfPlayersInLobby.set(noOfPlayersInLobby);
  }

  @Override
  public String toString() {
    return "LobbyListItem{" + "lobbyID=" + lobbyID + ", adminName=" + adminName
        + ", clientsInLobby=" + clientsInLobby + ", ownedByClient=" + ownedByClient + ", isOpen="
        + isOpen + ", MAX_CAPACITY=" + MAX_CAPACITY + ", noOfPlayersInLobby=" + noOfPlayersInLobby
        + '}';
  }
}
