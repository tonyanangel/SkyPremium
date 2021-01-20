package com.skypremiuminternational.app.app.features.profile;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface ProfileView<T extends Presentable> extends Viewable<T> {

  void render(ProfileViewState viewState);

  void updateCartCount(String count);

  //20200403 WIKI Toan Tran - update  image url
  void goToInviteFriend(String code, String salutation, String firstname, String referralCode,
                        String description,String imgBannerUrl);

  void render(Throwable error);

  void renderFirstTimePopup(FirstTimePopup value);

  void renderFirstTimePopupFailed(Throwable throwable);
}
