package com.skypremiuminternational.app.app.features.cart;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;

/**
 * Created by aeindraaung on 1/26/18.
 */

public interface ShoppingCartView<T extends Presentable> extends Viewable<T> {
  void showLoading(String message);

  void render(CartDetailResponse response);

  void render(Throwable error);

  void render(CartAllInformationResponse value, boolean containVirtualProduct);

  void render(String royaltyPoints, String grandTotal);

  void proceedToCheckout(int checkoutType, String paymentType);

  void renderErrorMessage(String errorMessage);

  void renderMessage(String message);

  void notifyCartItemStatusChanged();
}
