package com.skypremiuminternational.app.app.features.travel.ean.suggestion;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import java.util.List;

public interface SuggestionView<T extends Presentable> extends Viewable<T> {
  void render(List<Suggestion> suggestions);

  void render(Throwable error);
}
