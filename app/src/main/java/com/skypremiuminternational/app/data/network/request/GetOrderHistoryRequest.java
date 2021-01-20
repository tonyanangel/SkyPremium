package com.skypremiuminternational.app.data.network.request;

/**
 * Created by aeindraaung on 2/21/18.
 */

public class GetOrderHistoryRequest {
  public static final int REQUEST_TYPE_ALL = 1;
  public static final int REQUEST_TYPE_WITH_FILTER = 2;

  public final String selectedCategory;
  public final String selectedSorting;
  public final int requestType;

  public GetOrderHistoryRequest(String selectedCategory, String selectedSorting, int requestType) {
    this.selectedCategory = selectedCategory;
    this.selectedSorting = selectedSorting;
    this.requestType = requestType;
  }

  public static GetOrderHistoryRequest getAll(String filterSorting) {
    return new GetOrderHistoryRequest(null, filterSorting, REQUEST_TYPE_ALL);
  }

  public static GetOrderHistoryRequest getWithFILTER(String selectedCategory, String selectedSorting) {
    return new GetOrderHistoryRequest(selectedCategory, selectedSorting, REQUEST_TYPE_WITH_FILTER);
  }
}
