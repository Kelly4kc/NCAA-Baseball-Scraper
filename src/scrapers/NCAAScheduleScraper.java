package scrapers;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.NCAAUtils;

public class NCAAScheduleScraper {
  private static final int YEAR = 0;
  private static final int YEAR_ID = 1;
  private static final int DIVISION = 2;
  private static final int TEAM_CONFERENCE = 3;
  private static final int TEAM_NAME = 4;
  private static final int TEAM_ID = 5;
  private static final int TEAM_URL = 6;
  private static final int GAME_DATE = 7;
  private static final int OPPONENT_CONFERENCE = 8;
  private static final int OPPONENT_NAME = 9;
  private static final int OPPONENT_ID = 10;
  private static final int OPPONENT_URL = 11;
  private static final int NEUTRAL_SITE = 12;
  private static final int LOCATION = 13;
  private static final int HOME_TEAM = 14;
  private static final int WINNING_TEAM = 15;
  private static final int SCORE = 16;
  private static final int TEAM_SCORE = 17;
  private static final int OPPONENT_SCORE = 18;
  private static final int EXTRA_INNINGS = 19;
  private static final int INNINGS = 20;
  private static final int GAME_ID = 21;
  private static final int GAME_URL = 22;

  private static final String[] HEADER =
      new String[] {"Year", "Year ID", "Division", "Team Conference", "Team Name", "Team ID",
          "Team URL", "Game Date", "Opponent Conference", "Opponent Name", "Opponent ID",
          "Opponent URL", "Neutral Site", "Location", "Home Game", "Team Won", "Score",
          "Team Score", "Opponent Score", "Extra Innings", "Innings", "Game ID", "Game URL"};


  public static void scrapeSchedule(int year, int division) {
    long totalStartTime = System.currentTimeMillis();
    List<String[]> missedTeams = new ArrayList<>();
    String directoryName = "ncaa_" + year + "_D" + division;
    String readFileName =
        directoryName + "/ncaa_teams_by_conference_" + year + "_D" + division + ".csv";
    String scheduleFileName = directoryName + "/ncaa_schedule_" + year + "_D" + division + ".csv";

    CSVReader reader = NCAAUtils.CSVReader(readFileName);
    CSVWriter writer = NCAAUtils.CSVWriter(scheduleFileName, HEADER);
    HashMap<Integer, String[]> allTeams = NCAAUtils.getAllTeams(year, division);
    Iterator<String[]> it = reader.iterator();
    it.next();
    int numTeams = 0;
    while (it.hasNext()) {
      long startTime = System.currentTimeMillis();
      numTeams++;
      String[] teamInfo = it.next();
      String teamName = teamInfo[NCAAUtils.TEAM_NAME];
      System.out.print(numTeams + ": " + teamName + "... ");
      List<String[]> teamSchedule = scrapeSchedule(year, division, teamInfo, allTeams);
      if (teamSchedule == null) {
        missedTeams.add(teamInfo);
      } else {
        writer.writeAll(teamSchedule);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write.");
          System.exit(0);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to scrape: " + ((endTime - startTime) / 1000.0));
      }
    }
    try {
      reader.close();
      writer.close();
    } catch (IOException e) {
      System.out.println("Unable to close CSVReader/Writer.");
    }
    System.out.println("Teams Missed:");
    for (String[] team : missedTeams) {
      System.out.println(team[NCAAUtils.TEAM_NAME]);
    }
    System.out.println("Done!");
    long totalEndTime = System.currentTimeMillis();
    System.out.println("Time to scrape: " + ((totalEndTime - totalStartTime) / 1000.0));
  }

  public static void scrapeTeamSchedule(int year, int division, String team) {
    String directoryName = "ncaa_" + year + "_D" + division + "/"
        + team.toLowerCase().replaceAll(" ", "_") + "_" + year + "_D" + division;
    new File(directoryName).mkdirs();
    String scheduleFileName = directoryName + "/" + team.toLowerCase().replaceAll(" ", "_")
        + "_schedule_" + year + "_D" + division + ".csv";
    HashMap<Integer, String[]> allTeams = NCAAUtils.getAllTeams(year, division);
    CSVWriter writer = NCAAUtils.CSVWriter(scheduleFileName, HEADER);
    String[] teamInfo = NCAAUtils.teamFinder(year, division, team);
    List<String[]> teamSchedule = scrapeSchedule(year, division, teamInfo, allTeams);
    writer.writeAll(teamSchedule);
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
    System.out.println("Done! " + team + " schedule completed.");
  }

