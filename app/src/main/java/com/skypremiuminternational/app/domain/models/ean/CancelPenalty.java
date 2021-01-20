package com.skypremiuminternational.app.domain.models.ean;

import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class CancelPenalty implements Parcelable {

  @Nullable
  public abstract String currency();

  public abstract String start();

  public abstract String end();

  @Nullable
  public abstract String percent();

  @Nullable
  public abstract String nights();

  @Nullable
  public abstract String amount();

  @Nullable
  public abstract List<CancelPenalty> cancelPenaltyList();

  public abstract boolean isRefundable();

  public static CancelPenalty create(String currency, String start, String end, String percent,
                                     String nights, String amount, boolean isRefundable,List<CancelPenalty> cancelPenaltyList) {
    return builder()
        .currency(currency)
        .start(start)
        .end(end)
        .percent(percent)
        .nights(nights)
        .amount(amount)
        .isRefundable(isRefundable)
        .cancelPenaltyList(cancelPenaltyList)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_CancelPenalty.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder start(String start);

    public abstract Builder end(String end);

    public abstract Builder nights(String nights);

    public abstract Builder isRefundable(boolean isRefundable);

    public abstract Builder percent(String percent);

    public abstract Builder amount(String amount);

    public abstract Builder currency(String currency);

    public abstract Builder cancelPenaltyList(List<CancelPenalty> cancelPenaltyList);

    public abstract CancelPenalty build();
  }
}
