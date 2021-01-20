package com.skypremiuminternational.app.app.features.estore;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.interactor.category.GetFilterProducts;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.FilterListResponse;
import com.skypremiuminternational.app.domain.models.feature.FeatureProduct;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.skypremiuminternational.app.domain.models.wellness.ProductListResponse;

import org.json.JSONException;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface EstoreView<T extends Presentable> extends Viewable<T> {

  void notifyFavStatusChanged(boolean featuredItem, int position);

  void showLoading(String message);

  void render(Throwable error);

  void render(CategoryDetailsResponse categoryDetails);

  void render(CategoryResponse category);

  void render(FeatureProduct featureProduct);
  
  void render(ProductListResponse productList);
  
  void renderFilter(ProductListResponse productList);

  void renderFilterMore(ProductListResponse productList);

  void renderTreeCategory(String strJson) throws JSONException;

  void renderTreeCategoryLocal(String strJson) throws JSONException;

  void renderTreeCategoryMini(String strJson) throws JSONException;

  void renderTreeCategory(String strJson,String selectId) throws JSONException;

  void renderSrlRefreshFalse();

  void renderShoppingCart();

  void updateCartCount(String count);
}
