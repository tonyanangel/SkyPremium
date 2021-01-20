package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.data.network.request.AdminSignInRequest;
import com.skypremiuminternational.app.data.network.request.ForgotPasswordRequest;
import com.skypremiuminternational.app.data.network.request.PasswordHashRequest;
import com.skypremiuminternational.app.data.network.request.RenewalRequest;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.domain.models.Version;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public interface AuthService {

  @Headers("Content-Type: application/json")
  @POST(URL.SIGN_IN)
  Observable<ResponseBody> signIn(
      @Body SignInRequest signInRequest);

  @Headers("Content-Type: application/json")
  @PUT(URL.FORGOT_PASSWORD)
  Observable<ResponseBody> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.UPLOAD_AVATAR)
  Observable<UploadAvatarResponse> uploadAvatar(@Header("Authorization") String auth,
                                                @Body UploadAvatarRequest uploadAvatarRequest);

  @Headers("Content-Type: application/json")
  @POST(URL.ADMIN_SIGN_IN)
  Observable<ResponseBody> adminSignIn(@Body AdminSignInRequest adminSignInRequest);

  @Headers("Content-Type: application/json")
  @PUT(URL.UPDATE_PASSWORD)
  Observable<ResponseBody> updatePassword(@Header("Authorization") String auth,
                                          @Path("member_number") String member_number,
                                          @Body UpdatePasswordRequest updatePasswordRequest);

  @Headers("Content-Type: application/json")
  @POST(URL.PASSWORD_HASH)
  Observable<ResponseBody> passwordHash(@Header("Authorization") String auth,
                                        @Body PasswordHashRequest passwordHashRequest);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_APP_VERSION)
  Observable<Version> getAppVersion(@Header("Authorization") String clientToken);


  @Multipart
  @POST(URL.RENEWAL_TOKEN)
  Observable<ResponseBody> getTokenRenewal(@Header("Authorization") String auth,@PartMap() Map<String,String> params);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.CREATE_TOKEN)
  Observable<CrmTokenResponse> createToken(@Body CrmTokenRequest crmTokenRequest);



  @Headers("Content-Type: application/json")
  @GET(URL.CHECK_ADD_CARD_FIRST_TIME)
  Observable<Boolean> checkAddCardFirstTime(@Header("Authorization") String userToken);
}

