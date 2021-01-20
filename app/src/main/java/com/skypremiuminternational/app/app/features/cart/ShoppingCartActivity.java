package com.skypremiuminternational.app.app.features.cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.CheckoutActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.listener.AnimationListener;
import com.skypremiuminternational.app.app.view.SkyTextInputEditEventLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithouLineLayout;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import dagger.android.AndroidInjection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.http.GET;
import timber.log.Timber;

/**
 * Created by aeindraaung on 1/25/18.
 */

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter>
        implements ShoppingCartView<ShoppingCartPresenter> {

  private static final int REQUEST_CODE_CHECKOUT = 78;

  @BindView(R.id.img_delete_discount_applied)
  ImageView imgDeleteDiscount;
  @BindView(R.id.layout_notice)
  ViewGroup layoutNotice;
  @BindView(R.id.layout_shipping_fee)
  ViewGroup layoutShippingFee;
  @BindView(R.id.tv_shipping_fee)
  TextView tvShippingFee;
  @BindView(R.id.layout_delivery_fee)
  ViewGroup layoutDeliveryFee;
  @BindView(R.id.tv_delivery_fee)
  TextView tvDeliveryFee;
  @BindView(R.id.tv_error_message)
  TextView tvErrorMessage;
  @BindView(R.id.tv_royalty_pts_in_dollars)
  TextView tvPtsInDollars;
  @BindView(R.id.layout_royalty_points)
  ViewGroup layoutRoyaltyPts;
  @BindView(R.id.layout_tax)
  ViewGroup layoutTax;
  @BindView(R.id.promo_code_redeem_button)
  ImageView imgPromoCodeRedeemButton;
  @BindView(R.id.txt_tax)
  TextView tvTax;
  @BindView(R.id.label_discount_type)
  TextView tvDiscountType;
  @BindView(R.id.txt_sky_dollar_earn)
  TextView tvSkyDollarEarn;
  @BindView(R.id.root_layout)
  ViewGroup rootLayout;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.layout_description_redeem)
  RelativeLayout layoutDescriptionRedeem;
  @BindView(R.id.btn_redeem)
  TextView btnRedeem;
  @BindView(R.id.img_drop_down)
  ImageView imgDropDown;
  @BindView(R.id.recyclerview_shoppingcart)
  RecyclerView recyclerShoppingCart;
  @BindView(R.id.layout_no_royalty_pts)
  RelativeLayout layoutNoLoyaltyPoint;
  @BindView(R.id.nestedScroll)
  NestedScrollView nestedScrollView;
  @BindView(R.id.tv_save)
  TextView btnSave;
  @BindView(R.id.txt_discount)
  TextView tvDiscount;
  @BindView(R.id.layout_discount_applied)
  RelativeLayout layoutDiscountApplied;
  @BindView(R.id.edit_promo)
  SkyTextInputWithouLineLayout editPromo;
  @BindView(R.id.layout_promocode)
  RelativeLayout layoutPromoCode;
  @BindView(R.id.layout_redeem_loyalty)
  RelativeLayout layoutRedeemLoyalty;
  @BindView(R.id.layout_hide_promocode)
  FrameLayout layoutHidePromocode;
  @BindView(R.id.layout_hide_title_redeem)
  FrameLayout layoutHideTitleRedeem;
  @BindView(R.id.layout_no_product)
  RelativeLayout layoutNoProduct;
  @BindView(R.id.tvTitle_toolbar_amount)
  TextView txtToolbarAmount;
  @BindView(R.id.txt_total_amount)
  TextView txtSubtotal;
  @BindView(R.id.txt_amount_grand_total)
  TextView txtGrandTotal;
  @BindView(R.id.layout_child_nested)
  RelativeLayout layoutChildNested;
  @BindView(R.id.stiRedeemPoint)
  SkyTextInputEditEventLayout stiRedeemPoint;
  @Inject
  ErrorMessageFactory errorMessageFactory;


  //Variable

  public static final String FROM = "FROM";
  public static final String CART_ICON = "CART_ICON";
  public static final String BUY_NOW = "BUY_NOW";

  private ShoppingCartAdapter adapter;
  private List<CartDetailItem> cartDetailItemList;
  private ProgressDialog progressDialog;
  private boolean keyBoardIsVisible;
  private Animation fadeIn, fadeOut;
  private boolean isCheckout, applyRedeem;
  private boolean userHasRoyaltyPoints;
  private boolean isFullRedemption = false;
  private int amtShoppingProduct;
  private int currentFocusPosition = RecyclerView.NO_POSITION;
  private Double roylapoint = 0.00;
  public static Boolean CouponClick = false;
  public static Boolean SkyClick = false;
  public String from = "CART_ICON";

  public static void start(Context context) {
    Intent starter = new Intent(context, ShoppingCartActivity.class);
    context.startActivity(starter);
  }
  public static void start(Context context,String from) {
    Intent starter = new Intent(context, ShoppingCartActivity.class);
    starter.putExtra(FROM,from);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_cart);

    ButterKnife.bind(this);
    setupRecyclerView();

    if(getIntent().getStringExtra(FROM)!=null){
      from = getIntent().getStringExtra(FROM);
    }



    rootLayout.getViewTreeObserver()
            .addOnGlobalLayoutListener(() -> { int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
              if (heightDiff > MetricsUtils.convertDpToPixel(200,
                      ShoppingCartActivity.this)) { // if more than 200 dp, it's probably a keyboard...
                keyBoardIsVisible = true;
                //btnSave.startAnimation(fadeOut);
                btnSave.setVisibility(View.INVISIBLE);
              } else if (keyBoardIsVisible) {
                btnSave.startAnimation(fadeIn);
                btnSave.setVisibility(View.VISIBLE);
                keyBoardIsVisible = false;
                // checkAndUpdateItemCounts();
              }
            });

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);

    tvTitleToolbar.setText(getString(R.string.toolbarShoppinCart));
    fadeIn = AnimationUtils.loadAnimation(this, R.anim.btn_fadein);
    fadeOut = AnimationUtils.loadAnimation(this, R.anim.btn_fadeout);

    layoutChildNested.setOnTouchListener((v, event) -> {
      if (keyBoardIsVisible) {
        hideKeyboard();
      }
      return false;
    });

    recyclerShoppingCart.setOnTouchListener((v, event) -> {
      if (keyBoardIsVisible) {
        hideKeyboard();
      }
      return false;
    });


    if(from.equalsIgnoreCase(BUY_NOW)){
      presenter.getLoyaltyPoints();
      presenter.getCartDetailBuyNow();
      scrollChangeListener();
    }else {
      presenter.getLoyaltyPoints();
      presenter.getCartDetail();
      scrollChangeListener();
    }



  }


  private void checkAndUpdateItemCounts() {
    Integer[] counts = adapter.getCounts();
    List<CartDetailItem> items = adapter.getItems();

    if (counts != null && counts.length == items.size()) {
      for (int i = 0; i < items.size(); i++) {
        if (counts[i] != items.get(i).getQty()) {
          presenter.updateItemCount(items.get(i), counts[i]);
          break;
        }
      }
    }
  }

  @Inject
  @Override
  public void injectPresenter(ShoppingCartPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.img_close)
  public void OnClickImgClose() {
    finish();
  }

  @OnClick(R.id.layout_royalty_points)
  public void toggleLoyaltyPtsLayout() {
    if (isRoyaltyPtsLayoutExpanded()) {
      collapseRoyaltyPtsLayout();
    } else {
      expandRoyaltyPtsLayout();
    }
  }

  private boolean isRoyaltyPtsLayoutExpanded() {
    return layoutDescriptionRedeem.getVisibility() == View.VISIBLE
            || layoutNoLoyaltyPoint.getVisibility() == View.VISIBLE;
  }

  private void expandRoyaltyPtsLayout() {
    imgDropDown.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_up_arrow));
    if (userHasRoyaltyPoints) {
      layoutDescriptionRedeem.setVisibility(View.VISIBLE);
      btnRedeem.setVisibility(View.VISIBLE);
    } else {
      layoutNoLoyaltyPoint.setVisibility(View.VISIBLE);
    }
    checkAndSetCheckoutBtnVisibility();
  }

  private void collapseRoyaltyPtsLayout() {
    imgDropDown.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow));
    if (userHasRoyaltyPoints) {
      layoutDescriptionRedeem.setVisibility(View.GONE);
      btnRedeem.setVisibility(View.GONE);
    } else {
      layoutNoLoyaltyPoint.setVisibility(View.GONE);
    }
    checkAndSetCheckoutBtnVisibility();
  }

  @OnClick(R.id.tv_save)
  public void onSave() {
    if (isCheckout) {
      presenter.checkCartLimit(from);
    } else {
      finish();
    }
  }

  private void setupRecyclerView() {
    recyclerShoppingCart.setNestedScrollingEnabled(false);
    recyclerShoppingCart.setLayoutManager(new LinearLayoutManager(this));
    adapter = new ShoppingCartAdapter();
    adapter.setActionListener(new ShoppingCartAdapter.ActionListener() {
      @Override
      public void onDeleteClicked(CartDetailItem item) {
        // Tell user that SKY$ has been reset to 0 IF there is any SKY$ redeemed
        // AND item clicked is not the only item in the cart
        if (!tvDiscount.getText().equals("") && amtShoppingProduct != 1 &&
                tvDiscountType.getText().toString().equals(getString(R.string.hint_loyalty_sky_dollars_redeem))) {
          Toast.makeText(ShoppingCartActivity.this, "Sky Dollar Redemption reset to S$0.00",
                  Toast.LENGTH_LONG).show();
        }
        if(from.equalsIgnoreCase(BUY_NOW)){
          presenter.deleteShoppingCartBuyNow(String.valueOf(item.getItemId()));
        }else {
          presenter.deleteShoppingCart(String.valueOf(item.getItemId()));
        }
      }

      @Override
      public void onItemCountUpdated(CartDetailItem item, int updatedQyt) {
        if(from.equalsIgnoreCase(BUY_NOW)) {
          presenter.updateItemCountAfterChangeQtyBuyNow(item, updatedQyt);
        }else {
          presenter.updateItemCountAfterChangeQty(item, updatedQyt);
        }
        //presenter.getCartDetail();
      }

      @Override
      public void onItemCheckInStock() {
        presenter.checkCartLimitForAdapter();
      }

      @Override
      public void onFocusChanged(boolean hasFocus, int adapterPosition) {
        if (hasFocus) {
          currentFocusPosition = adapterPosition;
        } else {
          //checkAndUpdateItemCounts();
        }
      }
    });
    recyclerShoppingCart.setAdapter(adapter);
    recyclerShoppingCart.setHasFixedSize(true);
  }
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);


    // Checks whether a hardware keyboard is available
    if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
      Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
    } else if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
      Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
    }
  }

  private void scrollChangeListener() {
    nestedScrollView.setOnScrollChangeListener(
            (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

              View view = v.getChildAt(v.getChildCount() - 1);
              int diff = (view.getBottom() - (v.getHeight() + v.getScrollY()));

              // if diff is zero, then the bottom has been reached
              if (diff <= 0) {
                btnSave.startAnimation(fadeIn);
                btnSave.setVisibility(View.VISIBLE);
              } else {
                if (btnSave.getVisibility() == View.VISIBLE) {
                  btnSave.startAnimation(fadeOut);
                  btnSave.setVisibility(View.INVISIBLE);
                }
              }
            });
  }

  @Override
  public void showLoading(String message) {
    if (!isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void render(CartDetailResponse response) {
    //get config
    amtShoppingProduct = response.getItems().size();
    if (response.getItems().size() > 0) {
      cartDetailItemList = response.getItems();
      isCheckout = true;
      txtToolbarAmount.setVisibility(View.VISIBLE);
      txtToolbarAmount.setText(String.format("(%s)", String.valueOf(response.getItems().size())));
      nestedScrollView.setVisibility(View.VISIBLE);
      editPromo.setVisibility(View.VISIBLE);
      stiRedeemPoint.setVisibility(View.VISIBLE);
      layoutNoProduct.setVisibility(View.GONE);
      adapter.setDataList(response.getItems(), response.getVirtual());
      adapter.setCurrentFocusPosition(currentFocusPosition);
     /* if (response.isContainVirtualProduct()) {
          layoutPromoCode.setVisibility(View.GONE);
          layoutRedeemLoyalty.setVisibility(View.GONE);
          layoutNotice.setVisibility(View.GONE);
      } else {
          layoutPromoCode.setVisibility(View.VISIBLE);
          layoutRedeemLoyalty.setVisibility(View.VISIBLE);
          layoutNotice.setVisibility(View.VISIBLE);

      } */
      checkAndSetCheckoutBtnVisibility();
    } else {
      isCheckout = false;
      txtToolbarAmount.setVisibility(View.GONE);
      nestedScrollView.setVisibility(View.GONE);
      editPromo.setVisibility(View.INVISIBLE);
      stiRedeemPoint.setVisibility(View.INVISIBLE);
      layoutNoProduct.setVisibility(View.VISIBLE);
      btnSave.setText(getString(R.string.txt_continue_shopping));
      btnSave.setVisibility(View.VISIBLE);
    }

    getConfigPromotionCode(BuildConfig.BASE_URL+URL.CONFIGCOUPON,response);
    getConfigSkyDollar(BuildConfig.BASE_URL+URL.CONFIGSKY,response);


    presenter.checkCartLimitForAdapter();
  }

  private void checkAndSetCheckoutBtnVisibility() {
    List<CartDetailItem> items = adapter.getItems();
    if (items != null && items.size() == 1 && !isRoyaltyPtsLayoutExpanded()) {
      if (!keyBoardIsVisible && btnSave.getVisibility() != View.VISIBLE) {
        btnSave.startAnimation(fadeIn);
        btnSave.setVisibility(View.VISIBLE);
      }
    } else {
      if (applyRedeem && btnSave.getVisibility() != View.VISIBLE) {
        btnSave.setVisibility(View.VISIBLE);
      } else {
        if (btnSave.getVisibility() != View.INVISIBLE) {
          btnSave.startAnimation(fadeOut);
          btnSave.setVisibility(View.INVISIBLE);
        }
      }
    }
  }

  @Override
  public void render(Throwable error) {
    Timber.e(error);
    Toast.makeText(this, errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void render(CartAllInformationResponse response, boolean containVirtualProduct) {
    if (amtShoppingProduct > 0) {
      if (response.getSubtotal() != null && !TextUtils.isEmpty(response.getSubtotal())) {
        txtSubtotal.setText(
                String.format("S$%s",
                        DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getSubTotal()))));
      } else {
        txtSubtotal.setText(
                String.format("S$%s", DecimalUtil.roundTwoDecimals(0)));
      }
      if (response.getGrandTotal() != null && !TextUtils.isEmpty(response.getGrandTotal())) {
        txtGrandTotal.setText(
                String.format("S$%s",
                        DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getGrandTotal()))));
        if (response.getGrandTotal().equals("0")) {
          isFullRedemption = true;
        }
      } else {
        txtGrandTotal.setText(
                String.format("S$%s", DecimalUtil.roundTwoDecimals(0)));
      }

      if (response.getShippingFee() != null && !response.getShippingFee().equalsIgnoreCase("")) {
        layoutShippingFee.setVisibility(View.GONE);
        tvShippingFee.setText(
                String.format("S$%s",
                        DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getShippingFee()))));
      } else {
        layoutShippingFee.setVisibility(View.GONE);
      }

      if (response.getDeliveryFee() != null && !response.getDeliveryFee().equalsIgnoreCase("")) {
        layoutDeliveryFee.setVisibility(View.GONE);
        tvDeliveryFee.setText(
                String.format("S$%s",
                        DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getDeliveryFee()))));
      } else {
        layoutDeliveryFee.setVisibility(View.GONE);
      }

      if (isValid(response.getTax())) {
        layoutTax.setVisibility(View.VISIBLE);
        tvTax.setText(
                String.format("S$%s",
                        DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getTax()))));
      } else {
        layoutTax.setVisibility(View.GONE);
      }

      setImgDeleteDiscountListener();

      String redeemedDollars = response.getRedeemedSkyDollars();
      if (redeemedDollars != null
              && !redeemedDollars.equalsIgnoreCase("0")
              && !redeemedDollars.equalsIgnoreCase("")) {
        if (containVirtualProduct) {
          imgDeleteDiscount.setVisibility(View.GONE);
        } else {
          imgDeleteDiscount.setVisibility(View.VISIBLE);
        }
        disableRedeemButton();
        //stiRedeemPoint.setEnabled(false);
        layoutDiscountApplied.setVisibility(View.VISIBLE);
        tvDiscountType.setText(R.string.hint_loyalty_sky_dollars_redeem);
        tvDiscount.setText(
                String.format(DecimalUtil.roundTwoDecimals(Double.parseDouble(redeemedDollars))));

        enablePromoLayout(false);
      } else {
        String discount = "0" ;
        if (response.getDiscountAmount().startsWith("-")) {
          discount = response.getDiscountAmount().substring(1);
        }
        if (response.getDiscountAmount() != null
            && !discount.equalsIgnoreCase("0")
            && response.getDiscountAmount().length() > 0) {
            layoutDiscountApplied.setVisibility(View.VISIBLE);
            tvDiscountType.setText(R.string.hint_promocode);
            tvDiscount.setText("-"+
                String.format( DecimalUtil.roundTwoDecimals(Double.parseDouble(discount))));
            enableRoyaltyPtsLayout(false);
            imgPromoCodeRedeemButton.setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.ic_applied_button));

        } else {
          enableRoyaltyPtsLayout(true);
          enableRedeemButton();
          stiRedeemPoint.setEnabled(true);
          enablePromoLayout(true);
          stiRedeemPoint.setText("");
          layoutDiscountApplied.setVisibility(View.GONE);
        }


      }
    }
    double earndollar = response.getExtensionAttributes().getTotalSkyDollarEarn();
    tvSkyDollarEarn.setText(
        String.format(
            DecimalUtil.roundTwoDecimals(earndollar)));

    List<CartDetailItem> items = adapter.getItems();
    if(items.size()>0) {
      if (items.get(0).getSku().contains("membership_renewal")) {
        stiRedeemPoint.setEnabled(false);
        btnRedeem.setVisibility(View.GONE);
      } else {
        stiRedeemPoint.setEnabled(true);
        if(layoutDescriptionRedeem.getVisibility() == View.VISIBLE) {
          btnRedeem.setVisibility(View.VISIBLE);
        }else{
          btnRedeem.setVisibility(View.GONE);
        }
      }
    }
  }

  private boolean isValid(String fee) {
    return fee != null && !fee.equalsIgnoreCase("") && !fee.equalsIgnoreCase("0");
  }

  @Override
  public void render(String royaltyPoints, String grandTotal) {
    if (royaltyPoints != null
            && !TextUtils.isEmpty(royaltyPoints)
            && grandTotal != null
            && !TextUtils.isEmpty(grandTotal)) {
      roylapoint = Double.parseDouble(royaltyPoints);

      if (grandTotal.equals("0")) {
        isFullRedemption = true;
      }
      if (Float.parseFloat(grandTotal) > 0 && Float.parseFloat(grandTotal) <= Float.parseFloat(
              royaltyPoints)) {
        //set GrandTotal
        royaltyPoints = grandTotal;
      }
      stiRedeemPoint.setPoint(royaltyPoints);
      if(btnRedeem.isEnabled()){
        stiRedeemPoint.setEnabled(true);
      }else{
        //stiRedeemPoint.setEnabled(false);
      }

      try {
        if (Float.parseFloat(royaltyPoints) > 0) {
          if (!isFullRedemption) {
            tvPtsInDollars.setText(
                    "S$" + DecimalUtil.roundTwoDecimals(Double.parseDouble(royaltyPoints)));
          } else {
            tvPtsInDollars.setText("S$0.00");
            // disableRedeemButton();
            stiRedeemPoint.setEnabled(false);
          }
          userHasRoyaltyPoints = true;
        } else {
          if (isFullRedemption) {
            userHasRoyaltyPoints = true;
            tvPtsInDollars.setText("S$0.00");
          } else {
            userHasRoyaltyPoints = false;
          }
        }
      } catch (Exception e) {
        userHasRoyaltyPoints = false;
      }
    } else {
      userHasRoyaltyPoints = false;
    }
  }

  @Override
  public void proceedToCheckout(int checkoutType, String paymentType) {
    if(cartDetailItemList.size()>0) {
      if (cartDetailItemList.get(0).getSku().contains("membership_renewal")) {
        CheckoutActivity.startMe(this, REQUEST_CODE_CHECKOUT, checkoutType, paymentType, isFullRedemption,true);
      }else{
        CheckoutActivity.startMe(this, REQUEST_CODE_CHECKOUT, checkoutType, paymentType, isFullRedemption,false);
      }
    }

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHECKOUT) {
      finish();
    }
  }

  @Override
  public void renderErrorMessage(String errorMessage) {
    tvErrorMessage.setText(errorMessage);
    tvErrorMessage.setVisibility(View.VISIBLE);
    AnimationUtil.expand(tvErrorMessage, new AnimationListener() {
      @Override
      public void onAnimationEnd(Animation animation) {
        //do nothing
      }
    });

    new Handler().postDelayed(() -> {
      if (!ShoppingCartActivity.this.isDestroyed()) {
        AnimationUtil.collapse(tvErrorMessage, new AnimationListener() {
          @Override
          public void onAnimationEnd(Animation animation) {
            tvErrorMessage.setVisibility(View.GONE);
          }
        });
      }
    }, 5000);
  }

  @Override
  public void renderMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void notifyCartItemStatusChanged() {
    adapter.notifyDataSetChanged();
  }

  private void enablePromoLayout(boolean enable) {
    editPromo.setEnabled(enable);
    imgPromoCodeRedeemButton.setEnabled(enable);
    editPromo.setText("");
    editPromo.setHint(getString(R.string.hint_promocode));
  }

  private void enableRoyaltyPtsLayout(boolean enable) {
    layoutRoyaltyPts.setEnabled(enable);
    if (enable) {
      layoutHideTitleRedeem.setVisibility(View.GONE);
    } else {
      if (isRoyaltyPtsLayoutExpanded()) {
        collapseRoyaltyPtsLayout();
      }
      layoutHideTitleRedeem.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void hideLoading() {
    if (!isDestroyed()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.promo_code_redeem_button)
  public void onClickApplyPromoCode() {
    presenter.applyPromoCode(editPromo.getText(),from);
  }

  @OnClick(R.id.btn_redeem)
  public void onClickRedeem() {
    // Get value of available royalty points, but remove "S$" and ',' from string first
    String tempPoints = tvPtsInDollars.getText().toString()
            .replace("S$", "")
            .replace(",", "");
    float availableRoyaltyPoints = Float.valueOf(tempPoints);

    applyRedeem = true;
    try {
      float points = Float.parseFloat(stiRedeemPoint.getText());
      if (points > 0) {
        // Check that SKY$ offset is more than or equal to $1.00 or exactly $0.00
        if (availableRoyaltyPoints - points >= 1.0 || availableRoyaltyPoints - points == 0.0) {
          // If offset is $0.00
          if (availableRoyaltyPoints == points) {
            isFullRedemption = true;
          }
          //disableRedeemButton();
          presenter.applyRoyaltyPoints(points,from);
        } else {
          displayAlertDialog(txtGrandTotal.getText().toString().substring(2));  // remove the preceeding "S$"
        }
      } else {
        stiRedeemPoint.setError("must be greater than 0");
      }
    } catch (Exception e) {
      stiRedeemPoint.setError("amount to redeem");
    }
  }

  private void displayAlertDialog(String value) {
    AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
    builder.setTitle("Please enter another value")
            .setMessage(String.format(
                    ShoppingCartActivity.this.getString(R.string.error_invalid_skydollar_amount), value))
            .setPositiveButton("OK", (dialogInterface, i) -> {
              // Do Nothing and dismiss
            }).show();
  }

  private void disableRedeemButton() {
    stiRedeemPoint.setEnabled(false);
    btnRedeem.setText("Applied");
    btnRedeem.setEnabled(false);
    btnRedeem.setBackgroundResource(R.drawable.background_grey);

    // Also change Promo Code redeem button to disabled
    imgPromoCodeRedeemButton.setImageDrawable(ContextCompat.getDrawable(
            this, R.drawable.ic_redeem_button_invalid));
  }

  private void enableRedeemButton() {
    btnRedeem.setText("Redeem");
    btnRedeem.setEnabled(true);
    btnRedeem.setBackgroundResource(R.drawable.background_gold);

    // Also change Promo Code redeem button to enabled
    imgPromoCodeRedeemButton.setImageDrawable(ContextCompat.getDrawable(
            this, R.drawable.ic_redeem_button));
  }

  private void setImgDeleteDiscountListener() {
    List<CartDetailItem> items = adapter.getItems();
    if(items.size()>0) {
      if (!items.get(0).getSku().contains("membership_renewal")) {
        layoutDiscountApplied.setOnClickListener(v -> {   // SKY$/promocode discount delete clicked
          String promo = getString(R.string.hint_promocode);
          String royalty = getString(R.string.hint_loyalty_sky_dollars_redeem);
          String discountType = tvDiscountType.getText().toString();

          if (discountType.equalsIgnoreCase(promo)) {
            presenter.deletePromoCode();
            imgPromoCodeRedeemButton.setImageDrawable(ContextCompat.getDrawable(
                    this, R.drawable.ic_redeem_button));
          } else if (discountType.equalsIgnoreCase(royalty)) {
            presenter.deleteRoyaltyPoints(from);
            isFullRedemption = false; // Reset flag in case previous value was a full redemption
          }
        });
      }
    }
  }

  private void setImgDeleteDiscountforconfigpromocodeListener() {
    List<CartDetailItem> items = adapter.getItems();
    if(items.size()>0) {
      if (!items.get(0).getSku().contains("membership_renewal")) {
        if (layoutDiscountApplied.getVisibility() == View.VISIBLE) {
          String promo = getString(R.string.hint_promocode);
          String discountType = tvDiscountType.getText().toString();

          if (discountType.equalsIgnoreCase(promo)) {
            presenter.deletePromoCode();
            imgPromoCodeRedeemButton.setImageDrawable(ContextCompat.getDrawable(
                    this, R.drawable.ic_redeem_button));
          }
        }
      }
    }

  }
  private void setImgDeleteDiscountforconfigSkyListener() {
    List<CartDetailItem> items = adapter.getItems();
    if(items.size()>0) {
      if (!items.get(0).getSku().contains("membership_renewal")) {
        if (layoutDiscountApplied.getVisibility() == View.VISIBLE) {
          String royalty = getString(R.string.hint_loyalty_sky_dollars_redeem);
          String discountType = tvDiscountType.getText().toString();


          if (discountType.equalsIgnoreCase(royalty)) {
            presenter.deleteRoyaltyPoints(from);
            isFullRedemption = false; // Reset flag in case previous value was a full redemption
          }
        }
      }
    }

  }
  public void getConfigPromotionCode(String url,CartDetailResponse response){


    RequestQueue requestQueue = Volley.newRequestQueue(this);

    StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String s) {

        List<CartDetailItem> items = adapter.getItems();
        if (response.isContainVirtualProduct()) {
          layoutPromoCode.setVisibility(View.GONE);
          layoutNotice.setVisibility(View.GONE);

        } else {
          if(items.size()>0) {
            if(items!=null&&items.get(0).getSku().contains("membership_renewal") ){
              if(s.contains("\"true\"")) {
                layoutPromoCode.setVisibility(View.VISIBLE);
                layoutNotice.setVisibility(View.VISIBLE);
                stiRedeemPoint.setEnabled(true);
                btnRedeem.setEnabled(true);
              }else {
                layoutPromoCode.setVisibility(View.GONE);
                layoutNotice.setVisibility(View.GONE);
                setImgDeleteDiscountforconfigpromocodeListener();
                stiRedeemPoint.setEnabled(false);
                btnRedeem.setEnabled(false);
              }
            }else if(items!=null
                    &&!items.get(0).getSku().contains("membership_renewal") ){
              layoutPromoCode.setVisibility(View.VISIBLE);
              layoutNotice.setVisibility(View.VISIBLE);
            }
          }
          if(layoutPromoCode.getVisibility() == View.GONE && layoutRedeemLoyalty.getVisibility() == View.GONE){
            layoutNotice.setVisibility(View.GONE);
          }

        }
      }
    },
            new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Failed to load Redeem Coupon code config.",Toast.LENGTH_SHORT).show();
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
  public void getConfigSkyDollar(String url,CartDetailResponse response){


    RequestQueue requestQueue = Volley.newRequestQueue(this);

    StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String s) {

        if (response.isContainVirtualProduct()) {
          layoutRedeemLoyalty.setVisibility(View.GONE);
          layoutNotice.setVisibility(View.GONE);

        } else {
          List<CartDetailItem> items = adapter.getItems();
          if(items.size()>0) {
            if (items.get(0).getSku().contains("membership_renewal") && Double.parseDouble(items.get(0).getPrice()) > roylapoint) {
              if(s.contains("\"true\"")) {
                layoutRedeemLoyalty.setVisibility(View.VISIBLE);
                layoutNotice.setVisibility(View.VISIBLE);
                stiRedeemPoint.setEnabled(false);
                btnRedeem.setEnabled(false);
              }else{
                layoutRedeemLoyalty.setVisibility(View.GONE);
                layoutNotice.setVisibility(View.GONE);
                setImgDeleteDiscountforconfigSkyListener();
                stiRedeemPoint.setEnabled(true);
                btnRedeem.setEnabled(true);
              }
            }else if(!items.get(0).getSku().contains("membership_renewal")){
              stiRedeemPoint.setEnabled(true);
              btnRedeem.setEnabled(true);
              layoutRedeemLoyalty.setVisibility(View.VISIBLE);
              layoutNotice.setVisibility(View.VISIBLE);
            }else{
              layoutRedeemLoyalty.setVisibility(View.VISIBLE);
              layoutNotice.setVisibility(View.VISIBLE);
            }
          }

          if(layoutPromoCode.getVisibility() == View.GONE && layoutRedeemLoyalty.getVisibility() == View.GONE){
            layoutNotice.setVisibility(View.GONE);
          }
        }

      }
    },
            new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Failed to load Redeem Sky Dollar config.",Toast.LENGTH_SHORT).show();
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


}