package box_score_objects;

import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import utils.NCAAHeaders;
import utils.Type;

/**
 * Box score made up of individual lines. Only includes this teams's stats, not the opponents. Can
 * set the opponent's totals.
 */
public class TeamBoxScore implements Comparable<Integer> {

  private Type t; // the type of the box score (hitting, pitching, or fielding)
  private String[] header;
  private ArrayList<BoxScoreLine> lines;
  private int year;
  private Integer gameId;
  private String teamName;
  private String opponentTeamName;
  private Integer playerId;
  private boolean home;
  private BoxScoreLine totals;
  private BoxScoreLine opponentTotals;

  /**
   * Inititalizes a new box score using lines. Should be used when scraping from stats.ncaa.org.
   *
   * @param year     the year
   * @param gameId   the game ID
   * @param teamName the team's name
   * @param home     true if it is a home game
   * @param t        the type of the box score (home, away, neutral)
   */
  public TeamBoxScore(int year, Integer gameId, String teamName, boolean home, Type t) {
    this.t = t;
    this.year = year;
    this.gameId = gameId;
    this.teamName = teamName;
    this.home = home;
    header = NCAAHeaders.BOX_HEADERS.get(t).get(year);
    if (header == null) {
      throw new IllegalArgumentException("Year must be between 2012 and 2018");
    }
    lines = new ArrayList<>();
  }

  /**
   * Inititalizes a new box score using lines. Should be used when reading from CSV.
   *
   * @param t     the type
   * @param lines the lines of the box score
   */
  public TeamBoxScore(Type t, ArrayList<BoxScoreLine> lines) {
    this.t = t;
    this.lines = lines;
    this.year = lines.get(0).getYear();
    this.gameId = lines.get(0).getGameId();
    this.teamName = lines.get(0).getTeamName();
    this.playerId = lines.get(0).getPlayerId();
  }

  /**
   * Returns the team name of this box score.
   *
   * @return the team name
   */
  public String getTeamName() {
    return teamName;
  }

  /**
   * Returns the game ID of this box score.
   *
   * @return the game ID
   */
  public Integer getGameId() {
    return gameId;
  }

  /**
   * Sets the team name of this box score.
   *
   * @param teamName the new team name
   */
  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  /**
   * Adds a line to this box score.
   *
   * @param line the line to add
   */
  public void add(BoxScoreLine line) {
    if (line.getPlayerName().equals("Totals")) {
      totals = line;
    } else {
      lines.add(line);
    }
  }

  /**
   * Returns the lines of the box score.
   *
   * @return the lines
   */
  public ArrayList<BoxScoreLine> getLines() {
    return lines;
  }

  @Override
  public int compareTo(Integer other) {
    return gameId.compareTo(other);
  }

  /**
   * Writes the box score using the specified writer.
   * Writes in this format : Header
   * Line1
   * Line2
   * ...
   * lineN
   * Team Totals
   * Opponent Totals
   *
   * @param writer the writer to use
   */
  public void write(CSVWriter writer) {
    writer.writeNext(header);
    for (BoxScoreLine line : lines) {
      writer.writeNext(line.getCompleteLine());
    }
    writer.writeNext(totals.getCompleteLine());
    writer.writeNext(opponentTotals.getCompleteLine());
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
  }

  /**
   * Sets the opponent team name.
   *
   * @param teamName the team name to set the opponent to
   */
  public void setOpponentTeamName(String teamName) {
    opponentTeamName = teamName;
  }

  /**
   * Returns the totals for this game.
   *
   * @return the totals
   */
  public BoxScoreLine getTotals() {
    return totals;
  }

  /**
   * Returns the opponent's totals for this game.
   *
   * @return the opponent's totals
   */
  public BoxScoreLine getOpponentTotals() {
    return opponentTotals;
  }

  /**
   * Sets the opponent's totals for this game.
   *
   * @param totals the opponent's totals
   */
  public void setOpponentTotals(BoxScoreLine totals) {
    opponentTotals = totals;
  }

  /**
   * Returns whether this game is a home game for this team.
   *
   * @return true if the game is home, false otherwise
   */
  public boolean isHome() {
    return home;
  }
}
