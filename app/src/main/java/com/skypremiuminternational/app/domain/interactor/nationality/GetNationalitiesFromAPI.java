package com.skypremiuminternational.app.domain.interactor.nationality;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetNationalitiesFromAPI extends UseCase<List<Nationality>, Void> {

  @Inject
  protected GetNationalitiesFromAPI(DataManager dataManager, ThreadExecutor subscriberThread,
                                    PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<Nationality>> provideObservable(Void avoid) {
    return getDataManager().getNationalitiesFromAPI().doOnNext(new Action1<List<Nationality>>() {
      @Override
      public void call(List<Nationality> responseBody) {

        getDataManager().saveNationalities(responseBody);
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
