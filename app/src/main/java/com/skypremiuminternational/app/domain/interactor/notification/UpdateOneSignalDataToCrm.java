package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.OneSignalDeviceDataRequest;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class UpdateOneSignalDataToCrm  extends UseCase<ResponseBody, UpdateOneSignalDataToCrm.Params> {

  @Inject
  protected UpdateOneSignalDataToCrm(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(UpdateOneSignalDataToCrm.Params params) {
    return getDataManager().updateOSDataToCRM(params.oAuth, params.request);
  }

  public static class Params{
    String oAuth;
    OneSignalDeviceDataRequest request;

    public Params(String oAuth, OneSignalDeviceDataRequest request) {
      this.oAuth = oAuth;
      this.request = request;
    }
  }

}
