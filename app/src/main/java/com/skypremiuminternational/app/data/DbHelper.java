package com.skypremiuminternational.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.skypremiuminternational.app.data.database.EstoreItemDao;
import com.skypremiuminternational.app.data.database.FaqItemDao;
import com.skypremiuminternational.app.domain.models.cart.EstoreItem;
import com.skypremiuminternational.app.domain.models.faq.FaqItem;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by sandi on 1/17/17.
 */

@Singleton
public class DbHelper extends OrmLiteSqliteOpenHelper {

  private static final String DATABASE_NAME = "skypremium.db";
  private static final int DATABASE_VERSION = 1;

  private EstoreItemDao estoreItemDao;
  private FaqItemDao faqItemDao;

  @Inject
  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTableIfNotExists(connectionSource, FaqItem.class);
      TableUtils.createTableIfNotExists(connectionSource, EstoreItem.class);
    } catch (SQLException e) {
      Timber.e(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
                        int newVersion) {
    try {
      TableUtils.dropTable(connectionSource, FaqItem.class, true);
      TableUtils.dropTable(connectionSource, EstoreItem.class, true);
      onCreate(database, connectionSource);
    } catch (SQLException e) {
      Timber.e(e);
    }
  }

  @Override
  public void close() {
    super.close();
    faqItemDao = null;
    estoreItemDao = null;
  }

  public EstoreItemDao getEstoreItemDao() {
    if (estoreItemDao == null) {
      try {
        Dao<EstoreItem, String> dao = getDao(EstoreItem.class);
        estoreItemDao = new EstoreItemDao(dao);
      } catch (SQLException e) {
        Timber.e(e);
      }
    }
    return estoreItemDao;
  }

  public FaqItemDao getFaqItemDao() {
    if (faqItemDao == null) {
      try {
        Dao<FaqItem, Integer> faqDao = getDao(FaqItem.class);
        faqItemDao = new FaqItemDao(faqDao);
      } catch (SQLException e) {
        Timber.e(e);
      }
    }
    return faqItemDao;
  }
}
