package com.skypremiuminternational.app.app.features.profile.my_favourites;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import timber.log.Timber;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.stmt.query.In;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.DateTimeCountDown;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.ExtensionAttibutes;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.Product;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.util.ProductUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;
import static com.skypremiuminternational.app.domain.util.ProductUtil.isActive;
import static com.skypremiuminternational.app.domain.util.ProductUtil.isActiveSale;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class MyFavouriteViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ll_product)
  LinearLayout ll;
  @BindView(R.id.iv_product)
  ImageView iv;
  @BindView(R.id.tvCategory)
  TextView tvCategory;
  @BindView(R.id.tvTitle_product)
  TextView tvProduct;
  @BindView(R.id.tvSubTitle_product)
  TextView tvSubCategory;
  @BindView(R.id.ivFav)
  ImageView ivFav;
  @BindView(R.id.iv_new)
  ImageView ivNewLabel;
  @BindView(R.id.tvActualPrice)
  TextView tvActualPrice;
  @BindView(R.id.tvDiscountPrice)
  TextView tvDiscountPrice;
  @BindView(R.id.tvDiscountPercentage)
  TextView tvDiscountPercentage;
  @BindView(R.id.tv_loyaltyValue)
  TextView tvLoyaltyValue;
  @BindView(R.id.tvExpiryTime)
  TextView tvExpiryTime;
  @BindView(R.id.layout_count_down)
  ViewGroup layoutCountDown;

  @BindView(R.id.tv_end_in)
  TextView tvEndIn;

  @SerializedName("extension_attributes")
  @Expose
  private ExtensionAttibutes extensionAttibutes;

  public ExtensionAttibutes getExtensionAttibutes() {
    return extensionAttibutes;
  }

  public void setExtensionAttibutes(ExtensionAttibutes extensionAttibutes) {
    this.extensionAttibutes = extensionAttibutes;
  }

  private FavouriteListResponse item;
  private Subscription countDownSubscription;

  public MyFavouriteViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public Subscription bind(final FavouriteListResponse item) {
    ViewRatioUtils.setProduct(ll, iv , itemView.getContext());
    if(item.getProduct().getSpecialFromDate()==null){
      item.getProduct().setSpecialFromDate("");
    }else if(item.getProduct().getSpecialToDate()==null){
      item.getProduct().setSpecialToDate("");
    }else if(item.getProduct().getSpecialPrice()==null){
      item.getProduct().setSpecialPrice("");
    }
    tvLoyaltyValue.setText("");
    tvDiscountPercentage.setText("");
    this.item = item;
    unSubscribe();
    ivFav.setVisibility(View.VISIBLE);
    tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    int pos = getAdapterPosition();
    if(item.getExtensionAttibutes()!=null){
      if (isActive(item.getProduct().getNewsFromDate(),item.getProduct().getNewsToDate(),item.getProduct().getCurrentServerTime())) {
        ivNewLabel.setVisibility(View.VISIBLE);
      } else {
        ivNewLabel.setVisibility(View.INVISIBLE);
      }
    }


    ivFav.setEnabled(true);


    //====== Logic count down =======
    if (!item.getExtensionAttibutes()
        .isInStock()) {
      layoutCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
    }else {
      if (item.getProduct().getDealStatus().equalsIgnoreCase("1") || item.getProduct().isFlashSales()) {
        if (!item.getExtensionAttibutes()
            .isInStock()) {
          layoutCountDown.setVisibility(View.VISIBLE);
          tvExpiryTime.setVisibility(View.VISIBLE);
          layoutCountDown.setBackgroundColor(
              ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
          tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
          tvEndIn.setVisibility(View.GONE);
        } else {
          if (item.getProduct().isFlashSales() && !item.getProduct().getDealStatus().equalsIgnoreCase("1")) {
            layoutCountDown.setVisibility(View.VISIBLE);
            tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_over));
            layoutCountDown.setBackgroundColor(
                ContextCompat.getColor(itemView.getContext(), R.color.colorSaleEnded));
            tvEndIn.setVisibility(View.GONE);
          } else {
            if (item.getProduct().getDealFromDate() != null && item.getProduct().getDealToDate() != null && item.getProduct().getCurrentServerTime() != null) {
              Resources resources = itemView.getContext().getResources();
              initLayoutCountDown(item,item.getProduct().getDealFromDate(),item.getProduct().getDealToDate(),Constants.PATTERN_CURRENT_SERVER_TIME,resources);
              countDownSubscription = DateTimeCountDown.init(Constants.PATTERN_CURRENT_SERVER_TIME, item.getProduct().getDealFromDate(),
                  item.getProduct().getDealToDate(), item.getProduct().getCurrentServerTime())
                  .subscribe(new DateTimeCountDown.CountDownSubscriber() {
                    @Override
                    public void onNext(DateTimeCountDown.CountDown countDown) {
                      if ((countDown.alreadyPast()|| !item.getProduct().getDealStatus().equalsIgnoreCase("1"))&&item.getExtensionAttibutes().isInStock()) {

                        unSubscribe();
                        countDownSubscription = null;
                        //<<START>> 20200325 - WIKI Toan Tran - fix fav show with sale ended
                        ivFav.setVisibility(View.VISIBLE);
                        ivFav.setEnabled(true);
                        //<<END>> 20200325 - WIKI Toan Tran - fix fav show with sale ended
                        tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_over));
                        layoutCountDown.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.colorSaleEnded));
                        tvEndIn.setVisibility(View.GONE);
                      } else if (countDown.hasNotReached()) {
                        layoutCountDown.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
                        tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));

                        tvEndIn.setVisibility(View.GONE);
                      } else {
                        tvEndIn.setVisibility(View.VISIBLE);
                        layoutCountDown.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.light_blue));
                        tvExpiryTime.setText(String.format(Locale.getDefault(), "%s %s:%s:%s",
                            resources
                                .getQuantityString(R.plurals.days, countDown.day(), countDown.day()),
                            String.valueOf(countDown.hr()), countDown.min(), countDown.sec()));
                      }

                    }
                  });
            } else {
              layoutCountDown.setVisibility(View.VISIBLE);
              tvExpiryTime.setVisibility(View.VISIBLE);
              layoutCountDown.setBackgroundColor(
                  ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
              tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
              tvEndIn.setVisibility(View.GONE);
            }
          }
        }
      } else {
        layoutCountDown.setVisibility(View.INVISIBLE);
      }
    }
    // IF OUT STOCK
    if (!item.getExtensionAttibutes()
        .isInStock()) {
      layoutCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
    }
    //====== Logic count down =======

    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo
    String status ="Fixed";
    if(!ProductUtil.isValid(item.getProduct().getCurrentServerTime())){
      status ="Invalid";
    }else{
      if (ProductUtil.isValid(item.getProduct().getSpecialFromDate())){
        if (ProductUtil.isValid(item.getProduct().getSpecialToDate())){
          if (!ProductUtil.isValid(item.getProduct().getSpecialPrice())){
            status = "Fixed";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getProduct().getSpecialFromDate(),item.getProduct().getCurrentServerTime())
                && !ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getProduct().getSpecialToDate(),item.getProduct().getCurrentServerTime())){
              status = "Started";
            }else{
              status = "Ended";
            }
          }
        }else{
          if (!ProductUtil.isValid(item.getProduct().getSpecialPrice())){
            status = "Disabled";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getProduct().getSpecialFromDate(),item.getProduct().getCurrentServerTime())){
              if (!ProductUtil.isValid(item.getProduct().getSpecialPrice())){
                status = "Fixed";
              }else{
                status = "Started";
              }
            }else{
              status = "Fixed";
            }
          }
        }
      }else if (!ProductUtil.isValid(item.getProduct().getSpecialToDate()) && (!ProductUtil.isValid(item.getProduct().getSpecialPrice()))){
        status = "Disabled";
      }else{
        status = "Fixed";
      }
    }

    // IF FLASH SALE AND DAILY DEAL
    if( ProductUtil.isValid(getDiscountValue(item.getProduct().getDealStatus().equalsIgnoreCase("1")))
        &&((!status.equalsIgnoreCase("Disabled")&&!status.equalsIgnoreCase("Fixed"))||isCommingsoon())){
      if(item.getProduct().isFlashSales()){
        if(ProductUtil.isValid(item.getProduct().getDealValue())){
          showDiscountPrice(item.getProduct().getPrice(),item.getProduct().getDealValue(),item.getProduct().getDealDiscountType());

        }else {
          if(ProductUtil.isValid(item.getProduct().getSpecialPrice())){
            showDiscountPrice(item.getProduct().getPrice(),item.getProduct().getSpecialPrice(),item.getProduct().getDealDiscountType());

          }else {
            showOririginalPrice(item.getProduct().getPrice());
          }
        }
      }else{
        if(ProductUtil.isValid(item.getProduct().getSpecialPrice())){
          showDiscountPrice(item.getProduct().getPrice(),getDiscountValue(item.getProduct().getDealStatus().equalsIgnoreCase("1")),item.getProduct().getDealDiscountType());

        }else {
          showOririginalPrice(item.getProduct().getPrice());
        }
      }
    }else {
      showOririginalPrice(item.getProduct().getPrice());
    }

    if(!item.getProduct().isFlashSales()&&!item.getProduct().getDealStatus().equalsIgnoreCase("1")){
      //IN DATE
      if(isActive(item.getProduct().getSpecialFromDate()
          ,item.getProduct().getSpecialToDate(),item.getProduct().getCurrentServerTime())){
        if (ProductUtil.isValid(item.getProduct().getSpecialPrice())&&ProductUtil.isValid(item.getProduct().getPrice())) {
          tvDiscountPrice.setVisibility(View.VISIBLE);
          tvActualPrice.setVisibility(View.VISIBLE);
          tvDiscountPercentage.setVisibility(View.VISIBLE);
          tvDiscountPrice.setText(
              String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getProduct().getSpecialPrice()))));
          tvActualPrice.setText(String.format("U.P. "+"S$%s", roundTwoDecimals(Double.parseDouble(item.getProduct().getPrice()))));
          double discountPercentage =
              ((Double.valueOf(item.getProduct().getPrice()) - Double.valueOf(getDiscountValue(item.getProduct().getDealStatus().equalsIgnoreCase("1"))))
                    / Double.valueOf(item.getProduct().getPrice()) * 100);
          tvDiscountPercentage.setText("SAVE " + Math.round(discountPercentage) + "%");

        }else{
          //OUT DATE
          tvActualPrice.setVisibility(View.GONE);
          tvDiscountPercentage.setVisibility(View.GONE);
          tvDiscountPrice.setVisibility(View.VISIBLE);
          tvDiscountPrice.setText(
              String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getProduct().getPrice()))));
        }
      }
    }


    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo
    //2020325 - WIKI Toan Tran - add a flag for check state
    boolean flag = false;

    if (ProductUtil.isValid(item.getProduct().getSmallImage())){
      //2020325 - WIKI Toan Tran - have a image
      flag =true;
      Timber.e(URLUtils.formatImageURL(item.getProduct().getSmallImage().toString()),
          URLUtils.formatImageURL(item.getProduct().getSmallImage().toString()));

      RequestOptions requestOptions = new RequestOptions();
      requestOptions.placeholder(R.drawable.placeholder);
      requestOptions.dontAnimate();
      requestOptions.error(R.drawable.white);
      Glide.with(itemView.getContext())
          .load(URLUtils.formatImageURL(item.getProduct().getSmallImage().toString()))
          .apply(requestOptions)
          .listener(URLUtils.getGlideListener(itemView.getContext(),item.getProduct().getSmallImage().toString(),iv))
          .into(iv);
    }
    if (ProductUtil.isValid(item.getProduct().getLoyaltyValueToEarn())) {
      tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", item.getProduct().getLoyaltyValueToEarn().toString()));
      tvLoyaltyValue.setAllCaps(false);
    }

    // <<START>>2020325 - WIKI Toan Tran - add a flag for check state
    if(!flag){
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.placeholder(R.drawable.placeholder);
      requestOptions.dontAnimate();
      requestOptions.error(R.drawable.white);
      Glide.with(itemView.getContext())
          .load(R.drawable.white)
          .apply(requestOptions)
          .into(iv);
    }
    // <<END>>2020325 - WIKI Toan Tran - Set default image

    int favIcon =
        R.drawable.ic_favorite_accent;
    ivFav.setImageResource(favIcon);

    tvProduct.setText(item.getProduct().getName());
    tvSubCategory.setText(item.getCategoryName());
    return countDownSubscription;
  }
  private  void initLayoutCountDown(FavouriteListResponse itemsItem,String fromTime,String toTime ,String pattern ,Resources resources ){
    Date currentServerTime = null;
    Date toTimeStamp = null;
    Date fromTimeStamp = null;

    String txtHr="", txtMin="", txtSec="";
    int day = 0;
    long diff=0;
    try {
      toTimeStamp = DateParser.with(Constants.PATTERN_DATE_TIME).parse(toTime);
      fromTimeStamp = DateParser.with(Constants.PATTERN_DATE_TIME).parse(fromTime);
      currentServerTime = DateParser.with(pattern).parse(itemsItem.getProduct().getCurrentServerTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    diff = toTimeStamp.getTime() - currentServerTime.getTime();


    final boolean alreadyPast = diff < 0;
    final boolean hasNotReached = currentServerTime.before(fromTimeStamp);

    int favIcon = R.drawable.ic_favorite_accent ;
    ivFav.setImageResource(favIcon);
    if ((alreadyPast)) {
      layoutCountDown.setVisibility(View.VISIBLE);
      unSubscribe();
      countDownSubscription = null;
      //<<START>> 20200325 - WIKI Toan Tran - fix fav show with sale ended
      ivFav.setVisibility(View.VISIBLE);
      ivFav.setEnabled(true);
      //<<END>> 20200325 - WIKI Toan Tran - fix fav show with sale ended
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_over));
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleEnded));
      tvEndIn.setVisibility(View.GONE);

    } else if (hasNotReached) {
      layoutCountDown.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);

    } else {
      //=====
      long seconds = Math.abs(diff) / 1000;

      day = (int) TimeUnit.SECONDS.toDays(seconds);

      int hr = (int) TimeUnit.SECONDS.toHours(seconds) - day * 24;
      txtHr = hr > 9 ? String.valueOf(hr) : "0" + hr;
      int min =
          (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)
              * 60));
      txtMin = min > 9 ? String.valueOf(min) : "0" + min;
      int sec =
          (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds)
              * 60));

      txtSec = sec > 9 ? String.valueOf(sec) : "0" + sec;
      //=====
      layoutCountDown.setVisibility(View.VISIBLE);
      tvEndIn.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.light_blue));
      tvExpiryTime.setText(String.format(Locale.getDefault(), "%s %s:%s:%s",
          resources
              .getQuantityString(R.plurals.days, day, day),
          String.valueOf(txtHr), txtMin, txtSec));

    }
  }
  private String roundDecimal(String price) {
    if (price != null && !TextUtils.isEmpty(price)) {
      return new DecimalFormat("###,###,###,###.00").format(Double.parseDouble(price));
    } else {
      return "0.00";
    }
  }
  private String getDiscountValue(boolean dealStatus ){
    if(dealStatus){
      return item.getProduct().getDealValue();
    }else {
      return item.getProduct().getSpecialPrice();
    }
  }

  private boolean hasDiscount(String discountType, String discountPercentage) {
    return discountPercentage != null && !discountPercentage.trim()
        .equals("");
  }

  private void unSubscribe() {
    if (countDownSubscription != null && !countDownSubscription.isUnsubscribed()) {
      Timber.e("un subscribe " + item.getProduct().getName() + " at " + getAdapterPosition());
      countDownSubscription.unsubscribe();
    }
  }
  void showDiscountPrice(String price, String discountValue,String discountType){
    tvActualPrice.setVisibility(View.VISIBLE);
    tvDiscountPercentage.setVisibility(View.VISIBLE);
    tvDiscountPrice.setVisibility(View.VISIBLE);
    double discount = 0;
    double original = 0;

    //calcular price
    if(discountType.equals(Constants.DISCOUNT_TYPE_PERCENT)){

      original = Double.parseDouble(price.trim());

      try {
        discount  = original -  (((Double.valueOf(price.trim())/100) * (100-Double.valueOf(discountValue.trim()))));



        tvDiscountPercentage.setText("SAVE " + Math.round(100 - Double.parseDouble(discountValue)) + "%");
      } catch (NullPointerException ex) {
        tvDiscountPercentage.setVisibility(View.INVISIBLE);
      }

    }else {
      try {
        double discountPercentage =
            ((Double.valueOf(price.trim()) - Double.valueOf(discountValue.trim()))
                / Double.valueOf(price.trim()) * 100);
        tvDiscountPercentage.setText("SAVE " + Math.round(discountPercentage) + "%");
      } catch (NullPointerException ex) {
        tvDiscountPercentage.setVisibility(View.INVISIBLE);
      }
      discount = Double.parseDouble(discountValue.trim());
      original = Double.parseDouble(price.trim());
    }


    tvDiscountPrice.setText(String.format("S$%s", roundTwoDecimals(discount)));
    tvActualPrice.setText(String.format("U.P. "+"S$%s", roundTwoDecimals(original)));
  }


  void showOririginalPrice(String price){
    tvActualPrice.setVisibility(View.GONE);
    tvDiscountPercentage.setVisibility(View.GONE);
    tvDiscountPrice.setVisibility(View.VISIBLE);
    tvDiscountPrice.setText(String.format("S$%s", roundTwoDecimals(Double.parseDouble(price))));
  }

  boolean isCommingsoon(){
    return tvExpiryTime.getText().toString().equalsIgnoreCase(itemView.getContext().getString(R.string.sale_not_started));
  }
}
