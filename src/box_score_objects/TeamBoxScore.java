package box_score_objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.NCAAHeaders;
import utils.NCAAUtils;
import utils.Type;

public class TeamBoxScore implements Comparable<Integer> {

  private Type t;
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

  public TeamBoxScore(Type t, ArrayList<BoxScoreLine> lines) {
    this.t = t;
    this.lines = lines;
    this.year = lines.get(0).getYear();
    this.gameId = lines.get(0).getGameId();
    this.teamName = lines.get(0).getTeamName();
    this.playerId = lines.get(0).getPlayerId();
  }

  public String getTeamName() {
    return teamName;
  }

  public Integer getGameId() {
    return gameId;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public void add(BoxScoreLine line) {
    if (line.getPlayerName().equals("Totals")) {
      totals = line;
    } else {
      lines.add(line);
    }
  }

  public ArrayList<BoxScoreLine> getLines() {
    return lines;
  }

  @Override
  public int compareTo(Integer other) {
    return gameId.compareTo(other);
  }

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

  public void setOpponentTeamName(String teamName) {
    opponentTeamName = teamName;
  }

  public BoxScoreLine getTotals() {
    return totals;
  }
  
  public BoxScoreLine getOpponentTotals() {
    return opponentTotals;
  }

  public void setOpponentTotals(BoxScoreLine totals) {
    opponentTotals = totals;
  }

  public boolean isHome() {
    return home;
  }
}
