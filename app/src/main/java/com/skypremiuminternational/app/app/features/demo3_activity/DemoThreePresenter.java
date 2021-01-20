
package com.skypremiuminternational.app.app.features.demo3_activity;

import com.skypremiuminternational.app.app.features.demo3_activity.DemoThreeActivity;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.demo_package.GetProduct;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetOutletResevation;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetResevationBookingDetail;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DemoThreePresenter extends BasePresenter<DemoThreeActivity> {

    private  final GetProduct getProduct;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    //====
    @Inject
    public DemoThreePresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                              GetProduct getProduct
                              ) {
        super(getAppVersion, internalStorageManager);
        this.getProduct = getProduct;
    }


    @Override
    public void add(Subscription subscription) {
        compositeSubscription.add(subscription);
    }


    public void getProduct(){

        String path = "http://54.254.149.93/sg/member/rest/V1/products?searchCriteria[currentPage]=1&searchCriteria[filterGroups][0][filters][0][field]=visibility&searchCriteria[filterGroups][0][filters][0][conditionType]=eq&searchCriteria[filterGroups][0][filters][0][value]=4&searchCriteria[filterGroups][5][filters][1][field]=status&searchCriteria[filterGroups][5][filters][1][conditionType]=eq&searchCriteria[filterGroups][5][filters][1][value]=1&searchCriteria[pageSize]=30&rootCategory=55&searchCriteria[filterGroups][1][filters][0][field]=category_id&searchCriteria[filterGroups][1][filters][0][conditionType]=eq&searchCriteria[filterGroups][1][filters][0][value]=55&searchCriteria[sortOrders][11][field]=popularity_order&searchCriteria[sortOrders][11][direction]=ASC&searchCriteria[sortOrders][12][field]=name&searchCriteria[sortOrders][12][direction]=ASC";
        getView().showDialog();
        add(getProduct.execute(new SingleSubscriber<DemoResponse>() {
            @Override
            public void onSuccess(DemoResponse value) {
                getView().hideDialog();
                getView().renderProduct(value);
            }

            @Override
            public void onError(Throwable error) {
                getView().hideDialog();
            }
        },path));

    }

    public void action(String str){
        getView().renderA();
        getView().renderB(str);
        getView().renderC();
        getView().renderD();
    }


}
