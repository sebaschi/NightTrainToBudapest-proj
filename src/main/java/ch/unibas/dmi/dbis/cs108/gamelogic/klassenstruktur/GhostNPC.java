package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostNPC extends Ghost {
  public static final Logger LOGGER = LogManager.getLogger(GhostNPC.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new GhostNPC. Should be used at game start or if a HumanNPC is turned into a ghost.
   *
   * @param position position on the train
   * @param name     player name. if null, then a default name is used.
   * @param isOG     true if the ghost is the original ghost.
   */
  public GhostNPC(int position, String name, boolean isOG) {
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
   * Sends the message to the ghostNpcParser, if ghost has not been kicked off
   * @param msg the message that is sent to this npc.
   * @param game the game this npc is in
   */
  @Override
  public void send(String msg, Game game) {
    if (!getKickedOff()) {
      ServerGameInfoHandler.ghostNpcParser(this, msg, game);
    }
  }

  /**
   * Sets vote of this Ghost position on a number between 0 and 5,
   * but only for positions where there aren't any ghosts and sets hasVoted to true
   * @param game the Game the NPC lives on
   * TODO: Make NPC smarter
   */
  public void vote(Game game) {
    int ghostCounter = 0;
    Passenger[] train = game.getGameState().getPassengerTrain();
    for (Passenger passenger : train) {
      if (passenger.isGhost && !passenger.getKickedOff()) {
        ghostCounter++;
      }
    }
    int[] humanPositions = new int[game.getGameState().getNrOfPlayers() - ghostCounter];
    if (humanPositions.length == 0) {
      vote = Integer.MAX_VALUE;
    } else {
      int j = 0;
      for (int i = 0; i < train.length; i++) {
        if (!train[i].isGhost && !train[i].getKickedOff()) { //is human
          humanPositions[j] = train[i].getPosition();
          j++;
        }
      }
      int randomPosition = (int) (Math.random() * humanPositions.length);
      vote = humanPositions[randomPosition];
      hasVoted = true;
      game.getGameState().getClientVoteData().setHasVoted(position,hasVoted);
      LOGGER.info("GhostNPC at Position: " + this.getPosition() + " has voted for: " + vote);
    }
  }

}
