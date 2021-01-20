package com.skypremiuminternational.app.data.impl;

import android.text.TextUtils;
import android.util.Log;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.NetworkManager;
import com.skypremiuminternational.app.data.aws.HttpRequest;
import com.skypremiuminternational.app.data.aws.Signer;
import com.skypremiuminternational.app.data.aws.credentials.AwsCredentials;
import com.skypremiuminternational.app.data.mapper.AddBillingAddressRequestMapper;
import com.skypremiuminternational.app.data.mapper.BookRequestMapper;
import com.skypremiuminternational.app.data.mapper.BookingDetailMapper;
import com.skypremiuminternational.app.data.mapper.BookingHistoryMapper;
import com.skypremiuminternational.app.data.mapper.EditBillingAddressRequestMapper;
import com.skypremiuminternational.app.data.mapper.PriceCheckMapper;
import com.skypremiuminternational.app.data.mapper.SuggestionMapper;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddressesResponse;
import com.skypremiuminternational.app.data.model.billingaddress.RegionItem;
import com.skypremiuminternational.app.data.model.ean.availability.AvailableProperty;
import com.skypremiuminternational.app.data.model.ean.booking.book.BookResponse;
import com.skypremiuminternational.app.data.model.ean.booking.book.EanResult;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.itinerary.ItineraryResponse;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.data.network.RestAdapter;
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
import com.skypremiuminternational.app.data.network.request.PlaceOrderRequest;
import com.skypremiuminternational.app.data.network.request.PlaceRenewalOrderWithPointRequest;
import com.skypremiuminternational.app.data.network.request.ProductFilterSortRequest;
import com.skypremiuminternational.app.data.network.request.RenewalRequest;
import com.skypremiuminternational.app.data.network.request.SearchProductRequest;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserDeliveryRequest;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.data.network.service.AuthService;
import com.skypremiuminternational.app.data.network.service.CartService;
import com.skypremiuminternational.app.data.network.service.CategoryService;
import com.skypremiuminternational.app.data.network.service.CountryCodesService;
import com.skypremiuminternational.app.data.network.service.DemoServices;
import com.skypremiuminternational.app.data.network.service.DetailsService;
import com.skypremiuminternational.app.data.network.service.EanService;
import com.skypremiuminternational.app.data.network.service.FaqService;
import com.skypremiuminternational.app.data.network.service.FavouriteService;
import com.skypremiuminternational.app.data.network.service.HGWService;
import com.skypremiuminternational.app.data.network.service.HomeCategoryService;
import com.skypremiuminternational.app.data.network.service.MySkyDollarServices;
import com.skypremiuminternational.app.data.network.service.NationalitiesService;
import com.skypremiuminternational.app.data.network.service.NotificationService;
import com.skypremiuminternational.app.data.network.service.OrderHistoryService;
import com.skypremiuminternational.app.data.network.service.PhoneCodesService;
import com.skypremiuminternational.app.data.network.service.PopupService;
import com.skypremiuminternational.app.data.network.service.RatingCommentService;
import com.skypremiuminternational.app.data.network.service.SearchProductService;
import com.skypremiuminternational.app.data.network.service.ShoppingService;
import com.skypremiuminternational.app.data.network.service.SkyOneSignalSevices;
import com.skypremiuminternational.app.data.network.service.TravelService;
import com.skypremiuminternational.app.data.network.service.UserDetailService;
import com.skypremiuminternational.app.data.network.service.UserUpdateService;
import com.skypremiuminternational.app.data.network.service.WellnessService;
import com.skypremiuminternational.app.data.network.service.WineAndDineService;
import com.skypremiuminternational.app.data.utl.EanAuthHeaderGenerator;
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
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.creditCard.SetDefaultCreditCardrequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;
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
import com.skypremiuminternational.app.domain.models.notification.SkyNotification;
import com.skypremiuminternational.app.domain.models.notification.UpdateMappVersionRequest;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.search.SearchHomeResponse;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.skypremiuminternational.app.domain.models.user.BillingAddressPayment;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.Region;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.http.PartMap;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by johnsonmaung on 9/26/17.
 * <p>
 * Author: Levister Gutierrez
 * Task: To include the AWS Access Token and Keys on Cloudsearch APIs, add the variable "key"
 * and "secret"
 * Included: searchProperties & getSuggestions
 * Date: 15 Apr 2019
 */
@Singleton
public class NetworkManagerImpl implements NetworkManager {

  private final PriceCheckMapper priceCheckMapper;
  private final BookingHistoryMapper bookingHistoryMapper;
  private final BookRequestMapper bookRequestMapper;
  private final BookingDetailMapper bookingDetailMapper;
  private final SuggestionMapper suggestionMapper;
  private final AddBillingAddressRequestMapper addBillingAddressRequestMapper;
  private final EditBillingAddressRequestMapper editBillingAddressRequestMapper;
  private AuthService authService;
  private ShoppingService shoppingService;
  private WineAndDineService wineAndDineService;
  private WellnessService wellnessService;
  private DetailsService detailsService;
  private UserDetailService userDetailService;
  private FavouriteService favouriteService;
  private CategoryService categoryService;
  private HomeCategoryService homeCategoryService;
  private FaqService faqService;
  private TravelService travelService;
  private CountryCodesService countryCodesService;
  private NationalitiesService nationalitiesService;
  private UserUpdateService userUpdateService;
  private SearchProductService searchProductService;
  private PhoneCodesService phoneCodesService;
  private OrderHistoryService orderHistoryService;
  private CartService cartService;
  private EanService eanService;
  private RatingCommentService ratingCommentService;
  private NotificationService notificationService;
  private SkyOneSignalSevices skyOneSignalSevices;
  private PopupService popupService;
  private MySkyDollarServices mySkyDollarServices;
  private HGWService hgwService;
  private DemoServices demoServices;

