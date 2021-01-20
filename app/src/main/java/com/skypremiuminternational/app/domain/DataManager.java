package com.skypremiuminternational.app.domain;

import android.util.Log;

import com.google.gson.Gson;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.DBManager;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.NetworkManager;
import com.skypremiuminternational.app.data.aws.hash.CustomerSessionIdHasher;
import com.skypremiuminternational.app.data.mapper.BookingDetailMapper;
import com.skypremiuminternational.app.data.mapper.BookingHistoryMapper;
import com.skypremiuminternational.app.data.mapper.PropertyMapper;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.RegionItem;
import com.skypremiuminternational.app.data.model.ean.availability.AvailableProperty;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.AdminSignInRequest;
import com.skypremiuminternational.app.data.network.request.CaregoryDetailsRequest;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.FeatureProductRequest;
import com.skypremiuminternational.app.data.network.request.ForgotPasswordRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.GetOrderHistoryRequest;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.data.network.request.PasswordHashRequest;
import com.skypremiuminternational.app.data.network.request.ProductFilterSortRequest;
import com.skypremiuminternational.app.data.network.request.ProductListRequest;
import com.skypremiuminternational.app.data.network.request.ProductReviewRequest;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.data.network.request.SearchProductRequest;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCommentRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.interactor.cart.ApplyPromoCode;
import com.skypremiuminternational.app.domain.interactor.cart.ApplyRoyaltyPoints;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrder;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrderBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceRenewalOrder;
import com.skypremiuminternational.app.domain.interactor.cart.SetShippingAndBillingAddress;
import com.skypremiuminternational.app.domain.interactor.ean.BookRoom;
import com.skypremiuminternational.app.domain.interactor.ean.CheckPrice;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetail;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetailValue;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingHistories;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.interactor.user.DeleteAddress;
import com.skypremiuminternational.app.domain.interactor.user.DeleteCreditCard;
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
import com.skypremiuminternational.app.domain.models.cart.EstoreItem;
import com.skypremiuminternational.app.domain.models.cart.RoyaltyResponse;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingRequest;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingResponse;
import com.skypremiuminternational.app.domain.models.cart.UpdateItemCountRequest;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.details.RecommendItems;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;
import com.skypremiuminternational.app.domain.models.faq.FaqResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.CancelReservationResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
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
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 7/10/17.
 * <p>
 * Task: To include the AWS Access Token and Keys on Cloudsearch APIs
 * Author: Levister Gutierrez
 * Date: 15 Apr 2019
 */

@Singleton
public class DataManager {

  private final InternalStorageManager internalStorageManager;
  private final DBManager dbManager;
  private final NetworkManager networkManager;
  private final OccupancyArranger occupancyArranger;
  private final PropertyMapper propertyMapper;
  private final BookingDetailMapper bookingDetailMapper;
  private final BookingHistoryMapper bookingHistoryMapper;
  private final CustomerSessionIdHasher customerSessionIdHasher;

  private String sessionId;

  @Inject
  public DataManager(CustomerSessionIdHasher customerSessionIdHasher,
                     InternalStorageManager internalStorageManager,
                     DBManager dbManager,
                     NetworkManager networkManager,
                     OccupancyArranger occupancyArranger,
                     PropertyMapper propertyMapper,
                     BookingDetailMapper bookingDetailMapper,
                     BookingHistoryMapper bookingHistoryMapper) {
    this.occupancyArranger = occupancyArranger;
    this.bookingHistoryMapper = bookingHistoryMapper;
    this.bookingDetailMapper = bookingDetailMapper;
    this.internalStorageManager = internalStorageManager;
    this.customerSessionIdHasher = customerSessionIdHasher;
    this.dbManager = dbManager;
    this.propertyMapper = propertyMapper;
    this.networkManager = networkManager;
  }

  public void clearAuthToken() {
    internalStorageManager.clearAuthToken();
  }

  public Observable<ResponseBody> signIn(SignInRequest signInRequest) {
    return networkManager.signIn(signInRequest);
  }

  public Observable<ResponseBody> adminSignIn(AdminSignInRequest adminSignInRequest) {
    return networkManager.adminSignIn(adminSignInRequest);
  }

  public Observable<UploadAvatarResponse> uploadAvatar(String token,
                                                       UploadAvatarRequest uploadAvatarRequest) {
    return networkManager.uploadAvatar(token, uploadAvatarRequest);
  }

  public void saveAdminToken(String token) {
    internalStorageManager.saveAdminAuthToken(token);
  }


  public void saveUserToken(String token) {
    internalStorageManager.saveUserAuthToken(token);
  }

  public String getUserToken() {
    return "Bearer " + internalStorageManager.getUserAuthToken();
  }

  public String getHMSToken() {
    return internalStorageManager.getHMSToken();
  }

  public String getHMSSecret() {
    return internalStorageManager.getHMSSecret();
  }

