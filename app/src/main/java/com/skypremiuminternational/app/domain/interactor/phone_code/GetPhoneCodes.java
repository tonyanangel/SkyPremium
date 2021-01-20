package com.skypremiuminternational.app.domain.interactor.phone_code;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetPhoneCodes extends UseCase<PhoneCode, Void> {

  @Inject
  protected GetPhoneCodes(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<PhoneCode> provideObservable(Void avoid) {
    return Observable.just(getDataManager().getPhoneCodes());
  }
}
