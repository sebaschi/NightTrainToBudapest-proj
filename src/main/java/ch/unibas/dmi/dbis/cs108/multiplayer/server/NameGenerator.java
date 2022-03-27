package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import java.util.Random;


public class NameGenerator {

    /**
     * Creates a random alteration of a Name by adding 4 numbers at the end of the Name that shall be altered
     *
     * @param username the to be altered username
     * @return username + four numbers
     */
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
