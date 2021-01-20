package com.skypremiuminternational.app.data.network.service;

import com.skypremiuminternational.app.data.model.billingaddress.AddBillingAddressRequest;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddressesResponse;
import com.skypremiuminternational.app.data.model.billingaddress.EditBillingAddressRequest;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.InviteFriendDescription;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sandi on 11/12/17.
 */

public interface UserDetailService {

  @Headers("Content-Type: application/json")
  @GET(URL.USER_DETAIL)
  Observable<UserDetailResponse> getUserDetail(@Header("Authorization") String auth);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_CREDIT_CARDS_INFO)
  Observable<CardInfomationResponse> getCreditCardInfo(@Header("Authorization") String userToken, @Path("card_id") String cardId);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_CREDIT_CARDS)
  Observable<List<CreditCardResponse>> getCreditCards(@Header("Authorization") String userToken);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_CREDIT_CARDS_V2)
  Observable<List<CreditCardResponse>> getCreditCardsV2(@Header("Authorization") String userToken);

  @Headers("Content-Type: application/json")
  @GET(URL.GET_INVITE_FRIEND_DESCRIPTION)
  Observable<InviteFriendDescription> getInviteDescription(
      @Header("Authorization") String clientToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @GET(URL.GET_BILLING_ADDRESSES)
  Observable<BillingAddressesResponse> getBillingAddresses(
      @Header("Authorization") String userToken);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.ADD_BILLING_ADDRESSES)
  Observable<BillingAddress> addBillingAddress(@Header("Authorization") String userToken,
                                               @Body AddBillingAddressRequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.EDIT_BILLING_ADDRESSES)
  Observable<BillingAddress> editBillingAddress(@Header("Authorization") String userToken,
                                                @Body EditBillingAddressRequest request);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @DELETE(URL.DELETE_BILLING_ADDRESS)
  Observable<Object> deleteBillingAddress(@Header("Authorization") String userToken,
                                          @Path("address_id") String addressId);

  @Headers({"Cache-Control: no-cache", "Content-Type: application/json"})
  @PUT(URL.BILLING_ADDRESS_PAYMENT)
  Observable<BillingAddressPayment.BillingAddressPaymentRespond> requestBillingAddressPayment(
          @Header("Authorization") String userToken, @Body RequestBody params);
}
