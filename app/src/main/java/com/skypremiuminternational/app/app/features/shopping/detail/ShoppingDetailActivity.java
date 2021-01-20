package com.skypremiuminternational.app.app.features.shopping.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.andrognito.kerningview.KerningTextView;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.google.gson.Gson;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.RecommendationAdapter;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ActivityChooser;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.view.ExpandableWebView;
import com.skypremiuminternational.app.app.view.MediumTextView;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.app.view.WebTextView;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import dagger.android.AndroidInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by johnsonmaung on 9/28/17.
 */

public class ShoppingDetailActivity extends BaseActivity<ShoppingDetailPresenter>
    implements ShoppingDetailView<ShoppingDetailPresenter> {

  @BindView(R.id.tvExcerpt)
  TextView tvExcerpt;
  @BindView(R.id.iv_new)
  ImageView imgNewStatus;
  @BindView(R.id.ivFav)
  ImageView imgFav;
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
  @BindView(R.id.etvDescription)
  TextView etvDescription;
  @BindView(R.id.tvToogleDescription)
  TextView tvToogleDescription;
  @BindView(R.id.llTerms)
  LinearLayout llTerms;
  @BindView(R.id.etvTerms)
  TextView etvTerms;
  @BindView(R.id.tvToogleTerms)
  TextView tvToogleTerms;
  @BindView(R.id.rv_recommendation)
  RecyclerView rvRecommendation;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.iv_cart)
  ImageView ivCart;
  @BindView(R.id.ly_cart_count_white)
  FrameLayout lyCartCountWhite;
  @BindView(R.id.tv_cart_count_white)
  TextView tvCartCountWhite;
  @BindView(R.id.tvReserveNow)
  KerningTextView tvReserveNow;
  @BindView(R.id.ll_make_a_reservation)
  LinearLayout llMakeAReservation;
  @BindView(R.id.tv_make_a_reservation)
  TextView tvMakeReservation;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  String category = "";
  public static String category_id = "";
  private DetailsItem detailsItem;
  private DetailsItem detailsItemFinal;
  ItemsItem itemsItem;
  List<String> imgList = new ArrayList<>();
  int width, height;
  private ProgressDialog progressDialog;
  private ItemsItem clickedItem;
  private RecommendationAdapter recommendationAdapter;
  private static final int R_C_PRODUCT_DETAIL = 1234;
  private String sku = null;
  private String linkHGW = null;
  private String reserveButtonTitle;
  private String selectDisplayCatogry = "";
  private boolean isHGW = false;
  List<OutletItem> outletList = new ArrayList<>();


  public static void startMe(MyFavouritesActivity favouritesActivity, DetailsItem detailsItem,
                             String categoryName, int requestCode) {
    Intent intent = new Intent(favouritesActivity, ShoppingDetailActivity.class);
    intent.putExtra("fav_product", new Gson().toJson(detailsItem));
    intent.putExtra("category", categoryName);
    favouritesActivity.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Fragment fragment, ItemsItem itemsItem, String category,
                             int requestCode) {
    Intent intent = new Intent(fragment.getContext(), ShoppingDetailActivity.class);
    intent.putExtra("Category", category);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    fragment.startActivityForResult(intent, requestCode);
  }

  public static void startMe(Activity activity, ItemsItem itemsItem, String category,
                             int requestCode) {
    Intent intent = new Intent(activity, ShoppingDetailActivity.class);
    intent.putExtra("Category", category);
    intent.putExtra("Product", new Gson().toJson(itemsItem));
    activity.startActivityForResult(intent, requestCode);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_detail);
    ButterKnife.bind(this);

    toolbar.setVisibility(View.VISIBLE);
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading");

    detailsItem = new Gson().fromJson(getIntent().getStringExtra("fav_product"), DetailsItem.class);
    category = getIntent().getStringExtra("Category");
    itemsItem = new Gson().fromJson(getIntent().getStringExtra("Product"), ItemsItem.class);
    //setupCategoryId();
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
          /*etvDescription.setText(customAttribute.getValue().toString());
          tvExcerpt.setText(
              Html.fromHtml(customAttribute.getValue().toString()).toString().trim());*/

          setTextViewHTML(etvDescription,customAttribute.getValue().toString());
          setTextViewHTML(tvExcerpt,customAttribute.getValue().toString());
        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_terms_conditions")
            && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llTerms.setVisibility(View.VISIBLE);
