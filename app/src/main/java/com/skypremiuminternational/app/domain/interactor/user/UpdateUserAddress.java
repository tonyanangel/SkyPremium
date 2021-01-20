package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Observable;

public class UpdateUserAddress extends UseCase<UserDetailResponse, UpdateUserAddress.ParamsRequest> {

    @Inject
    protected UpdateUserAddress(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<UserDetailResponse> provideObservable(ParamsRequest params) {
        if (params.addNewAddress) {
            return getDataManager().getUserDetailFromAPI().flatMap(userDetail -> {
                userDetail.setAddresses(GetDeliveryAddress.filterAddress(userDetail.getAddresses()));
                if (userDetail.getAddresses() != null && userDetail.getAddresses().size() >= 3) {
                    return Observable.error(new Exception("Addresses are limited to a maximum of 3"));
                }
                return getDataManager().updateUserDetail(params.request)
                        .doOnNext(responseBody ->{} /*getDataManager().saveUserDetail(responseBody)*/);
            });
        } else {
            return getDataManager().updateUserDetail(params.request)
                    .doOnNext(responseBody -> {}/*getDataManager().saveUserDetail(responseBody)*/);
        }
    }

    public static class ParamsRequest {
        public final UpdateUserDeliveryRequest request;
        public final boolean addNewAddress;
        public ParamsRequest(UpdateUserDeliveryRequest request, boolean addNewAddress) {
            this.request = request;
            this.addNewAddress = addNewAddress;
        }
    }
}
