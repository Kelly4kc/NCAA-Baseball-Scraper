package play_by_play_objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Team {
  int year;
  private String teamName;
  private ArrayList<PlayByPlayGame> games;
  private ArrayList<String> players;
  
  public Team(int year, String teamName) {
    this.year = year;
    this.teamName = teamName;
  }
  
  public void addGame(PlayByPlayGame game) {
    games.add(game);
  }
}
