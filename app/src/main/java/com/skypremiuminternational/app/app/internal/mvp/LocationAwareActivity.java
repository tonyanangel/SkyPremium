package com.skypremiuminternational.app.app.internal.mvp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationRequest;
import com.skypremiuminternational.app.app.utils.LocationStream;

/**
 * Created by codigo on 6/2/18.
 */

public abstract class LocationAwareActivity<T extends BasePresenter> extends BaseActivity<T> {

  public LocationStream locationStream;

  public Location location;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    locationStream = LocationStream.with(this)
        .rationalMessage("Turn on location and search products around you")
        .fastestInterval(10000)
        .interval(60000)
        .priority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        .build();
  }

  @Override
  protected void onResume() {
    super.onResume();
    locationStream.subscribe(location -> LocationAwareActivity.this.location = location);
  }

  @Override
  protected void onPause() {
    super.onPause();
    locationStream.unSubscribe();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    locationStream.onDestroy();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    locationStream.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    locationStream.onActivityResult(requestCode, resultCode, data);
  }
}
