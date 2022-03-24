package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

public class GameFunctions {
    /**Can be extended for optional Game-settings**/
    int nrOfPlayers; //sets the length of the train
    int nrOfGhosts; // sets how many Ghosts we start witch
    int nrOfUsers; // safes how many clients are active in this Game

    GameFunctions (int nrOfPlayers, int nrOfGhosts, int nrOfUsers) {
        this.nrOfPlayers = nrOfPlayers;
        this.nrOfGhosts = nrOfGhosts;
        this.nrOfUsers = nrOfUsers;
    }

    public int getNrOfGhosts() {
        return nrOfGhosts;
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    public int getNrOfUsers() {
        return nrOfUsers;
    }


}
