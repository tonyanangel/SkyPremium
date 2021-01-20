package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.notification.UpdateMappVersionRequest;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface SkyOneSignalSevices {


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.GET_DEVICES_INFO)
  Observable<OSPlayer> getDeviceInfo(@Path("player_id") String playerId,
                                     @Query("app_id") String appId);

}
