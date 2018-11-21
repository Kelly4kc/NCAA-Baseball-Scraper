package objects;

import java.util.Arrays;

public class BoxScoreLine implements Comparable<Integer> {
  private int year;
  private Integer gameId;
  private boolean home;
  private String teamName;
  private String opponentTeamName;
  private Integer playerId;
  private String playerName;
  private String position;
  private String[] line;

  public BoxScoreLine(int year, boolean home, String[] line) {
    this.year = year;
    gameId = Integer.parseInt(line[0]);
    this.home = home;
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
    this.line = Arrays.copyOfRange(line, 5, line.length - 1);
  }

  public int getYear() {
    return year;
  }

  public Integer getGameId() {
    return gameId;
  }
  
  public boolean isHome() {
    return home;
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

  @Override
  public int compareTo(Integer other) {
    return gameId.compareTo(other);
  }

  public String[] getCompleteLine() {
    String[] wholeLine = new String[6 + line.length];
    wholeLine[0] = String.valueOf(gameId);
    wholeLine[1] = teamName;
    wholeLine[2] = String.valueOf(playerId);
    wholeLine[3] = playerName;
    wholeLine[4] = position;
    wholeLine[5] = "1";
    for (int i = 0; i < line.length; i++) {
      wholeLine[i + 6] = line[i];
    }
    return wholeLine;
  }
}
