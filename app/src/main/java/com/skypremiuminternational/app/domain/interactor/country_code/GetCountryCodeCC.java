package com.skypremiuminternational.app.domain.interactor.country_code;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class GetCountryCodeCC extends UseCase<List<CountryCodeCC>, Void> {

  @Inject
  protected GetCountryCodeCC(DataManager dataManager, ThreadExecutor subscriberThread,
                                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CountryCodeCC>> provideObservable( Void d) {
    return getDataManager().getCountryCodeCc();
  }
}
