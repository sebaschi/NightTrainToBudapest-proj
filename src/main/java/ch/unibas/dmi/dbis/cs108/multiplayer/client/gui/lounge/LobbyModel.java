package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;


import java.util.HashSet;

/**
 * This is a simple model of a Lobby designed to save Lobby objects for display
 */
public class LobbyModel {
  private final int id;
  private String admin;
  private HashSet<String> members = new HashSet<String>(5);
  private boolean lobbyIsOpen = true;
  private boolean hasBeenVisited = false;

  public LobbyModel(int id, String admin) {
    this.id = id;
    this.admin = admin;
  }

  public void addMember(String name) {
    members.add(name);
  }

  public void removeMember(String name) {
    members.remove(name);
  }

  public void removeAllMembers() {
    members.clear();
  }

  public HashSet<String> getMembers() {
    return members;
  }

  public int getId() {
    return id;
  }

  public String getAdmin() {
    return admin;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  public void setHasBeenVisited(boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  public boolean isHasBeenVisited() {
    return hasBeenVisited;
  }

  public void setLobbyIsOpen(boolean lobbyIsOpen) {
    this.lobbyIsOpen = lobbyIsOpen;
  }

  public boolean isLobbyIsOpen() {
    return lobbyIsOpen;
  }
}
