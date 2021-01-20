package com.skypremiuminternational.app.app.features.checkout.room.stepone;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.model.BookerInfo;

import java.util.List;

public interface CheckoutGuestDetailView<T extends Presentable> extends Viewable<T> {
  void render(List<BookerInfo> bookerInfos);

  void renderEmailValidationError(boolean withDialog);

  void renderBookerValidationError();

  void goToStepTwo(String emailAddress, String bedGroup);

  void renderBedGroupValidationError();
}
