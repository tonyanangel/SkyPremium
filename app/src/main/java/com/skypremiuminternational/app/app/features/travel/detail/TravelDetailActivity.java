package com.skypremiuminternational.app.app.features.travel.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.andrognito.kerningview.KerningTextView;
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
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.RecommendationAdapter;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.shopping.detail.ImagePagerAdapter;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ActivityChooser;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.BitmapCustomizer;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import dagger.android.AndroidInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by johnsonmaung on 9/28/17.
 */

public class TravelDetailActivity extends BaseActivity<TravelDetailPresenter>
    implements TravelDetailView<TravelDetailPresenter>, OnMapReadyCallback {



  @BindView(R.id.iv_new)
  ImageView imgNewStatus;
  @BindView(R.id.ivFav)
  ImageView imgFav;
  @BindView(R.id.tvReserveNow)
  KerningTextView tvReserveNow;
  @BindView(R.id.iv_map)
  ImageView ivMap;
  @BindView(R.id.llMarker)
  LinearLayout llMarker;
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
  TextView tvBenefits;
  @BindView(R.id.llDescription)
  LinearLayout llDescription;
  @BindView(R.id.tvToogleDescription)
  TextView tvToogleDescription;
  @BindView(R.id.llTerms)
  LinearLayout llTerms;
  @BindView(R.id.etvTerms)
  TextView etvTerms;
  @BindView(R.id.tvToogleTerms)
  TextView tvToogleTerms;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.iv_cart)
  ImageView ivCart;
  @BindView(R.id.ly_cart_count_white)
  FrameLayout lyCartCountWhite;
  @BindView(R.id.tv_cart_count_white)
  TextView tvCartCountWhite;
  @BindView(R.id.frmMap)
  FrameLayout frmMap;
  @BindView(R.id.ll_make_a_reservation)
  LinearLayout llMakeAReservation;

  @Inject
  BitmapCustomizer bitmapCustomizer;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  ItemsItem itemsItem;
  String category = "Travel";
  public static String category_id = "";
  List<String> imgList = new ArrayList<>();
  double longitude = 0.0, latitude = 0.0;
  String address;
  String reserveButtonTitle;
  int width, height;
  @BindView(R.id.tvExcerpt)
  TextView tvExcerpt;
  @BindView(R.id.etvDescription)
  TextView tvDescription;
  @BindView(R.id.rv_recommendation)
  RecyclerView rvRecommendaiton;
  private RecommendationAdapter recommendationAdapter;
  private GoogleMap mMap;
  private ProgressDialog progressDialog;
  private boolean fromTravel;
  private DetailsItem detailsItem;
  private DetailsItem detailsItemFinal;
  private static final int R_C_PRODUCT_DETAIL = 1234;
  private ItemsItem clickedItem;
  public static final int TRAVEL_DETAIL = 1111;
  private String sku = null;
  private String linkHGW = null;
  private String reserveButtonLink;
  private String from = "";
  private String skuFromNotification = "";
  private boolean isMapReady = false;
  private boolean isHGW = false;
  List<OutletItem> outletList;


  public static void startMe(Activity activity, ItemsItem itemsItem, String category,
                             boolean fromTravel, int requestCode) {
    Intent intent = new Intent(activity, TravelDetailActivity.class);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    intent.putExtra("category", category);
    intent.putExtra("from_travel", fromTravel);
    activity.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Fragment fragment, ItemsItem itemsItem, String category,
                             boolean fromTravel, int requestCode) {
    Intent intent = new Intent(fragment.getContext(), TravelDetailActivity.class);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    intent.putExtra("category", category);
    intent.putExtra("from_travel", fromTravel);
    fragment.startActivityForResult(intent, requestCode);
  }

  public static void startMe(MyFavouritesActivity favouritesActivity, DetailsItem detailsItem,
                             String categoryName, boolean fromTravel, int requestCode) {
    Intent intent = new Intent(favouritesActivity, TravelDetailActivity.class);
    intent.putExtra("fav_product", new Gson().toJson(detailsItem));
    intent.putExtra("from_travel", fromTravel);
    intent.putExtra("category", categoryName);
    favouritesActivity.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Context context, String from, String sku) {

    Intent intent = new Intent(context, TravelDetailActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("from", from);
    intent.putExtra("sku",   sku);

    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_travel_detail);
    ButterKnife.bind(this);

    toolbar.setVisibility(View.VISIBLE);
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading...");

    detailsItem = new Gson().fromJson(getIntent().getStringExtra("fav_product"), DetailsItem.class);
    itemsItem = new Gson().fromJson(getIntent().getStringExtra("Product"), ItemsItem.class);
    category = getIntent().getStringExtra("category");
    fromTravel = getIntent().getBooleanExtra("from_travel", false);
    skuFromNotification = getIntent().getStringExtra("sku");
    from = getIntent().getStringExtra("from");

    /*if (!fromTravel) {
      tvReserveNow.setVisibility(View.GONE);
    }*/

    if(category!=null) {
      setupCategoryId();
    }
    setupRecyclerView();

    if (detailsItem != null) {
      setFavStatus();
      setNewStatus(detailsItem.isNew());
      tvTitle_toolbar.setText(detailsItem.getName());
      tvCategory.setText(category);
      tvName.setText(detailsItem.getName());
      address = detailsItem.getName();

      for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase("description")
            && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llDescription.setVisibility(View.VISIBLE);
          //tvDescription.setText(customAttribute.getValue().toString());
          setTextViewHTML(tvDescription,customAttribute.getValue().toString());
          setTextViewHTML(tvExcerpt,customAttribute.getValue().toString());

//          tvExcerpt.setText(Html.fromHtml(customAttribute.getValue().toString()).toString().trim());

        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_terms_conditions")
            && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llTerms.setVisibility(View.VISIBLE);
          etvTerms.setText(customAttribute.getValue().toString());
          setTextViewHTML(etvTerms,customAttribute.getValue().toString());

        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_benefits")
            && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llBenefits.setVisibility(View.VISIBLE);
//          tvBenefits.setText(customAttribute.getValue().toString());
          setTextViewHTML(tvBenefits,customAttribute.getValue().toString());

        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(URLUtils.formatImageURL(customAttribute.getValue().toString()));
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("longitude")) {
          longitude = Double.parseDouble(customAttribute.getValue().toString());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("latitude")) {
          latitude = Double.parseDouble(customAttribute.getValue().toString());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partner_address")) {
          address = customAttribute.getValue().toString();
        }
      }

      if (imgList.size() > 0) {
        imgList.clear();
        for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
          imgList.add(
              URLUtils.formatImageURL(detailsItem.getMediaGalleryEntries().get(i).getFile()));
        }
        vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
        fpi.setViewPager(vpg);
        if (imgList.size() == 1) {
          fpi.setVisibility(View.INVISIBLE);
        }
      }
      presenter.getDetails(detailsItem.getSku());
    } else if (itemsItem != null) {
      setFavStatus();
      setNewStatus(itemsItem.isNew());
      tvTitle_toolbar.setText(itemsItem.getName());
      tvCategory.setText(category);
      tvName.setText(itemsItem.getName());
      address = itemsItem.getName();

      for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llDescription.setVisibility(View.VISIBLE);
//            tvDescription.setText(customAttributesItem.getValue().toString());
            setTextViewHTML(tvDescription,customAttributesItem.getValue().toString());
//
//            tvExcerpt.setText(
//                Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());
            setTextViewHTML(tvExcerpt,customAttributesItem.getValue().toString());

          }
        } else if (customAttributesItem.getAttributeCode()
            .equalsIgnoreCase("partners_terms_conditions")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llTerms.setVisibility(View.VISIBLE);
//            etvTerms.setText(customAttributesItem.getValue().toString());
            setTextViewHTML(etvTerms,customAttributesItem.getValue().toString());

          }
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("partners_benefits")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llBenefits.setVisibility(View.VISIBLE);
//            tvBenefits.setText(customAttributesItem.getValue().toString());
            setTextViewHTML(tvBenefits,customAttributesItem.getValue().toString());

          }
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(URLUtils.formatImageURL(customAttributesItem.getValue().toString()));
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("longitude")) {
          longitude = Double.parseDouble(customAttributesItem.getValue().toString());
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("latitude")) {
          latitude = Double.parseDouble(customAttributesItem.getValue().toString());
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("partner_address")) {
          address = customAttributesItem.getValue().toString();
        }
      }

      vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
      fpi.setViewPager(vpg);
      presenter.getDetails(itemsItem.getSku());
    }else if(fromNotification()||fromDeeplink()){
      presenter.getCategorySimple(skuFromNotification);
    }
    presenter.getCartCount();
    calculateDisplay();
    llImageHolder.getLayoutParams().height = height;
    final int min_height =
        ((width / 16) * 9) + MetricsUtils.convertDpToPixel(112, getApplicationContext());
    final int max_height = height - min_height;

    nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                                 int oldScrollY) {

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
      }
    });

    ivMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        goToMap();
      }
    });


    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    //etvTerms.collapse();

