package com.skypremiuminternational.app.domain.models.booking;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Map;

@AutoValue
public abstract class PriceCheckSummary implements Parcelable {

  public abstract String tourismFeeCurrency();

  public abstract double subTotal();

  public abstract double tourismFee();

  public abstract double grandTotal();

  public abstract String bookingLink();

  public abstract List<TourismFee> fees();

  public abstract String skyDollar();

  public abstract double mandatoryTax();

  public abstract String currencymandatoryTax();


  public static PriceCheckSummary create(String tourismFeeCurrency, double subTotal, double tourismFee,
                                         double grandTotal, String bookingLink, List<TourismFee> fees,
                                         String skyDollar,double mandatoryTax, String currencymandatoryTax) {
    return builder()
        .tourismFeeCurrency(tourismFeeCurrency)
        .subTotal(subTotal)
        .tourismFee(tourismFee)
        .grandTotal(grandTotal)
        .bookingLink(bookingLink)
        .fees(fees)
        .skyDollar(skyDollar)
        .mandatoryTax(mandatoryTax)
        .currencymandatoryTax(currencymandatoryTax)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_PriceCheckSummary.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder tourismFeeCurrency(String tourismFeeCurrency);

    public abstract Builder subTotal(double subTotal);

    public abstract Builder tourismFee(double tourismFee);

    public abstract Builder grandTotal(double grandTotal);

    public abstract Builder bookingLink(String bookingLink);

    public abstract Builder fees(List<TourismFee> fees);

    public abstract Builder skyDollar(String skyDollar);

    public abstract Builder mandatoryTax(double mandatoryTax);

    public abstract Builder currencymandatoryTax(String currencymandatoryTax);

    public abstract PriceCheckSummary build();
  }
}
