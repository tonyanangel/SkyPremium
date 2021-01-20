package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.myOrder.ExtraOrderDetail;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aeindraaung on 2/9/18.
 */

public interface OrderHistoryService {
  @Headers("Content-Type: application/json")
  @GET(URL.ORDER_HISTORY)
  Observable<MyOrderResponse> getOrderHistory(@Header("Authorization") String auth,
                                              @Query("searchCriteria[filterGroups][0][filters][0][value]") String email,
                                              @Query("searchCriteria[sortOrders][0][direction]") String selectedSorting);

  @Headers("Content-Type: application/json")
  @GET(URL.ORDERHISTORY_WITH_CATEGORY)
  Observable<MyOrderResponse> getOrderHistoryWithFilter(@Header("Authorization") String auth,
                                                        @Query("searchCriteria[filterGroups][0][filters][0][value]") String email,
                                                        @Query("searchCriteria[filterGroups][1][filters][0][value]") String selectedCategory,
                                                        @Query("searchCriteria[sortOrders][0][direction]") String selectedSorting,
                                                        @Query("searchCriteria[sortOrders][0][field]") String field);

  @Headers("Content-Type: application/json")
  @GET(URL.ORDER_DETAIL)
  Observable<OrderDetailResponse> getOrderDetail(@Header("Authorization") String clientToken,
                                                 @Path("order_id") Integer orderId);

  @Headers("Cache-Control: no-cache")
  @GET(URL.ORDER_HISTORY_EXTRA)
  Observable<ExtraOrderDetail> getOrderHistoryExtra(@Header("Authorization") String clientToken,
                                                    @Path("order_id") Integer orderId);
}
