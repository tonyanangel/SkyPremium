package com.skypremiuminternational.app.data.model.ean;

import com.skypremiuminternational.app.domain.models.ean.ISOCountry;

import java.util.List;

public class ISOCountryResponse {

  public final List<ISOCountry> countryCodes;

  public ISOCountryResponse(
      List<ISOCountry> countryCodes) {
    this.countryCodes = countryCodes;
  }
}
