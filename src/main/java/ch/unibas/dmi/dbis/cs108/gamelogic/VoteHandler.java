package ch.unibas.dmi.dbis.cs108.gamelogic;

/**
 * Handles the Event of Voting for Humans and Ghosts. Differentiates between day and night (human Ghost) and handles votes accordingly.
 * - Sends voting request to passengers that need to be concerned
 * - collects voting results
 * - calculates who was voted for
 * - decides consequence of vote:
 *      - Is Og Ghost: Humans win
 *      - Is last Human: Ghosts win
 *      - Is just a human: Message "x is a human"
 *      - Is a peasant Ghost -> kickoff
 *
 * (All messages going to Clients are handled via ServerGameInfoHandler)
 *
 * TODO: Think about if the timer needs to be implemented here or in the Game class
 */

public class VoteHandler {


}
