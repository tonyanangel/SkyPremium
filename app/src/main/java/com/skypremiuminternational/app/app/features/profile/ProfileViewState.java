package com.skypremiuminternational.app.app.features.profile;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class ProfileViewState {

  public static Builder builder() {
    return new AutoValue_ProfileViewState.Builder();
  }

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String txtLoading();

  @Nullable
  abstract UserDetailResponse message();

  public static ProfileViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                        String txtLoading, UserDetailResponse message) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .txtLoading(txtLoading)
        .message(message)
        .build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(UserDetailResponse message);

    public abstract ProfileViewState build();

    public abstract Builder txtLoading(String txtLoading);
  }
}
