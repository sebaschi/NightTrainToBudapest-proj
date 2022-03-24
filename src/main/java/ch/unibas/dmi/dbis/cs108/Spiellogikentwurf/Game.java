package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

import ch.unibas.dmi.dbis.cs108.Klassenstruktur.Passenger;

import java.lang.reflect.Constructor;

public class Game {
    /**Can be extended for optional Game-settings**/
    protected int nrOfPlayers; //sets the length of the train
    protected int nrOfGhosts; // sets how many Ghosts we start witch
    protected int nrOfUsers; // safes how many clients are active in this Game
    Train train; // safes who sits where ToDo: Figure out who and where and if this needs to be changed i.e. when a client looses connection. (Schon gelöst mit timer?)

    /**
     * Constructs a Game instance where
     * @param nrOfPlayers is the length of the Train
     * @param nrOfGhosts is the number of OG Ghosts you want to start with  and
     * @param nrOfUsers is the number of active users at the time (non NPC's)
     */
   Game (int nrOfPlayers, int nrOfGhosts, int nrOfUsers) {
       this.nrOfPlayers = nrOfPlayers;
       this.nrOfGhosts = nrOfGhosts;
       this.nrOfUsers = nrOfUsers;
   }





}
