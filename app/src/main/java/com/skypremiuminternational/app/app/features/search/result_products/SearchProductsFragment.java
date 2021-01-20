package com.skypremiuminternational.app.app.features.search.result_products;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.estore.IOnClickAddToCart;
import com.skypremiuminternational.app.app.features.estore.IOnClickBuyNow;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.detail.adapter.RecommendationAdapter;
import com.skypremiuminternational.app.app.features.travel.ProductAdapter;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.internal.mvp.LocationAwareActivity;
import com.skypremiuminternational.app.app.utils.ActivityChooser;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ProductGridSpacesItemDecoration;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by johnsonmaung on 9/21/17.
 */

public class SearchProductsFragment extends BaseFragment<SearchProductsPresenter>
    implements SearchProductsView<SearchProductsPresenter>, ProductActionListener<ItemsItem> , IOnClickAddToCart, IOnClickBuyNow {

  private static final int R_C_PRODUCT_DETAIL = 123;
  final int[] categoryIDArr = {
      -1, Constants.TRAVEL_ID, Constants.WINE_AND_DINE_ID, Constants.SHOPPING_ID,
      Constants.WELLNESS_ID, Constants.E_STORE_ID
  };
  public static String RELEVANCE = "relevance_order";
  @BindView(R.id.tvCategory_filter)
  TextView tvCategory;
  @BindView(R.id.tvSort_filter)
  TextView tvSort;
  @BindView(R.id.tvKeyword)
  TextView tvKeyword;
  @BindView(R.id.rv)
  RecyclerView rv;
  @BindView(R.id.llMessage)
  LinearLayout llMessage;
  @BindView(R.id.tvMessage)
  TextView tvMessage;
  @BindView(R.id.tvMessageInfo)
  TextView tvMessageInfo;
  @BindView(R.id.llSearchResult)
  LinearLayout llResult;
  @Inject
  ErrorMessageFactory errorMessageFactory;
  private List<ChildData_> categoryList = new ArrayList<>();

  private String selectedCategoryID = null;
  private String selectedCategory = null;
  private int selectedSorting = 0;
  private int selectedFromNoti = 0;
  private String[] categoryArr =
      {"All", "Travel", "Wine & Dine", "Shopping", "Wellness", "E-Store"};
  private String defCategoryID = null;

  private ProductAdapter adapter;
  private RecommendationAdapter estoreAdapter;
  private String keyword = "";
  private ProgressDialog progressDialog;
  private String[] sortFieldSearch;
  private String[] sortingArrSearch;
  private String[] sortDirectionSearch;
  private ItemsItem clickedItem;
  private boolean isEstore;
  private boolean isSelectedSort = false;

  public static SearchProductsFragment newInstance(String keyword,int selectedFromNoti, String selectedCategoryID,
                                                   boolean isEstore, boolean isSelectedSort) {
    SearchProductsFragment fragment = new SearchProductsFragment();
    fragment.keyword = keyword;
    /*fragment.selectedCategoryID = selectedCategoryID;
    fragment.sortingArr =
        selectedCategoryID == null ? Constants.sortingArrHome : Constants.sortingArr;
    fragment.sortDirection =
        selectedCategoryID == null ? Constants.sortDirectionHome : Constants.sortDirection;
    fragment.sortField =
        selectedCategoryID == null ? Constants.sortingFieldHome : Constants.sortField;
    fragment.defCategoryID = selectedCategoryID;
    fragment.isEstore = isEstore;*/

    fragment.selectedCategoryID = null;
    fragment.sortingArrSearch = Constants.sortingArrSearch;
    fragment.sortDirectionSearch = Constants.sortDirectionSearch;
    fragment.sortFieldSearch = Constants.sortFieldSearch;
    fragment.defCategoryID = null;
    fragment.selectedFromNoti = selectedFromNoti;
    fragment.isEstore = true;
    fragment.isSelectedSort = isSelectedSort;
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
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading...");

    setUpRecyclerView();
    if(isSelectedSort){
      selectedSorting = selectedFromNoti;
    }
    tvSort.setText(String.format("Sort By: %s", Constants.sortingArrSearch[selectedSorting]));
    tvCategory.setText(String.format("Refine: %s", "All"));

    presenter.getCategories(selectedCategoryID);
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
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_search_products;
  }

  @Inject
  @Override
  public void injectPresenter(SearchProductsPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.tvCategory_filter)
  void onClickCategory() {
    new AlertDialog.Builder(getActivity()).setTitle("REFINE: ")
        .setItems(categoryArr, (dialog, item) -> {

          if (defCategoryID == null) {
            selectedCategory = categoryList.get(item).getName();
            if (categoryList.get(item).getId() == -1) {
              selectedCategoryID = null;
            } else {
              selectedCategoryID = String.valueOf(categoryList.get(item).getId());
            }
            tvCategory.setText(String.format("Refine: %s", categoryList.get(item).getName()));
          } else {
            if (item == 0) {
              if (defCategoryID.equalsIgnoreCase(Constants.TRAVEL)) {
                selectedCategory = getResources().getString(R.string.travel_all_caps);
              } else if (defCategoryID.equalsIgnoreCase(Constants.WINE_AND_DINE)) {
                selectedCategory = getResources().getString(R.string.wine_all_caps);
              } else if (defCategoryID.equalsIgnoreCase(Constants.SHOPPING)) {
                selectedCategory = getResources().getString(R.string.shopping_all_caps);
              } else if (defCategoryID.equalsIgnoreCase(Constants.WELLNESS)) {
                selectedCategory = getResources().getString(R.string.wellness_all_caps);
              }
              selectedCategoryID = defCategoryID;
              tvCategory.setText(String.format("Refine: %s", "All"));
            } else {
              selectedCategory = categoryList.get(item - 1).getName();
              selectedCategoryID = String.valueOf(categoryList.get(item - 1).getId());
              tvCategory.setText(
                  String.format("Refine: %s", categoryList.get(item - 1).getName()));
            }
          }

          if (selectedCategoryID == null) {
            presenter.searchHome(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField() ,sortDirectionSearch[selectedSorting]);
          } else {
            presenter.searchProduct(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField(),sortDirectionSearch[selectedSorting]
                , selectedCategoryID);
          }
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  @OnClick(R.id.tvSort_filter)
  void onClickSort() {

    new AlertDialog.Builder(getActivity()).setTitle("SORT BY:")
        .setItems(sortingArrSearch, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            selectedSorting = item;
            tvSort.setText(String.format("Sort By: %s", sortingArrSearch[item]));

            if (selectedCategoryID == null) {
              presenter.searchHome(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField(),sortDirectionSearch[selectedSorting]);
            } else {
              presenter.searchProduct(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField(),sortDirectionSearch[selectedSorting]
                  , selectedCategoryID);
            }
          }
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  public void setUpRecyclerView() {

    int spanCount = 2;
    int spacing = 8;
    final boolean includeEdge = false;
    rv.addItemDecoration(new ProductGridSpacesItemDecoration(spanCount,
        MetricsUtils.convertDpToPixel(spacing, getContext()), includeEdge));
    rv.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
    if (isEstore) {
      estoreAdapter = new RecommendationAdapter(this, this);
      estoreAdapter.setEnableBuyNow(true);
      estoreAdapter.setActionListener(this);
      rv.setAdapter(estoreAdapter);
    } else {
      adapter = new ProductAdapter();
      adapter.setActionListener(this);
      rv.setAdapter(adapter);
    }
  }

  @Override
  public void render(SearchProductsViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        SearchProductResponse response = viewState.dataList();
        if (response.getItems().size() > 0) {
          llResult.setVisibility(View.VISIBLE);

          String count = String.valueOf(response.getItems().size());

          String ending = "\'" + keyword + "\'";
          String text = "We found " + count + " products for " + ending;
          SpannableString spannableString = new SpannableString(text);
          spannableString.setSpan(new StyleSpan(Typeface.BOLD),
              9, 9 + count.length(), 0);

          spannableString.setSpan(new StyleSpan(Typeface.BOLD),
              text.length() - ending.length(), text.length(), 0);

          tvKeyword.setText(spannableString);
          llMessage.setVisibility(View.GONE);
          List<ItemsItem> itemsItemList = new ArrayList<>();
          for (ItemsItem itemsItem : response.getItems()) {
            if (itemsItem.getStatus() == 1) {
              itemsItemList.add(itemsItem);
            }
          }
          if (isEstore) {
            estoreAdapter.setItemList(itemsItemList);
          } else {
            adapter.setDataList(itemsItemList);
          }
        } else {
          llResult.setVisibility(View.GONE);
          llMessage.setVisibility(View.VISIBLE);
          if (viewState.message().equals("1")) {
            tvMessage.setText(String.format(getString(R.string.faq_not_found), keyword));
            tvMessageInfo.setText(getString(R.string.try_search));
            tvMessageInfo.setVisibility(View.VISIBLE);
          }
          //else {
          //  tvMessage.setText(String.format(getString(R.string.faq_not_found), keyword));
          //  tvMessageInfo.setVisibility(View.VISIBLE);
          //}
        }
      } else {
        Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void showFilter(SearchProductsViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
      if (viewState.isSuccess()) {
        showCategory(viewState.category());
      } else {
        Toast.makeText(getContext(), errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void notifyFavStatusChanged(boolean isFavorite) {
    if (isEstore) {
      estoreAdapter.notifyDataSetChanged();
    } else {
      adapter.notifyDataSetChanged();
    }
  }

  @Override
  public void renderShoppingCart() {
    ShoppingCartActivity.start(this.getContext(),ShoppingCartActivity.BUY_NOW);
  }

  private void showCategory(CategoryResponse response) {

    if (selectedCategoryID == null) {
      for (int i = 0; i < categoryArr.length; i++) {
        categoryList.add(new ChildData_(categoryIDArr[i], categoryArr[i]));
      }
      presenter.searchHome(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField(),sortDirectionSearch[selectedSorting]);
    } else {
      List<ChildData> childDataList = response.getChildrenData();
      for (ChildData childData : childDataList) {
        if (childData.getId() == Integer.parseInt(selectedCategoryID)) {
          selectedCategory = childData.getName();
          categoryList = childData.getChildrenData();
          Collections.sort(categoryList, new Comparator<ChildData_>() {
            @Override
            public int compare(ChildData_ childData_1, ChildData_ childData_2) {
              return childData_1.getPosition().compareTo(childData_2.getPosition());
            }
          });

        /*categoryArr = new String[categoryList.size()];

        for (int i = 0; i < categoryList.size(); i++) {
          categoryArr[i] = categoryList.get(i).getName();
        }*/
          categoryArr = new String[categoryList.size() + 1];
          categoryArr[0] = "All";

          for (int i = 1; i <= categoryList.size(); i++) {
            categoryArr[i] = categoryList.get(i - 1).getName();
          }
        }
      }
      presenter.searchProduct(keyword, getSelectedSortField().equalsIgnoreCase(RELEVANCE) ? RELEVANCE+"_"+keyword : getSelectedSortField(),sortDirectionSearch[selectedSorting], selectedCategoryID);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == R_C_PRODUCT_DETAIL && resultCode == Activity.RESULT_OK) {
      boolean isFavourite = data.getBooleanExtra("fav_status", false);
      final int id = data.getIntExtra("item_id", -1);
      if (clickedItem != null) {
        if (clickedItem.getId() == id) {
          clickedItem.setFavourite(isFavourite);
          if (isEstore) {
            estoreAdapter.notifyDataSetChanged();
          } else {
            adapter.notifyDataSetChanged();
          }
        }
      }
    }
  }

  private String getSelectedSortField() {
    String sortField = Constants.sortFieldSearch[selectedSorting];

    if (Constants.sortingArrSearch[selectedSorting].equals("Location")) {
      Location location = getLocationFromActivity();
      sortField = Constants.sortFieldSearch[selectedSorting];
      if (location != null) {
        sortField += location.getLatitude() + "@" + location.getLongitude();
      } else {
        sortField += "0@0";
      }
    }
    return sortField;
  }

  @Nullable
  private Location getLocationFromActivity() {
    if (getActivity() instanceof LocationAwareActivity) {
      return ((LocationAwareActivity) getActivity()).location;
    }
    return null;
  }

  @Override
  public void onItemClicked(ItemsItem itemsItem, int position) {
    clickedItem = itemsItem;
    String categoryID =
        selectedCategoryID != null ? selectedCategoryID : itemsItem.getPillarId();
    if (categoryID.equals(Constants.TRAVEL)) {
      TravelDetailActivity.startMe(SearchProductsFragment.this, itemsItem,
          itemsItem.getCategoryName(), true, R_C_PRODUCT_DETAIL);
    } else if (categoryID.equals(Constants.WINE_AND_DINE)) {
      ActivityChooser.startMe(SearchProductsFragment.this, itemsItem,
          itemsItem.getCategoryName(), false, R_C_PRODUCT_DETAIL);
    } else if (categoryID.equals(Constants.SHOPPING)) {
      ActivityChooser.startMe(SearchProductsFragment.this, itemsItem,
          itemsItem.getCategoryName(), false, R_C_PRODUCT_DETAIL);
    } else if (categoryID.equals(Constants.WELLNESS)) {
      ActivityChooser.startMe(SearchProductsFragment.this, itemsItem,
          itemsItem.getCategoryName(), false, R_C_PRODUCT_DETAIL);
    } else if (categoryID.equals(Constants.E_STORE)) {
      EstoreDetailActivity.startMe(this, itemsItem, itemsItem.getCategoryName(),
          R_C_PRODUCT_DETAIL);
    }
  }

  @Override
  public void onFavItemClicked(ItemsItem itemsItem, int position) {
    if (itemsItem.isFavourite()) {
      presenter.removeFromFavourite(itemsItem);
    } else {
      presenter.addToFavourite(itemsItem);
    }
  }

  @Override
  public void onReserveButton(ItemsItem item, int position, TravelProductViewHolder holder) {

  }

  @Override
  public void hideLoading() {
    if (!isDetached() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
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
  @Override
  public void render(String massage) {
    Toast.makeText(this.getContext(), massage, Toast.LENGTH_SHORT).show();
  }
}
