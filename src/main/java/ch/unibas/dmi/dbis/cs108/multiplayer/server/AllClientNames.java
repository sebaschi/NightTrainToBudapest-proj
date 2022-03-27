package ch.unibas.dmi.dbis.cs108.multiplayer.server;

/**
 * This class is built to contain the usernames of all players in a single string. This allows a
 * duplicate check (ClientHandler) when a new player chooses a name: does the string with all
 * the previous names contain the new player's desired username? If yes, he is being assigned a
 * random name. If no, he can keep his desired name.
 */

public class AllClientNames {

  static StringBuilder names = new StringBuilder();

  /**
   * Saves a new name to the List of all Names
   *
   * @param currentName the new name to be added
   * @return All names adding the new currentName
   */
  public static String allNames(String currentName) {
    return names.append(currentName).toString();        //todo: might use a String<> instead.
  }
}
