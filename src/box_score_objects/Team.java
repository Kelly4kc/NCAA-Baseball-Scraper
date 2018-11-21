package box_score_objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.NCAAUtils;
import utils.Type;
import utils.Venue;

public class Team {
  private Type t;
  private int year;
  private String teamName;
  private ArrayList<TeamBoxScore> awayGames;
  private ArrayList<TeamBoxScore> homeGames;
  private HashMap<Integer, Player> players;
  private HashMap<Venue, double[]> totals;
  private HashMap<Venue, double[]> opponentTotals;

  public Team(int year, String teamName, Type t) {
    this.year = year;
    this.teamName = teamName;
    this.t = t;
    awayGames = new ArrayList<>();
    homeGames = new ArrayList<>();
    players = new HashMap<>();
    totals = new HashMap<>();
    opponentTotals = new HashMap<>();
  }

  public void add(TeamBoxScore boxScore) {
    if (!boxScore.getTeamName().equals(teamName)) {
      throw new IllegalArgumentException(
          "The team of the box score must be the same as the team it is being added to");
    }
    if (boxScore.isHome()) {
      homeGames.add(boxScore);
      addLineToTotals(boxScore.getTotals(), Venue.Home, totals);
      addLineToTotals(boxScore.getOpponentTotals(), Venue.Home, opponentTotals);
    } else {
      awayGames.add(boxScore);
      addLineToTotals(boxScore.getTotals(), Venue.Away, totals);
      addLineToTotals(boxScore.getOpponentTotals(), Venue.Away, opponentTotals);
    }
    for (BoxScoreLine line : boxScore.getLines()) {
      Player player = players.get(line.getPlayerId());
      if (player == null) {
        player = new Player(year, line.getPlayerName(), line.getPlayerId(), line.getTeamName(), t);
        players.put(player.getPlayerId(), player);
      }
      player.add(line);
    }
    addLineToTotals(boxScore.getTotals(), Venue.Neutral, totals);    
    addLineToTotals(boxScore.getOpponentTotals(), Venue.Neutral, opponentTotals);
  }

  public String getTeamName() {
    return teamName;
  }

  public void writePlayers(CSVWriter writer, Venue v) {
    Set<Integer> playerIds = players.keySet();
    for (Integer playerId : playerIds) {
      writer.writeNext(players.get(playerId).getTotals(v));
    }
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
  }
  
  public void writeTeams(CSVWriter writer, Venue v) {
    writer.writeNext(getTotals(v));
    writer.writeNext(getOpponentTotals(v));
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
  }

  public String[] getTotals(Venue v) {
    return getTotalsString(v);
  }
  
  public String[] getOpponentTotals(Venue v) {
    return getOpponentTotalsString(v);
  }

  public void writeBoxScores(CSVWriter writer) {
    for (TeamBoxScore game : awayGames) {
      game.write(writer);
    }
    for (TeamBoxScore game : homeGames) {
      game.write(writer);
    }
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Unable to write.");
      System.exit(0);
    }
  }

  private void addLineToTotals(BoxScoreLine line, Venue v, HashMap<Venue, double[]> totals) {
    double[] totalsArray = totals.get(v);
    for (int i = 0; i < line.getLine().length; i++) {
      if (totalsArray == null) {
        totalsArray = new double[line.getLine().length];
        totals.put(v, totalsArray);
      }
      if ((i + 6 < NCAAUtils.HEADERS.get(t).get(year).length)
          && NCAAUtils.HEADERS.get(t).get(year)[i + 6].equals("IP")) {
        totalsArray[i] =
            NCAAUtils.inningsAdder(totalsArray[i], Double.parseDouble(line.getLine()[i]));
      } else {
        totalsArray[i] = totalsArray[i] + Double.parseDouble(line.getLine()[i]);
      }
    }
  }

  private String[] getTotalsString(Venue v) {
    double[] totalsArray = totals.get(v);
    if (totalsArray != null) {
      String[] totalsString = new String[totalsArray.length + 6];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = "0";
      totalsString[3] = "Totals";
      totalsString[4] = v.toString();
      totalsString[5] = "";
      for (int i = 0; i < totalsArray.length; i++) {
        totalsString[i + 6] = String.valueOf(totalsArray[i]);
      }
      return totalsString;
    } else {
      String[] totalsString = new String[5];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = "0";
      totalsString[3] = "Totals";
      totalsString[4] = v.toString();
      return totalsString;
    }
  }
  private String[] getOpponentTotalsString(Venue v) {
    double[] totalsArray = opponentTotals.get(v);
    if (totalsArray != null) {
      String[] totalsString = new String[totalsArray.length + 6];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = "0";
      totalsString[3] = "Opponent Totals";
      totalsString[4] = v.toString();
      totalsString[5] = "";
      for (int i = 0; i < totalsArray.length; i++) {
        totalsString[i + 6] = String.valueOf(totalsArray[i]);
      }
      return totalsString;
    } else {
      String[] totalsString = new String[5];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = "0";
      totalsString[3] = "Totals";
      totalsString[4] = v.toString();
      return totalsString;
    }
  }

  public HashMap<Integer, Player> getPlayers() {
    return players;
  }
}