  @Inject
  public NetworkManagerImpl(SuggestionMapper suggestionMapper, PriceCheckMapper priceCheckMapper,
                            BookRequestMapper bookRequestMapper,
                            AddBillingAddressRequestMapper addBillingAddressRequestMapper,
                            BookingDetailMapper bookingDetailMapper,
                            EditBillingAddressRequestMapper editBillingAddressRequestMapper,
                            BookingHistoryMapper bookingHistoryMapper) {
    this.suggestionMapper = suggestionMapper;
    this.bookRequestMapper = bookRequestMapper;
    this.bookingHistoryMapper = bookingHistoryMapper;
    this.bookingDetailMapper = bookingDetailMapper;
    this.priceCheckMapper = priceCheckMapper;
    this.editBillingAddressRequestMapper = editBillingAddressRequestMapper;
    this.addBillingAddressRequestMapper = addBillingAddressRequestMapper;
    Retrofit retrofit = RestAdapter.create();

    eanService = retrofit.create(EanService.class);

    authService = retrofit.create(AuthService.class);

    shoppingService = retrofit.create(ShoppingService.class);

    wineAndDineService = retrofit.create(WineAndDineService.class);

    wellnessService = retrofit.create(WellnessService.class);

    detailsService = retrofit.create(DetailsService.class);

    userDetailService = retrofit.create(UserDetailService.class);

    categoryService = retrofit.create(CategoryService.class);

    homeCategoryService = retrofit.create(HomeCategoryService.class);

    faqService = retrofit.create(FaqService.class);

    travelService = retrofit.create(TravelService.class);

    countryCodesService = retrofit.create(CountryCodesService.class);

    nationalitiesService = retrofit.create(NationalitiesService.class);

    userUpdateService = retrofit.create(UserUpdateService.class);

    searchProductService = retrofit.create(SearchProductService.class);

    phoneCodesService = retrofit.create(PhoneCodesService.class);

    favouriteService = retrofit.create(FavouriteService.class);

    orderHistoryService = retrofit.create(OrderHistoryService.class);

    cartService = retrofit.create(CartService.class);

    ratingCommentService = retrofit.create(RatingCommentService.class);

    mySkyDollarServices = retrofit.create(MySkyDollarServices.class);

    popupService = retrofit.create(PopupService.class);

    notificationService = retrofit.create(NotificationService.class);

    skyOneSignalSevices = retrofit.create(SkyOneSignalSevices.class);

    hgwService = retrofit.create(HGWService.class);

    demoServices = retrofit.create(DemoServices.class);
  }

  @Override
  public Observable<ResponseBody> signIn(SignInRequest signInRequest) {
    return authService.signIn(signInRequest);
  }

  @Override
  public Observable<ResponseBody> adminSignIn(AdminSignInRequest adminSignInRequest) {
    return authService.adminSignIn(adminSignInRequest);
  }

  @Override
  public Observable<ResponseBody> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
    return authService.forgotPassword(forgotPasswordRequest);
  }

  @Override

  public Observable<UploadAvatarResponse> uploadAvatar(String token,
                                                       UploadAvatarRequest uploadAvatarRequest) {
    return authService.uploadAvatar(token, uploadAvatarRequest);
  }

  @Override
  public Observable<ProductListResponse> getShopping(String auth, String category_id, String field,
                                                     String direction) {
    if (field.equalsIgnoreCase("popularity_order")) {
      return shoppingService.getShoppingByPopularity(auth, category_id, field, direction, "name",
          "ASC");
    }
    return shoppingService.getShopping(auth, category_id, field, direction);
  }

  @Override
  public Observable<FeatureProduct> getFeatureShopping(String auth, String category_id) {
    return shoppingService.getFeatureShopping(auth, category_id);
  }

  @Override

  public Observable<ProductListResponse> getWineAndDine(String auth, String category_id,
                                                        String field,
                                                        String direction) {
    if (field.equalsIgnoreCase("popularity_order")) {
      return wineAndDineService.getWineAndDineByPopularity(auth, category_id, field, direction,
          "name", "ASC");
    }
    return wineAndDineService.getWineAndDine(auth, category_id, field, direction);
  }

  @Override
  public Observable<FeatureProduct> getFeatureWineAndDine(String auth, String category_id) {
    return wineAndDineService.getFeatureWineAndDine(auth, category_id);
  }

  @Override
  public Observable<ProductListResponse> getWellness(String auth, String category_id, String field,
                                                     String direction) {
    if (field.equalsIgnoreCase("popularity_order")) {
      return wellnessService.getWellnessByPopularity(auth, category_id, field, direction, "name",
          "ASC");
    }
    return wellnessService.getWellness(auth, category_id, field, direction);
  }

  @Override
  public Observable<FeatureProduct> getFeatureWellness(String auth, String category_id) {
    return wellnessService.getFeatureWellness(auth, category_id);
  }

  @Override
  public Observable<ProductListResponse> getTravel(String auth, String category_id, String field,
                                                   String direction) {
    if (field.equalsIgnoreCase("popularity_order")) {
      return travelService.getTravelByPopularity(auth, category_id, field, direction, "name",
          "ASC");
    }
    if (field.equalsIgnoreCase("price")) {
      return travelService.getTravelByPrice(auth, category_id, field, direction, "name",
          direction);
    }
    return travelService.getTravel(auth, category_id, field, direction);
  }

  @Override
  public Observable<FeatureProduct> getFeatureTravel(String auth, String category_id) {
    return travelService.getFeatureTravel(auth, category_id);
  }

  @Override
  public Observable<DetailsItem> getDetails(String token, DetailsRequest detailsRequest) {
    return detailsService.getDetails(token, detailsRequest.getSku());
  }

  @Override
  public Observable<RecommendItems> getRecommendations(String token,
                                                       DetailsRequest detailsRequest) {
    return detailsService.getRecommendations(token, detailsRequest.getSku(), detailsRequest.getCategory_id());
  }

  @Override
  public Observable<UserDetailResponse> getUserDetail(String token) {
    return userDetailService.getUserDetail(token);
  }

  @Override
  public Observable<CardInfomationResponse> getCreditCardInfo(String token, String cardId) {
    return userDetailService.getCreditCardInfo(token, cardId);
  }

  @Override
  public Observable<List<FavouriteListResponse>> getFavourites(String token,
                                                               GetFavouriteRequest request) {
    switch (request.requestType) {
      case GetFavouriteRequest.REQUEST_TYPE_ALL:
        return favouriteService.getAllFavourites(token);
      case GetFavouriteRequest.REQUEST_TYPE_WITH_CATEGORYT:
        return favouriteService.getFavouriteWithCategory(token, request.categoryId,
            request.partnerType, request.sortingField, request.sortingDirection);
      case GetFavouriteRequest.REQUEST_TYPE_WITHOUT_CATEGORY:
        return favouriteService.getFavouriteWithoutCategoty(token, request.partnerType,
            request.sortingField, request.sortingDirection);
      default:
        return favouriteService.getAllFavourites(token);
    }
  }

  @Override
  public Observable<CategoryResponse> getCategories(String token) {
    return categoryService.getCategories(token);
  }

  @Override
  public Observable<MyOrderResponse> getOrderHistory(String token, String email,
                                                     GetOrderHistoryRequest request) {
    String sorting = "DESC";
    String field = "created_at";
    if (request.selectedSorting.equalsIgnoreCase("Earliest first")) {
      sorting = "ASC";
    } else if (request.selectedSorting.equalsIgnoreCase("Order ID")) {
      field = "entity_id";
    }
    switch (request.requestType) {
      case GetOrderHistoryRequest.REQUEST_TYPE_ALL:
        return orderHistoryService.getOrderHistory(token, email, sorting);
      case GetOrderHistoryRequest.REQUEST_TYPE_WITH_FILTER:

        return orderHistoryService.getOrderHistoryWithFilter(token, email,
            getCategory(request.selectedCategory),
            sorting, field);
      default:
        return orderHistoryService.getOrderHistory(token, email, sorting);
    }
  }

  private String getCategory(String selectedCategory) {

    switch (selectedCategory) {
      case "Suspected Fraud":
        return Constants.ORDER_SUSPECTED_FRAUD;
      case "Payment Review":
        return Constants.ORDER_PAYMENT_REVIEW;
      case "Processing":
        return Constants.ORDER_PROCESSING;
      case "Delivering":
        return Constants.ORDER_PENDING;
      case "Collected":
        return Constants.ORDER_COMPLETE;
      case "Cancelled":
        return Constants.ORDER_CANCELLED;
      case "Fail":
        return Constants.ORDER_FAIL;
      case "On Hold":
        return Constants.ORDER_HOLDED;
      case "Shipping":
        return Constants.ORDER_SHIPPING;
      case "Closed":
        return Constants.ORDER_CLOSED;
    }
    return "";
  }

  @Override
  public Observable<CartDetailResponse> getCartDetail(String token) {
    return cartService.getCartDetail(token);
  }

  @Override
  public Observable<CartDetailResponse> getCartDetailBuyNow(String token) {
    return cartService.getCartDetailBuyNow(token);
  }

  @Override
  public Observable<CategoryDetailsResponse> getCategoryDetails(String token,
                                                                CaregoryDetailsRequest request) {
    return categoryService.getCategoryDetails(token, request.getCategoryId());
  }

  @Override
  public Observable<ResponseBody> getTreeCategory(String token, String request) {
    return categoryService.getTreeCategory(token, com.skypremiuminternational.app.data.network.URL.GET_TREE_CATEGORY + request);
  }


  @Override
  public Observable<HomeCategoryResponse> getHomeCategories(String token) {
    return homeCategoryService.getHomeCategories(token);
  }

  @Override
  public Observable<FaqResponse> getFaqs(String token) {
    return faqService.getFaqs(token);
  }

  @Override
  public Observable<List<CountryCode>> getCountryCodes() {
    return countryCodesService.getCountryCodes();
  }

