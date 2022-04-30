package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import org.junit.jupiter.api.Test;

public class GameStateTests {

  public void testGetGhostPositions() {
    Passenger[] passengers = {
      new GhostNPC(0, "hansli", true),
      new HumanPlayer(1, "berta", null, false),
      new GhostPlayer(2, "uhu", null, false),
      new HumanNPC(3, "vreneli"),
      new GhostNPC(4, "...", false),
      new GhostPlayer(5, "last one", null, false)
    };
    boolean[] check = {true, false, true, false, true, true};
  }
}
