package com.skypremiuminternational.app.app.features.estore;

import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import java.util.Locale;

import rx.Subscription;
import timber.log.Timber;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

/**
 * Created by codigo on 4/2/18.
 */

public class FeaturedEstoreViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.base_layout)
  FrameLayout baseLayout;
  @BindView(R.id.iv_travel_product)
  ImageView ivProduct;
  @BindView(R.id.layout_count_down)
  ViewGroup lyCountDown;
  @BindView(R.id.tv_expiry_time)
  TextView tvExpiryTime;
  @BindView(R.id.tv_category)
  TextView tvCategory;
  @BindView(R.id.tv_title_feature_product)
  TextView tvFeatureProductTitle;
  @BindView(R.id.tv_subTitle_feature_product)
  TextView tvFeatureProductSubtitle;
  @BindView(R.id.tv_discount_price)
  TextView tvDiscountPrice;
  @BindView(R.id.tv_actual_price)
  TextView tvActualPrice;
  @BindView(R.id.tv_discount_percentage)
  TextView tvDiscountPercentage;
  @BindView(R.id.iv_fav_feature_product)
  ImageView ivFav;
  @BindView(R.id.iv_rank)
  ImageView ivRank;
  @BindView(R.id.tv_end_in)
  TextView tvEndIn;
  @BindView(R.id.iv_new)
  ImageView ivNewLabel;
  private ItemsItem item;
  private Subscription countDownSubscription;

  public FeaturedEstoreViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public Subscription bind(ItemsItem item) {

    ViewRatioUtils.setProduct(baseLayout, ivProduct, itemView.getContext());

    this.item = item;
    if (item.isNew()) {
      ivNewLabel.setVisibility(View.VISIBLE);
    } else {
      ivNewLabel.setVisibility(View.INVISIBLE);
    }
    ViewRatioUtils.setEstoreProduct(baseLayout, ivProduct, itemView.getContext(), false);

    tvFeatureProductTitle.setText(item.getName());
    tvFeatureProductSubtitle.setText(item.getCategoryName());
    ivFav.setVisibility(View.VISIBLE);

    unSubscribe();

    if (item.getExtensionAttributes() != null && !item.getExtensionAttributes()
        .getStockItem()
        .getIsInStock()) {
      lyCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      tvExpiryTime.setText(itemView.getContext().getString(R.string.txt_sold_out));
      lyCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.soldOutBackground));
    } else if (item.isFlashSales()
        && item.getDealFromDate() != null
        && item.getDealToDate() != null && item.getCurrentServerTime() != null) {
      ivFav.setVisibility(View.INVISIBLE);
      lyCountDown.setVisibility(View.VISIBLE);
      ivFav.setEnabled(false);
      countDownSubscription = DateTimeCountDown.init("yyyy-MM-dd hh:mm:ss", item.getDealFromDate(),
          item.getDealToDate(), item.getCurrentServerTime())
          .subscribe(new DateTimeCountDown.CountDownSubscriber() {
            @Override
            public void onNext(DateTimeCountDown.CountDown countDown) {
              if (countDown.alreadyPast()) {
                unsubscribe();
                countDownSubscription = null;
                tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_over));
                lyCountDown.setBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), R.color.colorSaleEnded));
                tvEndIn.setVisibility(View.GONE);
              } else if (countDown.hasNotReached()) {
                lyCountDown.setBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
                tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
                tvEndIn.setVisibility(View.GONE);
              } else {
                lyCountDown.setBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), R.color.light_blue));
                tvExpiryTime.setText(
                    String.format(Locale.getDefault(), "%s %s:%s:%s", itemView.getContext()
                            .getResources()
                            .getQuantityString(R.plurals.days, countDown.day(), countDown.day()),
                        String.valueOf(countDown.hr()), countDown.min(), countDown.sec()));
                tvEndIn.setVisibility(View.VISIBLE);
              }
            }
          });

      if (hasDiscount(item.getDiscountType(), item.getDiscountPercentage())) {
        tvActualPrice.setVisibility(View.VISIBLE);
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getSpecialPrice()))));
        tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvActualPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));

        tvDiscountPercentage.setVisibility(View.VISIBLE);
        String discount = "SAVE " + item.getDiscountPercentage() + "%";
        tvDiscountPercentage.setText(discount);
      } else {
        tvDiscountPercentage.setVisibility(View.INVISIBLE);
        tvActualPrice.setVisibility(View.GONE);
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      }
    } else if (item.isWholeSale()) {
      lyCountDown.setVisibility(View.INVISIBLE);
      ivFav.setEnabled(true);
      tvDiscountPrice.setVisibility(View.VISIBLE);
      if (item.getSpecialPrice() != null && !TextUtils.isEmpty(item.getPrice())) {
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getSpecialPrice()))));

        tvActualPrice.setVisibility(View.VISIBLE);
        tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvActualPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      } else {
        tvActualPrice.setVisibility(View.GONE);
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      }

      tvDiscountPercentage.setVisibility(View.INVISIBLE);
      /*String discount = "SAVE " + item.getDiscountPercentage() + "%";
      tvDiscountPercentage.setText(discount);*/
    } else {
      lyCountDown.setVisibility(View.INVISIBLE);
      ivFav.setEnabled(true);
      tvDiscountPercentage.setVisibility(View.INVISIBLE);
      tvActualPrice.setVisibility(View.GONE);
      if (item.getPrice() != null && !TextUtils.isEmpty(item.getPrice())) {
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      }
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
            .into(ivProduct);
      }

      ivRank.setImageResource(R.drawable.ic_featured_product);
    }

    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
    ivFav.setImageResource(favIcon);

    return countDownSubscription;
  }

  private boolean hasDiscount(String discountType, String discountPercentage) {
    return discountPercentage != null && !discountPercentage.trim()
        .equals("");
  }

  private void unSubscribe() {
    if (countDownSubscription != null && !countDownSubscription.isUnsubscribed()) {
      Timber.e("un subscribe " + item.getName() + " at " + getAdapterPosition());
      countDownSubscription.unsubscribe();
    }
  }
}
