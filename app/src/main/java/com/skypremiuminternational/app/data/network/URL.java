package com.skypremiuminternational.app.data.network;

import com.skypremiuminternational.app.BuildConfig;

import static com.skypremiuminternational.app.BuildConfig.API_V2;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public class URL {

  public static final String IP_ADDRESS = "https://api.ipify.org?format=json";

  public static final String GET_COUNTRY ="https://skypremium.s3.amazonaws.com/CountryCodes.json";
      //"https://skypremium.s3.amazonaws.com/CountryCodes.json";
      //"https://s3-ap-southeast-1.amazonaws.com/codigo-skypremium/CountryCodes.json";
  public static final String GET_PHONE_CODE =
      "https://skypremium.s3.amazonaws.com/iSOCountryList.json";//"https://s3-ap-southeast-1.amazonaws.com/codigo-skypremium/DialingCodes.json";
  public static final String GET_NATIONALITIES ="https://skypremium.s3.amazonaws.com/Nationalities.json";
        //"https://skypremium.s3.amazonaws.com/Nationalities.json";
        //"https://skypremium.s3.amazonaws.com/Nationalities.json";//"https://s3-ap-southeast-1.amazonaws.com/codigo-skypremium/Nationalities.json";
  private static final String SEARCH_PAGE_SIZE = "200";
  private static final String PAGE_SIZE = "100";
  private static final String CURRENT_PAGE = "1";
  private static final String VISIBILITY = "1";
  private static final String API = BuildConfig.API;
  private static final String API_HGW = BuildConfig.API_HGW;
  //public static final String SIGN_IN = API + "/integration/customer/token";
  /*Lev*/
  public static final String SIGN_IN = API + "/integration/customer/token-access-key";
  /*EndLev*/
  public static final String ADMIN_SIGN_IN = API + "/integration/admin/token";
  public static final String FORGOT_PASSWORD = API + "/customers/password";
  public static final String UPLOAD_AVATAR = API + "/updateCustomersV2/uploadPictureProfile";
  public static final String UPDATE_PASSWORD = API + "/updateCustomersV2/{member_number}";
  public static final String PASSWORD_HASH = API + "/passwordHash";
  public static final String RENEWAL_TOKEN = API + "/token";
  public static final String CONFIGCOUPON = API + "/Skypremium/Checkout/getConfigAllowPromotionCode";
  public static final String CONFIGSKY = API + "/Skypremium/Checkout/getConfigAllowSkyDollar";

  public static final String ADD_RATING = API + "/skypremium-review/addReview";
  public static final String GET_REVIEW_DETAIL = API + "/skypremium-review/getReviewDetail";
  public static final String GET_PRODUCT_REVIEW_LIST = API + "/skypremium-review/getReviewProduct";
  public static final String EDIT_REVIEW = API + "/skypremium-review/editReview";
  public static final String GET_RATING_SUMMARY = API + "/skypremium-review/getRatingValueProduct";
  public static final String DELETE_REVIEW = API + "/skypremium-review/deleteReview";
  public static final String GET_RATING_OPTION = API + "/skypremium-review/getRatingDetail";



  // 20200428  - WIKI Toan Tran - config url for membership renewal
  public static final String RENEWAL = BuildConfig.RENEWAL_DOMAIN + "/membership/renewal?tok=";
  /*+ "&searchCriteria[filterGroups][0][filters][0][value]="
      + Constants.WINE_AND_DINE*/
  /* /products?searchCriteria[pageSize]="
      + PAGE_SIZE
      + "&searchCriteria[currentPage]=" */
  public static final String WINE_AND_DINE = API
      + "/products?searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][0][filters][0][field]=category_id"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][1][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=neq";
  public static final String SHOPPING = API
      + "/products?searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][0][filters][0][field]=category_id"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][1][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=neq";
  public static final String WELLNESS = API
      + "/products?searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][0][filters][0][field]=category_id"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][1][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=neq";
  public static final String TRAVEL = API
      + "/products?searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][0][filters][0][field]=category_id"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][1][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=neq";

  public static String PARAMS_FILTER = "searchCriteria[currentPage]=1&searchCriteria[filterGroups][0][filters][0][field]=category_id&searchCriteria[filterGroups][0][filters][0][value]=55&searchCriteria[filterGroups][0][filters][0][conditionType]=eq&searchCriteria[filterGroups][1][filters][0][field]=visibility&searchCriteria[filterGroups][1][filters][0][conditionType]=neq&searchCriteria[filterGroups][1][filters][0][value]=1";

  public static final String DETAILS = API + "/products/{sku}";
  public static final String RECOMMENDATIONS = API + "/products/{sku}/links/target-rule/related/category/{category_id}";
  //public static final String USER_DETAIL = API + "/customers/me";
  public static final String USER_DETAIL = API + "/customers/meV2";
  public static final String GET_FAVOURITE = API + "/wishlist/items";


  public static final String GET_ALL_FAVOURITE = GET_FAVOURITE
          +"?searchCriteria[filter_groups][0][filters][0][field]="
          +"&searchCriteria[filter_groups][0][filters][0][value]="
      + "&searchCriteria[sortOrders][0][field]=added_at"
      + "&searchCriteria[sortOrders][0][direction]=ASC";

  public static final String GET_FAVOURITE_WITH_CATEGORY = GET_FAVOURITE
      + "?searchCriteria[filter_groups][1][filters][0][field]=category_id"
      + "&searchCriteria[filter_groups][0][filters][0][field]=partner_type";

  public static final String GET_FAVOURITE_WITHOUT_CATEGORY =
      GET_FAVOURITE + "?searchCriteria[filter_groups][0][filters][0][field]=partner_type";

  public static final String GET_CATEGORIES = API + "/categories";
  public static final String GET_CATEGORY_DETAILS = API + "/categories/{categoryID}";
  public static final String GET_TREE_CATEGORY = API + "/tree-filter?";
  public static final String GET_TREE_CATEGORY_MINI = API + "/tree-filter-mini?";
  public static final String GET_HOME_CATEGORIES = API + "/homebanner/get";
  public static final String GET_FAQ = API + "/faq/faqs";
  public static final String USER_UPDATE = API + "/updateCustomersV2/{member_number}";

  public static final String FEATURE_WINE_AND_DINE =
      API + "/categories/{categoryID}/featuredproducts";

  public static final String FEATURE_SHOPPING = API + "/categories/{categoryID}/featuredproducts";

  public static final String DEMO_PARAM = API +"/products" +
          "?searchCriteria[currentPage]=1&" +

          "searchCriteria[filterGroups][0][filters][0][field]=visibility&" +
          "searchCriteria[filterGroups][0][filters][0][conditionType]=eq&" +
          "searchCriteria[filterGroups][0][filters][0][value]=4&" +

          "searchCriteria[filterGroups][5][filters][1][field]=status&" +
          "searchCriteria[filterGroups][5][filters][1][conditionType]=eq&" +
          "searchCriteria[filterGroups][5][filters][1][value]=1&" +

          "searchCriteria[pageSize]=30&" +
          "rootCategory=55&" +

          "searchCriteria[filterGroups][1][filters][0][field]=category_id&" +
          "searchCriteria[filterGroups][1][filters][0][conditionType]=eq&" +
          "searchCriteria[filterGroups][1][filters][0][value]=55&" +

          "searchCriteria[sortOrders][11][field]=popularity_order&" +
          "searchCriteria[sortOrders][11][direction]=ASC&" +

          "searchCriteria[sortOrders][12][field]=name&" +
          "searchCriteria[sortOrders][12][direction]=ASC";

  public static final String FEATURE_WELLNESS = API + "/categories/{categoryID}/featuredproducts";

  public static final String FEATURE_TRAVEL = API + "/categories/{categoryID}/featuredproducts";

  public static final String SEARCH_PRODUCT = API
      + "/products?"
      + "searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][2][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][2][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][2][filters][0][conditionType]=neq"
      + "&searchCriteria[filterGroups][0][filters][0][field]=name"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=like"
      + "&searchCriteria[filterGroups][0][filters][1][field]=description"
      + "&searchCriteria[filterGroups][0][filters][1][conditionType]=like"
      + "&searchCriteria[filterGroups][1][filters][0][field]=category_id"
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq";

  public static final String SEARCH_ALL = API
      + "/products?"
      + "searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][2][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][2][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][2][filters][0][conditionType]=neq"
      + "&searchCriteria[filterGroups][0][filters][0][field]=name"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=like"
      + "&searchCriteria[filterGroups][1][filters][0][field]=description"
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=like";

  public static final String SEARCH_HOME = API
      + "/search?"
      + "searchCriteria[requestName]=quick_search_container"
      + "&searchCriteria[filterGroups][0][filters][0][field]=search_term"
      + "&fields=items[id]";

  public static final String SEARCH_HOME_PRODUCTS = API
      + "/products?"
      + "searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][1][filters][0][field]=visibility"
      + "&searchCriteria[filterGroups][1][filters][0][value]="
      + VISIBILITY
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=neq"
      + "&searchCriteria[filterGroups][0][filters][0][field]=entity_id"
      + "&searchCriteria[filterGroups][0][filters][0][condition_type]=in";
  public static final String ADD_TO_FAVOURITE = API + "/wishlist/add/{product_id}";

  public static final String REMOVE_FROM_FAVOURITE = API + "/wishlist/delete/{wishlist_item_id}";

  public static final String REMOVE_SHOPPING_CART = API + "/carts/mine/items/{cart_item_id}";

  public static final String CART = API + "/carts/mine";

  public static final String CART_BUY_NOW = API + "/carts/mine?is_buynow=1";

  public static final String CART_ITEMS = API + "/carts/mine/items?is_buynow=0";

  public static final String CART_ITEMS_BUY_NOW = API + "/carts/mine/items?is_buynow=1";

  public static final String ADD_TO_BUY_NOW = API + "/carts/mine/items_buynow";

  public static final String UPDATE_BUY_NOW = API + "/carts/mine/update_cart";

  public static final String ALL_CART_INFORMATION = API + "/carts/mine/totals?is_buynow=0";

  public static final String ALL_CART_INFORMATION_BUY_NOW = API + "/carts/mine/totals?is_buynow=1";

  public static final String ORDER_HISTORY = API + "/orders?"
      + "searchCriteria[filterGroups][0][filters][0][field]=customer_email&searchCriteria[filterGroups][0][filters][0][value]=email"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[sortOrders][0][field]=created_at"
      + "&searchCriteria[sortOrders][0][direction]=selected_sorting";

  public static final String ORDERHISTORY_WITH_CATEGORY = API
      + "/orders?"
      + "searchCriteria[filterGroups][0][filters][0][field]=customer_email&searchCriteria[filterGroups][0][filters][0][value]=email"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][field]=status"
      + "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq"
      + "&searchCriteria[filterGroups][1][filters][0][value]=selected_category"
      + "&searchCriteria[sortOrders][0][direction]=selected_sorting";
  public static final String FILTER_PRODUCT = API
    + "/products?";
  public static final String CART_DETAIL = API + "/carts/mine?is_buynow=0";

  public static final String CART_DETAIL_BUY_NOW = API + "/carts/mine?is_buynow=1";

  public static final String CART_ITEM_COUNT_UPDATE = API + "/carts/mine/items/{item_id}";

  public static final String DELETE_ADDRESS = API + "/addresses/{address_id}";

  public static final String CHECK_CART_LIMIT = API + "/carts/mine/checkLimit";

  public static final String APPLY_PROMO_CODE = API + "/carts/mine/coupons/{coupon_code}";

  public static final String DELETE_PROMO_CODE = API + "/carts/mine/coupons";

  public static final String APPLY_ROYAL_POINT =
      API + "/carts/mine/loyalty_redeemed/{royalty_points}";

  public static final String DELETE_ROYALTY_POINT = API + "/carts/mine/loyalty_redeemed";

  public static final String GET_CREDIT_CARDS = API + "/customers/mine/stripe_creditcards?buildId="+BuildConfig.BuildId+"&chanel="+BuildConfig.Chanel;

  public static final String GET_CREDIT_CARDS_INFO = API_V2 + "/customers/mine/retrieveCard/{card_id}";

  public static final String GET_CREDIT_CARDS_V2 = API_V2 + "/customers/mine/stripe_creditcards?buildId="+BuildConfig.BuildId+"&chanel="+BuildConfig.Chanel;

  public static final String PLACE_ORDER = API + "/carts/mine/payment-information";

  public static final String PLACE_ORDER_BUY_NOW = API + "/carts/mine/payment-information";

  public static final String ORDER_DETAIL = API + "/orders/{order_id}";

  public static final String ORDER_HISTORY_EXTRA = API + "/orders/{order_id}/order_history_extra";

  public static final String ADD_CREDIT_CARDS = API + "/customers/mine/add_stripe_creditcard/";

  public static final String API_DEMO = API + "/customers/mine/add_stripe_creditcard/";

  public static final String ADD_CREDIT_CARDS_V2 = API_V2 + "/customers/mine/add_stripe_creditcard/";

  public static final String UPDATE_CREDIT_CARDS = API + "/customers/mine/edit_stripe_creditcard/";

  public static final String UPDATE_CREDIT_CARDS_V2 = API_V2 + "/customers/mine/edit_stripe_creditcard/";

  public static final String DELETE_CREDIT_CARD =
      API_V2 + "/customers/mine/remove_stripe_creditcard/{card_id}?buildId="+BuildConfig.BuildId+"&chanel="+BuildConfig.Chanel;
