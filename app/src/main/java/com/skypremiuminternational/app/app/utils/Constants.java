package com.skypremiuminternational.app.app.utils;

import com.skypremiuminternational.app.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by johnsonmaung on 1/7/17.
 *
 *  Author: Levister Gutierrez
 *  Task: To include the AWS Access Token and Keys on Cloudsearch APIs, add the variable "key"
 *     and "secret"
 *  Included: HMS_TOKEN_KEY & HMS_SECRET_KEY
 *  Date: 15 Apr 2019
 */
public class Constants {

  public static final String[] CHILDREN_AGES = new String[]{
      "Under 1", "1yo", "2yo", "3yo", "4yo", "5yo", "6yo", "7yo", "8yo", "9yo", "10yo", "11yo",
      "12yo",
      "13yo", "14yo", "15yo", "16yo", "17yo"
  };

  public static final int[] CHILDREN_AGES_IN_DIGIT = new int[]{
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
  };

  public static final String[] EU_COUNTRY_CODE = new String[]{
      "AT", "BE", "BG", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "GR", "HU", "HR", "IE", "IT",
      "LV", "LT", "LU", "MT", "NL", "PL", "PT", "RO", "SK", "SI", "ES", "SE", "GB"
  };
  public static final String BOOKING_STATUS_CONFIRMED = "confirmed";
  //public static final String CUSTOMER_IP = "103.224.164.53";
  //public static final String CUSTOMER_SESSION_ID = "103.224.164.53";
  public static final String SALE_ENVIRONMENT = "hotel_only";
  public static final String FILTER = "refundable";
  public static final String INCLUDE = "all_rates";
  public static final String CATEGORY_HOTELS_DIRECT = "Hotels (Direct)";
  public static final String CHECKOUT_STATUS_PROCESSING = "Processing";
  public static final String CHECKOUT_STATUS_COMPLETE = "complete";
  public static final String CHECKOUT_STATUS_SHIPPING = "shipping";


  /**
   * (Not Logged In)
   */
  public static final int GROUP_ID_NOT_LOGGED_IN = 0;

  /**
   * Gold members will have access to all pillars & features.
   */
  public static final int GROUP_ID_GOLD = 1;

  /**
   * [Reserved for future use]
   */
  public static final int GROUP_ID_PLATINUM = 2;

  /**
   * [Reserved for future use]
   */
  public static final int GROUP_ID_LITE = 3;

  /**
   * (Gold Member Active)
   * <p>
   * Active member can login to MWEB/MAPPS normally.
   */
  public static final int GROUP_ID_ACTIVE = 11;

  /**
   * (Gold Member Inactive / Expired / Cancelled)
   * <p>
   * "Members under this group can login to MWEB/MAPPS but can only access ""Member Profile"" & perform ""Membership Renewal"". Objective is to allow members with inactive, expired or cancelled memberships to renew their memberships in the system.
   * All other screens, pages, contents & features will not be accessible until they successful reinstate their memberships (upon which the system should move them to the ""Gold Member Active"" group (key = 11).
   * Display message on the home screen after successful login:
   * "Your membership account is inactive. Please renew your membership to continue to enjoy your benefits and privileges.
   * Kindly contact our membership services team (email: membersg@skypremium.com or contact: +65 6533-0000) from Mondays to Fridays, except public holidays, from 09:00 to 18:00 for further assistance."
   */
  public static final int GROUP_ID_EXPIRED = 12;

  /**
   * (Gold Member Awaiting Renewal (Grace))
   * <p>
   * Members under this group can login to MWEB/MAPPS but can only access ""Member Profile"" & perform ""Membership Renewal"" + ""Loyalty Point Redemptions"".
   * Objective is to allow members with inactive, expired or cancelled memberships to either renew their memberships, or to redeem their loyalty points, in the system.
   * All other screens, pages, contents & features will not be accessible until they successful reinstate their memberships (upon which the system should move them to the ""Gold Member Active"" group (key = 11).
   * Display message on the home screen after successful login:
   * "Your membership account has been extended for this grace period. Please renew your membership to continue to enjoy your benefits and privileges. Kindly contact our membership services team
   * (email: membersg@skypremium.com or contact: +65 6533-0000) from Mondays to Fridays, except public holidays, from 09:00 to 18:00 for further assistance."
   */
  public static final int GROUP_ID_AWAITING_RENEWAL = 13;