  public Observable<ResponseBody> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
    return networkManager.forgotPassword(forgotPasswordRequest);
  }

  public Observable<ProductListResponse> getShopping(ProductListRequest productListRequest) {
    return networkManager.getShopping(Constants.CLIENT_TOKEN, productListRequest.getCategoryId(),
        productListRequest.getField(), productListRequest.getDirection());
  }

  public Observable<FeatureProduct> getFeatureShopping(FeatureProductRequest request) {
    return networkManager.getFeatureShopping(Constants.CLIENT_TOKEN, request.getCategoryId());
  }

  public Observable<ProductListResponse> getWineAndDine(ProductListRequest productListRequest) {
    return networkManager.getWineAndDine(Constants.CLIENT_TOKEN, productListRequest.getCategoryId(),
        productListRequest.getField(), productListRequest.getDirection());
  }

  public Observable<FeatureProduct> getFeatureWineAndDine(FeatureProductRequest request) {
    return networkManager.getFeatureWineAndDine(Constants.CLIENT_TOKEN, request.getCategoryId());
  }

  public Observable<ProductListResponse> getWellness(ProductListRequest productListRequest) {
    return networkManager.getWellness(Constants.CLIENT_TOKEN, productListRequest.getCategoryId(),
        productListRequest.getField(), productListRequest.getDirection());
  }

  public Observable<FeatureProduct> getFeatureWellness(FeatureProductRequest request) {
    return networkManager.getFeatureWellness(Constants.CLIENT_TOKEN, request.getCategoryId());
  }

  public Observable<ProductListResponse> getTravel(ProductListRequest productListRequest) {
    return networkManager.getTravel(Constants.CLIENT_TOKEN, productListRequest.getCategoryId(),
        productListRequest.getField(), productListRequest.getDirection());

  }

  public Observable<FeatureProduct> getFeatureTravel(FeatureProductRequest request) {
    return networkManager.getFeatureTravel(Constants.CLIENT_TOKEN, request.getCategoryId());
  }

  public Observable<DetailsItem> getDetails(DetailsRequest detailsRequest) {
    return networkManager.getDetails(Constants.CLIENT_TOKEN, detailsRequest);
  }

  public Observable<RecommendItems> getRecommendations(DetailsRequest detailsRequest) {
    return networkManager.getRecommendations(Constants.CLIENT_TOKEN, detailsRequest);
  }

  public Observable<UserDetailResponse> getUserDetailFromAPI() {
    return networkManager.getUserDetail(getUserToken());
  }

  public Observable<CardInfomationResponse> getCreditCardInfo(String cardId) {
    return networkManager.getCreditCardInfo(getUserToken(),cardId);
  }

  public Observable<List<FavouriteListResponse>> getFavourites(GetFavouriteRequest request) {
    return networkManager.getFavourites(getUserToken(), request);
  }

  public void saveUserDetail(UserDetailResponse response) {
    internalStorageManager.saveUserDetail(response);
  }

  public void saveCRMToken(CrmTokenResponse response) {
    internalStorageManager.saveCRMToken(response);
  }

  public void saveListNotification(List<NotificationItem> payload) {
    internalStorageManager.saveListNotification(payload);
  }

  public CrmTokenResponse getCrmTokenResponse() {
    return internalStorageManager.getCRMToken();
  }

  public UserDetailResponse getUserDetail() {
    return internalStorageManager.getUserDetail();
  }

  public List<NotificationItem> getListNotification() throws FileNotFoundException {
    return internalStorageManager.getListNotification();
  }

  public void saveRatingOption(RatingOption response) {
    internalStorageManager.saveRatingOption(response);
  }

  public RatingOption getRatingOption() {
    return internalStorageManager.getRatingOption();
  }

  public Observable<CategoryResponse> getCategoriesFromAPI() {
    return networkManager.getCategories(Constants.CLIENT_TOKEN);
  }

  public Observable<MyOrderResponse> getOrderHistoryFromAPI(GetOrderHistoryRequest request) {
    return networkManager.getOrderHistory(Constants.CLIENT_TOKEN, getUserDetail().getEmail(),
        request);
  }

  public Observable<CartDetailResponse> getCartDetail() {
    return networkManager.getCartDetail(getUserToken());
  }

  public Observable<CartDetailResponse> getCartDetailBuyNow() {
    return networkManager.getCartDetailBuyNow(getUserToken());
  }

  public Observable<CategoryDetailsResponse> getCategoryDetails(CaregoryDetailsRequest categoryId) {
    return networkManager.getCategoryDetails(Constants.CLIENT_TOKEN, categoryId);
  }

  public Observable<ResponseBody> getTreeCategory(String categoryId) {
    return networkManager.getTreeCategory(Constants.CLIENT_TOKEN, categoryId);
  }

  public void saveCategories(CategoryResponse response) {
    internalStorageManager.saveCategories(response);
  }

  public CategoryResponse getCategories() {
    return internalStorageManager.getCategories();
  }

  public Observable<HomeCategoryResponse> getHomeCategoriesFromAPI() {
    return networkManager.getHomeCategories(Constants.CLIENT_TOKEN);
  }

  public Observable<FaqResponse> getFaqFromAPI() {
    return networkManager.getFaqs(Constants.CLIENT_TOKEN);
  }

  public void saveFaqItem(List<FaqItem> value) {
    dbManager.saveFaqItem(value);
  }

  public List<FaqItem> getAllFaqItem() {
    return dbManager.getAllFaqItem();
  }

  public List<FaqItem> getFaqItemByKeyword(String keyword) {
    return dbManager.getFaqItemByKeyword(keyword);
  }

  public Observable<List<CountryCode>> getCountryCodesFromAPI() {
    return networkManager.getCountryCodes();
  }

  public Observable<List<CountryCodeCC>> getCountryCodeCc() {
    return networkManager.getCountryCodeCc(getUserToken());
  }

  public void saveCountryCodes(List<CountryCode> response) {
    internalStorageManager.saveCountryCodes(response);
  }

  public List<CountryCode> getCountryCodes() {
    return internalStorageManager.getCountryCodes();
  }

  public Observable<PhoneCode> getPhoneCodesFromAPI() {
    return networkManager.getPhoneCodes();
  }

  public void savePhoneCodes(PhoneCode response) {
    internalStorageManager.savePhoneCodes(response);
  }

  public PhoneCode getPhoneCodes() {
    return internalStorageManager.getPhoneCodes();
  }

  public Observable<List<Nationality>> getNationalitiesFromAPI() {
    return networkManager.getNationalities();
  }

  public void saveNationalities(List<Nationality> response) {
    internalStorageManager.saveNationalities(response);
  }

  public List<Nationality> getNationalities() {
    return internalStorageManager.getNationalities();
  }

  public Observable<ResponseBody> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
    return networkManager.updatePassword(Constants.CLIENT_TOKEN,
        updatePasswordRequest.getMember_number(), updatePasswordRequest);
  }

  public Observable<ResponseBody> getPasswordHash(PasswordHashRequest passwordHashRequest) {
    return networkManager.getPasswordHash(Constants.CLIENT_TOKEN, passwordHashRequest);
  }

  public Observable<UserDetailResponse> updateUserDetail(UpdateUserRequest updateUserRequest) {
    return networkManager.updateUserDetail(Constants.CLIENT_TOKEN,
        updateUserRequest.getMember_number(), updateUserRequest);
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default delivery address*/
  public Observable<UserDetailResponse> updateUserDetail(UpdateUserDeliveryRequest updateUserRequest) {
    return networkManager.updateUserDetail(Constants.CLIENT_TOKEN,
        updateUserRequest.getMember_number(), updateUserRequest);
  }

  public Observable<SearchProductResponse> searchProduct(
      SearchProductRequest searchProductRequest) {
    return networkManager.searchProduct(Constants.CLIENT_TOKEN, searchProductRequest);
  }

  public Observable<SearchProductResponse> searchAll(SearchProductRequest searchProductRequest) {
    return networkManager.searchAll(Constants.CLIENT_TOKEN, searchProductRequest);
  }

  public Observable<SearchHomeResponse> searchHome(SearchProductRequest searchProductRequest) {
    return networkManager.searchHome(Constants.CLIENT_TOKEN, searchProductRequest);
  }

  public Observable<SearchProductResponse> searchHomeProducts(
      SearchProductRequest searchProductRequest) {
    return networkManager.searchHomeProducts(Constants.CLIENT_TOKEN, searchProductRequest);
  }

  public Observable<FavouriteResponse> addToFav(String productId) {
    return networkManager.addToFavourite(getUserToken(), productId);
  }

  public Observable<FavouriteResponse> removeFromFav(String wishlistItemId) {
    return networkManager.removeFromFavourite(getUserToken(), wishlistItemId);
  }

  public Observable<String> createCart() {
    return networkManager.getCartId(getUserToken())
        .map(Object::toString);
  }

  public Observable<ResponseBody> createCartResponseBody() {
    return networkManager.createCartResponseBody(getUserToken());
  }

  public Observable<AddCartItemCountRequest.Cart> addToCart(AddCartItemCountRequest request) {
    return networkManager.addToCart(request, getUserToken());
  }

  public Observable<AddCartItemCountRequest.Cart> addToBuyNow(AddCartItemCountRequest request) {
    return networkManager.addToBuyNow(request, getUserToken());
  }

  public Observable<ResponseBody> updateBuyNow() {
    return networkManager.updateBuyNow(getUserToken());
  }

  public Observable<CartDetailItem> addCartDetailItemCount(UpdateItemCountRequest item,
                                                           String itemId) {
    return networkManager.addCartDetailItemCount(getUserToken(), itemId, item);
  }

  public Observable<List<CartDetailItem>> getCartItems() {
    return networkManager.getCartItems(getUserToken());
  }

  public Observable<List<CartDetailItem>> getCartItemsBuyNow() {
    return networkManager.getCartItemsBuyNow(getUserToken());
  }

  public void saveCartCount(String count) {
    internalStorageManager.saveCartCount(count);
  }

  public String getCartCount() {
    return internalStorageManager.getCartCount();
  }

  public void removeCartId() {
    internalStorageManager.removeCartId();
  }

  public Observable<Boolean> removeShoppingCart(String cartItemId) {
    return networkManager.removeShoppingCart(getUserToken(), cartItemId);
  }

  public Observable<CartAllInformationResponse> getAllCartInformation() {
    return networkManager.getAllCartInformation(getUserToken());
  }

  public Observable<CartAllInformationResponse> getAllCartInformationBuyNow() {
    return networkManager.getAllCartInformationBuyNow(getUserToken());
  }

  public Observable<Boolean> deleteAddress(DeleteAddress.Params params) {
    return networkManager.deleteAddress(Constants.CLIENT_TOKEN, params.addressId);
  }

  public Observable<CheckLimitResponse> checkIfCartHasReachedLimit() {
    return networkManager.checkIfCartHasReachedLimit(getUserToken());
  }

  public Observable<Boolean> applyPromoCode(ApplyPromoCode.Params params) {
    return networkManager.applyPromoCode(getUserToken(), params.promoCode);
  }

  public Observable<Boolean> deletePromoCode() {
    return networkManager.deletePromoCode(getUserToken());
  }

  public Observable<RoyaltyResponse> applyRoyaltyPts(ApplyRoyaltyPoints.Params params) {
    return networkManager.applyRoyaltyPts(getUserToken(), params.points);
  }

  public Observable<Boolean> deleteRoyaltyPoints() {
    return networkManager.deleteRoyaltyPoints(getUserToken())
        .map(new Func1<RoyaltyResponse, Boolean>() {
          @Override
          public Boolean call(RoyaltyResponse royaltyResponse) {
            return royaltyResponse.success;
          }
        });
  }

  public Observable<List<CreditCardResponse>> getCreditCards() {
    return networkManager.getCreditCards(getUserToken());
  }
  public Observable<List<CreditCardResponse>> getCreditCardsV2() {
    return networkManager.getCreditCardsV2(getUserToken());
  }

  public Observable<String> placeOrder(PlaceOrder.Params params) {
    return networkManager.placeOrder(getUserToken(), params);
  }

  public Observable<String> placeOrderBuyNow(PlaceOrderBuyNow.Params params) {
    return networkManager.placeOrderBuyNow(getUserToken(), params);
  }

  public Observable<OrderDetailResponse> getOrderDetail(String orderId) {
    return networkManager.getOrderDetail(Constants.CLIENT_TOKEN, Integer.parseInt(orderId));
  }

  public Observable<ProductListResponse> getFilterResult(String stringQuery) {
    return networkManager.getFilterResult(Constants.CLIENT_TOKEN, stringQuery);
  }

  public Observable<ProductListResponse> getFilterResultSort(ProductFilterSortRequest request) {
    return networkManager.getFilterResultSort(Constants.CLIENT_TOKEN, request);
  }

  public Observable<ExtraOrderDetail> getExtraOrderDetail(String orderId) {
    return networkManager.getExtraOrderDetail(Constants.CLIENT_TOKEN, Integer.parseInt(orderId));
  }

  public Observable<UpdateUserResponse> addCreditCards(AddCreditCardRequest addCreditCardRequest) {
    return networkManager.addCreditCards(getUserToken(), addCreditCardRequest);
  }

  public Observable<UpdateUserResponse> addCreditCardsV2(AddCreditCardRequest addCreditCardRequest) {
    return networkManager.addCreditCardsV2(getUserToken(), addCreditCardRequest);
  }

  public Observable<UpdateUserResponse> updateCreditCard(
      UpdateCreditCardRequest updateCreditCardRequest) {
    return networkManager.updateCreditCard(getUserToken(), updateCreditCardRequest);
  }

  public Observable<UpdateUserResponse> updateCreditCardV2(
      UpdateCreditCardRequest updateCreditCardRequest) {
    return networkManager.updateCreditCardV2(getUserToken(), updateCreditCardRequest);
  }

  public Observable<UpdateUserResponse> deleteCreditCard(DeleteCreditCard.Params params) {
    return networkManager.deleteCreditCard(getUserToken(), params.cardId);
  }

  public Observable<UpdateUserResponse> setDefaultCreditCard(String cardId) {
    return networkManager.setDefaultCreditCard(getUserToken(), cardId);
  }
  public Observable<UpdateUserResponse> setDefaultCreditCardV2(String cardId) {
    return networkManager.setDefaultCreditCardV2(getUserToken(), cardId);
  }

  public Single<EstoreItem> getEstoreItemBySku(final String sku) {
    return dbManager.getEstoreItemBySku(sku)
        .onErrorResumeNext(
            ignored -> networkManager.getDetails(Constants.CLIENT_TOKEN, new DetailsRequest(sku, ""))
                .map(detailsItem -> {
                  String imageUrl = getImageUrl(detailsItem);
                  String category =
                      getCategoryName(getCategories().getChildrenData(), detailsItem);
                  return new EstoreItem(detailsItem.getSku(), imageUrl, category);
                })
                .doOnNext(dbManager::saveEstoreItem)
                .toSingle());
  }

  public Observable<DetailsItem> getDetailItemBySku(final String sku) {
    return networkManager.getDetails(Constants.CLIENT_TOKEN, new DetailsRequest(sku, ""));
  }

  public void saveEstoreItems(List<ItemsItem> items) {
    List<EstoreItem> estoreItems = new ArrayList<>();
    for (ItemsItem item : items) {
      String imageUrl = getImageUrl(item);
      String category = getCategoryName(getCategories().getChildrenData(), item);
      EstoreItem estoreItem = new EstoreItem(item.getSku(), imageUrl, category);
      estoreItems.add(estoreItem);
    }
    dbManager.saveEstoreItems(estoreItems);
  }

  private String getImageUrl(DetailsItem detailsItem) {
    for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("small_image")) {
        return customAttribute.getValue().toString();
      }
    }
    return "";
  }

  private String getCategoryName(List<ChildData> categories, DetailsItem item) {
    for (CustomAttribute attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (!id.equals("" + pillar.getId())) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(",");
                  }
                }
              }
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(",")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(","));
          }
          return subCategories;
        } catch (Exception e) {
          Timber.e(e);
          return "";
        }
      }
    }
    return "";
  }

  private String getImageUrl(ItemsItem item) {
    for (CustomAttributesItem customAttribute : item.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("small_image")) {
        return customAttribute.getValue().toString();
      }
    }
    return "";
  }

  private String getCategoryName(List<ChildData> categories, ItemsItem item) {
    for (CustomAttributesItem attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (!id.equals("" + pillar.getId())) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(",");
                  }
                }
              }
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(",")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(","));
          }
          return subCategories;
        } catch (Exception e) {
          Timber.e(e);
          return "";
        }
      }
    }
    return "";
  }

  public Observable<SetShippingAndBillingResponse> setShippingAndBillingAddress(
      SetShippingAndBillingAddress.Params params) {
    SetShippingAndBillingRequest request = buildRequest(params);
    return networkManager.setShippingAndBillingAddress(getUserToken(), request);
  }

  private SetShippingAndBillingRequest buildRequest(SetShippingAndBillingAddress.Params params) {
    SetShippingAndBillingRequest.Address shippingAddress =
        buildAddress(params.defaultDeliveryAddress, params.email);
    SetShippingAndBillingRequest.Address billingAddress =
        buildAddress(params.defaultBillingAddress, params.email);
    SetShippingAndBillingRequest.AddressInformation information =
        SetShippingAndBillingRequest.AddressInformation.builder()
            .shipping_address(shippingAddress)
            .billing_address(billingAddress)
            .shipping_carrier_code(params.shippingCarrierCode)
            .shipping_method_code(params.shippingMethodCode)
            .build();
    return new SetShippingAndBillingRequest(information);
  }

  private SetShippingAndBillingRequest.Address buildAddress(BillingAddress address, String email) {
    String region = "";
    String regionId = "";
    String regionCode = "";
    List<RegionItem> regionItems = address.getRegion();
    if (regionItems != null && regionItems.size() > 0) {
      RegionItem regionItem = regionItems.get(0);
      region = regionItem.getRegion();
      regionCode = regionItem.getRegionCode();
      regionId = regionItem.getRegionId();
    }

    return SetShippingAndBillingRequest.Address.builder()
        .email(email)
        .firstname(address.getFirstname())
        .lastname(address.getLastname())
        .region(region)
        .region_id(regionId)
        .region_code(regionCode)
        .country_id(address.getCountryId())
        .street(address.getStreet())
        .postcode(address.getPostcode())
        .city(address.getCity())
        .telephone(address.getTelephone())
        .unit_number(address.getUnitNumber())
        .build();
  }

  private SetShippingAndBillingRequest.Address buildAddress(Address address, String email) {
    String company =
        address.getCompany() != null ? address.getCompany() : "";
    String region =
        address.getRegion() != null ? address.getRegion().getRegion() != null ? address.getRegion()
            .getRegion()
            .toString() : "" : "";
    String regionId = address.getRegion() != null ? address.getRegion().getRegionId() != null ?
        address.getRegion().getRegionId()
            + "" : "" : "";

    String regionCode = address.getRegion() != null ? address.getRegion().getRegionCode() != null
        ? address.getRegion().getRegionCode().toString() : "" : "";

    return SetShippingAndBillingRequest.Address.builder()
        .email(email)
        .firstname(address.getFirstname())
        .lastname(address.getLastname())
        .company(company)
        .region(region)
        .region_id(regionId)
        .region_code(regionCode)
        .country_id(address.getCountryId())
        .street(address.getStreet())
        .postcode(address.getPostcode())
        .city(address.getCity())
        .telephone(address.getTelephone())
        .unit_number(address.getUnitNumber())
        .build();
  }

  public Observable<Boolean> addRenewalToCartWithCredit() {
    return networkManager.addRenewalToCartWithCredit(getUserToken());
  }

  public Observable<Version> getAppVersion() {
    return networkManager.getAppVersion(Constants.CLIENT_TOKEN);
  }

  public Observable<ItemsItem> getRenewalProduct() {
    return networkManager.getRenewalProduct(Constants.CLIENT_TOKEN);
  }

  public Observable<Boolean> addRenewalToCartWithPoints() {
    return networkManager.addRenewalToCartWithPoints(getUserToken());
  }

  public Observable<String> placeRenewalOrder(PlaceRenewalOrder.Params params) {
    return networkManager.placeRenewalOrder(getUserToken(), params);
  }

  public Observable<Map<String, Property>> getAvailableProperties(List<String> propertyIds,
                                                                  int roomCount, int adultCount,
                                                                  List<Child> children, String arrival,
                                                                  String departure, String countryCode) {
    List<String> occupancies =
        occupancyArranger.groupOccupancies(roomCount, adultCount, children);

    String roomItemOccupancy =
        occupancyArranger.arrangeForRoomItem(adultCount, children);

    return networkManager.getIpAddress()
        .flatMap(ipAddress -> networkManager.getAvailableProperties(propertyIds, arrival, departure,
            countryCode, occupancies, getCustomerSessionId(), ipAddress)
            .flatMap(
                (Func1<List<AvailableProperty>, Observable<Map<String, Property>>>) (List<AvailableProperty> availableProperties) -> {
                  if (availableProperties != null && availableProperties.size() > 0) {
                    return Observable
                        .from(availableProperties)
                        .map(AvailableProperty::getPropertyId)
                        .toList()
                        .flatMap(
                            availablePropertyIds -> networkManager.getPropertyContents(
                                availablePropertyIds,
                                getCustomerSessionId(), ipAddress)
                        )
                        .map(contentMap -> {
                          Map<String, PropertyContent> map = contentMap;

                          Map<String, Property> map2 = propertyMapper.transform(contentMap, availableProperties,
                              occupancies, roomItemOccupancy);

                          int i = 0;
                          return propertyMapper.transform(contentMap, availableProperties,
                              occupancies, roomItemOccupancy);
                        });
                  } else {
                    return Observable.empty();
                  }
                }));
  }

  public Observable<PriceCheckResult> checkPrice(CheckPrice.Params params) {
    return networkManager.getIpAddress()
        .flatMap(ipAddress -> networkManager.checkPrice(params, getCustomerSessionId(), ipAddress));
  }

  public Observable<List<String>> searchProperties(String query) {
    return networkManager.searchProperties(query, getHMSToken(), getHMSSecret());
  }

  public Observable<List<Suggestion>> getSuggestions(String keyword) {
    return networkManager.getSuggestions(keyword, getHMSToken(), getHMSSecret());
  }

  public Observable<List<BookingHistory>> getBookingHistories(GetBookingHistories.Params params) {
    return networkManager.getBookingHistories(getUserToken(), params);
  }

  public Observable<BookingDetail> getBookingDetail(GetBookingDetail.Params params) {
    Log.d("bookingDetail", params.toString());
    return networkManager.getIpAddress()
        .flatMap(ipAddress -> networkManager.getBookingDetail(params)
            .flatMap(booking -> {
              BookingData bookingData = bookingHistoryMapper.mapBookingData(booking.getBookingData());
              return networkManager.getItinerary(bookingData.getLinks().getRetrieve().getHref(), getCustomerSessionId(), ipAddress)
                  .flatMap(itinerary -> {
                    List<String> propertyIds = new ArrayList<>();
                    propertyIds.add(itinerary.getPropertyId());
                    return networkManager.getPropertyContents(propertyIds, getCustomerSessionId(), ipAddress)
                        .map(contents -> bookingDetailMapper.map(booking, itinerary, contents, bookingData, params.rooms));
                  });
            }));
  }

  public Observable<Booking> getBookingDetailvalue(GetBookingDetailValue.Params params) {

    return networkManager.getBookingDetailValue(params);

  }

  public Observable<List<CardOption>> getPaymentOptions(GetPaymentOptions.Params params) {
    return networkManager.getIpAddress()
        .flatMap(ipAddress -> networkManager.getPaymentOptions(params.paymentOptionLink,
            getCustomerSessionId(),
            ipAddress));
  }

  public Observable<BookingDetail> bookRoom(BookRoom.Params params) {

    return networkManager.getIpAddress()
        .flatMap(
            ipAddress -> networkManager.bookRoom(getUserToken(), params, getCustomerSessionId(),
                ipAddress));
  }

  public Observable<Boolean> cancelBooking(String bookingId) {
    return networkManager.cancelBooking(getUserToken(), bookingId);
  }

  public Observable<List<ISOCountry>> getISOCountryCodes() {
    return networkManager.getISOCountryCodes();
  }

  public Observable<InviteFriendDescription> getInviteFriendDescription() {
    return networkManager.getInviteDescription(Constants.CLIENT_TOKEN);
  }

  private String getCustomerSessionId() {
    if (Validator.isTextValid(sessionId)) {
      return sessionId;
    }

    String userId = String.valueOf(internalStorageManager.getUserDetail().getId());
    sessionId = customerSessionIdHasher.hash(getUserToken(), userId);

    return sessionId;
  }

  public Observable<List<BillingAddress>> getBillingAddresses() {
    return networkManager.getBillingAddresses(getUserToken());
  }

  public Observable<BillingAddress> addBillingAddress(
      AddBillingAddress.Params params) {
    return networkManager.addBillingAddress(getUserToken(), params);
  }

  public Observable<BillingAddress> editBillingAddress(EditBillingAddress.Params params) {
    return networkManager.editBillingAddress(getUserToken(), params);
  }

  public Observable<Boolean> deleteBillingAddress(DeleteBillingAddress.Params params) {
    return networkManager.deleteBillingAddress(getUserToken(), params);
  }

  public Observable<ResponseBody> getTreeCategoryById(String request) {
    return networkManager.getTreeCategoryById(Constants.CLIENT_TOKEN, request);

  }
  public Observable<ResponseBody> getTreeCategoryMini(String request) {
    return networkManager.getTreeCategoryMini(Constants.CLIENT_TOKEN, request);

  }

  public Observable<ResponseBody> getTreeCategoryByCheckList(String request) {
    return networkManager.getTreeCategoryByCheckList(Constants.CLIENT_TOKEN, request);
  }

  public Observable<BillingAddressPayment.BillingAddressPaymentRespond> requestBillingAddressPayment(RequestBody params) {
    return networkManager.requestBillingAddressPayment(getUserToken(), params);
  }

  public Observable<RatingResult> addRatingComment(AddCommentRequest request) {
    return networkManager.addRatingComment(getUserToken(), request.toMap());
  }

  public Observable<RatingOption> getRatingOptionFormAPI() {
    return networkManager.getRatingOption(getUserToken());
  }

  public Observable<ResponseBody> getRatingSummaryByProduct(Map<String, String> request) {
    return networkManager.getRatingSummaryByProduct(getUserToken(), request);
  }


  public Observable<ReviewDetailResponse> getReviewDetail(ReviewDetailRequest request) {
    return networkManager.getReviewDetail(getUserToken(), request.toMap());
  }


  public Observable<ProductReviewResponse> getListReviewByProduct(ProductReviewRequest request) {
    return networkManager.getListReviewByProduct(getUserToken(), request.toMap());
  }

  public Observable<RatingResult> updateReview(UpdateCommentRequest request) {
    return networkManager.updateReview(getUserToken(), request.toMap());
  }

  public Observable<RatingResult> deleteReview(Map<String, String> request) {
    return networkManager.deleteReview(getUserToken(), request);
  }

  public Observable<DetailsItem> getDetailCategory(String category_id) {
    return networkManager.getDetailCategory(Constants.CLIENT_TOKEN, category_id);
  }

  public Observable<ResponseBody> getRenewalToken(String params) {

    Map<String, String> map = new HashMap<>();

    map.put("input", params);
    return networkManager.getTokenRenewal(Constants.CLIENT_TOKEN, map);
  }

  public Observable<CrmTokenResponse> createCrmToken() {
    CrmTokenRequest request = new CrmTokenRequest();
    request.setGrantType(BuildConfig.CRM_GRANT_TYPE);
    request.setClientId(BuildConfig.CRM_CLIENT_ID);
    request.setClientSecret(BuildConfig.CRM_CLIENT_SECRET);
    request.setUsername(BuildConfig.CRM_USER);
    request.setPassword(BuildConfig.CRM_PASSWORD);
    request.setPlatform(BuildConfig.CRM_PLATFORM);
    return networkManager.createCrmToken(request);
  }

  public Observable<ResponseBody> sendNotification(String oAuth,NotificationRequest params) {

    return networkManager.sendNotification(oAuth, params);
  }

  public Observable<NotificationResponse> getNotification(String auth, String deviceId) {

    return networkManager.getNotification(auth, getUserDetail().getMemberNumber(), deviceId);
  }
  public Observable<ResponseBody> updateReadNotification(String auth, NotificationUpdateRequest request) {

    return networkManager.updateReadNotification(auth, request);
  }
  public Observable<ResponseBody> deleteNotification(String auth, NotificationDeleteRequest request) {

    return networkManager.deleteNotification(auth, request);
  }

  public Observable<SettingNotificationResponse> getNotificationSetting(String auth) {
    return networkManager.getNotificationSetting(auth,getUserDetail().getMemberNumber());
  }

  public Observable<ResponseBody> updateSettingNotification(String auth, SettingNotificationRequest request) {
    return networkManager.updateSettingNotification(auth,request);
  }
  public Observable<ResponseBody> changeFirstTimeLogin(String auth, FirstTimeToTrueRequest request) {
    request.setMemberId(getUserDetail().getMemberNumber());
    return networkManager.changeFirstTimeLogin(auth,request);
  }
  public Observable<ResponseBody> updateMappVersion(String auth) {
    UpdateMappVersionRequest request = new UpdateMappVersionRequest();
    request.setMemberId(getUserDetail().getMemberNumber());
    request.setMappVersion(BuildConfig.VERSION_NAME);
    return networkManager.updateMappVersion(auth,request);
  }
  public Observable<OSPlayer> getDeviceInfo(String playerId) {
    return networkManager.getDeviceInfo(playerId);
  }
  public Observable<CheckLoginResponse> checkFirstLogin(String oAuth) {
    return networkManager.checkFirstLogin(oAuth,internalStorageManager.getUserDetail().getMemberNumber());
  }
  public Observable<ResponseBody> updateOSDataToCRM(String oAuth,OneSignalDeviceDataRequest request) {
    request.setMember_id(internalStorageManager.getUserDetail().getMemberNumber());
    return networkManager.updateOSDataToCRM(oAuth, request);
  }

  public Observable<Boolean> checkAddCardFirstTime() {
    return networkManager.checkAddCardFirstTime(getUserToken());
  }

  public Observable<FirstTimePopup> getFirstTimePopup() {
    return  networkManager.getFirstTimePopup(getUserToken());
  }
  public Observable<ResponseBody> setFirstTime() {
    return  networkManager.setFirstTime(getUserToken());
  }

  public Observable<List<OutletItem>> getOutletByProductID(String productId) {
    return  networkManager.getOutletByProductID(getUserToken(),productId);
  }

  public Observable<ReservationTimeResponse> getReservationTime(String date, String restaurantId) {
    return networkManager.getReservationTime(date, restaurantId, BuildConfig.PARTNER_CODE, BuildConfig.PARTNER_AUTH_CODE);
  }


  public Observable<RestaurantMessageResponse> getRestaurantMsg(String restaurantId) {
    return networkManager.getRestaurantMsg(restaurantId);
  }

  public Observable<ReservationResultResponse> sendCreateReservation(Map<String,String> mapRequest) {
    return networkManager.sendCreateReservation(getUserToken(),mapRequest);
  }

  public Observable<ReservationResultResponse> sendEditReservation(Map<String,String> mapRequest,String reservationId,
                                                                   String verificationKey) {
    return networkManager.sendEditReservation(getUserToken(),mapRequest,reservationId, verificationKey);
  }

  public Observable<CancelReservationResponse> cancelReservation(String reservationId,
                                                                 String verificationKey) {
    return networkManager.cancelReservation(getUserToken(),reservationId, verificationKey);
  }

  public Observable<ReserveHistoryItem> getDetailReservationBooking(String bookingId) {
    return networkManager.getDetailReservationBooking(getUserToken(), bookingId);
  }

  public Observable<ReserveHistoryRespone> getHistoryAll(String sortBy,String currentPage, String pageSize) {
    return networkManager.getHistoryAll(getUserToken(),getUserDetail().getId().toString().trim(),sortBy,currentPage,pageSize);
  }
  public Observable<ReserveHistoryRespone> getHistoryFilter(String sortBy,String refine,String currentPage, String pageSize) {
    return networkManager.getHistoryFilter(getUserToken(),getUserDetail().getId().toString().trim(), sortBy,refine,currentPage,pageSize);
  }

 public Observable<DemoResponse> getProduct(String path) {
    return networkManager.getProduct(Constants.CLIENT_TOKEN,path);
  }

  public Observable<DemoRes> getProductTwo() {
    return networkManager.getProductTwo(Constants.CLIENT_TOKEN);
  }

  public Observable<SkyDollarHistoryResponse> getSkyDollarHistory(String crmAccectoken, String currentPage, String limit) {
    String memberNumber  = "";
    String oauth  = crmAccectoken;

    //member number
    UserDetailResponse userDetail  = internalStorageManager.getUserDetail();
    for (com.skypremiuminternational.app.domain.models.user.CustomAttribute attributes : userDetail.getCustomAttributes()) {
      if (attributes.getAttributeCode().equals("member_number")) {
        memberNumber = attributes.getValue() ;
      }
    }
    return networkManager.getSkyDollarHistory(oauth,memberNumber,currentPage,limit);
  }


  public void saveTreeFilter(String stringTreeFilter){
    internalStorageManager.saveTreeFilter(stringTreeFilter);
  }

  public String getStringTreeFilterLocal(){
    String str = internalStorageManager.getStringTreeFilterLocal();
    String obj2 = new Gson().fromJson(str, String.class);
    return obj2;
  }
}
