
package com.skypremiuminternational.app.app.features.demo_activity;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetOutletResevation;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetResevationBookingDetail;

import javax.inject.Inject;

import rx.Subscription;

public class DemoPresenter extends BasePresenter<DemoActvity> {
//====
    @Inject
    public DemoPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager) {
        super(getAppVersion, internalStorageManager);
    }


    @Override
    public void add(Subscription subscription) {

    }

    public void action(String str){
        getView().renderA();
        getView().renderB(str);
        getView().renderC();
        getView().renderD();
    }


}
