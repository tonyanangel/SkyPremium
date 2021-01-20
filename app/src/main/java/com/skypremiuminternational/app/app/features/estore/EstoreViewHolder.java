package com.skypremiuminternational.app.app.features.estore;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.app.utils.RatingUtils;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.util.ProductUtil;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import timber.log.Timber;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

/**
 * Created by codigo on 4/2/18.
 */

public class EstoreViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ll_product)
  ViewGroup baseLayout;
  @BindView(R.id.iv_product)
  ImageView ivProduct;
  @BindView(R.id.layout_count_down)
  ViewGroup layoutCountDown;
  @BindView(R.id.tvExpiryTime)
  TextView tvExpiryTime;
  @BindView(R.id.tvCategory)
  TextView tvCategory;
  @BindView(R.id.tvTitle_product)
  TextView tvTitle;
  @BindView(R.id.tvSubTitle_product)
  TextView tvSubTitle;
  @BindView(R.id.tvActualPrice)
  TextView tvActualPrice;
  @BindView(R.id.tvDiscountPrice)
  TextView tvDiscountPrice;
  @BindView(R.id.tvDiscountPercentage)
  TextView tvDiscountPercentage;
  @BindView(R.id.ivFav)
  ImageView ivFav;
  @BindView(R.id.tv_end_in)
  TextView tvEndIn;
  @BindView(R.id.iv_new)
  ImageView ivNewLabel;
  @BindView(R.id.tv_loyaltyValue)
  TextView tvLoyaltyValue;
  @BindView(R.id.tvNoRating)
  TextView tvNoRating;
  @BindView(R.id.rtProductStar)
  ScaleRatingBar rtProductStar;
  @BindView(R.id.tvAddToCart)
  TextView tvAddToCart;
  @BindView(R.id.tvBuyNow)
  TextView tvBuyNow;
  private ItemsItem item;
  private Subscription countDownSubscription;
  public List<ItemsItem> itemsItems = new ArrayList<>();

  private  IOnClickAddToCart onClickItemListener;
  private  IOnClickBuyNow onClickBuyNowListener;

  private String selectDisplayCatogry = "";

  public EstoreViewHolder(View itemView , List<ItemsItem> list,IOnClickAddToCart onClickItemListener,IOnClickBuyNow onClickBuyNowListener) {
    super(itemView);
    this.itemsItems = list;
    ButterKnife.bind(this, itemView);
    this.onClickItemListener  = onClickItemListener;
    this.onClickBuyNowListener  = onClickBuyNowListener;

  }

  public Subscription bind(final ItemsItem item) {

    float rate = item.getExtensionAttributes().summaryRating;
    float iRate = RatingUtils.getRatingRound(rate);
    float fRate = RatingUtils.getRating(rate);
    if(item.getExtensionAttributes().summaryRating>0){
      rtProductStar.setRating(fRate);
      rtProductStar.setVisibility(View.VISIBLE);
      tvNoRating.setVisibility(View.GONE);
    }
    else{
      rtProductStar.setVisibility(View.GONE);
      tvNoRating.setVisibility(View.VISIBLE);
      tvNoRating.setTypeface(null, Typeface.ITALIC);
    }


    tvAddToCart.setVisibility(View.VISIBLE);
    tvBuyNow.setVisibility(View.VISIBLE);
    setEnableButton(true);

    ViewRatioUtils.setProduct(baseLayout, ivProduct, itemView.getContext());
    tvLoyaltyValue.setText("");
    tvDiscountPercentage.setText("");
    this.item = item;
    unSubscribe();
    ivFav.setVisibility(View.VISIBLE);
    ivFav.setEnabled(true);
    int pos = getAdapterPosition();
    tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    if (item.isNew()) {
      ivNewLabel.setVisibility(View.VISIBLE);
    } else {
      ivNewLabel.setVisibility(View.INVISIBLE);
    }


    //====== Logic count down =======
    if (!item.getExtensionAttributes()
        .getStockItem()
        .getIsInStock()) {
      layoutCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
      setEnableButton(false);
    }else {
      if (item.getDealStatus() || item.isFlashSales()) {
        if (!item.getExtensionAttributes()
            .getStockItem()
            .getIsInStock()) {
          layoutCountDown.setVisibility(View.VISIBLE);
          tvExpiryTime.setVisibility(View.VISIBLE);
          layoutCountDown.setBackgroundColor(
              ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
          tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
          tvEndIn.setVisibility(View.GONE);
          setEnableButton(false);
        } else {
          if (item.isFlashSales() && !item.getDealStatus()) {
            layoutCountDown.setVisibility(View.VISIBLE);
            tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_over));
            layoutCountDown.setBackgroundColor(
                ContextCompat.getColor(itemView.getContext(), R.color.colorSaleEnded));
            tvEndIn.setVisibility(View.GONE);
            setEnableButton(false);
          } else {
            if (item.getDealFromDate() != null && item.getDealToDate() != null && item.getCurrentServerTime() != null) {
              Resources resources = itemView.getContext().getResources();
              initLayoutCountDown(item,item.getDealFromDate(),item.getDealToDate(),Constants.PATTERN_CURRENT_SERVER_TIME,resources);
              countDownSubscription = DateTimeCountDown.init(Constants.PATTERN_CURRENT_SERVER_TIME, item.getDealFromDate(),
                  item.getDealToDate(), item.getCurrentServerTime())
                  .subscribe(new DateTimeCountDown.CountDownSubscriber() {
                    @Override
                    public void onNext(DateTimeCountDown.CountDown countDown) {

                      int favIcon =
                          item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
                      ivFav.setImageResource(favIcon);
                      if ((countDown.alreadyPast())) {
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
                        setEnableButton(false);
                      } else if (countDown.hasNotReached()) {
                        layoutCountDown.setVisibility(View.VISIBLE);
                        layoutCountDown.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
                        tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
                        tvEndIn.setVisibility(View.GONE);
                        setEnableButton(false);
                      } else {
                        layoutCountDown.setVisibility(View.VISIBLE);
                        tvEndIn.setVisibility(View.VISIBLE);
                        layoutCountDown.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.light_blue));
                        tvExpiryTime.setText(String.format(Locale.getDefault(), "%s %s:%s:%s",
                            resources
                                .getQuantityString(R.plurals.days, countDown.day(), countDown.day()),
                            String.valueOf(countDown.hr()), countDown.min(), countDown.sec()));
                        setEnableButton(true);
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
              setEnableButton(false);
            }
          }
        }
      } else {
        layoutCountDown.setVisibility(View.INVISIBLE);
      }
    }
    // ÌF OUT STOCK
    if (!item.getExtensionAttributes()
        .getStockItem()
        .getIsInStock()) {
      layoutCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
      setEnableButton(false);
    }

    //====== Logic count down =======

    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo
    String status ="Fixed";
    if(!ProductUtil.isValid(item.getCurrentServerTime())){
      status ="Invalid";
    }else{
      if (ProductUtil.isValid(item.getSpecialFromDate())){
        if (ProductUtil.isValid(item.getSpecialToDate())){
          if (!ProductUtil.isValid(item.getSpecialPrice())){
            status = "Fixed";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getSpecialFromDate(),item.getCurrentServerTime())
                && !ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getSpecialToDate(),item.getCurrentServerTime())){
              status = "Started";
            }else{
              status = "Ended";
            }
          }
        }else{
          if (!ProductUtil.isValid(item.getSpecialPrice())){
            status = "Disabled";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_CURRENT_SERVER_TIME,item.getSpecialFromDate(),item.getCurrentServerTime())){
              if (!ProductUtil.isValid(item.getSpecialPrice())){
                status = "Fixed";
              }else{
                status = "Started";
              }
            }else{
              status = "Fixed";
            }
          }
        }
      }else if (!ProductUtil.isValid(item.getSpecialToDate()) && (!ProductUtil.isValid(item.getSpecialPrice()))){
        status = "Disabled";
      }else{
        status = "Fixed";
      }
    }
    // IF FLASH SALE AND DAILY DEAL
    if(ProductUtil.isValid(getDiscountValue(item.getDealStatus()))&&((!status.equalsIgnoreCase("Disabled")&&!status.equalsIgnoreCase("Fixed"))||isCommingsoon())){
      if(item.isFlashSales()){
        if(ProductUtil.isValid(item.getDealValue())){
          showDiscountPrice(item.getPrice(),item.getDealValue(),item.getDiscountType());
        }else {
          if(ProductUtil.isValid(item.getSpecialPrice())){
            showDiscountPrice(item.getPrice(),item.getSpecialPrice(),item.getDiscountType());
          }else {
            showOririginalPrice(item.getPrice());
          }
        }
      }else{
        if(ProductUtil.isValid(item.getSpecialPrice())){
          showDiscountPrice(item.getPrice(),getDiscountValue(item.getDealStatus()),item.getDiscountType());
        }else {
          showOririginalPrice(item.getPrice());
        }
      }
    }else {
      showOririginalPrice(item.getPrice());
    }
    // IF NORMAL SALE
    if(!item.isFlashSales()&&!item.getDealStatus()){
      if(item.isWholeSale()){
        //IN DATE
        if (ProductUtil.isValid(item.getSpecialPrice())&&ProductUtil.isValid(item.getPrice())) {
          tvDiscountPrice.setVisibility(View.VISIBLE);
          tvActualPrice.setVisibility(View.VISIBLE);
          tvDiscountPercentage.setVisibility(View.VISIBLE);

          tvDiscountPrice.setText(
              String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getSpecialPrice()))));
          tvActualPrice.setText(String.format("U.P. "+"S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
          double discountPercentage =
              ((Double.valueOf(item.getPrice()) - Double.valueOf(getDiscountValue(item.getDealStatus())))
                    / Double.valueOf(item.getPrice()) * 100);
          tvDiscountPercentage.setText("SAVE " + Math.round(discountPercentage) + "%");

        }else{
          tvActualPrice.setVisibility(View.GONE);
          tvDiscountPercentage.setVisibility(View.GONE);
          tvDiscountPrice.setVisibility(View.VISIBLE);
          tvDiscountPrice.setText(
              String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
        }
      }else {
        // OUT DATE
        tvActualPrice.setVisibility(View.GONE);
        tvDiscountPercentage.setVisibility(View.GONE);
        tvDiscountPrice.setVisibility(View.VISIBLE);
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      }
    }
    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo



    //2020325 - WIKI Toan Tran - add a flag for check state
    boolean flag = false;
    for (CustomAttributesItem customAttributesItem : item.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("small_image")) {
        //2020325 - WIKI Toan Tran - have a image
        flag =true;
        Timber.e(URLUtils.formatImageURL(customAttributesItem.getValue().toString()),
            URLUtils.formatImageURL(customAttributesItem.getValue().toString()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.dontAnimate();
        requestOptions.error(R.drawable.white);
        Glide.with(itemView.getContext())
            .load(URLUtils.formatImageURL(customAttributesItem.getValue().toString()))
            .apply(requestOptions)
            .listener(URLUtils.getGlideListener(itemView.getContext(),customAttributesItem.getValue().toString(),ivProduct))
            .into(ivProduct);
      }
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("loyalty_value_to_earn")) {
        tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", customAttributesItem.getValue().toString()));
        tvLoyaltyValue.setAllCaps(false);
      }
    }
    // <<START>>2020325 - WIKI Toan Tran - add a flag for check state
    if(!flag){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.white);
        Glide.with(itemView.getContext())
                .load(R.drawable.white)
                .apply(requestOptions)
                .into(ivProduct);
    }
    // <<END>>2020325 - WIKI Toan Tran - Set default image

    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
    ivFav.setImageResource(favIcon);

    tvTitle.setText(item.getName());

    for (CustomAttributesItem attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("select_display_category")) {
        selectDisplayCatogry =  attributesItem.getValue().toString();
      }
    }
    if(!selectDisplayCatogry.equalsIgnoreCase(""))
      tvSubTitle.setText(selectDisplayCatogry);
    else
      tvSubTitle.setText(item.getCategoryName());

    tvAddToCart.setOnClickListener(v -> {
      onClickItemListener.addToCart(getAdapterPosition());
    });
    tvBuyNow.setOnClickListener(v -> {
      onClickBuyNowListener.buyNow(getAdapterPosition());
    });
    if(tvAddToCart.getVisibility()==View.GONE){
      tvTitle.setTextColor(Color.RED);
    }



    return countDownSubscription;
  }
  private  void initLayoutCountDown(ItemsItem itemsItem,String fromTime,String toTime ,String pattern ,Resources resources ){
    Date currentServerTime = null;
    Date toTimeStamp = null;
    Date fromTimeStamp = null;

    String txtHr="", txtMin="", txtSec="";
    int day = 0;
    long diff=0;
    try {
      toTimeStamp = DateParser.with(Constants.PATTERN_DATE_TIME).parse(toTime);
      fromTimeStamp = DateParser.with(Constants.PATTERN_DATE_TIME).parse(fromTime);
      currentServerTime = DateParser.with(pattern).parse(itemsItem.getCurrentServerTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    diff = toTimeStamp.getTime() - currentServerTime.getTime();


    final boolean alreadyPast = diff < 0;
    final boolean hasNotReached = currentServerTime.before(fromTimeStamp);

    int favIcon =
        item.isFavourite() ? R.drawable.ic_favorite_accent : R.drawable.ic_favourite_border_accent;
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
      setEnableButton(false);
    } else if (hasNotReached) {
      layoutCountDown.setVisibility(View.VISIBLE);
      layoutCountDown.setBackgroundColor(
          ContextCompat.getColor(itemView.getContext(), R.color.colorSaleNotStarted));
      tvExpiryTime.setText(itemView.getContext().getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
      setEnableButton(false);
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
      setEnableButton(true);
    }
  }

  private  void setEnableButton(boolean isEnable){
    if(isEnable){
      tvAddToCart.setEnabled(isEnable);
      tvBuyNow.setEnabled(isEnable);
      tvAddToCart.setBackgroundColor(tvAddToCart.getContext().getResources().getColor(R.color.colorAddToCart));
      tvBuyNow.setBackgroundColor(tvAddToCart.getContext().getResources().getColor(R.color.yellowDark));
    }else {
      tvAddToCart.setEnabled(isEnable);
      tvBuyNow.setEnabled(isEnable);
      tvAddToCart.setBackgroundColor(tvAddToCart.getContext().getResources().getColor(R.color.colorAddToCartLightDisabled));
      tvBuyNow.setBackgroundColor(tvAddToCart.getContext().getResources().getColor(R.color.colorYellowDarkDisabled));
    }
  }

  private String getDiscountValue(boolean dealStatus ){
    if(dealStatus){
      return item.getDealValue();
    }else {
      return item.getSpecialPrice();
    }
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
    tvActualPrice.setVisibility(View.INVISIBLE);
    tvDiscountPercentage.setVisibility(View.INVISIBLE);
    tvDiscountPrice.setVisibility(View.VISIBLE);
    tvDiscountPrice.setText(String.format("S$%s", roundTwoDecimals(Double.parseDouble(price))));
  }

  boolean isCommingsoon(){
    return tvExpiryTime.getText().toString().equalsIgnoreCase(itemView.getContext().getString(R.string.sale_not_started));
  }


}
