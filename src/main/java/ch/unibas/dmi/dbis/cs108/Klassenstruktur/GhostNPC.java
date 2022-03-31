package ch.unibas.dmi.dbis.cs108.Klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GhostNPC extends Ghost {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new GhostNPC. Should be used at game start or if a HumanNPC is turned into a ghost.
   *
   * @param position position on the train
   * @param name     player name. if null, then a default name is used.
   * @param isOG     true if the ghost is the original ghost.
   */
  public GhostNPC(int position, String name, boolean isOG) {
    this.isOG = isOG;
    this.position = position;
    this.clientHandler = null;
    isGhost = true;
    isPlayer = false;
    kickedOff = false;
      if (name == null) {
          this.name = "Robot Nr. " + position;
      } else {
          this.name = name;
      }
  }
}
