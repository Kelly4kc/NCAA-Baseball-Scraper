package pbp_grammar;

/**
 * Contains any on base type in the play by play.
 */
public enum OnBaseType implements EnumWithString {
  Singled("singled"), Doubled("doubled"), Tripled("tripled"), Homered(
      "homered"), Intentionally_Walked(
          "intentionally walked"), Walked("walked"), HBP("hit by pitch");

  private String onBaseString;

  OnBaseType(String onBaseString) {
    this.onBaseString = onBaseString;
  }

  @Override
  public String toString() {
    return onBaseString;
  }

  @Override
  public String getGenericString() {
    return "onbasetype";
  }
}
