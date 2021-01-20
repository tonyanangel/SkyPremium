package com.skypremiuminternational.app.app.features.checkout.stepone;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

/**
 * Created by aeindraaung on 2/4/18.
 */

public interface CheckoutDeliveryView<T extends Presentable> extends Viewable<T> {
  void render(UserDetailResponse response);

  void render(Throwable error);

  void render(List<ISOCountry> value,boolean success);

  void render(String error);

  void render(List<CreditCardResponse> response);

  void showLoading(String message);

  void notifyStatusChanged();

  void hideAddressDialog();

  void showAddAddressDialog(Address address);

  void showAddCreditDialog(CreditCardResponse creditCardResponse);

  void showAddCreditDialogFirstTime();

  void showUpdateFail(String message);

  void showWarning(String message);

  void proceedToNextStep();

  void renderCountryCode(List<CountryCode> countryCodes);

  void renderBillingAddress(List<BillingAddress> value);

  void showEditBillingAddressDialog(List<CountryCode> countryCodes, BillingAddress address);

  void showAddBillingAddressDialog(List<CountryCode> countryCodes);

  void requestBillingAddressSuccess();

  void requestBillingAddressError(String error);

  void initCountryCodeCc(List<CountryCodeCC> countryCodeCc);
}
