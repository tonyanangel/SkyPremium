package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.hunry_go_where.CancelReservationResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;
import com.skypremiuminternational.app.domain.models.hunry_go_where.RestaurantMessageResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface HGWService {


  @Headers("Content-Type: application/json")
  @GET(URL.GET_OUTLET_BY_PRODUCT)
  Observable<List<OutletItem>> getOutletByProductID(@Header("Authorization") String userToken,
                                                    @Path("product_id") String productID);


  @Headers("Content-Type: application/json")
  @GET(URL.GET_RESERVATION_TIME)
  Observable<ReservationTimeResponse> getReservationTime(
                          @Path("date") String date,
                          @Path("restaurantId") String restaurantId,
                          @Path("partnerCode") String partnerCode,
                          @Path("partnerAuthCode") String partnerAuthCode
                          );


  @Headers("Content-Type: application/json")
  @GET(URL.GET_RESTAURANT_MSG)
  Observable<RestaurantMessageResponse> getRestaurantMsg(
                          @Path("restaurantId") String restaurantId
                          );


  @Multipart
  @POST(URL.SEND_CREATE_RESERVATION)
  Observable<ReservationResultResponse> sendCreateReservation(@Header("Authorization") String userToken,
                                                              @PartMap() Map<String,String> params);

  @Multipart
  @POST(URL.SEND_EDIT_RESERVATION)
  Observable<ReservationResultResponse> sendEditReservation(@Header("Authorization") String userToken,
                                                              @PartMap() Map<String,String> params,
                                                              @Path("reservation_id") String reservationId,
                                                              @Path("verificationKey") String verificationKey);
  @Headers("Content-Type: application/json")
  @DELETE(URL.CANCEL_RESERVATION)
  Observable<CancelReservationResponse> cancelReservation(@Header("Authorization") String userToken,
                                                          @Path("reservation_id") String reservationId,
                                                          @Path("verificationKey") String verificationKey);



  @Headers("Content-Type: application/json")
  @GET(URL.GET_HISTORY)
  Observable<ReserveHistoryRespone> getHistoryAll(@Header("Authorization") String userToken,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][field]") String membership_id,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][value]") String memberNumber,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][condition_type]") String condition_type,

                                                  @Query("searchCriteria[sortOrders][3][direction]") String director,
                                                  @Query("searchCriteria[sortOrders][3][field]") String reservation_date

      /*,@Query("searchCriteria[current_page]") String current_page,
                                                  @Query("searchCriteria[page_size]") String page_size*/);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_HISTORY)
  Observable<ReserveHistoryRespone> getHistoryFilter(@Header("Authorization") String userToken,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][field]") String membership_id,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][value]") String memberNumber,
                                                  @Query("searchCriteria[filter_groups][0][filters][0][condition_type]") String condition_type,

                                                  @Query("searchCriteria[sortOrders][3][direction]") String director,
                                                  @Query("searchCriteria[sortOrders][3][field]") String reservation_date,

                                                  @Query("searchCriteria[filter_groups][2][filters][0][field]") String sortField,
                                                  @Query("searchCriteria[filter_groups][2][filters][0][value]") String sortValue,
                                                  @Query("searchCriteria[filter_groups][2][filters][0][condition_type]") String sortCondition
                                                  /*,@Query("searchCriteria[current_page]") String current_page,
                                                  @Query("searchCriteria[page_size]") String page_size*/);
  @Headers("Content-Type: application/json")
  @GET(URL.GET_DETAIL_RESERVATION_BOOKING)
  Observable<ReserveHistoryItem> getDetailReservationBooking(@Header("Authorization") String userToken,
                                                         @Path("booking_id") String bookingId);



}
