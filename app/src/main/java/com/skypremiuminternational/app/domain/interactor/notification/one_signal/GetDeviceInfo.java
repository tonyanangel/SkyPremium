package com.skypremiuminternational.app.domain.interactor.notification.one_signal;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.interactor.notification.ChangeFirstTimeLogin;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class GetDeviceInfo extends UseCase<OSPlayer, String> {

  @Inject
  protected GetDeviceInfo(DataManager dataManager, ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<OSPlayer> provideObservable(String playerId) {
    return getDataManager().getDeviceInfo(playerId);
  }


}
