package com.skypremiuminternational.app.app.features.landing;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class LandingViewState {

  @Nullable
  abstract Double renewalPrice();

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract UserDetailResponse response();

  @Nullable
  abstract String membershipExpired();

  public static LandingViewState create(Double renewalPrice, Throwable error, boolean isLoading,
                                        boolean isSuccess, String message, UserDetailResponse response,
                                        String membershipExpired) {
    return builder()
        .renewalPrice(renewalPrice)
        .error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .response(response)
        .membershipExpired(membershipExpired)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_LandingViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder response(UserDetailResponse response);

    public abstract Builder renewalPrice(Double renewalPrice);

    public abstract LandingViewState build();

    public abstract Builder membershipExpired(String membershipExpired);
  }
}
