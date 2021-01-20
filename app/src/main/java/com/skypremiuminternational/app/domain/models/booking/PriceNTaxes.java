package com.skypremiuminternational.app.domain.models.booking;

public class PriceNTaxes {
  public final double roomPrice;
  public final double taxes;

  public PriceNTaxes(double roomPrice, double taxes) {
    this.roomPrice = roomPrice;
    this.taxes = taxes;
  }
}
