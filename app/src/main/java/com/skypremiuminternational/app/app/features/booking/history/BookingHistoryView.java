package com.skypremiuminternational.app.app.features.booking.history;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;

import java.util.ArrayList;
import java.util.List;

public interface BookingHistoryView<T extends Presentable> extends Viewable<T> {

  void render(Throwable error);

  void render(boolean isSuccess,String status);

  void render(List<BookingHistory> bookingHistories);

  void render(BookingData bookingHistories, boolean success, String bookingid, int pos);

  void render(BookingData bookingHistories, boolean success, String bookingid,int pos, ArrayList<BookingHistory.Room> rooms);
}
