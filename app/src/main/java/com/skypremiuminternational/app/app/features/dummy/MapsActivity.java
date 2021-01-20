package com.skypremiuminternational.app.app.features.dummy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skypremiuminternational.app.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;

  @BindView(R.id.llMarker)
  LinearLayout llMarker;
  @BindView(R.id.tvAddress)
  TextView tvAddress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    ButterKnife.bind(this);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    //mMap.getUiSettings().setAllGesturesEnabled(false);

    try {
      // Customise the styling of the base map using a JSON object defined
      // in a raw resource file.
      boolean success =
          googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

      if (!success) {
        //success
      }
    } catch (Resources.NotFoundException e) {
      //Log.e(TAG, "Can't find style. Error: ", e);
    }

    tvAddress.setText(getString(R.string.sample_product_description));
    //tvAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf"));

    llMarker.post(new Runnable() {
      @Override
      public void run() {

        //Bitmap bitmap = getBitmapFromView(llMarker);

        llMarker.setDrawingCacheEnabled(true);
        llMarker.buildDrawingCache();
        Bitmap bitmap = llMarker.getDrawingCache();

        final LatLng MELBOURNE = new LatLng(-37.813, 144.962);
        Marker melbourne = mMap.addMarker(new MarkerOptions().position(MELBOURNE)
            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));
      }
    });
  }

  public static Bitmap getBitmapFromView(View view) {
    view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
        Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    view.draw(c);
    return b;
  }
}
