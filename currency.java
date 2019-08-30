class Currency {
  private String name;
  private String symbol;
  private double fiatRate;
  private boolean fiat;

  Currency(String n, String s) {
    name = n;
    fiatRate = 1;
    symbol = s;
    fiat = true;
  }

  Currency(String n, String s, double r) {
    name = n;
    fiatRate = r;
    symbol = s;
    fiat = false;
  }

  // Trenger omregning mellom to "fremmede" valuta
  public double toCurrency(Currency c) {
    if (fiat) {
      return c.getRate();
    } else if (c.isFiat()) {
      return fiatRate;
    }
    return 0;
  }

  public boolean isFiat() {
    return fiat;
  }

  public double getRate() {
    return fiatRate;
  }
}
