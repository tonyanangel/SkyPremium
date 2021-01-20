package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;

import java.util.Map;

import autovalue.shaded.com.google$.common.collect.$AbstractIterator;
import okhttp3.ResponseBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

public interface RatingCommentService {

  @Multipart
  @POST(URL.ADD_RATING)
  Observable<RatingResult> addRatingCommment(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);

  @Multipart
  @POST(URL.GET_REVIEW_DETAIL)
  Observable<ReviewDetailResponse> getReviewDetail(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);

  @Multipart
  @POST(URL.GET_PRODUCT_REVIEW_LIST)
  Observable<ProductReviewResponse> getListReviewByProduct(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);

  @Multipart
  @POST(URL.EDIT_REVIEW)
  Observable<RatingResult> updateReview(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);

  @POST(URL.GET_RATING_OPTION)
  Observable<RatingOption> getRatingOption(@Header("Authorization") String userToken);

  @Multipart
  @POST(URL.GET_RATING_SUMMARY)
  Observable<ResponseBody> getRatingSummaryByProduct(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);

  @Multipart
  @POST(URL.DELETE_REVIEW)
  Observable<RatingResult> deleteReview(@Header("Authorization") String userToken, @PartMap() Map<String,String> params);
}
