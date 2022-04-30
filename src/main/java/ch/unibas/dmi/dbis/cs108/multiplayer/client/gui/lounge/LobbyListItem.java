package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;

public class LobbyListItem implements Observable {

  private final SimpleStringProperty lobbyID;
  private final SimpleStringProperty adminName;
  private ObservableList<SimpleStringProperty> clientsInLobby;
  private final Button button;
  private SimpleBooleanProperty ownedByClient;
  private SimpleBooleanProperty isOpen;

  public LobbyListItem(SimpleStringProperty lobbyID, SimpleStringProperty adminName,
      ObservableList<SimpleStringProperty> clientsInLobby, Button button,
      SimpleBooleanProperty ownedByClient) {
    this.lobbyID = lobbyID;
    this.adminName = adminName;
    this.clientsInLobby = clientsInLobby;
    this.button = button;
  }

  /**
   * Adds an {@link InvalidationListener} which will be notified whenever the {@code Observable}
   * becomes invalid. If the same listener is added more than once, then it will be notified more
   * than once. That is, no check is made to ensure uniqueness.
   * <p>
   * Note that the same actual {@code InvalidationListener} instance may be safely registered for
   * different {@code Observables}.
   * <p>
   * The {@code Observable} stores a strong reference to the listener which will prevent the
   * listener from being garbage collected and may result in a memory leak. It is recommended to
   * either unregister a listener by calling {@link #removeListener(InvalidationListener)
   * removeListener} after use or to use an instance of {@link WeakInvalidationListener} avoid this
   * situation.
   *
   * @param listener The listener to register
   * @throws NullPointerException if the listener is null
   * @see #removeListener(InvalidationListener)
   */
  @Override
  public void addListener(InvalidationListener listener) {

  }

  /**
   * Removes the given listener from the list of listeners, that are notified whenever the value of
   * the {@code Observable} becomes invalid.
   * <p>
   * If the given listener has not been previously registered (i.e. it was never added) then this
   * method call is a no-op. If it had been previously added then it will be removed. If it had been
   * added more than once, then only the first occurrence will be removed.
   *
   * @param listener The listener to remove
   * @throws NullPointerException if the listener is null
   * @see #addListener(InvalidationListener)
   */
  @Override
  public void removeListener(InvalidationListener listener) {

  }
}
