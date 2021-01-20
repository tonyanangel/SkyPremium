package com.skypremiuminternational.app.app.features.checkout.room.steptwo;

import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.domain.util.CreditNumberValidator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CheckoutPaymentReviewPresenterTest {

  private CheckoutPaymentReviewPresenter presenter;
  private CheckoutPaymentReviewView view;

  @Before
  public void setUp() throws Exception {

    view = mock(CheckoutPaymentReviewView.class);
    presenter = new CheckoutPaymentReviewPresenter(mock(CreditNumberValidator.class));
    presenter.attachView(view);
  }

  @Test
  public void renderAllError_EmptyDetail() {
    presenter.validate(getEmptyDetail());
  }

  private PaymentDetail getEmptyDetail() {
    return new PaymentDetail();
  }
}