package box_score_objects;

import pbp_grammar.Position;
import utils.CountingSet;

/**
 * Class that maps positions to number of games played.
 */
public class PositionMap extends CountingSet<Position> {

  private Position[] positions;

  /**
   * Initializes a new position map.
   */
  public PositionMap() {
    super();
    positions = Position.values();
    /* This next section is necessary since the replace method in the put method will not work if
     partial position abbreviations are found first (e.g. if a player was a pinch runner, which
     has the abbreviation PR, the put method would match P for pitcher first, leaving the R and
     never matching PR).*/
    Position pitcher = positions[0];
    Position catcher = positions[1];
    Position centerField = positions[7];
    Position rightField = positions[8];
    Position pinchRunner = positions[11];
    positions[0] = rightField;
    positions[1] = centerField;
    positions[7] = catcher;
    positions[8] = pinchRunner;
    positions[11] = pitcher;

    for (Position p : Position.values()) {
      putCount(p, 0); // initializes each games played to 0
    }
  }

  /**
   * Puts one game at this position into the map. The String may contain multiple positions that
   * do not need to be delimited in any way.
   *
   * @param position the position to add
   */
  public void put(String position) {
    for (Position p : positions) {
      if (position.contains(p.toString())) {
        position = position.replace(p.toString(), "");
        putCount(p, getCount(p) + 1);
      }
    }
  }
}
