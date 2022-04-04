package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.Client;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This Class Represents an Object containing different Maps, Lists and Sets wherein a server object
 * can find all needed data. An instance of this object can also be passed to other class-objects uf
 * they need the same data. This Class is used to query for information in collections.
 */
public class CentralServerData {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  private Set<Client> clientsOnServer;
  private Set<Game> activeGames;
  private Set<Game> gamesOpenToJoin;

  private Map<Client, Socket> clientSocketMap;
  private Map<Socket, Client> socketClientMap;
  private Map<Game,Client> gameClientMap;
}
