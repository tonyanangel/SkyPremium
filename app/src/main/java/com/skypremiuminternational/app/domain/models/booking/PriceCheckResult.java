package com.skypremiuminternational.app.domain.models.booking;

import java.util.Map;

public class PriceCheckResult {

  public final PriceCheckSummary summary;
  public final Map<String, PriceNTaxes> priceNTaxesMap;

  public PriceCheckResult(
      PriceCheckSummary summary,
      Map<String, PriceNTaxes> priceNTaxesMap) {
    this.summary = summary;
    this.priceNTaxesMap = priceNTaxesMap;
  }
}
