package pbp_grammar;

/**
 * Contains any assist type in the play by play.
 */
public enum AssistType implements EnumWithString{
  Assist_By("assist by"), Unassisted("unassisted");

  private String assistString;

  AssistType(String assistString) {
    this.assistString = assistString;
  }

  @Override
  public String toString() {
    return assistString;
  }

  @Override
  public String getGenericString() {
    return "assisttype";
  }

}
