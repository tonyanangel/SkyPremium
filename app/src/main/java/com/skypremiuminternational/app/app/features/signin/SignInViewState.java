package com.skypremiuminternational.app.app.features.signin;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class SignInViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  abstract int groupId();

  public static SignInViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                       String message, int groupId) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .groupId(groupId)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_SignInViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract SignInViewState build();

    public abstract Builder groupId(int groupId);
  }
}
