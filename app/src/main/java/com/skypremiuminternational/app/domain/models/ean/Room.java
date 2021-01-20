package com.skypremiuminternational.app.domain.models.ean;

import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by codigo on 23/4/18.
 */

@AutoValue
public abstract class Room implements Parcelable {

  @Nullable
  public abstract String minCheckInAge();

  public abstract String paymentOptionLink();

  public abstract String id();

  public abstract String image();

  public abstract String type();

  @Nullable
  public abstract String name();

  public abstract String occupancy();

  public abstract String detail();

  public abstract String cancellationPolicy();

  public abstract String nightlyPrice();

  public abstract CancelPenalty cancelPenalty();



  public abstract List<Amenity> amenities();

  public abstract List<String> priceCheckLinks();

  public abstract String checkInBeginTime();

  public abstract String checkInEndTime();

  public abstract String checkInInstructions();

  @Nullable
  public abstract String specialCheckInInstructions();

  public abstract String checkoutTime();

  public static Room create(String minCheckInAge, String paymentOptionLink, String id, String image,
                            String type, String name, String occupancy, String detail, String cancellationPolicy,
                            String nightlyPrice, CancelPenalty cancelPenalty, List<Amenity> amenities,
                            List<String> priceCheckLinks, String checkInBeginTime, String checkInEndTime,
                            String checkInInstructions, String specialCheckInInstructions, String checkoutTime) {
    return builder()
        .minCheckInAge(minCheckInAge)
        .paymentOptionLink(paymentOptionLink)
        .id(id)
        .image(image)
        .type(type)
        .name(name)
        .occupancy(occupancy)
        .detail(detail)
        .cancellationPolicy(cancellationPolicy)
        .nightlyPrice(nightlyPrice)
        .cancelPenalty(cancelPenalty)
        .amenities(amenities)
        .priceCheckLinks(priceCheckLinks)
        .checkInBeginTime(checkInBeginTime)
        .checkInEndTime(checkInEndTime)
        .checkInInstructions(checkInInstructions)
        .specialCheckInInstructions(specialCheckInInstructions)
        .checkoutTime(checkoutTime)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_Room.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder id(String id);

    public abstract Builder image(String image);

    public abstract Builder type(String type);

    public abstract Builder occupancy(String occupancy);

    public abstract Builder detail(String detail);

    public abstract Builder cancellationPolicy(String cancellationPolicy);

    public abstract Builder nightlyPrice(String nightlyPrice);

    public abstract Builder amenities(List<Amenity> amenities);

    public abstract Builder cancelPenalty(CancelPenalty cancelPenalty);

    public abstract Builder name(String name);

    public abstract Builder priceCheckLinks(List<String> priceCheckLinks);

    public abstract Builder checkInBeginTime(String checkInBeginTime);

    public abstract Builder checkInEndTime(String checkInEndTime);

    public abstract Builder checkInInstructions(String checkInInstructions);

    public abstract Builder checkoutTime(String checkoutTime);

    public abstract Builder paymentOptionLink(String paymentOptionLink);

    public abstract Builder minCheckInAge(String minCheckInAge);

    public abstract Builder specialCheckInInstructions(String specialCheckInInstructions);

    public abstract Room build();


  }
}
