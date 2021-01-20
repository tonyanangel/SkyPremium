package com.skypremiuminternational.app.app.internal.di.search;

import com.skypremiuminternational.app.app.features.search.result_keyword.SearchKeywordFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface SearchKeywordSubcomponent
    extends AndroidInjector<SearchKeywordFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<SearchKeywordFragment> {
  }
}
