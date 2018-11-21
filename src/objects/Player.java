package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import utils.NCAAUtils;
import utils.Type;
import utils.Venue;

public class Player {

  private static int newPlayerId = -1;

  Type t;
  int year;
  private String playerName;
  private Integer playerId;
  private String teamName;
  private HashMap<String, Integer> positions;
  private ArrayList<BoxScoreLine> awayGames;
  private ArrayList<BoxScoreLine> homeGames;
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
    awayGames = new ArrayList<>();
    homeGames = new ArrayList<>();
    totals = new HashMap<>();
  }

  public void add(BoxScoreLine line) {
    if (line.isHome()) {
      homeGames.add(line);
      addLineToTotals(line, Venue.Home);
    } else {
      awayGames.add(line);
      addLineToTotals(line, Venue.Away);
    }
    if (positions.containsKey(line.getPosition())) {
      positions.put(line.getPosition(), positions.get(line.getPosition()) + 1);
    } else {
      positions.put(line.getPosition(), 1);
    }
    addLineToTotals(line, Venue.Neutral);
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
      totalsString[2] = String.valueOf(playerId);
      totalsString[3] = playerName;
      Set<String> positionSet = positions.keySet();
      Iterator<String> it = positionSet.iterator();
      if (it.hasNext()) {
        String nextPosition = it.next();
        totalsString[4] = nextPosition + ":" + positions.get(nextPosition);
        while (it.hasNext()) {
          nextPosition = it.next();
          totalsString[4] += ", " + nextPosition + ":" + positions.get(nextPosition);
        }
      } else {
        totalsString[4] = "";
      }
      switch (v) {
        case Away:
          totalsString[5] = String.valueOf(awayGames.size());
          break;
        case Home:
          totalsString[5] = String.valueOf(homeGames.size());
          break;
        case Neutral:
          totalsString[5] = String.valueOf(awayGames.size() + homeGames.size());
          break;
      }
      for (int i = 0; i < totalsArray.length; i++) {
        totalsString[i + 6] = String.valueOf(totalsArray[i]);
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
}
