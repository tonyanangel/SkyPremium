package com.skypremiuminternational.app.domain.interactor.country_code;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetCountryCodesFromAPI extends UseCase<List<CountryCode>, Void> {

  @Inject
  protected GetCountryCodesFromAPI(DataManager dataManager, ThreadExecutor subscriberThread,
                                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CountryCode>> provideObservable(Void avoid) {
    return getDataManager().getCountryCodesFromAPI().doOnNext(new Action1<List<CountryCode>>() {
      @Override
      public void call(List<CountryCode> responseBody) {

        getDataManager().saveCountryCodes(responseBody);
        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
      }
    });
    /*return Single.create(new Single.OnSubscribe<ResponseBody>() {
      @Override public void call(SingleSubscriber<? super ResponseBody> singleSubscriber) {
        //getDataManager().saveToken();
        //singleSubscriber.onSuccess(Boolean.TRUE);
      }
    }).toObservable();*/
  }
}
