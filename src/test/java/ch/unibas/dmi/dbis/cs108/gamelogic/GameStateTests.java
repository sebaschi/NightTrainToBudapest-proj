package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.*;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GameStateTests {

  /*
   * Streams to store system.out and system.err content
   */
  private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  private ByteArrayOutputStream errStream = new ByteArrayOutputStream();

  /*
   * Here we store the previous pointers to system.out / system.err
   */
  private PrintStream outBackup;
  private PrintStream errBackup;

  /**
   * This method is executed before each test.
   * It redirects System.out and System.err to our variables {@link #outStream} and {@link #errStream}.
   * This allows us to test their content later.
   */
  @BeforeEach
  public void redirectStdOutStdErr() {
    System.out.println("im here");
    outBackup = System.out;
    errBackup = System.err;
    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));
    outBackup.println("this should");
  }

  /**
   * This method is run after each test.
   * It redirects System.out / System.err back to the normal streams.
   */
  @AfterEach
  public void reestablishStdOutStdErr() {
    System.setOut(outBackup);
    System.setErr(errBackup);
  }

  /**
   * This is a normal JUnit-Test. It executes the HelloWorld-Method and verifies that it actually wrote "Hello World" to stdout
   */
  @Test
  public void testMain() {

    /* old test. Todo: delete
    new Thread(new Runnable() {
      @Override
      public void run() {
        Server.main(1837);
      }
    }).start();
    outBackup.println("here now");
    String toTest = outStream.toString();
    outBackup.println(toTest);
    //toTest = removeNewline(toTest);
    assertTrue(toTest.contains("Port"));
     */
    try {
      int totalNumberOfPlayers = 6;
      int numberOfHumanPlayers = 3;
      Passenger[] passengers = new Passenger[totalNumberOfPlayers];
      VoteHandler v = new VoteHandler();
      Lobby l = null;

      Game game = new Game(6, 1, 3, l);
      passengers[0] = new GhostNPC(0,"0", true);
      passengers[1] = new GhostNPC(1,"1", false);
      passengers[2] = new GhostNPC(2,"2", false);
      passengers[3] = new HumanNPC(3,"3");
      passengers[4] = new HumanNPC(4,"4");
      passengers[5] = new HumanNPC(5,"5");

      int[] votesForPlayers = {0, 0, 0, 0, 0, 0};
      int maxVotes = v.ghostVoteEvaluation(passengers, votesForPlayers, null, game);
      assertEquals(2, maxVotes);

    } catch (Exception ignored) {
      outBackup.println("exception.");
    }
    outBackup.println("done");

  }

  private static String removeNewline(String str) {
    return str.replace("\n", "").replace("\r", "");
  }

}
