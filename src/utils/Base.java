package utils;

public enum Base {
  First("First"), Second("Second"), Third("Third"), Home("Home");

  private String baseString;

  private Base(String baseString) {
    this.baseString = baseString;
  }

  @Override
  public String toString() {
    return baseString;
  }
}
