package com.skypremiuminternational.app.app.features.profile.my_favourites;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface MyFavouritesView<T extends Presentable> extends Viewable<T> {

  void render(MyFavouritesViewState viewState);

  void goToDetail(DetailsItem detailsItem, FavouriteListResponse item);

  void renderCountItem(int count);

  void updateViewCount(String count);
}
