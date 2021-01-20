package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 1/23/18.
 */

public class UploadUserAvatar extends UseCase<UploadAvatarResponse, UploadAvatarRequest> {

  @Inject
  protected UploadUserAvatar(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UploadAvatarResponse> provideObservable(UploadAvatarRequest uploadAvatarRequest) {
    String token = getDataManager().getUserToken();
    return getDataManager().uploadAvatar(token, uploadAvatarRequest);
  }
}
