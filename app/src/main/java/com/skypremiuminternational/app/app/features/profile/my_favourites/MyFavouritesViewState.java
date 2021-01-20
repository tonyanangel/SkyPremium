package com.skypremiuminternational.app.app.features.profile.my_favourites;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

import java.util.List;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class MyFavouritesViewState {

  @Nullable
  abstract Integer totalCount();

  @Nullable
  abstract CategoryResponse category();

  @Nullable
  abstract List<FavouriteListResponse> myFavourites();

  @Nullable
  abstract Throwable error();

  public static MyFavouritesViewState create(Integer totalCount, CategoryResponse category,
                                             List<FavouriteListResponse> myFavourites, Throwable error) {
    return builder().totalCount(totalCount)
        .category(category)
        .myFavourites(myFavourites)
        .error(error)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_MyFavouritesViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder error(Throwable error);

    public abstract Builder myFavourites(List<FavouriteListResponse> myFavourites);

    public abstract Builder category(CategoryResponse category);

    public abstract Builder totalCount(Integer totalCount);

    public abstract MyFavouritesViewState build();
  }
}
