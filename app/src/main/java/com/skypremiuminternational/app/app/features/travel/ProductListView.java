package com.skypremiuminternational.app.app.features.travel;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface ProductListView<T extends Presentable> extends Viewable<T> {

  void render(TravelViewState viewState);

  void notifyFavStatusChanged(boolean featuredItem);

  void updateCartCount(String count);

  void render(List<Property> properties);

  void render(Throwable error);

  void render(CategoryDetailsResponse response);

  void render(@NonNull String startDate, @NonNull String endDate);

  void clearDateRange();

  void showOccupancyDialog(int roomCount, int adultCount, List<Child> children);

  void render(int roomCount, int adultCount, List<Child> children);

  void render(String areaOrHotelName);

  void goToPropertyDetail(Property property, int roomCount, int adultCount,
                          ArrayList<Child> children, List<CalendarDay> calendarSelectedDays);

  void renderPropertyError(Throwable error);

  void showBottomEanLayout(List<CalendarDay> selectedDays, int roomCount, int adultCount,
                           ArrayList<Child> children);

  void renderGetDetailToGoEstore(DetailsItem response);

  void showErrorMsg(int message);

  void renderOpenReservation(List<OutletItem> outletItems , int action, ItemsItem itemsItem);

  void renderGetOutletFailed(ItemsItem item , String linkHGW);
}
