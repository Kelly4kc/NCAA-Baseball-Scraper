package box_score_objects;

import java.util.HashMap;

import utils.Position;

@SuppressWarnings("serial")
public class PositionMap extends HashMap<Position, Integer> {

  private static Position[] positions;
  static {
    positions = Position.values();
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
  }

  public PositionMap() {
    super();
    for (Position p : Position.values()) {
      put(p, 0);
    }
  }

  public void put(String position) {
    for (Position p : positions) {
      if (position.contains(p.toString())) {
        position = position.replace(p.toString(), "");
        put(p, get(p) + 1);
      }
    }
  }
}
