package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostPlayer extends Ghost {
  public static final Logger LOGGER = LogManager.getLogger();
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


  @Override
  public void send(String msg, Game game) {
    String formattedMsg = ServerGameInfoHandler.format(msg, game);
    clientHandler.sendMsgToClient(formattedMsg);
  }

  /**
   * Gets the voting information vote and hasVoted from clientHandler and this values to those values.
   * Sets clientHandler fields to default: vote = Integer.MAX_VALUE , hasVoted = false
   */
  @Override
  public void getVoteFromClientHandler() {
    vote = clientHandler.getVote();
    hasVoted = clientHandler.getHasVoted();
    clientHandler.setVote(Integer.MAX_VALUE);
    clientHandler.setHasVoted(false);
    /*
    * if vote wasn't valid, make sure, the passenger field hasVoted == false, probably redundant but better be safe than sorry
    */
    if(vote == Integer.MAX_VALUE) {
      hasVoted = false;
    }
  }

}

