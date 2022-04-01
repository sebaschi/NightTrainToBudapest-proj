package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumanNPC extends Human {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a new HumanNPC.
   *
   * @param position position on the train
   * @param name     player name. if null, then a default name is used.
   */
  public HumanNPC(int position, String name) {
    this.position = position;
    this.clientHandler = null;
    isGhost = false;
    isPlayer = false;
    kickedOff = false;
    if (name == null) {
      this.name = "Robot Nr. " + position;
    } else {
      this.name = name;
    }
  }
}
