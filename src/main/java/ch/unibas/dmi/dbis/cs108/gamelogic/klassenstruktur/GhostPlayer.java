package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientVoteData;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostPlayer extends Ghost {
  public static final Logger LOGGER = LogManager.getLogger(GhostPlayer.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new GhostPlayer. Should be used at game start or if a HumanPlayer is turned into a
   * ghost.
   *  @param position position on the train
   * @param name     name. if null, then a default name is used.
   * @param isOG     true if the ghost is the original ghost.
   */
  public GhostPlayer(int position, String name, ClientHandler clientHandler, boolean isOG) {
    this.position = position;
    this.clientHandler = clientHandler;
    this.isOG = isOG;
    isGhost = true;
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
   * @param game the game the GhostPlayer lives on (in game.gameState.passengerTrain)
   */
  @Override
  public void send(String msg, Game game) {
    String formattedMsg = ServerGameInfoHandler.format(msg, this, game);
    clientHandler.sendMsgToClient(formattedMsg);
  }

  /**
   * Gets the voting information vote and hasVoted from clientHandler and this values to those values.
   * Sets clientHandler fields to default: vote = Integer.MAX_VALUE , hasVoted = false
   */
  @Override
  public void getVoteFromGameState(ClientVoteData clientVoteData, Game game) {
    vote = clientVoteData.getVote()[position];
    hasVoted = clientVoteData.getHasVoted()[position];
    clientVoteData.setVote(position, Integer.MAX_VALUE);
    clientVoteData.setHasVoted(position,false);
    LOGGER.info("Ghost at Pos: " + position + " has voted for: " + vote);
    /*
    * if vote wasn't valid, make sure, the passenger field hasVoted == false, probably redundant but better be safe than sorry
    */
    if(vote == Integer.MAX_VALUE) {
      hasVoted = false;
    }
  }

}

