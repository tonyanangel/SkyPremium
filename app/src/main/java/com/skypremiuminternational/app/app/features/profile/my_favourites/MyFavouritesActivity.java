package com.skypremiuminternational.app.app.features.profile.my_favourites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.utils.Constants.MAX_FAV_COUNT;

public class MyFavouritesActivity extends LocationAwareActivity<MyFavouritesPresenter>
    implements MyFavouritesView<MyFavouritesPresenter> {

  private static final int R_C_PRODUCT_DETAIL = 909;
  private String[] productCategory =  new String[]{};
  private String[] productCategoryIds =  new String[]{};
  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rvMyFavourites)
  RecyclerView rv;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategoryFilter;
  @BindView(R.id.tvSort_filter)
  TextView tvSortFilter;
  @BindView(R.id.ahbn)
  AHBottomNavigation ahbn;
  @BindView(R.id.ll_empty)
  ViewGroup llEmpty;
  @BindView(R.id.layout_cart_icon)
  ViewGroup layoutCart;
  @BindView(R.id.ivSearch_toolbar)
  ImageView ivSearch;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @Inject
  ErrorMessageFactory errorMessageFactory;
  private FavouriteAdapter favouriteAdapter;
  private FavouritePartnerAdapter favouritePartnerAdapter;
  private String selectedCategoryID = null;
  private int selectedSorting = 0;
  private ProgressDialog progressDialog;
  private String partnerType = null;
  private String[] partnerCategory = new String[]{};
  private String[] partnerCategoryIds = new String[]{};

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, MyFavouritesActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  public static void startMe(Context context, int deepLink) {
    Intent intent = new Intent(context, MyFavouritesActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra(Constants.DEEP_LINK_LANDING, deepLink);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_favourites);
    ButterKnife.bind(this);
    tvTitle_toolbar.setText(getResources().getString(R.string.my_favourite));
    tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrFav[selectedSorting]));
    tvCategoryFilter.setText(String.format("Refine: %s", "All"));
    layoutCart.setVisibility(View.VISIBLE);
    ivSearch.setVisibility(View.INVISIBLE);
    setUpTab();
    presenter.getNumberFav();

    ahbn.setTitleTextSizeInSp(9, 9);
    ahbn.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));
    ahbn.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
    ahbn.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    ahbn.setInactiveColor(Color.parseColor("#C0B8B6"));

    AHBottomNavigationItem item1 =
        new AHBottomNavigationItem(R.string.travel_all_caps, R.drawable.ic_travel_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item2 =
        new AHBottomNavigationItem(R.string.wine_all_caps, R.drawable.ic_wine_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item3 =
        new AHBottomNavigationItem(R.string.home_all_caps, R.drawable.ic_logo_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item4 =
        new AHBottomNavigationItem(R.string.shopping_all_caps, R.drawable.ic_shopping_tab,
            R.color.colorAccent);
    AHBottomNavigationItem item5 =
        new AHBottomNavigationItem(R.string.wellness_all_caps, R.drawable.ic_wellness_tab,
            R.color.colorAccent);

    ahbn.addItem(item1);
    ahbn.addItem(item2);
    ahbn.addItem(item3);
    ahbn.addItem(item4);
    ahbn.addItem(item5);

    ahbn.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
      @Override
      public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected && position != -1) {
          formConfig(position);
        }
        return true;
      }
    });
    ahbn.setCurrentItem(-1);

    //deeplink = getIntent().getIntExtra(Constants.DEEP_LINK_LANDING, 2);
    //ahbn.setCurrentItem(deeplink, true);
    //if (deeplink == 0) {
    //  formConfig(0);
    //}

    setUpRecyclerView();
    presenter.getCartCount();
    presenter.setRequest(selectedCategoryID, partnerType, getSelectedSortField(),
        Constants.sortDirectionFav[selectedSorting]);

    presenter.getCategory();
  }

  private void setUpTab() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.label_partners));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.label_products));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        selectedCategoryID = null;
        tvCategoryFilter.setText(String.format("Refine: %s", "All"));
        partnerType = tab.getPosition() == 0 ? Constants.FAVOURITE_PARTNER_TYPE_PARTNERS
            : Constants.FAVOURITE_PARTNER_TYPE_PRODUCTS;
        fetchFavourites();
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
    partnerType =
        tabLayout.getSelectedTabPosition() == 0 ? Constants.FAVOURITE_PARTNER_TYPE_PARTNERS
            : Constants.FAVOURITE_PARTNER_TYPE_PRODUCTS;
  }

  private void fetchFavourites() {
    presenter.setRequest(selectedCategoryID, partnerType, getSelectedSortField(),
        Constants.sortDirectionFav[selectedSorting]);
    presenter.getFavourites();
  }

  @Inject
  @Override
  public void injectPresenter(MyFavouritesPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  @Override
  public void showLoading() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
      progressDialog.setCancelable(false);
      progressDialog.setMessage(getString(R.string.message_loading_favourites));
    }
    if (!isFinishing() && !progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  @Override
  public void updateViewCount(String count) {
    if (count == null && TextUtils.isEmpty(count)) {
      lyCartCount.setVisibility(View.INVISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
    } else {
      if (!count.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCount.setText(count);
      } else {
        lyCartCount.setVisibility(View.INVISIBLE);
        tvCartCount.setVisibility(View.INVISIBLE);
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void hideLoading() {
    if (!isFinishing()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.tvCategory_filter)
  void onClickCategory() {
    boolean isPartners = tabLayout.getSelectedTabPosition() == 0;
    final String[] categoryIDArr = isPartners ? partnerCategoryIds : productCategoryIds;
    final String[] categoryArr = isPartners ? partnerCategory : productCategory;

    new AlertDialog.Builder(this).setTitle("REFINE: ")
        .setItems(categoryArr, (dialog, item) -> {
          selectedCategoryID = categoryIDArr[item];
          tvCategoryFilter.setText(String.format("Refine: %s", categoryArr[item]));
          fetchFavourites();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @OnClick(R.id.tvSort_filter)
  void onClickSort() {

    new AlertDialog.Builder(this).setTitle("SORT BY:")
        .setItems(Constants.sortingArrFav, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            selectedSorting = item;
            tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrFav[item]));
            fetchFavourites();
          }
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @Override
  public void render(MyFavouritesViewState viewState) {
    setUpRecyclerView();
    if (viewState.error() != null) {
      showToast(viewState.error().getLocalizedMessage());
      return;
    }
    if (viewState.category() != null) {
      setCategoryPartner(viewState.category());
      setCategoryProduct(viewState.category());

    }
    if (viewState.myFavourites() != null && viewState.myFavourites().size() > 0) {
      llEmpty.setVisibility(View.GONE);
      rv.setVisibility(View.VISIBLE);
      if(partnerType!=null && partnerType==Constants.FAVOURITE_PARTNER_TYPE_PARTNERS){
        favouritePartnerAdapter.set(viewState.myFavourites());
      }else {
        favouriteAdapter.set(viewState.myFavourites());
      }
    }
    if (viewState.myFavourites() != null && viewState.myFavourites().size() == 0) {
      llEmpty.setVisibility(View.VISIBLE);
      rv.setVisibility(View.GONE);
      if(partnerType!=null && partnerType==Constants.FAVOURITE_PARTNER_TYPE_PARTNERS){
        favouritePartnerAdapter.set(viewState.myFavourites());
      }else {
        favouriteAdapter.set(viewState.myFavourites());
      }
    }
    /*if (viewState.totalCount() != null) {
      String title = getString(R.string.my_favourite) + " ("
          + viewState.totalCount()
          + "/"
          + MAX_FAV_COUNT
          + ")";
      tvTitle_toolbar.setText(title);
    }*/
  }

  @Override
  public void renderCountItem(int count) {
    String title = getString(R.string.my_favourite) + " ("
        + count
        + "/"
        + MAX_FAV_COUNT
        + ")";
    tvTitle_toolbar.setText(title);
  }

  @Override
  public void goToDetail(DetailsItem detailsItem, FavouriteListResponse item) {
    detailsItem.isFavourite = true;
    if (item.getPillarId().equals(Constants.E_STORE)) {
      EstoreDetailActivity.startMe(this, detailsItem, item.getCategoryName(), R_C_PRODUCT_DETAIL);
    } else if (item.getPillarId().equals(Constants.TRAVEL)) {
      TravelDetailActivity.startMe(this, detailsItem, item.getCategoryName(), true,
          R_C_PRODUCT_DETAIL);
    } else {
      String lat = "";
      String lng = "";
      for (CustomAttribute customAttribute : detailsItem.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("latitude")) {
          lat = (String) customAttribute.getValue();
        }
        if (customAttribute.getAttributeCode().equals("longitude")) {
          lng = (String) customAttribute.getValue();
        }
      }
      if (TextUtils.isEmpty(lat) && TextUtils.isEmpty(lng)) {
        ShoppingDetailActivity.startMe(this, detailsItem, item.getCategoryName(),
            R_C_PRODUCT_DETAIL);
      } else {
        TravelDetailActivity.startMe(this, detailsItem, item.getCategoryName(), false,
            R_C_PRODUCT_DETAIL);
      }
    }
  }

 /* private void setCategory(CategoryResponse category) {
    if (partnerCategory != null
        && partnerCategory.length == category.getChildrenData().size() + 1) {
      return; //already set
    }
    partnerCategory = new String[category.getChildrenData().size() + 1];
    partnerCategoryIds = new String[category.getChildrenData().size() + 1];
    for (int i = 0; i < partnerCategory.length; i++) {
      if (i == 0) {
        partnerCategoryIds[i] = null;
        partnerCategory[i] = "All";
      } else {
        partnerCategoryIds[i] = String.valueOf(category.getChildrenData().get(i - 1).getId());
        partnerCategory[i] = category.getChildrenData().get(i - 1).getName();
      }
    }
  }*/
  private void setCategoryPartner(CategoryResponse category) {
    int countActive = 0;
    for (ChildData item : category.getChildrenData()) {
      if (item.getIsActive() && isInLimitRefine(item.getId()) ) {
        countActive++;
      }
    }
    if (partnerCategory != null
        && partnerCategory.length == countActive + 1) {
      return; //already set
    }

    if (countActive <= 0) {
      return;
    }
    List<String> partnerCategoryTemp = new ArrayList<>();
    List<String> partnerCategoryIdsTemp = new ArrayList<>();

    for (ChildData childData : category.getChildrenData()) {
      if (childData.getIsActive()&& isInLimitRefine(childData.getId())) {
        partnerCategoryTemp.add(childData.getName());
        partnerCategoryIdsTemp.add(childData.getId().toString());
      }
    }
    partnerCategoryIdsTemp.add(0,null);
    partnerCategoryTemp.add(0,"All");

    partnerCategory = partnerCategoryTemp.toArray(new String[countActive + 1]);
    partnerCategoryIds = partnerCategoryIdsTemp.toArray(new String[countActive + 1]);
  }

  boolean isInLimitRefine(Integer id){
    switch (id){
      case 24 : return true; // travel
      case 4  : return true; // Shopping
      case 5  : return true; // Wine & Dine
      case 6  : return true; // Wellness
    }

    return false;
  }


  private void setCategoryProduct(CategoryResponse category) {
    int countActive = 0;
    ChildData estoreData = new ChildData();
    for (ChildData item : category.getChildrenData()) {
      if (item.getId() == 55) {
        estoreData = item;
        for (ChildData_ item2 : estoreData.getChildrenData()) {
          if (item2.getIsActive()) {
            countActive++;
          }
        }
      }
    }



    if (productCategory != null
        && productCategory.length == countActive + 1) {
      return; //already set
    }

    if (countActive <= 0) {
      return;
    }
    List<String> productCategoryTemp = new ArrayList<>();
    List<String> productCategoryIdsTemp = new ArrayList<>();

    for (ChildData_ childData : estoreData.getChildrenData()) {
      if (childData.getIsActive()) {
        productCategoryTemp.add(childData.getName());
        productCategoryIdsTemp.add(childData.getId().toString());
      }
    }

    productCategoryTemp.add(0,"All");
    productCategoryIdsTemp.add(0,null);

    productCategory = productCategoryTemp.toArray(new String[countActive + 1]);
    productCategoryIds = productCategoryIdsTemp.toArray(new String[countActive + 1]);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (Activity.RESULT_OK == resultCode && R_C_PRODUCT_DETAIL == requestCode) {
      boolean isStillFavourite = data.getBooleanExtra("fav_status", true);
      if (!isStillFavourite) {
        presenter.getFavourites();
      }
    }
  }

  private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  void setUpRecyclerView() {
    int spanCount = 2;
    int spacing = 8;
    boolean includeEdge = false;
    //20200415 WIKI Toan Tran  - disable spacing on code Java
   /* rv.addItemDecoration(
        new ProductGridSpacesItemDecoration(spanCount, MetricsUtils.convertDpToPixel(spacing, this),
            includeEdge));*/
    rv.setLayoutManager(new GridLayoutManager(this, spanCount));

/*<<START>> WIKI Toan Tran */
    if(partnerType!=null && partnerType==Constants.FAVOURITE_PARTNER_TYPE_PARTNERS){
      favouritePartnerAdapter = new FavouritePartnerAdapter();
      favouritePartnerAdapter.setActionListener(new FavouritePartnerAdapter.ActionListener() {
        @Override
        public void onClickedItem(FavouriteListResponse item) {
          presenter.getDetails(item.getProduct().getSku(), item);
        }

        @Override
        public void onClickedFavourite(FavouriteListResponse item) {
          presenter.removeFromFav(item.getWishlistItemId());
        }
      });
      rv.setAdapter(favouritePartnerAdapter);
    }else if(partnerType!=null && partnerType==Constants.FAVOURITE_PARTNER_TYPE_PRODUCTS){
      favouriteAdapter = new FavouriteAdapter();
      favouriteAdapter.setActionListener(new FavouriteAdapter.ActionListener() {
        @Override
        public void onClickedItem(FavouriteListResponse item) {
          presenter.getDetails(item.getProduct().getSku(), item);
        }
        @Override
        public void onClickedFavourite(FavouriteListResponse item) {
          presenter.removeFromFav(item.getWishlistItemId());
        }
      });
      rv.setAdapter(favouriteAdapter);
    }
  }

  @OnClick(R.id.layout_cart_icon)
  void onClickCart() {
    ShoppingCartActivity.start(this);
  }

  @OnClick(R.id.ivSearch_toolbar)
  void onClickSearch() {
    SearchActivity.startMe(this);
  }

  private void formConfig(int position) {
    LandingActivity.startMe(this, position);
  }

  private String getSelectedSortField() {
    String sortField = Constants.sortFieldFav[selectedSorting];

    if (Constants.sortingArrFav[selectedSorting].equals("Location")) {
      sortField = Constants.sortFieldFav[selectedSorting];
      if (this.location != null) {
        sortField += location.getLatitude() + "@" + location.getLongitude();
      } else {
        sortField += "0@0";
      }
    }
    return sortField;
  }

  @OnClick(R.id.btn_estore)
  void onClickedEstore() {
    EstoreActivity.startMe(this);
  }
}
