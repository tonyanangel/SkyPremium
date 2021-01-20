package com.skypremiuminternational.app.app.features.travel;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.util.ProductUtil;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class TravelProductViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tvPillarName)
  TextView tvPillarName;
  @BindView(R.id.ll_travel_product)
  LinearLayout ll;
  @BindView(R.id.iv_travel_product)
  ImageView iv;
  @BindView(R.id.tvTitle_travel_product)
  TextView tvTitle;
  @BindView(R.id.tvSubTitle_travel_product)
  TextView tvSubTitle;
  @BindView(R.id.tv_reserve)
  TextView tvReserve;
  @BindView(R.id.tv_view_detail)
  TextView tvViewDetail;
  @BindView(R.id.ivFav_travel_product)
  ImageView ivFavourite;
  @BindView(R.id.iv_new)
  ImageView ivNew;

  boolean fromTravel;
  String pillar;
  String reserveButtonTitle =null;
  String sku=null;
  String linkHGW=null;
  String restaurantIDS=null;
  double longitude = 0;
  double latitude = 0;

  public TravelProductViewHolder(final View itemView,final boolean fromTravel,final String pillar) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.fromTravel = fromTravel;
    this.pillar = pillar;
  }

  public void bind(final ItemsItem item) {
    ViewRatioUtils.setProduct(ll, iv, itemView.getContext());

    String selectDisplayCatogry = "";


    if (item.isNew()) {
      ivNew.setVisibility(View.VISIBLE);
    } else {
      ivNew.setVisibility(View.INVISIBLE);
    }
    tvPillarName.setVisibility(View.VISIBLE);
    tvPillarName.setText(item.getPillarName());


    // === get custom attribute
    for (CustomAttributesItem customAttributesItem : item.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("small_image")) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.dontAnimate();
        requestOptions.error(R.drawable.white);
        requestOptions.centerCrop();
        Glide.with(itemView.getContext())
            .load(URLUtils.formatImageURL(customAttributesItem.getValue().toString()))
            .apply(requestOptions)
            .into(iv);
      }else if(customAttributesItem.getAttributeCode().equalsIgnoreCase("select_display_category")){
        selectDisplayCatogry = customAttributesItem.getValue().toString();
      }
    }
    tvTitle.setText(item.getName());
    // 20200916 - WIKI Toan Tran - disable old code
    //tvSubTitle.setText(item.getCategoryName());

    // 20200916 - WIKI Toan Tran -  enable with new data from api
    if(!selectDisplayCatogry.equalsIgnoreCase(""))
      tvSubTitle.setText(selectDisplayCatogry);
    else
      tvSubTitle.setText(item.getCategoryName());
    //=====

     reserveButtonTitle =null;
     sku=null;
     linkHGW=null;

    if (!ObjectUtil.isNull(item) && !ObjectUtil.isNull(item.getCustomAttributes())) {
      for (CustomAttributesItem customAttributesItem : item.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("reserve_button_title")) {
          reserveButtonTitle = customAttributesItem.getValue().toString();
          Log.e("LOG_INFO", "reserveButtonTitle: " + reserveButtonTitle);
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("reserve_button_link")) {
          sku = customAttributesItem.getValue().toString();
          Log.e("LOG_INFO", "sku: " + sku);
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("hgw_link")){
          linkHGW = customAttributesItem.getValue().toString();
          Log.e("LOG_INFO", "linkHGW: " + linkHGW);
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("restaurant_IDS")){
          restaurantIDS = customAttributesItem.getValue().toString();
          Log.e("LOG_INFO", "restaurant_IDS: " + restaurantIDS);
        }
      }
    }

    /*Handle display reserve button*/
    if (!ObjectUtil.isNull(reserveButtonTitle) && !ObjectUtil.isEmptyStr(reserveButtonTitle)){
      tvReserve.setText(reserveButtonTitle);
    }


    //Disable show tvReserve button
   /* if (fromTravel) {

      *//*Disable reserve button on list travel*//*
      *//*if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
        tvReserve.setVisibility(View.VISIBLE);
      } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
        tvReserve.setVisibility(View.VISIBLE);
      } else {
        tvReserve.setVisibility(View.VISIBLE);
        tvReserve.setText(itemView.getResources().getString(R.string.tv_reserve));
      }*//*
      *//*Disable reserve button on list travel*//*
      tvReserve.setVisibility(View.INVISIBLE);
    } else {
      if(item.getExtensionAttributes().isDisplayReservations()){
        if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
          tvReserve.setVisibility(View.VISIBLE);
        } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
          tvReserve.setVisibility(View.VISIBLE);
        } else {
          tvReserve.setVisibility(View.INVISIBLE);
        }
      }else {
        tvReserve.setVisibility(View.INVISIBLE);
      }
    }*/
    if(pillar.equalsIgnoreCase(Constants.WINE_AND_DINE)){
      if(item.getExtensionAttributes().isDisplayReservations()){
        if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
          tvReserve.setVisibility(View.VISIBLE);
          tvViewDetail.setVisibility(View.VISIBLE);
        }if(!TextUtils.isEmpty(restaurantIDS)){
          tvReserve.setVisibility(View.VISIBLE);
          tvViewDetail.setVisibility(View.VISIBLE);
        }else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
          tvReserve.setVisibility(View.VISIBLE);
          tvViewDetail.setVisibility(View.VISIBLE);
        } else {
          tvReserve.setVisibility(View.INVISIBLE);
          tvViewDetail.setVisibility(View.INVISIBLE);

        }
      }else {
        tvReserve.setVisibility(View.INVISIBLE);
        tvViewDetail.setVisibility(View.INVISIBLE);

      }

    }else {
      tvReserve.setVisibility(View.INVISIBLE);
      tvViewDetail.setVisibility(View.INVISIBLE);
    }

    //=====
    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
    ivFavourite.setImageResource(favIcon);
  }

  private boolean isReservations(String selectCate,String itemCate) {
    //isReservations(selectDisplayCatogry,item.getCategoryName();
    if(ProductUtil.isValid(selectCate)){
      if(selectCate.toUpperCase().contains(Constants.RESERVATIONS.toUpperCase())){
        return true;
      }else {
        return false;
      }
    }else {
      if(itemCate.toUpperCase().contains(Constants.RESERVATIONS.toUpperCase())){
        return true;
      }else {
        return false;
      }
    }
  }
  private  boolean getDisplayDeservations(){
    return false;
  }


  public String getReserveButtonTitle() {
    return reserveButtonTitle;
  }

  public void setReserveButtonTitle(String reserveButtonTitle) {
    this.reserveButtonTitle = reserveButtonTitle;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getLinkHGW() {
    return linkHGW;
  }

  public void setLinkHGW(String linkHGW) {
    this.linkHGW = linkHGW;
  }
}
