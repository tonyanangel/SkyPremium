package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.Version;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import rx.Observable;

public interface PopupService {

  @Headers("Content-Type: application/json")
  @GET(URL.GET_FIRST_TIME_POPUP)
  Observable<FirstTimePopup> getFirstTimePopup(@Header("Authorization") String userToken);

  @Headers("Content-Type: application/json")
  @GET(URL.SET_FIRST_TIME)
  Observable<ResponseBody> setFirstTime(@Header("Authorization") String userToken);
}
