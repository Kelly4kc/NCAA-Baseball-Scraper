package pbp_grammar;

/**
 * Contains any substitution in the play by play.
 */
public enum Substitution implements EnumWithString {
  Pinch_Hit("pinch hit"), Pinch_Ran("pinch ran"), Pinch_Hit_For("pinch hit for"), Pinch_Ran_For(
      "pinch ran for");


  private String substitutionString;

  Substitution(String substitutionString) {
    this.substitutionString = substitutionString;
  }

  @Override
  public String toString() {
    return substitutionString;
  }

  @Override
  public String getGenericString() {
    return "substitution";
  }

}
