package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class SearchRooms extends UseCase<Property, SearchRooms.Params> {

  @Inject
  protected SearchRooms(DataManager dataManager,
                        ThreadExecutor subscriberThread,
                        PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Property> provideObservable(Params params) {
    List<String> propertyIds = new ArrayList<>();
    propertyIds.add(params.propertyId);
    return getDataManager().getAvailableProperties(propertyIds, params.roomCount, params.adultCount,
        params.children, params.arrival, params.departure, params.countryCode)
        .flatMap(properties -> {
          if (properties != null && properties.size() == 1) {
            if (properties.entrySet().iterator().hasNext()) {
              return Observable.just(properties.entrySet().iterator().next().getValue());
            }
          }
          return Observable.error(new Exception("No room type is available"));
        });
  }

  public static class Result {
    public final List<Room> rooms;
    public final int roomCount;

    public Result(List<Room> rooms, int roomCount) {
      this.rooms = rooms;
      this.roomCount = roomCount;
    }
  }

  public static class Params {
    public final String propertyId;
    public final String arrival;
    public final String departure;
    public final String countryCode;
    public final int roomCount;
    public final int adultCount;
    public final List<Child> children;

    public Params(String propertyId, String arrival, String departure, String countryCode,
                  int roomCount, int adultCount, List<Child> children) {
      this.propertyId = propertyId;
      this.arrival = arrival;
      this.departure = departure;
      this.countryCode = countryCode;
      this.roomCount = roomCount;
      this.adultCount = adultCount;
      this.children = children;
    }
  }
}
