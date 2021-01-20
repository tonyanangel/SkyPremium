package com.skypremiuminternational.app.domain.models.ean;

import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Amenity implements Parcelable {

  public abstract String description();

  public abstract int id();

  public abstract boolean isCancellationPolicy();

  @Nullable
  public abstract CancelPenalty cancelPenalty();

  public static Amenity create(String description, int id, boolean isCancellationPolicy,
                               CancelPenalty cancelPenalty) {
    return builder()
        .description(description)
        .id(id)
        .isCancellationPolicy(isCancellationPolicy)
        .cancelPenalty(cancelPenalty)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_Amenity.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder description(String description);

    public abstract Builder id(int id);

    public abstract Builder isCancellationPolicy(boolean isCancellationPolicy);

    public abstract Builder cancelPenalty(CancelPenalty cancelPenalty);

    public abstract Amenity build();
  }
}
