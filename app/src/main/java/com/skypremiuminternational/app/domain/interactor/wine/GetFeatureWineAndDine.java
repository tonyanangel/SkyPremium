package com.skypremiuminternational.app.domain.interactor.wine;

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

public class GetFeatureWineAndDine extends UseCase<FeatureProduct, FeatureProductRequest> {

  private final ProductUtil productUtil;

  @Inject
  protected GetFeatureWineAndDine(DataManager dataManager, ThreadExecutor subscriberThread,
                                  PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.productUtil = productUtil;
  }

  @Override
  public Observable<FeatureProduct> provideObservable(FeatureProductRequest params) {
    return getDataManager().getFeatureWineAndDine(params)
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
