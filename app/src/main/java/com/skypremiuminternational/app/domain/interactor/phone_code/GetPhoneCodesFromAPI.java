package com.skypremiuminternational.app.domain.interactor.phone_code;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetPhoneCodesFromAPI extends UseCase<PhoneCode, Void> {

  @Inject
  protected GetPhoneCodesFromAPI(DataManager dataManager, ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<PhoneCode> provideObservable(Void avoid) {
    return getDataManager().getPhoneCodesFromAPI().doOnNext(new Action1<PhoneCode>() {
      @Override
      public void call(PhoneCode responseBody) {

        List<PhoneCode.PhoneCode_> phoneCode_list = responseBody.getPhoneCodes();
        Collections.sort(phoneCode_list, new Comparator<PhoneCode.PhoneCode_>() {
          @Override
          public int compare(PhoneCode.PhoneCode_ phoneCode_1, PhoneCode.PhoneCode_ phoneCode_2) {
            String one = phoneCode_1.getCountryName();
            String two = phoneCode_2.getCountryName();
            return one.compareTo(two);
          }
        });

        responseBody.setPhoneCodes(phoneCode_list);

        getDataManager().savePhoneCodes(responseBody);
      }
    });
  }
}
