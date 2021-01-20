package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public interface WellnessService {

  @Headers("Content-Type: application/json")
  @GET(URL.WELLNESS)
  Observable<ProductListResponse> getWellness(@Header("Authorization") String auth,
                                              @Query("searchCriteria[filterGroups][0][filters][0][value]") String category_id,
                                              @Query("searchCriteria[sortOrders][0][field]") String field,
                                              @Query("searchCriteria[sortOrders][0][direction]") String direction);

  @Headers("Content-Type: application/json")
  @GET(URL.WELLNESS)
  Observable<ProductListResponse> getWellnessByPopularity(@Header("Authorization") String auth,
                                                          @Query("searchCriteria[filterGroups][0][filters][0][value]") String category_id,
                                                          @Query("searchCriteria[sortOrders][0][field]") String popField,
                                                          @Query("searchCriteria[sortOrders][0][direction]") String popDirection,
                                                          @Query("searchCriteria[sortOrders][1][field]") String defaultField,
                                                          @Query("searchCriteria[sortOrders][1][direction]") String defaultDirection);

  @Headers("Content-Type: application/json")
  @GET(URL.FEATURE_WELLNESS)
  Observable<FeatureProduct> getFeatureWellness(@Header("Authorization") String auth,
                                                @Path("categoryID") String category_id);

  /*@FormUrlEncoded @POST(URL.LOGIN_SUPERVISOR) Observable<Supervisor> loginSupervisor(
      @Query("auth_token") String auth_token, @Field("supervisor_id") String supervisor_id,
      @Field("password") String password);*/
}

