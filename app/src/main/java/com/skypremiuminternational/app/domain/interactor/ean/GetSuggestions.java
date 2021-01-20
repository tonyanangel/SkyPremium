package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetSuggestions extends UseCase<List<Suggestion>, GetSuggestions.Params> {

  @Inject
  protected GetSuggestions(DataManager dataManager,
                           ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<Suggestion>> provideObservable(Params params) {
    String query = params.keyword + "&size=250&suggester=suggestname";
    return getDataManager().getSuggestions(query);
  }

  public static class Params {
    public final String keyword;

    public Params(String keyword) {
      this.keyword = keyword;
    }
  }
}
