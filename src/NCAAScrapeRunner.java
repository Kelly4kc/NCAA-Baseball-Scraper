import scrapers.NCAABoxScoreScraper;
import scrapers.NCAAConferenceScraper;
import scrapers.NCAAScheduleScraper;
import scrapers.NCAATeamScraper;

import utils.Type;

public class NCAAScrapeRunner {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    /*
     * for (int year = 2018; year < 2019; year++) { NCAATeamScraper.scrapeTeams(year, 1);
     * NCAAConferenceScraper.scrapeConferences(year, 1);
     * NCAATeamScraper.scrapeTeamsByConference(year, 1); }
     */
    for (int year = 2012; year < 2018; year++) {
     // NCAAScheduleScraper.scrapeSchedule(year, 1);
    }
    NCAABoxScoreScraper.scrapeGameInfo(2018, 1);
    for (Type t : Type.values()) {
      NCAABoxScoreScraper.scrapeBoxScores(2018, 1, t);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }
}
