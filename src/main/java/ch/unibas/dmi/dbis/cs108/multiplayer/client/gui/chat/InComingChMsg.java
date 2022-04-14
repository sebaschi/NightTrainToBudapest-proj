package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.chat;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * This class represents an incoming chat message to be displayed by the clients gui. When creating
 * an instance we should make sure we are also passing a String, otherwise {@code incomingChatMsg}
 * will have a null value. For now the {@code getValue()} and {@code setValue(Object value)} are the
 * main focus.
 */

public class InComingChMsg implements Property {

  private String incomingChatMsg;

  public InComingChMsg(String incomingChatMsg) {
    this.incomingChatMsg = incomingChatMsg;
  }

  public InComingChMsg() {
    this.incomingChatMsg = null;
  }

  /**
   * Create a unidirection binding for this {@code Property}.
   * <p>
   * Note that JavaFX has all the bind calls implemented through weak listeners. This means the
   * bound property can be garbage collected and stopped from being updated.
   *
   * @param observable The observable this {@code Property} should be bound to.
   * @throws NullPointerException if {@code observable} is {@code null}
   */
  @Override
  public void bind(ObservableValue observable) {

  }

  /**
   * Remove the unidirectional binding for this {@code Property}.
   * <p>
   * If the {@code Property} is not bound, calling this method has no effect.
   *
   * @see #bind(ObservableValue)
   */
  @Override
  public void unbind() {

  }

  /**
   * Can be used to check, if a {@code Property} is bound.
   *
   * @return {@code true} if the {@code Property} is bound, {@code false} otherwise
   * @see #bind(ObservableValue)
   */
  @Override
  public boolean isBound() {
    return false;
  }

  /**
   * Create a bidirectional binding between this {@code Property} and another one. Bidirectional
   * bindings exists independently of unidirectional bindings. So it is possible to add
   * unidirectional binding to a property with bidirectional binding and vice-versa. However, this
   * practice is discouraged.
   * <p>
   * It is possible to have multiple bidirectional bindings of one Property.
   * <p>
   * JavaFX bidirectional binding implementation use weak listeners. This means bidirectional
   * binding does not prevent properties from being garbage collected.
   *
   * @param other the other {@code Property}
   * @throws NullPointerException     if {@code other} is {@code null}
   * @throws IllegalArgumentException if {@code other} is {@code this}
   */
  @Override
  public void bindBidirectional(Property other) {

  }

  /**
   * Remove a bidirectional binding between this {@code Property} and another one.
   * <p>
   * If no bidirectional binding between the properties exists, calling this method has no effect.
   * <p>
   * It is possible to unbind by a call on the second property. This code will work:
   *
   * <blockquote><pre>
   *     property1.bindBirectional(property2);
   *     property2.unbindBidirectional(property1);
   * </pre></blockquote>
   *
   * @param other the other {@code Property}
   * @throws NullPointerException     if {@code other} is {@code null}
   * @throws IllegalArgumentException if {@code other} is {@code this}
   */
  @Override
  public void unbindBidirectional(Property other) {

  }

  /**
   * Returns the {@code Object} that contains this property. If this property is not contained in an
   * {@code Object}, {@code null} is returned.
   *
   * @return the containing {@code Object} or {@code null}
   */
  @Override
  public Object getBean() {
    return null;
  }

  /**
   * Returns the name of this property. If the property does not have a name, this method returns an
   * empty {@code String}.
   *
   * @return the name or an empty {@code String}
   */
  @Override
  public String getName() {
    return null;
  }

  /**
   * Adds a {@link ChangeListener} which will be notified whenever the value of the {@code
   * ObservableValue} changes. If the same listener is added more than once, then it will be
   * notified more than once. That is, no check is made to ensure uniqueness.
   * <p>
   * Note that the same actual {@code ChangeListener} instance may be safely registered for
   * different {@code ObservableValues}.
   * <p>
   * The {@code ObservableValue} stores a strong reference to the listener which will prevent the
   * listener from being garbage collected and may result in a memory leak. It is recommended to
   * either unregister a listener by calling {@link #removeListener(ChangeListener) removeListener}
   * after use or to use an instance of {@link WeakChangeListener} avoid this situation.
   *
   * @param listener The listener to register
   * @throws NullPointerException if the listener is null
   * @see #removeListener(ChangeListener)
   */
  @Override
  public void addListener(ChangeListener listener) {

  }

  /**
   * Removes the given listener from the list of listeners that are notified whenever the value of
   * the {@code ObservableValue} changes.
   * <p>
   * If the given listener has not been previously registered (i.e. it was never added) then this
   * method call is a no-op. If it had been previously added then it will be removed. If it had been
   * added more than once, then only the first occurrence will be removed.
   *
   * @param listener The listener to remove
   * @throws NullPointerException if the listener is null
   * @see #addListener(ChangeListener)
   */
  @Override
  public void removeListener(ChangeListener listener) {

  }

  /**
   * Returns the current value of this {@code ObservableValue}
   *
   * @return The current value
   */
  @Override
  public String getValue() {
    return this.incomingChatMsg;
  }

  /**
   * Set the wrapped value.
   *
   * @param value The new value
   */
  @Override
  public void setValue(Object value) {
    this.incomingChatMsg = (String) value;
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
