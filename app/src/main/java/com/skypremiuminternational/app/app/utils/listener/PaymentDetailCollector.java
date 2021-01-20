package com.skypremiuminternational.app.app.utils.listener;

import com.skypremiuminternational.app.app.model.PaymentDetail;

public interface PaymentDetailCollector {
  PaymentDetail collect();

  void scrollToTop();
}
