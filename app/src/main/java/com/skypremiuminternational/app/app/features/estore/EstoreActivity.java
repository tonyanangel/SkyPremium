package com.skypremiuminternational.app.app.features.estore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.SeekBar;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.filter.adapter.FilterAdapter;
import com.skypremiuminternational.app.app.features.estore.filter.adapter.IOnClickItemFilterAdapter;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.model.ListParamsFilter;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.HorizontalSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.LoadJSONFromAssets;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.view.AutoResizeTextView;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.data.network.request.ProductFilterSortRequest;
import com.skypremiuminternational.app.data.network.service.SearchProductService;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.category.FilterListResponse;
import com.skypremiuminternational.app.domain.models.category.TreeItem;
import com.skypremiuminternational.app.domain.models.category.treeCate.JSONObjectPlus;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class EstoreActivity extends LocationAwareActivity<EstorePresenter>
        implements EstoreView<EstorePresenter> , IOnClickItemFilterAdapter, IOnClickAddToCart, IOnClickBuyNow{

  private static final int R_C_PRODUCT_DETAIL = 1234;
  private static final int SIZE_PAGE = 30;
  private static int CURRENT_PAGE = 1;
  private static int MAX_COUNT=0;

  public static long TIME_COUNT_LOAD_DATA = System.currentTimeMillis();
  public static long TIME_COUNT_RENDER_UI = System.currentTimeMillis();
  public static boolean flag = false;

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rvFeatured)
  RecyclerView rvFeatured;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategory;
  @BindView(R.id.tvSort_filter)
  TextView tvSort;
  @BindView(R.id.srl)
  SwipyRefreshLayout srl;
  @BindView(R.id.rv)
  RecyclerView rv;
  @BindView(R.id.ivBanner)
  ImageView ivBanner;
  @BindView(R.id.tvHeading)
  TextView tvHeading;
  @BindView(R.id.tvSubHead)
  TextView tvSubHead;
  @BindView(R.id.rlFeatureProduct)
  RelativeLayout rl;
  @BindView(R.id.ahbn)
  AHBottomNavigation ahbn;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.tv_comingsoon)
  AutoResizeTextView tv_comingsoon;
  @BindView(R.id.layout_filter_popup)
  FrameLayout layout_filter_popup;

