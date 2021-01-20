package com.skypremiuminternational.app.app.features.profile.order.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import java.util.List;

/**
 * Created by wmw on 2/16/2018.
 */

public interface OrderDetailsView<T extends Presentable> extends Viewable<T> {

  void showLoading(String s);

  void hideLoading();

  void render(Throwable error);

  void render(OrderDetailResponse orderDetail);

  void render(List<ISOCountry> countryCodeList);
}
