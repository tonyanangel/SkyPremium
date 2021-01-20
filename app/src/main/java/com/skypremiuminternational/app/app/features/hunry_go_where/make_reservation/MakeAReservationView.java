package com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;

public interface MakeAReservationView <T extends Presentable> extends Viewable<T> {


  void renderReservationTime(ReservationTimeResponse reservationTimeResponse);


  void renderRestaurantMsg(String msg);

  void renderReservationTimeFailed();
}
