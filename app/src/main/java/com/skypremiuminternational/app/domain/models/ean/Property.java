package com.skypremiuminternational.app.domain.models.ean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by codigo on 19/4/18.
 */

@AutoValue
public abstract class Property implements Parcelable {

  public abstract int roomCount();

  public abstract String id();

  public abstract String name();

  public abstract String type();

  public abstract String city();

  public abstract Double lat();

  public abstract Double lng();

  public abstract String address();

  public abstract String description();

  public abstract List<Room> rooms();

  public abstract List<String> carouselImages();

  public abstract String image();

  public abstract String lowestNightlyPrice();

  public abstract boolean isEurope();

  public static Property create(int roomCount, String id, String name, String type, String city,
                                Double lat, Double lng, String address, String description, List<Room> rooms,
                                List<String> carouselImages, String image, String lowestNightlyPrice, boolean isEurope) {
    return builder()
        .roomCount(roomCount)
        .id(id)
        .name(name)
        .type(type)
        .city(city)
        .lat(lat)
        .lng(lng)
        .address(address)
        .description(description)
        .rooms(rooms)
        .carouselImages(carouselImages)
        .image(image)
        .lowestNightlyPrice(lowestNightlyPrice)
        .isEurope(isEurope)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_Property.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder name(String name);

    public abstract Builder type(String type);

    public abstract Builder city(String city);

    public abstract Builder lat(Double lat);

    public abstract Builder lng(Double lng);

    public abstract Builder address(String address);

    public abstract Builder description(String description);

    public abstract Builder rooms(List<Room> rooms);

    public abstract Builder carouselImages(List<String> carouselImages);

    public abstract Builder image(String image);

    public abstract Builder id(String id);

    public abstract Builder lowestNightlyPrice(String lowestNightlyPrice);

    public abstract Builder isEurope(boolean isEurope);

    public abstract Builder roomCount(int roomCount);

    public abstract Property build();
  }
}
