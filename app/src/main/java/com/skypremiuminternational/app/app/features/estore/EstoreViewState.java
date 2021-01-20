package com.skypremiuminternational.app.app.features.estore;

import com.google.auto.value.AutoValue;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;

/**
 * Created by johnsonmaung on 9/21/17.
 */
@AutoValue
public abstract class EstoreViewState {

  abstract CategoryDetailsResponse categoryDetails();

  abstract CategoryResponse category();

  abstract FeatureProduct featureProduct();

  public static EstoreViewState create(CategoryDetailsResponse categoryDetails,
                                       CategoryResponse category, FeatureProduct featureProduct) {
    return builder().categoryDetails(categoryDetails)
        .category(category)
        .featureProduct(featureProduct)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_EstoreViewState.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder categoryDetails(CategoryDetailsResponse categoryDetails);

    public abstract Builder category(CategoryResponse category);

    public abstract Builder featureProduct(FeatureProduct featureProduct);

    public abstract EstoreViewState build();
  }
}
