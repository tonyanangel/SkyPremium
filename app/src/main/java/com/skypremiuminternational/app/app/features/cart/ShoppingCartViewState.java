package com.skypremiuminternational.app.app.features.cart;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by aeindraaung on 1/26/18.
 */

@AutoValue
public abstract class ShoppingCartViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  public static ShoppingCartViewState create(Throwable error, boolean isLoading, boolean isSuccess) {
    return builder()
        .error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_ShoppingCartViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract ShoppingCartViewState build();
  }
}