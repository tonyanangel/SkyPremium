package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class CreateCart extends UseCase<String, Void> {

  @Inject
  protected CreateCart(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<String> provideObservable(Void avoid) {
    return getDataManager().createCart();
  }
}
