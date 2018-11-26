package box_score_objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import utils.NCAAHeaders;
import utils.NCAAUtils;
import utils.Position;
import utils.Type;
import utils.Venue;

public class Player {

  private static int newPlayerId = -1;

  Type t;
  int year;
  private String playerName;
  private Integer playerId;
  private String teamName;
  private HashMap<Venue, PositionMap> positions;
  private HashMap<Venue, HashMap<Integer, Integer>> lineUpSpots;
  private HashMap<Venue, ArrayList<BoxScoreLine>> games;
  private HashMap<Venue, double[]> totals;

  public Player(int year, String playerName, Integer playerId, String teamName, Type t) {
    this.year = year;
    this.playerName = playerName;
    if (playerId == 0) {
      this.playerId = newPlayerId;
      newPlayerId--;
    } else {
      this.playerId = playerId;
    }
    this.teamName = teamName;
    this.t = t;
    positions = new HashMap<>();
    positions.put(Venue.Away, new PositionMap());
    positions.put(Venue.Home, new PositionMap());
    positions.put(Venue.Neutral, new PositionMap());
    lineUpSpots = new HashMap<>();
    lineUpSpots.put(Venue.Away, new HashMap<>());
    lineUpSpots.put(Venue.Home, new HashMap<>());
    lineUpSpots.put(Venue.Neutral, new HashMap<>());
    games = new HashMap<>();
    games.put(Venue.Away, new ArrayList<>());
    games.put(Venue.Home, new ArrayList<>());
    games.put(Venue.Neutral, new ArrayList<>());
    totals = new HashMap<>();
  }

  public void add(BoxScoreLine line) {
    Venue v = line.getVenue();

    games.get(v).add(line);
    addLineToTotals(line, v);
    positions.get(v).put(line.getPosition());
    if (lineUpSpots.get(v).containsKey(line.getLineUpSpot())) {
      lineUpSpots.get(v).put(line.getLineUpSpot(),
          lineUpSpots.get(v).get(line.getLineUpSpot()) + 1);
    } else {
      lineUpSpots.get(v).put(line.getLineUpSpot(), 1);
    }
    games.get(Venue.Neutral).add(line);
    addLineToTotals(line, Venue.Neutral);
    positions.get(Venue.Neutral).put(line.getPosition());
    if (lineUpSpots.get(Venue.Neutral).containsKey(line.getLineUpSpot())) {
      lineUpSpots.get(Venue.Neutral).put(line.getLineUpSpot(),
          lineUpSpots.get(Venue.Neutral).get(line.getLineUpSpot()) + 1);
    } else {
      lineUpSpots.get(Venue.Neutral).put(line.getLineUpSpot(), 1);
    }
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public String[] getTotals(Venue v) {
    return getTotalsString(v);
  }

  private void addLineToTotals(BoxScoreLine line, Venue v) {
    double[] totalsArray = totals.get(v);
    for (int i = 0; i < line.getLine().length; i++) {
      if (totalsArray == null) {
        totalsArray = new double[line.getLine().length];
        totals.put(v, totalsArray);
      }
      String stat = line.getLine()[i].replaceAll("(\\*|\\-)*", "");
      if ((i + 6 < NCAAHeaders.BOX_HEADERS.get(t).get(year).length)
          && NCAAHeaders.BOX_HEADERS.get(t).get(year)[i + 6].equals("IP")) {
        totalsArray[i] =
            NCAAUtils.inningsAdder(totalsArray[i], Double.parseDouble(stat));
      } else {
        totalsArray[i] = totalsArray[i] + Double.parseDouble(stat);
      }
    }
  }

  private String[] getTotalsString(Venue v) {
    double[] totalsArray = totals.get(v);
    if (totalsArray != null) {
      String[] totalsString = new String[totalsArray.length + 6 + Position.values().length];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = String.valueOf(playerId);
      totalsString[3] = playerName;
      Set<Integer> lineUpSpotSet = lineUpSpots.get(v).keySet();
      Iterator<Integer> it2 = lineUpSpotSet.iterator();
      if (it2.hasNext()) {
        int nextLineUpSpot = it2.next();
        totalsString[4] = nextLineUpSpot + ":" + lineUpSpots.get(v).get(nextLineUpSpot);
        while (it2.hasNext()) {
          nextLineUpSpot = it2.next();
          totalsString[4] += ", " + nextLineUpSpot + ":" + lineUpSpots.get(v).get(nextLineUpSpot);
        }
      } else {
        totalsString[4] = "";
      }

      totalsString[5] = String.valueOf(games.get(v).size());
      for (int i = 0; i < totalsArray.length; i++) {
        totalsString[i + 6] = String.valueOf(totalsArray[i]);
      }
      for (int i = 0; i < Position.values().length; i++) {
        totalsString[i + totalsArray.length + 6] =
            String.valueOf(positions.get(v).get(Position.values()[i]));
      }
      return totalsString;
    } else {
      String[] totalsString = new String[4];
      totalsString[0] = "0";
      totalsString[1] = teamName;
      totalsString[2] = String.valueOf(playerId);
      totalsString[3] = playerName;
      return totalsString;
    }
  }

  public HashMap<Venue, PositionMap> getPositions() {
    return positions;
  }
}
