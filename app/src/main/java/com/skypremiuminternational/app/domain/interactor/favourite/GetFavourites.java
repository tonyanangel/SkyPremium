package com.skypremiuminternational.app.domain.interactor.favourite;

import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 1/24/18.
 */

public class GetFavourites
    extends UseCase<List<FavouriteListResponse>, GetFavouriteRequest> {

  @Inject
  protected GetFavourites(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<FavouriteListResponse>> provideObservable(GetFavouriteRequest request) {
    return getDataManager().getFavourites(request);
  }
}
