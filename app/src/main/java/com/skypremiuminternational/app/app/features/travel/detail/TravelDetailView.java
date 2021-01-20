package com.skypremiuminternational.app.app.features.travel.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface TravelDetailView<T extends Presentable> extends Viewable<T> {

  void render(TravelDetailViewState viewState);

  void renderDetailItem(DetailsItem detailsItem);

  void renderGetDetailToGoEstore(DetailsItem detailsItem);

  void notifyFavStatusChanged(boolean isFavourite, String id);

  void showRecommededList(List<ItemsItem> items);

  void updateCartCount(String count);

  void renderGotoEstore(DetailsItem detailsItem);

  void showErrorMsg(int msg);

  void renderGetOutletFailed();

  void renderGetOutletSuccess(List<OutletItem> outletItems);
}
