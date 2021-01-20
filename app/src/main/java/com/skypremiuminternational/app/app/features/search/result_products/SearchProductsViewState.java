package com.skypremiuminternational.app.app.features.search.result_products;

import androidx.annotation.Nullable;

import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */
@AutoValue
public abstract class SearchProductsViewState {

  @Nullable
  abstract Throwable error();

  abstract boolean isLoading();

  abstract boolean isSuccess();

  @Nullable
  abstract String message();

  @Nullable
  abstract SearchProductResponse dataList();

  @Nullable
  abstract List<FavouriteListResponse> favouriteList();

  @Nullable
  abstract CategoryResponse category();

  public static SearchProductsViewState create(Throwable error, boolean isLoading, boolean isSuccess,
                                               String message, SearchProductResponse dataList,
                                               List<FavouriteListResponse> favouriteList, CategoryResponse category) {
    return builder().error(error)
        .isLoading(isLoading)
        .isSuccess(isSuccess)
        .message(message)
        .dataList(dataList)
        .favouriteList(favouriteList)
        .category(category)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_SearchProductsViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder isLoading(boolean isLoading);

    public abstract Builder isSuccess(boolean isSuccess);

    public abstract Builder message(String message);

    public abstract Builder dataList(SearchProductResponse dataList);

    public abstract Builder category(CategoryResponse category);

    public abstract Builder favouriteList(List<FavouriteListResponse> favouriteList);

    public abstract SearchProductsViewState build();
  }
}
