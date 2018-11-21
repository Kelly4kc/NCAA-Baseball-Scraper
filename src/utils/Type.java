package utils;

public enum Type {
  Hitting("Hitting"), Pitching("Pitching"), Fielding("Fielding");

  private String typeString;

  private Type(String typeString) {
    this.typeString = typeString;
  }

  @Override
  public String toString() {
    return typeString;
  }
}
