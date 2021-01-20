package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.details.RecommendItems;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public interface DetailsService {

  public final String category_id ="";


  @Headers({"Content-Type: application/json", "cache-control:no-cache"})
  @GET(URL.DETAILS)
  Observable<DetailsItem> getDetails(@Header("Authorization") String auth,
                                     @Path("sku") String sku);

  @Headers({"Content-Type: application/json", "cache-control:no-cache"})
  @GET(URL.RECOMMENDATIONS)
  Observable<RecommendItems> getRecommendations(@Header("Authorization") String auth,
                                                @Path("sku") String sku,@Path("category_id") String category_id);


  /*@FormUrlEncoded @POST(URL.LOGIN_SUPERVISOR) Observable<Supervisor> loginSupervisor(
      @Query("auth_token") String auth_token, @Field("supervisor_id") String supervisor_id,
      @Field("password") String password);*/

  @Headers({"Content-Type: application/json", "cache-control:no-cache"})
  @GET(URL.GET_DETAIL_CATEGORIE)
  Observable<DetailsItem> getDetailCategory(@Header("Authorization") String auth,
                                            @Path("category_id") String category_id);
}

