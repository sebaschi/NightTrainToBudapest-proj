package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import java.util.Random;

// Creates a String beginning with "player_" followed by 4 random letters

public class NameGenerator {
    static String randomName(String username) {
        StringBuilder name;
        while (true) {

            name = new StringBuilder();
            Random r = new Random();
            for (int i = 0; i < 4; i++) {
                int c = r.nextInt(10);
                name.append(c);
            }
            if (!AllClientNames.allNames("").contains(username + name)) {
                break;
            }
        }
        return username + name;
    }

}
