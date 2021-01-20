package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface HomeCategoryService {

  @Headers("Content-Type: application/json")
  @GET(URL.GET_HOME_CATEGORIES)
  Observable<HomeCategoryResponse> getHomeCategories(@Header("Authorization") String auth);
}
