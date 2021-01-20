package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class GetHistoryFilter extends UseCase<ReserveHistoryRespone, GetHistoryFilter.Params> {

  @Inject
  protected GetHistoryFilter(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ReserveHistoryRespone> provideObservable(Params params) {
    return getDataManager().getHistoryFilter(params.sortBy,params.refine,params.currentPage,params.pageSize);
  }

  public static class Params{
    String currentPage;
    String pageSize;
    String sortBy;
    String refine;

    public Params(String sortBy,String refine,String currentPage, String pageSize) {
      this.currentPage = currentPage;
      this.pageSize = pageSize;
      this.sortBy = sortBy;
      this.refine = refine;
    }
  }
}
