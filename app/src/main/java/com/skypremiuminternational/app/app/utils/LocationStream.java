package com.skypremiuminternational.app.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skypremiuminternational.app.R;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

import static android.app.Activity.RESULT_OK;

/**
 * Created by khunzohn on 11/29/17.
 * This class takes the responsibility of
 * <p>
 * -checking google play services availability
 * -checking and requesting location permission
 * -checking location setting and
 * -listening to a stream of location updates
 */

public final class LocationStream {

  private static final int REQUEST_PLAY_SERVICES_RESOLUTION = 712;
  private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 923;
  private static final int REQUEST_CHECK_SETTINGS = 334;

  private final FusedLocationProviderClient locationClient;
  private final BehaviorSubject<Location> locationSubject;
  private final LocationRequest locationRequest;
  private final CompositeSubscription compositeSubscription = new CompositeSubscription();
  private final String rationaleMessage;
  private LocationCallback locationCallback;
  private Activity context;

  private LocationStream(final Activity activity, String rationaleMessage, int interval,
                         int fastestInterval, int priority) {

    this.context = activity;
    this.rationaleMessage = rationaleMessage;

    locationRequest = new LocationRequest();
    locationRequest.setInterval(interval);
    locationRequest.setFastestInterval(fastestInterval);
    locationRequest.setPriority(priority);

    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        locationSubject.onNext(locationResult.getLastLocation());
      }
    };

    locationClient = LocationServices.getFusedLocationProviderClient(activity);

    locationSubject = BehaviorSubject.create();

    checkPlayServiceAvailability();
  }

  public static Builder with(Activity activity) {
    return new Builder(activity);
  }

  /**
   * call it onResume() of activity
   * asObservable to location updates
   *
   * @param action1 action to be performed on the call side when an updated location is emitted
   */
  public void subscribe(Action1<Location> action1) {
    compositeSubscription.add(locationSubject.subscribe(action1));
    if (isPermissionGranted() && isPlayServicesAvailable()) {
      listenToLocationUpdates();
    }
  }

  /**
   * call it onPause() of activity
   */
  public void unSubscribe() {
    if (compositeSubscription.hasSubscriptions()) {
      compositeSubscription.clear();
    }
    stopListenToLocationUpdates();
  }

  public boolean isPermissionGranted() {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
  }

  private boolean isPlayServicesAvailable() {
    return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        == ConnectionResult.SUCCESS;
  }

  private void checkPlayServiceAvailability() {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int result = apiAvailability.isGooglePlayServicesAvailable(context);
    if (result == ConnectionResult.SUCCESS) {
      checkAndRequestLocationPermission();
    } else {
      if (apiAvailability.isUserResolvableError(result)) {
        apiAvailability.getErrorDialog(context, result, REQUEST_PLAY_SERVICES_RESOLUTION).show();
      }
    }
  }

  private void checkLocationSetting() {
    if (locationRequest == null) return;

    LocationSettingsRequest.Builder builder =
        new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

    SettingsClient client = LocationServices.getSettingsClient(context);
    client.checkLocationSettings(builder.build())
        .addOnSuccessListener(context, new OnSuccessListener<LocationSettingsResponse>() {
          @Override
          public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            // listening to location updates will be handled by asObservable() method
          }
        })
        .addOnFailureListener(context, new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
              case CommonStatusCodes.RESOLUTION_REQUIRED:
                try {
                  ResolvableApiException resolvable = (ResolvableApiException) e;
                  resolvable.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                  // Ignore the error.
                }
                break;
              case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // no way to fix the settings so we won't show the dialog.
                break;
            }
          }
        });
  }

  private void checkAndRequestLocationPermission() {
    if (!isPermissionGranted()) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(context,
          Manifest.permission.ACCESS_FINE_LOCATION)) {
        showRationaleAndRequestPermission();
      } else {
        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            PERMISSIONS_REQUEST_FINE_LOCATION);
      }
    } else {
      checkLocationSetting();
    }
  }

  private void showRationaleAndRequestPermission() {
    showSnack(rationaleMessage, R.string.label_dimiss, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //ignored
      }
    });

    requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        PERMISSIONS_REQUEST_FINE_LOCATION);
  }

  private void showSnack(String message, int actionMessage, View.OnClickListener onClickListener) {
    Snackbar.make(context.getWindow().getDecorView().getRootView(), message,
        Snackbar.LENGTH_INDEFINITE)
        .setAction(actionMessage, onClickListener)
        .setActionTextColor(context.getResources().getColor(R.color.colorPrimary))
        .show();
  }

  private void requestPermission(String[] permissions, int requestCode) {
    ActivityCompat.requestPermissions(context, permissions, requestCode);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_PLAY_SERVICES_RESOLUTION && resultCode == RESULT_OK) {
      checkAndRequestLocationPermission();
    }
  }

  public void onRequestPermissionsResult(int requestCode, String permissions[],
                                         int[] grantResults) {
    switch (requestCode) {
      case PERMISSIONS_REQUEST_FINE_LOCATION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          checkLocationSetting();
        }
      }
    }
  }

  @SuppressLint("MissingPermission")
  public void listenToLocationUpdates() {
    locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
  }

  /*
   * call it onDestroy() of activity
   * */
  public void onDestroy() {
    locationCallback = null;
    context = null;
  }

  private void stopListenToLocationUpdates() {
    locationClient.removeLocationUpdates(locationCallback);
  }

  public static class Builder {
    private Activity context;
    private String rationaleMessage;
    private int interval, fastestInterval, priority;

    Builder(Activity activity) {
      this.context = activity;
    }

    public Builder rationalMessage(String rationaleMessage) {
      this.rationaleMessage = rationaleMessage;
      return this;
    }

    public Builder interval(int interval) {
      this.interval = interval;
      return this;
    }

    public Builder fastestInterval(int fastestInterval) {
      this.fastestInterval = fastestInterval;
      return this;
    }

    public Builder priority(int priority) {
      this.priority = priority;
      return this;
    }

    public LocationStream build() {
      return new LocationStream(context, rationaleMessage, interval, fastestInterval, priority);
    }
  }
}
