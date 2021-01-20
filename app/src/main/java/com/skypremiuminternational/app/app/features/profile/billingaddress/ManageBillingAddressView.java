package com.skypremiuminternational.app.app.features.profile.billingaddress;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.List;

public interface ManageBillingAddressView<T extends Presentable> extends Viewable<T> {

  void render(Throwable error);

  void render(List<BillingAddress> billingAddresses);

  void setCountryCodes(List<CountryCode> countryCodes);

  void showAddAddressDialog(List<CountryCode> countryCodes);

  void showEditAddressDialog(List<CountryCode> countryCodes, BillingAddress address);
}