//
//  public static final String DELETE_CREDIT_CARD =
//      API + "/customers/mine/remove_stripe_creditcard/{card_id}?buildId="+BuildConfig.BuildId+"&chanel="+BuildConfig.Chanel;

  public static final String SET_DEFAULT_CREDIT_CARD =
      API + "/customers/mine/makedefault_stripe_creditcard/{card_id}";

  public static final String SET_DEFAULT_CREDIT_CARD_V2 =
      API_V2 + "/customers/mine/makedefault_stripe_creditcard/{card_id}";

  public static final String SET_SHIPPING_AND_BILLING = API + "/carts/mine/shipping-information";

  public static final String ADD_RENEWAL_TO_CART_WITH_CREDIT =
      API + "/carts/mine/items/membership-renewal/pay_full";

  public static final String GET_APP_VERSION = API + "/app_version.json";

  public static final String CHECK_ADD_CARD_FIRST_TIME = API_V2 + "/customers/mine/add_stripe_is_show_form";

  public static final String GET_COUNTRY_CC = API_V2 + "/customers/mine/getCountryList";

  public static final String GET_RENEWAL_PRODUCT = API + "/products/membership-renewal";

  public static final String ADD_RENEWAL_TO_CART_WITH_POINTS =
      API + "/carts/mine/items/membership-renewal/sky_dollar_full";
  public static final String PLACE_RENEWAL_ORDER = API + "/carts/mine/payment-information";

  public static final String GET_BOOKING_HISTORIES_WITH_FILTER = API
      + "/customers/mine/ean/history_booking?"
      + "searchCriteria[pageSize]="
      + SEARCH_PAGE_SIZE
      + "&searchCriteria[currentPage]="
      + CURRENT_PAGE
      + "&searchCriteria[filterGroups][0][filters][0][field]=booking_status"
      + "&searchCriteria[filterGroups][0][filters][0][conditionType]=eq";

  public static final String GET_BOOKING_HISTORIES_WITHOUT_FILTER = API
      + "/customers/mine/ean/history_booking?"
      + "searchCriteria[pageSize]="
      + SEARCH_PAGE_SIZE
      + "&searchCriteria[currentPage]="
      + CURRENT_PAGE;
  public static final String GET_BOOKING_DETAIL = API + "/ean/history_booking/{booking_id}";

  public static final String CREATE_BOOKING = API + "/booking/itineraries";

  public static final String CANCEL_BOOKING =
      API + "/customers/mine/ean/cancel_booking/{booking_id}";

  public static final String GET_INVITE_FRIEND_DESCRIPTION = API + "/invite/description";

  public static final String GET_BILLING_ADDRESSES = API + "/customers/me/customBillingAddress";

  public static final String ADD_BILLING_ADDRESSES = API + "/customers/me/customBillingAddress";

  public static final String EDIT_BILLING_ADDRESSES = API + "/customers/me/customBillingAddress";

  public static final String DELETE_BILLING_ADDRESS = API + "/customers/me/customBillingAddress/delete/{address_id}";

  public static final String BILLING_ADDRESS_PAYMENT = API + "/carts/mine/billing-address-payment";

  public static final String GET_DETAIL_CATEGORIE = API + "/categories/{category_id}";

  /*CRM API LINK*/

  public static final String CRM_BASE = BuildConfig.CRM_BASE ;

  public static final String CRM_API  = CRM_BASE + BuildConfig.CRM_API;
  public static final String GET_TRANSACTION_SKY_DOLLAR = CRM_API + "/GetMemberValueTransactionsHistory/{member_number}";//?current_page=3&limit=50

  public static final String GET_FIRST_TIME_POPUP = CRM_API + "/skypremium-popconfig/getPopUp";

  public static final String SET_FIRST_TIME = CRM_API + "/skypremium-popconfig/setFirstLogin";


  /*RESERVATION API*/


  public static final String GET_OUTLET_BY_PRODUCT = API + "/skypremium-hgw/getOptionOutlet/{product_id}";

  public static final String SEND_CREATE_RESERVATION = API + "/skypremium-hgw/sendBooking";

  public static final String CANCEL_RESERVATION = API + "/skypremium-hgw/cancelReservation/{reservation_id}/{verificationKey}";

  public static final String SEND_EDIT_RESERVATION = API + "/skypremium-hgw/editReservation/{reservation_id}/{verificationKey}";


  // FROM HGW
  public static final String GET_RESERVATION_TIME = API_HGW + "/tabledb-web/middlelayer/availability/slots/{date}/{restaurantId}/{partnerCode}/{partnerAuthCode}";

  public static final String GET_RESTAURANT_MSG = API_HGW + "/tabledb-web/restaurantMessageSetting/{restaurantId}";

  public static final String GET_HISTORY = API + "/skypremium-hgw/reservation/history";

  public static final String GET_DETAIL_RESERVATION_BOOKING = API + "/skypremium-hgw/history-detail/{booking_id}";

  //public static final String GET_HISTORY_UNCONFIRM = API + "/tabledb-web/middlelayer/availability/slots/{date}/{restaurantId}/{partnerCode}/{partnerAuthCode}";





  public static final String CREATE_TOKEN =  CRM_API + "/oauth2/token";

  public static final String SEND_NOTIFICATION = CRM_API + "/Notification";

  public static final String GET_NOTIFICATION = CRM_API + "/Notification";

  public static final String DELETE_NOTIFICATION = CRM_API + "/Notification";

  public static final String UPDATE_READ_NOTIFICATION = CRM_API + "/Notification";

  public static final String UPDATE_SETTING_NOTIFICATION = CRM_API + "/NotificationSettings";

  public static final String UPDATE_OS_DATA_TO_CRM = CRM_API + "/NotificationSettings";

  public static final String GET_SETTING_NOTIFICATION = CRM_API + "/NotificationSettings";

  public static final String CHANGE_FIRST_TIME_LOGIN = CRM_API + "/MappLogin";

  public static final String UPDATE_MAPP_VERSION = CRM_API + "/MappLogin";

  public static final String CHECK_FIRST_LOGIN = CRM_API + "/MappLogin";


  /*ONE SIGNAL API LINK*/

  public static final String ONESIGNAL_API  = BuildConfig.ONE_SIGNAL_BASE + BuildConfig.ONE_SIGNAL_API;
  public static final String GET_DEVICES_INFO = ONESIGNAL_API + "/players/{player_id}";

}