//    tvReserveNow.setText(reserveButtonTitle);

  }

  private void setupCategoryId() {

    String getcategory = category;
    if (category.contains(",")) {
      int iend = category.indexOf(",");

      if (iend != -1) {
        getcategory = category.substring(0, iend);
      }
    }


    if (getcategory.equals("THE TIME")) {
      category_id = "70";
    } else if (getcategory.equals("THE DIRECT")) {
      category_id = "56";
    } else if (getcategory.equals("THE SELECTION")) {
      category_id = "77";
    } else if (getcategory.equals("Travel")) {
      category_id = "24";
    } else if (getcategory.equals("Hotels")) {
      category_id = "57";
    } else if (getcategory.equals("Hotels Plus")) {
      category_id = "25";
    } else if (getcategory.equals("Cruises & Trains")) {
      category_id = "49";
    } else if (getcategory.equals("Packages")) {
      category_id = "50";
    } else if (getcategory.equals("Services")) {
      category_id = "53";
    } else if (getcategory.equals("Wine & Dine")) {
      category_id = "5";
    } else if (getcategory.equals("American")) {
      category_id = "17";
    } else if (getcategory.equals("Asian")) {
      category_id = "18";
    } else if (getcategory.equals("Australian")) {
      category_id = "44";
    } else if (getcategory.equals("Chinese")) {
      category_id = "43";
    } else if (getcategory.equals("French")) {
      category_id = "32";
    } else if (getcategory.equals("Fusion")) {
      category_id = "28";
    } else if (getcategory.equals("Indian")) {
      category_id = "46";
    } else if (getcategory.equals("International")) {
      category_id = "34";
    } else if (getcategory.equals("Italian")) {
      category_id = "29";
    } else if (getcategory.equals("Japanese")) {
      category_id = "42";
    } else if (getcategory.equals("Mediterranean")) {
      category_id = "33";
    } else if (getcategory.equals("Mexican")) {
      category_id = "80";
    } else if (getcategory.equals("Modern European")) {
      category_id = "30";
    } else if (getcategory.equals("Western")) {
      category_id = "31";
    } else if (getcategory.equals("Wholesome")) {
      category_id = "54";
    } else if (getcategory.equals("Reservations")) {
      category_id = "58";
    } else if (getcategory.equals("Beverages")) {
      category_id = "83";
    } else if (getcategory.equals("Dessert")) {
      category_id = "84";
    } else if (getcategory.equals("German")) {
      category_id = "86";
    } else if (getcategory.equals("Shopping")) {
      category_id = "4";
    } else if (getcategory.equals("Fashion")) {
      category_id = "10";
    } else if (getcategory.equals("Floral")) {
      category_id = "7";
    } else if (getcategory.equals("Fragrance")) {
      category_id = "37";
    } else if (getcategory.equals("Gourmet")) {
      category_id = "47";
    } else if (getcategory.equals("Grocery")) {
      category_id = "38";
    } else if (getcategory.equals("Lifestyle & Décor")) {
      category_id = "81";
    } else if (getcategory.equals("Skin Care")) {
      category_id = "48";
    } else if (getcategory.equals("Wellness")) {
      category_id = "6";
    } else if (getcategory.equals("Aesthetics")) {
      category_id = "12";
    } else if (getcategory.equals("Enrichment Classes")) {
      category_id = "13";
    } else if (getcategory.equals("Gym")) {
      category_id = "14";
    } else if (getcategory.equals("Health")) {
      category_id = "15";
    } else if (getcategory.equals("Meditation & Mindfulness")) {
      category_id = "36";
    } else if (getcategory.equals("Spa")) {
      category_id = "16";
    } else if (getcategory.equals("Flash Sales ")) {
      category_id = "23";
    }
  }

  private String toCategory(String id) {

    String categoryName = "";


    if (id.equals("70")) {
      categoryName = "THE TIME";
    } else if (id.equals("56")) {
      categoryName = "THE DIRECT";
    } else if (id.equals("77")) {
      categoryName = "THE SELECTION";
    } else if (id.equals("24")) {
      categoryName = "Travel";
    } else if (id.equals("57")) {
      categoryName = "Hotels";
    } else if (id.equals("25")) {
      categoryName = "Hotels Plus";
    } else if (id.equals("49")) {
      categoryName = "Cruises & Trains";
    } else if (id.equals("50")) {
      categoryName = "Packages";
    } else if (id.equals("53")) {
      categoryName = "Services";
    } else if (id.equals("5")) {
      categoryName = "Wine & Dine";
    } else if (id.equals("17")) {
      categoryName = "American";
    } else if (id.equals("18")) {
      categoryName = "Asian";
    } else if (id.equals("44")) {
      categoryName = "Australian";
    } else if (id.equals("43")) {
      categoryName = "Chinese";
    } else if (id.equals("32")) {
      categoryName = "French";
    } else if (id.equals("28")) {
      categoryName = "Fusion";
    } else if (id.equals("46")) {
      categoryName = "Indian";
    } else if (id.equals("34")) {
      categoryName = "International";
    } else if (id.equals("29")) {
      categoryName = "Italian";
    } else if (id.equals("42")) {
      categoryName = "Japanese";
    } else if (id.equals("33")) {
      categoryName = "Mediterranean";
    } else if (id.equals("80")) {
      categoryName = "Mexican";
    } else if (id.equals("30")) {
      categoryName = "Modern European";
    } else if (id.equals("31")) {
      categoryName = "Western";
    } else if (id.equals("54")) {
      categoryName = "Wholesome";
    } else if (id.equals("58")) {
      categoryName = "Reservations";
    } else if (id.equals("83")) {
      categoryName = "Beverages";
    } else if (id.equals("84")) {
      categoryName = "Dessert";
    } else if (id.equals("86")) {
      categoryName = "German";
    } else if (id.equals("4")) {
      categoryName = "Shopping";
    } else if (id.equals("10")) {
      categoryName = "Fashion";
    } else if (id.equals("7")) {
      categoryName = "Floral";
    } else if (id.equals("37")) {
      categoryName = "Fragrance";
    } else if (id.equals("47")) {
      categoryName = "Gourmet";
    } else if (id.equals("38")) {
      categoryName = "Grocery";
    } else if (id.equals("81")) {
      categoryName = "Lifestyle & Décor";
    } else if (id.equals("48")) {
      categoryName = "Skin Care";
    } else if (id.equals("6")) {
      categoryName = "Wellness";
    } else if (id.equals("12")) {
      categoryName = "Aesthetics";
    } else if (id.equals("13")) {
      categoryName = "Enrichment Classes";
    } else if (id.equals("14")) {
      categoryName = "Gym";
    } else if (id.equals("15")) {
      categoryName = "Health";
    } else if (id.equals("36")) {
      categoryName = "Meditation & Mindfulness";
    } else if (id.equals("16")) {
      categoryName = "Spa";
    } else if (id.equals("23")) {
      categoryName = "Flash Sales ";
    }
    return categoryName;
  }

  private void setupRecyclerView() {
    recommendationAdapter = new RecommendationAdapter();
    rvRecommendaiton.setNestedScrollingEnabled(false);
    int spanCount = 2;
    int spacing = 8;
    rvRecommendaiton.addItemDecoration(
        new ProductGridSpacesItemDecoration(spanCount,
            MetricsUtils.convertDpToPixel(spacing, this),
            true));

    rvRecommendaiton.setLayoutManager(new GridLayoutManager(this, spanCount));

    rvRecommendaiton.setAdapter(recommendationAdapter);
    recommendationAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        if (item.getPillarId().equalsIgnoreCase(Constants.E_STORE)) {
          EstoreDetailActivity.startMe(TravelDetailActivity.this, item, item.getCategoryName(),
              R_C_PRODUCT_DETAIL);
        } else if (item.getPillarId().equalsIgnoreCase(Constants.TRAVEL)) {
          TravelDetailActivity.startMe(TravelDetailActivity.this, item, item.getCategoryName(),
              true, R_C_PRODUCT_DETAIL);
        } else {
          ActivityChooser.startMe(TravelDetailActivity.this, item, item.getCategoryName(), false,
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
        if (holder.getSku() != null) {
          //presenter.getDetailsItem(holder.getSku());
        } else if (!ObjectUtil.isNull(holder.getLinkHGW()) && !ObjectUtil.isEmptyStr(holder.getLinkHGW())) {
          if (CommonUtils.isValidUrl(holder.getLinkHGW())) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.getLinkHGW()));
            startActivity(browserIntent);
          } else {
            CommonUtils.showToast(TravelDetailActivity.this, getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
          }
          //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
        } else {
          String name = item != null ? item.getName() : item.getName();
          MembershipActivity.startMe(TravelDetailActivity.this, "Hi Sky Premium Membership Services Team,\n\n"
              + "I will like to enquire on a travel booking for "
              + name
              + ", for the period from <<Check-In Date>> to <<Check-Out Date>>, for <<# of Adults>> adults and <<# of Child>> child.\n\n"
              + "Appreciate if you can kindly assist on my request. Thank you.");
        }
      }

    });
  }

  private void setFavStatus() {
    boolean isFavourite = itemsItem != null ? itemsItem.isFavourite()
        : detailsItem != null && detailsItem.isFavourite;
    int favIcon;
    int cartIcon;

    if (toolbar_white.getVisibility() == View.VISIBLE) {
      favIcon = isFavourite ? R.drawable.ic_favourite_fill_gold
          : R.drawable.ic_favourite_stroke_gold;
      cartIcon = R.drawable.ic_cart_accent;
    } else {
      favIcon = isFavourite ? R.drawable.ic_favourite_fill_white
          : R.drawable.ic_favourite_stroke_white;
      cartIcon = R.drawable.ic_cart;
    }
    imgFav.setImageResource(favIcon);
    ivCart.setImageResource(cartIcon);
  }

  private void goToMap() {
    String uri = "geo:0,0?q=" + latitude + "," + longitude + "(" + address + ")";
    Timber.e("Uri " + uri);
    Uri gmmIntentUri = Uri.parse(uri);
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    if (mapIntent.resolveActivity(getPackageManager()) != null) {
      startActivity(mapIntent);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
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

  @Inject
  @Override
  public void injectPresenter(TravelDetailPresenter presenter) {
    super.injectPresenter(presenter);
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

  @OnClick(R.id.llToogleDescription)
  void onClickToogleDescription() {
    final String readMore = getString(R.string.read_more);
    final String readLess = getString(R.string.read_less);
    if (tvToogleDescription.getText().toString().equals(readMore)) {
      tvDescription.setVisibility(View.VISIBLE);
      tvExcerpt.setVisibility(View.GONE);
      AnimationUtil.expand(tvDescription, new Animation.AnimationListener() {
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
      AnimationUtil.collapse(tvDescription, new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          tvDescription.setVisibility(View.GONE);
          tvExcerpt.setVisibility(View.VISIBLE);
          tvToogleDescription.setText(readMore);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
    }
  }

  @OnClick(R.id.etvTerms)
  void onClickTerms() {
    onClickToogleTerms();
  }

  @OnClick(R.id.llToogleTerms)
  void onClickToogleTerms() {
    //etvTerms.toggle();
    //tvToogleTerms.setText(etvTerms.isExpanded() ? R.string.read_more : R.string.read_less);
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

  @OnClick(R.id.ivNavigation_toolbar_white)
  void onClickBackWhite() {
    finish();
  }

  @OnClick(R.id.tvReserveNow)
  void onClickReserveNow() {
    if (sku != null) {
      presenter.getDetailsItem(sku);
    }else if(isHGW){
      MakeAReservationDialogFragment fragment = MakeAReservationDialogFragment.newInstance(outletList,detailsItemFinal.getId(),detailsItemFinal.getName());
      fragment.show(getSupportFragmentManager(), MakeAReservationDialogFragment.TAG);
    } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)) {
      if (CommonUtils.isValidUrl(linkHGW)) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkHGW));
        startActivity(browserIntent);
      } else {
        CommonUtils.showToast(TravelDetailActivity.this, getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
      }
      //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
    } else {

      String name = itemsItem != null ? itemsItem.getName() : detailsItem.getName();
      MembershipActivity.startMe(this, "Hi Sky Premium Membership Services Team,\n\n"
          + "I will like to enquire on a travel booking for "
          + name
          + ", for the period from <<Check-In Date>> to <<Check-Out Date>>, for <<# of Adults>> adults and <<# of Child>> child.\n\n"
          + "Appreciate if you can kindly assist on my request. Thank you.");
    }
  }

  @Override
  public void updateCartCount(String count) {
    if (count.equals("")) {
      lyCartCount.setVisibility(View.VISIBLE);
      lyCartCountWhite.setVisibility(View.VISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
      tvCartCountWhite.setVisibility(View.INVISIBLE);
    } else {
      if (count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.INVISIBLE);
        lyCartCountWhite.setVisibility(View.INVISIBLE);
      } else {
        lyCartCount.setVisibility(View.VISIBLE);
        lyCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
        tvCartCountWhite.setText(count);
      }
    }
  }

  @Override
  public void render(TravelDetailViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        DetailsItem detailsItem = viewState.message();
        detailsItemFinal = viewState.message();
        if (imgList.size() > 0) {
          imgList.clear();
          for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
            imgList.add(
                URLUtils.formatImageURL(detailsItem.getMediaGalleryEntries().get(i).getFile()));
          }
          vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
          fpi.setViewPager(vpg);
          if (imgList.size() == 1) {
            fpi.setVisibility(View.INVISIBLE);
          }
        }

        if (!ObjectUtil.isNull(detailsItem) && !ObjectUtil.isNull(detailsItem.getCustomAttributes())) {
          for (CustomAttribute customAttributesItem : detailsItem.getCustomAttributes()) {
            if (customAttributesItem.getAttributeCode().equalsIgnoreCase("reserve_button_title")) {
              reserveButtonTitle = customAttributesItem.getValue().toString();
              Log.e("LOG_INFO", "reserveButtonTitle: " + reserveButtonTitle);
            } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("reserve_button_link")) {
              sku = customAttributesItem.getValue().toString();
              Log.e("LOG_INFO", "sku: " + sku);
            } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("hgw_link")) {
              linkHGW = customAttributesItem.getValue().toString();
              Log.e("LOG_INFO", "linkHGW: " + linkHGW);
            }
          }
        }

        /*Handle display reserve button*/

        presenter.getOutletResevation(detailsItemFinal.getId().toString().trim());

        //============ OPEN tvReserveNow

        tvReserveNow.setVisibility(View.VISIBLE);

        //==================================
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void renderGetDetailToGoEstore(DetailsItem detailsItem) {
    /*String category="";
    for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("category_ids")
          && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          List<String> ids = (List<String>) customAttribute.getValue();
          category = ids.get(0);
      }
    }*/
   /* String getcategory = category;
    if(category.contains(",")){
      int iend = category.indexOf(",");

      if (iend != -1)
      {
        getcategory= category.substring(0 , iend);
      }
    }*/
    //presenter.getCategory(category);
    presenter.getCategoryDefault(detailsItem);

  }

  @Override
  public void renderGotoEstore(DetailsItem detailsItem) {
    EstoreDetailActivity.startMe(this, detailsItem, detailsItem.getCategoryName(), TRAVEL_DETAIL);

  }

  @Override
  public void showErrorMsg(int msg) {
    Toast.makeText(TravelDetailActivity.this, getText(msg), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderGetOutletFailed() {
    isHGW = false;
    if (!ObjectUtil.isNull(reserveButtonTitle) && !ObjectUtil.isEmptyStr(reserveButtonTitle)) {
      tvReserveNow.setText(reserveButtonTitle);
    }

    if (fromTravel) {
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);

    } else {
      if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)) {
        tvReserveNow.setVisibility(View.VISIBLE);
        llMakeAReservation.setVisibility(View.GONE);

      }else if(isHGW){
        tvReserveNow.setVisibility(View.GONE);
        llMakeAReservation.setVisibility(View.VISIBLE);

      } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)) {
        tvReserveNow.setVisibility(View.VISIBLE);
        llMakeAReservation.setVisibility(View.GONE);

      } else {
        tvReserveNow.setVisibility(View.GONE);
        llMakeAReservation.setVisibility(View.GONE);
      }
    }
  }

  @Override
  public void renderGetOutletSuccess(List<OutletItem> outletItems) {
    outletList =  outletItems;
    if(outletItems!=null && outletItems.size() > 0){
      isHGW = true;
    }else {
      isHGW = false;
    }


    if (!ObjectUtil.isNull(reserveButtonTitle) && !ObjectUtil.isEmptyStr(reserveButtonTitle)) {
      tvReserveNow.setText(reserveButtonTitle);
    }

    if (fromTravel) {
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);

    } else {
      if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)) {
        tvReserveNow.setVisibility(View.VISIBLE);
        llMakeAReservation.setVisibility(View.GONE);

      }else if(isHGW){
        tvReserveNow.setVisibility(View.GONE);
        llMakeAReservation.setVisibility(View.VISIBLE);

      } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)) {
        tvReserveNow.setVisibility(View.VISIBLE);
        llMakeAReservation.setVisibility(View.GONE);

      } else {
        tvReserveNow.setVisibility(View.GONE);
        llMakeAReservation.setVisibility(View.GONE);
      }
    }


  }

  @Override
  public void hideLoading() {
    if (progressDialog != null && !isDestroyed()) {
      progressDialog.dismiss();
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
      if (id.equals(detailsItem.getId().toString())) {
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
  public void onMapReady(GoogleMap googleMap) {
    isMapReady = true;
    mMap = googleMap;
    mMap.getUiSettings().setAllGesturesEnabled(false);

    try {
      googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
    } catch (Resources.NotFoundException e) {
      Timber.e(e);
    }

    tvAddress.setText(address);
    tvAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf"));

    llMarker.post(new Runnable() {
      @Override
      public void run() {

        llMarker.setDrawingCacheEnabled(true);
        llMarker.buildDrawingCache();
        Bitmap marker =
            Bitmap.createBitmap(llMarker.getMeasuredWidth(), llMarker.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(marker);
        llMarker.draw(canvas);

        final LatLng latLng = new LatLng(latitude, longitude);

        mMap.addMarker(
            new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void showRecommededList(List<ItemsItem> items) {
    recommendationAdapter.setItemList(items);
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

  private void setNewStatus(boolean isNew) {
    if (isNew) {
      imgNewStatus.setVisibility(View.VISIBLE);
    } else {
      imgNewStatus.setVisibility(View.GONE);
    }
  }

  @OnClick(R.id.iv_cart)
  public void onClickCartCount() {
    ShoppingCartActivity.start(this);
  }

  @Override
  public void renderDetailItem(DetailsItem detailsItem) {

    category = detailsItem.getCategoryName();
    if(category!=null){
      setupCategoryId();
    }
    setFavStatus();
    setNewStatus(detailsItem.isNew());
    tvTitle_toolbar.setText(detailsItem.getName());
    tvCategory.setText(category);
    tvName.setText(detailsItem.getName());
    address = detailsItem.getName();

    for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("description")
          && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
        llDescription.setVisibility(View.VISIBLE);
//        tvDescription.setText(customAttribute.getValue().toString());
        setTextViewHTML(tvDescription,customAttribute.getValue().toString());
//        tvExcerpt.setText(Html.fromHtml(customAttribute.getValue().toString()).toString().trim());
        setTextViewHTML(tvExcerpt,customAttribute.getValue().toString());

      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_terms_conditions")
          && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
        llTerms.setVisibility(View.VISIBLE);
//        etvTerms.setText(customAttribute.getValue().toString());
        setTextViewHTML(etvTerms,customAttribute.getValue().toString());

      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_benefits")
          && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
        llBenefits.setVisibility(View.VISIBLE);
//        tvBenefits.setText(customAttribute.getValue().toString());
        setTextViewHTML(tvBenefits,customAttribute.getValue().toString());

      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("image")) {
        imgList.add(URLUtils.formatImageURL(customAttribute.getValue().toString()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("longitude")) {
        longitude = Double.parseDouble(customAttribute.getValue().toString());
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("latitude")) {
        latitude = Double.parseDouble(customAttribute.getValue().toString());
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partner_address")) {
        address = customAttribute.getValue().toString();
      }
    }

    if (longitude == 0.0 && latitude == 0.0) {
      frmMap.setVisibility(View.GONE);
      ivMap.setVisibility(View.GONE);
    }else {
      frmMap.setVisibility(View.VISIBLE);
      ivMap.setVisibility(View.VISIBLE);
    }

    if (imgList.size() > 0) {
      imgList.clear();
      for (int i = 0; i < detailsItem.getMediaGalleryEntries().size(); i++) {
        imgList.add(
            URLUtils.formatImageURL(detailsItem.getMediaGalleryEntries().get(i).getFile()));
      }
      vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
      fpi.setViewPager(vpg);
      if (imgList.size() == 1) {
        fpi.setVisibility(View.INVISIBLE);
      }
    }
    presenter.getCategory(detailsItem.getSku());

    if(isMapReady){
      tvAddress.setText(address);
      tvAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf"));
      llMarker.post(new Runnable() {
        @Override
        public void run() {

          llMarker.setDrawingCacheEnabled(true);
          llMarker.buildDrawingCache();
          Bitmap marker =
              Bitmap.createBitmap(llMarker.getMeasuredWidth(), llMarker.getMeasuredHeight(),
                  Bitmap.Config.ARGB_8888);
          Canvas canvas = new Canvas(marker);
          llMarker.draw(canvas);

          final LatLng latLng = new LatLng(latitude, longitude);

          mMap.addMarker(
              new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(marker)));
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
      });
    }
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

  @Override
  public void onBackPressed() {
    if(!TextUtils.isEmpty(from)
      &&(fromNotification()||fromDeeplink())){
      LandingActivity.startMe(this);
      super.onBackPressed();;
    }else{
      super.onBackPressed();
    }
  }

  protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
  {
    int start = strBuilder.getSpanStart(span);
    int end = strBuilder.getSpanEnd(span);
    int flags = strBuilder.getSpanFlags(span);
    ClickableSpan clickable = new ClickableSpan() {
      public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(span.getURL()));
        TravelDetailActivity.this.startActivity(browserIntent);
      }
    };
    strBuilder.setSpan(clickable, start, end, flags);
    strBuilder.removeSpan(span);
  }

  protected void setTextViewHTML(TextView text, String html)
  {
    CharSequence sequence = Html.fromHtml(html);
    SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
    URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
    for(URLSpan span : urls) {
      makeLinkClickable(strBuilder, span);
    }
    text.setText(strBuilder);
    text.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @Override
  public void showLoading() {
    progressDialog.setMessage("Loading...");
    progressDialog.show();
  }
}
