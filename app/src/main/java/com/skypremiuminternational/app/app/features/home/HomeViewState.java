package com.skypremiuminternational.app.app.features.home;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.app.features.landing.LandingViewState;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;
import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class HomeViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract Double renewalPrice();

  @Nullable
  abstract UserDetailResponse response();

  @Nullable
  abstract String membershipExpired();

  @Nullable
  abstract String message();

  @Nullable
  abstract HomeCategoryResponse dataList();

  public static HomeViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                     String message, HomeCategoryResponse response,Double renewalPrice,UserDetailResponse responseuser,String membershipExpired) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .dataList(response)
        .membershipExpired(membershipExpired)
        .renewalPrice(renewalPrice)
        .response(responseuser)
        .build();
  }

  public static HomeViewState.Builder builder() {
    return new AutoValue_HomeViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract HomeViewState.Builder error(Throwable error);

    public abstract HomeViewState.Builder isLoading(boolean isLoading);

    public abstract HomeViewState.Builder isSuccess(boolean isSuccess);

    public abstract HomeViewState.Builder message(String message);

    public abstract HomeViewState.Builder response(UserDetailResponse response);

    public abstract HomeViewState.Builder renewalPrice(Double renewalPrice);

    public abstract HomeViewState.Builder membershipExpired(String membershipExpired);

    public abstract HomeViewState.Builder dataList(HomeCategoryResponse message);

    public abstract HomeViewState build();
  }
}
