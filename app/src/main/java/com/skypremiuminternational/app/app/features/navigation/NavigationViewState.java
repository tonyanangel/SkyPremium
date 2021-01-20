package com.skypremiuminternational.app.app.features.navigation;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class NavigationViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract UserDetailResponse message();

  @Nullable
  abstract String avatarLink();

  @Nullable
  abstract String amtLoyalty();

  @Nullable
  abstract String txtLoading();

  public static NavigationViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                           UserDetailResponse message, String avatarLink,
                                           String amtLoyalty, String txtLoading) {
    return builder()
        .error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .avatarLink(avatarLink)
        .amtLoyalty(amtLoyalty)
        .txtLoading(txtLoading)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_NavigationViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(UserDetailResponse message);

    public abstract Builder avatarLink(String avatarLink);

    public abstract Builder amtLoyalty(String amtLoyalty);

    public abstract NavigationViewState build();

    public abstract Builder txtLoading(String txtLoading);
  }
}
