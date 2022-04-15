package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Human extends Passenger {
  public static final Logger LOGGER = LogManager.getLogger(Human.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);


}
