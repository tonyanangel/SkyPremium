package com.skypremiuminternational.app.domain.interactor.details;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;

import javax.inject.Inject;

import rx.Observable;

public class GetDetailCategory extends UseCase<DetailsItem, String> {

    @Inject
    protected GetDetailCategory(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<DetailsItem> provideObservable(String id_category) {
        return getDataManager().getDetailCategory(id_category);
    }
}