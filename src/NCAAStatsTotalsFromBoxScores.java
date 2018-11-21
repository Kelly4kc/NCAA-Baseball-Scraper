import java.util.HashMap;
import java.util.Set;

import com.opencsv.CSVWriter;

import objects.Team;
import utils.NCAAUtils;
import utils.Type;
import utils.Venue;

public class NCAAStatsTotalsFromBoxScores {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    int year = 2018;
    for (Venue v : Venue.values()) {
      for (Type t : Type.values()) {
        String readFileName = "ncaa_" + year + "_D1/ncaa_box_scores/ncaa_"
            + t.toString().toLowerCase() + "_box_scores_" + year + "_D1.csv";
        HashMap<String, Team> teams = Team.getTeamBoxScoresFromFile(year, readFileName, t);

        String playersDirectoryName =
            "ncaa_" + year + "_D1/ncaa_stats/players/" + t.toString().toLowerCase();
        String teamsDirectoryName =
            "ncaa_" + year + "_D1/ncaa_stats/teams/" + t.toString().toLowerCase();

        String playersFileName = "ncaa_player_stats_" + v.toString().toLowerCase() + "_"
            + t.toString().toLowerCase() + "_" + year + "_" + "D1";
        String teamsFileName = "ncaa_team_stats_" + v.toString().toLowerCase() + "_"
            + t.toString().toLowerCase() + "_" + year + "_" + "D1";

        String playersWriterName =
            NCAAUtils.createDirectoryAndCSVFile(playersDirectoryName, playersFileName);
        String teamsWriterName =
            NCAAUtils.createDirectoryAndCSVFile(teamsDirectoryName, teamsFileName);

        CSVWriter playersWriter = NCAAUtils.CSVWriter(playersWriterName, NCAAUtils.HEADERS.get(t).get(year));
        CSVWriter teamsWriter = NCAAUtils.CSVWriter(teamsWriterName, NCAAUtils.HEADERS.get(t).get(year));

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
}
