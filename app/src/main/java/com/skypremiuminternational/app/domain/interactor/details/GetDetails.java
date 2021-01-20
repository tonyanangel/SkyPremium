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

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetDetails extends UseCase<DetailsItem, DetailsRequest> {

  private final ProductUtil productUtil;

  @Inject
  protected GetDetails(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.productUtil = productUtil;
  }

  @Override
  public Observable<DetailsItem> provideObservable(DetailsRequest params) {
    return getDataManager().getDetails(params).map(productUtil::flatInfo);
  }
}
