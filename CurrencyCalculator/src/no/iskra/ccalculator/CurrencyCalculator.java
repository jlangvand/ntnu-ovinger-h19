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
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

class CurrencyCalculator {
  static Currency nok;
  static Currency sek;
  static Currency usd;
  static JSONObject rates;

  static Scanner input;

  static boolean exit;
  static String lastUpdated;

  public static void main(String[] args) {
    nok = new Currency("Norske kroner", "NOK", 1);
    usd = new Currency("Amerikanske dollar", "USD", 7.5);
    sek = new Currency("Svenske kronor", "SEK", 0.93);

    System.out.println("Henter valutakurser...\n");

    rates = getCurrencyRates();

    input = new Scanner(System.in);

    exit = false;

    while (!exit) {
      rateMenu();
      if (!exit) waitForInput();
    }

    System.out.println();
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

  // Usexy metode for å demonstrere bruk av switch
  static void rateMenu() {
    printCurrencyMenu();
    double rate = 0;
    String symbol = "";
    boolean print = true;
    switch (getIntFromUser()) {
      case 1:
        rate = nok.getRate();
        symbol = nok.getSymbol();
        break;
      case 2:
        rate = sek.getRate();
        symbol = sek.getSymbol();
        break;
      case 3:
        rate = usd.getRate();
        symbol = usd.getSymbol();
        break;
      case 4:
        displayAllCurrencies();
        print = false;
        break;
      case 0:
        exit = true;
        print = false;
        break;
      default:
        break;
    }
    System.out.println();
    if (rate == 0 && print) {
      System.out.println("Bare bruk heltall!");
    } else if (print) {
      System.out.println(String.format("1.00%s = %.2fNOK", symbol, rate));
    }
  }

  static void displayAllCurrencies() {
    System.out.println(String.format("Viser verdier i %s", rates.getString("base")));
    System.out.println(String.format("Sist oppdatert: %s", rates.getString("date")));
    System.out.println();
    System.out.println(String.format("SEK: %.2f %s", sek.getRate(), rates.getString("base")));
    System.out.println(String.format("USD: %.2f %s", usd.getRate(), rates.getString("base")));
  }

  static void printCurrencyMenu() {
    System.out.println("Velg valuta\n");
    System.out.println("1 - NOK");
    System.out.println("2 - SEK");
    System.out.println("3 - USD");
    System.out.println("4 - Vis alle");
    System.out.println("0 - Avslutt");
  }

  static int getIntFromUser() {
    System.out.print("\n> ");
    int retVal = 99;
    try {
      retVal = input.nextInt();
    } catch(InputMismatchException e) {

    }
    return retVal;
  }

  static JSONObject getCurrencyRates() {
    JSONObject obj;

    try {
      String s = "https://api.exchangeratesapi.io/latest?symbols=USD,SEK&base=NOK";
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


  public static String executePost(String targetURL, String urlParameters) {
    HttpURLConnection connection = null;

    try {
      //Create connection
      URL url = new URL(targetURL);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type",
          "application/x-www-form-urlencoded");

      connection.setRequestProperty("Content-Length",
          Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");

      connection.setUseCaches(false);
      connection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream (
          connection.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.close();

      //Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      return response.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
