package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

import static com.skypremiuminternational.app.app.utils.Constants.STATUS_VALID_PRODUCT;

public class GetTreeCategory  extends UseCase<ResponseBody, String> {
    @Inject
    protected GetTreeCategory(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread, ProductUtil productUtil) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<ResponseBody> provideObservable(final String params) {

        return getDataManager().getTreeCategory(params)
                .doOnNext(responseBody -> {

                });

    }
}
