package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumanNPC extends Human {

  public static final Logger LOGGER = LogManager.getLogger(HumanNPC.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new HumanNPC.
   *
   * @param position position on the train
   * @param name     player name. if null, then a default name is used.
   */
  public HumanNPC(int position, String name) {
    this.position = position;
    this.clientHandler = null;
    isGhost = false;
    isPlayer = false;
    kickedOff = false;
    if (name == null) {
      this.name = "Robot Nr. " + position;
    } else {
      this.name = name;
    }
  }

  /**
   * Sends a msg to the ServerGameInfoHandler.humanNpcParser to decide what has to happen now, if
   * the npc hasn't been kicked off 8(should never happen to a human though)
   *
   * @param msg  the message that is sent to this player.
   * @param game the game the HumanNPC lives on (in game.gameState.passengerTrain)
   */
  @Override
  public void send(String msg, Game game) {
    if (!getKickedOff()) {
      ServerGameInfoHandler.humanNpcParser(this, msg, game);
    }
  }

  /**
   * Currently returns a random integer for voting, but only for passengers that haven't been kicked
   * off yet
   *
   * @param game the game this NPC lives on
   */
  public void vote(Game game) {
    Passenger[] passengers = game.getGameState().getPassengerTrain();
    int kickedOffCounter = 0;
    for (Passenger passenger : passengers) {
      if (passenger.getKickedOff()) {
        kickedOffCounter++;
      }
    }
    int[] inGamePositions = new int[passengers.length - kickedOffCounter];
    int i = 0;
    for (Passenger passenger : passengers) {
      if (!passenger.getKickedOff()) {
        inGamePositions[i] = passenger.getPosition();
        i++;
      }
    }
    int randomNr = (int) (Math.random() * inGamePositions.length);
    vote = inGamePositions[randomNr];
    hasVoted = true;
    game.getGameState().getClientVoteData().setHasVoted(position, hasVoted);
    LOGGER.info("HumanNPC at Position: " + this.getPosition() + " has voted for: " + vote);
  }
}
