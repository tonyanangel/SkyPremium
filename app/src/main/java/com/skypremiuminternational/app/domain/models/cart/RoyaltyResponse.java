package com.skypremiuminternational.app.domain.models.cart;

/**
 * Created by codigo on 24/2/18.
 */

public class RoyaltyResponse {

  public final Boolean success;
  public final String message;

  public RoyaltyResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
