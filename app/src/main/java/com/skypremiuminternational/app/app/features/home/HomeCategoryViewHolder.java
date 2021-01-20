package com.skypremiuminternational.app.app.features.home;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.domain.models.home.Banner;
import com.skypremiuminternational.app.domain.models.navigation.NavigationEvent;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.iv)
  ImageView iv;
  @BindView(R.id.tvSubTitle)
  TextView tvSubTitle;
  @BindView(R.id.tvHeading)
  TextView tvHeading;
  @BindView(R.id.rlCategoryItem)
  RelativeLayout rlCategoryItem;

  Banner banner;

  public HomeCategoryViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (banner.getTargetUrl() != null) {
          switch (banner.getTargetUrl()) {
            case "travel.html":
              RxBus.get().post(new NavigationEvent(Constants.DEEP_LINK_LANDING_TRAVEL));
              break;

            case "wine-dine.html":
              RxBus.get().post(new NavigationEvent(Constants.DEEP_LINK_LANDING_WINE));
              break;

            case "shopping.html":
              RxBus.get().post(new NavigationEvent(Constants.DEEP_LINK_LANDING_SHOPPING));
              break;

            case "wellness.html":
              RxBus.get().post(new NavigationEvent(Constants.DEEP_LINK_LANDING_WELLNESS));
              break;
          }
        }
      }
    });
  }

  public void bind(final Banner item, int height,int viewType) {
    this.banner = item;
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.glide_placeholder_square);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(itemView.getContext())
        .load(banner.getImage())
        .apply(requestOptions)
        .into(iv);

    if (banner.getName() == null) {
      tvSubTitle.setVisibility(View.GONE);
    } else {
      tvSubTitle.setText(Html.fromHtml(banner.getName()));
    }
    if (banner.getHeader() == null) {
      tvHeading.setVisibility(View.GONE);
    } else {
      tvHeading.setText(Html.fromHtml(banner.getHeader()));
    }
    RelativeLayout.LayoutParams lp;
    if(viewType==HomeAdapter.VIEW_TYPE_MAIN){
      lp =  new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                          height*2);
    }else {
      lp =  new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
          height);
    }
    rlCategoryItem.setLayoutParams(lp);

    /*tvCustomer.setText(history.getCustomer().getName());
    tvDateTime.setText(history.getTransactionDate());
    tvId.setText(history.getBillNo());
    tvValue.setText(String.valueOf(history.getTotalAmount()));*/
  }
  public void bind(final Banner item) {
    this.banner = item;
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.glide_placeholder_square);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(itemView.getContext())
        .load(banner.getImage())
        .apply(requestOptions)
        .into(iv);

    if (banner.getName() == null) {
      tvSubTitle.setVisibility(View.GONE);
    } else {
      tvSubTitle.setText(Html.fromHtml(banner.getName()));
    }
    if (banner.getHeader() == null) {
      tvHeading.setVisibility(View.GONE);
    } else {
      tvHeading.setText(Html.fromHtml(banner.getHeader()));
    }
    /*tvCustomer.setText(history.getCustomer().getName());
    tvDateTime.setText(history.getTransactionDate());
    tvId.setText(history.getBillNo());
    tvValue.setText(String.valueOf(history.getTotalAmount()));*/
  }
}
