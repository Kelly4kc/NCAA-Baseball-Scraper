package utils;

public enum Venue {
  Away("Away"), Home("Home"), Neutral("Neutral");

  private String venueString;

  private Venue(String venueString) {
    this.venueString = venueString;
  }

  @Override
  public String toString() {
    return venueString;
  }
}
