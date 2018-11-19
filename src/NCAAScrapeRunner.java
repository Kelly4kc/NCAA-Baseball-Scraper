import scrapers.NCAABoxScoreScraper;
import scrapers.NCAAConferenceScraper;
import scrapers.NCAAPlayByPlayScraper;
import scrapers.NCAAScheduleScraper;
import scrapers.NCAATeamScraper;

import utils.Type;

public class NCAAScrapeRunner {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    for (int year = 2012; year < 2018; year++) {
      // NCAATeamScraper.scrapeTeams(year, 1);
      // NCAAConferenceScraper.scrapeConferences(year, 1);
      // NCAATeamScraper.scrapeTeamsByConference(year, 1);
      // NCAAScheduleScraper.scrapeSchedule(year, 1);
      // NCAABoxScoreScraper.scrapeGameInfo(year, 1);
      for (Type t : Type.values()) {
        // NCAABoxScoreScraper.scrapeBoxScores(year, 1, t);
      }
      // NCAAPlayByPlayScraper.scrapePlayByPlay(year, 1);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }
}
