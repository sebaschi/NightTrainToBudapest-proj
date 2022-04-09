package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.ClientVoteData;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Passenger {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  protected int position;               //the player's Cabin number (0 to 5)
  protected String name;                //the player's Name
  protected boolean isGhost;            //boolean regarding if the player is a ghost. Could probably be removed since ghost is a subclass but I'm keeping it in.
  protected boolean isOG = false;       //true if the player is the original ghost, false by default.
  protected boolean isPlayer;           //same here
  protected boolean kickedOff;          //true if the player has been voted off.
  protected ClientHandler clientHandler;//the socket for the client associated with this Passenger, for NPCs, this can be null.
  protected boolean hasVoted;           //true if the player gave his vote during voting time
  protected int vote;                   //saves the number of the player this passenger voted for during voting (0-5)

  /**
   * Sends a protocol message to the respective player or NPC.
   * @param msg the message that is sent to this player.
   **/
  public void send(String msg, Game game) {
    if (isPlayer) {
      String formattedMsg = ServerGameInfoHandler.format(msg,this,game);
      clientHandler.sendMsgToClient(formattedMsg);
    }
    LOGGER.warn("This object should not just be a passenger. Position:" + position);
  }

  /**
   * sets the Position of this passenger
   *
   * @param position the position of this passenger
   */
  public void setPosition(int position) {
    this.position = position;
  }

  /**
   * sets the name of this passenger.
   *
   * @param name the new name for this passenger.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * sets the kickedOff status of this Passenger
   *
   * @param kickedOff should be set to true if the passenger has been kicked off.
   */
  public void setKickedOff(boolean kickedOff) {
    this.kickedOff = kickedOff;
  }

  public void setGhost() {
    // changes this passenger's status from human to ghost
    isGhost = true;
  }
  public void setHasVoted(boolean voted) {
    // used to signal that this passenger voted during a voting
    hasVoted = voted;
  }

  public void setVote(int vote) {
    this.vote = vote;
  }

  public void setIsOg() {
    isOG = true;
  }

  public int getPosition() {
    return position;
  }


  public String getName() {
    return name;
  }

  public boolean getIsGhost() {
    return isGhost;
  }

  public boolean getIsOG() { return isOG; }

  public boolean getKickedOff() {
    return kickedOff;
  }

  public boolean getIsPlayer() {
    return isPlayer;
  }

  public boolean getHasVoted() { return hasVoted; } // returns true if player already voted during a voting

  public int getVote() { return vote; } // returns who this passenger voted for during a voting

  public ClientHandler getClientHandler() {
    return clientHandler;
  }

  /**
   * When called by NPC nothing should happen, because clientHandler = null
   */
  public void getVoteFromGameState(ClientVoteData clientVoteData,Game game) {
    LOGGER.debug("a NPC called this method hopefully: " + position);
  }

}
