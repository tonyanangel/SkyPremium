package com.skypremiuminternational.app.app.features.travel;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class TravelProductFeaturedViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.rl)
  ViewGroup baseLayout;
  @BindView(R.id.iv)
  ImageView iv;
  @BindView(R.id.tvTitle)
  TextView tvTitle;
  @BindView(R.id.tvSubTitle)
  TextView tvSubTitle;
  @BindView(R.id.ivRank)
  ImageView ivRank;
  @BindView(R.id.ivFav)
  ImageView ivFav;
  @BindView(R.id.tv_category)
  TextView tvCategory;
  @BindView(R.id.iv_new)
  ImageView ivNew;

  TravelProductFeaturedViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final ItemsItem item) {
    ViewRatioUtils.setProduct(baseLayout, iv, itemView.getContext());
    String selectDisplayCatogry  = "";
    if (item.isNew()) {
      ivNew.setVisibility(View.VISIBLE);
    } else {
      ivNew.setVisibility(View.INVISIBLE);
    }
    tvCategory.setText(item.getPillarName());
    for (CustomAttributesItem customAttributesItem : item.getCustomAttributes()) {

      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("small_image")) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.dontAnimate();
        requestOptions.centerCrop();
        requestOptions.error(R.drawable.white);
        Glide.with(itemView.getContext())
            .load(URLUtils.formatImageURL(customAttributesItem.getValue().toString()))
            .apply(requestOptions)
            .into(iv);
      }else if(customAttributesItem.getAttributeCode().equalsIgnoreCase("select_display_category")){
        selectDisplayCatogry = customAttributesItem.getValue().toString();
      }
    }
    if(!selectDisplayCatogry.equalsIgnoreCase(""))
      tvSubTitle.setText(selectDisplayCatogry);
    else
      tvSubTitle.setText(item.getCategoryName());


    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
    ivFav.setImageResource(favIcon);

    tvTitle.setText(item.getName());

    ivRank.setImageResource(R.drawable.ic_featured_product);
  }
}
