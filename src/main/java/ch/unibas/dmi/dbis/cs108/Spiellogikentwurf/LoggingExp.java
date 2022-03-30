package ch.unibas.dmi.dbis.cs108.Spiellogikentwurf;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class LoggingExp {

  public static final Logger LOGGER = LogManager.getLogger();

  public static void main(String[] args) {

    /* change root logger level
     * copied from https://stackoverflow.com/questions/23434252/ - I think it works, but I don't know why
     */
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    Configuration config = ctx.getConfiguration();
    LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
    loggerConfig.setLevel(Level.DEBUG); // change level here
    ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.

    LOGGER.debug("I’m not printed by default ");
    LOGGER.error("An error , printed by default ");
    LOGGER.info("We can log variables with string concatenation :" + args);
    LOGGER.info("Or like this :{} ", args);
  }
}
