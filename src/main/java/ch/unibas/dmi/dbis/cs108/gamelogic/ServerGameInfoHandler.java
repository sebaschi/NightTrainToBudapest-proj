package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

/**
 * Handles all communications Server to Client concerning game state or game state related requests
 * - Needs a possibility to only send to Ghosts
 * - and only humans
 * TODO: Figure out what format the messages have, consider protocol add what is needed dont forget about parsing
 */

public class ServerGameInfoHandler {

  /**
   * TODO(Seraina): Handle NPC's Maybe do that in Passenger send methode!
   * Send a message "GVOTR" to a passenger urging them to vote for a human to infect
   * Currently only handles only Players, so send a message to corresponding client
   * @param passenger the passenger the message is meant to, should be a Ghost
   */
  public void sendVoteRequestGhosts(Passenger passenger){
    passenger.getClientHandler().sendMsgToClient("GVOTR");
  }


  /**
   * TODO(Seraina): Handle NPC's
   * Send a message "HVOTR" to a passenger urging them to vote for sm to kick off the train.
   * Currently only handles only Players, so send a message to corresponding client
   * @param passenger the passenger the message is meant to, can be either human or ghost
   */
  public void sendVoteRequestHumans(Passenger passenger){
    passenger.getClientHandler().sendMsgToClient("HVOTR");
  }

}
