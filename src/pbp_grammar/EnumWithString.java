package pbp_grammar;

public interface EnumWithString {
  
  @Override
  String toString();

  /**
   * Returns the generic string associated with this play by play type.
   * @return the generic string
   */
  String getGenericString();
}
