package com.skypremiuminternational.app.app.features.estore.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.DateTimeCountDown;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.IOnClickAddToCart;
import com.skypremiuminternational.app.app.features.estore.IOnClickBuyNow;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.CommentAdapter;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.CommentPageAdapter;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.ImagePagerAdapter;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.RecommendationAdapter;
import com.skypremiuminternational.app.app.features.estore.filter.adapter.IOnClickItemFilterAdapter;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.model.CommentPage;
import com.skypremiuminternational.app.app.utils.ActivityChooser;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.RatingUtils;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.view.ExpandableWebView;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.app.view.WebTextView;
import com.skypremiuminternational.app.data.network.request.ProductReviewRequest;
import com.skypremiuminternational.app.data.network.service.DetailsService;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.util.ProductUtil;
import com.willy.ratingbar.ScaleRatingBar;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import dagger.android.AndroidInjection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscription;
import timber.log.Timber;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

/**
 * Created by wmw on 2/6/2018.
 */

public class EstoreDetailActivity extends BaseActivity<EstoreDetailPresenter>
        implements EstoreDetailView<EstoreDetailPresenter>, OnMapReadyCallback,PageCallback,  IOnClickAddToCart, IOnClickBuyNow {

  private static String TAG = EstoreDetailActivity.class.getSimpleName();

  @BindView(R.id.tvExcerpt)
  ExpandableTextView tvExcerpt;
  @BindView(R.id.tv_discount_percentage)
  TextView tvDiscountPercentage;
  @BindView(R.id.tv_actual_price)
  TextView tvActualPrice;
  @BindView(R.id.tv_discount_price)
  TextView tvDiscountPrice;
  @BindView(R.id.tv_loyaltyValue)
  TextView tvLoyaltyValue;
  @BindView(R.id.iv_new)
  ImageView ivNew;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.ly_cart_count_white)
  FrameLayout lyCartCountWhite;
  @BindView(R.id.tv_cart_count_white)
  TextView tvCartCountWhite;
  @BindView(R.id.ivFav)
  ImageView imgFav;
  @BindView(R.id.iv_map)
  ImageView ivMap;
  @BindView(R.id.tvAddress)
  TextView tvAddress;
  @BindView(R.id.vStatusBar)
  View vStatusBar;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  @BindView(R.id.toolbar_white)
  LinearLayout toolbar_white;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.nsv)
  NestedScrollView nsv;
  @BindView(R.id.iv_cart)
  ImageView ivCart;
  @BindView(R.id.llImageHolder)
  LinearLayout llImageHolder;
  @BindView(R.id.vImage)
  View vImage;
  @BindView(R.id.vpg)
  ViewPager vpg;
  @BindView(R.id.fpi)
  FlycoPageIndicaor fpi;
  @BindView(R.id.tvCategory)
  TextView tvCategory;
  @BindView(R.id.tvName)
  TextView tvName;
  @BindView(R.id.llBenefits)
  LinearLayout llBenefits;
  @BindView(R.id.tvBenefits)
  WebTextView tvBenefits;
  @BindView(R.id.llDescription)
  LinearLayout llDescription;
  @BindView(R.id.ll_Comment)
  LinearLayout llComment;
  @BindView(R.id.etvDescription)
  //MediumTextView etvDescription;
          HtmlTextView etvDescription;
  @BindView(R.id.tvToogleDescription)
  TextView tvToogleDescription;
  @BindView(R.id.llTerms)
  LinearLayout llTerms;
  @BindView(R.id.etvTerms)
  ExpandableWebView etvTerms;
  @BindView(R.id.tvToogleTerms)
  TextView tvToogleTerms;
  @BindView(R.id.rv_recommendation)
  RecyclerView rvRecommendation;
  @BindView(R.id.layout_count_down)
  LinearLayout lyCountDown;
  @BindView(R.id.tv_end_in)
  TextView tvEndIn;
  @BindView(R.id.tv_expiry_time)
  TextView tvExpiryTime;
  @BindView(R.id.tv_checkout)
  TextView tvCheckout;
  @BindView(R.id.edit_qty)
  TextView edt_qty;
  @BindView(R.id.tvNotice)
  TextView tvNotice;
  @BindView(R.id.tvNoticeshort_description)
  HtmlTextView tvNoticeshort_description;
  @BindView(R.id.viewnotice)
  View viewnotice;
  @BindView(R.id.rvComment)
  RecyclerView rvComment;

  @BindView(R.id.llMarker)
  LinearLayout llMarker;
  @BindView(R.id.llNotice)
  LinearLayout llNotice;
  @BindView(R.id.layout_map)
  RelativeLayout layoutMap;
  @BindView(R.id.ll_comment_page)
  LinearLayout llCommentPage;
  @BindView(R.id.rvCommentPage)
  RecyclerView rvCommentPage;
  @BindView(R.id.llSort)
  LinearLayout llSort;
  @BindView(R.id.tvSort)
  TextView tvSort;
  @BindView(R.id.rtProductStar)
  ScaleRatingBar rtProductStar;
  @BindView(R.id.rtAvgStar)
  ScaleRatingBar rtAvgStar;
  @BindView(R.id.tvAvgRatingPoint)
  TextView tvAvgRatingPoint;
  @BindView(R.id.tvNoRating)
  TextView tvNoRating;
  @BindView(R.id.tvNoRatingTop)
  TextView tvNoRatingTop;
  @BindView(R.id.tv_product_review)
  TextView tvProductReview;
  @BindView(R.id.rlContent)
  RelativeLayout rlContent;
  @BindView(R.id.llTopComment)
  LinearLayout llTopComment;
  @BindView(R.id.llAddToCart)
  LinearLayout llAddToCart;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  String category = "";
  String from = "";
  String skuFromNotification = "";
  public static String category_id = "";
  public static String product_name = "";
  public static int MAX_PAGE = 5;
  public static int MAX_ITEM_PAGE = 5;
  public static int COUNT_PAGE = 0;
  public static int qty = 0;
  String urlbefor = "";
  String urlafter = "";
  private DetailsItem detailsItem;
  ItemsItem itemsItem;
  List<String> imgList = new ArrayList<>();
  List<ItemsItem> reloaditem = new ArrayList<>();
  List<CommentPage> listPage;
  int width, height;
  double longitude;
  double latitude;
  String address;
  private ProgressDialog progressDialog;
  private RecommendationAdapter recommendationAdapter;
  private Subscription countDownSubscription;
  private GoogleMap mMap;
  private static final int R_C_PRODUCT_DETAIL = 1234;
  private ItemsItem clickedItem;
  private String selectDisplayCatogry = "";
  CommentAdapter commentAdapter ;
  CommentPageAdapter commentPageAdapter ;
  ProductReviewRequest request;
  ProductReviewResponse reviewResponse;
  public static void startMe(Activity activity, ItemsItem itemsItem, String category,
                             int requestCode) {
    Intent intent = new Intent(activity, EstoreDetailActivity.class);
    intent.putExtra("Category", category);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    activity.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Fragment fragment, ItemsItem itemsItem, String category,
                             int requestCode) {
    Intent intent = new Intent(fragment.getContext(), EstoreDetailActivity.class);
    intent.putExtra("Category", category);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    fragment.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Activity activity, DetailsItem itemsItem, String category,
                             int requestCode) {
    Intent intent = new Intent(activity, EstoreDetailActivity.class);
    intent.putExtra("Category", category);
    intent.putExtra("fav_product", new Gson().toJson(itemsItem));
    activity.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Context context,String from,String sku) {

    Intent intent = new Intent(context, EstoreDetailActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("from", from);
    intent.putExtra("sku", sku);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_estore_detail);
    ButterKnife.bind(this);

    toolbar.setVisibility(View.VISIBLE);
    /*toolbar.setBackground(
        ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_transparent_toolbar));*/
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading");
    tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    detailsItem = new Gson().fromJson(getIntent().getStringExtra("fav_product"), DetailsItem.class);
    category = getIntent().getStringExtra("Category");
    from = getIntent().getStringExtra("from");
    skuFromNotification = getIntent().getStringExtra("sku");
    itemsItem = new Gson().fromJson(getIntent().getStringExtra("Product"), ItemsItem.class);
    if(category!=null){
      setupCategoryId();
    }
    setupRecyclerView();



    if (detailsItem != null) {

      setFavStatus();
      setNewStatus(detailsItem.isNew());
      tvTitle_toolbar.setText(detailsItem.getName());

      tvName.setText(detailsItem.getName());

      for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase("description")
                && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llDescription.setVisibility(View.VISIBLE);
          urlbefor = customAttribute.getValue().toString().replace("<img src=\"{{media url=\"","<img  src=\""+ BuildConfig.BASE_IMG_URL+ "/media/");
          urlafter = urlbefor.replace(".png\"}}\"",".png\"");
          etvDescription.setHtml(urlafter, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
              LevelListDrawable d = new LevelListDrawable();
              Drawable empty = getResources().getDrawable(R.drawable.ic_imgfail);
              d.addLevel(0, 0, empty);
              d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

              new LoadImage().execute(source, d);

              return d;
            }
          });
          tvExcerpt.setText(
                  Html.fromHtml(customAttribute.getValue().toString()).toString().trim());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_terms_conditions")
                && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llTerms.setVisibility(View.VISIBLE);
          etvTerms.setText(customAttribute.getValue().toString());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_benefits")
                && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llBenefits.setVisibility(View.VISIBLE);
          tvBenefits.setText(
                  Html.fromHtml(customAttribute.getValue().toString()).toString().trim());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(customAttribute.getValue().toString());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("product_notice")
            && !TextUtils.isEmpty(customAttribute.getValue().toString()) && !customAttribute.getValue().toString().equals(" ")) {
          if(customAttribute.getValue().toString().trim().length() == 0){
            tvNotice.setVisibility(View.GONE);
          }else {
            llNotice.setVisibility(View.VISIBLE);
            tvNotice.setVisibility(View.VISIBLE);
            viewnotice.setVisibility(View.VISIBLE);
            tvNotice.setText(customAttribute.getValue().toString());
          }
        }else if (customAttribute.getAttributeCode().equalsIgnoreCase("short_description")
                && !TextUtils.isEmpty(customAttribute.getValue().toString()) && !customAttribute.getValue().toString().equals(" ") ) {
          tvNoticeshort_description.setVisibility(View.VISIBLE);
          tvNoticeshort_description.setHtml(customAttribute.getValue().toString(), new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
              LevelListDrawable d = new LevelListDrawable();
              Drawable empty = getResources().getDrawable(R.mipmap.ic_imfailed);
              d.addLevel(0, 0, empty);
              d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

              new LoadImage().execute(source, d);

              return d;
            }
          });
        }else if (customAttribute.getAttributeCode().equalsIgnoreCase("loyalty_value_to_earn")) {
          tvLoyaltyValue.setVisibility(View.VISIBLE);
          tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", customAttribute.getValue().toString()));
          tvLoyaltyValue.setAllCaps(false);
        }else if(customAttribute.getAttributeCode().equalsIgnoreCase("select_display_category")){
          selectDisplayCatogry = customAttribute.getValue().toString();
        }

      }
      if(!selectDisplayCatogry.equalsIgnoreCase(""))
        tvCategory.setText(selectDisplayCatogry);
      else
        tvCategory.setText(category);


      if (imgList.size() > 0) {
        imgList.clear();
        for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
          imgList.add(
                  detailsItem.getMediaGalleryEntries().get(i).getFile());
        }
        vpg.setAdapter(
                new ImagePagerAdapter(this,
                        getSupportFragmentManager(), imgList));
        fpi.setViewPager(vpg);
        if (imgList.size() == 1) {
          fpi.setVisibility(View.INVISIBLE);
        }
      }

      renderProductDetail(detailsItem);
      checkMapInfos(detailsItem);
      presenter.getCategory(detailsItem.getSku(),getCategoryIdByCA(detailsItem.getCustomAttributes()));
      presenter.getCategoryName(detailsItem);
    } else if (itemsItem != null) {
      setNewStatus(itemsItem.isNew());
      setFavStatus();
      tvTitle_toolbar.setText(itemsItem.getName());
      tvCategory.setText(category);
      tvName.setText(itemsItem.getName());

      for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llDescription.setVisibility(View.VISIBLE);
            urlbefor = customAttributesItem.getValue().toString().replace("<img src=\"{{media url=\"","<img  src=\""+ BuildConfig.BASE_IMG_URL+ "/media/");
            urlafter = urlbefor.replace(".png\"}}\"",".png\"");
            etvDescription.setHtml(urlafter, new Html.ImageGetter() {
              @Override
              public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(R.mipmap.ic_imfailed);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                new LoadImage().execute(source, d);

                return d;
              }
            });
            tvExcerpt.setText(
                    Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());
          }
        } else if (customAttributesItem.getAttributeCode()
                .equalsIgnoreCase("partners_terms_conditions")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llTerms.setVisibility(View.VISIBLE);
            etvTerms.setText(customAttributesItem.getValue().toString());
          }
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("partners_benefits")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llBenefits.setVisibility(View.VISIBLE);
            tvBenefits.setText(
                    Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());
          }
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(customAttributesItem.getValue().toString());
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("loyalty_value_to_earn")) {
          tvLoyaltyValue.setVisibility(View.VISIBLE);
          tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", customAttributesItem.getValue().toString()));
          tvLoyaltyValue.setAllCaps(false);
        }else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("product_notice")
                && !TextUtils.isEmpty(customAttributesItem.getValue().toString()) && !customAttributesItem.getValue().toString().equals(" ")) {
          if(customAttributesItem.getValue().toString().trim().length() == 0){
            tvNotice.setVisibility(View.GONE);
          }else {
            llNotice.setVisibility(View.VISIBLE);
            tvNotice.setVisibility(View.VISIBLE);
            viewnotice.setVisibility(View.VISIBLE);
            tvNotice.setText(customAttributesItem.getValue().toString());
          }
        }else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("short_description")
                && !TextUtils.isEmpty(customAttributesItem.getValue().toString()) && !customAttributesItem.getValue().toString().equals(" ") ) {

          if(customAttributesItem.getValue().toString().trim().length() == 0){
            tvNoticeshort_description.setVisibility(View.GONE);
          }else {
            llNotice.setVisibility(View.VISIBLE);
            viewnotice.setVisibility(View.VISIBLE);
            tvNoticeshort_description.setVisibility(View.VISIBLE);
          }
          tvNoticeshort_description.setHtml(customAttributesItem.getValue().toString(), new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
              LevelListDrawable d = new LevelListDrawable();
              Drawable empty = getResources().getDrawable(R.mipmap.ic_imfailed);
              d.addLevel(0, 0, empty);
              d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

              new LoadImage().execute(source, d);

              return d;
            }
          });
        }
      }

      vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
      fpi.setViewPager(vpg);
      presenter.getDetails(itemsItem.getSku());
    }else if(from.equalsIgnoreCase("notification")) {
      presenter.getCategorySimple(skuFromNotification);
      //presenter.getCategoryName(itemsItem);
    }
    presenter.getCartCount();
    calculateDisplay();
    llImageHolder.getLayoutParams().height = height;
    final int min_height =
            ((width / 16) * 9) + MetricsUtils.convertDpToPixel(112, getApplicationContext());
    final int max_height = height - min_height;

    nsv.setOnScrollChangeListener(
            (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

              //RxBus.get().post(new ImageReloadEvent());

              if (scrollY < max_height) {
                vImage.getLayoutParams().height = scrollY;
                vpg.requestLayout();
              }

              if (scrollY > (height - MetricsUtils.convertDpToPixel(112, getApplicationContext()))) {
                if (toolbar_white.getVisibility() != View.VISIBLE) {
                  vStatusBar.setVisibility(View.VISIBLE);
                  toolbar_white.setVisibility(View.VISIBLE);
                  toolbar.setVisibility(View.GONE);
                  setFavStatus();
                }
              } else {
                if (toolbar.getVisibility() != View.VISIBLE) {
                  vStatusBar.setVisibility(View.GONE);
                  toolbar_white.setVisibility(View.GONE);
                  toolbar.setVisibility(View.VISIBLE);
                  setFavStatus();
                }
              }
            });
    etvTerms.collapse();

  }

  @Override
  public void setContentEstoreDetail(DetailsItem itemDetail){
    category = itemDetail.getCategoryName();
    setupCategoryId();
    setNewStatus(itemDetail.isNew());
    setFavStatus();
    tvTitle_toolbar.setText(itemDetail.getName());
    tvCategory.setText(category);
    tvName.setText(itemDetail.getName());

    for (CustomAttribute customAttributesItem : itemDetail.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
        if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
          llDescription.setVisibility(View.VISIBLE);
          urlbefor = customAttributesItem.getValue().toString().replace("<img src=\"{{media url=\"","<img  src=\""+ BuildConfig.BASE_IMG_URL+ "/media/");
          urlafter = urlbefor.replace(".png\"}}\"",".png\"");
          etvDescription.setHtml(urlafter, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
              LevelListDrawable d = new LevelListDrawable();
              Drawable empty = getResources().getDrawable(R.mipmap.ic_imfailed);
              d.addLevel(0, 0, empty);
              d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

              new LoadImage().execute(source, d);

              return d;
            }
          });
          tvExcerpt.setText(
              Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());
        }
      } else if (customAttributesItem.getAttributeCode()
          .equalsIgnoreCase("partners_terms_conditions")) {
        if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
          llTerms.setVisibility(View.VISIBLE);
          etvTerms.setText(customAttributesItem.getValue().toString());
        }
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("partners_benefits")) {
        if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
          llBenefits.setVisibility(View.VISIBLE);
          tvBenefits.setText(
              Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());
        }
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
        imgList.add(customAttributesItem.getValue().toString());
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("loyalty_value_to_earn")) {
        Log.d("loyalty_value_to_earn",""+customAttributesItem.getValue().toString());
        tvLoyaltyValue.setVisibility(View.VISIBLE);
        tvLoyaltyValue.setText(String.format("Earn %s.00 Sky Dollar", customAttributesItem.getValue().toString()));
        tvLoyaltyValue.setAllCaps(false);
      }else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("product_notice")
          && !TextUtils.isEmpty(customAttributesItem.getValue().toString()) && !customAttributesItem.getValue().toString().equals(" ")) {
        if(customAttributesItem.getValue().toString().trim().length() == 0){
          tvNotice.setVisibility(View.GONE);
        }else {
          llNotice.setVisibility(View.VISIBLE);
          tvNotice.setVisibility(View.VISIBLE);
          viewnotice.setVisibility(View.VISIBLE);
          tvNotice.setText(customAttributesItem.getValue().toString());
        }
      }else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("short_description")
          && !TextUtils.isEmpty(customAttributesItem.getValue().toString()) && !customAttributesItem.getValue().toString().equals(" ") ) {

        if(customAttributesItem.getValue().toString().trim().length() == 0){
          tvNoticeshort_description.setVisibility(View.GONE);
        }else {
          llNotice.setVisibility(View.VISIBLE);
          viewnotice.setVisibility(View.VISIBLE);
          tvNoticeshort_description.setVisibility(View.VISIBLE);
        }
        tvNoticeshort_description.setHtml(customAttributesItem.getValue().toString(), new Html.ImageGetter() {
          @Override
          public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            Drawable empty = getResources().getDrawable(R.mipmap.ic_imfailed);
            d.addLevel(0, 0, empty);
            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

            new LoadImage().execute(source, d);

            return d;
          }
        });
      }
    }

    vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
    fpi.setViewPager(vpg);
  }

  @OnClick(R.id.imgNextPage)
  public void onClickNextPage(){
    commentPageAdapter.nextPage();
    int scrollTo = llTopComment.getBottom()+llAddToCart.getBottom()+llAddToCart.getBottom()/4;
    nsv.smoothScrollTo(0, scrollTo);
  }
  @OnClick(R.id.imgPrePage)
  public void onClickPrePage(){
    commentPageAdapter.prePage();
    int scrollTo = llTopComment.getBottom()+llAddToCart.getBottom()+llAddToCart.getBottom()/4;
    nsv.smoothScrollTo(0, scrollTo);
  }
  @OnClick(R.id.llSort)
  void showSorting(){
    new AlertDialog.Builder(this).setTitle("Sort By :").setItems(Constants.sortingCommentArr,(dialog, which) -> {
      tvSort.setText(Constants.sortingCommentArr[which]);
      String sort = Constants.sortingDirectionReview[which];
      request.setSortDate(sort);
      presenter.getListReviewProductByPage(request);
    }).show();
  }


  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

      EstoreDetailActivity.startMe(this, itemsItem, itemsItem.getCategoryName(),
              R_C_PRODUCT_DETAIL);
      finish();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
      EstoreDetailActivity.startMe(this, itemsItem, itemsItem.getCategoryName(),
              R_C_PRODUCT_DETAIL);
      finish();
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  private void setupCategoryId(){
    if(category!=null)
    if(category.equals("THE TIME")){
      category_id = "70";
    }else if(category.equals("THE DIRECT")){
      category_id = "56";
    }else if(category.equals("THE SELECTION")){
      category_id = "77";
    }else if(category.equals("Travel")){
      category_id = "24";
    }
    else if(category.equals("Hotels")){
      category_id = "57";
    }
    else if(category.equals("Hotels Plus")){
      category_id = "25";
    }
    else if(category.equals("Cruises & Trains")){
      category_id = "49";
    }
    else if(category.equals("Packages")){
      category_id = "50";
    }
    else if(category.equals("Services")){
      category_id = "53";
    }
    else if(category.equals("Wine & Dine")){
      category_id = "5";
    }
    else if(category.equals("American")){
      category_id = "17";
    }
    else if(category.equals("Asian")){
      category_id = "18";
    }
    else if(category.equals("Australian")){
      category_id = "44";
    }
    else if(category.equals("Chinese")){
      category_id = "43";
    }
    else if(category.equals("French")){
      category_id = "32";
    }
    else if(category.equals("Fusion")){
      category_id = "28";
    }
    else if(category.equals("Indian")){
      category_id = "46";
    }
    else if(category.equals("International")){
      category_id = "34";
    }
    else if(category.equals("Italian")){
      category_id = "29";
    }
    else if(category.equals("Japanese")){
      category_id = "42";
    }
    else if(category.equals("Mediterranean")){
      category_id = "33";
    }
    else if(category.equals("Mexican")){
      category_id = "80";
    }
    else if(category.equals("Modern European")){
      category_id = "30";
    }
    else if(category.equals("Western")){
      category_id = "31";
    }
    else if(category.equals("Wholesome")){
      category_id = "54";
    }
    else if(category.equals("Reservations")){
      category_id = "58";
    }
    else if(category.equals("Beverages")){
      category_id = "83";
    }
    else if(category.equals("Dessert")){
      category_id = "84";
    }
    else if(category.equals("German")){
      category_id = "86";
    }
    else if(category.equals("Shopping")){
      category_id = "4";
    }
    else if(category.equals("Fashion")){
      category_id = "10";
    }
    else if(category.equals("Floral")){
      category_id = "7";
    }
    else if(category.equals("Fragrance")){
      category_id = "37";
    }
    else if(category.equals("Gourmet")){
      category_id = "47";
    }
    else if(category.equals("Grocery")){
      category_id = "38";
    }
    else if(category.equals("Lifestyle & Décor")){
      category_id = "81";
    }
    else if(category.equals("Skin Care")){
      category_id = "48";
    }
    else if(category.equals("Wellness")){
      category_id = "6";
    }
    else if(category.equals("Aesthetics")){
      category_id = "12";
    }
    else if(category.equals("Enrichment Classes")){
      category_id = "13";
    }
    else if(category.equals("Gym")){
      category_id = "14";
    }
    else if(category.equals("Health")){
      category_id = "15";
    }
    else if(category.equals("Meditation & Mindfulness")){
      category_id = "36";
    }
    else if(category.equals("Spa")){
      category_id = "16";
    }
    else if(category.equals("Flash Sales ")){
      category_id = "23";
    }
    else if(category.equals("LUXURY")){
      category_id = "89";
    }else{
      category_id = "56";
    }
  }

  @Override
  public void renderNewPage(int page) {
    if(page!= Integer.parseInt(request.getCurrentPage())){
      request.setCurrentPage(""+page);
      int scrollTo = llTopComment.getBottom()+llAddToCart.getBottom()+llAddToCart.getBottom()/4;
      nsv.smoothScrollTo(0, scrollTo);
      presenter.getListReviewProductByPage(request);
    }
  }

  @Override
  public void addToCart(int position) {
    presenter.addToCart(
        new AddToCart.Params(recommendationAdapter.getData().get(position).getSku()
            , 1
            , recommendationAdapter.getData().get(position).getName()
            , recommendationAdapter.getData().get(position).getTypeId()));
  }

  @Override
  public void buyNow(int position) {
    presenter.addToBuyNow(
        new AddToBuyNow.Params(recommendationAdapter.getData().get(position).getSku()
            , 1
            , recommendationAdapter.getData().get(position).getName()
            , recommendationAdapter.getData().get(position).getTypeId()));
  }

  class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private LevelListDrawable mDrawable;

    @Override
    protected Bitmap doInBackground(Object... params) {
      String source = (String) params[0];
      mDrawable = (LevelListDrawable) params[1];
      try {
        InputStream is = new URL(source).openStream();
        return BitmapFactory.decodeStream(is);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      if (bitmap != null) {
        BitmapDrawable d = new BitmapDrawable(bitmap);
        mDrawable.addLevel(1, 1, d);
        mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        mDrawable.setLevel(1);
        CharSequence t = etvDescription.getText();
        etvDescription.setText(t);
      }
    }
  }

  private void setupRecyclerView() {
    recommendationAdapter = new RecommendationAdapter(this,this);
    recommendationAdapter.setEnableBuyNow(false);
    recommendationAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        if (item.getPillarId().equalsIgnoreCase(Constants.E_STORE)) {
          EstoreDetailActivity.startMe(EstoreDetailActivity.this, item, item.getCategoryName(),
                  R_C_PRODUCT_DETAIL);
        } else if (item.getPillarId().equalsIgnoreCase(Constants.TRAVEL)) {
          TravelDetailActivity.startMe(EstoreDetailActivity.this, item, item.getCategoryName(),
                  true, R_C_PRODUCT_DETAIL);
        } else {
          ActivityChooser.startMe(EstoreDetailActivity.this, item, item.getCategoryName(), false,
                  R_C_PRODUCT_DETAIL);
        }
      }

      @Override
      public void onFavItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        if (item.isFavourite()) {
          presenter.removeFromFavourite(item.getId() + "");
        } else {
          presenter.addToFavourite(item.getId() + "");
        }
      }

      @Override
      public void onReserveButton(ItemsItem item, int position, TravelProductViewHolder holder) {

      }
    });
    int spanCount = 2;
    int spacing = 8;
    rvRecommendation.addItemDecoration(
            new ProductGridSpacesItemDecoration(spanCount,
                    MetricsUtils.convertDpToPixel(spacing, this),
                    true));

    rvRecommendation.setLayoutManager(new GridLayoutManager(this, spanCount));

    rvRecommendation.setNestedScrollingEnabled(false);
    rvRecommendation.setAdapter(recommendationAdapter);

    //====== Comment rating        at com.skypremiuminternational.app.app.features.estore.detail.viewholder.CommentViewHolder.a(:35)

    // <<START>> 20200623 - WIKI Toan Tran - setting comment rv
    commentAdapter = new CommentAdapter(this,getSupportFragmentManager());
    rvComment.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    rvComment.setAdapter(commentAdapter);
    // <<END>> 20200623 - WIKI Toan Tran - setting comment rv

  }

  private void setFavStatus() {
    boolean isFavourite = itemsItem != null ? itemsItem.isFavourite()
            : detailsItem != null && detailsItem.isFavourite;
    int favIcon;
    int cartIcon;

    if (toolbar_white.getVisibility() == View.VISIBLE) {
      favIcon =
              isFavourite ? R.drawable.ic_favourite_fill_gold : R.drawable.ic_favourite_stroke_gold;
      cartIcon = R.drawable.ic_cart_accent;
    } else {
      favIcon =
          isFavourite ? R.drawable.ic_favourite_fill_gold : R.drawable.ic_favourite_stroke_gold;
      cartIcon = R.drawable.ic_cart_accent;
      /*favIcon =
              isFavourite ? R.drawable.ic_favourite_fill_white : R.drawable.ic_favourite_stroke_white;
      cartIcon = R.drawable.ic_cart;*/
    }
    imgFav.setImageResource(favIcon);
    ivCart.setImageResource(cartIcon);
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Inject
  @Override
  public void injectPresenter(EstoreDetailPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.iv_map)
  void onMapClick() {
    String uri = "geo:0,0?q=" + latitude + "," + longitude + "(" + address + ")";
    Timber.e("Uri " + uri);
    Uri gmmIntentUri = Uri.parse(uri);
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    if (mapIntent.resolveActivity(getPackageManager()) != null) {
      startActivity(mapIntent);
    }
  }

  @OnClick(R.id.tv_checkout)
  void addToCart() {


    if(edt_qty.getText().toString().isEmpty() || edt_qty.getText().toString().equals("0")){
      edt_qty.setError("This field can not be blank and the value must be greater than 0.");
    }else if(Integer.valueOf(edt_qty.getText().toString()) > qty) {
      //edt_qty.setError("You have exceeded the product quantity allowed for purchase.");
      Toast.makeText(this, "We don't have as many \""+product_name+"\" as you requested.",
              Toast.LENGTH_SHORT).show();
    }else{
      if (detailsItem != null) {
        presenter.addToCart(
                new AddToCart.Params(detailsItem.getSku(), Integer.parseInt(edt_qty.getText().toString()), detailsItem.getName(), detailsItem.typeId));
      }
      if (itemsItem != null) {
        presenter.addToCart(
                new AddToCart.Params(itemsItem.getSku(), Integer.parseInt(edt_qty.getText().toString()), itemsItem.getName(), itemsItem.getTypeId()));
      }
    }
  }

  @OnClick(R.id.etvDescription)
  void onClickDescription() {
    onClickToogleDescription();
  }

  @OnClick(R.id.llToogleDescription)
  void onClickToogleDescription() {
    final String readMore = getString(R.string.read_more);
    final String readLess = getString(R.string.read_less);
    if (tvToogleDescription.getText().toString().equals(readMore)) {
      etvDescription.setVisibility(View.VISIBLE);
      tvExcerpt.setVisibility(View.GONE);
      AnimationUtil.expand(etvDescription, new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          tvToogleDescription.setText(readLess);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
    } else if (tvToogleDescription.getText().toString().equals(readLess)) {
      AnimationUtil.collapse(etvDescription, new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          etvDescription.setVisibility(View.GONE);
          tvExcerpt.setVisibility(View.VISIBLE);
          tvToogleDescription.setText(readMore);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
    }
  }

  @Override
  public void hideLoading() {
    if (progressDialog != null && !isDestroyed()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.etvTerms)
  void onClickTerms() {
    onClickToogleTerms();
  }

  @OnClick(R.id.llToogleTerms)
  void onClickToogleTerms() {
    etvTerms.toggle();
    tvToogleTerms.setText(etvTerms.isExpanded() ? R.string.read_more : R.string.read_less);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    if(!TextUtils.isEmpty(from)
        &&(fromNotification()||fromDeeplink())){
      LandingActivity.startMe(this);
      finish();
    }else{
      finish();
    }
  }

  @Override
  public void onBackPressed() {
    if(!TextUtils.isEmpty(from)
        &&(fromNotification()||fromDeeplink())){
      LandingActivity.startMe(this);
      super.onBackPressed();
    }else{
      super.onBackPressed();
    }
  }

  @OnClick(R.id.ivNavigation_toolbar_white)
  void onClickBackWhite() {
    if(!TextUtils.isEmpty(from)
        &&(fromNotification()||fromDeeplink())){
      LandingActivity.startMe(this);
      finish();
    }else{
      finish();
    }
  }

  @Override
  public void render(EstoreDetailViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.message() != null) {
          DetailsItem detailsItem = viewState.message();
          if (imgList.size() > 0) {
            imgList.clear();
            for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
              imgList.add(detailsItem.getMediaGalleryEntries().get(i).getFile());
            }
          }
          if (imgList != null) {
            vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
            fpi.setViewPager(vpg);
            if (imgList.size() == 1) {
              fpi.setVisibility(View.INVISIBLE);
            }
          }
          checkMapInfos(viewState.message());
          renderProductDetail(viewState.message());
        }

        presenter.getCategory(itemsItem.getSku(),getCategoryId(itemsItem.getCustomAttributes()));
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                Toast.LENGTH_SHORT).show();
      }
    }
  }
  @Override
  public void renderSimple(EstoreDetailViewState viewState) {
    detailsItem = viewState.message();
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        if (viewState.message() != null) {
          DetailsItem detailsItem = viewState.message();
          if (imgList.size() > 0) {
            imgList.clear();
            for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
              imgList.add(detailsItem.getMediaGalleryEntries().get(i).getFile());
            }
          }
          if (imgList != null) {
            vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
            fpi.setViewPager(vpg);
            if (imgList.size() == 1) {
              fpi.setVisibility(View.INVISIBLE);
            }
          }
          checkMapInfos(viewState.message());
          renderProductDetail(viewState.message());
        }

        presenter.getCategory(viewState.message().getSku(),getCategoryIdDetailItem(viewState.message().getCustomAttributes()));
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                Toast.LENGTH_SHORT).show();
      }
    }
  }


  String getCategoryIdByCA(List<CustomAttribute> customAttributes){
    String id="";
    for(CustomAttribute customAttribute : customAttributes){
      if(customAttribute.getAttributeCode().equalsIgnoreCase("category_ids")){
        List<String> ids = (List<String>) customAttribute.getValue();
        if(ids.size()>1)
          id = ids.get(1);
        else
          id = ids.get(0);
      }
    }
    return  id;
  }
  String getCategoryId(List<CustomAttributesItem> customAttributes){
    String id="";
    for(CustomAttributesItem customAttribute : customAttributes){
      if(customAttribute.getAttributeCode().equalsIgnoreCase("category_ids")){
        List<String> ids = (List<String>) customAttribute.getValue();
        if(ids.size()>1)
          id = ids.get(1);
        else
          id = ids.get(0);
      }
    }
    return  id;
  }
  String getCategoryIdDetailItem(List<CustomAttribute> customAttributes){
    String id="";
    for(CustomAttribute customAttribute : customAttributes){
      if(customAttribute.getAttributeCode().equalsIgnoreCase("category_ids")){
        List<String> ids = (List<String>) customAttribute.getValue();
        if(ids.size()>1)
          id = ids.get(1);
        else
          id = ids.get(0);
      }
    }
    return  id;
  }

  @Override
  public void finish() {
    boolean isFav = itemsItem != null ? itemsItem.isFavourite()
            : detailsItem != null && detailsItem.isFavourite;
    int id = itemsItem != null ? itemsItem.getId() : -1;
    Intent intent = new Intent();
    intent.putExtra("fav_status", isFav);
    intent.putExtra("item_id", id);
    setResult(RESULT_OK, intent);
    super.finish();
  }

  @OnClick(R.id.ivFav)
  void onClickFav() {
    boolean isFav = itemsItem != null ? itemsItem.isFavourite()
            : detailsItem != null && detailsItem.isFavourite;
    String id =
            itemsItem != null ? String.valueOf(itemsItem.getId()) : String.valueOf(detailsItem.getId());
    if (isFav) {
      presenter.removeFromFavourite(id);
    } else {
      presenter.addToFavourite(id);
    }
  }

  @Override
  public void notifyFavStatusChanged(boolean isFavourite, String id) {
    if (itemsItem != null) {
      if (id.equals(itemsItem.getId() + "")) {
        itemsItem.setFavourite(isFavourite);
        setFavStatus();
      }
    }
    if (detailsItem != null) {
      if (id.equals(detailsItem.getId() + "")) {
        detailsItem.isFavourite = isFavourite;
        setFavStatus();
      }
    }

    if (clickedItem != null && id.equals(clickedItem.getId() + "")) {
      clickedItem.setFavourite(isFavourite);
      recommendationAdapter.notifyDataSetChanged();
    }
  }

  private void calculateDisplay() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;
  }

  @Override
  public void showRecommendationList(List<ItemsItem> items) {
    reloaditem = items;
    recommendationAdapter.setItemList(items);
  }


  private void renderProductDetail(DetailsItem item) {



    unSubscribe();
    String dealFromDate = null;
    String dealToDate = null;
    String currentServerTime = null;
    boolean dealStatus = false;
    String dealDiscountType = null;

    for (CustomAttribute customAttribute : item.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("current_server_time")) {
        currentServerTime = customAttribute.getValue().toString();
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("deal_status")) {
        dealStatus = customAttribute.getValue().equals(Constants.DEAL_STATUS_ENABLE);
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("deal_from_date")) {
        dealFromDate = customAttribute.getValue().toString();
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("deal_to_date")) {
        dealToDate = customAttribute.getValue().toString();
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("deal_discount_type")) {
        dealDiscountType = customAttribute.getValue().toString();
      }
      else if (customAttribute.getAttributeCode().equalsIgnoreCase("news_from_date")
              && customAttribute.getValue() != null
              && !customAttribute.getValue().toString().equalsIgnoreCase("")) {
      }
    }

    if(item.getSpecialFromDate()==null){
      item.setSpecialFormDate("");
    }else if(item.getSpecialToDate()==null){
      item.setSpecialToDate("");
    }else if(item.getSpecialPrice()==null) {
      item.setSpecialPrice("");
    }


    //====== Logic count down =======
    if (!item.getExtensionAttributes()
        .getStockItem()
        .getIsInStock()) {
      lyCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setText(getString(R.string.sale_not_started));
      tvCheckout.setText(getString(R.string.sale_not_started));
      tvCheckout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStartedDisabled));
      tvCheckout.setEnabled(false);
      tvCheckout.setClickable(false);
      lyCountDown.setBackgroundColor(
          ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStarted));
    }else {
      if (dealStatus || item.isFlashSales()) {
        if (!item.getExtensionAttributes()
            .getStockItem()
            .getIsInStock()) {
          lyCountDown.setVisibility(View.VISIBLE);
          tvExpiryTime.setText(getString(R.string.sale_not_started));
          tvCheckout.setText(getString(R.string.sale_not_started));
          tvCheckout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStartedDisabled));
          tvCheckout.setEnabled(false);
          tvCheckout.setClickable(false);
          lyCountDown.setBackgroundColor(
              ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStarted));
        } else {
          if (item.isFlashSales() && !dealStatus) {
            lyCountDown.setVisibility(View.VISIBLE);
            tvExpiryTime.setText(getString(R.string.sale_over));
            tvCheckout.setText(getString(R.string.sale_over));
            tvCheckout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStartedDisabled));
            tvCheckout.setEnabled(false);
            tvCheckout.setClickable(false);
            lyCountDown.setBackgroundColor(
                ContextCompat.getColor(getBaseContext(), R.color.colorSaleEnded));
          } else {
            if (item.getDealFromDate() != null && dealToDate != null && currentServerTime != null) {
              Resources resources = this.getResources();
              countDownSubscription = DateTimeCountDown.init(Constants.PATTERN_DATE_TIME, dealFromDate,
                  dealToDate, currentServerTime)
                  .subscribe(new DateTimeCountDown.CountDownSubscriber() {
                    @Override
                    public void onNext(DateTimeCountDown.CountDown countDown) {

                      if (((countDown.alreadyPast()))) {
                        lyCountDown.setVisibility(View.VISIBLE);
                        tvExpiryTime.setText(getString(R.string.sale_over));
                        tvCheckout.setText(getString(R.string.sale_over));
                        tvCheckout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStartedDisabled));
                        tvCheckout.setEnabled(false);
                        tvCheckout.setClickable(false);
                        lyCountDown.setBackgroundColor(
                            ContextCompat.getColor(getBaseContext(), R.color.colorSaleEnded));
                        unSubscribe();
                      } else if (countDown.hasNotReached()) {
                        lyCountDown.setVisibility(View.VISIBLE);
                        tvExpiryTime.setText(getString(R.string.sale_not_started));
                        tvCheckout.setText(getString(R.string.sale_not_started));
                        tvCheckout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStartedDisabled));
                        tvCheckout.setEnabled(false);
                        tvCheckout.setClickable(false);
                        lyCountDown.setBackgroundColor(
                            ContextCompat.getColor(getBaseContext(), R.color.colorSaleNotStarted));
                      } else {
                        lyCountDown.setVisibility(View.VISIBLE);
                        tvEndIn.setVisibility(View.VISIBLE);
                        lyCountDown.setBackgroundColor(
                            ContextCompat.getColor(getBaseContext(), R.color.light_blue));
                        tvExpiryTime.setText(String.format(Locale.getDefault(), "%s %s:%s:%s",
                            resources.getQuantityString(R.plurals.days, countDown.day(),
                                countDown.day()), String.valueOf(countDown.hr()), countDown.min(),
                            countDown.sec()));
                      }
                    }
                  });
            } else {
              lyCountDown.setVisibility(View.VISIBLE);
              tvExpiryTime.setVisibility(View.VISIBLE);
              lyCountDown.setBackgroundColor(
                  ContextCompat.getColor(this, R.color.colorSaleNotStarted));
              tvExpiryTime.setText(this.getString(R.string.sale_not_started));
              tvEndIn.setVisibility(View.GONE);
            }
          }
        }
      } else {
        lyCountDown.setVisibility(View.GONE);
      }
    }
    // IF OUT STOCK
    if (!item.getExtensionAttributes()
        .getStockItem()
        .getIsInStock()) {
      lyCountDown.setVisibility(View.VISIBLE);
      tvExpiryTime.setVisibility(View.VISIBLE);
      lyCountDown.setBackgroundColor(
          ContextCompat.getColor(this, R.color.colorSaleNotStarted));
      tvExpiryTime.setText(this.getString(R.string.sale_not_started));
      tvEndIn.setVisibility(View.GONE);
    }
    //====== Logic count down =======

    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo
    String status ="Fixed";
    if(!ProductUtil.isValid(currentServerTime)){
      status ="Invalid";
    }else{
      if (ProductUtil.isValid(item.getSpecialFromDate())){
        if (ProductUtil.isValid(item.getSpecialToDate())){
          if (!ProductUtil.isValid(item.getSpecialPrice())){
            status = "Fixed";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_DATE_TIME,item.getSpecialFromDate(),currentServerTime)
                && !ProductUtil.compareDate(Constants.PATTERN_DATE_TIME,item.getSpecialToDate(),currentServerTime)){
              status = "Started";
            }else{
              status = "Ended";
            }
          }
        }else{
          if (!ProductUtil.isValid(item.getSpecialPrice())){
            status = "Disabled";
          }else{
            if (ProductUtil.compareDate(Constants.PATTERN_DATE_TIME,item.getSpecialFromDate(),currentServerTime)){
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
    if(ProductUtil.isValid(getDiscountValue(dealStatus,item))&&((!status.equalsIgnoreCase("Disabled")&&!status.equalsIgnoreCase("Fixed"))||isCommingsoon())){
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
          showDiscountPrice(item.getPrice(),getDiscountValue(dealStatus,item),item.getDiscountType());
        }else {
          showOririginalPrice(item.getPrice());
        }
      }
    }else {
      showOririginalPrice(item.getPrice());
    }

    if(!item.isFlashSales()&&!dealStatus){
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
                ((Double.valueOf(item.getPrice()) - Double.valueOf(getDiscountValue(dealStatus,item)))
                    / Double.valueOf(item.getPrice()) * 100);
          tvDiscountPercentage.setText("SAVE " + Math.round(discountPercentage) + "%");

        }else{
          tvActualPrice.setVisibility(View.INVISIBLE);
          tvDiscountPercentage.setVisibility(View.INVISIBLE);
          tvDiscountPrice.setVisibility(View.VISIBLE);
          tvDiscountPrice.setText(
              String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
        }
      }else {
        // OUT DATE
        tvActualPrice.setVisibility(View.INVISIBLE);
        tvDiscountPercentage.setVisibility(View.INVISIBLE);
        tvDiscountPrice.setVisibility(View.VISIBLE);
        tvDiscountPrice.setText(
            String.format("S$%s", roundTwoDecimals(Double.parseDouble(item.getPrice()))));
      }
    }



    //oooooooooooooooooooooooooooooo LOGIC PRICE oooooooooooooooooooooooooooooooooo
    // GET REVIEW
    request = new ProductReviewRequest();
    request.setProductId(item.getId().toString());
    request.setCurrentCustomer("0");
    request.setCurrentPage("1");
    request.setPageSize(""+MAX_ITEM_PAGE);
    request.setSortDate(Constants.sortingDirectionReview[0]);

    presenter.getListReviewProduct(request);

  imgFav.setEnabled(true);
    presenter.getRatingSummary(item.getId().toString());


    imgFav.setEnabled(true);
  }
  private void setNewStatus(boolean isNew) {
    if (isNew) {
      ivNew.setVisibility(View.VISIBLE);
    } else {
      ivNew.setVisibility(View.GONE);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    if (latitude > 0 && longitude > 0 && address != null) {
      layoutMap.setVisibility(View.VISIBLE);
      mMap = googleMap;
      mMap.getUiSettings().setAllGesturesEnabled(true);

      try {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
      } catch (Resources.NotFoundException e) {
        Timber.e(e);
      }

      tvAddress.setText(address);
      tvAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf"));

      llMarker.post(() -> {

        llMarker.setDrawingCacheEnabled(true);
        llMarker.buildDrawingCache();
        Bitmap marker =
                Bitmap.createBitmap(llMarker.getMeasuredWidth(), llMarker.getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(marker);
        llMarker.draw(canvas);

        final LatLng latLng = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
      });
    }
  }

  private void checkMapInfos(DetailsItem detailsItem) {
    address = detailsItem.getName();
    for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("longitude")) {
        longitude = Double.parseDouble(customAttribute.getValue().toString());
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("latitude")) {
        latitude = Double.parseDouble(customAttribute.getValue().toString());
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partner_address")) {
        address = customAttribute.getValue().toString();
      }
    }

    if (longitude > 0 && latitude > 0 && address != null) {
      SupportMapFragment mapFragment =
              (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
    }
  }

  @Override
  public void updateShoppingCartCount(String count) {
    if (count == null && TextUtils.isEmpty(count)) {
      lyCartCount.setVisibility(View.INVISIBLE);
      lyCartCountWhite.setVisibility(View.INVISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
      tvCartCountWhite.setVisibility(View.INVISIBLE);
    } else {
      if (!count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.VISIBLE);
        lyCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
        tvCartCountWhite.setText(count);
      } else {
        lyCartCount.setVisibility(View.INVISIBLE);
        lyCartCountWhite.setVisibility(View.INVISIBLE);
        tvCartCount.setVisibility(View.INVISIBLE);
        tvCartCountWhite.setVisibility(View.INVISIBLE);
      }
    }
  }

  @Override
  public void showAddToCartFailedDialog(final AddToCart.Params params, Throwable error) {
    if (isDestroyed()) return;

    String action =
            params == null ? getString(R.string.btn_label_ok) : getString(R.string.btn_label_retry);
    new AlertDialog.Builder(this).setMessage(errorMessageFactory.getErrorMessage(error))
            .setPositiveButton(action, (dialog, which) -> {
              if (params != null) {
                presenter.addToCart(params);
              }
            })
            .show();
  }

  @Override
  public void render(Throwable throwable) {
    Toast.makeText(this, errorMessageFactory.getErrorMessage(throwable), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderFailedComment(Throwable throwable) {
    Toast.makeText(this, errorMessageFactory.getErrorMessage(throwable), Toast.LENGTH_SHORT).show();
    llComment.setVisibility(View.GONE);
  }

  @Override
  public void render(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderProductReview(ProductReviewResponse reviewResponse) {
    commentAdapter.setCommentList(reviewResponse.getCommentList());
    if(reviewResponse.getCommentList()!=null&&reviewResponse.getCommentList().size()>0){
      llComment.setVisibility(View.VISIBLE);
    }else{
      llComment.setVisibility(View.GONE);
    }

    if(reviewResponse.getTotalCount()==null||reviewResponse.getTotalCount().isEmpty()||Integer.parseInt(reviewResponse.getTotalCount())<=0){
      llCommentPage.setVisibility(View.GONE);
    }

    hideLoading();
    this.reviewResponse =  reviewResponse;
    initPage( );
  }


  void initPage( ){

    rvCommentPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    commentPageAdapter = new CommentPageAdapter(this,this);
    rvCommentPage.setAdapter(commentPageAdapter);
    int countItem = Integer.parseInt(this.reviewResponse.getTotalCount());
    int totalPage = 0;
    int limitPage = 0 ;

    if(countItem%MAX_ITEM_PAGE>0){
      totalPage = (countItem / MAX_ITEM_PAGE)+1;
    }else {
      totalPage = countItem/MAX_ITEM_PAGE;
    }

    if(totalPage>=MAX_PAGE){
      limitPage = 5;
    }else {
      limitPage = totalPage;
    }

    listPage = new ArrayList<>();
    for(int i = 0 ;i<limitPage;i++ ){
      CommentPage page = new CommentPage();
      listPage.add(page);
    }

    commentPageAdapter.setListData(listPage,totalPage,limitPage);
    commentPageAdapter.setCurrentPage(1);
  }

  @Override
  public void renderRatingStar(ResponseBody responseBody) {
    String rate = "0";
    try {
      rate  = responseBody.string();
    } catch (IOException e) {
      e.printStackTrace();
    }
      float fRate =  RatingUtils.getRating(rate);
      float iRate = RatingUtils.getRatingRound(rate);
      DecimalFormat df2 = new DecimalFormat("#.#");
      if(fRate>0){
        rtProductStar.setRating(fRate);
        rtAvgStar.setRating(fRate);
        StringBuilder stringBuilder = new StringBuilder();
        tvAvgRatingPoint.setText(df2.format((fRate)) + " " + getString(R.string.average_rating));
        tvNoRating.setVisibility(View.GONE);
        tvNoRatingTop.setVisibility(View.GONE);
        rtProductStar.setVisibility(View.VISIBLE);
        rtAvgStar.setVisibility(View.VISIBLE);
        tvAvgRatingPoint.setVisibility(View.VISIBLE);
        llComment.setVisibility(View.VISIBLE);
      }else {

        rtProductStar.setVisibility(View.GONE);
        rtAvgStar.setVisibility(View.GONE);
        tvAvgRatingPoint.setVisibility(View.GONE);
        tvNoRating.setVisibility(View.VISIBLE);
        tvNoRatingTop.setVisibility(View.VISIBLE);
        tvNoRating.setTypeface(null, Typeface.ITALIC);
        tvNoRatingTop.setTypeface(null, Typeface.ITALIC);
        llComment.setVisibility(View.GONE);
      }
  }

  @Override
  public void renderProductReviewByPage(ProductReviewResponse productReviewResponse) {
    commentAdapter.setCommentList(productReviewResponse.getCommentList());
    hideLoading();

  }

  @Override
  public void renderShoppingCart() {
    ShoppingCartActivity.start(this,ShoppingCartActivity.BUY_NOW);
  }

  @Override
  public void renderCategory(String string) {
    tvCategory.setText(string);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == R_C_PRODUCT_DETAIL && resultCode == Activity.RESULT_OK) {
      boolean isFavourite = data.getBooleanExtra("fav_status", false);
      final int id = data.getIntExtra("item_id", -1);
      if (clickedItem != null) {
        if (clickedItem.getId() == id) {
          clickedItem.setFavourite(isFavourite);
          recommendationAdapter.notifyDataSetChanged();
        }
      }
    }
  }

  private void unSubscribe() {
    if (countDownSubscription != null && !countDownSubscription.isUnsubscribed()) {
      countDownSubscription.unsubscribe();
    }
  }

  @OnClick(R.id.iv_cart)
  public void onClickCartCount() {
    ShoppingCartActivity.start(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void onDestroy() {
    unSubscribe();
    super.onDestroy();
  }
  private String getDiscountValue(boolean dealStatus , DetailsItem item){
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
  private class ImageGetter implements Html.ImageGetter {
    public Drawable getDrawable(String source) {
      int id;
      if (source.equals("img_signin.jpg")) {
        id = R.drawable.img_signin;
      }
      else {
        return null;
      }
      Drawable d = getResources().getDrawable(id);
      d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
      return d;
    }
  };


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
    return tvExpiryTime.getText().toString().equalsIgnoreCase(this.getString(R.string.sale_not_started));
  }

  boolean fromNotification(){
    if(from.equalsIgnoreCase("notification")){
      return true;
    }else {
      return false;
    }
  }
  boolean fromDeeplink(){
    if(from.equalsIgnoreCase("deeplink")){
      return true;
    }else {
      return false;
    }
  }
}
