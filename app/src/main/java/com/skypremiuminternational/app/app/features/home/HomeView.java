package com.skypremiuminternational.app.app.features.home;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface HomeView<T extends Presentable> extends Viewable<T> {

  void render(HomeViewState viewState);

  void updateCartCount(String count);

  void goToShoppingCart();

  void render(Double pricevalue);

  void render(Throwable error);


  void showDialogLoading();

  void hideDialogLoading();

  void renderNumberUnread(int number);

  void getNumberUnread();

  void renderNumberUnreadLocal();
}
