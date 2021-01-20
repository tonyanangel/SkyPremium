package com.skypremiuminternational.app.app.features.estore.detail;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;

/**
 * Created by wmw on 2/6/2018.
 */
@AutoValue
public abstract class EstoreDetailViewState {

  @Nullable
  abstract Throwable error();

  @Nullable
  abstract CategoryResponse category();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract DetailsItem message();

  public static EstoreDetailViewState create(Throwable error, CategoryResponse category,
                                             boolean isLoading, boolean isSuccess, DetailsItem message) {
    return builder().error(error)
        .category(category)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_EstoreDetailViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(DetailsItem message);

    public abstract Builder category(CategoryResponse category);

    public abstract EstoreDetailViewState build();
  }
}
