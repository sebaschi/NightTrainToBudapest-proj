package ch.unibas.dmi.dbis.cs108.sebaschi;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Ghost;
import ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur.Passenger;
import java.io.BufferedOutputStream;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that shall contain all non-game logic information relevant to a game session needed for
 * client-server and client-client communication.
 */
public class GameSessionData {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  CentralServerData globalData;

  Set<Passenger> passengers;
  Set<BufferedOutputStream> clientOutputStreams;
  Set<Ghost> ghosts;
}
