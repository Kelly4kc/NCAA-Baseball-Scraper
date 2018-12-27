package pbp_grammar;

/**
 * Contains any out type in the play by play.
 */
public enum OutType implements EnumWithString {
  Out_At("out at"), Caught_Stealing("caught stealing"), Picked_Off(
      "picked off"), Out_On_Batters_Interference("out on batter's interference"), Interference(
          "interference"), On_The_Play(
              "out on the play"), Hidden_Ball_Trick("via hidden ball trick");

  private String outTypeString;

  OutType(String outTypeString) {
    this.outTypeString = outTypeString;
  }

  @Override
  public String toString() {
    return outTypeString;
  }

  @Override
  public String getGenericString() {
    return "outtype";
  }

}
