package com.skypremiuminternational.app.domain.interactor.travel;

import com.skypremiuminternational.app.data.network.request.FeatureProductRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import javax.inject.Inject;

import rx.Observable;

import static com.skypremiuminternational.app.app.utils.Constants.STATUS_VALID_PRODUCT;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetFeatureTravel extends UseCase<FeatureProduct, FeatureProductRequest> {

  private final ProductUtil productUtil;

  @Inject
  protected GetFeatureTravel(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.productUtil = productUtil;
  }

  @Override
  public Observable<FeatureProduct> provideObservable(final FeatureProductRequest params) {
    return getDataManager().getFeatureTravel(params)
        .doOnNext(responseBody -> {
          if (params.isSaveResult()) {
            getDataManager().saveEstoreItems(responseBody.getFeatureItems());
          }
        })
        .flatMap(response -> Observable.from(response.getFeatureItems())
            .filter(item -> item.getStatus() == STATUS_VALID_PRODUCT)
            .map(productUtil::flatInfo)
            .toList()
            .map(itemsItems -> {
              response.setFeatureItems(itemsItems);
              return response;
            }));
  }
}
