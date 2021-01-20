package com.skypremiuminternational.app.app.features.landing;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface LandingView<T extends Presentable> extends Viewable<T> {

  void render(LandingViewState viewState);

  void goToShoppingCart();

  void render(String areaOrHotel, String checkInDate, String checkOutDate, int roomCount,
              int adultCount, List<Child> children);

  void showSearchHotelDialog(String areaOrHotel, List<CalendarDay> selectedDays, int roomCount,
                             int adultCount, List<Child> children);

  void renderFirstTimePopup(FirstTimePopup firstTimePopup);

  void renderFirstTimePopupFailed(Throwable throwable);

  void showDialogLoading();

  void hideDialogLoading();

  void renderNumberUnread(int number);

  void sendFirstTimeMessage(boolean isFlogin);
}
