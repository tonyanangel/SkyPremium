package com.skypremiuminternational.app.data;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.ean.availability.AvailableProperty;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.ItineraryResponse;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.AdminSignInRequest;
import com.skypremiuminternational.app.data.network.request.CaregoryDetailsRequest;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.ForgotPasswordRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.GetOrderHistoryRequest;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.data.network.request.PasswordHashRequest;
import com.skypremiuminternational.app.data.network.request.ProductFilterSortRequest;
import com.skypremiuminternational.app.data.network.request.RenewalRequest;
import com.skypremiuminternational.app.data.network.request.SearchProductRequest;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrder;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrderBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceRenewalOrder;
import com.skypremiuminternational.app.domain.interactor.ean.BookRoom;
import com.skypremiuminternational.app.domain.interactor.ean.CheckPrice;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetail;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetailValue;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingHistories;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.AddBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.DeleteBillingAddress;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.EditBillingAddress;
import com.skypremiuminternational.app.domain.models.InviteFriendDescription;
import com.skypremiuminternational.app.domain.models.Version;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.cart.AddCartItemCountRequest;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;
import com.skypremiuminternational.app.domain.models.cart.RoyaltyResponse;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingRequest;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingResponse;
import com.skypremiuminternational.app.domain.models.cart.UpdateItemCountRequest;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.FilterListResponse;
import com.skypremiuminternational.app.domain.models.category.FilterResultResponse;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.details.RecommendItems;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;
import com.skypremiuminternational.app.domain.models.faq.FaqResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.CancelReservationResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryRespone;
import com.skypremiuminternational.app.domain.models.hunry_go_where.RestaurantMessageResponse;
import com.skypremiuminternational.app.domain.models.myOrder.ExtraOrderDetail;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;
import com.skypremiuminternational.app.domain.models.notification.OneSignalDeviceDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.UpdateMappVersionRequest;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.search.SearchHomeResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by johnsonmaung on 9/26/17.
 * <p>
 * Task: To include the AWS Access Token and Keys on Cloudsearch APIs, add the variable "key"
 * and "secret"
 * Author: Levister Gutierrez
 * Included: searchProperties & getSuggestions
 * Date: 15 Apr 2019
 */

public interface NetworkManager {

  Observable<Object> getCartId(String auth);

  Observable<ResponseBody> createCartResponseBody(String auth);

  Observable<AddCartItemCountRequest.Cart> addToCart(
      AddCartItemCountRequest addCartItemCountRequest, String auth);

  Observable<AddCartItemCountRequest.Cart> addToBuyNow(
      AddCartItemCountRequest addCartItemCountRequest, String auth);

  Observable<ResponseBody> updateBuyNow(String auth);

  Observable<List<CartDetailItem>> getCartItems(String token);

  Observable<List<CartDetailItem>> getCartItemsBuyNow(String token);

  Observable<ResponseBody> signIn(SignInRequest loginRequest);

  Observable<ResponseBody> adminSignIn(AdminSignInRequest adminSignInRequest);

  Observable<ResponseBody> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

  Observable<UploadAvatarResponse> uploadAvatar(String token,
                                                UploadAvatarRequest uploadAvatarRequest);

  Observable<ProductListResponse> getShopping(String auth, String category_id, String field,
                                              String direction);

  Observable<FeatureProduct> getFeatureShopping(String auth, String category_id);

  Observable<ProductListResponse> getWineAndDine(String auth, String category_id, String field,
                                                 String direction);

  Observable<FeatureProduct> getFeatureWineAndDine(String auth, String category_id);

  Observable<ProductListResponse> getWellness(String auth, String category_id, String field,
                                              String direction);

  Observable<FeatureProduct> getFeatureWellness(String auth, String category_id);

  Observable<ProductListResponse> getTravel(String auth, String category_id, String field,
                                            String direction);

  Observable<FeatureProduct> getFeatureTravel(String auth, String category_id);

  Observable<DetailsItem> getDetails(String token, DetailsRequest detailsRequest);

  Observable<RecommendItems> getRecommendations(String token, DetailsRequest detailsRequest);

  Observable<UserDetailResponse> getUserDetail(String token);

  Observable<CardInfomationResponse> getCreditCardInfo(String token, String cardId);

  Observable<List<FavouriteListResponse>> getFavourites(String token, GetFavouriteRequest request);

  Observable<CategoryResponse> getCategories(String token);

  Observable<ResponseBody> getTreeCategory(String token, String request);

  Observable<MyOrderResponse> getOrderHistory(String token, String email,
                                              GetOrderHistoryRequest request);

  Observable<CartDetailResponse> getCartDetail(String token);

  Observable<CartDetailResponse> getCartDetailBuyNow(String token);

  Observable<CategoryDetailsResponse> getCategoryDetails(String token,
                                                         CaregoryDetailsRequest category_id);

  Observable<HomeCategoryResponse> getHomeCategories(String token);

  Observable<FaqResponse> getFaqs(String token);

  Observable<List<CountryCode>> getCountryCodes();

