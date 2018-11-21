package utils;

public enum Position {
  PITCHER("P"), CATCHER("C"), FIRST_BASE("1B"), SECOND_BASE("2B"), THIRD_BASE("3B"), SHORTSTOP(
      "SS"), LEFT_FIELD("LF"), CENTER_FIELD("CF"), RIGHT_FIELD(
          "RF"), DESIGNATED_HITTER("DH"), PINCH_HITTER("PH"), PINCH_RUNNER("PR"),;
  private String positionString;

  private Position(String position) {
    this.positionString = position;
  }

  @Override
  public String toString() {
    return positionString;
  }
}
