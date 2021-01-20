package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.Version;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 12/3/18.
 */

public class GetAppVersion extends UseCase<Version.AppVersion, Void> {
  @Inject
  protected GetAppVersion(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Version.AppVersion> provideObservable(Void aVoid) {
    return getDataManager().getAppVersion()
        .flatMapIterable(version -> version.app_versions)
        .filter(appVersion -> appVersion.platform.equalsIgnoreCase("android"));
  }
}
