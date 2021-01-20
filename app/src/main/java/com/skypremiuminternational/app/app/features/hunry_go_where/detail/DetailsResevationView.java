package com.skypremiuminternational.app.app.features.hunry_go_where.detail;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;

import java.util.List;

public interface DetailsResevationView<T extends Presentable> extends Viewable<T> {

  void renderGetOutletSuccess(List<OutletItem> list,int action);

  void renderGetOutletFailed();

  void renderError(String msg);

  void renderBookingDetail(ReserveHistoryItem reserveHistoryItem);
}
