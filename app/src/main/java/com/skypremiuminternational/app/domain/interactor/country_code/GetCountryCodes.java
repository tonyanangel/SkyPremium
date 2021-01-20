package com.skypremiuminternational.app.domain.interactor.country_code;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetCountryCodes extends UseCase<List<CountryCode>, Void> {

  @Inject
  protected GetCountryCodes(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CountryCode>> provideObservable(Void avoid) {
    return Observable.just(getDataManager().getCountryCodes());
  }
}
