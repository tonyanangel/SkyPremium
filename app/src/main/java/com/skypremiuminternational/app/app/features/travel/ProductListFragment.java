package com.skypremiuminternational.app.app.features.travel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.j256.ormlite.stmt.query.In;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.features.travel.ean.PropertyAdapter;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.property.PropertyDetailActivity;
import com.skypremiuminternational.app.app.features.travel.ean.occupancy.OccupancyPickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.suggestion.SuggestionActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.utils.ActivityChooser;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.HorizontalSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.LocationStream;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.listener.SearchHistoryListener;
import com.skypremiuminternational.app.app.view.AutoResizeTextView;
import com.skypremiuminternational.app.app.view.HotelEditText;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import butterknife.Optional;
import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ADULT_COUNT;
import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ROOM_COUNT;
import static com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity.TRAVEL_DETAIL;

/**
 * Created by johnsonmaung on 9/21/17.
 */

public class ProductListFragment extends BaseFragment<ProductListPresenter>
    implements ProductListView<ProductListPresenter>, SearchHistoryListener {

  private static final int R_C_PRODUCT_DETAIL = 1234;
  private static final int REQUEST_CODE_SUGGESTION = 222;

  @BindView(R.id.stiAreaName)
  HotelEditText edtAreaOrHotelName;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;
  @BindView(R.id.tv_check_out_date)
  TextView tvCheckOutDate;
  @BindView(R.id.tv_check_in_date)
  TextView tvCheckInDate;
  @BindView(R.id.ly_ean)
  ViewGroup lyEan;
  @BindView(R.id.tv_searched_hotel_count)
  TextView tvSearchedHotelCount;
  @BindView(R.id.ly_ean_info)
  ViewGroup lyEanInfo;
  @BindView(R.id.nested_scroll_ean)
  NestedScrollView nestedScrollEan;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.rvFeatured)
  RecyclerView rvFeatured;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategory;
  @BindView(R.id.tvSort_filter)
  TextView tvSort;
  @BindView(R.id.rv_partner_product)
  RecyclerView rvPartnerProduct;
  @BindView(R.id.ivBanner)
  ImageView ivBanner;
  @BindView(R.id.tvHeading)
  TextView tvHeading;
  @BindView(R.id.tvSubHead)
  TextView tvSubHead;
  @BindView(R.id.rlFeatureProduct)
  RelativeLayout rl;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;

  @BindView(R.id.tv_comingsoon)
  AutoResizeTextView tvComingsoon;

  @BindView(R.id.rv_ean_product)
  RecyclerView rvEanProduct;
  @BindView(R.id.srl)
  SwipyRefreshLayout srl;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private PropertyAdapter propertyAdapter;
  private FeaturedProductAdapter productFeaturedAdapter;
  private ProductAdapter productAdapter;

  List<ChildData_> categoryList = new ArrayList<>();

  private List<ItemsItem> Listloading = new ArrayList<>();
  private List<ItemsItem> Listloading1 = new ArrayList<>();
  private List<ItemsItem> Listloading2 = new ArrayList<>();
  private int start = 0;
  private int stop = 30;

  private String[] categoryArr;
  private String selectedCategoryID;
  private String selectedCategory;

  int selectedSorting = 0;
  private ProgressDialog progressDialog;
  private ItemsItem clickedItem;
  private CategoryDetailsResponse responsetravel;

  private CategoryDetailsResponse responAll;

  private String pillarID;
  public static String from;
  public static String filter;
  public static String sortBy;

  public static ProductListFragment newInstance(String pillarID, String filter,String from, String sortBy) {
    Log.d( "NOTIFI_GET" ,"Case 0");
    ProductListFragment fragment = new ProductListFragment();
    fragment.pillarID = pillarID;
    fragment.selectedCategoryID = pillarID;
    fragment.from = from;
    fragment.filter = filter;
    fragment.sortBy = sortBy;
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    tvComingsoon.setVisibility(View.GONE);
    tvTitle_toolbar.setText(getCategoryByPillar(pillarID));

    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading...");

    render(DEFAULT_ROOM_COUNT, DEFAULT_ADULT_COUNT, new ArrayList<>());

    tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[selectedSorting]));
    selectSortFromNotification();
    if (!isTravel()) {
      tvCategory.setText(String.format("Refine: %s", "All"));
    }



    setUpRecyclerView();
    presenter.setPillarID(pillarID);


    if (isTravel()) {
      if (!TextUtils.isEmpty(filter)&&from!=null&&from.equalsIgnoreCase("notification")) {
        presenter.getCategoryDetails(filter, getCategoryByPillar(pillarID));
      } else {
        presenter.getCategoryDetails("25", getCategoryByPillar("25"));
      }
      presenter.getCategoryDetailsfortravel(Constants.TRAVEL);
    } else {
      if (!TextUtils.isEmpty(filter)&&from!=null&&from.equalsIgnoreCase("notification")) {
        presenter.getCategoryDetails(filter, getCategoryByPillar(pillarID));
      } else {
        presenter.getCategoryDetails(pillarID, getCategoryByPillar(pillarID));

      }
    }
    presenter.getCartCount();

    srl.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
    srl.setDistanceToTriggerSync(10);

  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();

      ft.detach(this).attach(this).commit();
     /* Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

      FragmentTransaction ft = getFragmentManager().beginTransaction();

      ft.detach(this).attach(this).commit();
    /*  Intent i = new Intent(getActivity(), LandingActivity.class);
      getActivity().finish();
      getActivity().overridePendingTransition(0, 0);
      i.putExtra("FRAGMENT_ID", pillarID);
      startActivity(i);
      getActivity().overridePendingTransition(0, 0); */
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
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
  public void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_product_list;
  }

  @Inject
  @Override
  public void injectPresenter(ProductListPresenter presenter) {

    super.injectPresenter(presenter);
  }

  public void setUpRecyclerView() {

    HorizontalSpacesItemDecoration horizontalSpacesItemDecoration =
        new HorizontalSpacesItemDecoration(MetricsUtils.convertDpToPixel(8, getContext()), false);

    rvFeatured.addItemDecoration(horizontalSpacesItemDecoration);
    //rvFeatured.setHasFixedSize(true);
    //rvFeatured.setNestedScrollingEnabled(false);
    rvFeatured.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    productFeaturedAdapter = new FeaturedProductAdapter();

    productFeaturedAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        ActivityChooser.startMe(ProductListFragment.this, item, item.getCategoryName(),
            pillarID.equals(Constants.TRAVEL), R_C_PRODUCT_DETAIL);
      }

      @Override
      public void onFavItemClicked(ItemsItem item, int position) {
        if (item.isFavourite()) {
          presenter.removeFromFavourite(item, true);
        } else {
          presenter.addToFavourite(item, true);
        }
      }
      @Override
      public void onReserveButton(ItemsItem item, int position,TravelProductViewHolder holder) {
        if(holder.getSku() != null){
          //presenter.getDetailsItem(holder.getSku());
        } else if(!TextUtils.isEmpty(holder.restaurantIDS)){
          presenter.getOutletResevation(item.getId()+"",MakeAReservationDialogFragment.ACTION_NEW,item,holder.getLinkHGW());
        }else if (!ObjectUtil.isNull(holder.getLinkHGW()) && !ObjectUtil.isEmptyStr(holder.getLinkHGW())){
          if (CommonUtils.isValidUrl(holder.getLinkHGW())){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.getLinkHGW()));
            startActivity(browserIntent);
          } else {
            CommonUtils.showToast(getContext(), getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
          }
          //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
        } else {
          String name = item != null ? item.getName() : item.getName();
          MembershipActivity.startMe(getContext(), "Hi Sky Premium Membership Services Team,\n\n"
              + "I will like to enquire on a travel booking for "
              + name
              + ", for the period from <<Check-In Date>> to <<Check-Out Date>>, for <<# of Adults>> adults and <<# of Child>> child.\n\n"
              + "Appreciate if you can kindly assist on my request. Thank you.");
        }
      }
    });

    int spanCount = 2;
    int spacing = 8;
    rvPartnerProduct.addItemDecoration(
        new ProductGridSpacesItemDecoration(spanCount,
            MetricsUtils.convertDpToPixel(spacing, getContext()),
            true));

    rvPartnerProduct.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
    selectedCategory = getCategoryByPillar(pillarID);
    productAdapter = new ProductAdapter();
    productAdapter.setFromTravel(pillarID.equals(Constants.TRAVEL));
    productAdapter.setPillar(pillarID);
    productAdapter.setActionListener(new ProductActionListener<ItemsItem>() {
      @Override
      public void onItemClicked(ItemsItem item, int position) {
        clickedItem = item;
        ActivityChooser.startMe(ProductListFragment.this, item, item.getCategoryName(),
            pillarID.equals(Constants.TRAVEL), R_C_PRODUCT_DETAIL);
      }

      @Override
      public void onFavItemClicked(ItemsItem item, int position) {
        if (item.isFavourite()) {
          presenter.removeFromFavourite(item, false);
        } else {
          presenter.addToFavourite(item, false);
        }
      }

      @Override
      public void onReserveButton(ItemsItem item, int position,TravelProductViewHolder holder) {
        if(holder.getSku() != null){
          presenter.getDetailsItem(holder.getSku());
        } else if(!TextUtils.isEmpty(holder.restaurantIDS)){
          presenter.getOutletResevation(item.getId()+"",MakeAReservationDialogFragment.ACTION_NEW,item,holder.getLinkHGW());
        }else if (!ObjectUtil.isNull(holder.getLinkHGW()) && !ObjectUtil.isEmptyStr(holder.getLinkHGW())){
          if (CommonUtils.isValidUrl(holder.getLinkHGW())){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.getLinkHGW()));
            startActivity(browserIntent);
          } else {
            CommonUtils.showToast(getContext(), getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
          }
          //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
        } else {
          String name = item != null ? item.getName() : item.getName();
          MembershipActivity.startMe(getContext(), "Hi Sky Premium Membership Services Team,\n\n"
              + "I will like to enquire on a travel booking for "
              + name
              + ", for the period from <<Check-In Date>> to <<Check-Out Date>>, for <<# of Adults>> adults and <<# of Child>> child.\n\n"
              + "Appreciate if you can kindly assist on my request. Thank you.");
        }
      }
    });

    rvPartnerProduct.setAdapter(productAdapter);

    propertyAdapter = new PropertyAdapter();
    propertyAdapter.setActionListener(new ProductActionListener<Property>() {

      @Override
      public void onItemClicked(Property property, int position) {
        presenter.collectBookingValues(property);
      }

      @Override
      public void onFavItemClicked(Property item, int position) {

      }

      @Override
      public void onReserveButton(Property item, int position, TravelProductViewHolder holder) {

      }
    });

    rvEanProduct.addItemDecoration(new ProductGridSpacesItemDecoration(spanCount,
        MetricsUtils.convertDpToPixel(spacing, getContext()), true));
    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
    rvEanProduct.setLayoutManager(gridLayoutManager);
    rvEanProduct.setAdapter(propertyAdapter);
    rvEanProduct.setNestedScrollingEnabled(false);
    rvEanProduct.setHasFixedSize(true);
    nestedScrollEan.setOnScrollChangeListener(
        (NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
          if (isEanLayoutVisible()) {
            hideBottomEanInfo();
          } else {
            showBottomEanInfo();
          }
        });
  }

  private boolean isEanLayoutVisible() {
    int[] location = new int[2];
    lyEanInfo.getLocationOnScreen(location);
    int height = lyEanInfo.getMeasuredHeight();

    return (location[1] + height) > MetricsUtils.convertDpToPixel(128, getContext());
  }

  private void showBottomEanInfo() {
    LandingActivity landingActivity = getHostActivity();
    if (!landingActivity.isEanInfoVisible()) {
      presenter.collectSearchValues();
    }
  }

  private void hideBottomEanInfo() {
    LandingActivity landingActivity = getHostActivity();
    if (landingActivity.isEanInfoVisible()) {
      landingActivity.hideEANInfo();
    }
  }

  private LandingActivity getHostActivity() {
    return (LandingActivity) getActivity();
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getChildFragmentManager(), "Navigation");
  }

  @OnClick(R.id.ivSearch_toolbar)
  void onClickSearch() {
    SearchActivity.startMe(getContext(), pillarID);
  }

  @OnClick(R.id.tvCategory_filter)
  void onClickCategory() {
    new AlertDialog.Builder(getActivity()).setTitle("REFINE: ")
        .setItems(categoryArr, (dialog, item) -> {
          stop = 30;

          if (item == 0 && !isTravel()) {

            presenter.getFeatureProducts(pillarID, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
            selectedCategory = getCategoryByPillar(pillarID);
            selectedCategoryID = pillarID;
            tvCategory.setText(String.format("Refine: %s", "All"));

          } else if (isTravel()) {
            if (isEanCategorySelected()) {
              selectedSorting = 0;
            }
            if (item == 0 && isTravel()) {

              //presenter.getCategoryDetails(selectedCategoryID);
              selectedCategory = getCategoryByPillar(pillarID);
              selectedCategoryID = pillarID;
              tvCategory.setText(String.format("Refine: %s", "All"));
              presenter.getFeatureProducts("25", getCategoryByPillar("25"), Integer.parseInt("25"));

            } else {

              // presenter.getCategoryDetails(selectedCategoryID);
              presenter.getFeatureProducts(String.valueOf(categoryList.get(item - 1).getId()), getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
              selectedCategory = categoryList.get(item - 1).getName();
              selectedCategoryID = String.valueOf(categoryList.get(item - 1).getId());
              tvCategory.setText(String.format("Refine: %s", categoryList.get(item - 1).getName()));


            }
          } else {

            presenter.getFeatureProducts(String.valueOf(categoryList.get(item - 1).getId()), getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
            selectedCategory = categoryList.get(item - 1).getName();
            selectedCategoryID = String.valueOf(categoryList.get(item - 1).getId());
            tvCategory.setText(String.format("Refine: %s", categoryList.get(item - 1).getName()));
          }


          setCategoryDetails(responAll);
          checkSortFieldAndGetProduct();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  private String getCategoryByPillar(String pillarID) {
    if (pillarID.equalsIgnoreCase(Constants.TRAVEL)) {
      return getString(R.string.label_travel);
    } else if (pillarID.equalsIgnoreCase(Constants.WINE_AND_DINE)) {
      return getString(R.string.wine_n_dine_label);
    } else if (pillarID.equalsIgnoreCase(Constants.SHOPPING)) {
      return getString(R.string.shopping_label);
    } else if (pillarID.equalsIgnoreCase(Constants.WELLNESS)) {
      return getString(R.string.wellness_label);
    } else {
      return getString(R.string.label_travel);
    }
  }

  private String getCategoryTravelByPillar(String pillarID) {
    if (pillarID.equalsIgnoreCase(Constants.TRAVEL)) {
      return getString(R.string.label_travel);
    } else if (pillarID.equalsIgnoreCase("57")) {
      return "Hotels";
    } else if (pillarID.equalsIgnoreCase("25")) {
      return "Hotels Plus";
    } else if (pillarID.equalsIgnoreCase("49")) {
      return "Cruises & Trains";
    } else if (pillarID.equalsIgnoreCase("50")) {
      return "Packages";
    } else if (pillarID.equalsIgnoreCase("53")) {
      return "Services";
    } else {
      return getString(R.string.label_travel);
    }
  }

  @OnClick(R.id.tvSort_filter)
  void onClickSort() {
    stop = 30;
    String[] sorting = Constants.sortingArr;

    if (isEanCategorySelected()) {
      sorting = Constants.sortingArrEan;
    }
    new AlertDialog.Builder(getActivity()).setTitle("SORT BY:")
        .setItems(sorting, (dialog, which) -> {
          selectedSorting = which;
          if (isEanCategorySelected()) {
            searchProperties();
          } else {
            checkSortFieldAndGetProduct();
          }
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  private boolean isEanCategorySelected() {
    return selectedCategory != null && selectedCategory.equalsIgnoreCase(
        "Hotels");
  }

  @Override
  public void render(TravelViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {

      if (viewState.isSuccess()) {
        if (viewState.message().equals("1")) {
          List<ItemsItem> itemsItemList = new ArrayList<>();
          for (ItemsItem itemsItem : viewState.dataList().getItems()) {
            if (itemsItem.getStatus() == 1) {
              itemsItemList.add(itemsItem);
            }
          }
          if (itemsItemList.size() == 0) {
            tvComingsoon.setVisibility(View.VISIBLE);
            hideLoading();
          } else {
            tvComingsoon.setVisibility(View.GONE);
          }
          if (itemsItemList.size() > 0) {
            if (progressDialog.isShowing()) {
              progressDialog.dismiss();
            }
            Listloading = itemsItemList;
          }
          if (itemsItemList.size() >= 30) {
            productAdapter.setDataList(itemsItemList.subList(0, 30));
          } else if (itemsItemList.size() < 30 && itemsItemList.size() > 0) {
            productAdapter.setDataList(itemsItemList);
          } else {
            productAdapter.setDataList(itemsItemList);
          }


          if (Listloading.size() > stop) {

            srl.setEnabled(true);

            srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {

                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                  new Handler().postDelayed(new Runnable() {
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
                        productAdapter.setDataList(Listloading1);

                      }

                      srl.setRefreshing(false);
                    }
                  }, 2000);
                }
              }
            });
          } else {
            srl.setEnabled(false);
          }


          // productAdapter.setDataList(itemsItemList);
        }

        responAll = viewState.categoryDetails();
        if (viewState.message().equals("2")) {
          setCategories(viewState.category());
          setCategoryDetails(viewState.categoryDetails());
        }
        if (viewState.message().equals("3")) {
          if (viewState.featureList().getFeatureItems().size() > 0) {
            if (isEanCategorySelected()) {
              rvFeatured.setVisibility(View.GONE);
            } else {
              rvFeatured.setVisibility(View.VISIBLE);
            }
            rvFeatured.setAdapter(productFeaturedAdapter);
            productFeaturedAdapter.setDataList(viewState.featureList().getFeatureItems());
          } else {
            rvFeatured.setVisibility(View.GONE);
          }
        }
      } else {
        String errorMessage;
        if (viewState.error() == null) {
          errorMessage = viewState.message();
        } else {
          errorMessage = errorMessageFactory.getErrorMessage(viewState.error());
        }
        showError(errorMessage);
      }
    }
  }

  private void showError(String errorMessage) {
    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void updateCartCount(String count) {
    if (count == null || TextUtils.isEmpty(count)) {
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
  public void render(List<Property> properties) {
    String text;

    if (properties.isEmpty()) {
      text = getString(R.string.product_list_emtpy_hotel_search);
      tvSearchedHotelCount.setText(text);
      rvEanProduct.setVisibility(View.GONE);
    } else {
      nestedScrollEan.scrollTo(0, 0);
      String size = String.valueOf(properties.size());
      text = size + " " + getString(R.string.product_list_hotel_search_count);

      SpannableString spannableString = new SpannableString(text);
      spannableString.setSpan(new StyleSpan(Typeface.BOLD),
          0, size.length(), 0);
      rvEanProduct.setVisibility(View.VISIBLE);
      tvSearchedHotelCount.setText(spannableString);
      propertyAdapter.setProperties(properties);
    }


  }

  @Override
  public void render(Throwable error) {
    error.printStackTrace();
    showError(errorMessageFactory.getErrorMessage(error));
  }

  @Override
  public void render(CategoryDetailsResponse response) {
    responsetravel = response;
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    for (CategoryDetailsResponse.CustomAttribute customAttributesItem : response.getCustomAttributes()) {
      if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
        tvSubHead.setText(Html.fromHtml(customAttributesItem.getValue()));
      } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
        Glide.with(getContext())
            .load(URLUtils.bannerImageURL(customAttributesItem.getValue()))
            .apply(requestOptions)
            .into(ivBanner);
      }
    }
  }

  @Override
  public void render(@NonNull String startDate, @NonNull String endDate) {
    tvCheckInDate.setText(startDate);

    if (TextUtils.isEmpty(endDate)) {
      tvCheckOutDate.setText(R.string.all_not_selected_label);
    } else {
      tvCheckOutDate.setText(endDate);
    }
  }

  @Override
  public void clearDateRange() {
    tvCheckOutDate.setText(R.string.all_not_selected_label);
    tvCheckInDate.setText(R.string.all_not_selected_label);
  }

  @Override
  public void showOccupancyDialog(int roomCount, int adultCount, List<Child> children) {
    OccupancyPickerDialogFragment fragment =
        OccupancyPickerDialogFragment.newInstance(roomCount, adultCount, children);
    fragment.setOnDoneClickListener(
        (int roomCount1, int adultCount1, List<Child> children1) -> {
          presenter.setOccupancyValues(roomCount1, adultCount1, (ArrayList<Child>) children1);
        });
    fragment.show(getChildFragmentManager(), "OccupancyPicker");
  }

  @Override
  public void render(int roomCount, int adultCount, List<Child> children) {

    Resources resources = getResources();
    String occupancy = resources.getQuantityString(R.plurals.adults, adultCount, adultCount) + ", ";

    occupancy += resources.getQuantityString(R.plurals.children, children.size(), children.size())
        + ", ";

    occupancy += resources.getQuantityString(R.plurals.rooms, roomCount, roomCount);
    tvOccupancy.setText(occupancy);
  }

  @Override
  public void render(String areaOrHotelName) {
    if (TextUtils.isEmpty(areaOrHotelName)) {
      edtAreaOrHotelName.setText(getString(R.string.all_hotel_area_name_input_hint));
    } else {
      edtAreaOrHotelName.setText(areaOrHotelName);
    }
  }

  @Override
  public void goToPropertyDetail(Property property, int roomCount, int adultCount,
                                 ArrayList<Child> children,
                                 List<CalendarDay> calendarSelectedDates) {
    PropertyDetailActivity.start(getContext(), property.id(), roomCount, adultCount, children,
        calendarSelectedDates);
  }

  @Override
  public void renderPropertyError(Throwable error) {
    rvEanProduct.setVisibility(View.GONE);
    tvSearchedHotelCount.setText(errorMessageFactory.getErrorMessage(
        new Exception(getString(R.string.product_list_search_properties_error))));
  }

  @Override
  public void showBottomEanLayout(List<CalendarDay> selectedDays, int roomCount, int adultCount,
                                  ArrayList<Child> children) {

    String areaOrHotel;
    areaOrHotel = edtAreaOrHotelName.getText();

    ((LandingActivity) getActivity())
        .showEANInfo(areaOrHotel,
            selectedDays, roomCount, adultCount, children, ProductListFragment.this);
  }

  @Override
  public void renderGetDetailToGoEstore(DetailsItem response) {
    EstoreDetailActivity.startMe(getActivity(), response, response.getCategoryName(), TRAVEL_DETAIL);
  }

  @Override
  public void showErrorMsg(int message) {
    Toast.makeText(getContext(), getText(message), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void notifyFavStatusChanged(boolean featuredItem) {

    if (featuredItem) {
      productFeaturedAdapter.notifyDataSetChanged();
    } else {
      productAdapter.notifyDataSetChanged();
    }
  }

  private void setCategoryDetails(CategoryDetailsResponse categoryDetailsResponse) {
    if (pillarID.equals(Constants.WINE_AND_DINE) || pillarID.equals(Constants.SHOPPING)) {
      int whiteColor = ContextCompat.getColor(getContext(), R.color.white);
      tvSubHead.setTextColor(whiteColor);
      tvHeading.setTextColor(whiteColor);
    }
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    if (isTravel()) {
      for (CategoryDetailsResponse.CustomAttribute customAttributesItem : categoryDetailsResponse.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
          tvSubHead.setText(Html.fromHtml(customAttributesItem.getValue()));
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
          Glide.with(getContext())
                  .load(URLUtils.bannerImageURL(customAttributesItem.getValue()))
                  .apply(requestOptions)
                  .into(ivBanner);
        }
      }
    } else {
      for (CategoryDetailsResponse.CustomAttribute customAttributesItem : categoryDetailsResponse.getCustomAttributes()) {
        if (customAttributesItem.getAttributeCode().equalsIgnoreCase("description")) {
          tvSubHead.setText(Html.fromHtml(customAttributesItem.getValue()));
        } else if (customAttributesItem.getAttributeCode().equalsIgnoreCase("image")) {
          Glide.with(getContext())
                  .load(URLUtils.bannerImageURL(customAttributesItem.getValue()))
                  .apply(requestOptions)
                  .into(ivBanner);

        }
      }

    }
    if (isTravel()) {
      tvHeading.setText(Constants.TRAVEL_NAME);
    } else {
      tvHeading.setText(categoryDetailsResponse.getName());
    }
  }

  private void setCategories(CategoryResponse response) {

    List<ChildData> childDataList = response.getChildrenData();
    for (ChildData childData : childDataList) {
      if (childData.getId() == Integer.parseInt(pillarID)) {

        categoryList = childData.getChildrenData();

        for (int i = 0; i < categoryList.size(); i++) {
          if (!categoryList.get(i).getIsActive()) {
            categoryList.remove(i);
            i--;
          }
        }


        Collections.sort(categoryList,
            (childData_1, childData_2) -> childData_1.getPosition()
                .compareTo(childData_2.getPosition()));

        if (isTravel()) {

          categoryArr = new String[categoryList.size() + 1];
          categoryArr[0] = "All";
          for (int i = 1; i <= categoryList.size(); i++) {
            categoryArr[i] = categoryList.get(i - 1).getName();
          }
         /* selectedCategory = getCategoryByPillar(pillarID);
          selectedCategoryID = pillarID;
          tvCategory.setText(String.format("Refine: %s", "All"));
          presenter.getFeatureProducts("25"); */
         /* categoryArr = new String[categoryList.size()];
          for (int i = 0; i < categoryList.size(); i++) {
            categoryArr[i] = categoryList.get(i).getName();
          } */

          selectedCategory = categoryList.get(1).getName();
          selectedCategoryID = String.valueOf(categoryList.get(1).getId());
          tvCategory.setText(String.format("Refine: %s", categoryList.get(1).getName()));
        } else {
          categoryArr = new String[categoryList.size() + 1];
          categoryArr[0] = "All";
          for (int i = 1; i <= categoryList.size(); i++) {
            categoryArr[i] = categoryList.get(i - 1).getName();
          }
        }
      }
    }
    for(ChildData_ childData_ : categoryList){
      if(from!=null&&from.equalsIgnoreCase("notification")
          && !TextUtils.isEmpty(filter)
          && filter.equalsIgnoreCase(childData_.getId().toString())){
        tvCategory.setText(String.format("Refine: %s", childData_.getName()));
        break;
      }
    }
    checkSortFieldAndGetProduct();
  }

  private boolean isTravel() {
    return pillarID.equalsIgnoreCase(Constants.TRAVEL);
  }

  private void searchProperties() {

    tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEan[selectedSorting]));
    String sortField = Constants.sortingArrEan[selectedSorting];

    if (Constants.sortingArrEan[selectedSorting].equals("Location")) {
      Location location = getLocationFromActivity();
      if (location != null) {
        presenter.searchProperties(edtAreaOrHotelName.getText(), tvCheckInDate.getText().toString(),
            tvCheckOutDate.getText().toString(), sortField, location);
      } else {
        LocationStream locationStream = getLocationStreamFromActivity();
        if (locationStream != null && !locationStream.isPermissionGranted()) {
          showPermissionDeniedWarning();
        } else if (locationStream != null && locationStream.isPermissionGranted()) {
          locationStream.listenToLocationUpdates();
        }
      }
    } else {
      presenter.searchProperties(edtAreaOrHotelName.getText(), tvCheckInDate.getText().toString(),
          tvCheckOutDate.getText().toString(), sortField, null);
    }
  }

  private void checkSortFieldAndGetProduct() {
    if (isEanCategorySelected()) {
      showEanLayout(true);
    } else if (Constants.sortingArr[selectedSorting].equals("Location")) {
      hideBottomEanInfo();
      showEanLayout(false);
      Location location = getLocationFromActivity();
      if (location != null) {
        String sortField = Constants.sortField[selectedSorting]
            + location.getLatitude()
            + "@"
            + location.getLongitude();

        tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[selectedSorting]));


        Log.d("NOTIFI_GET","Case 1");

        presenter.getProductList(selectedCategoryID, sortField,
            Constants.sortDirection[selectedSorting], selectedCategory, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
      } else {
        LocationStream locationStream = getLocationStreamFromActivity();
        if (locationStream != null && !locationStream.isPermissionGranted()) {
          showPermissionDeniedWarning();
        } else if (locationStream != null && locationStream.isPermissionGranted()) {
          locationStream.listenToLocationUpdates();
        }
      }
    } else {
      hideBottomEanInfo();
      showEanLayout(false);
      tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[selectedSorting]));
      selectSortFromNotification();

      if (!TextUtils.isEmpty(filter)&&(fromNotification()||fromDeeplink())) {
        if(!TextUtils.isEmpty(sortBy)){
          presenter.getProductList(filter, getSortFieldParam(),
              getSortDirParam(), selectedCategory, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
        }else {
          presenter.getProductList(filter, Constants.sortField[selectedSorting],
              Constants.sortDirection[selectedSorting], selectedCategory, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
        }
      } else {
        if(!TextUtils.isEmpty(sortBy)){
          presenter.getProductList(selectedCategoryID, getSortFieldParam(),
              getSortDirParam(), selectedCategory, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
        }else {

          presenter.getProductList(selectedCategoryID, Constants.sortField[selectedSorting],
              Constants.sortDirection[selectedSorting], selectedCategory, getCategoryByPillar(pillarID), Integer.parseInt(pillarID));
        }
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

  private void showEanLayout(boolean visible) {
    int headingColor = ContextCompat.getColor(getContext(), R.color.white);
    if (visible) {
      rvFeatured.setVisibility(View.GONE);
      rvPartnerProduct.setVisibility(View.GONE);
      lyEanInfo.setVisibility(View.VISIBLE);
      nestedScrollEan.setVisibility(View.VISIBLE);

      for (int i = 0; i < Constants.sortingArrEan.length; i++) {
        if (Constants.sortingArrEan[i].equalsIgnoreCase(Constants.EAN_SORT_POPULAR)) {
          selectedSorting = i;
          break;
        }
      }

      tvSort.setText(String.format("Sort By: %s", Constants.sortingArrEan[selectedSorting]));
    } else {
      tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[selectedSorting]));
      rvFeatured.setVisibility(View.VISIBLE);
      rvPartnerProduct.setVisibility(View.VISIBLE);
      lyEanInfo.setVisibility(View.GONE);
      nestedScrollEan.setVisibility(View.GONE);
    }

    tvSubHead.setTextColor(headingColor);
    tvHeading.setTextColor(headingColor);
  }

  private void showPermissionDeniedWarning() {
    new AlertDialog.Builder(getContext()).setTitle("Permission denied!")
        .setMessage(
            "Location permission is required to sort by location. Go to setting and enable it?")
        .setPositiveButton("Yes", (dialog, which) -> {
          dialog.dismiss();
          goToSetting();
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .show();
  }

  private void goToSetting() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
    intent.setData(uri);
    getActivity().startActivity(intent);
  }

  @Nullable
  private LocationStream getLocationStreamFromActivity() {
    if (getActivity() instanceof LocationAwareActivity) {
      return ((LocationAwareActivity) getActivity()).locationStream;
    }
    return null;
  }

  @Nullable
  private Location getLocationFromActivity() {
    if (getActivity() instanceof LocationAwareActivity) {
      return ((LocationAwareActivity) getActivity()).location;
    }
    return null;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == R_C_PRODUCT_DETAIL) {
        boolean isFavourite = data.getBooleanExtra("fav_status", false);
        final int id = data.getIntExtra("item_id", -1);
        if (clickedItem != null) {
          if (clickedItem.getId() == id) {
            clickedItem.setFavourite(isFavourite);
            productAdapter.notifyDataSetChanged();
            productFeaturedAdapter.notifyDataSetChanged();
          }
        }
      }

      if (requestCode == REQUEST_CODE_SUGGESTION) {
        if (data.hasExtra("suggestion")) {
          edtAreaOrHotelName.setText(data.getStringExtra("suggestion"));
        }
      }
    }
  }

  @OnClick(R.id.ivCart_toolbar)
  public void onClickCartCount() {
    ShoppingCartActivity.start(getActivity());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @OnClick(R.id.btn_search_hotels)
  void onClickedSearchHotels() {
    searchProperties();
  }

  @Override
  public void showLoading() {
    if (getActivity().isDestroyed()) {
      return;
    }
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (getActivity().isDestroyed()) {
      return;
    }
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.tv_check_in_date)
  void onClickedCheckInDate() {
    showDateRangePicker();
  }

  @OnClick(R.id.tv_check_out_date)
  void onClickedCheckOutDate() {
    showDateRangePicker();
  }

  @OnClick(R.id.tv_occupancy)
  void onClickedOccupancy() {
    presenter.collectOccupancyValues();
  }

  private void showDateRangePicker() {
    DateRangePickerDialogFragment fragment = DateRangePickerDialogFragment.newInstance();
    fragment.setOnDateRangeSelectListener(
        dates -> presenter.changeDateRange(dates));
    fragment.show(getChildFragmentManager(), "DateRangePicker");
  }

  @Override
  public void searchHistoryClicked(String areaOrHotel, List<CalendarDay> selectedDays,
                                   int roomCount, int adultCount, List<Child> children) {
    presenter.setOccupancyValues(roomCount, adultCount, (ArrayList<Child>) children);
    presenter.changeDateRange(selectedDays);
    edtAreaOrHotelName.setText(areaOrHotel);

    searchProperties();
  }

  @OnClick(R.id.hotel_name_overlay)
  void onHotelNameClicked() {
    SuggestionActivity.startForResult(this, REQUEST_CODE_SUGGESTION);
  }


  void selectSortFromNotification(){

    if(from!=null && (fromNotification()||fromDeeplink())
        && !TextUtils.isEmpty(sortBy)){
      switch (sortBy){
        case  "popularity_order" :{
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[0]));
          break;
        }
        case  "nameaz" :{
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[1]));
          break;
        }
        case  "nameza" :{
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[2]));
          break;
        }
        case  "latest" :{
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[3]));
          break;
        }
        case  "earliest" :{
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[4]));
          break;
        }
        default: {
          tvSort.setText(String.format("Sort By: %s", Constants.sortingArr[0]));
        }
      }
    }
  }

  String getSortFieldParam(){
    if(from!=null&&(fromNotification()||fromDeeplink())
        && !TextUtils.isEmpty(sortBy)){
      switch (sortBy){
        case  "popularity_order" :{
          return Constants.sortField[0];
        }
        case  "nameaz" :{
          return Constants.sortField[1];
        }
        case  "nameza" :{
          return Constants.sortField[2];
        }
        case  "latest" :{
          return Constants.sortField[3];
        }
        case  "earliest" :{
          return Constants.sortField[4];
        }
        default: {
          return Constants.sortField[0];
        }
      }
    }
    return Constants.sortField[0];
  }
  String getSortDirParam(){
    if(from!=null && (fromNotification()||fromDeeplink())
        && !TextUtils.isEmpty(sortBy)){
      switch (sortBy){
        case  "popularity_order" :{
          return Constants.sortDirection[0];
        }
        case  "nameaz" :{
          return Constants.sortDirection[1];
        }
        case  "nameza" :{
          return Constants.sortDirection[2];
        }
        case  "latest" :{
          return Constants.sortDirection[3];
        }
        case  "earliest" :{
          return Constants.sortDirection[4];
        }
        default: {
          return Constants.sortDirection[0];
        }
      }
    }
      return Constants.sortDirection[0];

  }

  @Override
  public void renderGetOutletFailed(ItemsItem itemsItem, String linkHWG) {
    if (!ObjectUtil.isNull(linkHWG) && !ObjectUtil.isEmptyStr(linkHWG)){
      if (CommonUtils.isValidUrl(linkHWG)){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkHWG));
        startActivity(browserIntent);
      } else {
        CommonUtils.showToast(getContext(), getString(R.string.text_url_invalid), Toast.LENGTH_SHORT);
      }
      //WebviewActivity.start(TravelDetailActivity.this, linkHGW, getString(R.string.text_hgw));
    }
  }

  @Override
  public void renderOpenReservation(List<OutletItem> outletItems, int action,ItemsItem itemsItem) {
    MakeAReservationDialogFragment fragment = MakeAReservationDialogFragment.newInstance(outletItems,itemsItem.getId(),itemsItem.getName());
    fragment.show(getChildFragmentManager(), MakeAReservationDialogFragment.TAG);
  }
}
