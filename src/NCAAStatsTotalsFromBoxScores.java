import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import box_score_objects.BoxScoreLine;
import box_score_objects.Team;
import box_score_objects.TeamBoxScore;
import utils.NCAAHeaders;
import utils.NCAAUtils;
import utils.Type;
import utils.Venue;

public class NCAAStatsTotalsFromBoxScores {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    boolean isTeam = true;
    String teamName = "james_madison";
    int year = 2018;
    for (Venue v : Venue.values()) {
      for (Type t : Type.values()) {
        String readFileName;
        String playersDirectoryName;
        String teamsDirectoryName;
        String playersFileName;
        String teamsFileName;
        HashMap<String, Team> teams;

        if (isTeam) {
          readFileName =
              "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName + "_box_scores/"
                  + teamName + "_" + t.toString().toLowerCase() + "_box_scores_" + year + "_D1.csv";
          teams = getTeamBoxScoresFromFile(year, readFileName, t);

          playersDirectoryName = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
              + "_" + "stats/players/" + t.toString().toLowerCase();
          teamsDirectoryName = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
              + "_" + "stats/teams/" + t.toString().toLowerCase();

          playersFileName = teamName + "_" + "player_stats_"
              + v.toString().toLowerCase() + "_" + t.toString().toLowerCase() + "_" + year + "_"
              + "D1";
          teamsFileName =
              teamName + "_" + "team_stats_" + v.toString().toLowerCase()
                  + "_" + t.toString().toLowerCase() + "_" + year + "_" + "D1";
        } else {
          readFileName = "ncaa_" + year + "_D1/ncaa_box_scores/ncaa_" + t.toString().toLowerCase()
              + "_box_scores_" + year + "_D1.csv";
          teams = getTeamBoxScoresFromFile(year, readFileName, t);

          playersDirectoryName =
              "ncaa_" + year + "_D1/ncaa_stats/players/" + t.toString().toLowerCase();
          teamsDirectoryName =
              "ncaa_" + year + "_D1/ncaa_stats/teams/" + t.toString().toLowerCase();

          playersFileName = "ncaa_player_stats_" + v.toString().toLowerCase() + "_"
              + t.toString().toLowerCase() + "_" + year + "_" + "D1";
          teamsFileName = "ncaa_team_stats_" + v.toString().toLowerCase() + "_"
              + t.toString().toLowerCase() + "_" + year + "_" + "D1";
        }


        String playersWriterName =
            NCAAUtils.createDirectoryAndCSVFile(playersDirectoryName, playersFileName);
        String teamsWriterName =
            NCAAUtils.createDirectoryAndCSVFile(teamsDirectoryName, teamsFileName);

        CSVWriter playersWriter =
            NCAAUtils.CSVWriter(playersWriterName, NCAAHeaders.STATS_HEADERS.get(t).get(year));
        CSVWriter teamsWriter =
            NCAAUtils.CSVWriter(teamsWriterName, NCAAHeaders.STATS_HEADERS.get(t).get(year));

        Set<String> teamSet = teams.keySet();
        for (String team : teamSet) {
          teams.get(team).writePlayers(playersWriter, v);
          teams.get(team).writeTeams(teamsWriter, v);
        }
      }
    }

    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }

  public static HashMap<String, Team> getTeamBoxScoresFromFile(int year, String fileName, Type t) {
    CSVReader reader = NCAAUtils.CSVReader(fileName);
    Iterator<String[]> it = reader.iterator();
    it.next();

    HashMap<String, Team> teams = new HashMap<>();
    while (it.hasNext()) {
      String[] line = it.next();
      TeamBoxScore awayBoxScore =
          new TeamBoxScore(year, Integer.parseInt(line[0]), line[1], false, t);
      for (int i = 1; !line[3].equals("Totals"); i++) {
        BoxScoreLine awayLine = new BoxScoreLine(year, Venue.Away, line, i);
        awayBoxScore.add(awayLine);
        line = it.next();
      }
      BoxScoreLine awayTotals = new BoxScoreLine(year, Venue.Away, line, 0);
      awayBoxScore.add(awayTotals);

      line = it.next();
      TeamBoxScore homeBoxScore =
          new TeamBoxScore(year, Integer.parseInt(line[0]), line[1], true, t);
      for (int i = 1; !line[3].equals("Totals"); i++) {
        BoxScoreLine homeLine = new BoxScoreLine(year, Venue.Home, line, i);
        homeBoxScore.add(homeLine);
        line = it.next();
      }

      BoxScoreLine homeTotals = new BoxScoreLine(year, Venue.Home, line, 0);
      homeBoxScore.add(homeTotals);

      awayBoxScore.setOpponentTeamName(homeBoxScore.getTeamName());
      homeBoxScore.setOpponentTeamName(awayBoxScore.getTeamName());

      awayBoxScore.setOpponentTotals(homeTotals);
      homeBoxScore.setOpponentTotals(awayTotals);

      Team awayTeam = teams.get(awayBoxScore.getTeamName());
      Team homeTeam = teams.get(homeBoxScore.getTeamName());
      if (awayTeam == null) {
        awayTeam = new Team(year, awayBoxScore.getTeamName(), t);
      }
      awayTeam.add(awayBoxScore);
      if (homeTeam == null) {
        homeTeam = new Team(year, homeBoxScore.getTeamName(), t);
      }
      homeTeam.add(homeBoxScore);
      teams.put(awayTeam.getTeamName(), awayTeam);
      teams.put(homeTeam.getTeamName(), homeTeam);
    }
    return teams;
  }
}
