package utils;

import java.util.HashMap;

public class NCAAHeaders {

  private static final String[] HITTING_BOX_HEADER_2012 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "AB", "R",
          "H", "2B", "3B", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "AB", "H", "TB", "R", "2B", "3B", "HR",
      "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2014 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "AB", "R", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2015 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked"};
  private static final String[] HITTING_BOX_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] HITTING_BOX_HEADER_2017 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] HITTING_BOX_HEADER_2018 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out"};
  private static final String[] PITCHING_BOX_HEADER_2012 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2014 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2015 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "App", "GS", "IP", "H", "R", "ER",
      "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2016 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G",
          "App", "GS", "IP", "CG", "H", "R", "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A",
          "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches",
          "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2017 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G",
          "App", "GS", "IP", "CG", "H", "R", "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A",
          "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches",
          "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] PITCHING_BOX_HEADER_2018 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G",
          "App", "GS", "IP", "CG", "H", "R", "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A",
          "Bk", "HR-A", "WP", "HB", "IBB", "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches",
          "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL"};
  private static final String[] FIELDING_BOX_HEADER_2012 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "PO", "A",
          "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2013 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "PO", "A",
          "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2014 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "PO",
          "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2015 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "PO",
          "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2016 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "PO",
          "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2017 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "G",
          "PO", "TC", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};
  private static final String[] FIELDING_BOX_HEADER_2018 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "PO",
          "TC", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP"};

  public static final HashMap<Type, HashMap<Integer, String[]>> BOX_HEADERS = new HashMap<>();
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
    BOX_HEADERS.put(Type.Hitting, HITTING_BOX_HEADERS);
    BOX_HEADERS.put(Type.Pitching, PITCHING_BOX_HEADERS);
    BOX_HEADERS.put(Type.Fielding, FIELDING_BOX_HEADERS);
  }
  
  private static final String[] HITTING_STATS_HEADER_2012 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "AB", "R",
          "H", "2B", "3B", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS", "Picked",
          "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2013 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "AB", "H",
          "TB", "R", "2B", "3B", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS",
          "Picked", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2014 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "AB",
          "R", "H", "2B", "3B", "TB", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS",
          "Picked", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2015 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "R",
          "AB", "H", "2B", "3B", "TB", "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "SB", "CS",
          "Picked", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out", "P", "C",
      "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2017 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out", "P", "C",
      "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] HITTING_STATS_HEADER_2018 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "R", "AB", "H", "2B", "3B", "TB",
      "HR", "RBI", "BB", "HBP", "SF", "SH", "K", "DP", "CS", "Picked", "SB", "RBI2out", "P", "C",
      "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2012 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL",
      "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL",
      "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2014 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "App", "GS", "IP", "H", "R", "ER", "BB",
      "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL",
      "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2015 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "App", "GS", "IP", "H", "R", "ER",
      "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "HR-A", "WP", "Bk", "HB", "IBB", "Inh Run",
      "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV", "OrdAppeared", "KL",
      "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "App", "GS", "IP", "CG", "H", "R",
      "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB",
      "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV",
      "OrdAppeared", "KL", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2017 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "App", "GS", "IP", "CG", "H", "R",
      "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB",
      "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV",
      "OrdAppeared", "KL", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] PITCHING_STATS_HEADER_2018 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "App", "GS", "IP", "CG", "H", "R",
      "ER", "BB", "SO", "SHO", "BF", "P-OAB", "2B-A", "3B-A", "Bk", "HR-A", "WP", "HB", "IBB",
      "Inh Run", "Inh Run Score", "SHA", "SFA", "Pitches", "GO", "FO", "W", "L", "SV",
      "OrdAppeared", "KL", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2012 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "PO", "A", "E", "CI", "PB", "SBA", "CSB",
      "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2013 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "PO", "A", "E", "CI", "PB", "SBA", "CSB",
      "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2014 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "PO", "A", "E", "CI", "PB", "SBA",
      "CSB", "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2015 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "PO", "A", "E", "CI", "PB", "SBA",
      "CSB", "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2016 = new String[] {"Game ID", "Team",
      "Player ID", "Player", "Lineup Position", "Games", "G", "PO", "A", "E", "CI", "PB", "SBA",
      "CSB", "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2017 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "G",
          "PO", "TC", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP", "P", "C", "1B", "2B", "3B",
          "SS", "LF", "CF", "RF", "DH", "PH", "PR"};
  private static final String[] FIELDING_STATS_HEADER_2018 =
      new String[] {"Game ID", "Team", "Player ID", "Player", "Lineup Position", "Games", "G", "PO",
          "TC", "A", "E", "CI", "PB", "SBA", "CSB", "IDP", "TP", "P", "C", "1B", "2B", "3B", "SS",
          "LF", "CF", "RF", "DH", "PH", "PR"};

  public static final HashMap<Type, HashMap<Integer, String[]>> STATS_HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> HITTING_STATS_HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> PITCHING_STATS_HEADERS = new HashMap<>();
  private static final HashMap<Integer, String[]> FIELDING_STATS_HEADERS = new HashMap<>();
  static {
    HITTING_STATS_HEADERS.put(2012, HITTING_STATS_HEADER_2012);
    HITTING_STATS_HEADERS.put(2013, HITTING_STATS_HEADER_2013);
    HITTING_STATS_HEADERS.put(2014, HITTING_STATS_HEADER_2014);
    HITTING_STATS_HEADERS.put(2015, HITTING_STATS_HEADER_2015);
    HITTING_STATS_HEADERS.put(2016, HITTING_STATS_HEADER_2016);
    HITTING_STATS_HEADERS.put(2017, HITTING_STATS_HEADER_2017);
    HITTING_STATS_HEADERS.put(2018, HITTING_STATS_HEADER_2018);
    PITCHING_STATS_HEADERS.put(2012, PITCHING_STATS_HEADER_2012);
    PITCHING_STATS_HEADERS.put(2013, PITCHING_STATS_HEADER_2013);
    PITCHING_STATS_HEADERS.put(2014, PITCHING_STATS_HEADER_2014);
    PITCHING_STATS_HEADERS.put(2015, PITCHING_STATS_HEADER_2015);
    PITCHING_STATS_HEADERS.put(2016, PITCHING_STATS_HEADER_2016);
    PITCHING_STATS_HEADERS.put(2017, PITCHING_STATS_HEADER_2017);
    PITCHING_STATS_HEADERS.put(2018, PITCHING_STATS_HEADER_2018);
    FIELDING_STATS_HEADERS.put(2012, FIELDING_STATS_HEADER_2012);
    FIELDING_STATS_HEADERS.put(2013, FIELDING_STATS_HEADER_2013);
    FIELDING_STATS_HEADERS.put(2014, FIELDING_STATS_HEADER_2014);
    FIELDING_STATS_HEADERS.put(2015, FIELDING_STATS_HEADER_2015);
    FIELDING_STATS_HEADERS.put(2016, FIELDING_STATS_HEADER_2016);
    FIELDING_STATS_HEADERS.put(2017, FIELDING_STATS_HEADER_2017);
    FIELDING_STATS_HEADERS.put(2018, FIELDING_STATS_HEADER_2018);
    STATS_HEADERS.put(Type.Hitting, HITTING_STATS_HEADERS);
    STATS_HEADERS.put(Type.Pitching, PITCHING_STATS_HEADERS);
    STATS_HEADERS.put(Type.Fielding, FIELDING_STATS_HEADERS);
  }
}
