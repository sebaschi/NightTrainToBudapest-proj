package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the noise handling of the game: will the passengers the ghost(s) walk(ed) by while going to
 * their victim receive the right amount of noise notifications? Since the noises heard by
 * passengers are stored in designated noiseAmount arrays, the following tests check if the content
 * of those arrays matches expected values. Different cases are being analyzed: ghost infects victim
 * while being on his right / on his left; two ghosts infect a victim while coming from the same
 * side / from different sides; four ghosts dispersed all over the train infect a victim.
 */
class NoiseHandlerTest {

  // variables used by every test
  Passenger[] passengers = new Passenger[6]; // create new collection of passengers
  Passenger victim = new Passenger(); // player who will be turned into a ghost
  int[] noiseAmount =
      new int[6]; // collect amount of times a ghost walked by other players (= noises heard)
  int[] noiseAmountGuideline =
      new int[6]; // array representing the right players having heard the right noise amount

  @BeforeEach
  void preparation() {
    // variables that need to be reset before every new test
    for (int i = 0;
        i < passengers.length;
        i++) { // new collection of passengers unaffected by previous tests
      passengers[i] = new Passenger();
    }
    // array storing all the noises heard gets initialized anew, changes from previous tests gone
    noiseAmount = new int[6];
    // array storing the expected noises gets initialized anew, changes from previous tests gone
    noiseAmountGuideline = new int[6];
  }

  @Test
  @DisplayName("The right players hear the right amount of noises; ghost is to the right of victim")
  void noiseTestWhileOneGhostActive1() {
    /* test 1: passengers heard the right noise amount for following case: ghost is at position 5 and ghostifies
    player at position 2 --> noises should occur at positions 3 and 4
     */

    /* test prep: define who is ghost, assign correct ghost/victim positions, define expected
    results */

    passengers[5].setGhost();
    passengers[2] = victim;
    passengers[5].setPosition(5);
    victim.setPosition(2);
    noiseAmountGuideline = new int[] {0, 0, 0, 1, 1, 0};

    /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
    updated in noiseAmount array) */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != victim.getPosition()) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
      }
    }

    // check if actual noises saved in noiseAmount match expected values
    assertArrayEquals(noiseAmountGuideline, noiseAmount);
  }

  @Test
  @DisplayName("The right players hear the right amount of noises; ghost is to the left of victim")
  void noiseTestWhileOneGhostActive2() {
    /* test 2: passengers heard the right noise amount for following case: ghost is at position 1 and ghostifies
    player at position 4 --> noises should occur at positions 2 and 3
     */

    /* test prep: define who is ghost, assign correct ghost/victim positions, define expected
    results */

    passengers[1].setGhost();
    passengers[4] = victim;
    passengers[1].setPosition(1);
    victim.setPosition(4);
    noiseAmountGuideline = new int[] {0, 0, 1, 1, 0, 0};

    /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
    updated in noiseAmount array) */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != victim.getPosition()) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
      }
    }

    // check if actual noises saved in noiseAmount match expected values
    assertArrayEquals(noiseAmountGuideline, noiseAmount);
  }

  @Test
  @DisplayName("The right players hear the right amount of noises: two ghosts from the same side")
  void noiseTestWhileTwoGhostsActive1() {
    /* test 3: passengers heard the right noise amount for following case: ghost 1 is at position 4, ghost 2 is at
    position 5; they ghostify player at position 1 --> expected noise occurrence sorted by player 0 - 5: 0-0-2-2-1-0
    */

    /* test prep: define who is ghost, assign correct ghost/victim positions, define expected
    results */

    passengers[4].setGhost();
    passengers[5].setGhost();
    passengers[1] = victim;
    passengers[4].setPosition(4);
    passengers[5].setPosition(5);
    victim.setPosition(1);
    noiseAmountGuideline = new int[] {0, 0, 2, 2, 1, 0};

    /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
    updated in noiseAmount array) */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != victim.getPosition()) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
      }
    }

    // check if actual noises saved in noiseAmount match expected values
    assertArrayEquals(noiseAmountGuideline, noiseAmount);
  }

  @Test
  @DisplayName("The right players hear the right amount of noises: two ghosts from different sides")
  void noiseTestWhileTwoGhostsActive2() {
    /* test 4: passengers heard the right noise amount for following case: ghost 1 is at position 0, ghost 2 is at
    position 5; they ghostify player at position 3 --> expected noise occurrence sorted by player 0 - 5: 0-1-1-0-1-0
    */

    /* test prep: define who is ghost, assign correct ghost/victim positions, define expected
    results */

    passengers[0].setGhost();
    passengers[5].setGhost();
    passengers[3] = victim;
    passengers[0].setPosition(0);
    passengers[5].setPosition(5);
    victim.setPosition(3);
    noiseAmountGuideline = new int[] {0, 1, 1, 0, 1, 0};

    /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
    updated in noiseAmount array) */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != victim.getPosition()) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
      }
    }

    // check if actual noises saved in noiseAmount match expected values
    assertArrayEquals(noiseAmountGuideline, noiseAmount);
  }

  @Test
  @DisplayName(
      "The right players hear the right amount of noises: four ghosts from all over the place")
  void noiseTestWhileFourGhostsActive() {
    /* test 5: passengers heard the right noise amount for following case: ghost 1 is at position 0, ghost 2 is at
    position 1, ghost 3 is at position 3, ghost 4 is at position 5; they ghostify player at position 4
    --> expected noise occurrence sorted by player 0 - 5: 0-1-2-2-0-0
    */

    /* test prep: define who is ghost, assign correct ghost/victim positions, define expected
    results */

    for (int i = 0; i < passengers.length; i++) {
      if (i == 0 || i == 1 || i == 3 || i == 5) {
        passengers[i].setGhost();
        passengers[i].setPosition(i);
      }
      if (i == 4) {
        passengers[i] = victim;
        victim.setPosition(i);
      }
    }
    noiseAmountGuideline = new int[] {0, 1, 2, 2, 0, 0};

    /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
    updated in noiseAmount array) */
    for (int i = 0; i < passengers.length; i++) {
      if (passengers[i].getIsGhost() && i != victim.getPosition()) {
        NoiseHandler n = new NoiseHandler();
        noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
      }
    }

    // check if actual noises saved in noiseAmount match expected values
    assertArrayEquals(noiseAmountGuideline, noiseAmount);
  }
}
