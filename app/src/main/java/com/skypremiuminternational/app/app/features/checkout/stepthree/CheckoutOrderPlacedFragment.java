package com.skypremiuminternational.app.app.features.checkout.stepthree;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.j256.ormlite.stmt.query.In;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.features.checkout.steptwo.OrderItemAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CustomNoUnderLineLinkUtils;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.myOrder.Address;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import dagger.android.support.AndroidSupportInjection;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by aeindraaung on 2/2/18.
 */

public class CheckoutOrderPlacedFragment extends BaseFragment<CheckoutOrderPlacedPresenter>
        implements CheckoutOrderPlacedView<CheckoutOrderPlacedPresenter> {
  private static final int REQUEST_CODE_WRITE_EXTERNAL = 123;

  @BindView(R.id.txt_title_billing)
  TextView tvBillingAddress;
  @BindView(R.id.layout_root)
  ViewGroup layoutRoot;
  @BindView(R.id.tvNext)
  TextView tvNext;
  @BindView(R.id.recycler_product)
  RecyclerView recyclerProduct;
  @BindView(R.id.txt_order_id)
  TextView txtOrderId;
  @BindView(R.id.txt_title_delivery_address)
  TextView txtTitleDeliveryAddress;
  @BindView(R.id.txt_shipping_customer_name)
  TextView tvShippingCustomerName;
  @BindView(R.id.txt_shipping_phone)
  TextView txtCustomerPhone;
  @BindView(R.id.txt_shipping_address)
  TextView txtCustomerAddress;
  @BindView(R.id.txt_billing_customer_name)
  TextView tvBillingCustomerName;
  @BindView(R.id.txt_billing_customer_phone)
  TextView tvBillingCustomerPhone;
  @BindView(R.id.txt_billing_customer_address)
  TextView tvBillingCustomerAddress;
  @BindView(R.id.txt_credit_card)
  TextView txtCreditCard;
  @BindView(R.id.img_visa)
  ImageView imgVisa;
  @BindView(R.id.txt_processing_state)
  TextView txtProcessingState;
  @BindView(R.id.txt_amt_subtotal)
  TextView txtAmtSubtotal;
  @BindView(R.id.layout_subtotal)
  FrameLayout layoutSubtotal;
  @BindView(R.id.txt_amt_grand_total)
  TextView txtAmtGrandTotal;
  @BindView(R.id.layout_shipping_fee)
  ViewGroup layoutShippingFee;
  @BindView(R.id.tv_shipping_fee)
  TextView tvShippingFee;
  @BindView(R.id.layout_discount)
  ViewGroup layoutDiscount;
  @BindView(R.id.tv_discount_type)
  TextView tvDiscountType;
  @BindView(R.id.tv_discount_amount)
  TextView tvDiscountAmount;
  @BindView(R.id.layout_sky_dollar)
  ViewGroup layoutSkyDollarEarn;
  @BindView(R.id.tv_sky_dollar_earn_type)
  TextView tvSkyEarnDollar;
  @BindView(R.id.tv_sky_dollar_earn)
  TextView tvSkyDollarEarnAmount;
  @BindView(R.id.layout_delivery_fee)
  ViewGroup layoutDeliveryFee;
  @BindView(R.id.txt_amt_delivery_fee)
  TextView tvAmtDeliveryFee;
  @BindView(R.id.layout_tax)
  ViewGroup layoutTax;
  @BindView(R.id.txt_amt_tax)
  TextView txtAmtTax;
  @BindView(R.id.txtHeader)
  TextView txtHeader;
  @BindView(R.id.txtSubHeader)
  TextView txtSubHeader;
  @BindView(R.id.txt_contact_sub_header)
  TextView txtConstansSubHeader;
  @BindView(R.id.txt_title_payment_by)
  TextView txt_title_payment_by;
  @BindView(R.id.layout_deliveryaddress)
  RelativeLayout layoutdelivery;

  private Unbinder unbinder;
  private OrderItemAdapter adapter;
  private Integer orderId;

  private ProgressDialog progressDialog;
  @Inject
  ErrorMessageFactory errorMessageFactory;
  private List<ISOCountry> isoCountries;
  private List<CountryCode> countryCodes;
  private int checkoutType;
  private boolean isMembership  = false;
  private boolean isFullRedemption  = false;

  public static CheckoutOrderPlacedFragment newInstance(Integer orderId, int checkouttype,boolean isMembership, boolean isFullRedemption) {
    CheckoutOrderPlacedFragment fragment = new CheckoutOrderPlacedFragment();
    fragment.orderId = orderId;
    fragment.isMembership = isMembership;
    fragment.checkoutType = checkouttype;
    fragment.isFullRedemption = isFullRedemption;
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
    unbinder = ButterKnife.bind(view);
    clearDataInstance();
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setMessage("Loading ...");
    if(isFullRedemption){
      txtCreditCard.setText(getString(R.string.card_selection_not_required_title));
      imgVisa.setVisibility(View.INVISIBLE);
    }else {
      txtCreditCard.setVisibility(View.VISIBLE);
      imgVisa.setVisibility(View.VISIBLE);
    }
    setupRecyclerView();
    getPresenter().getCountryCodes(orderId);
  }

  private void clearDataInstance() {
    App.idDeliveryAddress = null;
    App.idBillingAddress = null;
    App.idCard = null;
  }

  @Override
  public void render(OrderDetailResponse orderDetail) {

    layoutSkyDollarEarn.setVisibility(View.GONE);
    tvSkyEarnDollar.setVisibility(View.VISIBLE);
    tvSkyDollarEarnAmount.setVisibility(View.VISIBLE);
    tvSkyEarnDollar.setText(R.string.hint_loyalty_sky_earned);
    tvSkyDollarEarnAmount.setText("S$"+DecimalUtil.roundTwoDecimals(orderDetail.getExtensionAttributes().getTotalSkyCollarEarn()));

    double sum = 0;
    for(ItemsItem item : orderDetail.getItems()){
      sum+= item.getQtyOrdered()* Double.parseDouble(item.getLoyaltyValue());
    }
    tvSkyDollarEarnAmount.setText("S$"+DecimalUtil.roundTwoDecimals(sum));

    if (orderDetail.getStatusHistories() != null && orderDetail.getStatusHistories().size() > 0) {
      String status = orderDetail.getStatusHistories().get(0).getStatus();
      txtHeader.setVisibility(View.VISIBLE);
      txtSubHeader.setVisibility(View.VISIBLE);
      Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
              Html.fromHtml(getResources().getString(R.string.txt_order_contact)));
      Spannable processedText = CustomNoUnderLineLinkUtils.removeUnderlines(spannedText);
      if (status.equalsIgnoreCase(Constants.CHECKOUT_STATUS_PROCESSING) || status.equalsIgnoreCase(
              Constants.CHECKOUT_STATUS_SHIPPING) || status.equalsIgnoreCase(
              Constants.CHECKOUT_STATUS_COMPLETE)) {
        txtHeader.setText(getString(R.string.complete_header));
        txtSubHeader.setText(getString(R.string.complete_subheader));
        txtConstansSubHeader.setText(processedText);
      } else {
        txtHeader.setText(getString(R.string.pending_header));
        txtSubHeader.setText(getString(R.string.pending_subheader));
        txtConstansSubHeader.setText(processedText);

      }
      txtProcessingState.setText(getStatus(orderDetail.getStatusHistories().get(0).getStatus()));
    } else {
      txtHeader.setVisibility(View.GONE);
      txtSubHeader.setVisibility(View.GONE);
    }



    txtConstansSubHeader.setMovementMethod(LinkMovementMethod.getInstance());
    if (orderDetail.getCoupon_code() != null && !TextUtils.isEmpty(
            orderDetail.getCoupon_code())) {
      layoutDiscount.setVisibility(View.VISIBLE);
      tvDiscountType.setText(R.string.hint_promocode);

      String discountAmount = orderDetail.getDiscountAmount();
      if (discountAmount != null) {
        if (discountAmount.startsWith("-")) {
          discountAmount = discountAmount.substring(1);
        }
        if (!TextUtils.isEmpty(discountAmount)) {
          tvDiscountAmount.setText(
                  String.format("-S$%s",
                          DecimalUtil.roundTwoDecimals(Double.parseDouble(discountAmount))));
          tvSkyDollarEarnAmount.setText(
              "S$" + DecimalUtil.roundTwoDecimals(0));
        }
      }
    } else if (orderDetail.getTotalLoyaltyValueRedeemed() != null && !TextUtils.isEmpty(
            orderDetail.getTotalLoyaltyValueRedeemed())) {
      try {
        if (Float.parseFloat(orderDetail.getTotalLoyaltyValueRedeemed()) > 0) {
          layoutDiscount.setVisibility(View.VISIBLE);
          tvDiscountType.setText(R.string.hint_loyalty_sky_dollars_redeemed);
          String discountAmount = orderDetail.getTotalLoyaltyValueRedeemed();
          tvDiscountAmount.setText(
                  "S$" + DecimalUtil.roundTwoDecimals(Double.parseDouble(discountAmount)));
          tvSkyDollarEarnAmount.setText(
              "S$" + DecimalUtil.roundTwoDecimals(0));
        } else {
          layoutDiscount.setVisibility(View.GONE);
        }
      } catch (Exception e) {
        layoutDiscount.setVisibility(View.GONE);
      }
    } else {
      layoutDiscount.setVisibility(View.GONE);
    }
    if (orderDetail.getShippingFee() != null && !TextUtils.isEmpty(
            orderDetail.getShippingFee())) {
      try {
        if (Float.parseFloat(orderDetail.getShippingFee()) > 0) {
          layoutShippingFee.setVisibility(View.VISIBLE);
          tvShippingFee.setText(String.format("S$%s",
                  DecimalUtil.roundTwoDecimals(Double.parseDouble(orderDetail.getShippingFee()))));
        } else {
          layoutShippingFee.setVisibility(View.GONE);
        }
      } catch (Exception e) {
        layoutShippingFee.setVisibility(View.GONE);
      }
    } else {
      layoutShippingFee.setVisibility(View.GONE);
    }

    if (orderDetail.getDeliveryFee() != null && !TextUtils.isEmpty(
            orderDetail.getDeliveryFee())) {
      try {
        if (Float.parseFloat(orderDetail.getDeliveryFee()) > 0) {
          layoutDeliveryFee.setVisibility(View.VISIBLE);
          tvAmtDeliveryFee.setText(String.format("S$%s",
                  DecimalUtil.roundTwoDecimals(Double.parseDouble(orderDetail.getDeliveryFee()))));
        } else {
          layoutDeliveryFee.setVisibility(View.GONE);
        }
      } catch (Exception e) {
        layoutDeliveryFee.setVisibility(View.GONE);
      }
    } else {
      layoutDeliveryFee.setVisibility(View.GONE);
    }

    if (orderDetail.getBaseTaxAmount() != null && !TextUtils.isEmpty(
            orderDetail.getBaseTaxAmount())) {
//      if (orderDetail.getBaseTaxAmount().equalsIgnoreCase("0")) {
//        layoutTax.setVisibility(View.GONE);
//      } else {
      txtAmtTax.setText(
              String.format("S$%s",
                      DecimalUtil.roundTwoDecimals(Double.parseDouble(orderDetail.getBaseTaxAmount()))));
//      }
    } else {
      layoutTax.setVisibility(View.GONE);
    }

    if (orderDetail.getGrandTotal() != null && !TextUtils.isEmpty(orderDetail.getGrandTotal())) {
      txtAmtGrandTotal.setText(
              String.format("S$%s",
                      DecimalUtil.roundTwoDecimals(Double.parseDouble(orderDetail.getGrandTotal()))));
    } else {
      txtAmtGrandTotal.setText(
              String.format("S$%s", DecimalUtil.roundTwoDecimals(0.0)));
    }
    if (orderDetail.getSubtotal() != null && !TextUtils.isEmpty(orderDetail.getSubtotal())) {
      txtAmtSubtotal.setText(
              String.format("S$%s",
                      DecimalUtil.roundTwoDecimals(Double.parseDouble(orderDetail.getSubtotal()))));
    } else {
      txtAmtSubtotal.setText(
              String.format("S$%s", DecimalUtil.roundTwoDecimals(0.0)));
    }

    if(checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS){
      txt_title_payment_by.setVisibility(View.GONE);
      txtCreditCard.setVisibility(View.GONE);
      imgVisa.setVisibility(View.GONE);
      txtTitleDeliveryAddress.setVisibility(View.GONE);
      tvShippingCustomerName.setVisibility(View.GONE);
      txtCustomerPhone.setVisibility(View.GONE);
      txtCustomerAddress.setVisibility(View.GONE);
    }else {
      txt_title_payment_by.setVisibility(View.VISIBLE);
      txtCreditCard.setVisibility(View.VISIBLE);
      imgVisa.setVisibility(View.VISIBLE);
      txtTitleDeliveryAddress.setVisibility(View.VISIBLE);
      tvShippingCustomerName.setVisibility(View.VISIBLE);
      txtCustomerPhone.setVisibility(View.VISIBLE);
      txtCustomerAddress.setVisibility(View.VISIBLE);
      if(isFullRedemption){
        txtCreditCard.setText(getString(R.string.no_payment_information_required));
        txtCreditCard.setTextSize(13);
        imgVisa.setVisibility(View.INVISIBLE);
      }else {
        String creditNumber;
        if (orderDetail.getPayment().getCcLast4() != null) {
          if (!TextUtils.isEmpty(orderDetail.getPayment().getCcLast4())) {
            creditNumber =
                getString(R.string.txt_first_sign_creditcard) + " " + orderDetail.getPayment()
                    .getCcLast4();
          } else {
            creditNumber = getString(R.string.txt_first_sign_creditcard) + " " + getString(
                R.string.txt_null_creditcard);
          }
        } else {
          creditNumber = getString(R.string.txt_first_sign_creditcard) + " " + getString(
              R.string.txt_null_creditcard);
        }
        txtCreditCard.setText(creditNumber);
        txtCreditCard.setVisibility(View.VISIBLE);
        imgVisa.setVisibility(View.VISIBLE);
      }


      String type = orderDetail.getPayment().getCcType();
      if (type != null) {
        if (!TextUtils.isEmpty(type)) {
          if (type.equalsIgnoreCase(Constants.VISA_BRAND)) {
            imgVisa.setImageResource(R.drawable.ic_visa_blue_background);
          } else if (type.equalsIgnoreCase(Constants.MASTER_BRAND)) {
            imgVisa.setImageResource(R.drawable.ic_master_pay);
          } else {
            imgVisa.setImageResource(R.drawable.ic_american_express_blue_background);
          }
        } else {
          imgVisa.setVisibility(View.INVISIBLE);
        }
      } else {
        imgVisa.setVisibility(View.INVISIBLE);
      }
    }



    if (orderDetail.getExtensionAttributes().getShippingAssignments().size() > 0) {
      Address address = orderDetail.getExtensionAttributes()
              .getShippingAssignments()
              .get(0)
              .getShipping()
              .getAddress();
      if (address != null) {
        renderShippingAddress(address);
      }
    }

    com.skypremiuminternational.app.domain.models.myOrder.billingaddress.BillingAddress
            billingAddress = orderDetail.getBillingAddress();
    if (billingAddress != null) {
      showBillingAddressView(true);
      renderBillingAddress(billingAddress);
    } else {
      showBillingAddressView(false);
    }
    txtOrderId.setText(String.format(getString(R.string.label_template_order_id),
            orderDetail.getIncrementId()));
    adapter.setDataList(orderDetail.getItems());
  }

  private void renderBillingAddress(
          com.skypremiuminternational.app.domain.models.myOrder.billingaddress.BillingAddress address) {

    String unitNumber = "";
    String countryName = null;
    String stateName = "";
    String company = "";
    String city = "";
    String phoneNumber = "";

    if (address.getExtensionAttributes() != null
            && address.getExtensionAttributes().getUnitNumber() != null) {
      unitNumber = address.getExtensionAttributes().getUnitNumber();
    }
    String salutation = address.getPrefix();
    if (Validator.isTextValid(salutation) && !salutation.endsWith(".")) {
      salutation += ".";
    }

    tvBillingCustomerName.setText(
            String.format("%s %s",address.getFirstname(), address.getLastname()));
//    tvBillingCustomerPhone.setText(String.format("%s", address.getTelephone()));
    tvBillingCustomerPhone.setVisibility(View.GONE);

    company = address.getCompany();
    city = address.getCity();
    phoneNumber = address.getTelephone();

    if (isoCountries != null && isoCountries.size() > 0) {
      for (ISOCountry countryCode : isoCountries) {
        if (address.getCountryId() != null && countryCode.country_code.equalsIgnoreCase(
                address.getCountryId().trim())) {
          countryName = countryCode.country_name;
          break;
        }
      }
    }

    if (Validator.isTextValid(address.getRegion())) {
      stateName = address.getRegion();
    }

    StringBuilder streets = new StringBuilder();
    if (address.getStreet() != null && address.getStreet().size() > 0) {
      for (String street : address.getStreet()) {
        streets.append(street);
        streets.append(", ");
      }

      if (streets.length() > 0) {
        String str = streets.substring(0, streets.lastIndexOf(","));
        String fullAdd="";

        if (company != null && !TextUtils.isEmpty(company)) {
          fullAdd += company;
        }

        if (unitNumber != null && !TextUtils.isEmpty(unitNumber)) {
          fullAdd += "\n" + unitNumber;
        }

        fullAdd += " "+str;

        if (city != null && !TextUtils.isEmpty(city)) {
          fullAdd += "\n" + city;
        }
        if (stateName != null && !TextUtils.isEmpty(stateName)) {
          fullAdd += " " + stateName;
        }
        if (countryName != null && !TextUtils.isEmpty(countryName)) {

          fullAdd += "\n" + countryName;
        }
        if (address.getPostcode() != null && !TextUtils.isEmpty(address.getPostcode())) {
          if (stateName != null && !TextUtils.isEmpty(stateName)) {
            fullAdd += " " + address.getPostcode();
          } else {
            fullAdd += " " + address.getPostcode();
          }
        }
        if (phoneNumber != null && !TextUtils.isEmpty(phoneNumber)) {

          fullAdd += "\n" + phoneNumber;
        }

        tvBillingCustomerAddress.setText(fullAdd);
      }
    }
  }

  private void renderShippingAddress(Address address) {

    String unitNumber = "";
    String company = "";
    String telephone = "";
    String streetAddress = "";
    String city = "";
    String state = "";
    String postCode = "";
    String countryName = "";


    if (isoCountries != null && isoCountries.size() > 0) {
      for (ISOCountry countryCode : isoCountries) {
        if (address.getCountryId() != null && countryCode.country_code.equalsIgnoreCase(
            address.getCountryId().trim())) {
          countryName = countryCode.country_name;
          break;
        }
      }
    }

    if (address.getOrderDetailAddExtenshion() != null
            && address.getOrderDetailAddExtenshion().getUnitNumber() != null) {
      if (!TextUtils.isEmpty(address.getOrderDetailAddExtenshion().getUnitNumber())) {
        unitNumber = address.getOrderDetailAddExtenshion().getUnitNumber();
      }
    }
    company = address.getCompany();
    telephone = address.getTelephone();
    state =  address.getRegion();
    postCode =  address.getPostcode();
    city =  address.getCity();
    tvShippingCustomerName.setText(
            String.format("%s %s", address.getFirstname(), address.getLastname()));
    //txtCustomerPhone.setText(address.getTelephone());
    txtCustomerPhone.setVisibility(View.GONE);

    StringBuilder streets = new StringBuilder();
    if (address.getStreet() != null) {
      if (address.getStreet().size() > 0) {
        for (String street : address.getStreet()) {
          streets.append(street);
          streets.append(",");
        }
      }

      if (streets.length() > 0) {
        streetAddress = streets.substring(0, streets.lastIndexOf(","));

      }
    }
    String fullAdd = "";
    if(company!=null){
      fullAdd += company ;
    }
    if(unitNumber!=null){
      if(TextUtils.isEmpty(company)){
        fullAdd += ""+unitNumber ;
      }else {
        fullAdd += "\n"+unitNumber ;
      }
    }
    if(streetAddress!=null){
      if(TextUtils.isEmpty(unitNumber)){
        fullAdd += ""+streetAddress;
      }else {
        fullAdd += " "+streetAddress;
      }
    }
    if(city!=null){
      fullAdd += "\n"+city;
    }
    if(state!=null){
      if(TextUtils.isEmpty(city)){
        fullAdd += ""+state;
      }else {
        fullAdd += " "+state;
      }
    }
    if(countryName!=null){
      fullAdd += "\n"+countryName;
    }
    if(postCode!=null){
      if(TextUtils.isEmpty(countryName)){
        fullAdd += ""+postCode;
      }else {
        fullAdd += " "+postCode;
      }
    }
    if(telephone!=null){
      fullAdd += "\n"+telephone;
    }
    txtCustomerAddress.setText(fullAdd);
  }

  private void showBillingAddressView(boolean visible) {
    int visibility = visible ? View.VISIBLE : View.GONE;
    tvBillingAddress.setVisibility(visibility);
    tvBillingCustomerAddress.setVisibility(visibility);
    tvBillingCustomerName.setVisibility(visibility);
    tvBillingCustomerPhone.setVisibility(visibility);
  }

  private String getStatus(String status) {
    if (status.equalsIgnoreCase(Constants.ORDER_PENDING)) {
      return getString(R.string.txt_delivering);
    } else if (status.equalsIgnoreCase(Constants.ORDER_COMPLETE)) {
      return getString(R.string.txt_collected);
    } else if (status.equalsIgnoreCase(Constants.ORDER_HOLDED)) {
      return getString(R.string.txt_holded);
    } else if (status.equalsIgnoreCase(Constants.ORDER_PROCESSING)) {
      return getString(R.string.txt_processing);
    } else if (status.equalsIgnoreCase(Constants.ORDER_CLOSED)) {
      return getString(R.string.txt_closed);
    } else if (status.equalsIgnoreCase(Constants.ORDER_FAIL)) {
      return getString(R.string.txt_fail);
    } else if (status.equalsIgnoreCase(Constants.ORDER_SHIPPING)) {
      return getString(R.string.txt_shipping);
    } else if (status.equalsIgnoreCase(Constants.ORDER_CANCELLED)) {
      return getString(R.string.txt_cancelled);
    }
    return "";
  }

  private Bitmap loadBitmapFromView(View v, int width, int height) {
    Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    v.draw(c);
    return b;
  }

  private void setupRecyclerView() {
    recyclerProduct.setNestedScrollingEnabled(false);
    recyclerProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new OrderItemAdapter();
    recyclerProduct.setAdapter(adapter);
    recyclerProduct.setHasFixedSize(true);
  }

  @Inject
  @Override
  public void injectPresenter(CheckoutOrderPlacedPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_checkout_order_placed;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) unbinder.unbind();
  }

  @OnClick(R.id.tvNext)
  public void onClickedNext() {
    getActivity().setResult(Activity.RESULT_OK);
    getActivity().finish();
  }

  private void takeScreenshot() {
    Date now = new Date();
    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

    try {
      String mPath = null;
      tvNext.setVisibility(View.GONE);
      Bitmap bitmap = loadBitmapFromView(layoutRoot, layoutRoot.getWidth(), layoutRoot.getHeight());
      if (bitmap != null) {
        mPath =
                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "title",
                        null);
      }
      tvNext.setVisibility(View.VISIBLE);

      openScreenshot(mPath);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private void openScreenshot(String path) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    Uri uri = Uri.parse(path);
    intent.setData(uri);
    startActivity(intent);
  }

  private void showRationaleAndRequestPermission() {
    Toast.makeText(getContext(), "Plz grant permission", Toast.LENGTH_SHORT).show();
    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
            REQUEST_CODE_WRITE_EXTERNAL);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_CODE_WRITE_EXTERNAL: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          takeScreenshot();
        }
      }
    }
  }

  public boolean isPermissionGranted() {
    return ContextCompat.checkSelfPermission(getContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  @OnClick(R.id.txt_print)
  void onClickedPrint() {
    if (isPermissionGranted()) {
      takeScreenshot();
    } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION)) {
      showRationaleAndRequestPermission();
    } else {
      requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
              REQUEST_CODE_WRITE_EXTERNAL);
    }
  }

  @Override
  public void render(Throwable e) {
    Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(e), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void render(List<ISOCountry> isoCountries) {
    this.isoCountries = isoCountries;
  }

  @Override
  public void hideLoading() {
    if (!getActivity().isDestroyed() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void showLoading() {
    if (!getActivity().isDestroyed() && !progressDialog.isShowing()) {
      progressDialog.show();
    }
  }
}
