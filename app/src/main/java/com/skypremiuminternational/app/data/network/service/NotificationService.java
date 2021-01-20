package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;
import com.skypremiuminternational.app.domain.models.notification.OneSignalDeviceDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.UpdateMappVersionRequest;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface NotificationService {


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.SEND_NOTIFICATION)
  Observable<ResponseBody> sendNotification(@Header("oauth-token") String oauth,
                                            @Body NotificationRequest notification);


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.GET_NOTIFICATION)
  Observable<NotificationResponse> getNotification(@Header("oauth-token") String oauth,
                                                   @Query("member_id") String member_id,
                                                   @Query("device_id") String device_id);

  @Headers({"Content-Type: application/json"})
  @HTTP(method = "DELETE", path = URL.DELETE_NOTIFICATION, hasBody = true)
  Observable<ResponseBody> deleteNotification(@Header("oauth-token") String oauth,
                                                      @Body NotificationDeleteRequest notification);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.UPDATE_READ_NOTIFICATION)
  Observable<ResponseBody> updateReadNotification(@Header("oauth-token") String oauth,
                                                          @Body NotificationUpdateRequest notification);


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.UPDATE_SETTING_NOTIFICATION)
  Observable<ResponseBody> updateSettingNotification(@Header("oauth-token") String oauth,
                                                          @Body SettingNotificationRequest notification);


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.GET_SETTING_NOTIFICATION)
  Observable<SettingNotificationResponse> getNotificationSetting(@Header("oauth-token") String oauth,
                                                                 @Query("member_id") String memberId);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.CHANGE_FIRST_TIME_LOGIN)
  Observable<ResponseBody> changeFirstTimeLogin(@Header("oauth-token") String oauth,
                                                              @Body FirstTimeToTrueRequest request);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.UPDATE_MAPP_VERSION)
  Observable<ResponseBody> updateMappVersion(@Header("oauth-token") String oauth,
                                                              @Body UpdateMappVersionRequest request);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.CHECK_FIRST_LOGIN)
  Observable<CheckLoginResponse> checkFirstLogin(@Header("oauth-token") String oauth,
                                                 @Query("member_id") String memberId);



  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.UPDATE_OS_DATA_TO_CRM)
  Observable<ResponseBody> updateOSDataToCRM(@Header("oauth-token") String oauth,
                                            @Body OneSignalDeviceDataRequest request);


}
