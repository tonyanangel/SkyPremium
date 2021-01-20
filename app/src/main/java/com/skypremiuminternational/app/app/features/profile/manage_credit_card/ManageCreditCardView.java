package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface ManageCreditCardView<T extends Presentable> extends Viewable<T> {

  void render(List<CreditCardResponse> response);

  void showLoading(String s);

  void render(Throwable error);

  void showWarning(String message);

  void showAddCreditDialog(CreditCardResponse creditCard);

  void showAddCreditDialogV2(CreditCardResponse creditCard);

  void showAddCreditDialogFirstTime();

  void hideAddressDialog();

  void notifyStatusChanged();

  void initCountryCode(List<CountryCodeCC> countryCodeCCS);
}