  Observable<List<CountryCodeCC>> getCountryCodeCc(String auth);

  Observable<PhoneCode> getPhoneCodes();

  Observable<List<Nationality>> getNationalities();

  Observable<ResponseBody> updatePassword(String token, String member_number,
                                          UpdatePasswordRequest updatePasswordRequest);

  Observable<ResponseBody> getPasswordHash(String token, PasswordHashRequest passwordHashRequest);

  Observable<UserDetailResponse> updateUserDetail(String token, String member_number,
                                                  UpdateUserRequest updateUserRequest);

  /*20201707 - WIKI Viet Nguyen - fix bug set default delivery address*/
  Observable<UserDetailResponse> updateUserDetail(String token, String member_number,
                                                  UpdateUserDeliveryRequest updateUserRequest);


  Observable<SearchProductResponse> searchProduct(String token, SearchProductRequest request);

  Observable<SearchProductResponse> searchAll(String token, SearchProductRequest request);

  Observable<SearchHomeResponse> searchHome(String token, SearchProductRequest request);

  Observable<SearchProductResponse> searchHomeProducts(String token, SearchProductRequest request);

  Observable addToFavourite(String token, String productId);

  Observable<FavouriteResponse> removeFromFavourite(String token, String wishListItemId);

  Observable<CartDetailItem> addCartDetailItemCount(String userToken, String itemId,
                                                    UpdateItemCountRequest addCartRequest);

  Observable<Boolean> removeShoppingCart(String userToken, String cartItemId);

  Observable<CartAllInformationResponse> getAllCartInformation(String userToken);

  Observable<CartAllInformationResponse> getAllCartInformationBuyNow(String userToken);

  Observable<Boolean> deleteAddress(String clientToken, Integer addressId);

  Observable<CheckLimitResponse> checkIfCartHasReachedLimit(String userToken);

  Observable<Boolean> applyPromoCode(String userToken, String promoCode);

  Observable<Boolean> deletePromoCode(String userToken);

  Observable<RoyaltyResponse> applyRoyaltyPts(String userToken, String points);

  Observable<RoyaltyResponse> deleteRoyaltyPoints(String userToken);

  Observable<List<CreditCardResponse>> getCreditCards(String userToken);

  Observable<List<CreditCardResponse>> getCreditCardsV2(String userToken);

  Observable<String> placeOrder(String userToken, PlaceOrder.Params params);

  Observable<String> placeOrderBuyNow(String userToken, PlaceOrderBuyNow.Params params);

  Observable<OrderDetailResponse> getOrderDetail(String userToken, Integer orderId);

  Observable<ProductListResponse> getFilterResult(String userToken, String orderId);

  Observable<ProductListResponse> getFilterResultSort(String userToken, ProductFilterSortRequest request);

  Observable<UpdateUserResponse> addCreditCards(String clientToken,
                                                AddCreditCardRequest addCreditCardRequest);

  Observable<UpdateUserResponse> addCreditCardsV2(String clientToken,
                                                AddCreditCardRequest addCreditCardRequest);

  Observable<UpdateUserResponse> updateCreditCard(String userToken,
                                                  UpdateCreditCardRequest updateCreditCardRequest);

  Observable<UpdateUserResponse> updateCreditCardV2(String userToken,
                                                  UpdateCreditCardRequest updateCreditCardRequest);

  Observable<UpdateUserResponse> deleteCreditCard(String userToken, String cardId);

  Observable<UpdateUserResponse> setDefaultCreditCard(String userToken, String cardId);

  Observable<UpdateUserResponse> setDefaultCreditCardV2(String userToken, String cardId);

  Observable<SetShippingAndBillingResponse> setShippingAndBillingAddress(String userToken,
                                                                         SetShippingAndBillingRequest request);

  Observable<ExtraOrderDetail> getExtraOrderDetail(String clientToken, int orderId);

  Observable<Boolean> addRenewalToCartWithCredit(String userToken);

  Observable<Version> getAppVersion(String clientToken);

  Observable<ItemsItem> getRenewalProduct(String clientToken);

  Observable<Boolean> addRenewalToCartWithPoints(String userToken);

  Observable<String> placeRenewalOrder(String userToken, PlaceRenewalOrder.Params params);

  Observable<List<AvailableProperty>> getAvailableProperties(List<String> propertyIds,
                                                             String arrival, String departure,
                                                             String countryCode, List<String> occupancies,
                                                             String sessionId, String ipAddress);

  Observable<Map<String, PropertyContent>> getPropertyContents(List<String> availablePropertyIds,
                                                               String sessionId, String ipAddress);

  Observable<PriceCheckResult> checkPrice(CheckPrice.Params params, String sessionId,
                                          String ipAddress);

  Observable<List<String>> searchProperties(String query, String token, String secret);

  Observable<List<Suggestion>> getSuggestions(String keyword, String token, String secret);

  Observable<List<BookingHistory>> getBookingHistories(String token,
                                                       GetBookingHistories.Params params);

