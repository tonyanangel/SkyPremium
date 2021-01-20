package com.skypremiuminternational.app.app.features.dummy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailImagePagerAdapter;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

  @BindView(R.id.vStatusBar)
  View vStatusBar;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  @BindView(R.id.toolbar_white)
  LinearLayout toolbar_white;
  @BindView(R.id.nsv)
  NestedScrollView nsv;
  @BindView(R.id.vpg)
  ViewPager vpg;
  @BindView(R.id.fpi)
  FlycoPageIndicaor fpi;

  int width, height;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, TestActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    ButterKnife.bind(this);

    calculateDisplay();
    vpg.getLayoutParams().height = height;

    final int min_height = (width / 16) * 9;

    nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                                 int oldScrollY) {

        int new_height = height - scrollY;
        if (new_height > min_height) {
          vpg.getLayoutParams().height = new_height;
          vpg.requestLayout();
          /*Timber.e("Min Height = %s", min_height);
          Timber.e("Scroll Y = %s", scrollY);
          Timber.e("New Height = %s", new_height);*/
        }

        //Down
        if (scrollY > oldScrollY) {

        }
        //Up
        if (scrollY < oldScrollY) {

        }
        //Top
        if (scrollY == 0) {

        }
        //End
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

        }

        if (scrollY > (vpg.getLayoutParams().height - MetricsUtils.convertDpToPixel(180,
            getApplicationContext()))) {
          if (toolbar_white.getVisibility() != View.VISIBLE) {
            vStatusBar.setVisibility(View.VISIBLE);
            toolbar_white.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
          }
        } else {
          if (toolbar.getVisibility() != View.VISIBLE) {
            vStatusBar.setVisibility(View.GONE);
            toolbar_white.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
          }
        }
      }
    });

    /*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) fpi.getLayoutParams();
    layoutParams.setMargins(0, height - (MetricsUtils.convertDpToPixel(96, this)), 0, 0);
    fpi.setLayoutParams(layoutParams);*/

    List<String> imgList = new ArrayList<>();
    imgList.add(
        "https://static.temptingplaces.com/img/fluxiom/boutique-hotel-Martinhal-Beach-Resort-Hotel-Sagres-Room-84-3-2.jpg");
    imgList.add(
        "https://static.temptingplaces.com/img/fluxiom//boutique-hotel-Vitrum-Hotel-Duplex-Suite-Living-room-2-3-2.jpg");
    imgList.add("https://s-ec.bstatic.com/images/hotel/max1024x768/416/41666469.jpg");

    vpg.setAdapter(new TravelDetailImagePagerAdapter(this, getSupportFragmentManager(), imgList));
    fpi.setViewPager(vpg);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    NavigationDialogFragment.newInstance().show(getSupportFragmentManager(), "Navigation");
  }

  /*@OnClick(R.id.img) void onClickimg() {
    Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
  }*/

  private void calculateDisplay() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;
  }
}
