package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface CategoryService {

  @Headers("Content-Type: application/json")
  @GET(URL.GET_CATEGORIES)
  Observable<CategoryResponse> getCategories(@Header("Authorization") String auth);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_CATEGORY_DETAILS)
  Observable<CategoryDetailsResponse> getCategoryDetails(@Header("Authorization") String auth,
                                                         @Path("categoryID") String category_id);
  @Headers("Content-Type: application/json")
  @GET
  Observable<ResponseBody> getTreeCategory(@Header("Authorization") String auth,
                                           @Url String path);

  @Headers("Content-Type: application/json")
  @GET
  Observable<ResponseBody> getTreeCategoryMini(@Header("Authorization") String auth,
                                           @Url String path);


  @Headers("Content-Type: application/json")
  @GET
  Observable<ResponseBody> getTreeCategoryByCheckList(@Header("Authorization") String auth,
                                           @Url String path);
}
