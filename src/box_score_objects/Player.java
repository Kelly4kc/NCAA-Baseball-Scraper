package box_score_objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import pbp_grammar.Position;
import utils.Venue;
import utils.CountingSet;
import utils.NCAAHeaders;
import utils.NCAAUtils;
import utils.Type;

/**
 * Player made up of box score lines. Tracks home and away stats.
 */
public class Player {

  private static int newPlayerId = -1;  /* used for players that do not have IDs, should only be
                                         players from extra divisional teams */

  Type t; // the type of the player (hitting, pitching, or fielding)
  int year;
  private String playerName;
  private Integer playerId;
  private String teamName;
  private HashMap<Venue, PositionMap> positions; /* a map of the number of games
                                                  played at each position at each venue */
  private HashMap<Venue, CountingSet<Integer>> lineUpSpots; /* a map of this player's
                                                                  line up spots at each game at
                                                                  each venue */
  private HashMap<Venue, ArrayList<BoxScoreLine>> games;
  private HashMap<Venue, double[]> totals; /* the player's stat totals at each venue (neutral is
                                            home + away*/

  /**
   * Constructor to be used when scraping from stats.ncaa.org.
   *
   * @param year       the year
   * @param playerName the player's name
   * @param playerId   the player's ID
   * @param teamName   the name of this player's team
   * @param t          the type of this player (hitting, pitching, fielding)
   */
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
    lineUpSpots.put(Venue.Away, new CountingSet<>());
    lineUpSpots.put(Venue.Home, new CountingSet<>());
    lineUpSpots.put(Venue.Neutral, new CountingSet<>());
    games = new HashMap<>();
    games.put(Venue.Away, new ArrayList<>());
    games.put(Venue.Home, new ArrayList<>());
    games.put(Venue.Neutral, new ArrayList<>());
    totals = new HashMap<>();
  }

  /**
   * Adds a box score line to this player.
   *
   * @param line the line to add
   */
  public void add(BoxScoreLine line) {
    // Adds the line to whichever totals the line's venue matches
    Venue v = line.getVenue();
    games.get(v).add(line);
    addLineToTotals(line, v);
    positions.get(v).put(line.getPosition());
    if (lineUpSpots.get(v).contains(line.getLineUpSpot())) {
      lineUpSpots.get(v).add(line.getLineUpSpot());
    } else {
      lineUpSpots.get(v).add(line.getLineUpSpot());
    }
    // Adds the line to neutral no matter what
    if (!v.equals(Venue.Neutral)) {
      games.get(Venue.Neutral).add(line);
      addLineToTotals(line, Venue.Neutral);
      positions.get(Venue.Neutral).put(line.getPosition());
      if (lineUpSpots.get(Venue.Neutral).contains(line.getLineUpSpot())) {
        lineUpSpots.get(Venue.Neutral).add(line.getLineUpSpot());
      } else {
        lineUpSpots.get(Venue.Neutral).add(line.getLineUpSpot());
      }
    }
  }

  /**
   * Returns this player's ID
   *
   * @return the player's ID
   */
  public Integer getPlayerId() {
    return playerId;
  }

  /**
   * Returns the totals associated with the supplied venue.
   *
   * @param v the venue (home, away, neutral)
   * @return the totals
   */
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
      Iterator<Integer> it2 = lineUpSpots.get(v).iterator();
      if (it2.hasNext()) {
        int nextLineUpSpot = it2.next();
        totalsString[4] = nextLineUpSpot + ":" + lineUpSpots.get(v).getCount(nextLineUpSpot);
        while (it2.hasNext()) {
          nextLineUpSpot = it2.next();
          totalsString[4] += ", " + nextLineUpSpot + ":" + lineUpSpots.get(v).getCount(nextLineUpSpot);
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
            String.valueOf(positions.get(v).getCount(Position.values()[i]));
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
