package com.skypremiuminternational.app.app.features.profile.order.detail;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.profile.order.detail.edit_review.EditReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.see_review.ReviewContentDialogFragment;
import com.skypremiuminternational.app.app.utils.AspectRatioImageView;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.domain.models.myOrder.detail.ItemsItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skypremiuminternational.app.app.utils.Constants.COMMENT_APPROVED;
import static com.skypremiuminternational.app.app.utils.Constants.COMMENT_NOT_APPROVED;
import static com.skypremiuminternational.app.app.utils.Constants.COMMENT_PENDING;

public class OrderDetailItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.img_product)
  AspectRatioImageView imgProduct;
  @BindView(R.id.txt_product_name)
  TextView txtProductName;
  @BindView(R.id.txt_kind_of_product) TextView txtKindOfProduct;
  @BindView(R.id.txt_product_amount) TextView txtProductAmount;
  @BindView(R.id.img_delete)
  ImageView imgDelete;
  @BindView(R.id.eidt_qty)
  EditText editQty;
  @BindView(R.id.txt_amount) TextView txtAmount;
  @BindView(R.id.layout_main)
  LinearLayout layoutMain;
  @BindView(R.id.tv_error_message) TextView tvErrorMessage;
  @BindView(R.id.tv_loyaltyValue) TextView tvLoyaltyValue;
  @BindView(R.id.tvReview) TextView tvReview;
  @BindView(R.id.tvEdtReview) TextView tvEdtReview;
  @BindView(R.id.tvSeeReview) TextView tvSeeReview;

  public OrderDetailItemViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(FragmentManager fragmentManager, ItemsItem response, int position,String status) {
    imgDelete.setVisibility(View.GONE);
    if ((position % 2) != 0) {
      layoutMain.setBackgroundColor(
          ContextCompat.getColor(layoutMain.getContext(), R.color.white));
    } else {
      layoutMain.setBackgroundColor(
          ContextCompat.getColor(layoutMain.getContext(), R.color.background));
    }

    tvErrorMessage.setVisibility(View.GONE);
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(itemView.getContext())
        .load(URLUtils.formatImageURL(response.getExtensionAttributes().getThumbnailImageUrl()))
        .listener(URLUtils.getGlideListener(itemView.getContext(),response.getExtensionAttributes().getThumbnailImageUrl(),imgProduct))
        .apply(requestOptions)
        .into(imgProduct);
    txtKindOfProduct.setText(response.getExtensionAttributes().getCategoryName());
    txtProductName.setText(response.getName());
    if (response.getPrice() != null && !TextUtils.isEmpty(response.getPrice())) {
      txtProductAmount.setText(
          String.format("S$%s", DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getPrice()))));
    }
    editQty.setText(String.valueOf(response.getQtyOrdered()));
    String amount =
        DecimalUtil.roundTwoDecimals(response.getQtyOrdered() * Double.parseDouble(response.getPrice()));
    txtAmount.setText(String.format("S$%s", amount));
    editQty.setEnabled(false);
    if(response.getLoyaltyValue() != null && !response.getLoyaltyValue().equals("0")){
      tvLoyaltyValue.setVisibility(View.VISIBLE);
      tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", response.getLoyaltyValue()));
      tvLoyaltyValue.setAllCaps(false);
    }


    //<<START>> 20200623 - Wiki Toan Tran - TEMP to test layout

    if(status.equalsIgnoreCase(Constants.ORDER_COMPLETE)){
      if(response.getStatus()!=null){
        if(response.getStatus().equalsIgnoreCase(COMMENT_APPROVED)){
          tvSeeReview.setVisibility(View.VISIBLE);
          tvEdtReview.setVisibility(View.GONE);
          tvReview.setVisibility(View.GONE);
        }else if (response.getStatus().equalsIgnoreCase(COMMENT_PENDING)) {
          tvSeeReview.setVisibility(View.GONE);
          tvEdtReview.setVisibility(View.VISIBLE);
          tvReview.setVisibility(View.GONE);
        }else if(response.getStatus().equalsIgnoreCase(COMMENT_NOT_APPROVED)){
          tvSeeReview.setVisibility(View.GONE);
          tvEdtReview.setVisibility(View.VISIBLE);
          tvReview.setVisibility(View.GONE);
        }else if (response.getStatus().equalsIgnoreCase("0")) {
          tvReview.setVisibility(View.VISIBLE);
          tvEdtReview.setVisibility(View.GONE);
          tvSeeReview.setVisibility(View.GONE);
        }
      }else{
        tvReview.setVisibility(View.VISIBLE);
        tvEdtReview.setVisibility(View.GONE);
        tvSeeReview.setVisibility(View.GONE);
      }
    }else{
      tvReview.setVisibility(View.GONE);
      tvEdtReview.setVisibility(View.GONE);
      tvSeeReview.setVisibility(View.GONE);
    }

    //<<END>> 20200623 - Wiki Toan Tran - TEMP to test layout


    //<<START>> 20200623 - Wiki Toan Tran - set onClick to review
    tvReview.setOnClickListener(v -> {
      ImageDataUtils.clearAllBitmap(v.getContext());
      ReviewDialogFragment frg = ReviewDialogFragment.newInstance();
      Bundle bundle = new Bundle();
      bundle.putString("imageUrl",URLUtils.formatImageURL(response.getExtensionAttributes().getThumbnailImageUrl()));
      bundle.putString("nameProduct",response.getName());
      bundle.putString("idOrder",response.getOrderId());
      bundle.putString("idProduct",response.getProductId());
      bundle.putString("sku",response.getSku());
      frg.setArguments(bundle);
      frg.show(fragmentManager,"REVIEW");
    });
    //<<END>> 20200623 - Wiki Toan Tran - set onClick to review


    //<<START>> 20200623 - Wiki Toan Tran - set onClick to edit review
    tvEdtReview.setPaintFlags(tvEdtReview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    tvSeeReview.setPaintFlags(tvSeeReview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    tvEdtReview.setOnClickListener(v->{
      ImageDataUtils.clearAllBitmapEditZone(v.getContext());
      EditReviewDialogFragment frg = EditReviewDialogFragment.newInstance();
      Bundle bundle = new Bundle();
      bundle.putString("imageUrl",URLUtils.formatImageURL(response.getExtensionAttributes().getThumbnailImageUrl()));
      bundle.putString("nameProduct",response.getName());
      bundle.putString("idOrder",response.getOrderId());
      bundle.putString("idProduct",response.getProductId());
      bundle.putString("sku",response.getSku());
      frg.setArguments(bundle);
      frg.show(fragmentManager,"EDIT-REVIEW");
    });
    //<<END>> 20200623 - Wiki Toan Tran - set onClick to edit review
    tvSeeReview.setOnClickListener(v->{
      ImageDataUtils.clearAllBitmapEditZone(v.getContext());
      ReviewContentDialogFragment frg = ReviewContentDialogFragment.newInstance();
      Bundle bundle = new Bundle();
      bundle.putString("imageUrl",URLUtils.formatImageURL(response.getExtensionAttributes().getThumbnailImageUrl()));
      bundle.putString("nameProduct",response.getName());
      bundle.putString("idOrder",response.getOrderId());
      bundle.putString("idProduct",response.getProductId());
      bundle.putString("sku",response.getSku());
      frg.setArguments(bundle);
      frg.show(fragmentManager,"SEE-REVIEW");
    });

  }
}
