package scrapers;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import utils.NCAAUtils;

public class NCAAConferenceScraper {
  private static final int YEAR = 0;
  private static final int CONF_ID = 1;
  private static final int CONF_NAME = 2;
  private static final int CONF_URL = 3;

  public static void scrapeConferences(int year, int division) {
    long startTime = System.currentTimeMillis();
    String directoryName = "ncaa_" + year + "_D" + division;
    String fileName = directoryName + "/ncaa_conferences_" + year + "_D" + division + ".csv";
    String url = NCAAUtils.BASE_URL + "/team/inst_team_list?sport_code=MBA&academic_year=" + year
        + "&division=" + division + "&conf_id=-1&schedule_date=";
    String[] header = {"Year", "Conference ID", "Conference Name", "Conference URL"};
    CSVWriter writer = NCAAUtils.CSVWriter(fileName, header);
    Document doc = NCAAUtils.getWebPage(url);

    int numConferences = 0;
    Elements conferences =
        doc.getElementsByAttributeValueContaining("href", "javascript:changeConference");
    for (Element conference : conferences) {
      numConferences++;
      String[] row = new String[4];
      String confString = conference.toString();
      row[YEAR] = String.valueOf(year);
      row[CONF_ID] = confString.substring(confString.indexOf("(") + 1, confString.indexOf(")"));
      row[CONF_NAME] = conference.text();
      row[CONF_URL] = NCAAUtils.BASE_URL + "/team/inst_team_list?sport_code=MBA&academic_year="
          + year + "&division=" + division + "&conf_id=" + row[CONF_ID] + "&schedule_date=";
      writer.writeNext(row);
      try {
        writer.flush();
      } catch (IOException e) {
        System.out.println("Unable to write to file.");
      }
    }
    try {
      writer.close();
    } catch (IOException e) {
      System.out.println("Unable to close CSVReader/Writer.");
    }
    System.out.println(year + ": Found " + numConferences + " conferences");
    long endTime = System.currentTimeMillis();
    System.out.println("Time to scrape: " + ((endTime - startTime) / 1000.0));
  }
}
