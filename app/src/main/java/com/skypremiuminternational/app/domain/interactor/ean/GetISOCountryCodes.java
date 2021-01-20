package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetISOCountryCodes extends UseCase<List<ISOCountry>, Void> {
  @Inject
  protected GetISOCountryCodes(DataManager dataManager,
                               ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<ISOCountry>> provideObservable(Void aVoid) {
    return getDataManager().getISOCountryCodes();
  }
}
