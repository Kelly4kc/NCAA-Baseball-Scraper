package box_score_objects;

import java.util.Arrays;

import utils.Venue;

public class BoxScoreLine implements Comparable<Integer> {
  private Venue v;
  private int year;
  private Integer gameId;
  private String teamName;
  private String opponentTeamName;
  private Integer playerId;
  private String playerName;
  private String position;
  private int lineUpSpot;
  private String[] line;

  public BoxScoreLine(int year, Venue v, String[] line, int lineUpSpot) {
    this.year = year;
    gameId = Integer.parseInt(line[0]);
    this.v = v;
    teamName = line[1];
    if (line[2] != "") {
      try {
        playerId = Integer.parseInt(line[2]);
      } catch (NumberFormatException n) {
        playerId = 0;
      }
    } else {
      playerId = -1;
    }
    playerName = line[3];
    position = line[4];
    this.line = Arrays.copyOfRange(line, 5, line.length);
    this.lineUpSpot = lineUpSpot;
  }

  public int getYear() {
    return year;
  }

  public Integer getGameId() {
    return gameId;
  }

  public Venue getVenue() {
    return v;
  }

  public String getTeamName() {
    return teamName;
  }

  public String getOpponentTeamName() {
    return opponentTeamName;
  }

  public void setOpponentTeamName(String teamName) {
    this.opponentTeamName = teamName;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public String getPosition() {
    return position;
  }

  public String[] getLine() {
    return line;
  }

  public int getLineUpSpot() {
    return lineUpSpot;
  }

  @Override
  public int compareTo(Integer other) {
    return gameId.compareTo(other);
  }

  public String[] getCompleteLine() {
    String[] wholeLine = new String[7 + line.length];
    wholeLine[0] = String.valueOf(gameId);
    wholeLine[1] = teamName;
    wholeLine[2] = String.valueOf(playerId);
    wholeLine[3] = playerName;
    wholeLine[4] = position;
    wholeLine[5] = String.valueOf(lineUpSpot);
    wholeLine[6] = "1";
    for (int i = 0; i < line.length; i++) {
      wholeLine[i + 7] = line[i];
    }
    return wholeLine;
  }
}
