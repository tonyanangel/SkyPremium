package com.skypremiuminternational.app.app.features.membership_renewal;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import okhttp3.ResponseBody;

/**
 *  Create by WIKI Toan Tran 20200428
 * @param <T>
 */

public interface MembershipRenewalView <T extends Presentable> extends Viewable<T> {

    //render web view
    void renderWebView(UserDetailResponse response );


    void loadWebView(ResponseBody response );
}
