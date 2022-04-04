package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ghost extends Passenger {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  protected boolean isOG = false;             //true if the Ghost is the original ghost false by default.

  public boolean getIsOG() {
    return isOG;
  }

  public void setIsOG(boolean og) { isOG = og; };
}
