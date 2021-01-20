package com.skypremiuminternational.app.domain.interactor.wellness;

import com.skypremiuminternational.app.data.network.request.ProductListRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

import static com.skypremiuminternational.app.app.utils.Constants.STATUS_VALID_PRODUCT;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetWellness extends UseCase<ProductListResponse, ProductListRequest> {

  private final ProductUtil productUtil;

  @Inject
  protected GetWellness(DataManager dataManager, ThreadExecutor subscriberThread,
                        PostExecutionThread observerThread, ProductUtil productUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.productUtil = productUtil;
  }

  @Override
  public Observable<ProductListResponse> provideObservable(ProductListRequest params) {
    return getDataManager().getWellness(params).flatMap(response -> Observable.from(response.getItems())
        .filter(item -> item.getStatus() == STATUS_VALID_PRODUCT)
        .map(productUtil::flatInfo)
        .toList()
        .map(itemsItems -> {
          response.setItems(itemsItems);
          return response;
        }));
  }
}
