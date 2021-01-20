package com.skypremiuminternational.app.app.features.checkout;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by aeindraaung on 1/29/18.
 */

public interface CheckoutView<T extends Presentable> extends Viewable<T> {

  void showLoading(String message);

  void render(Throwable error);
}
