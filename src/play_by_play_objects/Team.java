package play_by_play_objects;

import java.util.ArrayList;

public class Team {
  int year;
  private String teamName;
  private ArrayList<PlayByPlayGame> games;
  
  public Team(int year, String teamName) {
    this.year = year;
    this.teamName = teamName;
  }
  
  public void addGame(PlayByPlayGame game) {
    games.add(game);
  }
}
