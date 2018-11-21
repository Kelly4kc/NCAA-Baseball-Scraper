package play_by_play_objects;

import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class PlayByPlayInning {
  private int year;
  private Integer gameId;
  private int inning;
  private String awayTeam;
  private String homeTeam;
  private ArrayList<PlayByPlayLine> lines;
  private String score;
  private String awaySummary;
  private int awayRuns;
  private int awayHits;
  private int awayErrors;
  private int awayLeftOnBase;
  private String homeSummary;
  private int homeRuns;
  private int homeHits;
  private int homeErrors;
  private int homeLeftOnBase;

  public PlayByPlayInning(int year, Integer gameId, int inning, String awayTeam, String homeTeam) {
    this.year = year;
    this.gameId = gameId;
    this.inning = inning;
    this.awayTeam = awayTeam;
    this.homeTeam = homeTeam;
    lines = new ArrayList<>();
  }

  public void addLine(PlayByPlayLine line) {
    lines.add(line);
  }

  public void setAwaySummary(String summary) {
    awaySummary = summary;
    awayRuns =
        Integer.parseInt(summary.substring(summary.indexOf("R: ") + 3, summary.indexOf(" H:")));
    awayHits =
        Integer.parseInt(summary.substring(summary.indexOf("H: ") + 3, summary.indexOf(" E:")));
    awayErrors =
        Integer.parseInt(summary.substring(summary.indexOf("E: ") + 3, summary.indexOf(" LOB:")));
    awayLeftOnBase = Integer.parseInt(summary.substring(summary.indexOf("LOB: ") + 5));
  }

  public void setHomeSummary(String summary) {
    homeSummary = summary;
    if (!summary.equals("")) {
      homeRuns =
          Integer.parseInt(summary.substring(summary.indexOf("R: ") + 3, summary.indexOf(" H:")));
      homeHits =
          Integer.parseInt(summary.substring(summary.indexOf("H: ") + 3, summary.indexOf(" E:")));
      homeErrors =
          Integer.parseInt(summary.substring(summary.indexOf("E: ") + 3, summary.indexOf(" LOB:")));
      homeLeftOnBase = Integer.parseInt(summary.substring(summary.indexOf("LOB: ") + 5));
    } else {
      homeRuns = 0;
      homeHits = 0;
      homeErrors = 0;
      homeLeftOnBase = 0;
    }
  }

  public void setScore(String score) {
    this.score = score;
  }

  public void writeInning(CSVWriter writer) {
    for (PlayByPlayLine line : lines) {
      writer.writeNext(line.getCompleteLine());
    }
    writer.writeNext(new String[] {String.valueOf(gameId), String.valueOf(inning), awayTeam,
        homeTeam, "Inning Summary", awaySummary, homeSummary, score});
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Failed to write to file");
      System.exit(0);
    }
  }

  public int getAwayRuns() {
    return awayRuns;
  }

  public int getAwayHits() {
    return awayHits;
  }

  public int getAwayErrors() {
    return awayErrors;
  }

  public int getAwayLeftOnBase() {
    return awayLeftOnBase;
  }

  public int getHomeRuns() {
    return homeRuns;
  }

  public int getHomeHits() {
    return homeHits;
  }

  public int getHomeErrors() {
    return homeErrors;
  }

  public int getHomeLeftOnBase() {
    return homeLeftOnBase;
  }
}
