package com.skypremiuminternational.app.app.features.memership_services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.andrognito.kerningview.KerningTextView;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.membership.Title;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.AndroidInjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MembershipActivity extends BaseActivity<MembershipPresenter>
    implements MembershipView<MembershipPresenter> {

  @BindView(R.id.ly_cart)
  ViewGroup lyCart;
  @BindView(R.id.iv_cart)
  ImageView ivCart;
  @BindView(R.id.ly_cart_count)
  ViewGroup lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rlBanner)
  RelativeLayout rl;
  @BindView(R.id.tvName)
  TextView tvName;

  @BindView(R.id.ivTravel)
  ImageView ivTravel;
  @BindView(R.id.ivRestaurant)
  ImageView ivRestaurant;
  @BindView(R.id.ivWellness)
  ImageView ivWellness;
  @BindView(R.id.ivShopping)
  ImageView ivShopping;
  @BindView(R.id.ivEnquiry)
  ImageView ivEnquiry;
  @BindView(R.id.ivTechnical)
  ImageView ivTechnical;
  @BindView(R.id.ivFees)
  ImageView ivFees;
  @BindView(R.id.ivFeedbacks)
  ImageView ivFeedbacks;

  @BindView(R.id.edtTravelDesc)
  EditText edtTravelDesc;
  @BindView(R.id.edtRestaurantDesc)
  EditText edtRestaurantDesc;
  @BindView(R.id.edtWellnessDesc)
  EditText edtWellnessDesc;
  @BindView(R.id.edtShoppingDesc)
  EditText edtShoppingDesc;
  @BindView(R.id.edtEnquiryDesc)
  EditText edtEnquiryDesc;
  @BindView(R.id.edtTechnicalDesc)
  EditText edtTechnicalDesc;
  @BindView(R.id.edtFeesDesc)
  EditText edtFeesDesc;
  @BindView(R.id.edtFeedbacksDesc)
  EditText edtFeedbacksDesc;

  @BindView(R.id.llTravelDesc)
  LinearLayout llTravelDesc;
  @BindView(R.id.llRestaurantDesc)
  LinearLayout llRestaurantDesc;
  @BindView(R.id.llWellnessDesc)
  LinearLayout llWellnessDesc;
  @BindView(R.id.llShoppingDesc)
  LinearLayout llShoppingDesc;
  @BindView(R.id.llEnquiryDesc)
  LinearLayout llEnquiryDesc;
  @BindView(R.id.llTechnicalDesc)
  LinearLayout llTechnicalDesc;
  @BindView(R.id.llFeesDesc)
  LinearLayout llFeesDesc;
  @BindView(R.id.llFeedbacksDesc)
  LinearLayout llFeedbacksDesc;

  @BindView(R.id.ktvTravelSubmit)
  KerningTextView ktvTravelSubmit;
  @BindView(R.id.ktvRestaurantSubmit)
  KerningTextView ktvRestaurantSubmit;
  @BindView(R.id.ktvWellnessSubmit)
  KerningTextView ktvWellnessSubmit;
  @BindView(R.id.ktvShoppingSubmit)
  KerningTextView ktvShoppingSubmit;
  @BindView(R.id.ktvEnquirySubmit)
  KerningTextView ktvEnquirySubmit;
  @BindView(R.id.ktvTechnicalSubmit)
  KerningTextView ktvTechnicalSubmit;
  @BindView(R.id.ktvFeesSubmit)
  KerningTextView ktvFeesSubmit;
  @BindView(R.id.ktvFeedbacksSubmit)
  KerningTextView ktvFeedbacksSubmit;

  //@BindView(R.id.elw) ExpandableListView elw;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private int lastExpandedPosition = -1;

  String travel = "";

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, MembershipActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  public static void startMe(Context context, String travel) {
    Intent intent = new Intent(context, MembershipActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra("Travel", travel);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_membership_service);
    ButterKnife.bind(this);

    tvTitle_toolbar.setText(getResources().getString(R.string.membership));

    ViewRatioUtils.set3_1(rl, 0, this);

    travel = getIntent().getStringExtra("Travel");

    lyCart.setVisibility(View.VISIBLE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ktvTravelSubmit.setLetterSpacing(0.0f);
      ktvRestaurantSubmit.setLetterSpacing(0.0f);
      ktvWellnessSubmit.setLetterSpacing(0.0f);
      ktvShoppingSubmit.setLetterSpacing(0.0f);
      ktvEnquirySubmit.setLetterSpacing(0.0f);
      ktvTechnicalSubmit.setLetterSpacing(0.0f);
      ktvFeesSubmit.setLetterSpacing(0.0f);
      ktvFeedbacksSubmit.setLetterSpacing(0.0f);
    } else {
      ktvTravelSubmit.setKerningFactor(0.0f);
      ktvRestaurantSubmit.setKerningFactor(0.0f);
      ktvWellnessSubmit.setKerningFactor(0.0f);
      ktvShoppingSubmit.setKerningFactor(0.0f);
      ktvEnquirySubmit.setKerningFactor(0.0f);
      ktvTechnicalSubmit.setKerningFactor(0.0f);
      ktvFeesSubmit.setKerningFactor(0.0f);
      ktvFeedbacksSubmit.setKerningFactor(0.0f);
    }

    presenter.getUserDetail();
    if (travel != null) {
      checkDescription(llTravelDesc);
      edtTravelDesc.setText(travel);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Inject
  @Override
  public void injectPresenter(MembershipPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  @OnClick(R.id.llCall)
  void onClickCall() {
    Intent dial = new Intent();
    dial.setAction("android.intent.action.DIAL");
    dial.setData(Uri.parse("tel:" + "+65 6533 0000"));
    startActivity(dial);
  }

  @OnClick(R.id.llMail)
  void onClickMail() {
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
    emailIntent.setData(Uri.parse("mailto: " + BuildConfig.EMAIL));
    startActivity(Intent.createChooser(emailIntent, "Email via..."));
  }

  @Override
  public void render(MembershipViewState viewState) {
    if (viewState.isLoading()) {
    } else {
      if (viewState.isSuccess()) {
        UserDetailResponse response = viewState.message();
        tvName.setText(
            response.getPrefix() + " " + response.getFirstname() + " " + response.getLastname());
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void render(String cartCount) {
    if (TextUtils.isEmpty(cartCount) || cartCount.equalsIgnoreCase("0")) {
      lyCartCount.setVisibility(View.INVISIBLE);
    } else {
      lyCartCount.setVisibility(View.VISIBLE);
      tvCartCount.setText(cartCount);
    }
  }

  //@Subscribe public void onTextChange(MembershipEvent membershipEvent) {
  //  ViewRatioUtils.setListViewHeight(elw);
  //}

  void setMembership() {

    List<Title> headerList = new ArrayList<>();
    headerList.add(new Title("Travel Arrangements & Itineraries", R.drawable.ic_travel_black,
        R.drawable.ic_travel));
    headerList.add(new Title("Restaurant Bookings & Reservations", R.drawable.ic_wine_black,
        R.drawable.ic_wine));
    headerList.add(new Title("Wellness Programs & Activities", R.drawable.ic_wellness_black,
        R.drawable.ic_wellness));
    headerList.add(new Title("Shopping Recommendations", R.drawable.ic_shopping_black,
        R.drawable.ic_shopping));
    headerList.add(new Title("General Enquiry / Other Assistance", R.drawable.ic_enquiry_black,
        R.drawable.ic_enquiry_black));
    HashMap<String, List<String>> childMap = new HashMap<>();
    for (int i = 0; i < headerList.size(); i++) {
      List<String> childList = new ArrayList<>();
      if (i == 0) {
        if (travel == null) {
          childList.add("");
        } else {
          childList.add(travel);
        }
      } else {
        childList.add("");
      }
      childMap.put(headerList.get(i).getTitle(), childList);
    }
    //MembershipExpandableAdapter membershipExpandableAdapter =
    //    new MembershipExpandableAdapter(this, headerList, childMap);
    //elw.setAdapter(membershipExpandableAdapter);
    //ViewRatioUtils.setListViewHeight(elw);
    //
    //if (travel != null) {
    //  elw.expandGroup(0);
    //}
  }

  @OnClick(R.id.llTravel)
  void onClickTravel() {
    checkDescription(llTravelDesc);
  }

  @OnClick(R.id.llRestaurant)
  void onClickRestaurant() {
    checkDescription(llRestaurantDesc);
  }

  @OnClick(R.id.llWellness)
  void onClickWellness() {
    checkDescription(llWellnessDesc);
  }

  @OnClick(R.id.llShopping)
  void onClickShopping() {
    checkDescription(llShoppingDesc);
  }

  @OnClick(R.id.llEnquiry)
  void onClickEnquiry() {
    checkDescription(llEnquiryDesc);
  }

  @OnClick(R.id.llTechnical)
  void onClickTechnical() {
    checkDescription(llTechnicalDesc);
  }

  @OnClick(R.id.llFees)
  void onClickFees() {
    checkDescription(llFeesDesc);
  }

  @OnClick(R.id.llFeedbacks)
  void onClickFeedbacks() {
    checkDescription(llFeedbacksDesc);
  }

  @OnClick(R.id.ktvTravelSubmit)
  void onClickTravelSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_travel), edtTravelDesc);
  }

  @OnClick(R.id.ktvRestaurantSubmit)
  void onClickRestaurantSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_restaurants),
        edtRestaurantDesc);
  }

  @OnClick(R.id.ktvWellnessSubmit)
  void onClickWellnessSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_wellness), edtWellnessDesc);
  }

  @OnClick(R.id.ktvShoppingSubmit)
  void onClickShoppingSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_shopping), edtShoppingDesc);
  }

  @OnClick(R.id.ktvEnquirySubmit)
  void onClickEnquirySubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_enquiry), edtEnquiryDesc);
  }

  @OnClick(R.id.ktvTechnicalSubmit)
  void onClickTechnicalSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_technical),
        edtTechnicalDesc);
  }

  @OnClick(R.id.ktvFeesSubmit)
  void onClickFeesSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_fees), edtFeesDesc);
  }

  @OnClick(R.id.ktvFeedbacksSubmit)
  void onClickFeedbacksSubmit() {
    sentDescriptionViaMail(getResources().getString(R.string.membership_feedbacks),
        edtFeedbacksDesc);
  }

  private void sentDescriptionViaMail(String title, EditText edt) {
    if (!TextUtils.isEmpty(edt.getText().toString())) {
      Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.setType("plain/text");
      intent.setData(Uri.parse("mailto:"));
      intent.putExtra(Intent.EXTRA_EMAIL, new String[]{BuildConfig.EMAIL});
      intent.putExtra(Intent.EXTRA_SUBJECT, title);
      intent.putExtra(Intent.EXTRA_TEXT, edt.getText().toString());
      startActivity(Intent.createChooser(intent, "Email via..."));
    }
  }

  private void checkDescription(LinearLayout ll) {
    if (ll.getId() == llTravelDesc.getId()) {
      if (llTravelDesc.getVisibility() == View.GONE) {
        showDescription(llTravelDesc, ivTravel, R.drawable.ic_travel_accent);
      } else {
        hideDescription(llTravelDesc, ivTravel, R.drawable.ic_travel_black);
      }
    } else {
      hideDescription(llTravelDesc, ivTravel, R.drawable.ic_travel_black);
    }

    if (ll.getId() == llRestaurantDesc.getId()) {
      if (llRestaurantDesc.getVisibility() == View.GONE) {
        showDescription(llRestaurantDesc, ivRestaurant, R.drawable.ic_wine_accent);
      } else {
        hideDescription(llRestaurantDesc, ivRestaurant, R.drawable.ic_wine_black);
      }
    } else {
      hideDescription(llRestaurantDesc, ivRestaurant, R.drawable.ic_wine_black);
    }

    if (ll.getId() == llWellnessDesc.getId()) {
      if (llWellnessDesc.getVisibility() == View.GONE) {
        showDescription(llWellnessDesc, ivWellness, R.drawable.ic_wellness_accent);
      } else {
        hideDescription(llWellnessDesc, ivWellness, R.drawable.ic_wellness_black);
      }
    } else {
      hideDescription(llWellnessDesc, ivWellness, R.drawable.ic_wellness_black);
    }

    if (ll.getId() == llShoppingDesc.getId()) {
      if (llShoppingDesc.getVisibility() == View.GONE) {
        showDescription(llShoppingDesc, ivShopping, R.drawable.ic_shopping_accent);
      } else {
        hideDescription(llShoppingDesc, ivShopping, R.drawable.ic_shopping_black);
      }
    } else {
      hideDescription(llShoppingDesc, ivShopping, R.drawable.ic_shopping_black);
    }

    if (ll.getId() == llEnquiryDesc.getId()) {
      if (llEnquiryDesc.getVisibility() == View.GONE) {
        showDescription(llEnquiryDesc, ivEnquiry, R.drawable.ic_enquiry_accent);
      } else {
        hideDescription(llEnquiryDesc, ivEnquiry, R.drawable.ic_enquiry_black);
      }
    } else {
      hideDescription(llEnquiryDesc, ivEnquiry, R.drawable.ic_enquiry_black);
    }

    if (ll.getId() == llTechnicalDesc.getId()) {
      if (llTechnicalDesc.getVisibility() == View.GONE) {
        showDescription(llTechnicalDesc, ivTechnical, R.drawable.ic_support_accent);
      } else {
        hideDescription(llTechnicalDesc, ivTechnical, R.drawable.ic_support_black);
      }
    } else {
      hideDescription(llTechnicalDesc, ivTechnical, R.drawable.ic_support_black);
    }

    if (ll.getId() == llFeesDesc.getId()) {
      if (llFeesDesc.getVisibility() == View.GONE) {
        showDescription(llFeesDesc, ivFees, R.drawable.ic_membership_fee_accent);
      } else {
        hideDescription(llFeesDesc, ivFees, R.drawable.ic_membership_fee_black);
      }
    } else {
      hideDescription(llFeesDesc, ivFees, R.drawable.ic_membership_fee_black);
    }

    if (ll.getId() == llFeedbacksDesc.getId()) {
      if (llFeedbacksDesc.getVisibility() == View.GONE) {
        showDescription(llFeedbacksDesc, ivFeedbacks, R.drawable.ic_feedback_accent);
      } else {
        hideDescription(llFeedbacksDesc, ivFeedbacks, R.drawable.ic_feedback_black);
      }
    } else {
      hideDescription(llFeedbacksDesc, ivFeedbacks, R.drawable.ic_feedback_black);
    }
  }

  private void showDescription(LinearLayout ll, ImageView iv, int image) {
    iv.setImageResource(image);
    ll.setVisibility(View.VISIBLE);
  }

  private void hideDescription(LinearLayout ll, ImageView iv, int image) {
    iv.setImageResource(image);
    ll.setVisibility(View.GONE);
  }

  @OnClick(R.id.ly_cart)
  void onCartClicked() {
    ShoppingCartActivity.start(this);
  }
}
