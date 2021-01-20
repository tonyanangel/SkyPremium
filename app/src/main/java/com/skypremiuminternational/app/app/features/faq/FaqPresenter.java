package com.skypremiuminternational.app.app.features.faq;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.faq.GetFaq;
import com.skypremiuminternational.app.domain.interactor.faq.GetFaqItemFromDB;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;
import com.skypremiuminternational.app.domain.models.faq.FaqResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class FaqPresenter extends BasePresenter<FaqView> {

  private FaqViewState viewState;

  private final GetFaq getFaq;
  private final GetFaqItemFromDB getFaqItemFromDB;
  private final GetCartCount getCartCount;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public FaqPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                      GetFaq getFaq, GetFaqItemFromDB getFaqItemFromDB, GetCartCount getCartCount) {
    super(getAppVersion, internalStorageManager);
    viewState = FaqViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .build();

    this.getFaq = getFaq;
    this.getFaqItemFromDB = getFaqItemFromDB;
    this.getCartCount = getCartCount;
    attachLoading();
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }

  public void getFaq() {

    onShowLoading();

    Subscription subscription = getFaq.execute(new Subscriber<FaqResponse>() {
      @Override
      public void onSuccess(FaqResponse response) {
        viewState = FaqViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message("1")
            .dataList(response.getItems())
            .build();
        getView().render(viewState);
        Timber.e(response.toString());
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getFaqItemByKeyword(String keyword) {

    onShowLoading();

    Subscription subscription = getFaqItemFromDB.execute(new Subscriber<List<FaqItem>>() {
      @Override
      public void onSuccess(List<FaqItem> response) {
        viewState = FaqViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message("2")
            .dataList(response)
            .build();
        getView().render(viewState);
        Timber.e(response.toString());
      }
    }, keyword);

    compositeSubscription.add(subscription);
  }

  public void onShowLoading() {
    viewState = FaqViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .dataList(viewState.dataList())
        .build();
    getView().render(viewState);
  }

  abstract class Subscriber<T> extends BaseSubscriber<T> {

    @Override
    public void onError(Throwable error) {
      super.onError(error);
      error.printStackTrace();
      viewState = FaqViewState.builder()
          .error(error)
          .isLoading(false)
          .isSuccess(false)
          .message(null)
          .dataList(viewState.dataList())
          .build();
      getView().render(viewState);
    }
  }
}
