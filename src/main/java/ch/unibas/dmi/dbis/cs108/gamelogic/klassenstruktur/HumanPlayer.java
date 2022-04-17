package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientVoteData;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumanPlayer extends Human {
  public static final Logger LOGGER = LogManager.getLogger(HumanPlayer.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new GhostPlayer. Should be used at game start or if a HumanPlayer is turned into a
   * ghost.
   * @param position position on the train
   * @param name     name. if null, then a default name is used.
   * @param clientHandler the clienthandler connection this Passenger to a client
   * @param isOG the boolean defining if this is the Og ghost
   */
  public HumanPlayer(int position, String name, ClientHandler clientHandler, boolean isOG) {
    this.position = position;
    this.clientHandler = clientHandler;
    isGhost = false;
    isPlayer = true;
    kickedOff = false;
    if (name == null) {
      this.name = "Player Nr. " + position;
    } else {
      this.name = name;
    }
  }

  /**
   * Sends a message to the client handled bye this client handler
   * TODO: does this also work with 2 clients?
   * @param msg the message that is sent to this player.
   * @param game the game the HumanPlayer lives on (in game.gameState.passengerTrain)
   */
  @Override
  public void send(String msg, Game game) {
    String formattedMsg = ServerGameInfoHandler.format(msg,this, game);
    clientHandler.sendMsgToClient(formattedMsg);
  }

  /**
   * Gets the voting information vote and hasVoted from clientHandler and this values to those values.
   * Sets clientHandler fields to default: vote = Integer.MAX_VALUE , hasVoted = false
   */
  @Override
  public void getVoteFromGameState(ClientVoteData clientVoteData, Game game) {
    if(game.getIsDay()) {
      LOGGER.debug(Arrays.toString(clientVoteData.getVote()));
      LOGGER.debug("method was called by: " + position);
      vote = clientVoteData.getVote()[position];
      LOGGER.info("Human at Pos: " + position + " has voted for: " + vote);
      hasVoted = clientVoteData.getHasVoted()[position];
      LOGGER.debug(Arrays.toString(clientVoteData.getVote()));
      clientVoteData.setVote(position, Integer.MAX_VALUE);
      clientVoteData.setHasVoted(position, false);
      /*
       * if vote wasn't valid, make sure, the passenger field hasVoted == false, probably redundant but better be safe than sorry
       */
      if (vote == Integer.MAX_VALUE || game.getGameState().getPassengerTrain()[vote].getKickedOff()) {
        send("Your vote was invalid", game);
        hasVoted = false;
      }
    }
  }
}
