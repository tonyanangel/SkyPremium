package com.skypremiuminternational.app.app.features.profile.my_sky_dollar;

import com.skypremiuminternational.app.app.features.profile.ProfileViewState;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.crm.SkyDollarHistoryResponse;

public interface MySkyDollarView<T extends Presentable> extends Viewable<T> {

  void renderError(Throwable error);

  void renderCreatedToken(CrmTokenResponse value);

  void renderSkyDollarHistory(SkyDollarHistoryResponse value);
}
