package scrapers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import utils.Type;

public class NCAABoxScoreScraper {

  private static final int BOX_SCORE_INFO_GAME_ID = 0;
  private static final int BOX_SCORE_INFO_DATE = 1;
  private static final int BOX_SCORE_INFO_LOCATION = 2;
  private static final int BOX_SCORE_INFO_ATTENDANCE = 3;
  private static final int BOX_SCORE_INFO_NUM_INNINGS = 4;
  private static final int BOX_SCORE_INFO_TEAM_NAME = 5;

  private static final int SCHEDULE_DATE = 7;
  private static final int SCHEDULE_LOCATION = 13;
  private static final int SCHEDULE_GAME_ID = 21;
  private static final String[] GAME_INFO_HEADER =
      new String[] {"Game ID", "Date", "Location", "Attendance", "Number of Innings", "Team",};
  private static final String[] HITTING_HEADER = new String[] {"Game ID", "Team Name", "Player ID",
      "Player Name", "Position", "G", "R", "AB", "H", "2B", "3B", "TB", "HR", "RBI", "BB", "HBP",
      "SF", "SH", "K", "DP", "CS", "Picked Off", "SB", "2 Out RBI"};
  private static final String[] PITCHING_HEADER = new String[] {"Game ID", "Team Name", "Player ID",
      "Player Name", "Position", "G", "App", "GS", "IP", "CG", "H", "R", "ER", "BB", "SO", "SHO",
      "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB", "IR", "IRS", "SHA", "SFA",
      "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] FIELDING_HEADER = new String[] {"Game ID", "Team Name", "Player ID",
      "Player Name", "Position", "G", "PO", "TC", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};

  public static void scrapeGameInfo(int year, int division) {
    String directoryName = "ncaa_" + year + "_D" + division + "/ncaa_box_scores";
    new File(directoryName).mkdirs();
    String readFileName =
        "ncaa_" + year + "_D" + division + "/ncaa_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = "ncaa_box_score_info_" + year + "_D" + division;

    String fileName = NCAAUtils.createDirectoryAndCSVFile(directoryName, writeFileName);

    getGameInfoFromSchedule(readFileName, fileName);
  }

  public static void scrapeTeamGameInfo(int year, int division, String team) {
    String directoryName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_box_scores";
    new File(directoryName).mkdirs();
    String readFileName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = team.toLowerCase().replaceAll(" ", "_") + "_box_score_info_" + year
        + "_D" + division;

    String fileName = NCAAUtils.createDirectoryAndCSVFile(directoryName, writeFileName);

    getGameInfoFromSchedule(readFileName, fileName);
  }

  public static void scrapeBoxScores(int year, int division, Type type) {
    String directoryName = "ncaa_" + year + "_D" + division + "/ncaa_box_scores";
    new File(directoryName).mkdirs();
    String[] header = null;
    String readFileName =
        "ncaa_" + year + "_D" + division + "/ncaa_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = null;
    switch (type) {
      case Hitting:
        header = HITTING_HEADER;
        writeFileName =
            "ncaa_hitting_box_scores_" + year + "_D" + division;
        break;
      case Pitching:
        header = PITCHING_HEADER;
        writeFileName =
            "ncaa_pitching_box_scores_" + year + "_D" + division;
        break;
      case Fielding:
        header = FIELDING_HEADER;
        writeFileName =
            "ncaa_fielding_box_scores_" + year + "_D" + division;
        break;
    }

    String fileName = NCAAUtils.createDirectoryAndCSVFile(directoryName, writeFileName);

    getBoxScoresFromSchedule(readFileName, fileName, header, year, header.length, type);
  }

  public static void scrapeTeamBoxScores(int year, int division, String team, Type type) {
    String directoryName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_box_scores";
    new File(directoryName).mkdirs();
    String[] header = null;
    String readFileName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_schedule_" + year + "_D" + division + ".csv";
    String writeFileName = null;

    switch (type) {
      case Hitting:
        header = HITTING_HEADER;
        writeFileName = directoryName + "/" + team.toLowerCase().replaceAll(" ", "_")
            + "_hitting_box_scores_" + year + "_D" + division;
        break;
      case Pitching:
        header = PITCHING_HEADER;
        writeFileName = directoryName + "/" + team.toLowerCase().replaceAll(" ", "_")
            + "_pitching_box_scores_" + year + "_D" + division;
        break;
      case Fielding:
        header = FIELDING_HEADER;
        writeFileName = directoryName + "/" + team.toLowerCase().replaceAll(" ", "_")
            + "_fielding_box_scores_" + year + "_D" + division;
        break;
    }

    String fileName = NCAAUtils.createDirectoryAndCSVFile(directoryName, writeFileName);

    getBoxScoresFromSchedule(readFileName, fileName, header, year, header.length, type);
  }

  private static List<String[]> scrapeBoxScore(int year, String url, int numStats, String gameID) {
    List<String[]> boxScore = new ArrayList<>();
    Document doc = NCAAUtils.getWebPage(url);
    Elements tables = doc.select("table");
    for (int i = 5; i < 7; i++) {
      Element teamStats = tables.get(i);
      Elements teamStatLines = teamStats.select("tr");
      String teamName = teamStatLines.remove(0).text();
      teamStatLines.remove(0);
      for (Element line : teamStatLines) {
        Elements lineStats = line.select("td");
        String[] stats = new String[numStats];
        stats[0] = gameID.toString();
        stats[1] = teamName;
        int j = 3;
        String playerId = "";
        for (Element stat : lineStats) {
          if (j == 3) {
            try {
              String playerURL = NCAAUtils.getURL(stat);
              playerId = NCAAUtils.getIDFromURL(playerURL);
              stats[2] = playerId;
            } catch (NullPointerException n) {
              playerId = "";
            }
          }
          if (stat.text().equals("")) {
            stats[j] = "0";
          } else {
            stats[j] = stat.text().replace("/", "");
          }
          j++;
        }
        boxScore.add(stats);
      }
    }
    System.out.println("Game ID: " + gameID);
    return boxScore;
  }

