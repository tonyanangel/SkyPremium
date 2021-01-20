package com.skypremiuminternational.app.app.features.forgot_password.update;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class UpdatePasswordViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract UserDetailResponse userDetailResponse();

  public static UpdatePasswordViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                               String message, UserDetailResponse userDetailResponse) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .userDetailResponse(userDetailResponse)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_UpdatePasswordViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder userDetailResponse(UserDetailResponse userDetailResponse);

    public abstract UpdatePasswordViewState build();
  }
}
