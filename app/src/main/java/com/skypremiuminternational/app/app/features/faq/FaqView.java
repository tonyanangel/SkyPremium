package com.skypremiuminternational.app.app.features.faq;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface FaqView<T extends Presentable> extends Viewable<T> {

  void render(FaqViewState viewState);

  void updateCartCount(String count);
}
