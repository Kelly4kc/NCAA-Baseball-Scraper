import scrapers.NCAABoxScoreScraper;
import scrapers.NCAAConferenceScraper;
import scrapers.NCAAPlayByPlayScraper;
import scrapers.NCAAScheduleScraper;
import scrapers.NCAATeamScraper;

import utils.Type;

public class NCAAScrapeRunner {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    String teamName = "James Madison";
    for (int year = 2018; year < 2019; year++) {
      // NCAATeamScraper.scrapeTeams(year, 1);
      // NCAAConferenceScraper.scrapeConferences(year, 1);
      // NCAATeamScraper.scrapeTeamsByConference(year, 1);
      // NCAAScheduleScraper.scrapeSchedule(year, 1);
      // NCAAScheduleScraper.scrapeTeamSchedule(2018, 1, teamName);
      // NCAABoxScoreScraper.scrapeGameInfo(year, 1);
      //NCAABoxScoreScraper.scrapeTeamGameInfo(year, 1, teamName);
      for (Type t : Type.values()) {
        // NCAABoxScoreScraper.scrapeBoxScores(year, 1, t);
        //NCAABoxScoreScraper.scrapeTeamBoxScores(year, 1, teamName, t);
      }
      // NCAAPlayByPlayScraper.scrapePlayByPlay(year, 1);
      // NCAAPlayByPlayScraper.scrapeTeamPlayByPlay(2018, 1, teamName);
    }

    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }
}
