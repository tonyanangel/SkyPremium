package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class Exclusive {

  @SerializedName("billable_currency")
  private BillableCurrency billableCurrency;

  @SerializedName("request_currency")
  private RequestCurrency requestCurrency;

  public void setBillableCurrency(BillableCurrency billableCurrency) {
    this.billableCurrency = billableCurrency;
  }

  public BillableCurrency getBillableCurrency() {
    return billableCurrency;
  }

  public void setRequestCurrency(RequestCurrency requestCurrency) {
    this.requestCurrency = requestCurrency;
  }

  public RequestCurrency getRequestCurrency() {
    return requestCurrency;
  }
}