  /**
   * (Gold Member Blacklisted / Terminated)
   * <p>
   * Members under this group are not allowed to log in to member site. Objective is not to allow these members any further access, unless otherwise reviewed & reinstated manually by the membership services team in the CRM.
   * Display message on the login screen itself:
   * "Your membership account has been suspended. Kindly contact our membership services team
   * (email: membersg@skypremium.com or contact: +65 6533-0000) from Mondays to Fridays, except public holidays, from 09:00 to 18:00 for further assistance."
   */
  public static final int GROUP_ID_TERMINATED = 14;

  public static final String PATTERN_CURRENT_SERVER_TIME = "dd-MM-yyyy HH:mm:ss";
  public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_TIME = "HH.mm";
  public static final String PATTERN_DATE_FULL = "yyyy-MMMM-dd";
  public static final String PATTERN_DATE_SEMI = "yyyy-MMM-dd";
  public static final String PATTERN_DATE_SEMI_DAY_NEM = "EEE yyyy-MMM-dd";
  public static final String PATTERN_DATE_SEMI_DAY_NEM_SP = "EEE yyyy MMM dd";
  public static final String PATTERN_DATE_SEMI_DAY_NEM_SP_REV = "EEE dd MMM yyyy";
  public static final String PATTERN_DATE_SHORT = "yyyy-MM-dd";
  public static final String PATTERN_TIME_SHORT = "hh.mma";

  public static final String PATTERN_DATE_FULL_SP_REV = "dd MMMM yyyy";
  public static final String PATTERN_DATE_SEMI_REV = "dd-MMM-yyyy";
  public static final String PATTERN_DATE_SHORT_REV = "dd-MM-yyyy";

  public static final int STATUS_VALID_PRODUCT = 1;
  public static final String SHIPPING_CARRIER_CODE = "shippingcharges";
  public static final String SHIPPING_METHOD_CODE = "shippingcharges";

  public static final String DEAL_STATUS_DISABLED = "0";
  public static final String DEAL_STATUS_ENABLE = "1";

  public static final String DISCOUNT_TYPE_FIXED = "fixed";
  public static final String DISCOUNT_TYPE_PERCENT = "percent";

  public static final String FAVOURITE_PARTNER_TYPE_PARTNERS = "1";
  public static final String FAVOURITE_PARTNER_TYPE_PRODUCTS = "0";

  public static final int MAX_FAV_COUNT = 100;
  public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
  /* To get the aws cloudsearch access */
  /* Author: Levister Gutierrez */
  public static final String HMS_TOKEN_KEY = "HMS_TOKEN_KEY";
  public static final String HMS_SECRET_KEY = "HMS_SECRET_KEY";
  /* End */
  public static final String CLIENT_TOKEN = "Bearer "+ BuildConfig.USER_TOKEN; ;

  public static final String DEEP_LINK_LANDING = "Screen";

  public static final int DEEP_LINK_LANDING_TRAVEL = 0;

  public static final int DEEP_LINK_LANDING_WINE = 1;

  public static final int DEEP_LINK_LANDING_HOME = 2;

  public static final int DEEP_LINK_LANDING_SHOPPING = 3;

  public static final int DEEP_LINK_LANDING_WELLNESS = 4;

  public static final String AWS_DATE_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

  public static final String BOOKING_DATE_FORMAT = "dd MMM yyyy (E)";

  public static final String DATE_RANGE_FORMAT = "dd MMM yyyy";

  public static final String DAY_RANGE_FORMAT = "dd";

  public static final String E_STORE_NAME = "E-Store";

  public static final String WINE_AND_DINE_NAME = "Wine & Dine";

  public static final String SHOPPING_NAME = "Shopping";

  public static final String WELLNESS_NAME = "Wellness";

  public static final String TRAVEL_NAME = "Travel";

  public static final String SKU_MEMBERSHIP_RENEWAL = "membership-renewal";

  public static final String COUNTRY_DEFAULT = "Singapore ";

