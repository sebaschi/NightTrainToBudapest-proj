package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.TrainOverflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostNPC extends Ghost {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new GhostNPC. Should be used at game start or if a HumanNPC is turned into a ghost.
   *
   * @param position position on the train
   * @param name     player name. if null, then a default name is used.
   * @param isOG     true if the ghost is the original ghost.
   */
  public GhostNPC(int position, String name, boolean isOG, Game game) {
    super(game);
    this.isOG = isOG;
    this.position = position;
    this.clientHandler = null;
    isGhost = true;
    isPlayer = false;
    kickedOff = false;
      if (name == null) {
          this.name = "Robot Nr. " + position;
      } else {
          this.name = name;
      }
  }

  /**
   * Sets vote of this Ghost position on a number between 0 and 5,
   * but only for positions where there aren't any ghosts and sets hasVoted to true
   */
  public void vote(Game game){
    int ghostCounter = 0;
    Passenger[] train = game.getGameFunctions().passengerTrain;
    for(Passenger passenger : train) {
      if(passenger.isGhost) {
        ghostCounter++;
      }
    }
    int[] humanPositions = new int[game.getNrOfPlayers() - ghostCounter ];
    int j = 0;
    for (int i = 0; i < train.length; i++) {
      if (!train[i].isGhost) { //is human
        humanPositions[j] = train[i].getPosition();
        j++;
      }
    }
    int randomPosition = (int) (Math.random()*humanPositions.length);
    vote = humanPositions[randomPosition];
    hasVoted = true;
    LOGGER.info("GhostNPC at Position: " + this.getPosition() + " has voted for: " + vote);
  }

  public void noise() {
    clientHandler.broadcastChatMessage("I heard some noise tonight");
  }

  public static void main(String[] args) {

    try {
      Game game = new Game(6,1,1);
      Passenger p = new Passenger(game);
      GhostNPC ghostNPC = new GhostNPC(2,"peter", false, game);
      p = ghostNPC;
      ghostNPC.vote(game);
    } catch (TrainOverflow e) {
      LOGGER.warn(e.getMessage());
    }

  }
}
