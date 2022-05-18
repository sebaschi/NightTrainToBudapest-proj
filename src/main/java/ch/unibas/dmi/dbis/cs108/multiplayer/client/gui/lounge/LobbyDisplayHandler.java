package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LobbyDisplayHandler {
  public static final Logger LOGGER = LogManager.getLogger(LobbyDisplayHandler.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


  private static HashSet<LobbyModel> lobbies = new HashSet<>();
  private static boolean threadRunning = false;

  public static HashSet<LobbyModel> getLobbies() {
    return lobbies;
  }

  public static void setThreadRunning(boolean threadRunning) {
    LobbyDisplayHandler.threadRunning = threadRunning;
  }

  public static boolean isThreadRunning() {
    return threadRunning;
  }

  /**
   * searches lobbies for a lobby with a certain id
   * @param id the int representing a Lobby id to be lookes for
   * @return the LobbyModel with the id if found and null otherwise
   */
  public LobbyModel searchForLobbyId(int id) {
    for(LobbyModel lobby : lobbies) {
      if (lobby.getId() == id) {
        return lobby;
      }
    }
    return null;
  }

  public void updateLobbies(String data) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        while(isThreadRunning()) {
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        setThreadRunning(true);
        try {
          for (LobbyModel model : lobbies) {
            model.setHasBeenVisited(false);
          }
          String[] lobbiesString = data.split("\\$");
          //System.out.println(lobbiesString.length);
          for (String lobby : lobbiesString) {
            String[] oneLobby = lobby.split(":");
            //System.out.println(oneLobby.length);
            int id = Integer.parseInt(oneLobby[0]);
            String admin = oneLobby[1];
            boolean isOpen = Boolean.parseBoolean(oneLobby[2]);
            if (searchForLobbyId(id) == null) { //the lobby is new and has not been saved yet
              addLobbyFromString(id, admin, isOpen, oneLobby);
            } else { // the lobby exists but might need to be updated
              updateExistingLobby(id, admin, isOpen, oneLobby);
            }
          }
          //System.out.println("lobby size before removal: " + lobbies.size());
          lobbies.removeIf(
              lobby -> !lobby.isHasBeenVisited()); //removes all lobbies that aren't in this string
        } catch (Exception e) {
          e.printStackTrace();
          LOGGER.info("empty list");
        } finally {
          setThreadRunning(false);
        }
      }

    }).start();
  }

  private void addLobbyFromString(int id, String admin, boolean isOpen, String[] oneLobby) {
    //System.out.println("add Lobby");
    LobbyModel newLobby = new LobbyModel(id, admin);
    newLobby.setHasBeenVisited(true);
    //System.out.println(newLobby);
    newLobby.setLobbyIsOpen(isOpen);
    for (int i = 3; i < oneLobby.length; i++) {
      newLobby.addMember(oneLobby[i]);
      //System.out.println(oneLobby[i]);
    }
    lobbies.add(newLobby);
    //System.out.println("lobby size: " + lobbies.size());
  }

  private void updateExistingLobby(int id, String admin,boolean isOpen, String[] oneLobby) {
    //System.out.println("update");
    LobbyModel oldLobby = searchForLobbyId(id);
    if (!oldLobby.getAdmin().equals(admin)) {
      oldLobby.setAdmin(admin);
    }
    oldLobby.setHasBeenVisited(true);
    oldLobby.setLobbyIsOpen(isOpen);
    oldLobby.removeAllMembers();
    for (int i = 3; i < oneLobby.length; i++) {
      oldLobby.addMember(oneLobby[i]);
    }
  }

  public static void main(String[] args) {
    LobbyDisplayHandler handler = new LobbyDisplayHandler();
    String lobby = "1:Seraina:true:Alex:Jonas$2:Sebi:false:Maria:Claudia:Hansli$3:Vanessa:true:Lara:Flu";
    handler.updateLobbies(lobby);
    //System.out.println("lobby size in main:" + lobbies.size());
    for (LobbyModel model : lobbies) {
      //System.out.println(model);
      System.out.println("Lobby " + model.getId() + " " + model.isLobbyIsOpen() + " (" + model.getAdmin() + "):");
      for (String member : model.getMembers()) {
        System.out.println("- " + member);
      }
    }

    String lobby2 = "1:Seraina:true:Alex:Jonas$3:Vanessa:true:Lara:Flu:Sebastian";
    handler.updateLobbies(lobby2);
    System.out.println("lobby size in main:" + lobbies.size());
    for (LobbyModel model : lobbies) {
      //System.out.println(model);
      System.out.println("Lobby " + model.getId() + " " + model.isLobbyIsOpen() + " (" + model.getAdmin() + "):");
      for (String member : model.getMembers()) {
        System.out.println("- " + member);
      }
    }

    System.out.println("done");
  }


}
