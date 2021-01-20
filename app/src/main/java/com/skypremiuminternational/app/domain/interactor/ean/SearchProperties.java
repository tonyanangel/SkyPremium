package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class SearchProperties extends UseCase<List<Property>, SearchProperties.Params> {

  @Inject
  protected SearchProperties(DataManager dataManager,
                             ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<Property>> provideObservable(Params params) {
    return getDataManager().searchProperties(params.query)
        .flatMap(propertyIds -> {
          if (propertyIds == null || propertyIds.isEmpty()) {
            return Observable.just(new ArrayList<>());
          } else {
            return getDataManager().getAvailableProperties(propertyIds, params.roomCount,
                params.adultCount, params.children, params.arrival, params.departure,
                params.countryCode).map(properties -> sort(propertyIds, properties))
                .defaultIfEmpty(new ArrayList<>());
          }
        });
  }

  private List<Property> sort(List<String> propertyIds, Map<String, Property> propertyMap) {
    List<Property> sorted = new ArrayList<>();
    if (!propertyMap.isEmpty()) {
      for (String propertyId : propertyIds) {
        Property property = propertyMap.get(propertyId);
        if (property != null) {
          sorted.add(property);
        }
      }
    }
    return sorted;
  }

  public static class Params {

    public final String query;
    public final String arrival;
    public final String departure;
    public final String countryCode;
    public final int roomCount;
    public final int adultCount;
    public final List<Child> children;

    public Params(String query, String arrival, String departure, String countryCode,
                  int roomCount, int adultCount,
                  List<Child> children) {
      this.query = query;
      this.arrival = arrival;
      this.departure = departure;
      this.countryCode = countryCode;
      this.roomCount = roomCount;
      this.adultCount = adultCount;
      this.children = children;
    }
  }
}
