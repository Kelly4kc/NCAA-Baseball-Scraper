package play_by_play_objects;

public class PlayByPlayLine {
  private int year;
  private Integer gameId;
  private int inning;
  private String awayTeam;
  private String homeTeam;
  private String team;
  private int eventId;
  private String event;
  private String score;

  public PlayByPlayLine(int year, String[] line) {
    this.year = year;
    gameId = Integer.parseInt(line[0]);
    inning = Integer.parseInt(line[1]);
    awayTeam = line[2];
    homeTeam = line[3];
    team = line[4];
    eventId = Integer.parseInt(line[5]);
    event = line[6];
    score = line[7];
  }

  public String[] getCompleteLine() {
    return new String[] {String.valueOf(gameId), String.valueOf(inning), awayTeam, homeTeam, team,
        String.valueOf(eventId), event, score};
  }
}
