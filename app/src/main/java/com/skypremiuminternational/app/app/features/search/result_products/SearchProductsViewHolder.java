package com.skypremiuminternational.app.app.features.search.result_products;

import androidx.fragment.app.FragmentManager;
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

public class SearchProductsViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ll_shopping_product)
  LinearLayout ll;
  @BindView(R.id.iv_shopping_product)
  ImageView iv;
  @BindView(R.id.tvTitle_shopping_product)
  TextView tvTitle;
  @BindView(R.id.tvSubTitle_shopping_product)
  TextView tvSubTitle;

  private ItemsItem itemsItem;
  private String categoryID;

  public SearchProductsViewHolder(final View itemView, final FragmentManager fragmentManager) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /*if (categoryID.equals(Constants.TRAVEL)) {
          TravelDetailActivity.startMe(itemView.getContext(), itemsItem,
              itemsItem.getCategoryName(), true);
          } else if (categoryID.equals(Constants.WINE_AND_DINE)) {
          ActivityChooser.startMe(itemView.getContext(), itemsItem, itemsItem.getCategoryName(),
              false);

          } else if (categoryID.equals(Constants.SHOPPING)) {
          ActivityChooser.startMe(itemView.getContext(), itemsItem, itemsItem.getCategoryName(),
              false);
          } else if (categoryID.equals(Constants.WELLNESS)) {
          ActivityChooser.startMe(itemView.getContext(), itemsItem, itemsItem.getCategoryName(),
              false);
          } else {
          Timber.e("No category found for search!");
          }*/
      }
    });
  }

  public void bind(final ItemsItem itemsItem, String categoryID) {
    this.itemsItem = itemsItem;

    this.categoryID = categoryID != null ? categoryID : itemsItem.getPillarId();

    ViewRatioUtils.setProduct(ll, iv, itemView.getContext());
    for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("small_image")) {


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.dontAnimate();
        requestOptions.centerCrop();
        requestOptions.error(R.drawable.white);


        Glide.with(itemView.getContext())
            .load(URLUtils.formatImageURL(customAttributesItem.getValue().toString().toString()))
            .apply(requestOptions)
            .into(iv);
      }
    }
    tvTitle.setText(itemsItem.getName());
    tvSubTitle.setText(itemsItem.getCategoryName());
  }
}
