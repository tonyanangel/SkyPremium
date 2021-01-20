package com.skypremiuminternational.app.data.network.request;

/**
 * Created by codigo on 25/1/18.
 */

public class GetFavouriteRequest {
  public static final int REQUEST_TYPE_ALL = 1;
  public static final int REQUEST_TYPE_WITHOUT_CATEGORY = 3;
  public static final int REQUEST_TYPE_WITH_CATEGORYT = 2;

  public final String categoryId;
  public final String partnerType;
  public final String sortingField;
  public final String sortingDirection;
  public final int requestType;

  private GetFavouriteRequest(String categoryId, String partnerType, String sortingField,
                              String sortingDirection, int requestType) {
    this.categoryId = categoryId;
    this.partnerType = partnerType;
    this.sortingField = sortingField;
    this.sortingDirection = sortingDirection;
    this.requestType = requestType;
  }

  public static GetFavouriteRequest getAll() {
    return new GetFavouriteRequest(null, null, null, null, REQUEST_TYPE_ALL);
  }

  public static GetFavouriteRequest getWithCategory(String categoryId, String partnerType, String sortingField, String sortingDirection) {
    return new GetFavouriteRequest(categoryId, partnerType, sortingField, sortingDirection, REQUEST_TYPE_WITH_CATEGORYT);
  }

  public static GetFavouriteRequest getWithoutCategory(String partnerType, String sortingField, String sortingDirection) {
    return new GetFavouriteRequest(null, partnerType, sortingField, sortingDirection, REQUEST_TYPE_WITHOUT_CATEGORY);
  }
}
