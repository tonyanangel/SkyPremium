package com.skypremiuminternational.app.app.features.estore.detail.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class VHProduct extends RecyclerView.ViewHolder {

  @BindView(R.id.iv_new)
  ImageView ivNew;
  @BindView(R.id.ll_travel_product)
  LinearLayout ll;
  @BindView(R.id.iv_travel_product)
  ImageView iv;
  @BindView(R.id.tvTitle_travel_product)
  TextView tvTitle;
  @BindView(R.id.tvSubTitle_travel_product)
  TextView tvSubTitle;
  @BindView(R.id.iv_fav)
  public ImageView ivFav;
  @BindView(R.id.tvPillarName)
  TextView tvPillarName;

  public VHProduct(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final ItemsItem item) {
    ViewRatioUtils.setProduct(ll, iv, itemView.getContext());

    tvPillarName.setText(item.getPillarName());
    if (item.isNew()) {
      ivNew.setVisibility(View.VISIBLE);
    } else {
      ivNew.setVisibility(View.INVISIBLE);
    }
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
      }
    }
    tvTitle.setText(item.getName());
    tvSubTitle.setText(item.getCategoryName());
    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
    ivFav.setImageResource(favIcon);
  }
}

