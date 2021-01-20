package com.skypremiuminternational.app.app.features.profile.edit_profile;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface EditProfileView<T extends Presentable> extends Viewable<T> {

  void render(EditProfileViewState viewState);

  void showCheckoutNotice(Double renewalPrice);

  void goToShoppingCart();

  //void render(String country,String id);
}
