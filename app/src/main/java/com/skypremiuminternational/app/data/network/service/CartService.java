package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.PlaceOrderRequest;
import com.skypremiuminternational.app.data.network.request.PlaceRenewalOrderWithPointRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.domain.models.cart.AddCartItemCountRequest;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;
import com.skypremiuminternational.app.domain.models.cart.RoyaltyResponse;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingRequest;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingResponse;
import com.skypremiuminternational.app.domain.models.cart.UpdateItemCountRequest;
import com.skypremiuminternational.app.domain.models.creditCard.SetDefaultCreditCardrequest;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by codigo on 9/2/18.
 */

public interface CartService {

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.CART)
  Observable<Object> createCart(@Header("Authorization") String auth);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.CART)
  Observable<ResponseBody> createCartResponseBody(@Header("Authorization") String auth);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.CART_ITEMS)
  Observable<AddCartItemCountRequest.Cart> addToCart(@Header("Authorization") String auth,
                                                     @Body AddCartItemCountRequest addCartItemCountRequest);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.CART_DETAIL)
  Observable<CartDetailResponse> getCartDetail(@Header("Authorization") String token);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.CART_DETAIL_BUY_NOW)
  Observable<CartDetailResponse> getCartDetailBuyNow(@Header("Authorization") String token);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @PUT(URL.CART_ITEM_COUNT_UPDATE)
  Observable<CartDetailItem> addCartItemCount(
      @Header("Authorization") String auth, @Path("item_id") String itemId,
      @Body UpdateItemCountRequest addCartRequest);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.CART_ITEMS)
  Observable<List<CartDetailItem>> getCartItems(@Header("Authorization") String auth);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.CART_ITEMS_BUY_NOW)
  Observable<List<CartDetailItem>> getCartItemsBuyNow(@Header("Authorization") String auth);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @DELETE(URL.REMOVE_SHOPPING_CART)
  Observable<Boolean> removeShoppingCart(@Header("Authorization") String userToken,
                                         @Path("cart_item_id") String cartItemId);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.ALL_CART_INFORMATION)
  Observable<CartAllInformationResponse> getAllCartInformation(
      @Header("Authorization") String userToken);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @GET(URL.ALL_CART_INFORMATION_BUY_NOW)
  Observable<CartAllInformationResponse> getAllCartInformationBuyNow(
      @Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache"})
  @GET(URL.CHECK_CART_LIMIT)
  Observable<CheckLimitResponse> checkIfCartHasReachedLimit(
      @Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache"})
  @PUT(URL.APPLY_PROMO_CODE)
  Observable<Boolean> applyPromoCode(@Header("Authorization") String userToken,
                                     @Path("coupon_code") String promoCode);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @DELETE(URL.DELETE_PROMO_CODE)
  Observable<Boolean> deletePromoCode(@Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.APPLY_ROYAL_POINT)
  Observable<RoyaltyResponse> applyRoyaltyPts(
      @Header("Authorization") String userToken,
      @Path("royalty_points") String points);

  @Headers({"Cache-Control: no-cache"})
  @DELETE(URL.DELETE_ROYALTY_POINT)
  Observable<RoyaltyResponse> deleteRoyaltyPoints(@Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.PLACE_ORDER)
  Observable<Object> placeOrder(@Header("Authorization") String userToken, @Body PlaceOrderRequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.PLACE_ORDER_BUY_NOW)
  Observable<Object> placeOrderBuyNow(@Header("Authorization") String userToken, @Body PlaceOrderRequest request, @Query("is_buynow") String is_buynow);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.ADD_CREDIT_CARDS)
  Observable<UpdateUserResponse> addCreditCards(
      @Header("Authorization") String clientToken, @Body AddCreditCardRequest addCreditCardRequest);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.ADD_CREDIT_CARDS_V2)
  Observable<UpdateUserResponse> addCreditCardsV2(
      @Header("Authorization") String clientToken, @Body AddCreditCardRequest addCreditCardRequest);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.UPDATE_CREDIT_CARDS)
  Observable<UpdateUserResponse> updateCreditCard(
      @Header("Authorization") String userToken,
      @Body UpdateCreditCardRequest updateCreditCardRequest);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.UPDATE_CREDIT_CARDS_V2)
  Observable<UpdateUserResponse> updateCreditCardV2(
      @Header("Authorization") String userToken,
      @Body UpdateCreditCardRequest updateCreditCardRequest);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @DELETE(URL.DELETE_CREDIT_CARD)
  Observable<UpdateUserResponse> deleteCreditCard(@Header("Authorization") String userToken, @Path("card_id") String cardId);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.SET_DEFAULT_CREDIT_CARD)
  Observable<UpdateUserResponse> setDefaultCreditCard(@Header("Authorization") String userToken, @Path("card_id") String cardId, @Body SetDefaultCreditCardrequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.SET_DEFAULT_CREDIT_CARD_V2)
  Observable<UpdateUserResponse> setDefaultCreditCardV2(@Header("Authorization") String userToken, @Path("card_id") String cardId, @Body SetDefaultCreditCardrequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.SET_SHIPPING_AND_BILLING)
  Observable<SetShippingAndBillingResponse> setShippingAndBillingAddress(
      @Header("Authorization") String userToken, @Body SetShippingAndBillingRequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.ADD_RENEWAL_TO_CART_WITH_CREDIT)
  Observable<Object> addRenewalToCartWithCredit(@Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @GET(URL.GET_RENEWAL_PRODUCT)
  Observable<ItemsItem> getRenewalProduct(@Header("Authorization") String clientToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.ADD_RENEWAL_TO_CART_WITH_POINTS)
  Observable<Object> addRenewalToCartWithPoints(@Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.PLACE_RENEWAL_ORDER)
  Observable<Object> placeRenewalOrderWithCredit(@Header("Authorization") String userToken, @Body PlaceOrderRequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @POST(URL.PLACE_RENEWAL_ORDER)
  Observable<Object> placeRenewalOrderWithPoints(@Header("Authorization") String userToken, @Body PlaceRenewalOrderWithPointRequest request);


  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.ADD_TO_BUY_NOW)
  Observable<AddCartItemCountRequest.Cart> addToBuyNowCart(@Header("Authorization") String auth,
                                                           @Body AddCartItemCountRequest addCartItemCountRequest);

  @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
  @POST(URL.UPDATE_BUY_NOW)
  Observable<ResponseBody> updateBuyNowCart(@Header("Authorization") String auth);
}
