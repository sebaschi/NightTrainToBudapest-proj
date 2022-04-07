package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

/**
 * Handles all communication Client to Server concerning games tate updates i.e. client a has voted
 * Maybe unnecessary, everything that is needed might already be implemented in ClientHandler.
 * We might only need to extend the protocol and its parser.
 */

public class ClientGameInfoHandler {

  /**
   * sends a msg "" to Server stating who voted for who, this being the Client that votes
   * @param position the position of the passenger that is voted for
   */
  public void vote(int position) {

  }
}
