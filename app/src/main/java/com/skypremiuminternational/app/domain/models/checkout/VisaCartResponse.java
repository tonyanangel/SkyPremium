package com.skypremiuminternational.app.domain.models.checkout;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class VisaCartResponse {
  private String visaNumber;
  private String date;
  private boolean isChecked;

  public VisaCartResponse(String visaNumber, String date, boolean isChecked) {
    this.visaNumber = visaNumber;
    this.date = date;
    this.isChecked = isChecked;
  }

  public String getVisaNumber() {
    return visaNumber;
  }

  public void setVisaNumber(String visaNumber) {
    this.visaNumber = visaNumber;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }
}
