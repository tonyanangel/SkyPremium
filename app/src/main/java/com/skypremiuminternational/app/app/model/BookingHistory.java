package com.skypremiuminternational.app.app.model;

import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;

import java.util.List;

@AutoValue
public abstract class BookingHistory implements Parcelable {

  public abstract String itineraryId();

  public abstract String id();

  public abstract String bookingDate();

  public abstract String hotelName();

  public abstract String city();

  public abstract String bookingPeriod();

  public abstract int nightCount();

  public abstract String totalCost();

  public abstract String status();

  public abstract List<Room> rooms();

  public abstract String skyDollar();

  public abstract List<BookingData> bookingDataList();



  public static BookingHistory create(String itineraryId, String id, String bookingDate, String hotelName,
                                      String city, String bookingPeriod, int nightCount, String totalCost,
                                      String status, List<Room> rooms, String skyDollar,List<BookingData> bookingDataList) {
    return builder()
        .itineraryId(itineraryId)
        .id(id)
        .bookingDate(bookingDate)
        .hotelName(hotelName)
        .city(city)
        .bookingPeriod(bookingPeriod)
        .nightCount(nightCount)
        .totalCost(totalCost)
        .status(status)
        .rooms(rooms)
        .skyDollar(skyDollar)
        .bookingDataList(bookingDataList)
        .build();
  }


  public static Builder builder() {
    return new AutoValue_BookingHistory.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder id(String id);

    public abstract Builder bookingDate(String bookingDate);

    public abstract Builder hotelName(String hotelName);

    public abstract Builder city(String city);

    public abstract Builder bookingPeriod(String bookingPeriod);

    public abstract Builder nightCount(int nightCount);

    public abstract Builder totalCost(String totalCost);

    public abstract Builder status(String status);

    public abstract Builder itineraryId(String itineraryId);

    public abstract Builder rooms(List<Room> rooms);

    public abstract Builder skyDollar(String skyDollar);

    public abstract Builder bookingDataList(List<BookingData> bookingDataList);


    public abstract BookingHistory build();
  }

  @AutoValue
  public static abstract class Room implements Parcelable {
    @Nullable
    public abstract List<Integer> childAges();

    public abstract String email();

    public abstract String familyName();

    public abstract String givenName();

    public abstract int numberOfAdults();

    public abstract String phone();

    public abstract boolean smoking();

    @Nullable
    public abstract String specialRequest();

    public abstract String title();

    public static Room create(List<Integer> childAges, String email, String familyName, String givenName,
                              int numberOfAdults, String phone, boolean smoking, String specialRequest,
                              String title) {
      return builder()
          .childAges(childAges)
          .email(email)
          .familyName(familyName)
          .givenName(givenName)
          .numberOfAdults(numberOfAdults)
          .phone(phone)
          .smoking(smoking)
          .specialRequest(specialRequest)
          .title(title)
          .build();
    }

    public static Builder builder() {
      return new AutoValue_BookingHistory_Room.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder childAges(List<Integer> childAges);

      public abstract Builder email(String email);

      public abstract Builder familyName(String familyName);

      public abstract Builder givenName(String givenName);

      public abstract Builder numberOfAdults(int numberOfAdults);

      public abstract Builder phone(String phone);

      public abstract Builder smoking(boolean smoking);

      public abstract Builder specialRequest(String specialRequest);

      public abstract Builder title(String title);

      public abstract Room build();
    }
  }
}
