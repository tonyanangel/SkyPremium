package com.skypremiuminternational.app.domain.interactor.first_time_popup;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class SetFirstTime extends UseCase<ResponseBody, Void>{



    @Inject
    protected SetFirstTime(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
      super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<ResponseBody> provideObservable(Void aVoid) {
      return getDataManager().setFirstTime();
    }
}
