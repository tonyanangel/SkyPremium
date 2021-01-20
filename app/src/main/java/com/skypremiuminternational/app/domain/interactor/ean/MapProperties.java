package com.skypremiuminternational.app.domain.interactor.ean;

import android.util.Log;

import com.google.gson.Gson;
import com.skypremiuminternational.app.data.mapper.PropertyMapper;
import com.skypremiuminternational.app.data.model.ean.availability.AvailableProperty;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class MapProperties extends UseCase<Property, MapProperties.Params> {

  private final OccupancyArranger occupancyArranger;
  private Gson gson;
  private final PropertyMapper propertyMapper;

  @Inject
  protected MapProperties(DataManager dataManager,
                          ThreadExecutor subscriberThread, OccupancyArranger occupancyArranger,
                          PostExecutionThread observerThread, PropertyMapper propertyMapper) {
    super(dataManager, subscriberThread, observerThread);
    this.propertyMapper = propertyMapper;
    this.occupancyArranger = occupancyArranger;
    this.gson = new Gson();
  }

  @Override
  public Observable<Property> provideObservable(Params params) {
/*    Log.e("MapProperties", "provideObservable" + System.currentTimeMillis());
    String roomItemOccupancy =
        propertyMapper.formatRoomOccupancy(params.adultCount, params.children);

    List<String> occupancies =
        occupancyArranger.arrangeWithChildAge(params.roomCount, params.adultCount, params.children);

    return Observable.from(params.availableProperties)
        .map(availableProperty -> propertyMapper.map(availableProperty, gson, params.contents,
            occupancies, roomItemOccupancy)).filter(property -> property != null);*/
    return null;
  }

  public static class Params {
    public final List<AvailableProperty> availableProperties;
    public final Map<String, PropertyContent> contents;
    public final int roomCount;
    public final int adultCount;
    public final List<Child> children;

    public Params(
        List<AvailableProperty> availableProperties, Map<String, PropertyContent> contents,
        int roomCount, int adultCount,
        List<Child> children) {
      this.availableProperties = availableProperties;
      this.contents = contents;
      this.roomCount = roomCount;
      this.adultCount = adultCount;
      this.children = children;
    }
  }
}
