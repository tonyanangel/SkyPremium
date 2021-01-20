package com.skypremiuminternational.app.domain.models.booking;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.RoomsItem;
import com.skypremiuminternational.app.domain.models.ean.Amenity;

import java.util.List;

@AutoValue
public abstract class BookingDetail {

  public abstract String itineraryId();

  public abstract String totalGuest();

  public abstract double subTotal();

  public abstract String tourismFeeCurrency();

  public abstract double tourismFee();

  public abstract List<TourismFee> fees();

  public abstract List<RoomsItem> roomsItems();

  public abstract double grandTotal();

  public abstract boolean success();

  @Nullable
  public abstract List<String> errorMessages();

  public abstract String creditNumber();

  public abstract String creditType();

  public abstract String paymentStatus();

  public abstract String bookingStatus();

  public abstract String hotelName();

  public abstract String city();

  public abstract String roomType();

  public abstract String bedType();

  public abstract String roomImage();

  public abstract String bookingPeriod();

  public abstract int nightCount();

  public abstract int adultCount();

  public abstract int childCount();

  public abstract List<GuestDetail> guestDetails();

  public abstract List<Amenity> amenities();

  public abstract String skyDollar();

  public static BookingDetail create(String itineraryId, String totalGuest, double subTotal,
                                     String tourismFeeCurrency, double tourismFee, List<TourismFee> fees,
                                     double grandTotal, boolean success, List<String> errorMessages,
                                     String creditNumber, String creditType, String paymentStatus,
                                     String bookingStatus, String hotelName, String city, String roomType,
                                     String bedType, String roomImage, String bookingPeriod, int nightCount,
                                     int adultCount, int childCount, List<GuestDetail> guestDetails,
                                     List<Amenity> amenities, String skyDollar,List<RoomsItem> roomsItems) {
    return builder()
        .itineraryId(itineraryId)
        .totalGuest(totalGuest)
        .subTotal(subTotal)
        .tourismFeeCurrency(tourismFeeCurrency)
        .tourismFee(tourismFee)
        .fees(fees)
        .grandTotal(grandTotal)
        .success(success)
        .errorMessages(errorMessages)
        .creditNumber(creditNumber)
        .creditType(creditType)
        .paymentStatus(paymentStatus)
        .bookingStatus(bookingStatus)
        .hotelName(hotelName)
        .city(city)
        .roomType(roomType)
        .bedType(bedType)
        .roomImage(roomImage)
        .bookingPeriod(bookingPeriod)
        .nightCount(nightCount)
        .adultCount(adultCount)
        .childCount(childCount)
        .guestDetails(guestDetails)
        .amenities(amenities)
        .skyDollar(skyDollar)
        .roomsItems(roomsItems)
        .build();
  }


  public static Builder builder() {
    return new AutoValue_BookingDetail.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder itineraryId(String itineraryId);

    public abstract Builder totalGuest(String totalGuest);

    public abstract Builder subTotal(double subTotal);

    public abstract Builder tourismFeeCurrency(String tourismFeeCurrency);

    public abstract Builder tourismFee(double tourismFee);

    public abstract Builder fees(List<TourismFee> fees);

    public abstract Builder grandTotal(double grandTotal);

    public abstract Builder success(boolean success);

    public abstract Builder errorMessages(List<String> errorMessages);

    public abstract Builder creditNumber(String creditNumber);

    public abstract Builder creditType(String creditType);

    public abstract Builder paymentStatus(String paymentStatus);

    public abstract Builder bookingStatus(String bookingStatus);

    public abstract Builder hotelName(String hotelName);

    public abstract Builder city(String city);

    public abstract Builder roomType(String roomType);

    public abstract Builder bedType(String bedType);

    public abstract Builder roomImage(String roomImage);

    public abstract Builder bookingPeriod(String bookingPeriod);

    public abstract Builder nightCount(int nightCount);

    public abstract Builder adultCount(int adultCount);

    public abstract Builder childCount(int childCount);

    public abstract Builder guestDetails(List<GuestDetail> guestDetails);

    public abstract Builder amenities(List<Amenity> amenities);

    public abstract Builder skyDollar(String skyDollar);

    public abstract Builder roomsItems(List<RoomsItem> roomsItems);

    public abstract BookingDetail build();
  }
}