  public static final String PHONE_CODE_DEFAULT = "65";

  public static String E_STORE = "55";

  public static String WINE_AND_DINE = "5";

  public static String SHOPPING = "4";

  public static String WELLNESS = "6";

  public static String TRAVEL = "24";

  public static String THE_TIME = "70";

  public static String THE_DIRECT= "56";

  public static int E_STORE_ID = 55;

  public static int WINE_AND_DINE_ID = 5;

  public static int SHOPPING_ID = 4;

  public static int WELLNESS_ID = 6;

  public static int TRAVEL_ID = 24;

  public static final String[] sortingArrHome =
      {"A - Z", "Z - A", "Latest first", "Earliest first", "Popular"};

  public static final String[] sortingFieldHome =
      {"name", "name", "created_at", "created_at", "popularity_order"};

  public static final String[] sortDirectionHome = {"ASC", "DESC", "DESC", "ASC", "ASC"};

  public static final String[] categoryArrBooking =
      {"All", "Booked", "Cancellation Under Review", "Canceled"};

  public static final String[] categoryValueBooking =
      {"All", "confirmed", "cancellation", "cancelled"};

  public static final String[] sortingArrBooking =
      {"Latest Booking Date First", "Booking ID", "Earliest Booking Date First"};
  public static final String[] sortingFieldBooking = {"created_at", "booking_id", "created_at"};
  public static final String[] sortingDirectionBooking = {"DESC", "ASC", "ASC"};

  public static final String[] sortingDirectionReview = {"DESC", "ASC"};


  //search
  public static final String[] sortingArrSearch =
      {"Relevance","Popular","A - Z", "Z - A", "Latest first", "Earliest first"/*, "Location" */};

  public static final String[] sortFieldSearch =
      {"relevance_order","popularity_order","name", "name", "created_at", "created_at"/*, "location@"*/};

  public static final String[] sortDirectionSearch = {"ASC","ASC","ASC", "DESC", "DESC", "ASC"/*, "ASC"*/};


  //Pillar
  public static final String[] sortingArr =
      {"Popular","A - Z", "Z - A", "Latest first", "Earliest first"/*, "Location" */};

  public static final String[] sortField =
      {"popularity_order","name", "name", "created_at", "created_at"/*, "location@"*/};


  public static final String[] sortDirection = {"ASC","ASC", "DESC", "DESC", "ASC"/*, "ASC"*/};


  // Comment
  public static final String[] sortingCommentArr =
      {"Newest","Oldest"};


  // Estore
  public static final String[] sortingArrEstore =
      {"Popular","Highest Rating","Name: A-Z", "Name: Z-A", "Price: Low to High", "Price: High to Low", "Latest First", "Earliest First"};

  public static final String[] sortFieldEstore =
      {"popularity_order","rating","name", "name",  "price", "price", "created_at", "created_at"};

  public static final String[] sortDirectionEstore = {"ASC","DESC","ASC", "DESC","ASC", "DESC","DESC","ASC" };


  //FAV

  public static final String[] sortingArrFav =
      {"Popular","A - Z", "Z - A", "Latest first", "Earliest first", "Location"};

  public static final String[] sortFieldFav =
      {"popularity_order","name", "name", "added_at", "added_at", "location@"};

  public static final String[] sortDirectionFav = {"ASC","ASC", "DESC", "DESC", "ASC", "ASC"};

  public static final String[] sortingArrOrder = {"Latest first", "Earliest first", "Order ID"};

  public static final String[] sortingArrNotifi = {"Latest first", "Earliest first"};

  public static final String[] sortingDirectionNotifi = {"ASC, DESC"};


  public static final String[] refineArrNotifi = {"All", "General", "Promotions" , "New Launches" , "Events"};

  public static final String[] refineDirectionNotifi = {"all", "general", "promotions" , "new_launches" , "events"};

  public static final String[] refineArrReservation = {"All",/* "Unconfirm" ,*/ "Confirmed", "Cancelled"};

  public static final String[] refineDirectionReservation = {"all", "1", /*"2" ,*/ "4"};

  public static final String[] sortArrReservation = {"Latest first", "Earliest first"};

