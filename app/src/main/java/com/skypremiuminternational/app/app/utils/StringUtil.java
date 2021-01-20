package com.skypremiuminternational.app.app.utils;

/**
 * Created by codigo on 15/1/18.
 */

public class StringUtil {

  private StringUtil() {
  }

  public static String toTitleCase(String[] sources) {
    if (sources == null) return "";
    StringBuilder sourceBuilder = new StringBuilder();
    for (String source : sources) {
      sourceBuilder.append(source.substring(0, 1).toUpperCase())
          .append(source.substring(1, source.length()).toLowerCase())
          .append(" ");
    }
    return sourceBuilder.toString();
  }
}
