package utils;

public enum FieldLocation {
  First_Base("First Base"), Second_Base("Second Base"), Third_Base("Third Base"), Shortstop(
      "Shortstop"), Left_Field_Line("LF Line"), Left_Field("Left Field"), Left_Center(
          "Left Center"), Center_Field("Center Field"), Right_Center(
              "Right Center"), Right_Field("Right Field"), Right_Field_Line("RF_Line");
  private String fieldLocationString;

  private FieldLocation(String fieldLocationString) {
    this.fieldLocationString = fieldLocationString;
  }

  @Override
  public String toString() {
    return fieldLocationString;
  }
}
