package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.category.FilterListResponse;
import com.skypremiuminternational.app.domain.models.search.SearchHomeResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public interface SearchProductService {


  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_PRODUCT)
  Observable<SearchProductResponse> searchProduct(@Header("Authorization") String auth,
                                                  @Query("searchCriteria[filterGroups][0][filters][0][value]") String keywordName,
                                                  @Query("searchCriteria[filterGroups][0][filters][1][value]") String keywordDescription,
                                                  @Query("searchCriteria[sortOrders][0][field]") String field,
                                                  @Query("searchCriteria[sortOrders][0][direction]") String direction,
                                                  @Query("searchCriteria[filterGroups][1][filters][0][value]") String category_id);

  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_PRODUCT)
  Observable<SearchProductResponse> searchProductbyPrice(@Header("Authorization") String auth,
                                                  @Query("searchCriteria[filterGroups][0][filters][0][value]") String keywordName,
                                                  @Query("searchCriteria[filterGroups][0][filters][1][value]") String keywordDescription,
                                                  @Query("searchCriteria[sortOrders][0][field]") String field,
                                                  @Query("searchCriteria[sortOrders][0][direction]") String direction,
                                                  @Query("searchCriteria[filterGroups][1][filters][0][value]") String category_id,
                                                  @Query("searchCriteria[sortOrders][2][field]") String field2,
                                                  @Query("searchCriteria[sortOrders][2][direction]") String direction2);

  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_PRODUCT)
  Observable<SearchProductResponse> searchProductByPopularity(@Header("Authorization") String auth,
                                                              @Query("searchCriteria[filterGroups][0][filters][0][value]") String keywordName,
                                                              @Query("searchCriteria[filterGroups][0][filters][1][value]") String keywordDescription,
                                                              @Query("searchCriteria[sortOrders][0][field]") String popField,
                                                              @Query("searchCriteria[sortOrders][0][direction]") String popDirection,
                                                              @Query("searchCriteria[sortOrders][1][field]") String defaultField,
                                                              @Query("searchCriteria[sortOrders][1][direction]") String defaultDirection,
                                                              @Query("searchCriteria[filterGroups][1][filters][0][value]") String category_id);

  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_ALL)
  Observable<SearchProductResponse> searchAll(@Header("Authorization") String auth,
                                              @Query("searchCriteria[filterGroups][0][filters][0][value]") String keywordName,
                                              @Query("searchCriteria[filterGroups][1][filters][0][value]") String keywordDescription,
                                              @Query("searchCriteria[sortOrders][0][field]") String field,
                                              @Query("searchCriteria[sortOrders][0][direction]") String direction);

  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_HOME)
  Observable<SearchHomeResponse> searchHome(
      @Query("searchCriteria[filterGroups][0][filters][0][value]") String keyword,
      @Query("searchCriteria[sortOrders][0][field]") String field,
      @Query("searchCriteria[sortOrders][0][direction]") String direction);

  @Headers("Content-Type: application/json")
  @GET(URL.SEARCH_HOME_PRODUCTS)
  Observable<SearchProductResponse> searchHomeProducts(@Header("Authorization") String auth,
                                                       @Query("searchCriteria[filterGroups][0][filters][0][value]") String IDList,
                                                       @Query("searchCriteria[sortOrders][0][field]") String field,
                                                       @Query("searchCriteria[sortOrders][0][direction]") String direction);
  
  
  @Headers("Content-Type: application/json")
  @GET
  Observable<ProductListResponse> getProductFilter(@Header("Authorization") String auth,
                                                   @Url String query );
}

