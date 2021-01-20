package com.skypremiuminternational.app.app.features.landing.popup;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.skypremiuminternational.app.domain.models.popup.PopupItem;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends FragmentPagerAdapter {

  Activity activity;
  int count = 12;

  List<Fragment> fragmentList;

  public SlideAdapter(FragmentManager fragmentManager, Activity activity, List<PopupItem> popupItemList){
    super(fragmentManager);
    this.activity = activity;
    fragmentList = new ArrayList<>();


    for( PopupItem item : popupItemList){
      if(item.getType().equalsIgnoreCase("youtube")){
        fragmentList.add(FragmentYoutube.newInstance(this.activity,item.getVideoId()));
      }else if(item.getType().equalsIgnoreCase("vimeo")){
        fragmentList.add(FragmentVimeo.newInstance(item.getVideoId()));
      }else if(item.getType().equalsIgnoreCase("local")){
        fragmentList.add(FragmentVideo.newInstance(this.activity,item.getUrl()));
      }else if(item.getType().equalsIgnoreCase("image")){
        fragmentList.add(FragmentImage.newInstance(item.getUrl()));
      }
    }
  }


  @NonNull
  @Override
  public Fragment getItem(int position) {


    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  void setData(){
    this.fragmentList.clear();
    notifyDataSetChanged();
  }

  public void setCount(int count ){
    this.count  =  count;
    notifyDataSetChanged();
  }
}