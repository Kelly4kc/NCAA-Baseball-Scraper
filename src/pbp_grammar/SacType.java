package pbp_grammar;

/**
 * Contains any sac type in the play by play.
 */
public enum SacType implements EnumWithString {
  Sacrifice_Fly("sacrifice fly"), SF("SF"), SAC("SAC");

  private String sacTypeString;

  private SacType(String sacTypeString) {
    this.sacTypeString = sacTypeString;
  }

  @Override
  public String toString() {
    return sacTypeString;
  }

  @Override
  public String getGenericString() {
    return "sactype";
  }
}
