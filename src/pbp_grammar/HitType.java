package pbp_grammar;

/**
 * Contains any hit type in the play by play.
 */
public enum HitType implements EnumWithString {
  Bunt("bunt"), Ground_Rule("ground-rule"), Inside_The_Park("inside the park");

  private String hitTypeString;

  HitType(String hitTypeString) {
    this.hitTypeString = hitTypeString;
  }

  @Override
  public String toString() {
    return hitTypeString;
  }

  @Override
  public String getGenericString() {
    return "hittype";
  }
}
