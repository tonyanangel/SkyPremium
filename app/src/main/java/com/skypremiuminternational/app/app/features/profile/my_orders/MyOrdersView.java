package com.skypremiuminternational.app.app.features.profile.my_orders;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface MyOrdersView<T extends Presentable> extends Viewable<T> {

  void showLoading(String message);

  void render(MyOrderResponse value);

  void render(Throwable error);
}
