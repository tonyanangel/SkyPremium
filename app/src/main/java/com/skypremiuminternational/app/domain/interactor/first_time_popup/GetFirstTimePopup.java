package com.skypremiuminternational.app.domain.interactor.first_time_popup;

import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetFirstTimePopup extends UseCase<FirstTimePopup, Void> {

  @Inject
  protected GetFirstTimePopup(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<FirstTimePopup> provideObservable(Void aVoid) {
    return getDataManager().getFirstTimePopup();
  }



}

