package play_by_play_objects;

import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class PlayByPlayGame {
  private int year;
  private Integer gameId;
  private String awayTeam;
  private String homeTeam;
  private ArrayList<PlayByPlayInning> innings;
  private int awayRuns;
  private int awayHits;
  private int awayErrors;
  private int awayLeftOnBase;
  private int homeRuns;
  private int homeHits;
  private int homeErrors;
  private int homeLeftOnBase;

  public PlayByPlayGame(int year, Integer gameId, int inning, String awayTeam, String homeTeam) {
    this.year = year;
    this.gameId = gameId;
    this.awayTeam = awayTeam;
    this.homeTeam = homeTeam;
    innings = new ArrayList<>();
  }

  public void addInning(PlayByPlayInning inning) {
    innings.add(inning);
    awayRuns += inning.getAwayRuns();
    awayHits += inning.getAwayHits();
    awayErrors += inning.getAwayErrors();
    awayLeftOnBase += inning.getAwayLeftOnBase();
    homeRuns += inning.getHomeRuns();
    homeHits += inning.getHomeHits();
    homeErrors += inning.getHomeErrors();
    homeLeftOnBase += inning.getHomeLeftOnBase();
  }

  public void writeGame(CSVWriter writer) {
    for (PlayByPlayInning inning : innings) {
      inning.writeInning(writer);
    }
    writer.writeNext(new String[] {String.valueOf(gameId), String.valueOf(innings.size()), awayTeam,
        homeTeam, awayTeam, "Game Summary",
        "R: " + awayRuns + " H: " + awayHits + " E: " + awayErrors + " LOB: " + awayLeftOnBase,
        awayRuns + "-" + homeRuns});
    writer.writeNext(new String[] {String.valueOf(gameId), String.valueOf(innings.size()), awayTeam,
        homeTeam, homeTeam, "Game Summary",
        "R: " + homeRuns + " H: " + homeHits + " E: " + homeErrors + " LOB: " + homeLeftOnBase,
        awayRuns + "-" + homeRuns});
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Failed to write to file");
    }
  }

  public Integer getGameId() {
    return gameId;
  }

  public ArrayList<PlayByPlayInning> getInnings() {
    return innings;
  }
}
