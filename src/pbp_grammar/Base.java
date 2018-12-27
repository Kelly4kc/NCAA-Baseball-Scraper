package pbp_grammar;

/**
 * Contains any base in the play by play.
 */
public enum Base implements EnumWithString{
  First("first"), Second("second"), Third("third"), Home("home");

  private String baseString;

  Base(String baseString) {
    this.baseString = baseString;
  }

  @Override
  public String toString() {
    return baseString;
  }
  
  public String getGenericString() {
    return "base";
  }
}
