package box_score_objects;

import java.util.Arrays;

import utils.Venue;

/**
 * A line in a box score. Works with any type of box score line from stats.ncaa.org.
 *
 * @author Kevin Kelly
 */
public class BoxScoreLine implements Comparable<Integer> {
  private int year;
  private Venue v;
  private Integer gameId;
  private String teamName;
  private String opponentTeamName;
  private Integer playerId;
  private String playerName;
  private String position;
  private int lineUpSpot; /* the rank this player appeared in the box score, may not be 100%
                           accurate, especially for pitchers or when subs occur */
  private String[] line; // the line from the box score, not including the first 4 elements

  /**
   * Constructor to use when scraping from the web.
   *
   * @param year       the year of this box score
   * @param v          the venue (home, away, neutral) this game was at
   * @param line       the entire line from the box score - A line should be formatted as
   *                   follows: {Game ID, Team Name, Player ID, Player Name, Position, ...[rest of
   *                   line]}
   * @param lineUpSpot the spot in the lineup this line is in
   */
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

  /**
   * Constructor for use when each value needs to be specified. The line should only include
   * stats.
   *
   * @param year             the year
   * @param v                the venue (home, away, neutral)
   * @param gameId           the game's ID
   * @param teamName         the team's name
   * @param opponentTeamName the opponent team's name
   * @param playerId         the player's ID
   * @param playerName       the player's name
   * @param position         the player's position
   * @param lineUpSpot       the spot in the line up of this line
   * @param line             the stats of the line
   */
  public BoxScoreLine(int year, Venue v, int gameId, String teamName, String opponentTeamName,
                      int playerId,
                      String playerName, String position, int lineUpSpot, String[] line) {
    this.year = year;
    this.gameId = gameId;
    this.v = v;
    this.teamName = teamName;
    this.opponentTeamName = opponentTeamName;
    this.playerId = playerId;
    this.playerName = playerName;
    this.position = position;
    this.lineUpSpot = lineUpSpot;
    this.line = Arrays.copyOf(line, line.length);
  }

  /**
   * Returns the year of this box score line.
   *
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Returns the game ID of this box score line.
   *
   * @return the game ID
   */
  public Integer getGameId() {
    return gameId;
  }

  /**
   * Returns the venue (home, away, or neutral) of this box score line.
   *
   * @return the venue
   */
  public Venue getVenue() {
    return v;
  }

  /**
   * Returns the team name of this box score line.
   *
   * @return the team name
   */
  public String getTeamName() {
    return teamName;
  }

  /**
   * Returns the opponent team name of this box score line.
   *
   * @return the opponent team name
   */
  public String getOpponentTeamName() {
    return opponentTeamName;
  }

  /**
   * Sets the opponent team name of this box score line.
   *
   * @param teamName the opponent team's name
   */
  public void setOpponentTeamName(String teamName) {
    this.opponentTeamName = teamName;
  }

  /**
   * Returns the player ID of this box score line.
   *
   * @return the player ID
   */
  public Integer getPlayerId() {
    return playerId;
  }

  /**
   * Returns the player name of this box score line.
   *
   * @return the player name
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Returns the position of this box score line.
   *
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * Returns the line, not containing the game ID, player ID, player name, or position.
   *
   * @return the line
   */
  public String[] getLine() {
    return line;
  }

  /**
   * Returns the line up spot of this box score line.
   *
   * @return the line up spot
   */
  public int getLineUpSpot() {
    return lineUpSpot;
  }

  @Override
  public int compareTo(Integer other) {
    return gameId.compareTo(other);
  }

  /**
   * Returns the entire line.
   *
   * @return the entire line, in this format: {game ID, team name, player ID, player name,
   * position, line up spot, ...[rest of line]}
   */
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