//          etvTerms.setText(customAttribute.getValue().toString());
          setTextViewHTML(etvTerms,customAttribute.getValue().toString());

        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("partners_benefits")
            && !TextUtils.isEmpty(customAttribute.getValue().toString())) {
          llBenefits.setVisibility(View.VISIBLE);
//          tvBenefits.setText(customAttribute.getValue().toString());
          setTextViewHTML(tvBenefits,customAttribute.getValue().toString());

        } else if (customAttribute.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(URLUtils.formatImageURL(customAttribute.getValue().toString()));
        }else if(customAttribute.getAttributeCode().equalsIgnoreCase("select_display_category")){
          selectDisplayCatogry = (String) customAttribute.getValue();
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
      setNewStatus(itemsItem.isNew());
      setFavStatus();
      tvTitle_toolbar.setText(itemsItem.getName());
      tvName.setText(itemsItem.getName());

      for (CustomAttributesItem customAttributesItem : itemsItem.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
          if (!TextUtils.isEmpty(customAttributesItem.getValue().toString())) {
            llDescription.setVisibility(View.VISIBLE);
            /*etvDescription.setText(customAttributesItem.getValue().toString());
            tvExcerpt.setText(
                Html.fromHtml(customAttributesItem.getValue().toString()).toString().trim());*/

            setTextViewHTML(etvDescription,customAttributesItem.getValue().toString());
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
            tvBenefits.setText(customAttributesItem.getValue().toString());
            setTextViewHTML(tvBenefits,customAttributesItem.getValue().toString());

          }
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
          imgList.add(URLUtils.formatImageURL(customAttributesItem.getValue().toString()));
        }else if(customAttributesItem.getAttributeCode().equalsIgnoreCase("select_display_category")){
          selectDisplayCatogry = (String) customAttributesItem.getValue();
        }
      }
      if(!selectDisplayCatogry.equalsIgnoreCase(""))
        tvCategory.setText(selectDisplayCatogry);
      else
        tvCategory.setText(category);

      vpg.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager(), imgList));
      fpi.setViewPager(vpg);

      presenter.getDetails(itemsItem.getSku());
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
//    etvTerms.collapse();

  }

  @OnClick({R.id.tvReserveNow,R.id.tv_make_a_reservation})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tvReserveNow:
        if(!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
          presenter.getDetailsItem(sku);
        }else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
          if (CommonUtils.isValidUrl(linkHGW)){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkHGW));
            startActivity(browserIntent);
          } else {
            CommonUtils.showToast(ShoppingDetailActivity.this,getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
          }
          //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
        }

        break;
      case R.id.tv_make_a_reservation :
        MakeAReservationDialogFragment fragment = MakeAReservationDialogFragment.newInstance(outletList,detailsItemFinal.getId(),detailsItemFinal.getName());
        fragment.show(getSupportFragmentManager(), MakeAReservationDialogFragment.TAG);
        break;
    }
  }

  @Override
  public void renderGetDetailToGoEstore(DetailsItem detailsItem) {
    presenter.getCategoryDefault(detailsItem);
  }

  @Override
  public void renderGotoEstore(DetailsItem detailsItem){
    EstoreDetailActivity.startMe(this,detailsItem,detailsItem.getCategoryName(),0);
  }

  @Override
  public void showErrorMsg(int msg) {
    Toast.makeText(ShoppingDetailActivity.this, getText(msg), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void renderGetOutletFailed() {
    isHGW = false;

    if (!ObjectUtil.isNull(reserveButtonTitle) && !ObjectUtil.isEmptyStr(reserveButtonTitle)){
      tvReserveNow.setText(reserveButtonTitle);
    }

    if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);

    } else if(isHGW){
      tvReserveNow.setVisibility(View.GONE);
      llMakeAReservation.setVisibility(View.VISIBLE);

    } else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);

    }else {
      tvReserveNow.setVisibility(View.GONE);
      llMakeAReservation.setVisibility(View.GONE);

    }
  }

  @Override
  public void renderGetOutletSuccess(List<OutletItem> outletItems) {
    outletList =  outletItems;
    if(outletItems!=null&&outletItems.size()>0){
      isHGW = true;
    }else {
      isHGW = false;
    }

    if (!ObjectUtil.isNull(reserveButtonTitle) && !ObjectUtil.isEmptyStr(reserveButtonTitle)){
      tvReserveNow.setText(reserveButtonTitle);
    }

    if (!ObjectUtil.isNull(sku) && !ObjectUtil.isEmptyStr(sku)){
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);
    } else if(isHGW){
      tvReserveNow.setVisibility(View.GONE);
      llMakeAReservation.setVisibility(View.VISIBLE);

    }else if (!ObjectUtil.isNull(linkHGW) && !ObjectUtil.isEmptyStr(linkHGW)){
      tvReserveNow.setVisibility(View.VISIBLE);
      llMakeAReservation.setVisibility(View.GONE);

    } else {
      tvReserveNow.setVisibility(View.GONE);
      llMakeAReservation.setVisibility(View.GONE);

    }

  }

  private void setupCategoryId(){
    String currentCategory = "";
    if(!selectDisplayCatogry.equalsIgnoreCase(""))
      currentCategory = selectDisplayCatogry;
    else
      currentCategory = category;


    if(currentCategory.equals("THE TIME")){
      category_id = "70";
    }else if(currentCategory.equals("THE DIRECT")){
      category_id = "56";
    }else if(currentCategory.equals("THE SELECTION")){
      category_id = "77";
    }else if(currentCategory.equals("Travel")){
      category_id = "24";
    }
    else if(currentCategory.equals("Hotels")){
      category_id = "57";
    }
    else if(currentCategory.equals("Hotels Plus")){
      category_id = "25";
    }
    else if(currentCategory.equals("Cruises & Trains")){
      category_id = "49";
    }
    else if(currentCategory.equals("Packages")){
      category_id = "50";
    }
    else if(currentCategory.equals("Services")){
      category_id = "53";
    }
    else if(currentCategory.equals("Wine & Dine")){
      category_id = "5";
    }
    else if(currentCategory.equals("American")){
      category_id = "17";
    }
    else if(currentCategory.equals("Asian")){
      category_id = "18";
    }
    else if(currentCategory.equals("Australian")){
      category_id = "44";
    }
    else if(currentCategory.equals("Chinese")){
      category_id = "43";
    }
    else if(currentCategory.equals("French")){
      category_id = "32";
    }
    else if(currentCategory.equals("Fusion")){
      category_id = "28";
    }
    else if(currentCategory.equals("Indian")){
      category_id = "46";
    }
    else if(currentCategory.equals("International")){
      category_id = "34";
    }
    else if(currentCategory.equals("Italian")){
      category_id = "29";
    }
    else if(currentCategory.equals("Japanese")){
      category_id = "42";
    }
    else if(currentCategory.equals("Mediterranean")){
      category_id = "33";
    }
    else if(currentCategory.equals("Mexican")){
      category_id = "80";
    }
    else if(currentCategory.equals("Modern European")){
      category_id = "30";
    }
    else if(currentCategory.equals("Western")){
      category_id = "31";
    }
    else if(currentCategory.equals("Wholesome")){
      category_id = "54";
    }
    else if(currentCategory.equals("Reservations")){
      category_id = "58";
    }
    else if(currentCategory.equals("Beverages")){
      category_id = "83";
    }
    else if(currentCategory.equals("Dessert")){
      category_id = "84";
    }
    else if(currentCategory.equals("German")){
      category_id = "86";
    }
    else if(currentCategory.equals("Shopping")){
      category_id = "4";
    }
    else if(currentCategory.equals("Fashion")){
      category_id = "10";
    }
    else if(currentCategory.equals("Floral")){
      category_id = "7";
    }
    else if(currentCategory.equals("Fragrance")){
      category_id = "37";
    }
    else if(currentCategory.equals("Gourmet")){
      category_id = "47";
    }
    else if(currentCategory.equals("Grocery")){
      category_id = "38";
    }
    else if(currentCategory.equals("Lifestyle & Décor")){
      category_id = "81";
    }
    else if(currentCategory.equals("Skin Care")){
      category_id = "48";
    }
    else if(currentCategory.equals("Wellness")){
      category_id = "6";
    }
    else if(currentCategory.equals("Aesthetics")){
      category_id = "12";
    }
    else if(currentCategory.equals("Enrichment Classes")){
      category_id = "13";
    }
    else if(currentCategory.equals("Gym")){
      category_id = "14";
    }
    else if(currentCategory.equals("Health")){
      category_id = "15";
    }
    else if(currentCategory.equals("Meditation & Mindfulness")){
      category_id = "36";
    }
    else if(currentCategory.equals("Spa")){
      category_id = "16";
    }
    else if(currentCategory.equals("Flash Sales ")){
      category_id = "23";
    }else {
      category_id = "23";
    }
  }

  private void setupRecyclerView() {
    recommendationAdapter = new RecommendationAdapter();
    rvRecommendation.setNestedScrollingEnabled(false);
    int spanCount = 2;
    int spacing = 8;
    rvRecommendation.addItemDecoration(
        new ProductGridSpacesItemDecoration(spanCount,
            MetricsUtils.convertDpToPixel(spacing, this),
            true));

    rvRecommendation.setLayoutManager(new GridLayoutManager(this, spanCount));

    rvRecommendation.setAdapter(recommendationAdapter);
    recommendationAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
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
      public void onItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        if (item.getPillarId().equalsIgnoreCase(Constants.E_STORE)) {
          EstoreDetailActivity.startMe(ShoppingDetailActivity.this, item, item.getCategoryName(),
              R_C_PRODUCT_DETAIL);
        } else if (item.getPillarId().equalsIgnoreCase(Constants.TRAVEL)) {
          TravelDetailActivity.startMe(ShoppingDetailActivity.this, item, item.getCategoryName(),
              true, R_C_PRODUCT_DETAIL);
        } else {
          ActivityChooser.startMe(ShoppingDetailActivity.this, item, item.getCategoryName(), false,
              R_C_PRODUCT_DETAIL);
        }
      }

      @Override
      public void onReserveButton(ItemsItem item, int position, TravelProductViewHolder holder) {

      }
    });
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
          isFavourite ? R.drawable.ic_favourite_fill_white : R.drawable.ic_favourite_stroke_white;
      cartIcon = R.drawable.ic_cart;
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
  public void injectPresenter(ShoppingDetailPresenter presenter) {
    super.injectPresenter(presenter);
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
//    etvTerms.toggle();
//    tvToogleTerms.setText(etvTerms.isExpanded() ? R.string.read_more : R.string.read_less);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    finish();
  }

  @OnClick(R.id.ivNavigation_toolbar_white)
  void onClickBackWhite() {
    finish();
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
  public void render(ShoppingDetailViewState viewState) {
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
            } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("hgw_link")){
              linkHGW = customAttributesItem.getValue().toString();
              Log.e("LOG_INFO", "linkHGW: " + linkHGW);
            }else if(customAttributesItem.getAttributeCode().equalsIgnoreCase("select_display_category")){
              selectDisplayCatogry =  customAttributesItem.getValue().toString();
            }

          }
        }
        if(!selectDisplayCatogry.equalsIgnoreCase(""))
          tvCategory.setText(selectDisplayCatogry);
        else
          tvCategory.setText(category);
        /*Handle display reserve button*/
        presenter.getOutletResevation(detailsItemFinal.getId().toString().trim());
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
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
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void showRecommendationList(List<ItemsItem> items) {
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

  @OnClick(R.id.iv_cart)
  public void onClickCartCount() {
    ShoppingCartActivity.start(this);
  }
  protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
  {
    int start = strBuilder.getSpanStart(span);
    int end = strBuilder.getSpanEnd(span);
    int flags = strBuilder.getSpanFlags(span);
    ClickableSpan clickable = new ClickableSpan() {
      public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(span.getURL()));
        ShoppingDetailActivity.this.startActivity(browserIntent);
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