  public static final String[] sortDirectionReservation = {"DESC", "ASC"};

  public static final String[] categoryArrOrder =
      {
          "All", "Cancelled", "Closed", "Collected", "Fail", "Suspected Fraud", "On Hold",
          "Payment Review", "Delivering", "Processing",
          "Shipping"
      };

  public static final String ATTR_SET_ID_PARTNERS = "10";

  public static final String ATTR_SET_ID_PRODUCTS = "4";

  public static final String ATTR_SET_ID_RENEWAL = "11";

  public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy", Locale.US);

  public static SimpleDateFormat DATE_YEAR_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

  public static SimpleDateFormat DATE_FORMAT_DOB = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

  public static int MIN_AGE = -10;

  public static String PREF_KEY_SHOW_EXPIRY_NOTICE = "noti_dialog_show";

  public static final String CART_COUNT = "cart_count";

  public static final String ORDER_PAYMENT_REVIEW = "payment_review";
  public static final String ORDER_SUSPECTED_FRAUD = "fraud";
  public static final String ORDER_FAIL = "fail";
  public static final String ORDER_PENDING = "pending";
  public static final String ORDER_PROCESSING = "processing";
  public static final String ORDER_COMPLETE = "complete";
  public static final String ORDER_HOLDED = "holded";
  public static final String ORDER_CLOSED = "closed";
  public static final String ORDER_CANCELLED = "canceled";
  public static final String ORDER_SHIPPING = "shipping";

  public static final String UNIT_NUMBER = "unit_number";

  public static final String VISA_BRAND = "Visa";
  public static final String MASTER_BRAND = "MasterCard";
  public static final String AMERICAN_EXPRESS_BRAND = "American Express";
  public static final String DISCOVER_BRAND = "Discover";
  public static final String DINERS_CLUB_BRAND = "Diners Club";
  public static final String JCB_BRAND = "JCB";
  public static final String UNKNOWN_BRAND = "Unknown";

  public static final String GRAND_TOTAL = "grand_total";
  public static final String SUB_TOTAL = "subtotal";

  public static final String MEMBERSHIP_EXPIRED = "member_expiry_date";
  public static final String EAN_SORT_A_Z = "Name:A-Z";
  public static final String EAN_SORT_Z_A = "Name:Z-A";
  public static final String EAN_SORT_LOCATION = "Location";
  public static final String EAN_SORT_POPULAR = "Popular";

  public static final String COMMENT_APPROVED = "Approved";
  public static final String COMMENT_NOT_APPROVED = "Not Approved";
  public static final String COMMENT_PENDING = "Pending";



  public static String[] sortingArrEan =
      {EAN_SORT_A_Z, EAN_SORT_Z_A, EAN_SORT_LOCATION, EAN_SORT_POPULAR};

  public static final String[] SALUTATIONS = {"Mr.", "Ms.", "Mrs.", "Dr.", "Prof."};

  public static final String CHANNEL_ID = "SkypremiumId";
  public static final String CHANNEL_NAME = "SkypremiumChannel";
  public static final String FIREBASE_MSG = "FirebaseMsgService";

  public static final String TITLE_KEY = "title";
  public static final String BODY_KEY = "body";
  public static final String ACTION_KEY = "action";
  public static final String BILLING_ADDRESS_ID = "billing_address_id";


  public static final String TITILE = "Title";

  //Notification settings

  public static final String MARKETING_ALERT      = "marketing_alert";
  public static final String EVENT_INVITES_AND_UP = "event_invites_and_up";
  public static final String TRAVEL_DEALS         = "travel_deals";
  public static final String WINE_DINE_DEALS      = "wine_dine_deals";
  public static final String SHOPPING_DEALS       = "shopping_deals";
  public static final String ESTORE_DEALS         = "estore_deals";
  public static final String WELLNESS_DEALS       = "wellness_deals";

  public static final String RESERVATIONS= "Reservations";


  //KEY CRM TOKEN
  public static final String GRANT_TYPE = "grant_type";
  public static final String CLIENT_ID = "client_id";
  public static final String CLIENT_SECRET = "client_secret";
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String PLATFORM = "platform";



}

