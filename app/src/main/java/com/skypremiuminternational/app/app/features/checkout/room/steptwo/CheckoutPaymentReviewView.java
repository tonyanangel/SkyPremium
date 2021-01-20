package com.skypremiuminternational.app.app.features.checkout.room.steptwo;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.model.PaymentDetail;

public interface CheckoutPaymentReviewView<T extends Presentable> extends Viewable<T> {

  void renderError(CheckoutPaymentReviewPresenter.ValidationResult validationResult,
                   boolean showDialog);

  void startBooking(PaymentDetail paymentDetail);

  void showMandatoryNotFilledError();
}
