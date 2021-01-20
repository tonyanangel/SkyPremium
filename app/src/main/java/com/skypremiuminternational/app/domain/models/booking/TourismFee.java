package com.skypremiuminternational.app.domain.models.booking;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by codigo on 22/6/18.
 */

@AutoValue
public abstract class TourismFee implements Parcelable {

  public abstract String feesType();

  public abstract String price();

  public abstract String currency();

  public static TourismFee create(String feesType, String price, String currency) {
    return builder()
        .feesType(feesType)
        .price(price)
        .currency(currency)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_TourismFee.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder feesType(String feesType);

    public abstract Builder price(String price);

    public abstract Builder currency(String currency);

    public abstract TourismFee build();
  }
}
