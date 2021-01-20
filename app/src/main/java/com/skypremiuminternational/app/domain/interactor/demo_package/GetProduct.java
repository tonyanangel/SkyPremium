package com.skypremiuminternational.app.domain.interactor.demo_package;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetHistoryFilter;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;

import javax.inject.Inject;

import rx.Observable;

public class GetProduct extends UseCase<DemoResponse, String> {

    @Inject
    protected GetProduct(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<DemoResponse> provideObservable(String params) {
        return getDataManager().getProduct(params);
    }




}