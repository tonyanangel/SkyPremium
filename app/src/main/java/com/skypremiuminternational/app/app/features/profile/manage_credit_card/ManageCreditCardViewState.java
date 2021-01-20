package com.skypremiuminternational.app.app.features.profile.manage_credit_card;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class ManageCreditCardViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();
}
