package pbp_grammar;

/**
 * Contains any double play in the play by play.
 */

public enum DoublePlayType implements EnumWithString {
  Hit_Into("hit into double play"), Flied_Into("flied into double play"), Fouled_Into(
      "fouled into double play"), Grounded_Into(
      "grounded into double play"), Lined_Into("lined into double play"), Popped_Into("popped " +
      "into double play");

  private String doublePlayString;

  DoublePlayType(String doublePlayString) {
    this.doublePlayString = doublePlayString;
  }

  @Override
  public String toString() {
    return doublePlayString;
  }

  @Override
  public String getGenericString() {
    return "doubleplay";
  }
}
