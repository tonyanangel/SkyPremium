package com.skypremiuminternational.app.domain.interactor.cart;

    import com.skypremiuminternational.app.domain.DataManager;
    import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
    import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
    import com.skypremiuminternational.app.domain.interactor.base.UseCase;

    import javax.inject.Inject;

    import okhttp3.ResponseBody;
    import rx.Observable;

public class UpdateBuyNowCart extends UseCase<ResponseBody, Void> {

  @Inject
  protected UpdateBuyNowCart(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(Void d) {
    return getDataManager().updateBuyNow();
  }

}

