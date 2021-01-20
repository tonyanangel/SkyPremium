package com.skypremiuminternational.app.data.impl;

import com.skypremiuminternational.app.data.DBManager;
import com.skypremiuminternational.app.data.DbHelper;
import com.skypremiuminternational.app.data.database.EstoreItemDao;
import com.skypremiuminternational.app.data.database.FaqItemDao;
import com.skypremiuminternational.app.domain.models.cart.EstoreItem;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Completable;
import rx.Single;

/**
 * Created by hein on 5/11/17.
 */
@Singleton
public class DBManagerImpl implements DBManager {

  private final FaqItemDao faqItemDao;
  private final EstoreItemDao estoreItemDao;
  private final DbHelper dbHelper;

  @Inject
  public DBManagerImpl(DbHelper dbHelper) {
    this.dbHelper = dbHelper;
    faqItemDao = dbHelper.getFaqItemDao();
    estoreItemDao = dbHelper.getEstoreItemDao();
  }

  @Override
  public void saveFaqItem(List<FaqItem> value) {
    for (FaqItem faqItem : value) {
      try {
        faqItemDao.createOrUpdate(faqItem);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<FaqItem> getAllFaqItem() {
    try {
      return faqItemDao.getAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<FaqItem> getFaqItemByKeyword(String keyword) {
    try {
      return faqItemDao.getAllByKeyword(keyword);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void saveEstoreItems(final List<EstoreItem> estoreItems) {
    estoreItemDao.createOrUpdate(estoreItems);
  }

  @Override
  public Completable clearEstoreItems() {
    return Completable.error(new IllegalAccessException("Not supported yet"));
  }

  @Override
  public Single<EstoreItem> getEstoreItemBySku(final String sku) {
    return Single.defer(new Callable<Single<EstoreItem>>() {
      @Override
      public Single<EstoreItem> call() throws Exception {
        EstoreItem estoreItem = estoreItemDao.getBySku(sku);
        if (estoreItem != null) {
          return Single.just(estoreItem);
        }
        return Single.error(new Exception("Estore Item is null"));
      }
    });
  }

  @Override
  public void saveEstoreItem(EstoreItem estoreItem) {
    estoreItemDao.createOrUpdate(estoreItem);
  }

  public void close() {
    dbHelper.close();
  }
}
