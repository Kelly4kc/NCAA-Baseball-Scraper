package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class NCAAUtils {

  private static final String[] HITTING_BOX_HEADER_2012 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "AB", "R", "H", "2B",
          "3B", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2013 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "AB", "H", "TB", "R",
          "2B", "3B", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2014 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "AB", "R", "H",
          "2B", "3B", "TB", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2015 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "R", "AB", "H",
          "2B", "3B", "TB", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "R", "AB", "H", "2B", "3B", "TB", "HR", "RBI",
      "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] HITTING_BOX_HEADER_2017 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "R", "AB", "H", "2B", "3B", "TB", "HR", "RBI",
      "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] HITTING_BOX_HEADER_2018 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "R", "AB", "H", "2B", "3B", "TB", "HR", "RBI",
      "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] PITCHING_BOX_HEADER_2012 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "App", "GS", "IP", "H", "R", "ER", "BB", "SO", "SHO",
      "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run", "Inh Run Score",
      "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "App", "GS", "IP", "H", "R", "ER", "BB", "SO", "SHO",
      "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run", "Inh Run Score",
      "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2014 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "App", "GS", "IP", "H", "R", "ER", "BB", "SO", "SHO",
      "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run", "Inh Run Score",
      "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2015 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "App", "GS", "IP", "H", "R", "ER", "BB", "SO",
      "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "App", "GS", "IP", "CG", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2017 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "App", "GS", "IP", "CG", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2018 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "G", "App", "GS", "IP", "CG", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] FIELDING_BOX_HEADER_2012 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "PO", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Pos", "Games", "PO", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2014 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "PO", "A", "E",
          "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2015 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "PO", "A", "E",
          "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2016 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "PO", "A", "E",
          "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2017 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "G", "PO", "TC",
          "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2018 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Pos", "Games", "G", "PO", "TC", "A",
          "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};

  public static final HashMap<Type, HashMap<Integer, String[]>> HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> HITTING_BOX_HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> PITCHING_BOX_HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> FIELDING_BOX_HEADERS = new HashMap<>();
  static {
    HITTING_BOX_HEADERS.put(2012, HITTING_BOX_HEADER_2012);
    HITTING_BOX_HEADERS.put(2013, HITTING_BOX_HEADER_2013);
    HITTING_BOX_HEADERS.put(2014, HITTING_BOX_HEADER_2014);
    HITTING_BOX_HEADERS.put(2015, HITTING_BOX_HEADER_2015);
    HITTING_BOX_HEADERS.put(2016, HITTING_BOX_HEADER_2016);
    HITTING_BOX_HEADERS.put(2017, HITTING_BOX_HEADER_2017);
    HITTING_BOX_HEADERS.put(2018, HITTING_BOX_HEADER_2018);
    PITCHING_BOX_HEADERS.put(2012, PITCHING_BOX_HEADER_2012);
    PITCHING_BOX_HEADERS.put(2013, PITCHING_BOX_HEADER_2013);
    PITCHING_BOX_HEADERS.put(2014, PITCHING_BOX_HEADER_2014);
    PITCHING_BOX_HEADERS.put(2015, PITCHING_BOX_HEADER_2015);
    PITCHING_BOX_HEADERS.put(2016, PITCHING_BOX_HEADER_2016);
    PITCHING_BOX_HEADERS.put(2017, PITCHING_BOX_HEADER_2017);
    PITCHING_BOX_HEADERS.put(2018, PITCHING_BOX_HEADER_2018);
    FIELDING_BOX_HEADERS.put(2012, FIELDING_BOX_HEADER_2012);
    FIELDING_BOX_HEADERS.put(2013, FIELDING_BOX_HEADER_2013);
    FIELDING_BOX_HEADERS.put(2014, FIELDING_BOX_HEADER_2014);
    FIELDING_BOX_HEADERS.put(2015, FIELDING_BOX_HEADER_2015);
    FIELDING_BOX_HEADERS.put(2016, FIELDING_BOX_HEADER_2016);
    FIELDING_BOX_HEADERS.put(2017, FIELDING_BOX_HEADER_2017);
    FIELDING_BOX_HEADERS.put(2018, FIELDING_BOX_HEADER_2018);
    HEADERS.put(Type.Hitting, HITTING_BOX_HEADERS);
    HEADERS.put(Type.Pitching, PITCHING_BOX_HEADERS);
    HEADERS.put(Type.Fielding, FIELDING_BOX_HEADERS);
  }
  public static final String BASE_URL = "http://stats.ncaa.org";

  public static final int HITTING = 0;
  public static final int PITCHING = 1;
  public static final int FIELDING = 2;
  // 2018, 2017, 2016, 2015, 2014, 2013, 2012
  public static final int[] CATEGORY_IDS = {11953, 11000, 10946, 10780, 10460, 10120, 10082};

  public static final String[] YEAR_IDS =
      // 2018, 2017, 2016, 2015, 2014, 2013, 2012
      {"12973", "12560", "12360", "12080", "11620", "11320", "10942"};

  public static final int TEAM_YEAR = 0;
  public static final int TEAM_YEAR_ID = 1;
  public static final int TEAM_DIVISION = 2;
  public static final int TEAM_CONFERENCE = 3;
  public static final int TEAM_NAME = 4;
  public static final int TEAM_ID = 5;
  public static final int TEAM_URL = 6;

  public static CSVWriter CSVWriter(String fileName, String[] header) {
    CSVWriter writer = null;
    try {
      writer = new CSVWriter(new FileWriter(fileName));
    } catch (IOException e) {
      System.out.println("Failed to create CSVWriter, check file name/existence.");
      System.out.println(fileName);
      System.exit(0);
    }
    System.out.println("Exporting data into: " + fileName);
    writer.writeNext(header);
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
    return writer;
  }

  public static Document getWebPage(String url) {
    int attemptLimit = 10;
    int attempts = 0;
    Document doc = null;
    while (doc == null && attempts < attemptLimit) {
      try {
        doc = Jsoup.connect(url).get();
      } catch (IOException e1) {
        System.out.println("Unable to connect to website, check url.");
        System.out.println(url);
      }
      attempts++;
      if (doc != null) {
        return doc;
      }
      try {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Waiting, attempt: " + (attempts));
      } catch (InterruptedException e) {
        System.out.println("Error occurred while waiting to reconnect.");
        System.exit(0);
      }
    }
    return null;
  }

  public static CSVReader CSVReader(String fileName) {
    CSVReader reader = null;
    try {
      reader = new CSVReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      System.out.println("Failed to create CSVReader, check file name/existence.");
      System.out.println(fileName);
      System.exit(0);
    }
    System.out.println("Importing data from: " + fileName);
    return reader;
  }

  public static String getURL(Element elementContainingURL) {
    try {
      String text = elementContainingURL.toString();
      return BASE_URL + text.substring(text.indexOf("/player/"), text.lastIndexOf('\"'));
    } catch (StringIndexOutOfBoundsException s) {
      return null;
    }
  }

  public static String getIDFromURL(String url) {
    return url.substring(url.indexOf("seq=") + 4);
  }


  public static ArrayList<String[]> getAllLines(String fileName) {
    CSVReader reader = CSVReader(fileName);

    ArrayList<String[]> lines = new ArrayList<>();

    Iterator<String[]> it = reader.iterator();
    it.next();

    while (it.hasNext()) {
      lines.add(it.next());
    }
    return lines;
  }

  public static String[] teamFinder(int year, int division, String team) {
    String readFileName = "ncaa_" + year + "_D" + division + "/ncaa_teams_by_conference_" + year
        + "_D" + division + ".csv";
    CSVReader reader = CSVReader(readFileName);
    Iterator<String[]> it = reader.iterator();
    it.next();
    String[] teamInfo = null;
    while (it.hasNext()) {
      teamInfo = it.next();
      if (team.equals(teamInfo[TEAM_NAME])) {
        System.out.println(team + " found!");
        break;
      }
    }
    if (!team.equals(teamInfo[TEAM_NAME])) {
      System.out.println("Team not found, check spelling: " + team);
      teamInfo = null;
    }
    return teamInfo;
  }

  public static HashMap<Integer, String[]> getAllTeams(int year, int division) {
    String readFileName = "ncaa_" + year + "_D" + division + "/ncaa_teams_by_conference_" + year
        + "_D" + division + ".csv";
    CSVReader reader = CSVReader(readFileName);
    Iterator<String[]> it = reader.iterator();
    it.next();
    HashMap<Integer, String[]> allTeams = new HashMap<>();
    while (it.hasNext()) {
      String[] teamInfo = it.next();
      allTeams.put(Integer.parseInt(teamInfo[TEAM_ID]), teamInfo);
    }
    return allTeams;
  }

  public static String[] conferenceFinder(int year, int division, String conference) {
    String conferenceFileName =
        "ncaa_" + year + "_D" + division + "/ncaa_conferences_" + year + "_D" + division + ".csv";
    CSVReader conferenceReader = CSVReader(conferenceFileName);

    Iterator<String[]> it = conferenceReader.iterator();
    it.next();
    String[] conferenceInfo = null;
    while (it.hasNext()) {
      conferenceInfo = it.next();
      if (conferenceInfo[2].equals(conference)) {
        System.out.println(conference + " found!");
        break;
      }
    }
    if (!conference.equals(conferenceInfo[2])) {
      System.out.println("Conference not found, check spelling: " + conference);
      System.exit(0);
    }
    return conferenceInfo;
  }

  public static String createDirectoryAndCSVFile(String directoryName, String fileName) {
    new File(directoryName).mkdirs();
    return directoryName + "/" + fileName + ".csv";
  }

  public static double inningsAdder(double currentInnings, double inningsToAdd) {
    int integerSum = ((int) currentInnings) + ((int) inningsToAdd);
    int decimalSum = (int) (((currentInnings * 10) % 10) + ((inningsToAdd * 10) % 10));
    return integerSum + (decimalSum / 3) + ((decimalSum % 3) / 10.0);
  }
}
