package com.skypremiuminternational.app.app.features.travel;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class TravelViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract ProductListResponse dataList();

  @Nullable
  abstract FeatureProduct featureList();

  @Nullable
  abstract CategoryResponse category();

  @Nullable
  abstract List<FavouriteListResponse> favouriteList();

  @Nullable
  abstract CategoryDetailsResponse categoryDetails();

  public static TravelViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                       String message, ProductListResponse dataList, FeatureProduct featureList,
                                       CategoryResponse category, List<FavouriteListResponse> favouriteList,
                                       CategoryDetailsResponse categoryDetails) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .dataList(dataList)
        .featureList(featureList)
        .category(category)
        .favouriteList(favouriteList)
        .categoryDetails(categoryDetails)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_TravelViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder dataList(ProductListResponse message);

    public abstract Builder featureList(FeatureProduct message);

    public abstract Builder category(CategoryResponse message);

    public abstract Builder categoryDetails(CategoryDetailsResponse message);

    public abstract Builder favouriteList(List<FavouriteListResponse> favouriteList);

    public abstract TravelViewState build();
  }
}