  Observable<Booking> getBookingDetail(GetBookingDetail.Params params);

  Observable<Booking> getBookingDetailValue(GetBookingDetailValue.Params params);

  Observable<ItineraryResponse> getItinerary(String endpoint, String sessionId,
                                             String ipAddress);

  Observable<List<CardOption>> getPaymentOptions(String paymentOptionLink,
                                                 String sessionId, String ipAddress);

  Observable<BookingDetail> bookRoom(String userToken, BookRoom.Params params,
                                     String sessionId, String ipAddress);

  Observable<Boolean> cancelBooking(String userToken, String bookingId);

  Observable<List<ISOCountry>> getISOCountryCodes();

  Observable<InviteFriendDescription> getInviteDescription(String clientToken);

  Observable<String> getIpAddress();

  Observable<List<BillingAddress>> getBillingAddresses(String userToken);

  Observable<BillingAddress> addBillingAddress(String userToken, AddBillingAddress.Params params);

  Observable<BillingAddress> editBillingAddress(String userToken, EditBillingAddress.Params params);

  Observable<Boolean> deleteBillingAddress(String userToken, DeleteBillingAddress.Params params);

  Observable<ResponseBody> getTreeCategoryById(String userToken, String id);

  Observable<ResponseBody> getTreeCategoryMini(String userToken, String id);

  Observable<ResponseBody> getTreeCategoryByCheckList(String userToken, String id);

  Observable<BillingAddressPayment.BillingAddressPaymentRespond> requestBillingAddressPayment(String userToken, RequestBody params);

  Observable<RatingResult> addRatingComment(String userToken, Map<String, String> addCommentRequest);

  Observable<ReviewDetailResponse> getReviewDetail(String userToken, Map<String, String> addCommentRequest);

  Observable<RatingResult> updateReview(String userToken, Map<String, String> updateReview);

  Observable<ProductReviewResponse> getListReviewByProduct(String userToken, Map<String, String> productReviewRequest);





  Observable<RatingOption> getRatingOption(String userToken);

  Observable<ResponseBody> getRatingSummaryByProduct(String userToken, Map<String, String> request);

  Observable<RatingResult> deleteReview(String userToken, Map<String, String> request);



  Observable<DetailsItem> getDetailCategory(String token, String category_id);

  Observable<ResponseBody> getTokenRenewal(String token, Map<String, String> params);

  Observable<Boolean> checkAddCardFirstTime(String token);

  Observable<FirstTimePopup> getFirstTimePopup(String userToken);

  Observable<ResponseBody> setFirstTime(String userToken);


  Observable<SkyDollarHistoryResponse> getSkyDollarHistory(String oauth, String memberNumber,String currentPage, String limit);

  Observable<ResponseBody> sendNotification(String token, NotificationRequest NotificationItem);

  Observable<NotificationResponse> getNotification(String auth, String memberId, String deviceId);

  Observable<ResponseBody> updateReadNotification(String auth, NotificationUpdateRequest request);


  Observable<ResponseBody> deleteNotification(String auth, NotificationDeleteRequest request);

  Observable<SettingNotificationResponse> getNotificationSetting(String auth, String memberId);

  Observable<ResponseBody> updateSettingNotification(String auth, SettingNotificationRequest request);

  Observable<ResponseBody> changeFirstTimeLogin(String auth, FirstTimeToTrueRequest request);

  Observable<ResponseBody> updateMappVersion(String auth, UpdateMappVersionRequest request);

  Observable<CrmTokenResponse> createCrmToken(CrmTokenRequest request);

  Observable<OSPlayer> getDeviceInfo(String request);

  Observable<CheckLoginResponse> checkFirstLogin(String oAuth ,String memberNumber);

  Observable<ResponseBody> updateOSDataToCRM(String oAuth , OneSignalDeviceDataRequest memberNumber);

  Observable<List<OutletItem>> getOutletByProductID(String oAuth , String productId);

  Observable<ReservationTimeResponse> getReservationTime(String date , String restaurantId, String partnerCode, String partnerAuthCode);


  Observable<RestaurantMessageResponse> getRestaurantMsg(String restaurantId);

  Observable<ReservationResultResponse> sendCreateReservation(String userToken , Map<String,String> mapRequest);

  Observable<ReservationResultResponse>sendEditReservation(String userToken,
                                                           Map<String,String> mapRequest,String reservationId,
                                                           String verificationKey);


  Observable<CancelReservationResponse>cancelReservation(String userToken, String reservationId,
                                                         String verificationKey);

  Observable<ReserveHistoryItem> getDetailReservationBooking(String userToken , String bookingId);

  Observable<ReserveHistoryRespone> getHistoryAll(String userToken , String memberNumber, String sortBy,String currentPage, String pageSize);

  Observable<ReserveHistoryRespone> getHistoryFilter(String userToken , String memberNumber, String refine, String sortBy,String currentPage, String pageSize);

  Observable<DemoResponse> getProduct(String auth,String path);

  Observable<DemoRes> getProductTwo(String auth);


}
