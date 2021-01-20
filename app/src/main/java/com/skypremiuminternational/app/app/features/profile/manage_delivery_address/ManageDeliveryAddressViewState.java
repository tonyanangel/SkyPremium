package com.skypremiuminternational.app.app.features.profile.manage_delivery_address;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by aeindraaung on 2/20/18.
 */

@AutoValue
public abstract class ManageDeliveryAddressViewState {
  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  public static ManageDeliveryAddressViewState create(Throwable error, boolean isLoading,
                                                      boolean isSuccess) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_ManageDeliveryAddressViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract ManageDeliveryAddressViewState build();
  }
}