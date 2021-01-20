package com.skypremiuminternational.app.domain.models.phone_code;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/16/17.
 */

public class PhoneCode implements Serializable {

  @SerializedName("countryCodes")
  @Expose
  private List<PhoneCode_> phoneCodes = null;

  public List<PhoneCode_> getPhoneCodes() {
    return phoneCodes;
  }

  public void setPhoneCodes(List<PhoneCode_> countryCodes) {
    this.phoneCodes = phoneCodes;
  }

  public static class PhoneCode_ implements Serializable {

    @SerializedName("dialling_code")
    @Expose
    private String diallingCode;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    public String getDiallingCode() {
      return diallingCode;
    }

    public void setDiallingCode(String diallingCode) {
      this.diallingCode = diallingCode;
    }

    public String getCountryName() {
      return countryName;
    }

    public void setCountryName(String countryName) {
      this.countryName = countryName;
    }
  }
}
