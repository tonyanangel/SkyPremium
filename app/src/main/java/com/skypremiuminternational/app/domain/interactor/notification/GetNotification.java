package com.skypremiuminternational.app.domain.interactor.notification;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class GetNotification extends UseCase<NotificationResponse, String> {
  @Inject
  protected GetNotification(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<NotificationResponse> provideObservable(String oAuth) {
    String UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
    return getDataManager().getNotification(oAuth, UUID);
  }


}
