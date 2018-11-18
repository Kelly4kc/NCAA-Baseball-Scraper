package scrapers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.NCAAUtils;

public class NCAAPlayByPlayScraper {

  private static final int SCHEDULE_GAME_ID = 21;

  private static final String[] HEADER = new String[] {"Game ID", "Inning", "Away Team",
      "Home Team", "Team", "Event ID", "Event", "Score"};

  public static void scrapePlayByPlay(int year, int division) {
    String directoryName = "ncaa_" + year + "_D" + division + "/ncaa_play_by_play";
    new File(directoryName).mkdirs();
    String readFileName =
        "ncaa_" + year + "_D" + division + "/ncaa_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = directoryName + "/ncaa_play_by_play_" + year + "_D" + division + ".csv";

    CSVReader reader = NCAAUtils.CSVReader(readFileName);
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, HEADER);

    Iterator<String[]> it = reader.iterator();
    it.next();
    int numBoxScores = 0;
    HashSet<Integer> completedBoxScores = new HashSet<>();
    while (it.hasNext()) {
      String[] gameInfo = it.next();
      if (completedBoxScores.add(Integer.parseInt(gameInfo[SCHEDULE_GAME_ID]))) {
        numBoxScores++;
        System.out.print(numBoxScores + ": ");
        try {
          TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
          System.out.println("Error occurred while waiting to reconnect.");
          System.exit(0);
        }
        List<String[]> game = scrapeGamePlayByPlay(year, gameInfo[SCHEDULE_GAME_ID]);
        writer.writeAll(game);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write.");
          System.exit(0);
        }
      }
    }
    System.out.println("Done!");
  }

  public static void scrapeTeamPlayByPlay(int year, int division, String team) {
    String directoryName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_play_by_play";
    new File(directoryName).mkdirs();
    String readFileName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = directoryName + "/" + team.toLowerCase().replaceAll(" ", "_")
        + "_play_by_play_" + year + "_D" + division + ".csv";

    CSVReader reader = NCAAUtils.CSVReader(readFileName);
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, HEADER);

    Iterator<String[]> it = reader.iterator();
    it.next();
    int numBoxScores = 0;
    while (it.hasNext()) {
      String[] gameInfo = it.next();
      numBoxScores++;
      System.out.print(numBoxScores + ": ");
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        System.out.println("Error occurred while waiting to reconnect.");
        System.exit(0);
      }
      List<String[]> game = scrapeGamePlayByPlay(year, gameInfo[SCHEDULE_GAME_ID]);
      writer.writeAll(game);
      try {
        writer.flush();
      } catch (IOException e) {
        System.out.println("Unable to write.");
        System.exit(0);
      }
    }

    System.out.println("Done!");
  }

  private static List<String[]> scrapeGamePlayByPlay(int year, String gameID) {
    String url = NCAAUtils.BASE_URL + "/game/play_by_play/" + gameID;
    try {
      TimeUnit.MILLISECONDS.sleep(250);
    } catch (InterruptedException e) {
      System.out.println("Error occurred while waiting to reconnect.");
      System.exit(0);
    }
    Document doc = NCAAUtils.getWebPage(url);
    Elements tables = doc.select("table");
    int inning = 0;
    int eventID = 0;
    String endScore = "";
    String awayTeam = null;
    String homeTeam = null;
    List<String[]> events = new ArrayList<>();
    for (int i = 4; i < tables.size(); i++) {
      Elements allRows = tables.get(i).select("tr");
      if (i % 2 == 1) {
        Elements rows = allRows.select("tr");
        int j = 0;
        inning++;
        for (Element line : rows) {
          Elements columns = line.select("td");
          if (j == 0) {
            awayTeam = columns.get(0).text();
            homeTeam = columns.get(2).text();
            j++;
          } else {
            String awayPBP = columns.get(0).text();
            String scorePBP = columns.get(1).text();
            String homePBP = columns.get(2).text();
            if (i == tables.size() - 1) {
              endScore = scorePBP;
            }
            if (awayPBP.contains("R: ") && awayPBP.contains("H: ") && awayPBP.contains("E: ")
                && awayPBP.contains("LOB: ")) {
              String[] inningSummary = new String[] {gameID, String.valueOf(inning), awayTeam,
                  homeTeam, "Inning Summary", awayPBP, homePBP, scorePBP};
              events.add(inningSummary);
            } else {
              if (!awayPBP.equals("")) {
                if (gameID.equals("4541970")) {
                  if (awayPBP
                      .equals("McConnell flied out to cf (1-0 B)3a Koppens3a Walsh3a Solomon.")) {
                    awayPBP =
                        "McConnell flied out to cf (1-0 B)3a Koppens advanced to second3a Walsh advanced to third3a Solomon scored.";
                  }
                }
                String[] awayPBPLine = new String[] {gameID, String.valueOf(inning), awayTeam,
                    homeTeam, awayTeam, String.valueOf(eventID), awayPBP, scorePBP};
                events.add(awayPBPLine);
              } else {
                if (!homePBP.equals("")) {
                  String[] homePBPLine = new String[] {gameID, String.valueOf(inning), awayTeam,
                      homeTeam, homeTeam, String.valueOf(eventID), homePBP, scorePBP};
                  events.add(homePBPLine);
                }
              }
              eventID++;
            }
          }
        }
      }
      if (i == tables.size() - 1) {
        System.out.print(gameID + " ");
        System.out.println(awayTeam + " " + endScore + " " + homeTeam);
      }
    }

    return events;
  }
}
