package com.skypremiuminternational.app.app.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by codigo on 4/2/18.
 */

public final class DateParser {





  private final SimpleDateFormat simpleDateFormat;

  private DateParser(String pattern) {
    simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public static DateParser with(String pattern) {
    if (TextUtils.isEmpty(pattern)) {
      throw new IllegalArgumentException("Pattern is not defined");
    }
    return new DateParser(pattern);
  }

  public final Date parse(String date) throws ParseException {
    return simpleDateFormat.parse(date);
  }

  public static String changeFormatDate(String patternFrom,String patternTo ,String strDate) throws ParseException {
    String s  = "";
    Date date =  new SimpleDateFormat(patternFrom).parse(strDate);
    SimpleDateFormat sdf  = new SimpleDateFormat(patternTo);
    s =  sdf.format(date);

    return s;
  }
}
