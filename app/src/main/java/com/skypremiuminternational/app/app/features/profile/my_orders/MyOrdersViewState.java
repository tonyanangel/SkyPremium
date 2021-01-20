package com.skypremiuminternational.app.app.features.profile.my_orders;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class MyOrdersViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();
}
