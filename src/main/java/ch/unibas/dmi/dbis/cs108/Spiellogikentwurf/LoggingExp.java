package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingExp {

  public static final Logger LOGGER = LogManager.getLogger();

  public static void main(String[] args) {
    LOGGER.debug("I’m not printed by default ");
    LOGGER.error("An error , printed by default ");
    LOGGER.info("We can log variables with string concatenation :" + args);
    LOGGER.info("Or like this :{} ", args);
  }
}
