package com.skypremiuminternational.app.domain.interactor.user.billingaddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment.BillingAddressPaymentRespond;
import javax.inject.Inject;
import okhttp3.RequestBody;
import rx.Observable;

public class BillingAddressPayment extends UseCase<BillingAddressPaymentRespond, RequestBody> {
    @Inject
    protected BillingAddressPayment(DataManager dataManager,
                                    ThreadExecutor subscriberThread,
                                    PostExecutionThread observerThread) {
        super(dataManager, subscriberThread, observerThread);
    }

    @Override
    public Observable<BillingAddressPaymentRespond> provideObservable(RequestBody params) {
        return getDataManager().requestBillingAddressPayment(params);
    }
}