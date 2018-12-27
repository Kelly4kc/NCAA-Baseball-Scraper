package pbp_grammar;

/**
 * Contains any run type in the play by play.
 */
public enum RunType implements EnumWithString {
  Unearned("unearned"), Team_Unearned("team unearned");

  private String runTypeString;

  RunType(String runTypeString) {
    this.runTypeString = runTypeString;
  }

  @Override
  public String toString() {
    return runTypeString;
  }

  @Override
  public String getGenericString() {
    return "runType";
  }

}
