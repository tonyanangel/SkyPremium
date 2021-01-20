package com.skypremiuminternational.app.app.features.estore.detail.adapter;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.skypremiuminternational.app.app.features.estore.detail.FragmentImageItem;

import java.util.List;

/**
 * Created by johnsonmaung on 7/22/17.
 */

public class ImagePagerAdapter extends FragmentPagerAdapter {
  AppCompatActivity mActivity;
  List<String> dataset;

  public ImagePagerAdapter(Activity mActivity, FragmentManager fragmentManager,
                           List<String> dataset) {
    super(fragmentManager);
    this.mActivity = (AppCompatActivity) mActivity;
    this.dataset = dataset;
  }

  // Returns total number of pages
  @Override
  public int getCount() {
    return dataset.size();
  }

  // Returns the fragment to display for that page
  @Override
  public Fragment getItem(int position) {
    return FragmentImageItem.newInstance(dataset, position);
  }

  // Returns the page title for the top indicator
  @Override
  public CharSequence getPageTitle(int position) {
    return null;
  }
}