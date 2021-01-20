package com.skypremiuminternational.app.app.features.membership_renewal;

import com.skypremiuminternational.app.app.features.landing.LandingViewState;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.interactor.auth.ClearUserData;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.auth.GetRenewalToken;
import com.skypremiuminternational.app.domain.interactor.cart.AddRenewalToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetRenewalProductPrice;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *  Created  by WIKI Toan Tran  20200428
 */
public class MembershipRenewalPresenter extends BasePresenter<MembershipRenewalView> {


    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    InternalStorageManager internalStorageManager;
    GetRenewalToken getRenewalToken;
    @Inject
    public MembershipRenewalPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                      GetRenewalToken getRenewalToken) {
        super(getAppVersion, internalStorageManager);
        this.internalStorageManager = internalStorageManager;
        this.getRenewalToken = getRenewalToken;
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
        super.onStop();
    }
    @Override
    public void add(Subscription subscription) {

    }
    void getUserDetail(){

        // 20200428 WIKI Toan Tran get user detail from  internal storage
        UserDetailResponse userDetail =  internalStorageManager.getUserDetail();
        getView().renderWebView(userDetail);
    }

    void getRenewalToken(String customer){
        getView().showLoading();
        add(getRenewalToken.execute(new SingleSubscriber<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                getView().hideLoading();
                getView().loadWebView(responseBody);
            }
            @Override
            public void onError(Throwable error) {
                getView().hideLoading();
            }
        },customer));
    }
}
