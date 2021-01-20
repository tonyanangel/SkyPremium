package com.skypremiuminternational.app.app.utils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by codigo on 12/3/18.
 */

public class DecimalUtil {

  private DecimalUtil() {
  }

  public static String roundTwoDecimals(double amount) {
    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);
    formatter.format("%(,.2f", amount);
    return sb.toString();
  }

  public static String roundTowDecimalWithoutBrace(double amount) {
    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);
    formatter.format("%,.2f", amount);
    return sb.toString();
  }
}
