package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableSet;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;

public class LobbyListItem extends ListCell {

  private final String lobbyID;
  private final String adminName;
  private ObservableSet<SimpleStringProperty> clientsInLobby;
  private final ToggleButton button;

  public LobbyListItem(String lobbyID, String adminName,
      ObservableSet<SimpleStringProperty> clientsInLobby, ToggleButton button) {
    this.lobbyID = lobbyID;
    this.adminName = adminName;
    this.clientsInLobby = clientsInLobby;
    this.button = button;
  }
}