@Override
  public Observable<List<CountryCodeCC>> getCountryCodeCc(String auth) {
    return countryCodesService.getCountryCodeCc(auth);
  }

  @Override
  public Observable<PhoneCode> getPhoneCodes() {
    return phoneCodesService.getPhoneCodes();
  }

  @Override
  public Observable<List<Nationality>> getNationalities() {
    return nationalitiesService.getNationalities();
  }

  @Override
  public Observable<Object> getCartId(String auth) {
    return cartService.createCart(auth);
  }

  @Override
  public Observable<ResponseBody> createCartResponseBody(String auth) {
    return cartService.createCartResponseBody(auth);
  }

  @Override
  public Observable<AddCartItemCountRequest.Cart> addToCart(
      AddCartItemCountRequest addCartItemCountRequest,
      String auth) {
    return cartService.addToCart(auth, addCartItemCountRequest);
  }

  @Override
  public Observable<AddCartItemCountRequest.Cart> addToBuyNow(
      AddCartItemCountRequest addCartItemCountRequest,
      String auth) {
    return cartService.addToBuyNowCart(auth, addCartItemCountRequest);
  }

  @Override
  public Observable<ResponseBody> updateBuyNow(String auth) {
    return cartService.updateBuyNowCart(auth);
  }

  @Override
  public Observable<Boolean> removeShoppingCart(String userToken,
                                                String cartItemId) {
    return cartService.removeShoppingCart(userToken, cartItemId);
  }

  @Override
  public Observable<CartAllInformationResponse> getAllCartInformation(String userToken) {
    return cartService.getAllCartInformation(userToken);
  }

  @Override
  public Observable<CartAllInformationResponse> getAllCartInformationBuyNow(String userToken) {
    return cartService.getAllCartInformationBuyNow(userToken);
  }

  @Override
  public Observable<Boolean> deleteAddress(String clientToken, Integer addressId) {
    return userUpdateService.deleteAddress(clientToken, addressId);
  }

  @Override
  public Observable<CheckLimitResponse> checkIfCartHasReachedLimit(String userToken) {
    return cartService.checkIfCartHasReachedLimit(userToken);
  }

  @Override
  public Observable<Boolean> applyPromoCode(String userToken, String promoCode) {
    return cartService.applyPromoCode(userToken, promoCode);
  }

  @Override
  public Observable<Boolean> deletePromoCode(String userToken) {
    return cartService.deletePromoCode(userToken);
  }

  @Override
  public Observable<RoyaltyResponse> applyRoyaltyPts(String userToken, String points) {
    return cartService.applyRoyaltyPts(userToken, points);
  }

  @Override
  public Observable<RoyaltyResponse> deleteRoyaltyPoints(String userToken) {
    return cartService.deleteRoyaltyPoints(userToken);
  }

  @Override
  public Observable<List<CreditCardResponse>> getCreditCards(String userToken) {
    return userDetailService.getCreditCards(userToken);
  }
  @Override
  public Observable<List<CreditCardResponse>> getCreditCardsV2(String userToken) {
    return userDetailService.getCreditCardsV2(userToken);
  }

  @Override
  public Observable<String> placeOrder(String userToken, PlaceOrder.Params params) {
    PlaceOrderRequest request =
        buildPlaceOrderRequest(params.email, params.address, params.creditCard, params.paymentType);
    return cartService.placeOrder(userToken, request).map(Object::toString);
  }

  @Override
  public Observable<String> placeOrderBuyNow(String userToken, PlaceOrderBuyNow.Params params) {
    PlaceOrderRequest request =
        buildPlaceOrderRequest(params.email, params.address, params.creditCard, params.paymentType);
    return cartService.placeOrderBuyNow(userToken, request, "1").map(Object::toString);
  }

  @Override
  public Observable<OrderDetailResponse> getOrderDetail(String clientToken, Integer orderId) {
    return orderHistoryService.getOrderDetail(clientToken, orderId);
  }

  @Override
  public Observable<ProductListResponse> getFilterResult(String userToken, String stringQuery) {
    Observable<ProductListResponse> b = searchProductService.getProductFilter(userToken
        , com.skypremiuminternational.app.data.network.URL.FILTER_PRODUCT + stringQuery);

    return b;
  }

  @Override
  public Observable<ProductListResponse> getFilterResultSort(String userToken, ProductFilterSortRequest request) {

    if (request.getField().equalsIgnoreCase("price")) {
      return searchProductService.getProductFilter(userToken, com.skypremiuminternational.app.data.network.URL.FILTER_PRODUCT + request.toQueryPrice());
    } else if (request.getField().equalsIgnoreCase("popularity_order")) {
      return searchProductService.getProductFilter(userToken, com.skypremiuminternational.app.data.network.URL.FILTER_PRODUCT + request.toQueryPopularity());
    } else if (request.getField().equalsIgnoreCase("name")) {
      return searchProductService.getProductFilter(userToken, com.skypremiuminternational.app.data.network.URL.FILTER_PRODUCT + request.toQueryName());
    } else {
      return searchProductService.getProductFilter(userToken, com.skypremiuminternational.app.data.network.URL.FILTER_PRODUCT + request.toQueryRating());
    }


  }

  private PlaceRenewalOrderWithPointRequest buildPlaceOrderWithPointRequest(
      PlaceRenewalOrder.Params params) {
    List<RegionItem> regions = params.address.getRegion();

    String regionName = "";

    String regionCode = "";
    if (regions != null && regions.size() > 0) {
      RegionItem region = regions.get(0);
      regionName = region.getRegion() != null ? region.getRegion() : "";
      regionCode = region.getRegion() != null ? region.getRegionCode() : "";
    }

    PlaceRenewalOrderWithPointRequest.Address address =
        new PlaceRenewalOrderWithPointRequest.Address(params.email, params.address.getFirstname(),
            params.address.getLastname(), regionName,
            params.address.getRegionId(), regionCode,
            params.address.getCountryId(), params.address.getStreet(),
            params.address.getPostcode(), params.address.getCity(),
            params.address.getTelephone());
    PlaceRenewalOrderWithPointRequest.PaymentMethod paymentMethod =
        new PlaceRenewalOrderWithPointRequest.PaymentMethod("free");
    return new PlaceRenewalOrderWithPointRequest(paymentMethod, address);
  }

  private PlaceOrderRequest buildPlaceOrderRequest(String email, BillingAddress address,
                                                   CreditCardResponse creditCard, String paymentType) {
    PlaceOrderRequest.AdditionalData additionalData;
    if(!TextUtils.isEmpty(creditCard.getId())){
      additionalData =
          new PlaceOrderRequest.AdditionalData(creditCard.getId(), "", "", "", "", "true",
              "", BuildConfig.BuildId, BuildConfig.Chanel);
    }else {
      if(!TextUtils.isEmpty(paymentType)&&paymentType.equalsIgnoreCase("free")){
        additionalData =
            new PlaceOrderRequest.AdditionalData("", "", "", "", "", "true",
                "", BuildConfig.BuildId, BuildConfig.Chanel);
      }else {
        additionalData =
            new PlaceOrderRequest.AdditionalData(creditCard.getId(), "", "", "", "", "true",
                "", BuildConfig.BuildId, BuildConfig.Chanel);
      }
    }

    PlaceOrderRequest.PaymentMethod paymentMethod =
        new PlaceOrderRequest.PaymentMethod(paymentType, additionalData);
    List<RegionItem> regions = address.getRegion();

    String regionName = "";

    String regionCode = "";
    if (regions != null && regions.size() > 0) {
      RegionItem region = regions.get(0);
      regionName = region.getRegion() != null ? region.getRegion() : "";
      regionCode = region.getRegion() != null ? region.getRegionCode() : "";
    }

    PlaceOrderRequest.Address requestAddress =
        new PlaceOrderRequest.Address(email, address.getFirstname(),
            address.getLastname(), regionName,
            address.getRegionId(), regionCode,
            address.getCountryId(), address.getStreet(),
            address.getPostcode(),
            address.getCity(), address.getTelephone());
    return new PlaceOrderRequest(paymentMethod, requestAddress);
  }

  @Override
  public Observable<UpdateUserResponse> addCreditCards(String clientToken,
                                                       AddCreditCardRequest addCreditCardRequest) {
    return cartService.addCreditCards(clientToken, addCreditCardRequest);
  }
  @Override
  public Observable<UpdateUserResponse> addCreditCardsV2(String clientToken,
                                                       AddCreditCardRequest addCreditCardRequest) {
    return cartService.addCreditCardsV2(clientToken, addCreditCardRequest);
  }

  @Override
  public Observable<UpdateUserResponse> updateCreditCard(String userToken,
                                                         UpdateCreditCardRequest updateCreditCardRequest) {
    return cartService.updateCreditCard(userToken, updateCreditCardRequest);
  }

  @Override
  public Observable<UpdateUserResponse> updateCreditCardV2(String userToken,
                                                         UpdateCreditCardRequest updateCreditCardRequest) {
    return cartService.updateCreditCardV2(userToken, updateCreditCardRequest);
  }

  @Override
  public Observable<UpdateUserResponse> deleteCreditCard(String userToken, String cardId) {
    return cartService.deleteCreditCard(userToken, cardId);
  }

  @Override
  public Observable<UpdateUserResponse> setDefaultCreditCard(String userToken, String cardId) {
    SetDefaultCreditCardrequest setDefaultCreditCard = new SetDefaultCreditCardrequest(BuildConfig.BuildId, BuildConfig.Chanel);
    return cartService.setDefaultCreditCard(userToken, cardId, setDefaultCreditCard);
  }
  @Override
  public Observable<UpdateUserResponse> setDefaultCreditCardV2(String userToken, String cardId) {
    SetDefaultCreditCardrequest setDefaultCreditCard = new SetDefaultCreditCardrequest(BuildConfig.BuildId,BuildConfig.Chanel);
    return cartService.setDefaultCreditCardV2(userToken, cardId,setDefaultCreditCard);
  }

  @Override
  public Observable<SetShippingAndBillingResponse> setShippingAndBillingAddress(String userToken,
                                                                                SetShippingAndBillingRequest request) {
    return cartService.setShippingAndBillingAddress(userToken, request);
  }

  @Override
  public Observable<ExtraOrderDetail> getExtraOrderDetail(String clientToken, int orderId) {
    return orderHistoryService.getOrderHistoryExtra(clientToken, orderId);
  }

  @Override
  public Observable<Boolean> addRenewalToCartWithCredit(String userToken) {
    return cartService.addRenewalToCartWithCredit(userToken)
        .map(response -> response.toString().equalsIgnoreCase("true"));
  }

  @Override
  public Observable<Version> getAppVersion(String clientToken) {
    return authService.getAppVersion(clientToken);
  }

  @Override
  public Observable<ItemsItem> getRenewalProduct(String clientToken) {
    return cartService.getRenewalProduct(clientToken);
  }

  @Override
  public Observable<Boolean> addRenewalToCartWithPoints(String userToken) {
    return cartService.addRenewalToCartWithPoints(userToken)
        .map(response -> response.toString().equalsIgnoreCase("true"));
  }

  @Override
  public Observable<String> placeRenewalOrder(String userToken, PlaceRenewalOrder.Params params) {
    if (params.checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS) {
      PlaceRenewalOrderWithPointRequest request = buildPlaceOrderWithPointRequest(params);
      return cartService.placeRenewalOrderWithPoints(userToken, request).map(Object::toString);
    } else {
      PlaceOrderRequest request =
          buildPlaceOrderRequest(params.email, params.address, params.creditCard, params.paymentType);
      return cartService.placeRenewalOrderWithCredit(userToken, request).map(Object::toString);
    }
  }

  @Override
  public Observable<List<AvailableProperty>> getAvailableProperties(List<String> propertyIds,
                                                                    String arrival, String departure,
                                                                    String countryCode, List<String> occupancies,
                                                                    String sessionId, String ipAddress) {
    StringBuilder propertyIdBuilder = new StringBuilder();
    for (String propertyId : propertyIds) {
      propertyIdBuilder.append("&");
      propertyIdBuilder.append("property_id=");
      propertyIdBuilder.append(propertyId);
    }

    String url = BuildConfig.EAN_BASE_URL
        + "/"
        +
        BuildConfig.EAN_API_VERSION
        + "/properties/availability?checkin="
        + arrival
        + "&checkout="
        + departure
        + "&rate_plan_count=100"
        +
        "&currency=SGD&language=en-US&country_code="
        + countryCode
        + joinOccupancies(occupancies)
        + propertyIdBuilder.toString()
        + "&sales_channel=mobile_app&sales_environment="
        + Constants.SALE_ENVIRONMENT
        + "&sort_type=preferred"
        + "&include="
        + Constants.INCLUDE
        + "&billing_terms=123&payment_terms=Payment_CC&partner_point_of_sale=SG";
    String authHeader = new EanAuthHeaderGenerator().generate(BuildConfig.EAN_API_KEY,
        BuildConfig.EAN_SHARED_SECRET);

    return eanService.getAvailableProperties(authHeader, ipAddress,
        sessionId, url).onErrorResumeNext(
        throwable -> {
          if (throwable instanceof HttpException && ((HttpException) throwable).code() == 404) {
            return Observable.just(new ArrayList<>());
          }
          return Observable.error(throwable);
        });
  }

  private String joinOccupancies(List<String> occupancies) {
    StringBuilder builder = new StringBuilder();
    for (String occupancy : occupancies) {
      builder.append("&occupancy=");
      builder.append(occupancy);
    }
    return builder.toString();
  }

  @Override
  public Observable<Map<String, PropertyContent>> getPropertyContents(
      List<String> availablePropertyIds, String sessionId, String ipAddress) {
    StringBuilder propertyIdBuilder = new StringBuilder();
    for (String propertyId : availablePropertyIds) {
      propertyIdBuilder.append("&");
      propertyIdBuilder.append("property_id=");
      propertyIdBuilder.append(propertyId);
    }

    String url = BuildConfig.EAN_BASE_URL
        + "/"
        + BuildConfig.EAN_API_VERSION
        + "/properties/content?language=en-US"
        + propertyIdBuilder.toString();
    String authHeader = new EanAuthHeaderGenerator().generate(BuildConfig.EAN_API_KEY,
        BuildConfig.EAN_SHARED_SECRET);

    return eanService.getPropertyContents(authHeader, ipAddress,
        sessionId, url);
  }

  @Override
  public Observable<PriceCheckResult> checkPrice(CheckPrice.Params params,
                                                 String sessionId, String ipAddress) {

    String url = BuildConfig.EAN_BASE_URL + params.bookingLink;
    String authHeader = new EanAuthHeaderGenerator().generate(BuildConfig.EAN_API_KEY,
        BuildConfig.EAN_SHARED_SECRET);

    return eanService.checkPrice(authHeader, ipAddress,
        sessionId, url)
        .map(response -> priceCheckMapper.map(response, params.roomCount, params.adultCount,
            params.children));
  }

  @Override
  public Observable<List<String>> searchProperties(String query, String token, String secret) {
    try {

      Calendar now = Calendar.getInstance();
      TimeZone tz = TimeZone.getTimeZone("UTC");
      now.setTimeZone(tz);

      String urlStr = "http://"
          + getSearchDomain(now)
          + "/"
          + BuildConfig.AWS_PROTOCOL_VERSION
          + "/search?q="
          + query;

      URL url = new URL(urlStr);

      URI uri =
          new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
              url.getQuery(), url.getRef());

      HttpRequest request = new HttpRequest("GET", uri.getPath(), "q=" + query);

      String hashedEmptyPayload =
          "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
      String date = formatDate(now);
      Signer signer = Signer.builder()
          .awsCredentials(
              new AwsCredentials(token, secret)) //new AwsCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY))
          .header("Host", getSearchDomain(now))
          .header("x-amz-date", date)
          .region(BuildConfig.AWS_REGION)
          .build(request, BuildConfig.AWS_SERVICE, hashedEmptyPayload);

      try {
        return eanService.searchProperties(signer.getSignature(), date, uri.toASCIIString())
            .flatMap(response -> {
              if (response.getHits().getFound() > 0) {
                return Observable.from(response.getHits().getHit())
                    .map(hitItem -> hitItem.getFields().getPropertyId())
                    .toList();
              } else {
                return Observable.just(new ArrayList<>());
              }
            });
      } catch (NoSuchAlgorithmException e) {
        return Observable.error(e);
      }
    } catch (Exception e) {
      return Observable.error(e);
    }
  }

  @Override
  public Observable<List<BookingHistory>> getBookingHistories(String token,
                                                              GetBookingHistories.Params params) {
    if (params.status.equalsIgnoreCase("All")) {
      return eanService.getBookingHistoriesWithoutFilter(token, params.sortDirection,
          params.sortField).map(bookingHistoryMapper::map);
    } else {
      return eanService.getBookingHistoriesWithFilter(token, params.status, params.sortDirection,
          params.sortField).map(bookingHistoryMapper::map);
    }
  }

  @Override
  public Observable<Booking> getBookingDetail(GetBookingDetail.Params params) {
    return eanService.getBookingDetail(Constants.CLIENT_TOKEN, params.bookingId);
  }

  @Override
  public Observable<Booking> getBookingDetailValue(GetBookingDetailValue.Params params) {
    return eanService.getBookingDetail(Constants.CLIENT_TOKEN, params.bookingId);
  }

  @Override
  public Observable<ItineraryResponse> getItinerary(String endpoint,
                                                    String sessionId, String ipAddress) {
    String url = BuildConfig.EAN_BASE_URL + endpoint;
    String authHeader = new EanAuthHeaderGenerator().generate(BuildConfig.EAN_API_KEY,
        BuildConfig.EAN_SHARED_SECRET);

    return eanService.getItinerary(authHeader, ipAddress,
        sessionId, url);
  }

  @Override
  public Observable<List<CardOption>> getPaymentOptions(String paymentOptionLink,
                                                        String sessionId, String ipAddress) {
    String url = BuildConfig.EAN_BASE_URL + paymentOptionLink;
    String authHeader = new EanAuthHeaderGenerator().generate(BuildConfig.EAN_API_KEY,
        BuildConfig.EAN_SHARED_SECRET);

    return eanService.getPaymentOptions(authHeader, ipAddress,
        sessionId, url)
        .map(response -> response.getCreditCard().getCardOptions());
  }

  @Override
  public Observable<BookingDetail> bookRoom(String userToken, BookRoom.Params params,
                                            String sessionId, String ipAddress) {

    return eanService.bookRoom(userToken, bookRequestMapper.map(params, sessionId, ipAddress))
        .flatMap(bookResponse -> Observable.from(bookResponse.getEanResult())
            .first()
            .flatMap(eanResult -> {
              if (eanResult != null
                  && !eanResult.getBookingStatus()
                  && eanResult.getType() != null
                  && eanResult.getType().equals("create.system_failure")) {
                List<BookResponse.Error> errors = new ArrayList<>();
                BookResponse.Error error =
                    new BookResponse.Error(eanResult.getType(), eanResult.getMessage(),
                        new ArrayList<>());
                errors.add(error);
                return Observable.just(bookingDetailMapper.mapFail(errors));
              } else if (eanResult.getErrors() != null && eanResult.getErrors().size() > 0) {
                return Observable.just(bookingDetailMapper.mapFail(eanResult.getErrors()));
              } else {
                return getItinerary(eanResult.getLinks().getRetrieve().getHref(), sessionId,
                    ipAddress)
                    .flatMap(itineraryResponse -> {
                      List<String> propertyIds = new ArrayList<>();
                      propertyIds.add(itineraryResponse.getPropertyId());

                      return getPropertyContents(propertyIds, sessionId, ipAddress)
                          .map(contentMap -> bookingDetailMapper.map(contentMap, itineraryResponse, bookResponse, params.skyDollar));
                    });
              }
            }));
  }

  @Override
  public Observable<Boolean> cancelBooking(String userToken, String bookingId) {
    return eanService.cancelBooking(userToken, bookingId)
        .map(response -> response.toString().equalsIgnoreCase("true"));
  }

  @Override
  public Observable<List<ISOCountry>> getISOCountryCodes() {
    String url = "https://skypremium.s3.amazonaws.com/iSOCountryList.json";//"https://skypremium.s3.amazonaws.com/iSOCountryList.json";//"https://s3-ap-southeast-1.amazonaws.com/codigo-skypremium/iSOCountryList.json";
    return eanService.getISOCountryCodes(url).map(response -> response.countryCodes);

  }

  @Override
  public Observable<InviteFriendDescription> getInviteDescription(String clientToken) {
    return userDetailService.getInviteDescription(clientToken);
  }

  @Override
  public Observable<String> getIpAddress() {
    //return Observable.fromCallable(ipAddressFetcher::fetch);
    return eanService.getIpAddress(com.skypremiuminternational.app.data.network.URL.IP_ADDRESS)
        .map(response -> response.ip);
  }

  @Override
  public Observable<List<Suggestion>> getSuggestions(String keyword, String token, String secret) {

    try {
      Calendar now = Calendar.getInstance();
      TimeZone tz = TimeZone.getTimeZone("UTC");
      now.setTimeZone(tz);

      String urlStr = "http://"
          + getSearchDomain(now)
          + "/"
          + BuildConfig.AWS_PROTOCOL_VERSION
          + "/suggest?q="
          + keyword;

      URL url = new URL(urlStr);

      URI uri =
          new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
              url.getQuery(), url.getRef());

      HttpRequest request = new HttpRequest("GET", uri.getPath(), "q=" + keyword);

      String hashedEmptyPayload =
          "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
      String date = formatDate(now);
      Signer signer = Signer.builder()
          .awsCredentials(
              new AwsCredentials(token, secret)) //new AwsCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY))
          .header("Host", getSearchDomain(now))
          .header("x-amz-date", date)
          .region(BuildConfig.AWS_REGION)
          .build(request, BuildConfig.AWS_SERVICE, hashedEmptyPayload);

      try {
        return eanService.getSuggestions(signer.getSignature(), date, uri.toASCIIString())
            .map(suggestionMapper::map);
      } catch (NoSuchAlgorithmException e) {
        return Observable.error(e);
      }
    } catch (Exception e) {
      return Observable.error(e);
    }
  }

  @Override
  public Observable<List<BillingAddress>> getBillingAddresses(String userToken) {
    return userDetailService.getBillingAddresses(userToken)
        .map(BillingAddressesResponse::getBillingAddresses);
  }

  @Override
  public Observable<BillingAddress> addBillingAddress(String userToken,
                                                      AddBillingAddress.Params params) {
    return userDetailService.addBillingAddress(userToken,
        addBillingAddressRequestMapper.map(params.address));
  }

  @Override
  public Observable<BillingAddress> editBillingAddress(String userToken,
                                                       EditBillingAddress.Params params) {
    return userDetailService.editBillingAddress(userToken,
        editBillingAddressRequestMapper.map(params.billingAddress));
  }

  @Override
  public Observable<Boolean> deleteBillingAddress(String userToken,
                                                  DeleteBillingAddress.Params params) {
    return userDetailService.deleteBillingAddress(userToken, params.addressId)
        .map(response -> response.toString().equalsIgnoreCase("true"));
  }

  @Override
  public Observable<ResponseBody> getTreeCategoryById(String userToken, String request) {
    return categoryService.getTreeCategory(userToken, com.skypremiuminternational.app.data.network.URL.GET_TREE_CATEGORY + request);
  }

 @Override
  public Observable<ResponseBody> getTreeCategoryMini(String userToken, String request) {
    return categoryService.getTreeCategoryMini(userToken, com.skypremiuminternational.app.data.network.URL.GET_TREE_CATEGORY_MINI + request);
  }

  @Override
  public Observable<ResponseBody> getTreeCategoryByCheckList(String userToken, String request) {
    return categoryService.getTreeCategoryByCheckList(userToken, com.skypremiuminternational.app.data.network.URL.GET_TREE_CATEGORY + request);
  }

  @Override
  public Observable<RatingResult> addRatingComment(String userToken, Map<String, String> addCommentRequest) {
    return ratingCommentService.addRatingCommment(userToken, addCommentRequest);
  }


  @Override
  public Observable<ReviewDetailResponse> getReviewDetail(String userToken, Map<String, String> addCommentRequest) {
    return ratingCommentService.getReviewDetail(userToken, addCommentRequest);
  }

  @Override
  public Observable<RatingResult> updateReview(String userToken, Map<String, String> updateReview) {
    return ratingCommentService.updateReview(userToken, updateReview);
  }

  @Override
  public Observable<ProductReviewResponse> getListReviewByProduct(String userToken, Map<String, String> productReviewRequest) {
    return ratingCommentService.getListReviewByProduct(userToken, productReviewRequest);
  }

  @Override
  public Observable<RatingOption> getRatingOption(String userToken) {
    return ratingCommentService.getRatingOption(userToken);
  }

  @Override
  public Observable<ResponseBody> getRatingSummaryByProduct(String userToken, Map<String, String> request) {
    return ratingCommentService.getRatingSummaryByProduct(userToken, request);
  }


  @Override
  public Observable<RatingResult> deleteReview(String userToken, Map<String, String> request) {
    return ratingCommentService.deleteReview(userToken, request);
  }

  private String formatDate(Calendar now) {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat dateFormat =
        new SimpleDateFormat(Constants.AWS_DATE_FORMAT, Locale.getDefault());
    dateFormat.setTimeZone(tz);

    return dateFormat.format(now.getTime());
  }

  private String getSearchDomain(Calendar now) {
    boolean oddMonth = (now.get(Calendar.MONTH) + 1) % 2 != 0;
    return oddMonth ? BuildConfig.AWS_DOMAIN_ODD : BuildConfig.AWS_DOMAIN_EVEN;
  }

  @Override
  public Observable<ResponseBody> updatePassword(String token, String member_number,
                                                 UpdatePasswordRequest updatePasswordRequest) {
    return authService.updatePassword(token, member_number, updatePasswordRequest);
  }

  @Override
  public Observable<ResponseBody> getPasswordHash(String token,
                                                  PasswordHashRequest passwordHashRequest) {
    return authService.passwordHash(token, passwordHashRequest);
  }

  @Override
  public Observable<UserDetailResponse> updateUserDetail(String token, String member_number,
                                                         UpdateUserRequest updateUserRequest) {
    return userUpdateService.getUserDetail(token, member_number, updateUserRequest);
  }

  @Override
  public Observable<SearchProductResponse> searchProduct(String token,
                                                         SearchProductRequest request) {
    if (request.getField().equalsIgnoreCase("popularity_order")) {
      return searchProductService.searchProductByPopularity(token, "%" + request.getKeyword() + "%",
          "%" + request.getKeyword() + "%", request.getField(), request.getDirection(),
          "name", "ASC", request.getCategoryId());
    }
    return searchProductService.searchProduct(token, "%" + request.getKeyword() + "%",
        "%" + request.getKeyword() + "%", request.getField(), request.getDirection(),
        request.getCategoryId());
  }

  @Override
  public Observable<SearchProductResponse> searchAll(String token, SearchProductRequest request) {
    return searchProductService.searchAll(token, "%" + request.getKeyword() + "%",
        "%" + request.getKeyword() + "%", request.getField(), request.getDirection());
  }

  @Override
  public Observable<SearchHomeResponse> searchHome(String token, SearchProductRequest request) {
    return searchProductService.searchHome(request.getKeyword(), request.getField(),
        request.getDirection());
  }

  @Override
  public Observable<SearchProductResponse> searchHomeProducts(String token,
                                                              SearchProductRequest request) {
    return searchProductService.searchHomeProducts(token, request.getCategoryId(),
        request.getField(), request.getDirection());
  }

  @Override
  public Observable<FavouriteResponse> addToFavourite(String token, String productId) {
    return favouriteService.addToFav(token, productId)
        .map(favouriteResponses -> favouriteResponses.get(0));
  }

  @Override
  public Observable<FavouriteResponse> removeFromFavourite(String token, String wishListItemId) {
    return favouriteService.removeFromFav(token, wishListItemId)
        .map(favouriteResponses -> favouriteResponses.get(0));
  }

  @Override
  public Observable<CartDetailItem> addCartDetailItemCount(String userToken, String itemId,
                                                           UpdateItemCountRequest addCartRequest) {
    return cartService.addCartItemCount(userToken, itemId, addCartRequest);
  }

  @Override
  public Observable<List<CartDetailItem>> getCartItems(String token) {
    return cartService.getCartItems(token);
  }

  @Override
  public Observable<List<CartDetailItem>> getCartItemsBuyNow(String token) {
    return cartService.getCartItemsBuyNow(token);
  }


  @Override
  public Observable<UserDetailResponse> updateUserDetail(String token, String member_number, UpdateUserDeliveryRequest updateUserRequest) {
    return userUpdateService.getUserDetail(token, member_number, updateUserRequest);
  }

  @Override
  public Observable<BillingAddressPayment.BillingAddressPaymentRespond> requestBillingAddressPayment(String userToken, RequestBody params) {
    return userDetailService.requestBillingAddressPayment(userToken, params);
  }

  @Override
  public Observable<DetailsItem> getDetailCategory(String token, String category_id) {
    return detailsService.getDetailCategory(token, category_id);
  }

  @Override
  public Observable<ResponseBody> getTokenRenewal(String token, Map<String, String> params) {
    return authService.getTokenRenewal(token, params);
  }

  @Override
  public Observable<ResponseBody> sendNotification(String token, NotificationRequest skyNotification) {
    return notificationService.sendNotification(token, skyNotification);
  }

  @Override
  public Observable<NotificationResponse> getNotification(String token, String memberId, String deviceId) {
    return notificationService.getNotification(token, memberId, deviceId);
  }
  @Override
  public Observable<ResponseBody> updateReadNotification(String token, NotificationUpdateRequest request) {
    return notificationService.updateReadNotification(token, request);
  }

  @Override
  public Observable<ResponseBody> deleteNotification(String token, NotificationDeleteRequest request) {
    return notificationService.deleteNotification(token, request);
  }

  @Override
  public Observable<CrmTokenResponse> createCrmToken(CrmTokenRequest request) {
    return authService.createToken(request);
  }
  @Override
  public Observable<SettingNotificationResponse> getNotificationSetting(String oAuth, String memberId) {
    return notificationService.getNotificationSetting(oAuth, memberId);
  }

  @Override
  public Observable<ResponseBody> updateSettingNotification(String auth, SettingNotificationRequest request) {
    return notificationService.updateSettingNotification(auth, request);
  }
  @Override
  public Observable<ResponseBody> changeFirstTimeLogin(String auth, FirstTimeToTrueRequest request) {
    return notificationService.changeFirstTimeLogin(auth, request);
  }
  @Override
  public Observable<ResponseBody> updateMappVersion(String auth, UpdateMappVersionRequest request) {
    return notificationService.updateMappVersion(auth, request);
  }
  @Override
  public Observable<OSPlayer> getDeviceInfo(String playerId) {
    return skyOneSignalSevices.getDeviceInfo(playerId, BuildConfig.OS_APP_ID);
  }

  @Override
  public Observable<CheckLoginResponse> checkFirstLogin(String oAuth, String memberNumber) {
    return notificationService.checkFirstLogin(oAuth, memberNumber);
  }

  @Override
  public Observable<ResponseBody> updateOSDataToCRM(String oAuth, OneSignalDeviceDataRequest request) {
    return notificationService.updateOSDataToCRM(oAuth, request);
  }

  @Override
  public Observable<Boolean> checkAddCardFirstTime(String token) {
    return authService.checkAddCardFirstTime(token);
  }

  @Override
  public Observable<FirstTimePopup> getFirstTimePopup(String userToken) {
    return popupService.getFirstTimePopup(userToken);
  }
  @Override
  public Observable<ResponseBody> setFirstTime(String userToken) {
    return popupService.setFirstTime(userToken);
  }


  @Override
  public Observable<SkyDollarHistoryResponse> getSkyDollarHistory(String oauth,String memberNumber,String currentPage, String limit) {
    return mySkyDollarServices.getSkyDollarHistory(oauth,memberNumber,currentPage,limit);
  }

  @Override
  public Observable<List<OutletItem>> getOutletByProductID(String oAuth, String productId) {
    return hgwService.getOutletByProductID(oAuth, productId) ;
  }

  @Override
  public Observable<ReservationTimeResponse> getReservationTime(String date, String restaurantId, String partnerCode, String partnerAuthCode) {
    return hgwService.getReservationTime(date, restaurantId, partnerCode, partnerAuthCode) ;
  }


  @Override
  public Observable<RestaurantMessageResponse> getRestaurantMsg(String restaurantId) {
    return hgwService.getRestaurantMsg(restaurantId) ;
  }


  @Override
  public Observable<ReservationResultResponse> sendCreateReservation(String userToken, Map<String,String> mapRequest) {
    return hgwService.sendCreateReservation(userToken, mapRequest);
  }

  @Override
  public Observable<ReservationResultResponse> sendEditReservation(String userToken,
                                                    Map<String,String> mapRequest,String reservationId,
                                                    String verificationKey) {
    return hgwService.sendEditReservation(userToken, mapRequest, reservationId, verificationKey);
  }
 @Override
  public Observable<CancelReservationResponse> cancelReservation(String userToken,
                                                                 String reservationId,
                                                                 String verificationKey) {
    return hgwService.cancelReservation(userToken, reservationId, verificationKey);
  }

  @Override
  public Observable<ReserveHistoryItem> getDetailReservationBooking(String userToken, String bookingId) {
    return hgwService.getDetailReservationBooking(userToken, bookingId);
  }

  @Override
  public Observable<ReserveHistoryRespone> getHistoryAll(String userToken, String memberNumber, String sortBy,String currentPage, String pageSize) {
    return hgwService.getHistoryAll(userToken,
        "entity_id",
        memberNumber,
        "eq",

        sortBy,
        "reservation_date"
        /*,
        currentPage, pageSize*/);
  }

  @Override
  public Observable<ReserveHistoryRespone> getHistoryFilter(String userToken, String memberNumber, String sortBy,String refineBy, String currentPage, String pageSize) {
    return hgwService.getHistoryFilter(userToken,
        "entity_id",
        memberNumber,
        "eq",

        sortBy,
        "reservation_date",

        "reservation_status",
        refineBy,
        "eq"
        /*,
        currentPage, pageSize*/);
  }

  @Override
  public Observable<DemoResponse> getProduct(String auth,String path) {
    return demoServices.getProduct(auth ,path);
  }
  @Override
  public Observable<DemoRes> getProductTwo(String auth) {
    return demoServices.getProductTwo(auth);
  }


}
