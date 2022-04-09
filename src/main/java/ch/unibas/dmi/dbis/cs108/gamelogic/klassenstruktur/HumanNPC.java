package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumanNPC extends Human {
  public static final Logger LOGGER = LogManager.getLogger();
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
   * Sends a msg to the ServerGameInfoHandler.humanNpcParser to decide what has to happen now
   * @param msg the message that is sent to this player.
   * @param game the game the HumanNPC lives on (in game.gameState.passengerTrain)
   */
  @Override
  public void send(String msg, Game game) {
    ServerGameInfoHandler.humanNpcParser(this, msg, game);
  }

  /**
   * Currently returns a random integer for voting
   * @return integer between 0 and 5
   */
  public void vote(){
    int randomNr =  (int) (Math.random()*6);
    vote = randomNr;
    hasVoted = true;
    LOGGER.info("HumanNPC at Position: " + this.getPosition() + " has voted for: " + vote);
  }

  public void noise() {
    clientHandler.broadcastChatMessage("I heard some noise tonight");
  }
}
