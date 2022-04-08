package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Attributes of a client visible to server.
 */
public class ClientAttributes {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);
  private String clientUserName;
  private boolean loggedIn;
  private boolean isInALobby;
  private Lobby clientsLobby;
}
