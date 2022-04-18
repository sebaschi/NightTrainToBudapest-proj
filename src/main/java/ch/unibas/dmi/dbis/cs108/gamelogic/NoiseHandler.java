package ch.unibas.dmi.dbis.cs108.gamelogic;


import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;

/**
 * Determines who heard something (via Passenger Array currently in GameFunctions 'passengerTrain')
 * and broadcasts noise message to them (via ServerGameInfoHandler)
 */
public class NoiseHandler {
  /** Updates the amount of times passengers on the train heard ghosts walk by them. Differentiates between two
   * cases: if the active ghost (predator) is to the right of his victim, the Passenger array is
   * being walked through from right to left (from the predator's position back to the victim's
   * position), otherwise the other way around. One call of noiseNotifier only deals with one
   * predator infecting a victim, so if there are already multiple ghosts in the game, the method
   * should be called for each of them individually.

   * @param passengers passengers of the train the game is played in
   * @param predator ghost that has infected a human player during this night (called upon as
   *                 passenger for convenience reasons)
   * @param victim human player who has been turned into a ghost this night
   * @param noiseAmount array containing information about how many times each passenger heard a noise this night
   * @param game current game instance
   * @return updated array with info on who heard how many noises
   */
  public int[] noiseNotifier(Passenger[] passengers, Passenger predator, Passenger victim, int[] noiseAmount, Game game) {
    if (predator.getPosition() - victim.getPosition()
        > 0) { // if predator is to the right of victim
      for (int i = predator.getPosition() - 1; i > victim.getPosition(); i--) {
        noiseAmount[i]++;
      }
    } else { // if predator is to the left of victim
      for (int i = predator.getPosition() + 1; i < victim.getPosition(); i++) {
        noiseAmount[i]++;
      }
    }
    return noiseAmount;
  }
}
