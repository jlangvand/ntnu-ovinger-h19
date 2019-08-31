package no.iskra.ccalculator;

import no.iskra.ccalculator.Currency;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

class CurrencyCalculator {
  static Currency nok;
  static Currency sek;
  static Currency usd;
  static JSONObject rates;

  static Scanner input;

  static boolean exit;
  static String lastUpdated;
  static String fiatCurrency;

  public static void main(String[] args) {
    fiatCurrency = "NOK";
    input = new Scanner(System.in);
    exit = false;

    System.out.print("Fetching currency rates... ");
    rates = getCurrencyRates();
    System.out.println("OK!");

    System.out.println("Iskra Valutakalkulator\n..fordi hvorfor gjøre det enkelt?\n");
    System.out.println("Tast inn en verdi, og hvilken valuta du vil regne fra og til. Eks:");
    System.out.println("25 eur nok\nfor å se verdien av 25 euro i kroner.");

    while (!exit) {
      displayCommandPrompt();
    }

    System.out.println("Bye");
  }

  static void waitForInput() {
    System.out.println();
    System.out.println("Trykk enter for å gå tilbake...");
    try {
      System.in.read();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  static void displayCommandPrompt() {
    // System.out.println("Skriv 'list' for å se hvilke valutaer som kan brukes.");
    String[] command = getStringFromUser().toUpperCase().split(" ");
    if (command.length < 1) {
      System.out.println("Ingen kommando gjenkjent");
    } else if (command[0] == "list") {
      listCurrencies();
      return;
    } else if (command[0] == "exit" || command[0] == "q" || command[0] == "quit") {
      exit = true;
    } else if (command.length == 3) {
      try {
        double val = Double.parseDouble(command[0]);
        String fromCurrency = command[1];
        String toCurrency = command.length == 4 ? command[3] : command[2];
        double val2 = val*rates.getJSONObject("rates").getDouble(toCurrency)/
          rates.getJSONObject("rates").getDouble(fromCurrency);
        System.out.println(String.format("%.2f %s = %2f %s", val, fromCurrency, val2, toCurrency));
      } catch(JSONException e) {
        System.out.println("Kommando ikke gjenkjent eller ugyldig valuta");
      } catch (NumberFormatException e) {
        System.out.println("Kommandoen ble formulert feil. Prøv på nytt.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  static void listCurrencies() {
    System.out.println("Tilgjengelige valutakurser:");
    rates.getJSONObject("rates").keySet().forEach(System.out::println);
  }

  static void displayAllCurrencies() {
    System.out.println(String.format("Viser verdier i %s", rates.getString("base")));
    System.out.println(String.format("Sist oppdatert: %s", rates.getString("date")));
    System.out.println();
    System.out.println(String.format("SEK: %.2f %s", sek.getRate(), rates.getString("base")));
    System.out.println(String.format("USD: %.2f %s", usd.getRate(), rates.getString("base")));
  }

  static int getIntFromUser() {
    System.out.print("\n> ");
    int retVal = 99;
    try {
      retVal = input.nextInt();
    } catch(InputMismatchException e) {
      // Bruker kan ha gjort noe nasty. Tøm buffer og prøv på nytt.
      input.next();
    }
    return retVal;
  }

  static String getStringFromUser() {
    System.out.print("\n> ");
    String retVal = "";
    try {
      retVal = input.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("Prøvde du deg på noe stygt? Fikk ikke til å behandle teksten..");
      input.next();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }

  static JSONObject getCurrencyRates() {
    JSONObject obj;

    try {
      String s = "https://api.exchangeratesapi.io/latest?base="+fiatCurrency;
      //s = URLEncoder.encode(s, "UTF-8");
      URL url = new URL(s);
      Scanner scan = new Scanner(url.openStream());
      String str = new String();
      while (scan.hasNext()) {
        str += scan.nextLine();
      }
      scan.close();

      obj = new JSONObject(str);
      JSONObject currentRates = new JSONObject(obj.getJSONObject("rates"));

      sek.setRate(1/obj.getJSONObject("rates").getDouble("SEK"));
      usd.setRate(1/obj.getJSONObject("rates").getDouble("USD"));

      lastUpdated = obj.getString("date");
    } catch(Exception e) {
      e.printStackTrace();
      return new JSONObject();
    }

    return obj;
  }

  public static String getHTML(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
  }
}
