package com.skypremiuminternational.app.app.features.shopping.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface ShoppingDetailView<T extends Presentable> extends Viewable<T> {

  void render(ShoppingDetailViewState viewState);

  void notifyFavStatusChanged(boolean isFavourite, String id);

  void showRecommendationList(List<ItemsItem> items);

  void updateCartCount(String count);

  void renderGetDetailToGoEstore(DetailsItem detailsItem);

  void renderGotoEstore(DetailsItem detailsItem);

  void showErrorMsg(int msg);


  void renderGetOutletFailed();

  void renderGetOutletSuccess(List<OutletItem> outletItems);
}
