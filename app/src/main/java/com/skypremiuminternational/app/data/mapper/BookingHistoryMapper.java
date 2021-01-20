package com.skypremiuminternational.app.data.mapper;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewPresenter;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.LocationStream;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.booking.history.AdditionalData;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingHistoryResponse;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.data.model.ean.booking.history.RoomsItem;
import com.skypremiuminternational.app.domain.models.ean.Amenity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookingHistoryMapper {

  private Gson gson;

  @Inject
  public BookingHistoryMapper() {
    gson = new Gson();
  }

  public BookingData mapBookingData(Object jsonBookingData) {
    BookingData bookingData = null;

    try {
      JSONArray jsonArray = new JSONArray(gson.toJson(jsonBookingData));

      //Log.d("jsonData1",jsonBookingData.toString());
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONArray bookingArray = jsonArray.optJSONArray(i);
        if (bookingArray != null && bookingArray.getJSONObject(0) != null) {
          bookingData = gson.fromJson(bookingArray.getJSONObject(0).toString(), BookingData.class);
          if (bookingData != null) {
            break;
          }
        } else {
          JSONObject bookingObject = jsonArray.optJSONObject(i);
          if (bookingObject != null) {
            bookingData = gson.fromJson(bookingObject.toString(), BookingData.class);
            if (bookingData != null) {
              break;
            }
          }
        }
      }
      return bookingData;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return bookingData;
  }

  public List<BookingData> mapBookingDatas(Object jsonBookingData) {
    List<BookingData> bookingDatas = new ArrayList<>();
    //Log.d("jsonData2",bookingDatas.toString());
    try {
      JSONArray jsonArray = new JSONArray(gson.toJson(jsonBookingData));


      BookingData bookingData = null;
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONArray bookingArray = jsonArray.optJSONArray(i);
        if (bookingArray != null && bookingArray.getJSONObject(0) != null) {
          bookingData = gson.fromJson(bookingArray.getJSONObject(0).toString(), BookingData.class);
        } else {
          JSONObject bookingObject = jsonArray.optJSONObject(i);
          if (bookingObject != null) {
            bookingData = gson.fromJson(bookingObject.toString(), BookingData.class);
          }
        }
        if (bookingData != null) {

          bookingDatas.add(bookingData);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return bookingDatas;
  }

  public List<BookingHistory> map(BookingHistoryResponse response) {


    List<BookingHistory> bookingHistories = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    for (Booking booking : response.getItems()) {

      String bookingPeriod = "";
      String totalCost = "";
      String city = "Singapore";
      String itineraryId = "";
      String skyDollar = "";
      int nightCount = 1;
      if (Validator.isTextValid(booking.getTotalCost())) {
        totalCost = booking.getTotalCost();
      }
      try {
        Calendar checkInDate = Calendar.getInstance();
        Calendar checkOutDate = Calendar.getInstance();
        checkInDate.setTime(simpleDateFormat.parse(booking.getCheckIn()));
        checkOutDate.setTime(simpleDateFormat.parse(booking.getCheckOut()));
        bookingPeriod = formatDateRange(checkInDate, checkOutDate);
        nightCount = (int) ((checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis()) / (1000
            * 60
            * 60
            * 24));
      } catch (Exception e) {
        e.printStackTrace();
      }
      List<BookingData> bookingDataList = mapBookingDatas(booking.getBookingData());
      List<BookingHistory.Room> rooms = new ArrayList<>();
      if (bookingDataList != null && bookingDataList.size() > 0) {
        itineraryId = bookingDataList.get(0).getItineraryId();
        if (bookingDataList.get(0).getAdditionalData() != null
            && bookingDataList.get(0).getAdditionalData().rooms != null) {
          rooms = buildRooms(bookingDataList.get(0).getAdditionalData().rooms);
        }
      }

      skyDollar = booking.getSkyDollar();
      bookingHistories.add(BookingHistory.builder()
          .id(booking.getBookingId())
          .bookingDate(booking.getBookingDate())
          .hotelName(booking.getHotelName() == null ? "" : booking.getHotelName())
          .city(city)
          .bookingPeriod(bookingPeriod)
          .nightCount(nightCount)
          .totalCost(totalCost)
          .status(booking.getBookingStatus())
          .itineraryId(itineraryId)
          .rooms(rooms)
          .skyDollar(skyDollar)
          .bookingDataList(bookingDataList)
          .build());
    }
    return bookingHistories;
  }

  private List<BookingHistory.Room> buildRooms(List<AdditionalData.Room> rooms) {
    List<BookingHistory.Room> roomList = new ArrayList<>();
    for (AdditionalData.Room room : rooms) {
      roomList.add(BookingHistory.Room.builder()
          .childAges(room.child_ages)
          .email(room.email)
          .familyName(room.family_name)
          .givenName(room.given_name)
          .numberOfAdults(room.number_of_adults)
          .phone(room.phone)
          .smoking(room.smoking)
          .specialRequest(room.special_request)
          .title(room.title)
          .build());
    }
    return roomList;
  }

  private String formatDateRange(Calendar checkInDate, Calendar checkOutDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_RANGE_FORMAT,
        Locale.getDefault());
    SimpleDateFormat dayFormat = new SimpleDateFormat(Constants.DAY_RANGE_FORMAT,
        Locale.getDefault());
    final String formattedCheckIn = dateFormat.format(checkInDate.getTime());
    final String formattedCheckOut = dateFormat.format(checkOutDate.getTime());

    if (checkInDate.get(Calendar.YEAR) != checkOutDate.get(Calendar.YEAR)) {
      return formattedCheckIn + " - " + formattedCheckOut;
    } else if (checkInDate.get(Calendar.MONTH) != checkOutDate.get(Calendar.MONTH)) {
      return dayFormat.format(checkInDate.get(Calendar.DAY_OF_MONTH))
          + " "
          + formattedCheckIn.split(" ")[1]
          + " - "
          + formattedCheckOut;
    } else {
      if(String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)).length() > 1){
        return String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)) + " - " + formattedCheckOut;
      }else {
        return "0"+String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)) + " - " + formattedCheckOut;
      }

    }
  }
}
