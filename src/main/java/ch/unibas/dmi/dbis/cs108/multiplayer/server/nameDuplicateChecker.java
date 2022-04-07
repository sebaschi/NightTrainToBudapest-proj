package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import java.util.Random;

/**
 * This class is responsible for checking names for duplicates and assigning suffixes in case
 * of duplicate names.
 */
public class nameDuplicateChecker {
  static final String[] suffixes = new String[] {
      " from London",
      " of Prussia",
      " of Zagreb",
      " of Istanbul",
      " from Munich",
      ", the Belgian traveller",
      ", the wagon-lit conductor",
      ", the American",
      " the 3rd",
      ", Heir to the Throne of Liechtenstein",
      ", the private investigator",
      ", the butler",
      ", the mysterious stranger",
      ", the Bulgarian novelist",
      ", the French delegate",
      ", young and sweet, only 17",
      ", definitely not a ghost"
  };

  /**
   * Adds a randomly chosen suffix to the name.
   */
  static String extendName(String name) {
    Random r = new Random();
    return (name + suffixes[r.nextInt(suffixes.length)]);
  }

  /**
   * returns true if this name is already taken by some clientHandler.
   */
  static boolean isTaken(String name) {
    for (ClientHandler client : ClientHandler.getConnectedClients()) {
      if (client.getClientUserName().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the name as a String, if that name is already used by some other ClientHandler,
   * it returns the name with some suffix.
   */
  public static String singularName(String name) {
    String rtrn = name;                 //if this line is used, only duplicate names get a suffix.
    //String rtrn = extendName(name);     //if this line is used, all clients get a suffix
    while (isTaken(rtrn)) {        //todo: handle the (very unlikely) case that all names are taken.
      rtrn = extendName(name);
    }
    return rtrn;
  }

}