//  @BindView(R.id.btn_closeestorefilterdialog)
////  Button btn_closeestorefilterdialog;
//  @BindView(R.id.layout_content_filter)
//  ViewGroup layoutContentFilter;


  String from ;
  String pillar ;
  String sku ;
  String sortBy ;
  static String filterOption;
  static boolean isFlagFilter = false;


  @Inject
  ErrorMessageFactory errorMessageFactory;
  private EstoreAdapter estoreAdapter;

  /* 20200313 -  WIKI Toan Tran - disable feature product*/
  //private FeaturedEstoreAdapter featuredEstoreAdapter;

  private ProgressDialog progressDialog;

  FilterAdapter adapter;
  RecyclerView recyclerFilter;
  private List<ChildData_> categoryList;
  private String[] categoryArr;
  private int selectedSorting;
  private String selectedCategoryID = Constants.E_STORE;
  private ItemsItem clickedItem;
  private boolean isFeaturedItemClick = false;
  private int clickedPosition = 0;
  private int start = 0;
  private int stop = SIZE_PAGE;
  private List<ItemsItem> Listloading = new ArrayList<>();
  private List<ItemsItem> Listloading1 = new ArrayList<>();
  private List<ItemsItem> Listloading2 = new ArrayList<>();

  private List<TreeItem> treeFilter = new ArrayList<>();
  private List<TreeItem> treeFilterMini = new ArrayList<>();
  private List<JSONObjectPlus> listJSObj = new ArrayList<>();
  private static boolean filterLoaded = false;
  ListParamsFilter listParamsFilter ;
  private static String PARAMS_FILTER;
  private  String[] arrayFilterOption;

  public static void startMe(Context context) {
    Intent intent = new Intent(context, EstoreActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void startMe(Context context,String from,String filter,String sortBy) {
    Intent intent = new Intent(context, EstoreActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("filter",filter);
    intent.putExtra("from",from);
    intent.putExtra("sortBy",sortBy);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_estore);
    ButterKnife.bind(this);
    getIntentParam();
    filterOption = getIntent().getStringExtra("filter");
    if(filterOption!=null){
      arrayFilterOption = filterOption.trim().split(",");
    }
    filterLoaded = false;

    tvTitle_toolbar.setText(getResources().getString(R.string.e_store));
    setUpProgressDialog();
    setupFooterBar();
    setUpRecyclerView();

    srl.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
    srl.setDistanceToTriggerSync(10);

    if(!TextUtils.isEmpty(sortBy)){
      selectedSorting = getSelectedSortingIxdex(sortBy);
      tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEstore[selectedSorting]));
    }else {
      tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEstore[selectedSorting]));
    }

    tvCategory.setText(String.format("FILTER OPTIONS", ""));

    listParamsFilter = new ListParamsFilter();
    /*20200317 WIKI Toan Tran - Disable page size*/
    listParamsFilter.addSizePage(SIZE_PAGE);
    //presenter.getCategoryDetails(selectedCategoryID);
    presenter.getCartCount();
    presenter.getRatingOption();
    recyclerFilter = (RecyclerView)findViewById(R.id.recycler_filter);

    listParamsFilter.addRoot(Constants.E_STORE);
    String query ="";
    CURRENT_PAGE=1;
    query = listParamsFilter.string(CURRENT_PAGE);
    //presenter.getProductFilter(query);
    ProductFilterSortRequest request = new ProductFilterSortRequest();
    request.setStrFilter(query);
    request.setField(getSelectedSortField());
    request.setDirection(Constants.sortDirectionEstore[selectedSorting]);
    request.setPostionGroup(listParamsFilter.size());
    TIME_COUNT_LOAD_DATA = System.currentTimeMillis();
    flag =false;


    if(TextUtils.isEmpty(filterOption)){
      showLoading("Loading ...");
      presenter.getCategoryDetails(selectedCategoryID,request);
      presenter.getTreeCateStringJson(listParamsFilter.string(1));
    }else {
      showLoading("Loading ...");
      presenter.getCategoryDetails(selectedCategoryID);
      //presenter.getProductWithFilterLocal();
      presenter.getProductWithFilterMini(listParamsFilter.string(1));
    }


    //presenter.getTreeCateStringJson(listParamsFilter.string(1));
  }

  private int getSelectedSortingIxdex(String  sortBy) {
      switch (sortBy){
        case "popularity_order" :{
          return 0;
        }
        case "rating" :{
          return 1;
        }
        case "nameaz" :{
          return 2;
        }
        case "nameza" :{
          return 3;
        }
        case "pricelowtohigh" :{
          return 4;
        }
        case "pricehightolow" :{
          return 5;
        }
        case "latest" :{
          return 6;
        }
        case "earliest" :{
          return 7;
        }
        default:{
          return 0;

        }
      }

  }


  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void setUpProgressDialog() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
  }

  private void setupFooterBar() {
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
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

     /* Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){


    /*  Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  private void setUpRecyclerView() {

    HorizontalSpacesItemDecoration horizontalSpacesItemDecoration =
            new HorizontalSpacesItemDecoration(MetricsUtils.convertDpToPixel(8, this), false);

    /* <<START>> 20200313 WIKI Toan Tran - Disable feature product */
    //============ FEATURE PRODUCTS
    /*rvFeatured.addItemDecoration(horizontalSpacesItemDecoration);
    rvFeatured.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    featuredEstoreAdapter = new FeaturedEstoreAdapter();
    featuredEstoreAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        isFeaturedItemClick = true;
        clickedItem = item;
        //ActivityChooser.startMe(EstoreActivity.this, item, item.getCategoryName(), false,
        //R_C_PRODUCT_DETAIL);
        EstoreDetailActivity.startMe(EstoreActivity.this, item, item.getCategoryName(),
                R_C_PRODUCT_DETAIL);
      }

      @Override
      public void onFavItemClicked(ItemsItem item, int position) {
        if (item.isFavourite()) {
          presenter.removeFromFavourite(item, true, position);
        } else {
          presenter.addToFavourite(item, true, position);
        }
      }
    });
    rvFeatured.setAdapter(featuredEstoreAdapter);
    */
    //============ FEATURE PRODUCTS
    /* <<END>> 20200313 WIKI Toan Tran - Disable feature product */


    int spanCount = 2;
    int spacing = 8;
    rv.addItemDecoration(
            new ProductGridSpacesItemDecoration(spanCount,
                    MetricsUtils.convertDpToPixel(spacing, this),
                    true));

    rv.setLayoutManager(new GridLayoutManager(this, spanCount));

    estoreAdapter = new EstoreAdapter(this,this);
    estoreAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        isFeaturedItemClick = false;
        clickedPosition = position;
        clickedItem = item;
        //ActivityChooser.startMe(EstoreActivity.this, item, item.getCategoryName(), false,
        //    R_C_PRODUCT_DETAIL);
        EstoreDetailActivity.startMe(EstoreActivity.this, item, item.getCategoryName(),
                R_C_PRODUCT_DETAIL);
      }

      @Override
      public void onFavItemClicked(ItemsItem item, int position) {
        if (item.isFavourite()) {
          presenter.removeFromFavourite(item, false, position);
        } else {
          presenter.addToFavourite(item, false, position);
        }
      }

      @Override
      public void onReserveButton(ItemsItem item, int position, TravelProductViewHolder holder) {

      }
    });


    rv.setAdapter(estoreAdapter);
  }

  @Inject
  @Override
  public void injectPresenter(EstorePresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  @Override
  public void notifyFavStatusChanged(boolean featuredItem, int position) {
    if (featuredItem) {

      /* 20200313 -  WIKI Toan Tran - disable feature product*/
      //featuredEstoreAdapter.notifyItemChanged(position);
    } else {
      estoreAdapter.notifyItemChanged(position);
    }
  }

  @Override
  public void updateCartCount(String count) {
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
  public void showLoading(String message) {
    if (!isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (!isDestroyed() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void render(Throwable error) {
    Timber.e(error);
    Toast.makeText(this, errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void render(CategoryDetailsResponse categoryDetailsResponse) {
    for (CategoryDetailsResponse.CustomAttribute customAttributesItem : categoryDetailsResponse.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
        tvSubHead.setText(Html.fromHtml(customAttributesItem.getValue().toString()));
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.centerCrop();
        requestOptions.dontAnimate();
        requestOptions.error(R.drawable.white);
        Glide.with(this)
                .load(URLUtils.bannerImageURL(customAttributesItem.getValue().toString()))
                .apply(requestOptions)
                .into(ivBanner);
      }
    }
    tvHeading.setText(categoryDetailsResponse.getName());
  }

  @Override
  public void render(CategoryResponse category) {

    /* <<START>> 20200313 - WIKI Toan Tran - Disable old version code */
    List<ChildData> childDataList = category.getChildrenData();
    for (ChildData childData : childDataList) {
      if (childData.getId() == Integer.parseInt(Constants.E_STORE)) {

        categoryList = childData.getChildrenData();
        Collections.sort(categoryList,
                (childData_1, childData_2) -> childData_1.getPosition()
                        .compareTo(childData_2.getPosition()));

        categoryArr = new String[categoryList.size() + 1];
        categoryArr[0] = "All";

        for (int i = 1; i <= categoryList.size(); i++) {
          categoryArr[i] = categoryList.get(i - 1).getName();
        }
        Log.d("CategoryArr","CategoryArr");
      }
    }
    /* <<END>> 20200313 - WIKI Toan Tran - Disable old version code */
  }

  @OnClick(R.id.tvCategory_filter)
  void onClickCategory() {
    layout_filter_popup.setVisibility(View.VISIBLE);
    if(!filterLoaded) {
      progressDialog.setMessage("Getting Filter ...");
      progressDialog.show();
    }
  }

  /**
   * 20200229 WIKI Toan Tran - create list tree filter
   * @param jsonObject
   * @throws JSONException
   */
  void createTree(JSONObjectPlus jsonObject) throws JSONException {

    TreeItem item = new TreeItem();
    Iterator<String> iter= jsonObject.getObj().keys();

    while (iter.hasNext()){
      String key = iter.next();
      if(jsonObject.getObj().get(key) instanceof JSONObjectPlus){

      }else  if(jsonObject.getObj().get(key) instanceof JSONArray){
      }else {
        parceToTreeItem(key ,jsonObject.getObj().get(key).toString(),item);
      }
    }
    item.setIdObj(""+jsonObject.getId());
    treeFilter.add(item);
  }

  void createTreeMini(JSONObjectPlus jsonObject) throws JSONException {

    TreeItem item = new TreeItem();
    Iterator<String> iter= jsonObject.getObj().keys();

    while (iter.hasNext()){
      String key = iter.next();
      if(jsonObject.getObj().get(key) instanceof JSONObjectPlus){

      }else  if(jsonObject.getObj().get(key) instanceof JSONArray){
      }else {
        parceToTreeItem(key ,jsonObject.getObj().get(key).toString(),item);
      }
    }
    item.setIdObj(""+jsonObject.getId());
    treeFilterMini.add(item);
  }

  /**
   * 20200229 WIKI Toan Tran -  Create data filter
   * @param string
   * @throws JSONException
   */
  void createFilter(String string) throws JSONException {
    Object object = new JSONTokener(string).nextValue();
    if(object instanceof JSONObject){
      JSONObject jsonObject = (JSONObject)object;
      Iterator<String> iter= jsonObject.keys();
      String tempKey = "";
      while (iter.hasNext()){
        String key = iter.next();
        if(jsonObject.get(key) instanceof JSONObject){

          JSONObjectPlus obj  = new JSONObjectPlus();
          obj.setObj((JSONObject) jsonObject.get(key));
          try {
            Integer.parseInt(key);
            obj.setId(key);
          }catch (NumberFormatException ex){
            obj.setId("");
          }
          listJSObj.add(obj);
          createFilter(jsonObject.get(key).toString());

        }else if(jsonObject.get(key) instanceof JSONArray){
          JSONArray array = new JSONArray();
          array = (JSONArray) jsonObject.get(key);
          for(int i  = 0;i <array.length();i++){
            JSONObjectPlus obj2 = new JSONObjectPlus();
            obj2.setObj((JSONObject) array.get(i));
            try {
              Integer.parseInt(key);
              obj2.setId(key);
            }catch (NumberFormatException ex){
              obj2.setId("");
            }
            listJSObj.add(obj2);
            createFilter(((JSONObject)array.get(i)).toString());
          }
        }
      }
    }else {

    }
  }

  /**
   * 20200229 WIKI Toan Tran -  Create data filter
   * @param string
   * @throws JSONException
   */
  void createFilterMini(String string) throws JSONException {
    Object object = new JSONTokener(string).nextValue();
    if(object instanceof JSONObject){
      JSONObject jsonObject = (JSONObject)object;
      Iterator<String> iter= jsonObject.keys();
      String tempKey = "";
      while (iter.hasNext()){
        String key = iter.next();
        if(jsonObject.get(key) instanceof JSONObject){

          JSONObjectPlus obj  = new JSONObjectPlus();
          obj.setObj((JSONObject) jsonObject.get(key));
          try {
            Integer.parseInt(key);
            obj.setId(key);
          }catch (NumberFormatException ex){
            obj.setId("");
          }
          listJSObj.add(obj);
          createFilterMini(jsonObject.get(key).toString());

        }else if(jsonObject.get(key) instanceof JSONArray){
          JSONArray array = new JSONArray();
          array = (JSONArray) jsonObject.get(key);
          for(int i  = 0;i <array.length();i++){
            JSONObjectPlus obj2 = new JSONObjectPlus();
            obj2.setObj((JSONObject) array.get(i));
            try {
              Integer.parseInt(key);
              obj2.setId(key);
            }catch (NumberFormatException ex){
              obj2.setId("");
            }
            listJSObj.add(obj2);
            createFilterMini(((JSONObject)array.get(i)).toString());
          }
        }
      }
    }else {

    }
  }


  /**
   * 20200229 WIKI Toan Tran -  covert field to object TreeItem
   * @param key
   * @param value
   * @param item
   */
  void parceToTreeItem(String key , String value,TreeItem item){
    switch(key){
      case "filter_label":{
        item.setFilterLabel(value);
        break;
      }
      case "filter_type":{
        item.setFilterType(value);
        break;
      }
      case "tree":{
        item.setTree(value);
        break;
      }
      case "attribute_id":{
        item.setAttributeId(value);
        break;
      }
      case "name":{
        item.setName(value);
        break;
      }
      case "maximum_price":{
        item.setMaximumPrice(value);
        break;
      }
      case "minimum_price":{
        item.setMinimumPrice(value);
        break;
      }
      case "level":{
        item.setLevel(value);
        break;
      }
      case "options":{
        item.setOptions(value);
        break;
      }
      case "is_active":{
        item.setIsActive(value);
        break;
      }
      case "position":{
        item.setPosition(value);
        break;
      }
      case "children_data":{
        item.setChildrenData(value);
        break;
      }
      case "label":{
        item.setLabel(value);
        break;
      }
      case "attribute_code":{
        item.setAttributeCode(value);
        break;
      }
      case "category_id":{
        item.setCategoryId(value);
        break;
      }
      case "product_count":{
        item.setProductCount(value);
        break;
      }
      case "parent_id":{
        item.setParentId(value);
        break;
      }
    }

  }

  @OnClick(R.id.tvSort_filter)
  void onClickSort() {

    /*new AlertDialog.Builder(this).setTitle("SORT BY:")
            .setItems(Constants.sortingArrEstore, (dialog, item) -> {
              selectedSorting = item;
              tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEstore[item]));
              String isthetime = "";
              if(selectedCategoryID.contains("70")){
                isthetime = "true";
              }else{
                isthetime = "false";
              }
              estoreAdapter.setData(null);
              presenter.getProductList(selectedCategoryID, getSelectedSortField(),
                      Constants.sortDirectionEstore[selectedSorting],isthetime);
            })
            .setNegativeButton("Cancel", null)
            .show();*/

    new AlertDialog.Builder(this).setTitle("SORT BY:")
            .setItems(Constants.sortingArrEstore, (dialog, item) -> {
              selectedSorting = item;
              tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEstore[item]));
              String isthetime = "";
              if(selectedCategoryID.contains("70")){
                isthetime = "true";
              }else{
                isthetime = "false";
              }
              estoreAdapter.setData(null);
              /*<<START>> - WIKI Toan Tran - add params to sort  */
              String query ="";
              CURRENT_PAGE=1;
              query = listParamsFilter.string(CURRENT_PAGE);
              //presenter.getProductFilter(query);
              ProductFilterSortRequest request = new ProductFilterSortRequest();
              request.setStrFilter(query);
              request.setField(getSelectedSortField());
              request.setDirection(Constants.sortDirectionEstore[selectedSorting]);
              request.setPostionGroup(listParamsFilter.size());
              presenter.getProductFilter(request);
              /*<<START>> - WIKI Toan Tran - add params to sort  */
            })
            .setNegativeButton("Cancel", null)
            .show();
  }

  @Override
  public void render(FeatureProduct featureProduct) {
    //Disable features product

//    rvFeatured.setVisibility(View.VISIBLE);
//    featuredEstoreAdapter.setData(featureProduct.getFeatureItems());
//    featuredEstoreAdapter.notifyDataSetChanged();
  }


  @Override
  public void render(ProductListResponse productList) {

    if(productList.getItems().size() > 0){
      tv_comingsoon.setVisibility(View.GONE);
    }else{
      tv_comingsoon.setVisibility(View.VISIBLE);
    }
    if(productList.getItems().size() > 0 ){
      Listloading = productList.getItems();

    }
    Log.e("TOANNA-MAXCOUNT",":"+productList.getItems().size());
    if(productList.getItems().size() >= SIZE_PAGE) {
      estoreAdapter.setData(productList.getItems().subList(0, SIZE_PAGE));
    }else if(productList.getItems().size() < SIZE_PAGE && productList.getItems().size() >0){
      estoreAdapter.setData(productList.getItems());
    }else{
      estoreAdapter.setData(productList.getItems());
    }

    if(productList.getItems().size() > 0 ) {
      srl.setEnabled(true);
      srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
          if (direction == SwipyRefreshLayoutDirection.TOP) {

          } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            /*<<START>> 20200313 - WIKI Toan Tran - Disable old version lazyloading*/
            /*new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {


                Listloading1 = Listloading.subList(start, stop);

                Listloading2 = Listloading;
                stop = stop + 30;
                if (Listloading2.size() < stop) {
                  stop = Listloading2.size();
                  if (stop == (Listloading2.size())) {
                    //Toast.makeText(getApplicationContext(),"There are")
                  }
                  //Listloading1 = Listloading.subList(start,stop);
                } else {
                  // Listloading1 = Listloading.subList(start,stop);
                }

                if (Listloading2.size() > 0) {
                  Listloading1 = Listloading2.subList(start, stop);
                  estoreAdapter.setData(Listloading1);

                }


                srl.setRefreshing(false);
              }
            }, 2000);*/
            /*<<END>> 20200313 - WIKI Toan Tran - Disable old version lazyloading*/


            /*<<START>> 20200313 - WIKI Toan Tran - enable new version lazyloading*/
            presenter.getProductFilter(listParamsFilter.string(CURRENT_PAGE));
            Listloading1 = Listloading.subList(start, stop);
            Listloading2 = Listloading;
            stop = stop + SIZE_PAGE;
            if (Listloading2.size() < stop) {
              stop = Listloading2.size();
              if (stop == (Listloading2.size())) {
                //Toast.makeText(getApplicationContext(),"There are")
              }
              //Listloading1 = Listloading.subList(start,stop);
            } else {
              // Listloading1 = Listloading.subList(start,stop);
            }

            if (Listloading2.size() > 0) {
              Listloading1 = Listloading2.subList(start, stop);
              estoreAdapter.setData(Listloading1);
            }
            srl.setRefreshing(false);
            /*<<END>> 20200313 - WIKI Toan Tran - enable new version lazyloading*/
          }
        }
      });
    }else{
      srl.setEnabled(false);
    }



  }

  /**
   * 20200229 WIKI Toan Tran - render list after apply filter
   * @param productList
   */
  @Override
  public void renderFilter(ProductListResponse productList) {


    flag =true;
    TIME_COUNT_LOAD_DATA = System.currentTimeMillis() - TIME_COUNT_LOAD_DATA;
    Log.d("COUNTTIME_DATA_API" , "Time : "+TIME_COUNT_LOAD_DATA+" ms");

    TIME_COUNT_RENDER_UI = System.currentTimeMillis();
    MAX_COUNT = productList.getTotalCount();
    layout_filter_popup.setVisibility(View.GONE);

    if(productList.getItems().size() > 0){
      tv_comingsoon.setVisibility(View.GONE);
    }else{
      tv_comingsoon.setVisibility(View.VISIBLE);
    }
    if(productList.getItems().size() > 0 ){
      Listloading = productList.getItems();

    }
    Log.e("TOANNA",""+Listloading.size());

    if(productList.getItems().size() >= SIZE_PAGE) {
      estoreAdapter.setData(productList.getItems().subList(0, SIZE_PAGE));
    }else if(productList.getItems().size() < SIZE_PAGE && productList.getItems().size() >0){
      estoreAdapter.setData(productList.getItems());
    }else{
      estoreAdapter.setData(productList.getItems());
    }


    if(productList.getItems().size() > 0 ) {
      srl.setEnabled(true);
      srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
          if (direction == SwipyRefreshLayoutDirection.TOP) {

          } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            /*<<START>> 20200313 - WIKI Toan Tran - Disable old version lazyloading*/
            /*new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {

                try{
                  Listloading1 = Listloading.subList(start, stop);
                }catch (IndexOutOfBoundsException ex){}
                Listloading2 = Listloading;
                if((productList.getItems().size()-stop)<SIZE_PAGE)
                  stop = stop + (productList.getItems().size()-stop);
                else {
                  stop = stop + SIZE_PAGE;
                }
                if (Listloading2.size() < stop) {
                  stop = Listloading2.size();
                  if (stop == (Listloading2.size())) {
                    //Toast.makeText(getApplicationContext(),"There are")
                  }
                  //Listloading1 = Listloading.subList(start,stop);
                } else {
                  // Listloading1 = Listloading.subList(start,stop);
                }
                if (Listloading2.size() > 0) {
                  Listloading1 = Listloading2.subList(start, stop);
                  estoreAdapter.setData(Listloading1);
                }
                srl.setRefreshing(false);
              }
            }, 2000);*/
            /*<<END>> 20200313 - WIKI Toan Tran - Disable old version lazyloading*/

            Log.e("TOANNA-MAXCOUNT",":"+MAX_COUNT);
            Log.e("TOANNA-Listloading",":"+Listloading.size());

            /*<<START>> 20200313 - WIKI Toan Tran - enable new version lazyloading*/
            if(Listloading.size()<MAX_COUNT){
              CURRENT_PAGE++;
              String query ="";
              query = listParamsFilter.string(CURRENT_PAGE);
              //presenter.getProductFilter(query);
              ProductFilterSortRequest request = new ProductFilterSortRequest();
              request.setStrFilter(query);
              request.setField(getSelectedSortField());
              request.setDirection(Constants.sortDirectionEstore[selectedSorting]);
              request.setPostionGroup(listParamsFilter.size());
              presenter.getProductFilterMore(request);
            }else {
              srl.setRefreshing(false);
              Toast.makeText(EstoreActivity.this,R.string.txt_message_end_list,Toast.LENGTH_SHORT).show();
            }
            /*<<END>> 20200313 - WIKI Toan Tran - enable new version lazyloading*/
          }
        }
      });
    }else{
      srl.setEnabled(false);
    }


    if(!TextUtils.isEmpty(filterOption)){
      presenter.getTreeCateStringJson(listParamsFilter.string(1));
    }
  }

  @Override
  public void renderFilterMore(ProductListResponse productList) {
    MAX_COUNT = productList.getTotalCount();
    Listloading.addAll(productList.getItems());
    estoreAdapter.setData(Listloading);
    srl.setRefreshing(false);
  }

  void addParentExpandId(){
    String tmp = "";
    String tmpOption ="";
    for(TreeItem item : treeFilter){

      if(item.getFilterLabel().compareToIgnoreCase("")!=0){
        tmp = item.getAttributeCode();
      }else{
        item.setIdParentExpand(tmp);
        if(item.getAttributeCode().compareToIgnoreCase("eStore")==0){
          item.setIdParentExpand(tmp);
        }
      }
      if(item.getParentId().equalsIgnoreCase(Constants.E_STORE)){
        tmpOption = item.getCategoryId();
      }else{
        if(!item.getCategoryId().equalsIgnoreCase("")){
          item.setIdParentExpandOption(tmpOption);
        }
      }
    }
  }
  void addParentExpandIdMini(){
    String tmp = "";
    String tmpOption ="";
    for(TreeItem item : treeFilterMini){

      if(item.getFilterLabel().compareToIgnoreCase("")!=0){
        tmp = item.getAttributeCode();
      }else{
        item.setIdParentExpand(tmp);
        if(item.getAttributeCode().compareToIgnoreCase("eStore")==0){
          item.setIdParentExpand(tmp);
        }
      }
      if(item.getParentId().equalsIgnoreCase(Constants.E_STORE)){
        tmpOption = item.getCategoryId();
      }else{
        if(!item.getCategoryId().equalsIgnoreCase("")){
          item.setIdParentExpandOption(tmpOption);
        }
      }
    }
  }

  /**
   * 20200229 WIKI Toan Tran - render tree category for the first time
   * @param strJson
   * @throws JSONException
   */
  @Override
  public void renderTreeCategory(String strJson) throws JSONException {
    if(progressDialog!=null){
      progressDialog.dismiss();
    }
    presenter.saveTreeFilter(strJson);
    filterLoaded = true;
    //initialize data of tree category
    intiDataTreeCategory(strJson);
    adapter = new FilterAdapter(treeFilter,this,this);
    Animation animation;
    animation = AnimationUtils.loadAnimation(this,R.anim.btn_fadeout);
    adapter.setAmin(animation);
    recyclerFilter.setAdapter(adapter);
    recyclerFilter.setLayoutManager(new LinearLayoutManager(this));
    adapter.notifyDataSetChanged();

    if(!TextUtils.isEmpty(filterOption)){
      adapter.getItemList();

      for(int i = 0 ; i < adapter.getItemList().size() ; i++ ) {
        for(String filterItem : arrayFilterOption){
          if(!TextUtils.isEmpty(adapter.getItemList().get(i).getIdObj())
              &&adapter.getItemList().get(i).getIdObj().equalsIgnoreCase(filterItem)){
            adapter.logicCheckBoxByOptions(i);
            List<TreeItem> list =  adapter.getItemList();

            List<TreeItem> checkList = new ArrayList<>();

            for(TreeItem item  : list){
              if(item.isCheck()){
                checkList.add(item);
              }
            }
            if(isFlagFilter){
              onClickApplyFilter(checkList);
            }
            adapter.notifyDataSetChanged();
            break;
          }
        }
      }

      // presenter.getCategoryDetails(filterOption,);
    }
  }

  @Override
  public void renderTreeCategoryLocal(String strJson) throws JSONException {
    if(progressDialog!=null){
      progressDialog.dismiss();
    }
    //presenter.saveTreeFilter(strJson);
    //initialize data of tree category
    intiDataTreeCategory(strJson);
    //filterLoaded = true;
    adapter = new FilterAdapter(treeFilter,this,this);
    Animation animation;
    animation = AnimationUtils.loadAnimation(this,R.anim.btn_fadeout);
    adapter.setAmin(animation);
    recyclerFilter.setAdapter(adapter);
    recyclerFilter.setLayoutManager(new LinearLayoutManager(this));
    adapter.notifyDataSetChanged();

    if(!TextUtils.isEmpty(filterOption)){
      adapter.getItemList();

      for(int i = 0 ; i < adapter.getItemList().size() ; i++ ) {
        for(String filterItem : arrayFilterOption){
          if(!TextUtils.isEmpty(adapter.getItemList().get(i).getIdObj())
              &&adapter.getItemList().get(i).getIdObj().equalsIgnoreCase(filterItem)){
            adapter.logicCheckBoxByOptions(i);
            List<TreeItem> list =  adapter.getItemList();

            List<TreeItem> checkList = new ArrayList<>();

            for(TreeItem item  : list){
              if(item.isCheck()){
                checkList.add(item);
              }
            }
            onClickApplyFilter(checkList);
            adapter.notifyDataSetChanged();
            break;
          }
        }
      }

      // presenter.getCategoryDetails(filterOption,);
    }
  }

  @Override
  public void renderTreeCategoryMini(String strJson) throws JSONException {
    if(progressDialog!=null){
      progressDialog.dismiss();
    }


    intiDataTreeCategoryMini(strJson);

    if(!TextUtils.isEmpty(filterOption)){
      List<TreeItem> checkList = new ArrayList<>();

      for(int i = 0 ; i < treeFilterMini.size() ; i++ ) {
        for(String filterItem : arrayFilterOption){
          if(!TextUtils.isEmpty(treeFilterMini.get(i).getCategoryId())
              &&treeFilterMini.get(i).getCategoryId().equalsIgnoreCase(filterItem)){
            logicCheckBoxByOptions(treeFilterMini,i);
            List<TreeItem> list =  treeFilterMini;


            for(TreeItem item  : list){
              if(item.isCheck()){
                checkList.add(item);
              }
            }
            //adapter.notifyDataSetChanged();
            break;
          }
        }
      }
      onClickApplyFilter(checkList);

      // presenter.getCategoryDetails(filterOption,);
    }
  }

  public void logicCheckBoxByOptions(List<TreeItem> itemList ,int position){
    int adapterPosition = position;
    String idClick = itemList.get(adapterPosition).getCategoryId();
    boolean isChecked = itemList.get(adapterPosition).isCheck();

    itemList.get(adapterPosition).setCheck(true);


    if(itemList.get(adapterPosition).getParentId().compareToIgnoreCase("")==0){
      return;
    }

    int levelItemClick = Integer.parseInt(itemList.get(adapterPosition).getLevel());
    for(int i = adapterPosition+1; i<itemList.size() ; i++){
      try {
        int levelItem =0;
        levelItem = Integer.parseInt(itemList.get(i).getLevel());
        if(levelItemClick > levelItem){
          break;
        }
        if(levelItemClick == levelItem){
          if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())==0){
            break;
          }
        }

        if(levelItemClick < levelItem){
          if(!isChecked){
            itemList.get(i).setCheck(true);
          }else {
            itemList.get(i).setCheck(false);
          }
        }
      }catch (NumberFormatException ex){}
    }
  }

  /**
   * 20200229 WIKI - Toan Tran set up data tree filter for the first time
   * @param strJson
   * @throws JSONException
   */
  void intiDataTreeCategory(String strJson) throws JSONException {
    //Don't change the struct of code below
    treeFilter.clear();
    listJSObj.clear();
    //Create list filter
    createFilter(strJson);
    for(JSONObjectPlus item :  listJSObj){
      try {
        createTree(item);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    //Remove the nested Json
    for(int i = 0 ; i < treeFilter.size(); i++){
      if(treeFilter.get(i).isEmptylAll()){
        treeFilter.remove(i);
      }
    }
    //Move the time to the end of category group
   /* TreeItem theTimeItem = new TreeItem();
    for(TreeItem item : treeFilter){
      if(item.getCategoryId().equalsIgnoreCase(Constants.THE_TIME)){
        theTimeItem = item;
        treeFilter.remove(item);
        break;
      }
    }
    for(int i = 0 ; i < treeFilter.size(); i++){
      if(!treeFilter.get(i).getCategoryId().equalsIgnoreCase("")&&
              treeFilter.get(i+1).getCategoryId().equalsIgnoreCase("")){
        treeFilter.add(i+1,theTimeItem);
        break;
      }
    }*/
    //move Group type to the top
    List<TreeItem> listType = new ArrayList<>();
    int s = 0;
    for(int i = 0 ; i < treeFilter.size(); i++){
      if(treeFilter.get(i).getFilterLabel().compareToIgnoreCase("BY TYPE")==0){
        TreeItem item = new TreeItem();
        item = treeFilter.get(i);
        listType.add(item);
        treeFilter.remove(i);
        i--;
        s= 1;
      }else {
        if(s==1){
          if (treeFilter.size() < i)
            break;
          if ((treeFilter.get(i).getFilterLabel().compareToIgnoreCase("") != 0))
            break;
          else {
            TreeItem item = new TreeItem();
            item = treeFilter.get(i);
            listType.add(item);
            treeFilter.remove(i);
            i--;
          }
        }
      }
    }
    int pos = 0;
    for(TreeItem item : listType){
      treeFilter.add(pos,item);
      pos++;
    }
    //End move
    //==
    //Move itemPrice move to the bot
    TreeItem itemPrice = new TreeItem();
    for(TreeItem item : treeFilter){
      if(item.getMaximumPrice().compareToIgnoreCase("")!=0) {
        itemPrice = item;
        treeFilter.remove(item);
        break;
      }
    }
    treeFilter.add(itemPrice);


    addParentExpandId();
    //to there
  }

  void intiDataTreeCategoryMini(String strJson) throws JSONException {
    //Don't change the struct of code below
    treeFilterMini.clear();
    listJSObj.clear();
    //Create list filter
    createFilterMini(strJson);
    for(JSONObjectPlus item :  listJSObj){
      try {
        createTreeMini(item);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    //Remove the nested Json
    for(int i = 0 ; i < treeFilterMini.size(); i++){
      if(treeFilterMini.get(i).isEmptylAll()){
        treeFilterMini.remove(i);
      }
    }

    List<TreeItem> listType = new ArrayList<>();
    int s = 0;
    for(int i = 0 ; i < treeFilterMini.size(); i++){
      if(treeFilterMini.get(i).getFilterLabel().compareToIgnoreCase("BY TYPE")==0){
        TreeItem item = new TreeItem();
        item = treeFilterMini.get(i);
        listType.add(item);
        treeFilterMini.remove(i);
        i--;
        s= 1;
      }else {
        if(s==1){
          if (treeFilterMini.size() < i)
            break;
          if ((treeFilterMini.get(i).getFilterLabel().compareToIgnoreCase("") != 0))
            break;
          else {
            TreeItem item = new TreeItem();
            item = treeFilterMini.get(i);
            listType.add(item);
            treeFilterMini.remove(i);
            i--;
          }
        }
      }
    }
    int pos = 0;
    for(TreeItem item : listType){
      treeFilterMini.add(pos,item);
      pos++;
    }
    //End move
    //==
    //Move itemPrice move to the bot
    TreeItem itemPrice = new TreeItem();
    for(TreeItem item : treeFilterMini){
      if(item.getMaximumPrice().compareToIgnoreCase("")!=0) {
        itemPrice = item;
        treeFilterMini.remove(item);
        break;
      }
    }
    treeFilterMini.add(itemPrice);


    addParentExpandIdMini();
    //to there
  }

  /**
   * 20200229 WIKI - Toan Tran render list filter after apply filter
   * @param strJson
   * @param selectId
   * @throws JSONException
   */
  @Override
  public void renderTreeCategory(String strJson, String selectId) throws JSONException {
    //Don't change the struct of code below

    treeFilter.clear();
    listJSObj.clear();
    createFilter(strJson);
    for(JSONObjectPlus item :  listJSObj){
      try {
        createTree(item);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    for(int i = 0 ; i < treeFilter.size(); i++){
      if(treeFilter.get(i).isEmptylAll()){
        treeFilter.remove(i);
      }
    }
    addParentExpandId();
    for(int i = 0 ; i < treeFilter.size(); i++){
    }
    //to there

    adapter.notifyDataSetChanged();
  }

  @Override
  public void renderSrlRefreshFalse() {
    srl.setEnabled(false);
  }

  @Override
  public void renderShoppingCart() {
    ShoppingCartActivity.start(this,ShoppingCartActivity.BUY_NOW);
  }


  private void formConfig(int position) {
    LandingActivity.startMe(this, position);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    /* 20200313 -  WIKI Toan Tran - disable feature product*/
    //featuredEstoreAdapter.unSubscribe();
    estoreAdapter.unSubscribe();
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void onBackPressed() {
    /*20200228 WIKI Toan Tran - on back pressed to close   */
    if(layout_filter_popup.getVisibility() == View.VISIBLE){
      layout_filter_popup.setVisibility(View.GONE);
    }else {
      if(!TextUtils.isEmpty(from)
          &&(fromNotification()||fromDeeplink())){
        LandingActivity.startMe(this);
        super.onBackPressed();
      }else{
        super.onBackPressed();
      }
    }
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
          if (isFeaturedItemClick) {
            /* 20200313 -  WIKI Toan Tran - disable feature product*/
            //featuredEstoreAdapter.notifyDataSetChanged();
          } else {
            estoreAdapter.notifyItemChanged(clickedPosition);
          }
        }
      }
    }
  }

  @OnClick(R.id.ivCart_toolbar)
  public void onClickCartCount() {
    ShoppingCartActivity.start(this);
  }

  @OnClick(R.id.ivSearch_toolbar)
  public void  onClickSearch() {
    SearchActivity.startMe(this);
  }



  private String getSelectedSortField() {
    String sortField = Constants.sortFieldEstore[selectedSorting];

    if (Constants.sortingArrEstore[selectedSorting].equals("Location")) {
      sortField = Constants.sortFieldEstore[selectedSorting];
      if (this.location != null) {
        sortField += location.getLatitude() + "@" + location.getLongitude();
      } else {
        sortField += "0@0";
      }
    }
    return sortField;
  }

  /**
   * 20200228 WIKI Toan Tran -  do action after click apply filter
   * @param listTreeItem
   */
  @Override
  public void onClickApplyFilter(List<TreeItem> listTreeItem) {
    listParamsFilter.getList().clear();
    /*20200317 WIKI Toan Tran -  disable NEW version*/
    listParamsFilter.addSizePage(SIZE_PAGE);
    CURRENT_PAGE = 1;
    boolean isCheckAll= false ;
    for(TreeItem item  : listTreeItem){
      if(item.getAttributeCode().compareToIgnoreCase("eStore")==0){
        listParamsFilter.addeStrore();
        isCheckAll = true;
      }
      //if(!isCheckAll){
      if(item.getCategoryId()!=""&&item.getCategoryId()!=null){
        listParamsFilter.addOrCate(item.getCategoryId(),listParamsFilter.size());
      }else {
        if(item.getIdObj()!=null&&item.getIdObj().compareToIgnoreCase("")!=0){
          listParamsFilter.addOrBrand(item.getIdObj(),listParamsFilter.size());
        }
        if(item.getFilterLabel()==""&&item.getName()!=""){
          if(item.getName().compareToIgnoreCase("news_to_date")==0){

            listParamsFilter.addOrTypeNewIn(listTreeItem.size()+4,item.getName());
          }else if(item.getName().compareToIgnoreCase("is_featured")==0){

            listParamsFilter.addOrTypeFeatures(listTreeItem.size()+4,item.getName());
          }
          else if(item.getName().compareToIgnoreCase("loyalty_value_to_earn")==0){

            listParamsFilter.addOrTypeLoyal(listTreeItem.size()+4,item.getName());
          }

        }
        //}
        if(item.getMaximumPrice().compareToIgnoreCase("")!=0&&
                item.getMaximumPrice().compareToIgnoreCase("")!=0&&
                item.getMinimumPrice()!=null&&
                item.getMaximumPrice()!=null){
          listParamsFilter.addAndPrice(Integer.parseInt(item.getMinimumPrice()),Integer.parseInt(item.getMaximumPrice()));
        }
      }
    }
    listParamsFilter.addRoot(Constants.E_STORE);
    PARAMS_FILTER = listParamsFilter.string(CURRENT_PAGE);
    String query ="";
    query = listParamsFilter.string(CURRENT_PAGE);
    //presenter.getProductFilter(query);
    ProductFilterSortRequest request = new ProductFilterSortRequest();
    request.setStrFilter(query);
    request.setField(getSelectedSortField());
    request.setDirection(Constants.sortDirectionEstore[selectedSorting]);
    request.setPostionGroup(listParamsFilter.size());
    presenter.getProductFilter(request);


    //presenter.getTreeCateStringJsonByCheckList(listParamsFilter.toString());
  }

  /**
   * 20200301 WIKI Toan Tran - click x to close popup
   */
  @Override
  public void onCloseFilterPopup() {

    layout_filter_popup.setVisibility(View.GONE);
  }

  /**
   * 20200303 WIKI Toan Tran - click label to move to inside the category
   * @param idCate
   */
  @Override
  public void onClickCategoryItem(int idCate) {
    selectedCategoryID =  ""+idCate;
    listParamsFilter.getList().clear();
    listParamsFilter.addRoot(selectedCategoryID);
    listParamsFilter.addOrCate(""+selectedCategoryID,0);
    presenter.getTreeCateStringJson(listParamsFilter.string(1));


    presenter.getProductFilter(listParamsFilter.string(CURRENT_PAGE));
    //presenter.getFeatureProducts(selectedCategoryID);
  }


  @Override
  public void addToCart(int position) {
    presenter.addToCart(
        new AddToCart.Params(estoreAdapter.getData().get(position).getSku()
            , 1
            , estoreAdapter.getData().get(position).getName()
            , estoreAdapter.getData().get(position).getTypeId()));
  }

  @Override
  public void buyNow(int position) {
    presenter.addToBuyNow(
        new AddToBuyNow.Params(estoreAdapter.getData().get(position).getSku()
            , 1
            , estoreAdapter.getData().get(position).getName()
            , estoreAdapter.getData().get(position).getTypeId()));
  }
  private void getIntentParam() {

    String query = "";
    from = getIntent().getStringExtra("from");
    Map<String,String> mapQuery = new HashMap<>();

    try {
      query = getIntent().getData().getQuery();
      String[] arrayQuery = query.split("&");
      for (String item : arrayQuery) {
        String[] arrayMap = item.split("=");
        if (arrayMap.length >= 2) {
          mapQuery.put(arrayMap[0], arrayMap[1]);
        }
      }
    } catch (NullPointerException ex) {
    }
    filterOption = mapQuery.get("filter");
    sortBy = mapQuery.get("sortby");
    if(from!=null&&from.equalsIgnoreCase("notification")){
      filterOption = getIntent().getStringExtra("filter");
      sortBy = getIntent().getStringExtra("sortBy");
      if (filterOption == null){
        filterOption = "";
      }
      if(from == null){
        from ="";
      }
      if(sortBy == null){
        sortBy ="";
      }
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
}
