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
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

class Main {
  static Currency nok;
  static Currency sek;
  static Currency usd;

  static Scanner input;

  public static void main(String[] args) {
    nok = new Currency("Norske kroner", "NOK", 1);
    usd = new Currency("Amerikanske dollar", "USD", 7.5);
    sek = new Currency("Svenske kronor", "SEK", 0.93);

    input = new Scanner(System.in);

    //rateMenu();
    getCurrencyRates();
    System.out.println(String.format("SEK: %.2f\nUSD: %.2f", sek.getRate(), usd.getRate()));

    System.out.println();
  }

  // Usexy metode for å demonstrere bruk av switch
  static void rateMenu() {
    printCurrencyMenu();
    double rate = 0;
    String symbol = "";
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
      default:
        break;
    }
    System.out.println();
    if (rate == 0) {
      System.out.println("En rar feil har oppstått..");
    } else {
      System.out.println(String.format("1.00%s = %.2fNOK", symbol, rate));
    }
  }

  static void printCurrencyMenu() {
    System.out.println("Velg valuta\n");
    System.out.println("1 - NOK");
    System.out.println("2 - SEK");
    System.out.println("3 - USD");
  }

  static int getIntFromUser() {
    System.out.print("\n> ");
    return input.nextInt();
  }

  static boolean getCurrencyRates() {
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

      sek.setRate(obj.getJSONObject("rates").getDouble("SEK"));
      usd.setRate(obj.getJSONObject("rates").getDouble("USD"));
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
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
