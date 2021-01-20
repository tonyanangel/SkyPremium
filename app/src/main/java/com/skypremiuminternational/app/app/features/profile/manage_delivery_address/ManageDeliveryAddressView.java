package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

/**
 * Created by aeindraaung on 2/19/18.
 */

public interface ManageDeliveryAddressView<T extends Presentable> extends Viewable<T> {
  void showLoading(String message);

  void render(UserDetailResponse response);

  void render(Throwable error);

  void render(String error);

  void notifyStatusChanged();

  void hideAddressDialog();

  void showAddAddressDialog(Address address);

  void render(List<CountryCode> value,List<ISOCountry> value1);


}
