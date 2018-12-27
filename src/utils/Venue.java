package utils;

/**
 * Home, away, or neutral.
 */
public enum Venue {
  Away("Away"), Home("Home"), Neutral("Neutral");

  private String venueString;

  Venue(String venueString) {
    this.venueString = venueString;
  }

  @Override
  public String toString() {
    return venueString;
  }
}
