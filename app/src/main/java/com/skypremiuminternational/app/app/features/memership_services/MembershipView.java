package com.skypremiuminternational.app.app.features.memership_services;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface MembershipView<T extends Presentable> extends Viewable<T> {

  void render(MembershipViewState viewState);

  void render(String cartCount);
}
