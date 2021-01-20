package com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.hunry_go_where.CancelReservationResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;

public interface ConfirmReservationView<T extends Presentable> extends Viewable<T> {

  void renderCreateSuccess(ReservationResultResponse reservationResultResponse);

  void renderCreateFailed();

  void renderCancel(CancelReservationResponse response);

  void renderCancelError(String msg);

}
