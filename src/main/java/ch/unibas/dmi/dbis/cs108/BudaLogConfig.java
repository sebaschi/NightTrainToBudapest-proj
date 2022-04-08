package ch.unibas.dmi.dbis.cs108;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 */
public class BudaLogConfig {

  public Logger LOGGER;

  public BudaLogConfig(Logger LOGGER) {
    this.LOGGER = LOGGER;
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    Configuration config = ctx.getConfiguration();
    LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
    loggerConfig.setLevel(Level.DEBUG); // change level here
    ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
  }

}
