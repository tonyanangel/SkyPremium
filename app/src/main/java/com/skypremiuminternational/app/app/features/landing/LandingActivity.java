package com.skypremiuminternational.app.app.features.landing;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.onesignal.OSDevice;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationPayload;
import com.onesignal.OneSignal;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.dummy.FragmentDummy;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.home.HomeFragment;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentVideo;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentVimeo;
import com.skypremiuminternational.app.app.features.landing.popup.FragmentYoutube;
import com.skypremiuminternational.app.app.features.landing.popup.SlideAdapter;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.MembershipRenewalDialogFragment;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.features.travel.ProductListFragment;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.features.travel.ean.search.SearchHotelDialogFragment;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.listener.MemberShipRenewalActionsListener;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.app.utils.listener.SearchHistoryListener;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.navigation.NavigationEvent;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.UserUtil;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;

import dagger.android.AndroidInjection;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.HttpException;


public class LandingActivity extends LocationAwareActivity<LandingPresenter>
    implements LandingView<LandingPresenter>, SearchHistoryListener, MovePillarListener,
    ChangeNotificationListener {

  private static final String IS_FIRST = "FIRST_LOGIN";
  private static final String DEEP_INTENT = "DEEP_INTENT";
  @BindView(R.id.shadow)
  View eanLayoutShadow;
  @BindView(R.id.ly_ean_info)
  ViewGroup lyEanInfo;
  @BindView(R.id.flContent)
  FrameLayout flContent;
  @BindView(R.id.root_layout)
  ViewGroup rootLayout;
  @BindView(R.id.ahbn)
  AHBottomNavigation ahbn;
  @BindView(R.id.tv_property_area)
  TextView tvPropertyArea;
  @BindView(R.id.tv_date_range)
  TextView tvDateRange;
  @BindView(R.id.tv_room_occupancy)
  TextView tvRoomOccupancy;
  @BindView(R.id.frm_popup)
  FrameLayout frmPopup;
  @BindView(R.id.pagePopup)
  ViewPager pagePopup;
  @BindView(R.id.cons_popup)
  LinearLayout consPopup;
  @BindView(R.id.fpi)
  FlycoPageIndicaor fpi;
  @BindView(R.id.img_close)
  ImageView imgClose;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  @Inject
  PreferenceUtils preferenceUtils;

  private ProgressDialog progressDialog;
  private ProgressDialog progressDialog2;
  private long back_pressed;
  private SearchHistoryListener searchHotelListener;
  public static Double pricevalue;
  SlideAdapter slideAdapter;
  int currentPos = 0;

  public static String pillarback = "";
  public static String backFrom = "";

  boolean left = false;

  String from ;
  String pillar ;
  String sku ;
  String sortBy ;
  static String filter;
  int defaultSelected = -1;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Log.d("NOTIMOVE", "startMe5a");
    Intent intent = new Intent(context, LandingActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
  public static void startMeToTop(Context context) {
    Log.d("NOTIMOVE", "startMe5a");

    Intent intent = new Intent(context, LandingActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
  public static void startMe(Context context,Intent deepIntent) {
    Log.d("NOTIMOVE", "startMe5b");


    Intent intent = new Intent(context, LandingActivity.class);
    intent.putExtra(DEEP_INTENT, deepIntent);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void startMe(Context context, String from, String pillar) {
    Log.d("NOTIMOVE", "startMe2");
    Intent intent = new Intent(context, LandingActivity.class);
    intent.putExtra("from", from);
    intent.putExtra("pillar", pillar);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void startMe(Context context, String from, String pillar, String filter,String sortBy) {
    Log.d("NOTIMOVE", "startMe1 filter " + filter + "from " +from+ "sortBy "+ sortBy +"pillar " +pillar );
    Intent intent = new Intent(context, LandingActivity.class);
    intent.putExtra("from", from);
    intent.putExtra("pillar", pillar);
    intent.putExtra("filter", filter);
    intent.putExtra("sortBy", sortBy);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(intent);
  }

  public static void startMe(Context context, boolean isFirstLogin, Intent deepIntent ) {

    Log.d("NOTIMOVE", "startMe3");

    Intent intent = new Intent(context, LandingActivity.class);
    intent.putExtra(IS_FIRST, isFirstLogin);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
  public static void startMe(Context context, boolean isFirstLogin) {

    Intent intent = new Intent(context, LandingActivity.class);
    intent.putExtra(IS_FIRST, isFirstLogin);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void startMe(Context context, int deepLink) {
    Log.d("NOTIMOVE", "startMe4");

    Intent intent = new Intent(context, LandingActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra(Constants.DEEP_LINK_LANDING, deepLink);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_landing);
    ButterKnife.bind(this);
    App.isInApp = true;
    presenter.intitOneSignal(getApplicationContext(),this,this);


    from = getIntent().getStringExtra("from");

    getIntentParam();

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    presenter.getRatingOption();
    //progressDialog.setMessage(getString(R.string.loggingIn));

    progressDialog2 = new ProgressDialog(this);
    progressDialog2.setCanceledOnTouchOutside(false);

    /** hidden membership popup in landing **/
    // presenter.getUserDetailFromApi();

    ahbn.setTitleTextSizeInSp(9, 9);
    ahbn.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));
    ahbn.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
    ahbn.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    ahbn.setInactiveColor(Color.parseColor("#C0B8B6"));

    AHBottomNavigationItem item1 =
        new AHBottomNavigationItem(R.string.travel_all_caps, R.drawable.ic_travel_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item2 =
        new AHBottomNavigationItem(R.string.wine_all_caps, R.drawable.ic_wine_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item3 =
        new AHBottomNavigationItem(R.string.home_all_caps, R.drawable.ic_logo_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item4 =
        new AHBottomNavigationItem(R.string.shopping_all_caps, R.drawable.ic_shopping_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item5 =
        new AHBottomNavigationItem(R.string.wellness_all_caps, R.drawable.ic_wellness_tab,
            R.color.colorAccent);

    ahbn.addItem(item1);
    ahbn.addItem(item2);
    ahbn.addItem(item3);
    ahbn.addItem(item4);
    ahbn.addItem(item5);

    ahbn.setOnTabSelectedListener((position, wasSelected) -> {
      if (defaultSelected!=position) {
        formConfig(position);
        defaultSelected=position;
      }else if (!TextUtils.isEmpty(from)
          &&from.equalsIgnoreCase("notification")) {
        formConfig(position);
        defaultSelected=position;
      }
      return true;
    });


    if (pillar != null) {
      movePillar(pillar);
    } else {
      int deepLink = getIntent().getIntExtra(Constants.DEEP_LINK_LANDING, 2);
      ahbn.setCurrentItem(deepLink, true);
    /*  if (deepLink == 0) {
        formConfig(0);
      }*/
    }


    //checkingFirtTimeLogin();
    // ========== first time pop up
      presenter.getFristTimePopup();
      presenter.checkFirstLogin();
//      presenter.updateMappVersion();
//      presenter.getSettingFromAPI();

  }


  @Override
  public void sendFirstTimeMessage(boolean isFlogin) {
    checkingFirtTimeLogin(isFlogin);
  }

  private void getIntentParam() {
    Intent deepIntent = getIntent().getParcelableExtra("deepIntent");
    String query = "";
    Map<String,String> mapQuery = new HashMap<>();

    if(TextUtils.isEmpty(from)){
      try{
        from = deepIntent.getData().getScheme();
      }catch (NullPointerException ex){}
      if(from!=null&&from.equalsIgnoreCase("skypremium")){
        from = "deeplink";
      }
    }
    if(from!=null&&from.equalsIgnoreCase("deeplink")) {
      if (TextUtils.isEmpty(pillar)) {
        try {
          pillar = deepIntent.getData().getHost();
        } catch (NullPointerException ex) {
        }
      }


      try {
        query = getIntent().getData().getQuery();
        String[] arrayQuery = query.split("&");
        for (String item : arrayQuery) {
          String[] arrayMap = item.split("=");
          if (arrayMap.length >= 2) {
            mapQuery.put(arrayMap[0], arrayMap[1]);
          }
        }
      } catch (NullPointerException ex) {

      }
      filter = mapQuery.get("filter");
      sortBy = mapQuery.get("sortby");
      sku = mapQuery.get("sku");
      if(!TextUtils.isEmpty(sku)){
        TravelDetailActivity.startMe(this, "deeplink", mapQuery.get("sku"));
      }
    }else if(from!=null&&from.equalsIgnoreCase("notification")){
      pillar = getIntent().getStringExtra("pillar");
      filter = getIntent().getStringExtra("filter");
      sortBy = getIntent().getStringExtra("sortBy");
      if (filter == null){
        filter = "";
      }
      if(from == null){
        from ="";

      }
      if (pillar == null){
        pillar = "";
      }
      if(sortBy == null){
        sortBy ="";

      }
    }

  }

  private void checkingFirtTimeLogin(boolean isFisrtTime) {

    if (isFisrtTime) {
      presenter.sendFirstTimeNotification();
    }
    presenter.disableFisrtTime();
  }

  @Override
  public void onBackPressed() {
    if (isTaskRoot()) {
      if (ahbn.getCurrentItem() == 2) {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
          finish();
        } else {
          Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
      } else {
        ahbn.setCurrentItem(2, true);
      }
    } else {
      finish();
    }
  }

  @Inject
  @Override
  public void injectPresenter(LandingPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Subscribe
  public void onClickNavigation(NavigationEvent navigationEvent) {
    ahbn.setCurrentItem(navigationEvent.getDeeplink(), true);
  }

  private void formConfig(int position) {
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
    switch (position) {
      case 0:
        trans.replace(R.id.flContent, ProductListFragment.newInstance(Constants.TRAVEL, filter,from,sortBy));
        break;

      case 1:
        trans.replace(R.id.flContent, ProductListFragment.newInstance(Constants.WINE_AND_DINE, filter,from,sortBy));
        break;

      case 2:
        trans.replace(R.id.flContent, HomeFragment.newInstance());
        break;

      case 3:
        trans.replace(R.id.flContent, ProductListFragment.newInstance(Constants.SHOPPING, filter,from,sortBy));
        break;

      case 4:
        trans.replace(R.id.flContent, ProductListFragment.newInstance(Constants.WELLNESS, filter,from,sortBy));
        break;

      default:
        trans.replace(R.id.flContent, FragmentDummy.newInstance("Default"));
        break;
    }
    from ="";
    hideEANInfo();
    trans.commitAllowingStateLoss();
  }

  @Override
  public void render(LandingViewState viewState) {
    {
      if (viewState.isLoading()) {
        progressDialog.show();
      } else {
        progressDialog.dismiss();
        if (viewState.isSuccess()) {
          UserDetailResponse user = viewState.response();


          if (viewState.renewalPrice() != null && user != null) {


            MembershipRenewalDialogFragment fragment;
            if (viewState.renewalPrice() > user.getLoyaltyPoints()) {
              fragment = MembershipRenewalDialogFragment.newInstance(
                  MembershipRenewalDialogFragment.DIALOG_TYPE_WITHOUT_LOYALTY_POINTS);
            } else {
              fragment = MembershipRenewalDialogFragment.newInstance(
                  MembershipRenewalDialogFragment.DIALOG_TYPE_WITH_LOYALTY_POINTS,
                  viewState.renewalPrice(), user.getLoyaltyPoints());
              fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                @Override
                public void onClickedRenewLater(boolean showPromptAgain) {
                  // do nothing
                }

                @Override
                public void onClickedRenewMemberShip(boolean showPromptAgain) {

                }

                @Override
                public void onClickedRenew(boolean usePoints) {
                  presenter.addRenewalToCart(usePoints);
                }
              });
            }


            fragment.show(getSupportFragmentManager(), "membership_renewal");
          } else if (user != null) {
            if (user.getGroupId() == Constants.GROUP_ID_EXPIRED
                || user.getGroupId() == Constants.GROUP_ID_AWAITING_RENEWAL) {
              finish();
              ProfileActivity.startMe(this);
            } else if (user.getGroupId() != Constants.GROUP_ID_ACTIVE) {
              finish();
              SignInActivity.startMe(this, user.getGroupId());
            } else {
              if (user.getDaysLeft() != UserUtil.DEFAULT_DAYS_LEFT
                  && user.getDaysLeft() <= 7
                  && user.isShowMemberExpiryPrompt()) {
                getConfigPromotionCode(BuildConfig.BASE_URL + URL.CONFIGCOUPON, BuildConfig.BASE_URL + URL.CONFIGSKY, user, 1.0);

              }
            }
          }
        } else {
          Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
              Toast.LENGTH_SHORT).show();
          if (viewState.error() instanceof HttpException) {
            HttpException httpException = (HttpException) viewState.error();
            if (httpException.code() == 401) {
              onClickLogout();
            }
          }
        }
      }
    }

  }

  @Override
  public void goToShoppingCart() {
    progressDialog.dismiss();
    ShoppingCartActivity.start(this);
  }

  @Override
  public void render(String areaOrHotel, String checkInDate, String checkOutDate, int roomCount,
                     int adultCount, List<Child> children) {

    lyEanInfo.setVisibility(View.VISIBLE);

    if (TextUtils.isEmpty(areaOrHotel)) {
      tvPropertyArea.setText(R.string.product_list_property_search_primary_hint);
    } else {
      tvPropertyArea.setText(areaOrHotel);
    }

    if (TextUtils.isEmpty(checkInDate)) {
      checkInDate = getString(R.string.all_not_selected_label);
    }
    if (TextUtils.isEmpty(checkOutDate)) {
      checkOutDate = getString(R.string.all_not_selected_label);
    }
    String dateRange = checkInDate + " - " + checkOutDate;
    tvDateRange.setText(dateRange);

    tvRoomOccupancy.setText(formatOccupancy(roomCount, adultCount, children));
  }

  @Override
  public void showSearchHotelDialog(String areaOrHotel, List<CalendarDay> selectedDays,
                                    int roomCount, int adultCount, List<Child> children) {

    SearchHotelDialogFragment fragment =
        SearchHotelDialogFragment.newInstance(areaOrHotel, selectedDays, roomCount, adultCount,
            children);
    fragment.setListener(this);
    fragment.show(getSupportFragmentManager(), "SearchHotel");
  }

  private String formatOccupancy(int roomCount, int adultCount, List<Child> children) {
    Resources resources = getResources();
    String occupancy = resources.getQuantityString(R.plurals.adults, adultCount, adultCount) + ", ";

    occupancy += resources.getQuantityString(R.plurals.children, children.size(), children.size())
        + ", ";

    occupancy += resources.getQuantityString(R.plurals.rooms, roomCount, roomCount);
    return occupancy;
  }

  private void onClickLogout() {
    presenter.onLogout();
    finish();
    Intent mIntent = new Intent(this, SignInActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(mIntent);
  }

  @Override
  public ViewGroup getRootLayout() {
    return rootLayout;
  }

  @OnClick(R.id.btn_estore)
  void onClickedEstore() {
    EstoreActivity.startMe(this);
  }

  @OnClick(R.id.btn_edit_ean)
  void onClickedEditEan() {
    presenter.collectSearchValues();
  }

  public boolean isEanInfoVisible() {
    return lyEanInfo.getVisibility() == View.VISIBLE;
  }

  public void showEANInfo(String area, List<CalendarDay> selectedDays, int roomCount,
                          int adultCount, List<Child> children, SearchHistoryListener listener) {
    searchHotelListener = listener;
    lyEanInfo.setVisibility(View.VISIBLE);
    eanLayoutShadow.setVisibility(View.VISIBLE);
    presenter.setSearchValues(area, selectedDays, roomCount, adultCount, children);
  }

  public void hideEANInfo() {
    lyEanInfo.setVisibility(View.GONE);
    eanLayoutShadow.setVisibility(View.GONE);
  }

  @Override
  public void searchHistoryClicked(String areaOrHotel, List<CalendarDay> selectedDays,
                                   int roomCount, int adultCount, List<Child> children) {
    presenter.setSearchValues(areaOrHotel, selectedDays, roomCount, adultCount, children);
    if (searchHotelListener != null) {
      searchHotelListener.searchHistoryClicked(areaOrHotel, selectedDays, roomCount, adultCount,
          children);
    }
  }

  public void getConfigPromotionCode(String urlcode, String urlsky, UserDetailResponse user, Double price) {


    RequestQueue requestQueue = Volley.newRequestQueue(this);

    StringRequest postRequest = new StringRequest(Request.Method.GET, urlcode, new Response.Listener<String>() {
      @Override
      public void onResponse(String a) {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, urlsky, new Response.Listener<String>() {
          @Override
          public void onResponse(String s) {

            if (s.contains("\"false\"") && a.contains("\"false\"")) {


              MembershipRenewalDialogFragment fragment =
                  MembershipRenewalDialogFragment.newInstance(
                      MembershipRenewalDialogFragment.DIALOG_TYPE_WITH_REDEEM_HIDE,
                      user.getExpiryDate());

              fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                @Override
                public void onClickedRenewLater(boolean showPromptAgain) {
                  if (!showPromptAgain) {
                    presenter.updateUserNotToShowPrompt();
                  }
                }

                @Override
                public void onClickedRenewMemberShip(boolean showPromptAgain) {
                  if (!showPromptAgain) {
                    presenter.updateUserNotToShowPrompt();
                  }
                  presenter.getRenewalPrice();
                }

                @Override
                public void onClickedRenew(boolean usePoints) {
                  presenter.addRenewalToCart(false);
                }
              });

              fragment.show(getSupportFragmentManager(), "membership_renewal");
            } else if (s.contains("\"false\"")) {
              MembershipRenewalDialogFragment fragment =
                  MembershipRenewalDialogFragment.newInstance(
                      MembershipRenewalDialogFragment.DIALOG_TYPE_WITH_REDEEM_HIDE,
                      user.getExpiryDate());

              fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                @Override
                public void onClickedRenewLater(boolean showPromptAgain) {
                  if (!showPromptAgain) {
                    presenter.updateUserNotToShowPrompt();
                  }
                }

                @Override
                public void onClickedRenewMemberShip(boolean showPromptAgain) {
                  if (!showPromptAgain) {
                    presenter.updateUserNotToShowPrompt();
                  }
                  presenter.getRenewalPrice();
                }

                @Override
                public void onClickedRenew(boolean usePoints) {
                  presenter.addRenewalToCart(false);
                }
              });

              fragment.show(getSupportFragmentManager(), "membership_renewal");

            } else {
              presenter.getRenewalPricevalue();
              progressDialog.show();
              new CountDownTimer(3000, 1000) {
                public void onFinish() {

                  // When timer is finished
                  // Execute your code here
                  progressDialog.dismiss();

                  if (pricevalue != null) {

                    if (user.getLoyaltyPoints() >= pricevalue) {
                      MembershipRenewalDialogFragment fragment =
                          MembershipRenewalDialogFragment.newInstance(
                              MembershipRenewalDialogFragment.DIALOG_TYPE_MEMBERSHIP_RENEWAL,
                              user.getExpiryDate());

                      fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                        @Override
                        public void onClickedRenewLater(boolean showPromptAgain) {
                          if (!showPromptAgain) {
                            presenter.updateUserNotToShowPrompt();
                          }
                        }

                        @Override
                        public void onClickedRenewMemberShip(boolean showPromptAgain) {
                          if (!showPromptAgain) {
                            presenter.updateUserNotToShowPrompt();
                          }
                          presenter.getRenewalPrice();
                        }

                        @Override
                        public void onClickedRenew(boolean usePoints) {
                          // do nothing
                        }
                      });

                      fragment.show(getSupportFragmentManager(), "membership_renewal");


                    } else {
                      MembershipRenewalDialogFragment fragment =
                          MembershipRenewalDialogFragment.newInstance(
                              MembershipRenewalDialogFragment.DIALOG_TYPE_WITH_REDEEM_HIDE,
                              user.getExpiryDate());

                      fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                        @Override
                        public void onClickedRenewLater(boolean showPromptAgain) {
                          if (!showPromptAgain) {
                            presenter.updateUserNotToShowPrompt();
                          }
                        }

                        @Override
                        public void onClickedRenewMemberShip(boolean showPromptAgain) {
                          if (!showPromptAgain) {
                            presenter.updateUserNotToShowPrompt();
                          }
                          presenter.getRenewalPrice();
                        }

                        @Override
                        public void onClickedRenew(boolean usePoints) {
                          presenter.addRenewalToCart(false);
                        }
                      });

                      fragment.show(getSupportFragmentManager(), "membership_renewal");
                    }
                  } else {
                    Toast.makeText(getApplicationContext(), "Failed to get Renewal Price, Try agian later.", Toast.LENGTH_SHORT).show();
                  }
                }

                public void onTick(long millisUntilFinished) {
                  // millisUntilFinished    The amount of time until finished.
                }
              }.start();


            }
          }


        },
            new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Redeem Sky Dollar config.", Toast.LENGTH_SHORT).show();
              }
            }) {

          //This is for Headers
          @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json");
            params.put("Authorization", Constants.CLIENT_TOKEN);
            return params;
          }

        };


        requestQueue.add(postRequest);

      }
    },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Failed to load Redeem Coupon code config.", Toast.LENGTH_SHORT).show();
          }
        }) {

      //This is for Headers
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Authorization", Constants.CLIENT_TOKEN);
        return params;
      }

    };


    requestQueue.add(postRequest);

  }

  @Override
  public void showDialogLoading() {
    progressDialog2.setMessage(getString(R.string.loading));
    progressDialog2.show();
  }

  @Override
  public void hideDialogLoading() {
    if (progressDialog2 != null && progressDialog2.isShowing()) {
      progressDialog2.dismiss();
    }

  }

  @Override
  public void renderNumberUnread(int number) {}
  public void showFullscreen() {
    pagePopup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    fpi.setVisibility(View.GONE);
    imgClose.setVisibility(View.GONE);
  }

  public void exitFullscreen() {
    pagePopup.setLayoutParams(new LinearLayout.LayoutParams(MetricsUtils.convertDpToPixel(350f, this), MetricsUtils.convertDpToPixel(200f, this)));
    consPopup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    fpi.setVisibility(View.VISIBLE);
    imgClose.setVisibility(View.VISIBLE);
  }


  @OnClick(R.id.img_close)
  void onSkipPopup() {

    frmPopup.setVisibility(View.GONE);
    slideAdapter.notifyDataSetChanged();

    if (slideAdapter.getItem(currentPos) instanceof FragmentYoutube) {
      if (((FragmentYoutube) slideAdapter.getItem(currentPos)).getYouTubePlayerView() != null) {
        ((FragmentYoutube) slideAdapter.getItem(currentPos)).getYouTubePlayerView().pause();
      }
    } else if (slideAdapter.getItem(currentPos) instanceof FragmentVideo) {
      if (((FragmentVideo) slideAdapter.getItem(currentPos)).getVideoView() != null) {

        ((FragmentVideo) slideAdapter.getItem(currentPos)).pauseVideo();

      }
    } else if (slideAdapter.getItem(currentPos) instanceof FragmentVimeo) {
      if (((FragmentVimeo) slideAdapter.getItem(currentPos)).getVimeoPlayer() != null) {

        ((FragmentVimeo) slideAdapter.getItem(currentPos)).getVimeoPlayer().pause();

      }
    }
    presenter.setFirstTimePopup();
  }


  @Override
  public void renderFirstTimePopup(FirstTimePopup firstTimePopup) {
    if (!firstTimePopup.isFirstTime()) {
      frmPopup.setVisibility(View.GONE);

      return;
    }
    frmPopup.setVisibility(View.VISIBLE);
    if (slideAdapter == null) {
      pagePopup.setBackgroundColor(getResources().getColor(R.color.black));
      slideAdapter = new SlideAdapter(getSupportFragmentManager(), this, firstTimePopup.getPopupItem());
      pagePopup.setAdapter(slideAdapter);
      fpi.setViewPager(pagePopup);
    }
    pagePopup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      int pos = 0;
      int pos2 = 0;

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


      }

      @Override
      public void onPageSelected(int position) {
        for (int i = 0; i < slideAdapter.getCount(); i++) {
          if (slideAdapter.getItem(i) instanceof FragmentYoutube) {
            if (((FragmentYoutube) slideAdapter.getItem(i)).getYouTubePlayerView() != null) {
              ((FragmentYoutube) slideAdapter.getItem(i)).getYouTubePlayerView().pause();
            }
          } else if (slideAdapter.getItem(i) instanceof FragmentVideo) {
            if (((FragmentVideo) slideAdapter.getItem(i)).getVideoView() != null) {

              ((FragmentVideo) slideAdapter.getItem(i)).pauseVideo();

            }
          }
        }
        if (position != pos) {
          if (slideAdapter.getItem(pos) instanceof FragmentVimeo) {
            if (((FragmentVimeo) slideAdapter.getItem(pos)).getVimeoPlayer() != null) {

              ((FragmentVimeo) slideAdapter.getItem(pos)).getVimeoPlayer().pause();

            }
          }
        }
        pos = position;
        currentPos = position;
        exitFullscreen();
      }

      @Override
      public void onPageScrollStateChanged(int state) {}

    });
  }

  @Override
  public void movePillar(String pillar,String filter,String from, String sortBy) {
    Log.d("NOTIMOVE", "movePillar");
    this.filter = filter;
    this.from = from;
    this.sortBy = sortBy;
    switch (pillar) {
      case "travel":
        ahbn.setCurrentItem(0);
        break;

      case "wine-dine":
        ahbn.setCurrentItem(1);
        break;

      case "shopping":
        ahbn.setCurrentItem(3);
        break;

      case "wellness":
        ahbn.setCurrentItem(4);
        break;
    }
  }

  @Override
  public void movePillar(String pillar) {
    switch (pillar) {
      case "travel":
        ahbn.setCurrentItem(0);
        break;

      case "wine-dine":
        ahbn.setCurrentItem(1);
        break;

      case "shopping":
        ahbn.setCurrentItem(3);
        break;

      case "wellness":
        ahbn.setCurrentItem(4);
        break;
    }
  }

  @Override
  public void renderFirstTimePopupFailed(Throwable throwable) {
    frmPopup.setVisibility(View.GONE);
  }

  @Override
  public void renderNumberNotification() {

  }

  @Override
  public void renderListNotification() {

  }
}

