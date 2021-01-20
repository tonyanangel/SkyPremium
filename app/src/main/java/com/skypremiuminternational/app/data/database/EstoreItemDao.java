package com.skypremiuminternational.app.data.database;

import com.j256.ormlite.dao.Dao;
import com.skypremiuminternational.app.domain.models.cart.EstoreItem;

import java.sql.SQLException;
import java.util.List;

import timber.log.Timber;

/**
 * Created by codigo on 6/3/18.
 */

public class EstoreItemDao {

  private final Dao<EstoreItem, String> dao;

  public EstoreItemDao(Dao<EstoreItem, String> dao) {
    this.dao = dao;
  }

  public void createOrUpdate(EstoreItem estoreItem) {
    try {
      dao.createOrUpdate(estoreItem);
    } catch (SQLException e) {
      Timber.e(e);
    }
  }

  public void createOrUpdate(List<EstoreItem> estoreItems) {
    for (EstoreItem estoreItem : estoreItems) {
      createOrUpdate(estoreItem);
    }
  }

  public EstoreItem getBySku(String sku) throws SQLException {
    return dao.queryForId(sku);
  }
}
