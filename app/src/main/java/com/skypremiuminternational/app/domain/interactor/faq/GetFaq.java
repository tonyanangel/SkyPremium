package com.skypremiuminternational.app.domain.interactor.faq;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.faq.FaqResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetFaq extends UseCase<FaqResponse, Void> {

  @Inject
  protected GetFaq(DataManager dataManager, ThreadExecutor subscriberThread,
                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<FaqResponse> provideObservable(Void avoid) {
    return getDataManager().getFaqFromAPI().doOnNext(new Action1<FaqResponse>() {
      @Override
      public void call(FaqResponse responseBody) {
        getDataManager().saveFaqItem(responseBody.getItems());
        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
      }
    });
  }
}
