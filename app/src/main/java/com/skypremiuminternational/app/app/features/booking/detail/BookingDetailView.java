package com.skypremiuminternational.app.app.features.booking.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;

import java.util.List;

public interface BookingDetailView<T extends Presentable> extends Viewable<T> {
  void render(Throwable error);


  void render(BookingDetail value);

  void render(BookingData bookingData);

  void render(List<BookingHistory> value,boolean success);


}
