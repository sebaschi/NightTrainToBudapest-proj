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
   * Adjusts the name to avoid conflicts and returns it as a String. Namely:
   * If that name is already used by some other ClientHandler, it returns the name with some suffix.
   * Also, any ":" or "$" are removed, so they can be used for whisper chat.
   * Also, if the name is empty, it assigns a default value ("U.N. Owen").
   * @param name the name that is checked for
   * @return returns either just the name or added some suffix
   */
  public static String checkName(String name) {
    String tempname = name;                 //if this line is used, only duplicate names get a suffix.
    //String tempname = extendName(name);     //if this line is used, all clients get a suffix
    tempname = tempname.replace("$","");
    tempname = tempname.replace(":","");
    if (tempname.equalsIgnoreCase("")) {tempname = "U.N. Owen";}
    String rtrn = tempname;
    while (isTaken(rtrn)) {        //todo: handle the (very unlikely) case that all names are taken.
      rtrn = extendName(tempname);
    }
    return rtrn;
  }

}
