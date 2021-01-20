package com.skypremiuminternational.app.data.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by sandi on 11/24/16.
 */
public class FaqItemDao {
  private final Dao<FaqItem, Integer> mDao;

  public FaqItemDao(Dao<FaqItem, Integer> mDao) {
    this.mDao = mDao;
  }

  public Dao.CreateOrUpdateStatus createOrUpdate(FaqItem object) throws SQLException {
    return mDao.createOrUpdate(object);
  }

  public void insertAll(ArrayList<FaqItem> object) throws SQLException {
    for (FaqItem data : object) {
      mDao.createOrUpdate(data);
      Timber.e(String.valueOf(data));
    }
  }

  public List<FaqItem> getAll() throws SQLException {
    return mDao.queryForAll();
  }

  public List<FaqItem> getAllByKeyword(String keyword) throws SQLException {
    QueryBuilder<FaqItem, Integer> queryBuilder = mDao.queryBuilder();
    Where<FaqItem, Integer> where = queryBuilder.where();
    where.like(FaqItem.FIELD_NAME_CONTENT, "%" + keyword + "%");
    where.like(FaqItem.FIELD_NAME_TITLE, "%" + keyword + "%");
    where.or(2);
    queryBuilder.limit(Long.valueOf(20));
    queryBuilder.orderBy(FaqItem.FIELD_NAME_FAQ_ID, true);
    return queryBuilder.query();
  }
}
