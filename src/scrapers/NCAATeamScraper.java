package scrapers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.NCAAUtils;

public class NCAATeamScraper {
  private static final String[] HEADER =
      {"Year", "Year ID", "Division", "Conference", "Team Name", "Team ID", "Team URL"};

  public static void scrapeTeams(int year, int division) {
    long startTime = System.currentTimeMillis();
    String directoryName = "ncaa_" + year + "_D" + division;
    String fileName = "ncaa_teams_" + year + "_D" + division;

    String url = NCAAUtils.BASE_URL + "/team/inst_team_list?sport_code=MBA&academic_year=" + year
        + "&division=" + division + "conf_id=-1&schedule_date=";

    String directoryAndFileName = NCAAUtils.createDirectoryAndCSVFile(directoryName, fileName);

    CSVWriter writer = NCAAUtils.CSVWriter(directoryAndFileName, HEADER);
    Document doc = NCAAUtils.getWebPage(url);

    int foundTeams = 0;

    Elements elements = doc.select("a[href]");
    for (Element element : elements) {
      String link = element.attr("href");
      if (link.contains("/team") && !link.contains("?")) {
        foundTeams++;
        String[] teamInfo = new String[HEADER.length];
        String[] urlAttrs = link.split("/");
        teamInfo[NCAAUtils.TEAM_YEAR] = String.valueOf(year);
        teamInfo[NCAAUtils.TEAM_YEAR_ID] = urlAttrs[3];
        teamInfo[NCAAUtils.TEAM_DIVISION] = String.valueOf(division);
        teamInfo[NCAAUtils.TEAM_CONFERENCE] = "";
        teamInfo[NCAAUtils.TEAM_NAME] = element.text();
        teamInfo[NCAAUtils.TEAM_ID] = urlAttrs[2];
        teamInfo[NCAAUtils.TEAM_URL] = NCAAUtils.BASE_URL + link;
        System.out.println(teamInfo[NCAAUtils.TEAM_NAME]);
        writer.writeNext(teamInfo);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write.");
        }
      }
    }
    System.out.println(year + ": Found " + foundTeams + " teams in Division " + division);
    try {
      writer.close();
    } catch (IOException e) {
      System.out.println("Unable to close CSVWriter.");
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Time to scrape: " + ((endTime - startTime) / 1000.0));
  }

  public static void scrapeTeamsByConference(int year, int division) {
    long totalStartTime = System.currentTimeMillis();
    String directoryName = "ncaa_" + year + "_D" + division;

    String readFileName = directoryName + "/ncaa_conferences_" + year + "_D" + division + ".csv";
    String writeFileName =
        directoryName + "/ncaa_teams_by_conference_" + year + "_D" + division + ".csv";

    CSVReader reader = NCAAUtils.CSVReader(readFileName);
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, HEADER);

    ArrayList<String[]> allTeamsInfo = new ArrayList<>();
    HashSet<String> allTeamsSet = new HashSet<>();
    HashMap<String, String[]> allTeamsMap = new HashMap<>();
    HashSet<String> inConference = new HashSet<>();

    allTeamsInfo =
        NCAAUtils.getAllLines(directoryName + "/ncaa_teams_" + year + "_D" + division + ".csv");

    for (String[] team : allTeamsInfo) {
      allTeamsSet.add(team[NCAAUtils.TEAM_NAME]);
      allTeamsMap.put(team[NCAAUtils.TEAM_NAME], team);
    }

    int numConferences = 0;
    int totalTeams = 0;

    Iterator<String[]> it = reader.iterator();
    it.next();
    it.next();

    while (it.hasNext()) {
      long startTime = System.currentTimeMillis();
      String[] conferenceInfo = it.next();
      String conference = conferenceInfo[2];
      String url = conferenceInfo[3];
      numConferences++;

      Document doc = NCAAUtils.getWebPage(url);

      int confTeams = 0;
      Element teamElements = doc.select("tbody").get(1);
      Elements teams = teamElements.select("a[href]");
      System.out.print(conference + "...");
      for (Element team : teams) {
        confTeams++;
        totalTeams++;
        String[] confTeamInfo = new String[HEADER.length];
        String teamURL = team.toString().substring(team.toString().indexOf("/"),
            team.toString().lastIndexOf("\""));
        confTeamInfo[NCAAUtils.TEAM_YEAR] = String.valueOf(year);
        confTeamInfo[NCAAUtils.TEAM_YEAR_ID] = teamURL.substring(teamURL.lastIndexOf("/") + 1);
        confTeamInfo[NCAAUtils.TEAM_DIVISION] = String.valueOf(division);
        confTeamInfo[NCAAUtils.TEAM_CONFERENCE] = conference;
        confTeamInfo[NCAAUtils.TEAM_NAME] = team.text();
        confTeamInfo[NCAAUtils.TEAM_ID] = teamURL.substring(teamURL.indexOf("/team/") + 6,
            teamURL.indexOf("/", teamURL.indexOf("/team/") + 6));
        confTeamInfo[NCAAUtils.TEAM_URL] = NCAAUtils.BASE_URL + teamURL;
        inConference.add(confTeamInfo[NCAAUtils.TEAM_NAME]);
        writer.writeNext(confTeamInfo);
        try {
          writer.flush();
        } catch (IOException e) {
          System.out.println("Unable to write to file.");
          System.exit(0);
        }

      }
      System.out.println("found " + confTeams + " teams");
      long endTime = System.currentTimeMillis();
      System.out.println("Time to scrape: " + ((endTime - startTime) / 1000.0));
    }
    allTeamsSet.removeAll(inConference);
    for (String team : allTeamsSet) {
      totalTeams++;
      writer.writeNext(allTeamsMap.get(team));
      try {
        writer.flush();
      } catch (IOException e) {
        System.out.println("Unable to write to file.");
        System.exit(0);
      }
    }
    System.out
        .println("Found " + numConferences + " conferences and " + totalTeams + " total teams");
    try {
      reader.close();
      writer.close();
    } catch (IOException e) {
      System.out.println("Unable to close CSVReader/Writer.");
    }
    long totalEndTime = System.currentTimeMillis();
    System.out.println("Time to scrape: " + ((totalEndTime - totalStartTime) / 1000.0));
  }
}
