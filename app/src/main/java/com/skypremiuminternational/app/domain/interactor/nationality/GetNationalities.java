package com.skypremiuminternational.app.domain.interactor.nationality;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetNationalities extends UseCase<List<Nationality>, Void> {

  @Inject
  protected GetNationalities(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<Nationality>> provideObservable(Void avoid) {
    return Observable.just(getDataManager().getNationalities());
  }
}