  private static List<String[]> scrapeSchedule(int year, int division, String[] teamInfo,
      HashMap<Integer, String[]> allTeams) {
    List<String[]> scheduleList = new ArrayList<>();
    String url = teamInfo[NCAAUtils.TEAM_URL];

    Document doc = NCAAUtils.getWebPage(url);
    int foundGames = 0;
    int missingURL = 0;
    int unfinishedGames = 0;
    int wins = 0;
    int losses = 0;
    int ties = 0;

    if (doc == null) {
      url = "https://stats.ncaa.org/player/game_by_game?game_sport_year_ctl_id="
          + teamInfo[NCAAUtils.TEAM_YEAR_ID] + "&org_id=" + teamInfo[NCAAUtils.TEAM_ID]
          + "&stats_player_seq=-100";
      doc = NCAAUtils.getWebPage(url);
      doc.getAllElements();
      return null;
    } else {
      Element schedule = doc.selectFirst("tbody").select("tbody").get(1);
      Elements games = schedule.getElementsByTag("tr");
      games.remove(0);
      games.remove(0);
      for (Element game : games) {
        foundGames++;
        String[] scheduleInfo = new String[HEADER.length];
        scheduleInfo[YEAR] = teamInfo[NCAAUtils.TEAM_YEAR];
        scheduleInfo[YEAR_ID] = teamInfo[NCAAUtils.TEAM_YEAR_ID];
        scheduleInfo[DIVISION] = teamInfo[NCAAUtils.TEAM_DIVISION];
        scheduleInfo[TEAM_CONFERENCE] = teamInfo[NCAAUtils.TEAM_CONFERENCE];
        scheduleInfo[TEAM_NAME] = teamInfo[NCAAUtils.TEAM_NAME];
        scheduleInfo[TEAM_ID] = teamInfo[NCAAUtils.TEAM_ID];
        scheduleInfo[TEAM_URL] = teamInfo[NCAAUtils.TEAM_URL];
        Elements gameInfo = game.select("td");
        Element date = gameInfo.get(0);
        scheduleInfo[GAME_DATE] = date.text();
        Element opponent = gameInfo.get(1);
        String gameString = opponent.text();
        Elements linkElement = opponent.select("a[href]");
        String link = null;
        try {
          link = linkElement.toString().substring(linkElement.toString().indexOf("/"),
              linkElement.toString().lastIndexOf("\""));
          scheduleInfo[OPPONENT_URL] = NCAAUtils.BASE_URL + link;
          scheduleInfo[OPPONENT_ID] = link.substring(6, link.lastIndexOf("/"));
        } catch (StringIndexOutOfBoundsException s) {
          missingURL++;
          link = "";
          scheduleInfo[OPPONENT_URL] = "";
          scheduleInfo[OPPONENT_ID] = "";
          scheduleInfo[OPPONENT_CONFERENCE] = "Other";
        }
        if (gameString.contains("@ ")) {
          if (gameString.indexOf("@ ") == 0) {
            scheduleInfo[OPPONENT_NAME] = gameString.substring(2);
            scheduleInfo[HOME_TEAM] = scheduleInfo[OPPONENT_NAME];
            scheduleInfo[NEUTRAL_SITE] = String.valueOf(false);
          } else {
            scheduleInfo[OPPONENT_NAME] = gameString.substring(0, gameString.indexOf("@ ") - 1);
            scheduleInfo[HOME_TEAM] = "Neutral";
            scheduleInfo[NEUTRAL_SITE] = String.valueOf(true);
          }
        } else {
          scheduleInfo[OPPONENT_NAME] = gameString;
          scheduleInfo[HOME_TEAM] = scheduleInfo[TEAM_NAME];
          scheduleInfo[NEUTRAL_SITE] = String.valueOf(false);
        }

        if (!scheduleInfo[OPPONENT_ID].equals("")) {
          String[] opponentTeamInfo = allTeams.get(Integer.parseInt(scheduleInfo[OPPONENT_ID]));
          if (opponentTeamInfo != null) {
            scheduleInfo[OPPONENT_CONFERENCE] = opponentTeamInfo[NCAAUtils.TEAM_CONFERENCE];
          }
        }
        if (Boolean.valueOf(scheduleInfo[NEUTRAL_SITE])) {
          scheduleInfo[LOCATION] = gameString.substring(gameString.indexOf("@ ") + 2);
        } else if (scheduleInfo[HOME_TEAM].equals(scheduleInfo[TEAM_NAME])) {
          scheduleInfo[LOCATION] = scheduleInfo[TEAM_NAME];
        } else {
          scheduleInfo[LOCATION] = scheduleInfo[OPPONENT_NAME];
        }

        Element score = gameInfo.get(2);
        Elements gameLink = score.select("a[href]");
        String gameLinkString = null;
        try {
          gameLinkString = gameLink.toString().substring(9, gameLink.toString().indexOf("\"", 9));
        } catch (StringIndexOutOfBoundsException s) {
          break;
        }
        scheduleInfo[GAME_URL] = NCAAUtils.BASE_URL + gameLinkString;
        scheduleInfo[GAME_ID] = gameLinkString.substring(gameLinkString.indexOf("index/") + 6,
            gameLinkString.indexOf("?"));
        String scoreString = score.text().replaceAll(" ", "");
        if (scoreString.equals("-")) {
          unfinishedGames++;
        } else {
          if (scoreString.contains("(")) {
            scheduleInfo[EXTRA_INNINGS] = String.valueOf(true);
            scheduleInfo[INNINGS] =
                scoreString.substring(scoreString.indexOf("(") + 1, scoreString.indexOf(")"));
          } else {
            scheduleInfo[EXTRA_INNINGS] = String.valueOf(false);
            scheduleInfo[INNINGS] = "";
          }

          if (scoreString.startsWith("W")) {
            scoreString = scoreString.substring(scoreString.indexOf("W") + 1);
          }
          if (scoreString.startsWith("L")) {
            scoreString = scoreString.substring(scoreString.indexOf("L") + 1);
          }
          if (scoreString.startsWith("T")) {
            scoreString = scoreString.substring(scoreString.indexOf("T") + 1);
          }
          if (Boolean.valueOf(scheduleInfo[EXTRA_INNINGS])) {
            scheduleInfo[SCORE] = scoreString.substring(0, scoreString.indexOf("("));
          } else {
            scheduleInfo[SCORE] = scoreString;
          }
          scheduleInfo[TEAM_SCORE] =
              scheduleInfo[SCORE].substring(0, scheduleInfo[SCORE].indexOf("-"));
          scheduleInfo[OPPONENT_SCORE] =
              scheduleInfo[SCORE].substring(scheduleInfo[SCORE].indexOf("-") + 1);
          if (Integer.parseInt(scheduleInfo[TEAM_SCORE]) > Integer
              .parseInt(scheduleInfo[OPPONENT_SCORE])) {
            scheduleInfo[WINNING_TEAM] = scheduleInfo[TEAM_NAME];
            wins++;
          } else if (Integer.parseInt(scheduleInfo[TEAM_SCORE]) == Integer
              .parseInt(scheduleInfo[OPPONENT_SCORE])) {
            scheduleInfo[WINNING_TEAM] = "Tie";
            ties++;
          } else {
            scheduleInfo[WINNING_TEAM] = (scheduleInfo[OPPONENT_NAME]);
            losses++;
          }
        }
        scheduleList.add(scheduleInfo);
      }
    }
    System.out.println(missingURL + " missing URL.");
    System.out.println("Total Games: " + foundGames + ", " + unfinishedGames + " unfinished.");
    System.out.println(
        wins + "-" + losses + "-" + ties + " " + String.format("%.3f", wins / (double) foundGames));
    return scheduleList;
  }
}
