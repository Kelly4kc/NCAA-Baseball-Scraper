package pbp_grammar;

/**
 * Contains any advance type in the play by play.
 */
public enum AdvanceType implements EnumWithString {
  No_Advance("no advance"), Did_Not_Advance("did not advance"), Error_By("on an error by"), Error(
      "on the error"), Fielding_Error("on a fielding error by"), Throwing_Error(
          "on a throwing error by"), Muffed_Throw("on a muffed throw by"), Throw(
              "on the throw"), Wild_Pitch("on a wild pitch"), Passed_Ball("on a passed ball"), Balk(
                  "on a balk"), Fielders_Choice("on a fielder's choice"), Dropped_Fly(
                      "on a dropped fly by"), Failed_Pick_Off(
                          "failed pickoff attempt"), On_Catchers_Interference(
                              "on catcher's interference"),;

  private String advanceString;

  AdvanceType(String advanceType) {
    this.advanceString = advanceType;
  }

  @Override
  public String toString() {
    return advanceString;
  }

  @Override
  public String getGenericString() {
    return "advancetype";
  }

}
