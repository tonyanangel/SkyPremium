package com.skypremiuminternational.app.data;

import com.skypremiuminternational.app.domain.models.cart.EstoreItem;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;

import java.util.List;

import rx.Completable;
import rx.Single;

/**
 * Created by hein on 5/11/17.
 */

public interface DBManager {

  void saveFaqItem(List<FaqItem> value);

  List<FaqItem> getAllFaqItem();

  List<FaqItem> getFaqItemByKeyword(String keyword);

  void saveEstoreItems(List<EstoreItem> estoreItems);

  Completable clearEstoreItems();

  Single<EstoreItem> getEstoreItemBySku(String sku);

  void saveEstoreItem(EstoreItem estoreItem);
}