  private static void getGameInfoFromSchedule(String schedule, String writeFileName) {
    CSVReader reader = NCAAUtils.CSVReader(schedule);
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, GAME_INFO_HEADER);
    int numBoxScores = 0;
    Iterator<String[]> it = reader.iterator();
    it.next();
    HashSet<Integer> completedBoxScores = new HashSet<>();
    while (it.hasNext()) {
      List<String[]> gameBoxScoreInfo = new ArrayList<>();
      String[] gameInfo = it.next();
      if (completedBoxScores.add(Integer.parseInt(gameInfo[SCHEDULE_GAME_ID]))) {
        List<String> teams = new ArrayList<>();
        List<String> scores = new ArrayList<>();
        numBoxScores++;
        String url = NCAAUtils.BASE_URL + "/game/box_score/" + gameInfo[SCHEDULE_GAME_ID]
            + "?year_stat_category_id=11953";
        try {
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          System.out.println("Error occurred while waiting to reconnect.");
          System.exit(0);
        }
        Document doc = NCAAUtils.getWebPage(url);
        Element allInnings = doc.getElementsByAttribute("align").select("table").get(0);
        Element gameDetails = doc.select("table").get(2);

        String gameId = gameInfo[SCHEDULE_GAME_ID];
        String date = "";
        String location = "";
        String attendance = "";
        if (gameDetails.select("tr").size() > 0) {
          date = gameDetails.select("tr").get(0).select("td").get(1).text();
          if (gameDetails.select("tr").size() > 1) {
            location = gameDetails.select("tr").get(1).select("td").get(1).text();
            if (gameDetails.select("tr").size() > 2) {
              attendance = gameDetails.select("tr").get(2).select("td").get(1).text();
            }
          } else {
            location = gameInfo[SCHEDULE_LOCATION];
          }
        } else {
          date = gameInfo[SCHEDULE_DATE];
        }

        for (int i = 1; i < 3; i++) {
          Elements inningsHeader = allInnings.getElementsByTag("tr").get(i).getElementsByTag("td");

          Element team = inningsHeader.remove(0);

          String[] row = new String[GAME_INFO_HEADER.length + inningsHeader.size()];

          String numInnings = String.valueOf(inningsHeader.size() - 3);
          String teamName = team.text();
          teams.add(teamName);
          row[BOX_SCORE_INFO_GAME_ID] = gameId;
          row[BOX_SCORE_INFO_DATE] = date;
          row[BOX_SCORE_INFO_LOCATION] = location;
          row[BOX_SCORE_INFO_ATTENDANCE] = attendance;
          row[BOX_SCORE_INFO_NUM_INNINGS] = numInnings;
          row[BOX_SCORE_INFO_TEAM_NAME] = teamName;

          for (int j = 0; j < inningsHeader.size(); j++) {
            row[j + GAME_INFO_HEADER.length] = inningsHeader.get(j).text();
            if (j == inningsHeader.size() - 3) {
              scores.add(inningsHeader.get(j).text());
            }
          }
          gameBoxScoreInfo.add(row);
        }
        writer.writeAll(gameBoxScoreInfo);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write.");
          System.exit(0);
        }
        System.out.print(numBoxScores + ": " + gameInfo[SCHEDULE_GAME_ID] + ": ");
        System.out.println(gameDetails.text());
        System.out.print(numBoxScores + ": " + gameInfo[SCHEDULE_GAME_ID] + ": ");
        System.out
            .println(teams.get(0) + " " + scores.get(0) + "-" + scores.get(1) + " " + teams.get(1));
      }
    }
    System.out.println("Done! " + numBoxScores + " box scores analyzed.");
  }

  private static void getBoxScoresFromSchedule(String schedule, String writeFileName,
      String[] header, int year, int numStats, Type type) {
    CSVReader reader = NCAAUtils.CSVReader(schedule);
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, header);
    int adjustment = 0;
    switch (type) {
      case Hitting:
        adjustment = NCAAUtils.HITTING;
        break;
      case Pitching:
        adjustment = NCAAUtils.PITCHING;
        break;
      case Fielding:
        adjustment = NCAAUtils.FIELDING;
        break;
    }

    String categoryId = String.valueOf(NCAAUtils.CATEGORY_IDS[2018 - year] + adjustment);

    Iterator<String[]> it = reader.iterator();
    it.next();
    int numBoxScores = 0;
    HashSet<String> completedBoxScores = new HashSet<>();
    while (it.hasNext()) {
      String[] gameInfo = it.next();
      if (completedBoxScores.add(gameInfo[SCHEDULE_GAME_ID])) {
        numBoxScores++;
        String url = NCAAUtils.BASE_URL + "/game/box_score/" + gameInfo[SCHEDULE_GAME_ID]
            + "?year_stat_category_id=" + categoryId;
        System.out.print(numBoxScores + ": ");
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          System.out.println("Error occurred while waiting to reconnect.");
          System.exit(0);
        }
        List<String[]> boxScore = scrapeBoxScore(year, url, numStats, gameInfo[SCHEDULE_GAME_ID]);
        writer.writeAll(boxScore);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write to file.");
          System.exit(0);
        }
      }
    }
    System.out.println("Done! " + numBoxScores + " box scores analyzed.");
  }
}
