package com.skypremiuminternational.app.app.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull; //javax.annotation.Nonnull;
import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by codigo on 2/4/18.
 */

public class DateFormatter {
  private static final String[] MONTHS = new String[]{
      "Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"
  };

  @Inject
  public DateFormatter() {

  }

  @NonNull
  public String format(String rawDate) {
    String formattedDate = "";
    if (rawDate == null) return formattedDate;
    try {
      Date date = DateParser.with(Constants.PATTERN_DATE_TIME).parse(rawDate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      formattedDate = calendar.get(Calendar.DAY_OF_MONTH) + " ";
      formattedDate += MONTHS[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
    } catch (ParseException e) {
      Timber.e(e);
    }
    return formattedDate;
  }
}
