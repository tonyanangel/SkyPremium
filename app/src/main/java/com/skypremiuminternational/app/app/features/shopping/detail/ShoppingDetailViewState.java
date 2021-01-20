package com.skypremiuminternational.app.app.features.shopping.detail;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.google.auto.value.AutoValue;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class ShoppingDetailViewState {

  @Nullable
  abstract Throwable error();

  @Nullable
  abstract CategoryResponse category();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract DetailsItem message();

  public static ShoppingDetailViewState create(Throwable error, CategoryResponse category,
                                               boolean isLoading, boolean isSuccess, DetailsItem message) {
    return builder().error(error)
        .category(category)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_ShoppingDetailViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(DetailsItem message);

    public abstract Builder category(CategoryResponse category);

    public abstract ShoppingDetailViewState build();
  }
}
