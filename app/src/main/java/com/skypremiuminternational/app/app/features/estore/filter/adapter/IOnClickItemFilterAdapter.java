package com.skypremiuminternational.app.app.features.estore.filter.adapter;

import com.skypremiuminternational.app.domain.models.category.TreeItem;

import java.util.List;

public interface IOnClickItemFilterAdapter {
  void onClickApplyFilter(List<TreeItem> listTreeItem);
  void onCloseFilterPopup();
  void onClickCategoryItem(int positon);
}
