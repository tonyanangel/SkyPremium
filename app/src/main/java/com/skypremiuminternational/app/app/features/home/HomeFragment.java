package com.skypremiuminternational.app.app.features.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
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
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditCreditCardDialog;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.MembershipRenewalDialogFragment;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.signin.SignInActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.SpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.listener.MemberShipRenewalActionsListener;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.UserUtil;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dagger.android.support.AndroidSupportInjection;
import retrofit2.HttpException;

import javax.inject.Inject;

/**
 * Created by sandi on 7/27/17.
 */

public class HomeFragment extends BaseFragment<HomePresenter>
    implements HomeView<HomePresenter> , ChangeNotificationListener {

  //@BindView(R.id.frv) FeaturedRecyclerView frv;

  @BindView(R.id.rv)
  RecyclerView rv;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  @BindView(R.id.icon_skypremium)
  ImageView iconSkypremium;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.ly_notification_count)
  FrameLayout lyNotificationCount;
  @BindView(R.id.tv_notification_count)
  TextView tvNotificationCount;

  private ProgressDialog progressDialog;
  private ProgressDialog progressDialog2;

  public static Double pricevalue;

  public int onlUnreadNotification;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private HomeAdapter adapter;
  private Uri deepData;

  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);

    App.isInApp = true;
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading...");

    progressDialog2 = new ProgressDialog(getContext());
    progressDialog2.setCanceledOnTouchOutside(false);

    setUpRecyclerView();
    presenter.getHomeCategories();
    presenter.getUserDetailFromApi();
    OneSignal.getCurrentOrNewInitBuilder()
        .setNotificationReceivedHandler(new SkyNotificationReceivedHandler(App.getAppContext(),presenter.getDataManager(),this))
        .setNotificationOpenedHandler(new SkyNotificationOpenHandler(App.getAppContext())).init();
    renderNumberUnreadLocal();
   /* if(deepIntent!=null){
      String scheme = "";
      try {
        scheme = deepIntent.getData().getScheme();

      }catch (NullPointerException ex){}
      if(!TextUtils.isEmpty(scheme)&&scheme.equalsIgnoreCase("skypremium")){
        NotificationActivity.startMe(getActivity());
      }
    }*/
  }
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();

      ft.detach(this).attach(this).commit();
     /* Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

      FragmentTransaction ft = getFragmentManager().beginTransaction();

      ft.detach(this).attach(this).commit();
    /*  Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.getCartCount();
    //getNumberUnread();
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_home;
  }

  @Inject
  @Override
  public void injectPresenter(HomePresenter presenter) {
    super.injectPresenter(presenter);
  }

  public void setUpRecyclerView() {
    rv.addItemDecoration(new SpacesItemDecoration(1, 1, false));
    //rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new HomeAdapter();

    ViewTreeObserver viewTreeObserver = rv.getViewTreeObserver();
    if (viewTreeObserver.isAlive()) {
      viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          rv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          adapter.setHeight(rv.getHeight()-5);
          Log.d("OFFSET","height"+rv.getHeight());
        }
      });
    }
    rv.setAdapter(adapter);
    rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
          //scrolledtotop
          iconSkypremium.setVisibility(View.VISIBLE);
        } else if (!recyclerView.canScrollVertically(1)) {
          //scrolledtobottom
          iconSkypremium.setVisibility(View.INVISIBLE);
        }

        if (dy < 0) {
          //scrolledup
          iconSkypremium.setVisibility(View.VISIBLE);
        } else if (dy > 0) {
          //scrolleddown
          iconSkypremium.setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getChildFragmentManager(), "Navigation");
  }

  @OnClick(R.id.ivSearch_toolbar)
  void onClickSearch() {
    SearchActivity.startMe(getContext());
  }

  @OnClick(R.id.ivNotificationBell)
  void onClickNotification (){
    NotificationActivity.startMe(getContext());
  }

  @OnClick(R.id.ivCart_toolbar)
  void onClickShoppingCart() {
    ShoppingCartActivity.start(getContext());
  }

  private void gotoVisaDialog() {
    Bundle bundle = new Bundle();
    bundle.putInt(AddOrEditCreditCardDialog.EXTRA_KEY,
        AddOrEditCreditCardDialog.ADD_CARD);
    AddOrEditCreditCardDialog bottomSheetVisaDialog =
        new AddOrEditCreditCardDialog();
    bottomSheetVisaDialog.setArguments(bundle);
    bottomSheetVisaDialog.show(getChildFragmentManager(), "VisaDialog");
  }

  @Override
  public void updateCartCount(String count) {
    if (count == null && TextUtils.isEmpty(count)) {
      lyCartCount.setVisibility(View.INVISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
    } else {
      if (!count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
      } else {
        lyCartCount.setVisibility(View.INVISIBLE);
        tvCartCount.setVisibility(View.INVISIBLE);
      }
    }
  }

  @Override
  public void goToShoppingCart() {
    progressDialog.dismiss();
    ShoppingCartActivity.start(getContext());
  }

  @Override
  public void render(Double pricevalue) {
    this.pricevalue = pricevalue;
  }

  @Override
  public void render(Throwable error) {
    Toast.makeText(getActivity(), errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void render(HomeViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.message().equals("1")) {
          adapter.setDataList(viewState.dataList().getBanners());
        }
      } else {
        Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }


      /** <<START>> 20200317- Nhat Nguyen  only show the Renewal Membership popup on Home page code <<START>>  **/


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


            fragment.show(getActivity().getSupportFragmentManager(), "membership_renewal");
          } else if (user != null) {
            if (user.getGroupId() == Constants.GROUP_ID_EXPIRED
                    || user.getGroupId() == Constants.GROUP_ID_AWAITING_RENEWAL) {
              getActivity().finish();
              ProfileActivity.startMe(getContext());
            } else if (user.getGroupId() != Constants.GROUP_ID_ACTIVE) {
              getActivity().finish();
              SignInActivity.startMe(getContext(), user.getGroupId());
            } else {
              if (user.getDaysLeft() != UserUtil.DEFAULT_DAYS_LEFT
                      && user.getDaysLeft() <= 7
                      && user.isShowMemberExpiryPrompt()) {
                getConfigPromotionCode(BuildConfig.BASE_URL+ URL.CONFIGCOUPON,BuildConfig.BASE_URL+URL.CONFIGSKY,user);

              }
            }
          }
        } else {
          Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(viewState.error()),
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
  public void getConfigPromotionCode(String urlcode,String urlsky,UserDetailResponse user){


    RequestQueue requestQueue = Volley.newRequestQueue(getContext());

    StringRequest postRequest = new StringRequest(Request.Method.GET, urlcode, new Response.Listener<String>() {
      @Override
      public void onResponse(String a) {



        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, urlsky, new Response.Listener<String>() {
          @Override
          public void onResponse(String s) {

            if(s.contains("\"false\"") && a.contains("\"false\"")){


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

              fragment.show(getActivity().getSupportFragmentManager(), "membership_renewal");
            }else if(s.contains("\"false\"")) {
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

              fragment.show(getActivity().getSupportFragmentManager(), "membership_renewal");

            }else {
              presenter.getRenewalPricevalue();
              progressDialog.show();
              new CountDownTimer(3000, 1000) {
                public void onFinish() {

                  // When timer is finished
                  // Execute your code here
                  progressDialog.dismiss();

                  if(pricevalue != null) {

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

                      fragment.show(getActivity().getSupportFragmentManager(), "membership_renewal");


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

                      fragment.show(getActivity().getSupportFragmentManager(), "membership_renewal");
                    }
                  }else{
                    Toast.makeText(getContext(),"Failed to get Renewal Price, Try agian later.",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(),"Failed to load Redeem Sky Dollar config.",Toast.LENGTH_SHORT).show();
                  }
                }){

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
                Toast.makeText(getContext(),"Failed to load Redeem Coupon code config.",Toast.LENGTH_SHORT).show();
              }
            }){

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

  private void onClickLogout() {
    presenter.onLogout();
    getActivity().finish();
    Intent mIntent = new Intent(getActivity(), SignInActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(mIntent);
  }

  /** <<END>> only show the Renewal Membership popup on Home page code <<END>> **/


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
  public void renderNumberUnreadLocal() {
    int number = 0;
    try {
      number = presenter.getNotificationCountLocal();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if(number<=0){
      lyNotificationCount.setVisibility(View.INVISIBLE);
    }else {
      lyNotificationCount.setVisibility(View.VISIBLE);
      tvNotificationCount.setText(""+number);
    }

  }
  @Override
  public void renderNumberUnread(int onlNumber) {
    onlUnreadNotification = onlNumber;
    int localUnreadNotification = 0;
    int sumUnreadNotification = 0;
    try {
      localUnreadNotification = presenter.getNotificationCountLocal();
      sumUnreadNotification = onlUnreadNotification + localUnreadNotification;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      hideDialogLoading();
      return;
    } catch (NullPointerException ex){
      ex.printStackTrace();
      hideDialogLoading();
      return;
    }
    if(sumUnreadNotification<=0){
      lyNotificationCount.setVisibility(View.INVISIBLE);
    }else {
      lyNotificationCount.setVisibility(View.VISIBLE);
      tvNotificationCount.setText(""+sumUnreadNotification);
    }
    hideDialogLoading();
  }

  @Override
  public void getNumberUnread() {
    //presenter.getNotificationCount();
  }


  @Override
  public void renderNumberNotification() {
    //renderNumberUnread(onlUnreadNotification);
    InternalStorageManager internalStorageManager =  new InternalStorageManagerImpl(new PreferenceUtils(App.getAppContext()), new DataUtils(App.getAppContext()));

      int num = 0 ;
      try {

          num = getNotificationCountLocal();

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      int finalNum = num;
      if(getActivity()!=null){

        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if(finalNum <=0){
              lyNotificationCount.setVisibility(View.INVISIBLE);
            }else {
              lyNotificationCount.setVisibility(View.VISIBLE);
              tvNotificationCount.setText(""+ finalNum);
            }
          }
        });
      }


  }
  public int getNotificationCountLocal() throws FileNotFoundException {
    int numberUnread = 0;
    InternalStorageManager internalStorageManager =  new InternalStorageManagerImpl(new PreferenceUtils(App.getAppContext()), new DataUtils(App.getAppContext()));
    try {
      for(com.skypremiuminternational.app.domain.models.notification.NotificationItem NotificationItem  : internalStorageManager.getListNotificationByMemberNumber()){
        if(!NotificationItem.getIsRead()){
          numberUnread++;
        }
      }
    }catch (NullPointerException ex){
      return 0;
    }
    return numberUnread;
  }
  @Override
  public void renderListNotification() {

  }
}
