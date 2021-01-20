package com.skypremiuminternational.app.app.features.splash;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class SplashViewState {

  abstract boolean isUserLoggedIn();

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  abstract int groupId();

  public static SplashViewState create(boolean isUserLoggedIn, Throwable error, boolean isLoading,
                                       boolean isSuccess, String message, int groupId) {
    return builder().isUserLoggedIn(isUserLoggedIn)
        .error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .groupId(groupId)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_SplashViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder isUserLoggedIn(boolean isUserLoggedIn);

    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract SplashViewState build();

    public abstract Builder groupId(int groupId);
  }
}
