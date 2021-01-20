package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import rx.Observable;

public interface DemoServices {


    @Headers("Content-Type: application/json")
    @GET
    Observable<DemoResponse> getProduct(@Header("Authorization") String auth,
                                             @Url String path);

    @Headers("Content-Type: application/json")
    @GET(URL.DEMO_PARAM)
    Observable<DemoRes> getProductTwo(@Header("Authorization") String auth);

}
