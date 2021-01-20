package com.skypremiuminternational.app.app.features.checkout.stepthree;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import java.util.List;

/**
 * Created by aeindraaung on 2/4/18.
 */

public interface CheckoutOrderPlacedView<T extends Presentable> extends Viewable<T> {

  void render(OrderDetailResponse orderDetail);

  void render(Throwable e);

  void render(List<ISOCountry> isoCountries);
}
