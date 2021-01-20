package com.skypremiuminternational.app.app.features.profile.edit_profile;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
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
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.landing.LandingPresenter;
import com.skypremiuminternational.app.app.features.membership_renewal.MembershipRenewalActivity;
import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateFormatter;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.listener.MemberShipRenewalActionsListener;
import com.skypremiuminternational.app.data.network.URL;

import java.util.HashMap;
import java.util.Map;

import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

/**
 * Created by aeindraaung on 1/30/18.
 */

public class MembershipRenewalDialogFragment extends DialogFragment {
  public static final int DIALOG_TYPE_MEMBERSHIP_RENEWAL = 1;
  public static final int DIALOG_TYPE_WITH_LOYALTY_POINTS = 2;
  public static final int DIALOG_TYPE_WITHOUT_LOYALTY_POINTS = 3;
  public static final int DIALOG_TYPE_WITH_REDEEM_SKY_ONLY = 4;
  public static final int DIALOG_TYPE_WITH_REDEEM_HIDE = 5;

  @BindView(R.id.title)
  TextView txtTitle;
  @BindView(R.id.txt_notice_expire)
  TextView txtNoticeExpire;
  @BindView(R.id.txt_que_expire)
  TextView txtQueExpire;
  @BindView(R.id.btn_renew_later)
  TextView btnRenewLater;
  @BindView(R.id.checkbox)
  CheckBox checkBox;
  @BindView(R.id.btn_keey_loyalty)
  TextView btnKeepLoyalty;
  @BindView(R.id.btn_renew_membership)
  TextView btnRenewMembership;

  @Inject
  DateFormatter dateFormatter;

  private MemberShipRenewalActionsListener actionsListener;
  private String expiryDate = "";
  private Double renewalPrice = null;
  private Double loyaltyPoints = null;
  private int dialogType;
  private boolean Alloff = false;
  private boolean Skyon = false;

  public static MembershipRenewalDialogFragment newInstance(int dialogType, Double renewalPrice,
                                                            Double loyaltyPoints) {
    MembershipRenewalDialogFragment fragment = new MembershipRenewalDialogFragment();
    fragment.dialogType = dialogType;
    fragment.loyaltyPoints = loyaltyPoints;
    fragment.renewalPrice = renewalPrice;
    return fragment;
  }

  public static MembershipRenewalDialogFragment newInstance(int dialogType) {
    MembershipRenewalDialogFragment fragment = new MembershipRenewalDialogFragment();
    fragment.dialogType = dialogType;
    return fragment;
  }

  public static MembershipRenewalDialogFragment newInstance(int dialogType, String expiryDate) {
    MembershipRenewalDialogFragment fragment = new MembershipRenewalDialogFragment();
    fragment.dialogType = dialogType;
    fragment.expiryDate = expiryDate;
    return fragment;
  }

  public void setActionsListener(MemberShipRenewalActionsListener actionsListener) {
    this.actionsListener = actionsListener;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
    wmlp.gravity = Gravity.FILL_HORIZONTAL;

    View view = inflater.inflate(R.layout.dialog_noti_membership_renewal, container, false);
    ButterKnife.bind(this, view);

    setupBindView();

    return view;
  }

  private void setupBindView() {
    if (dialogType == DIALOG_TYPE_MEMBERSHIP_RENEWAL) {
      txtTitle.setText(getResources().getString(R.string.title_noti_membership_renewal));
      txtNoticeExpire.setText(getResources().getString(R.string.txt_notice_membership_renewal));
      txtQueExpire.setText(getResources().getString(R.string.txt_que_membership_renewal,
              dateFormatter.format(expiryDate)));
      checkBox.setVisibility(View.VISIBLE);
      btnRenewLater.setText(getResources().getString(R.string.btn_renew_later));
    } else if (dialogType == DIALOG_TYPE_WITH_LOYALTY_POINTS) {
      txtTitle.setText(getResources().getString(R.string.title_added_to_cart));
      txtNoticeExpire.setText(getResources().getString(R.string.txt_notice_added_to_cart));
      txtQueExpire.setText(
              String.format(getResources().getString(R.string.txt_added_to_cart_description),
                      DecimalUtil.roundTwoDecimals(renewalPrice),
                      DecimalUtil.roundTwoDecimals(loyaltyPoints)));
      checkBox.setVisibility(View.GONE);
      btnRenewMembership.setText(getResources().getString(R.string.btn_use_loyalty));
      btnKeepLoyalty.setVisibility(View.VISIBLE);
      btnRenewLater.setText(getResources().getString(R.string.btn_checkout_later));
    } else if (dialogType == DIALOG_TYPE_WITHOUT_LOYALTY_POINTS) {
      txtTitle.setText(getResources().getString(R.string.title_added_to_cart));
      txtNoticeExpire.setText(getResources().getString(R.string.txt_notice_added_to_cart));
      txtQueExpire.setText(getResources().getString(R.string.txt_que_checkout));
      checkBox.setVisibility(View.GONE);
      btnRenewMembership.setText(getResources().getString(R.string.btn_checkout_now));
      btnKeepLoyalty.setVisibility(View.GONE);
      btnRenewLater.setText(getResources().getString(R.string.btn_checkout_later));
    } else if (dialogType == DIALOG_TYPE_WITH_REDEEM_HIDE) {
      txtTitle.setText(getResources().getString(R.string.title_noti_membership_renewal));
      txtNoticeExpire.setText(getResources().getString(R.string.txt_notice_membership_renewal));
      txtQueExpire.setText(getResources().getString(R.string.txt_que_membership_renewal,
              dateFormatter.format(expiryDate)));
      checkBox.setVisibility(View.VISIBLE);
      btnRenewLater.setText(getResources().getString(R.string.btn_renew_later));
    }


      getConfigPromotionCode(BuildConfig.BASE_URL + URL.CONFIGCOUPON, BuildConfig.BASE_URL + URL.CONFIGSKY);


    //  getConfigSkyDollar(BuildConfig.BASE_URL+URL.CONFIGSKY);
  }

