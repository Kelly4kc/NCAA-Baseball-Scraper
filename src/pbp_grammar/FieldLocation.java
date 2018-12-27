package pbp_grammar;

/**
 * Contains any field location in the play by play.
 */
public enum FieldLocation implements EnumWithString {
  Pitcher("pitcher"), Catcher("catcher"), Down_The_First_Base_Line("down the 1b line"),First_Base("first base"), Through_The_Right_Side(
      "through the right side"), Second_Base("second base"), Third_Base(
          "third base"), Down_The_Third_Base_Line("down the 3b line"), Through_The_Left_Side("through the left side"), Shortstop(
              "shortstop"), Up_The_Middle("up the middle"), LF_Line(
                  "down the lf line"), Left_Field_Line("down the left field line"), Left_Field(
                      "left field"), Left_Center("left center"), Center_Field(
                          "center field"), Right_Center("right center"), Right_Field_Line(
                              "down the right field line"), Right_Field(
                                  "right field"), RF_Line("down the rf line");
  private String fieldLocationString;

  FieldLocation(String fieldLocationString) {
    this.fieldLocationString = fieldLocationString;
  }

  @Override
  public String toString() {
    return fieldLocationString;
  }

  public String getGenericString() {
    return "fieldlocation";
  }
}
