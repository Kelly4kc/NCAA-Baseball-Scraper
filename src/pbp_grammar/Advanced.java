package pbp_grammar;

/**
 * Contains any advance in the play by play.
 */
public enum Advanced implements EnumWithString {
  Advanced_To("advanced"), Stole("stole"), Obstruction("obstruction"), Reached(
      "reached"), Scored("scored");

  private String advanceString;

  Advanced(String advanceString) {
    this.advanceString = advanceString;
  }

  @Override
  public String toString() {
    return advanceString;
  }

  @Override
  public String getGenericString() {
    return "advanced";
  }

}
