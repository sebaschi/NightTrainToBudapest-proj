package ch.unibas.dmi.dbis.cs108.gamelogic.klassenstruktur;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import ch.unibas.dmi.dbis.cs108.gamelogic.Game;
import ch.unibas.dmi.dbis.cs108.gamelogic.ServerGameInfoHandler;
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
  public HumanNPC(int position, String name, Game game) {
    super(game);
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

  @Override
  public void send(String msg) {
    ServerGameInfoHandler.humanNpcParser(this, msg, game);
  }

  /**
   * Currently returns a random integer for voting
   * @return integer between 0 and 5
   */
  public int vote(){
    return (int) (Math.random()*6);
  }

  public void noise() {
    clientHandler.broadcastChatMessage("I heard some noise tonight");
  }
}
