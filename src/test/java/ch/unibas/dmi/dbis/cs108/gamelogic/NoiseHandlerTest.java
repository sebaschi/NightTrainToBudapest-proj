package ch.unibas.dmi.dbis.cs108.gamelogic;

import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.ClientHandler;
import ch.unibas.dmi.dbis.cs108.multiplayer.server.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class NoiseHandlerTest {
    Passenger[] passengers = new Passenger[6];  // create new collection of passengers
    Passenger predator = new Passenger();
    Passenger victim = new Passenger();  // player who will be turned into a ghost this night
    int[] noiseAmount = new int[6];  // collect amount of times a ghost walked by the humans (= noises heard)
    int[] noiseAmountGuideline = new int[6];  // array that represents the right people having heard the right noise amount

    @BeforeEach
    void preparation() {
        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = new Passenger();
        }
        predator.setGhost();
    }

    @Test
    @DisplayName("The right players hear the right amount of noises")

    void noiseTest1() {
        /* test 1: people heard the right noise amount for following case: ghost is at position 5 and ghostifies
        player at position 2 --> noises at positions 3 and 4
         */
        predator.setPosition(5);
        victim.setPosition(2);
        passengers[predator.getPosition()] = predator;
        passengers[victim.getPosition()] = victim;

        for (int i = 3; i < 5; i++) {
            noiseAmountGuideline[i]++;
        }
        /* call main method of NoiseHandler so that all passengers are notified about all noises (notifications are
        updated in noiseAmount array) */
        for (int i = 0; i < passengers.length; i++) {
            if (passengers[i].getIsGhost() && i != victim.getPosition()) {
                NoiseHandler n = new NoiseHandler();
                noiseAmount = n.noiseNotifier(passengers[i], victim, noiseAmount);
            }
        }
        assertArrayEquals(noiseAmountGuideline, noiseAmount);
    }
}