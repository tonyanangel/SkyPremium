package com.skypremiuminternational.app.app.features.search;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface SearchView<T extends Presentable> extends Viewable<T> {

  void render(SearchViewState viewState);
}
