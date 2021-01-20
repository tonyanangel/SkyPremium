package com.skypremiuminternational.app.app.features.demo_two_activity;


import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.demo_package.GetProduct;
import com.skypremiuminternational.app.domain.interactor.demo_package.GetProductTwo;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DemoTwoPresenter extends BasePresenter<DemoTwoActivity> {
    private final GetProduct getProduct;
    private final GetProductTwo getProductTwo;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public DemoTwoPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                            GetProduct getProduct, GetProductTwo getProductTwo ) {
        super(getAppVersion, internalStorageManager);
        this.getProduct = getProduct;
        this.getProductTwo = getProductTwo;
    }

    @Override
    public void add(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void getProduct(){

        //String path = "http://54.254.149.93/sg/member/rest/V1/products?searchCriteria[currentPage]=1&searchCriteria[filterGroups][0][filters][0][field]=visibility&searchCriteria[filterGroups][0][filters][0][conditionType]=eq&searchCriteria[filterGroups][0][filters][0][value]=4&searchCriteria[filterGroups][5][filters][1][field]=status&searchCriteria[filterGroups][5][filters][1][conditionType]=eq&searchCriteria[filterGroups][5][filters][1][value]=1&searchCriteria[pageSize]=30&rootCategory=55&searchCriteria[filterGroups][1][filters][0][field]=category_id&searchCriteria[filterGroups][1][filters][0][conditionType]=eq&searchCriteria[filterGroups][1][filters][0][value]=55&searchCriteria[sortOrders][11][field]=popularity_order&searchCriteria[sortOrders][11][direction]=ASC&searchCriteria[sortOrders][12][field]=name&searchCriteria[sortOrders][12][direction]=ASC";


        getView().showDialog();
        add(getProductTwo.execute(new SingleSubscriber<DemoRes>() {
            @Override
            public void onSuccess(DemoRes value) {
                getView().hideDialog();
                getView().renderProduct(value);
            }

            @Override
            public void onError(Throwable error) {
                getView().hideDialog();
            }
        }));

    }

    public void action(String sadasd) {

    }
}
