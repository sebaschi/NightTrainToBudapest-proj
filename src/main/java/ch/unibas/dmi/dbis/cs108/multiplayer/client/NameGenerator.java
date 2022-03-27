package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import java.util.Random;

// Creates a String beginning with "player_" followed by 4 random letters

public class NameGenerator {
    static String randomName() {
        StringBuilder name = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            name.append(c);
        }
        return "player_" + name;
    }
    public static void main (String[] args) {
        System.out.println(randomName());
    }
}
