package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class GetTreeCategoryByCheckList extends UseCase<ResponseBody, String> {
    @Inject
    protected GetTreeCategoryByCheckList(DataManager dataManager, ThreadExecutor subscriberThread,
                                  PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<ResponseBody> provideObservable(String request) {
        return getDataManager().getTreeCategoryByCheckList(request).doOnNext(responseBody -> {

        });
    }
}
