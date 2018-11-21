import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import play_by_play_objects.PlayByPlayGame;
import play_by_play_objects.PlayByPlayInning;
import play_by_play_objects.PlayByPlayLine;
import utils.NCAAUtils;

public class NCAAPlayByPlayGamesFromPlayByPlay {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    int year = 2018;
    String readFileName =
        "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_play_by_play_" + year + "_D1.csv";
    String writeFileName = "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_pbp_" + year + "_D1.csv";
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, new String[] {"Game ID", "Inning",
        "Away Team", "Home Team", "Team", "Event ID", "Event", "Score"});
    ArrayList<PlayByPlayGame> allGames = getPlayByPlayFromFile(year, readFileName);
    for (PlayByPlayGame game : allGames) {
      game.writeGame(writer);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }

  public static ArrayList<PlayByPlayGame> getPlayByPlayFromFile(int year, String fileName) {
    ArrayList<PlayByPlayGame> games = new ArrayList<>();
    CSVReader reader = NCAAUtils.CSVReader(fileName);
    Iterator<String[]> it = reader.iterator();
    it.next();
    String[] line = it.next();
    while (it.hasNext()) {
      PlayByPlayGame game = new PlayByPlayGame(year, Integer.parseInt(line[0]),
          Integer.parseInt(line[1]), line[2], line[3]);
      int inning = 1;
      while (line != null && inning <= Integer.parseInt(line[1])) {
        inning = Integer.parseInt(line[1]);
        PlayByPlayInning inningPlayByPlay = new PlayByPlayInning(year, Integer.parseInt(line[0]),
            Integer.parseInt(line[1]), line[2], line[3]);
        while (!line[4].equals("Inning Summary")) {
          inningPlayByPlay.addLine(new PlayByPlayLine(year, line));
          line = it.next();
        }
        inningPlayByPlay.setAwaySummary(line[5]);
        inningPlayByPlay.setHomeSummary(line[6]);
        inningPlayByPlay.setScore(line[7]);
        game.addInning(inningPlayByPlay);
        line = it.next();
      }
      games.add(game);
    }
    return games;
  }
}
