package com.skypremiuminternational.app.app.features.forgot_password;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class ForgotPasswordViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  public static ForgotPasswordViewState create(Throwable error, boolean isLoading,
                                               boolean isSuccess, String message) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_ForgotPasswordViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract ForgotPasswordViewState build();
  }
}
