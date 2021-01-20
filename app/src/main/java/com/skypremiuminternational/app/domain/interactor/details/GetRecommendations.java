package com.skypremiuminternational.app.domain.interactor.details;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.details.RecommendItems;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetRecommendations extends UseCase<RecommendItems, DetailsRequest> {

  private final ProductUtil productUtil;
  private RecommendItems recommendItems;

  @Inject
  protected GetRecommendations(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.productUtil = productUtil;
  }

  @Override
  public Observable<RecommendItems> provideObservable(DetailsRequest params) {
    return getDataManager().getRecommendations(params).flatMapIterable(recommendItems -> {
      GetRecommendations.this.recommendItems = recommendItems;
      return recommendItems.getItems();
    })
        .filter(item -> item.getStatus() == Constants.STATUS_VALID_PRODUCT)
        .map(productUtil::flatInfo)
        .toList()
        .map(itemsItems -> {
          GetRecommendations.this.recommendItems.setItems(itemsItems);
          return GetRecommendations.this.recommendItems;
        });
  }
}
