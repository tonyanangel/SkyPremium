package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface UserUpdateService {

  @Headers("Content-Type: application/json")
  @PUT(URL.USER_UPDATE)
  Observable<UserDetailResponse> getUserDetail(@Header("Authorization") String auth,
                                               @Path("member_number") String member_number, @Body UpdateUserRequest updateUserRequest);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @DELETE(URL.DELETE_ADDRESS)
  Observable<Boolean> deleteAddress(
      @Header("Authorization") String auth, @Path("address_id") Integer addressId);

  @Headers("Content-Type: application/json")
  @PUT(URL.USER_UPDATE)
  Observable<UserDetailResponse> getUserDetail(@Header("Authorization") String auth,
                                               @Path("member_number") String member_number, @Body UpdateUserDeliveryRequest updateUserRequest);
}
