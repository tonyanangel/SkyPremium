package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetOutletResevation extends UseCase<List<OutletItem>, String> {

  @Inject
  protected GetOutletResevation(DataManager dataManager, ThreadExecutor subscriberThread,
                                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<OutletItem>> provideObservable(String productId) {
    return getDataManager().getOutletByProductID(productId);
  }
}