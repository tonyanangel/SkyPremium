package com.skypremiuminternational.app.app.features.checkout.steptwo;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.utils.CountryUtil;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import java.util.List;

/**
 * Created by aeindraaung on 2/4/18.
 */

public interface CheckoutReviewView<T extends Presentable> extends Viewable<T> {
  void showLoading(String message);

  void render(Throwable error);

  void render(List<CartDetailItem> cartDetails);

  void render(CartAllInformationResponse cartAllInformationResponse);

  void proceedToNextStep(Integer orderId);

  void notifyCartItemStatusChanged();

  void renderErrorMessage(String errorMessage);

  void render(Address address,BillingAddress billingAddress);

  void render(CreditCardResponse defaultCreditCard);

  void hideProcessingOrder();

  void showProcessingOrder();

  void showProcessingOrderError();

  void renderCountryCode(List<CountryCode> countryList);

}
