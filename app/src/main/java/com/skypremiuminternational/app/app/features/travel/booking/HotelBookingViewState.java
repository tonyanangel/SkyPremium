package com.skypremiuminternational.app.app.features.travel.booking;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 10/6/17.
 */
@AutoValue
public abstract class HotelBookingViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  public static HotelBookingViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                             String message) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_HotelBookingViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract HotelBookingViewState build();
  }
}