  @OnClick(R.id.imgClose)
  public void onClickClose() {
    if (dialogType == DIALOG_TYPE_MEMBERSHIP_RENEWAL && actionsListener != null) {
      actionsListener.onClickedRenewLater(!checkBox.isChecked());
    }
    dismiss();
  }

  @OnClick(R.id.btn_renew_membership)
  public void onClickRenew() {

    // 20200428 - WIKI Toan Tran - Disable to show dialog show option payment for membership renewal
    /*
    ShoppingCartActivity.CouponClick = false;
    if (actionsListener != null && dialogType == DIALOG_TYPE_MEMBERSHIP_RENEWAL && !Alloff) {
      actionsListener.onClickedRenewMemberShip(!checkBox.isChecked());
    } else if (actionsListener != null && !Alloff  ) {
      ShoppingCartActivity.SkyClick = true;
      actionsListener.onClickedRenew(dialogType == DIALOG_TYPE_WITH_LOYALTY_POINTS);
    }else if(Alloff && actionsListener != null ){
      actionsListener.onClickedRenew(false);
      dismiss();
    }else if (!Skyon && actionsListener != null){
      actionsListener.onClickedRenew(false);
      dismiss();
    }*/

    // 20200428 - WIKI Toan Tran - opent activity with web view
    MembershipRenewalActivity.startMe(getActivity());
    dismiss();
  }

  @OnClick(R.id.btn_keey_loyalty)
  public void onClickKeepLoyalty() {
    if (actionsListener != null && dialogType != DIALOG_TYPE_MEMBERSHIP_RENEWAL) {
      ShoppingCartActivity.CouponClick = true;
      actionsListener.onClickedRenew(false);
    }
    dismiss();
  }

  @OnClick(R.id.btn_renew_later)
  public void onClickRenewLater() {
    ShoppingCartActivity.CouponClick = false;
    if (dialogType == DIALOG_TYPE_MEMBERSHIP_RENEWAL && actionsListener != null) {
      actionsListener.onClickedRenewLater(!checkBox.isChecked());
    }
    dismiss();
  }



  public void getConfigPromotionCode(String urlcode, String urlsky) {


    RequestQueue requestQueuecode = Volley.newRequestQueue(getContext());

    StringRequest postRequestcode = new StringRequest(Request.Method.GET, urlcode, new Response.Listener<String>() {
      @Override
      public void onResponse(String s) {


        if (s.contains("\"true\"") && !btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
          btnKeepLoyalty.setVisibility(View.VISIBLE);
        } else if (s.contains("\"false\"") && !btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
          btnKeepLoyalty.setVisibility(View.VISIBLE);
        } else if (btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
          btnKeepLoyalty.setVisibility(View.GONE);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, urlsky, new Response.Listener<String>() {
          @Override
          public void onResponse(String a) {


            if (a.contains("\"true\"") && !btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
              btnRenewMembership.setVisibility(View.VISIBLE);
              txtQueExpire.setVisibility(View.VISIBLE);
            } else if (a.contains("\"false\"") && !btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
              txtQueExpire.setVisibility(View.GONE);
              btnRenewMembership.setVisibility(View.GONE);
            } else if (btnRenewMembership.getText().toString().contains(getString(R.string.btn_renewal_membership))) {
              btnRenewMembership.setVisibility(View.VISIBLE);
            }
            if(a.contains("\"true\"") ){
              Skyon = true;
            }
            if(a.contains("\"false\"") ){
              Skyon = false;
            }

            if(a.contains("\"false\"") && s.contains("\"false\"")){
              Alloff = true;
            }else{
              Alloff = false;
            }

          }


        },
                new Response.ErrorListener() {

                  @Override
                  public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Failed to load Redeem Sky Dollar config.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Failed to load Redeem Coupon code config.", Toast.LENGTH_SHORT).show();
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


    requestQueuecode.add(postRequestcode);

  }

}
