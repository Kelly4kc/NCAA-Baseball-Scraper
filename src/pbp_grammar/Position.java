package pbp_grammar;

/**
 * Contains any position in the play by play.
 */
public enum Position implements EnumWithString {
  PITCHER("p"), CATCHER("c"), FIRST_BASE("1b"), SECOND_BASE("2b"), THIRD_BASE("3b"), SHORTSTOP(
      "ss"), LEFT_FIELD("lf"), CENTER_FIELD("cf"), RIGHT_FIELD(
          "rf"), DESIGNATED_HITTER("dh"), PINCH_HITTER("ph"), PINCH_RUNNER("pr"),;
  private String positionString;

  Position(String position) {
    this.positionString = position;
  }

  @Override
  public String toString() {
    return positionString;
  }
  
  public String getGenericString() {
    return "position";
  }
}
