package pbp_grammar;

/**
 * Contains any number of RBIs in the play by play.
 */
public enum RBI implements EnumWithString {
  Two_RBI("2 RBI"), Three_RBI("3 RBI"), Four_RBI("4 RBI"), One_RBI("RBI");

  private String rbiString;

  RBI(String rbiString) {
    this.rbiString = rbiString;
  }

  @Override
  public String toString() {
    return rbiString;
  }

  @Override
  public String getGenericString() {
    return "rbivalue";
  }
}
