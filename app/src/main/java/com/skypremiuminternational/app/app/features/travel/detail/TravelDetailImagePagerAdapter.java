package com.skypremiuminternational.app.app.features.travel.detail;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Created by johnsonmaung on 7/22/17.
 */

public class TravelDetailImagePagerAdapter extends FragmentPagerAdapter {
  AppCompatActivity mActivity;
  List<String> dataset;

  public TravelDetailImagePagerAdapter(Activity mActivity, FragmentManager fragmentManager,
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
    return TravelDetailFragmentImageItem.newInstance(dataset, position);
  }

  // Returns the page title for the top indicator
  @Override
  public CharSequence getPageTitle(int position) {
    return null;
  }
}