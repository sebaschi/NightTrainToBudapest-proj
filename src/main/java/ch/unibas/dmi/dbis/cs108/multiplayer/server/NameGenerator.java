package ch.unibas.dmi.dbis.cs108.multiplayer.server;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NameGenerator {
  public static final Logger LOGGER = LogManager.getLogger();
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  /**
   * Creates a random alteration of a Name by adding 4 numbers at the end of the Name that shall be
   * altered
   *
   * @param username the to be altered username
   * @return username + four numbers
   */
  static String randomName(String username) {
    StringBuilder name;
    while (true) {

      name = new StringBuilder();
      Random r = new Random();
      for (int i = 0; i < 4; i++) {
        int c = r.nextInt(10);
        name.append(c);
      }
      if (!AllClientNames.allNames("").contains(username + name)) {
        break;
      }
    }
    return username + name;
  }

}
