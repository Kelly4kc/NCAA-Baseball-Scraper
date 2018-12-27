package box_score_objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.opencsv.CSVWriter;

import utils.Venue;
import utils.NCAAHeaders;
import utils.NCAAUtils;
import utils.Type;

/**
 * Team made up of games and players. Tracks home and away stats.
 */
public class Team {
  private Type t; // the type of the team (hitting, pitching, or fielding)
  private int year;
  private String teamName;
  private HashMap<Venue, HashMap<Integer, TeamBoxScore>> games;
  private HashMap<Integer, Player> players;
  private HashMap<Venue, double[]> totals;
  private HashMap<Venue, double[]> opponentTotals;

  public Team(int year, String teamName, Type t) {
    this.year = year;
    this.teamName = teamName;
    this.t = t;
    games = new HashMap<>();
    games.put(Venue.Away, new HashMap<>());
    games.put(Venue.Home, new HashMap<>());
    games.put(Venue.Neutral, new HashMap<>());
    players = new HashMap<>();
    totals = new HashMap<>();
    opponentTotals = new HashMap<>();
  }

  public void add(TeamBoxScore boxScore) {
    if (!boxScore.getTeamName().equals(teamName)) {
      throw new IllegalArgumentException(
          "The team of the box score must be the same as the team it is being added to");
    }
    Venue v;
    if (boxScore.isHome()) {
      v = Venue.Home;
    } else {
      v = Venue.Away;
    }
    games.get(v).put(boxScore.getGameId(), boxScore);
    addLineToTotals(boxScore.getTotals(), v, totals);
    addLineToTotals(boxScore.getOpponentTotals(), v, opponentTotals);

    for (BoxScoreLine line : boxScore.getLines()) {
      Player player = players.get(line.getPlayerId());
      if (player == null) {
        player = new Player(year, line.getPlayerName(), line.getPlayerId(), line.getTeamName(), t);
        players.put(player.getPlayerId(), player);
      }
      player.add(line);
    }
    games.get(Venue.Neutral).put(boxScore.getGameId(), boxScore);
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
    HashMap<Integer, TeamBoxScore> allGames = games.get(Venue.Neutral);
    Set<Integer> gameIds = allGames.keySet();
    for (Integer gameId : gameIds) {
      allGames.get(gameId).write(writer);
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
      String stat = line.getLine()[i].replaceAll("(\\*|\\-)*", "");
      if ((i + 6 < NCAAHeaders.BOX_HEADERS.get(t).get(year).length)
          && NCAAHeaders.BOX_HEADERS.get(t).get(year)[i + 6].equals("IP")) {
        totalsArray[i] = NCAAUtils.inningsAdder(totalsArray[i], Double.parseDouble(stat));
      } else {
        totalsArray[i] = totalsArray[i] + Double.parseDouble(stat);
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
