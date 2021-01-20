package com.skypremiuminternational.app.app.features.travel.booking;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 10/6/17.
 */

public interface HotelBookingView<T extends Presentable> extends Viewable<T> {

  void render(HotelBookingViewState viewState);
}
