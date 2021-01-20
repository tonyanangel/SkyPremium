package com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;

public interface MyReservationsView<T extends Presentable> extends Viewable<T> {

  void renderHistoryReservation(ReserveHistoryRespone reserveHistoryRespone);

  void renderError(String msg);

  void renderGotoBookingDetail(ReserveHistoryItem reserveHistoryItem);

}
