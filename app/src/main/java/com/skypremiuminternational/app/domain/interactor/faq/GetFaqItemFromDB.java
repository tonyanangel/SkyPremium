package com.skypremiuminternational.app.domain.interactor.faq;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by sandi on 5/23/17.
 */

public class GetFaqItemFromDB extends UseCase<List<FaqItem>, String> {

  @Inject protected GetFaqItemFromDB(DataManager dataManager, ThreadExecutor subscriberThread,
      PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override public Observable<List<FaqItem>> provideObservable(String keyword) {
    return Observable.just(getDataManager().getFaqItemByKeyword(keyword));
  }
}
