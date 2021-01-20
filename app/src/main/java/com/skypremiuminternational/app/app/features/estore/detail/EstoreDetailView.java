package com.skypremiuminternational.app.app.features.estore.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.ResponseBody;

/**
 * Created by wmw on 2/6/2018.
 */

public interface EstoreDetailView<T extends Presentable> extends Viewable<T> {
  void render(EstoreDetailViewState state);

  void renderSimple(EstoreDetailViewState state);

  void notifyFavStatusChanged(boolean isFavorite, String id);

  void showRecommendationList(List<ItemsItem> items);

  void updateShoppingCartCount(String count);

  void showAddToCartFailedDialog(AddToCart.Params params, Throwable error);

  void render(Throwable throwable);

  void renderFailedComment(Throwable throwable);

  void render(String  message);

  void renderProductReview(ProductReviewResponse  reviewResponse);
  void renderRatingStar(ResponseBody responseBody);

  void renderProductReviewByPage(ProductReviewResponse productReviewResponse);

  void renderShoppingCart();

  void renderCategory(String string);

  void setContentEstoreDetail(DetailsItem itemDetail);
}
