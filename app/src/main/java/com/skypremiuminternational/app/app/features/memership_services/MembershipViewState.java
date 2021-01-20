package com.skypremiuminternational.app.app.features.memership_services;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class MembershipViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract UserDetailResponse message();

  public static MembershipViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                           UserDetailResponse message) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_MembershipViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(UserDetailResponse message);

    public abstract MembershipViewState build();
  }
}
