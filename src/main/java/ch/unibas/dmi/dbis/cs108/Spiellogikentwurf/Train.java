package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

import ch.unibas.dmi.dbis.cs108.Klassenstruktur.Passenger;

import java.util.Arrays;

public class Train {
    int[] orderOfTrain; //gives the random order in which the passengers enter the train

    /**
     * Constructs a Train with orderOfTrain of the size nrOfPlayers, filled with a Random order of the numbers 0-5
     * @param nrOfPlayers sets how many Players fit in the Train
     * @param nrOfUsers sets how many of the Players are Users (vs NPC's)
     * @throws TrainOverflow if you want to play with to many users (Train is to small)
     */
    Train (int nrOfPlayers, int nrOfUsers) throws TrainOverflow {

        if (nrOfPlayers <  nrOfUsers) {
            throw new TrainOverflow(); //ToDo: What kind of Exception must be thrown here and who handles it how?
        }

        int[] userTrain = new int[nrOfPlayers];
        int[] check = new int[nrOfPlayers];
        int i = 0;
        int zeroCounter = 0;
        while (i < nrOfPlayers) {
            int randomNr = (int) ((Math.random() * ((nrOfPlayers)) + 0));
            if (randomNr == 0) {
                if (zeroCounter == 0) {
                    i++;
                    zeroCounter++;
                }
            }
            if (!isInArray(check, randomNr)) {
                userTrain[i] = randomNr;
                check[randomNr] = randomNr;
                i++;
            }

        }
        this.orderOfTrain = userTrain;
    }

    /**
     * Checks if the key is to be found in an array
     * @param array the array to be searched
     * @param key the value searched for
     * @return true if and only if key is found
     */
    static public boolean isInArray(int[] array, int key) {
        int i = 0;
        while (i < array.length) {
            if (array[i] == key) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        System.out.println(isInArray(a,0));
        //Test
        try {
            Train t = new Train(6,1);
        } catch (TrainOverflow e) {
            System.out.println(e.getMessage());
        }

    }
}
