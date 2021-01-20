package com.skypremiuminternational.app.app.features.estore;

import android.util.Log;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by codigo on 4/2/18.
 */

public final class DateTimeCountDown {

  private final Date toTime;
  private final Date fromTime;
  private final Date currentServerTime;
  long diff ;
  private DateTimeCountDown(String pattern, String fromTime, String toTime,
                            String currentServerTime) {
    try {
      this.toTime = DateParser.with(Constants.PATTERN_DATE_TIME).parse(toTime);
      this.fromTime = DateParser.with(Constants.PATTERN_DATE_TIME).parse(fromTime);
      this.currentServerTime =
          DateParser.with(pattern).parse(currentServerTime);

      diff = this.toTime.getTime() - this.currentServerTime.getTime();
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static DateTimeCountDown init(String pattern, String fromTime, String toTime,
                                       String currentServerTime) {
    return new DateTimeCountDown(pattern, fromTime, toTime, currentServerTime);
  }

  public Subscription subscribe(Subscriber<CountDown> subscriber) {
    return Observable.interval(1, TimeUnit.SECONDS)
        .flatMap(interval -> {
          Calendar now  = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
          diff -=1000;
          final boolean alreadyPast = diff < 0;
          final boolean hasNotReached = currentServerTime.before(fromTime);





          long seconds = Math.abs(diff) / 1000;

          int day = (int) TimeUnit.SECONDS.toDays(seconds);

          int hr = (int) TimeUnit.SECONDS.toHours(seconds) - day * 24;
          String txtHr = hr > 9 ? String.valueOf(hr) : "0" + hr;
          int min =
              (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)
                  * 60));
          String txtMin = min > 9 ? String.valueOf(min) : "0" + min;
          int sec =
              (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds)
                  * 60));

          String txtSec = sec > 9 ? String.valueOf(sec) : "0" + sec;

          return Observable.just(CountDown.builder()
              .alreadyPast(alreadyPast)
              .hasNotReached(hasNotReached)
              .day(day)
              .hr(txtHr)
              .min(txtMin)
              .sec(txtSec)
              .build());
        })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  @AutoValue
  public abstract static class CountDown {
    public static CountDown create(boolean alreadyPast, boolean hasNotReached, int day, String hr,
                                   String min, String sec) {
      return builder().alreadyPast(alreadyPast)
          .hasNotReached(hasNotReached)
          .day(day)
          .hr(hr)
          .min(min)
          .sec(sec)
          .build();
    }

    public static Builder builder() {
      return new AutoValue_DateTimeCountDown_CountDown.Builder();
    }

    public abstract boolean alreadyPast();

    public abstract boolean hasNotReached();

    public abstract int day();

    public abstract String hr();

    public abstract String min();

    public abstract String sec();

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder alreadyPast(boolean alreadyPast);

      public abstract Builder day(int day);

      public abstract Builder hr(String hr);

      public abstract Builder min(String min);

      public abstract Builder sec(String sec);

      public abstract Builder hasNotReached(boolean hasNotReached);

      public abstract CountDown build();
    }
  }

  public abstract static class CountDownSubscriber extends rx.Subscriber<CountDown> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
      e.printStackTrace();
    }
  }
}
