package com.skypremiuminternational.app.app.features.travel.ean.suggestion;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.GetSuggestions;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SuggestionPresenter extends BasePresenter<SuggestionView> {

  private final GetSuggestions getSuggestions;
  private CompositeSubscription compositeSubscription;

  @Inject
  public SuggestionPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                             GetSuggestions getSuggestions) {
    super(getAppVersion, internalStorageManager);
    this.getSuggestions = getSuggestions;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }

  public void getSuggestions(String keyword) {
    add(getSuggestions.execute(new SingleSubscriber<List<Suggestion>>() {
      @Override
      public void onSuccess(List<Suggestion> suggestions) {
        getView().render(suggestions);
      }

      @Override
      public void onError(Throwable error) {
        getView().render(error);
      }
    }, new GetSuggestions.Params(keyword)));
  }
}
