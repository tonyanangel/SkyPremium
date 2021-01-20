package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class MarketingFee {

  @SerializedName("billable_currency")
  private BillableCurrency billableCurrency;

  @SerializedName("request_currency")
  private RequestCurrency requestCurrency;

  public BillableCurrency getBillableCurrency() {
    return billableCurrency;
  }

  public void setBillableCurrency(BillableCurrency billableCurrency) {
    this.billableCurrency = billableCurrency;
  }

  public RequestCurrency getRequestCurrency() {
    return requestCurrency;
  }

  public void setRequestCurrency(RequestCurrency requestCurrency) {
    this.requestCurrency = requestCurrency;
  }
}
