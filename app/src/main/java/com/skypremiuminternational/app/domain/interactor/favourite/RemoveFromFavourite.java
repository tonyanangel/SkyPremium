package com.skypremiuminternational.app.domain.interactor.favourite;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 25/1/18.
 */

public class RemoveFromFavourite extends UseCase<FavouriteResponse, RemoveFromFavourite.Params> {

  @Inject
  protected RemoveFromFavourite(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<FavouriteResponse> provideObservable(Params params) {
    return getDataManager().removeFromFav(params.wishlistItemId);
  }

  public static class Params {
    public final String wishlistItemId;

    public Params(String wishlistItemId) {
      this.wishlistItemId = wishlistItemId;
    }
  }
}
