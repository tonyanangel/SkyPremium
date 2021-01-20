package com.skypremiuminternational.app.domain.interactor.demo_package;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;

import javax.inject.Inject;

import rx.Observable;

public class GetProductTwo extends UseCase<DemoRes, Void> {

    @Inject
    protected GetProductTwo(DataManager dataManager, ThreadExecutor subscriberThread,
                         PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<DemoRes> provideObservable(Void v) {
        return getDataManager().getProductTwo();
    }


}
