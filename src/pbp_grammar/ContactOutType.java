package pbp_grammar;

/**
 * Contains any out that occurs after contact in the play by play.
 */
public enum ContactOutType implements EnumWithString {
  Flied_Out("flied out"), Fouled_Out("fouled out"), Grounded_Out("grounded out"), Infield_Fly(
      "infield fly"), Lined_Out("lined out"), Popped_Up("popped up");

  private String outString;

  ContactOutType(String outString) {
    this.outString = outString;
  }

  @Override
  public String toString() {
    return outString;
  }

  @Override
  public String getGenericString() {
    return "contactout";
  }
}
