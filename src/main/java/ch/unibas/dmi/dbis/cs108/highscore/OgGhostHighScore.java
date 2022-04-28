package ch.unibas.dmi.dbis.cs108.highscore;

import ch.unibas.dmi.dbis.cs108.BudaLogConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * used for logging OG ghost highscore
 */
public class OgGhostHighScore {

  public static final Logger LOGGER = LogManager.getLogger(OgGhostHighScore.class);
  public static final BudaLogConfig l = new BudaLogConfig(LOGGER);

  static ArrayList<String> ogGhostWinners = new ArrayList<>();
  static final File ogGhostFile = new File("OgGhostWinners.txt");

  /**
   * Writes the current state of the ogGhostWinners String[] to the ogGhostFile, then closes
   * the fileWriter.
   */
  static void writeOgGhostWinnersToFile() {
    try {
      FileWriter fileWriter = new FileWriter(ogGhostFile, false);
      for (String name : ogGhostWinners) {
        fileWriter.write(name);
        fileWriter.write(System.lineSeparator());
      }
      fileWriter.close();
    } catch (Exception e) {
      LOGGER.debug("Exception while trying to write ogGhostWinners to file.");
      LOGGER.debug(e.getMessage());
    }
  }

  /**
   * adds the given name to the list of og ghost winners and updates the file listing the og ghost
   * winners via writeOgGhostWinnersToFile
   */
  public static void addOgGhostWinner(String name){
    ogGhostWinners.add(name);
    writeOgGhostWinnersToFile();
  }

  /**
   * outputs the highscore list as it could be shown to clients.
   * @return
   */
  public static String formatGhostHighscoreList() {

    //create the hashMap which lists all names along with their number of appearances
    //int max = 0;
    HashMap<String, Integer> hm = new HashMap<>();
    for (String name: ogGhostWinners) {
      if (hm.containsKey(name)) {
        hm.replace(name, hm.get(name) + 1);
      } else {
        hm.put(name, 1);
      }
      //if (max < hm.get(name)) max = hm.get(name);
    }

    StringBuilder sb = new StringBuilder();


    //add the 5 highest scoring peeps to the StringBuilder sb
    for (int i = 0; i < 5; i++) {
      //find first place among the remaining members of hm. ("remaining" because we remove people once theyre listed)
      if (!hm.isEmpty()) {
        String firstplace = (String) hm.keySet().toArray()[0];    //choose one candidate for first place just so we dont get null pointer
        for (String name: hm.keySet()) {
          if (hm.get(name) > hm.get(firstplace)) firstplace = name;
        }
        sb.append(firstplace).append(": ").append(hm.get(firstplace)).append(" wins.")
            .append(System.lineSeparator());
        hm.remove(firstplace);
      }
    }

    return sb.toString();
  }

  /**
   * reads the highscore file (or if not yet present create it) and reads the ogGhostWinners;
   */
  public static void main(String[] args) {
    try {
      ogGhostWinners = new ArrayList<>();
      //if not already present, the following creates the file and enters the if statement.
      //if already present, it reads what's already in the file into the ogGhostWinners array.
      if (!ogGhostFile.createNewFile()) {
        BufferedReader buffreader = new BufferedReader(new FileReader(ogGhostFile));
        String line = buffreader.readLine();
        while (line != null) {
          ogGhostWinners.add(line);
          line = buffreader.readLine();
        }
      }

      /*
      addOgGhostWinner("Seraina");
      ogGhostWinners.add("Jonas, the ultimate winner");

      writeOgGhostWinnersToFile();
      System.out.println(formatGhostHighscoreList());
      */



    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}