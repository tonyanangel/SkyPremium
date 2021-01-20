package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.cart.AddCartItemCountRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MySkyDollarServices {



  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.CREATE_TOKEN)
  Observable<CrmTokenResponse> createToken(@Body CrmTokenRequest crmTokenRequest);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.GET_TRANSACTION_SKY_DOLLAR)
  Observable<SkyDollarHistoryResponse> getSkyDollarHistory(@Header("oauth-token") String oauth ,
                                                    @Path("member_number") String memberNumber,
                                                    @Query("current_page") String currentPage,
                                                    @Query("limit") String limit);

}
