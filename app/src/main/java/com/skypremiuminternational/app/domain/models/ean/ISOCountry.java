package com.skypremiuminternational.app.domain.models.ean;

public class ISOCountry {
  public final String country_code;
  public final String country_name;
  public final String dialling_code;

  public ISOCountry(String country_code, String country_name, String dialling_code) {
    this.country_code = country_code;
    this.country_name = country_name;
    this.dialling_code = dialling_code;
  }
}
