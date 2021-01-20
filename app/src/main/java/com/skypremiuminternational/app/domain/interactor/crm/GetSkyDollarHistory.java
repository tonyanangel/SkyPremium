package com.skypremiuminternational.app.domain.interactor.crm;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetSkyDollarHistory extends UseCase<SkyDollarHistoryResponse, GetSkyDollarHistory.Params> {
  @Inject
  protected GetSkyDollarHistory(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<SkyDollarHistoryResponse> provideObservable(GetSkyDollarHistory.Params params) {
    return getDataManager().getSkyDollarHistory(params.oauth,params.currentPage,params.limit);
  }


  public static class Params{
    String oauth;
    String currentPage;
    String limit;

    public Params(String oauth, String currentPage, String limit) {
      this.oauth = oauth;
      this.currentPage = currentPage;
      this.limit = limit;
    }
  }

}
