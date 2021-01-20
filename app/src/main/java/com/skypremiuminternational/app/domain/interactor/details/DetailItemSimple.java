package com.skypremiuminternational.app.domain.interactor.details;

import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import javax.inject.Inject;

import rx.Observable;

public class DetailItemSimple extends UseCase<DetailsItem, String> {


  @Inject
  protected DetailItemSimple(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<DetailsItem> provideObservable(String sku) {
    return getDataManager().getDetailItemBySku(sku);
  }
}

