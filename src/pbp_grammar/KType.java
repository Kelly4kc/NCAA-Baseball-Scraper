package pbp_grammar;

/**
 * Contains any strikeout type in the play by play.
 */
public enum KType implements EnumWithString {
  Swinging("struck out swinging"), Looking("struck out looking"), Generic("struck out");

  private String kString;

  KType(String kString) {
    this.kString = kString;
  }

  @Override
  public String toString() {
    return kString;
  }

  @Override
  public String getGenericString() {
    return "ktype";
  }

}
