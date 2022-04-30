package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import java.util.Set;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class LobbyListItem {

  private final Label lobbyID;
  private final Label adminName;
  private Set<StringProperty> clientsInLobby;
  private final ToggleButton button;

  public LobbyListItem(Label lobbyID, Label adminName,
      Set<StringProperty> clientsInLobby, ToggleButton button) {
    this.lobbyID = lobbyID;
    this.adminName = adminName;
    this.clientsInLobby = clientsInLobby;
    this.button = button;
  }
}
