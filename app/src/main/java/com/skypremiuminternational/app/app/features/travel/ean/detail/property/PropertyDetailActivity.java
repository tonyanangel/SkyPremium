package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.summary.BookingSummaryActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.shopping.detail.ImagePagerAdapter;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.room.RoomDetailDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.occupancy.OccupancyPickerDialogFragment;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.HorizontalSpacesItemDecoration;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import dagger.android.AndroidInjection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by codigo on 23/4/18.
 */

public class PropertyDetailActivity extends BaseActivity<PropertyDetailPresenter>
    implements PropertyDetailView<PropertyDetailPresenter>, OnMapReadyCallback {

  @BindView(R.id.tv_room_search_result)
  TextView tvRoomSearchResult;
  @BindView(R.id.rv_room)
  RecyclerView rvRoom;
  @BindView(R.id.ivFav)
  ImageView imgFav;
  @BindView(R.id.iv_map)
  ImageView ivMap;
  @BindView(R.id.llMarker)
  LinearLayout llMarker;
  @BindView(R.id.tvAddress)
  TextView tvAddress;
  @BindView(R.id.vStatusBar)
  View vStatusBar;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  @BindView(R.id.toolbar_white)
  LinearLayout toolbar_white;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.nsv)
  NestedScrollView nsv;
  @BindView(R.id.llImageHolder)
  LinearLayout llImageHolder;
  @BindView(R.id.vImage)
  View vImage;
  @BindView(R.id.vpg)
  ViewPager vpg;
  @BindView(R.id.fpi)
  FlycoPageIndicaor fpi;
  @BindView(R.id.tv_type)
  TextView tvType;
  @BindView(R.id.tvName)
  TextView tvName;
  @BindView(R.id.llBenefits)
  LinearLayout llBenefits;
  @BindView(R.id.tvBenefits)
  TextView tvBenefits;
  @BindView(R.id.llDescription)
  LinearLayout llDescription;
  @BindView(R.id.tvToogleDescription)
  TextView tvToogleDescription;
  @BindView(R.id.llTerms)
  LinearLayout llTerms;
  @BindView(R.id.etvTerms)
  ExpandableTextView etvTerms;
  @BindView(R.id.tv_cart_count)
  TextView tvCartCount;
  @BindView(R.id.ly_cart_count)
  FrameLayout lyCartCount;
  @BindView(R.id.iv_cart)
  ImageView ivCart;
  @BindView(R.id.ly_cart_count_white)
  FrameLayout lyCartCountWhite;
  @BindView(R.id.tv_cart_count_white)
  TextView tvCartCountWhite;
  @BindView(R.id.tvExcerpt)
  ExpandableTextView tvExcerpt;
  @BindView(R.id.tvDescription)
  TextView tvDescription;
  @BindView(R.id.tv_check_out_date)
  TextView tvCheckOutDate;
  @BindView(R.id.tv_check_in_date)
  TextView tvCheckInDate;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private RoomAdapter roomAdapter;

  private ProgressDialog progressDialog;
  private int width, height;
  private GoogleMap mMap;
  // also keep a reference in activity for the ease of rendering map info when asynchronous onMapReady triggers

  public static void start(Context context,
                           String propertyId, int roomCount, int adultCount, ArrayList<Child> children,
                           List<CalendarDay> selectedDays) {
    Intent starter = new Intent(context, PropertyDetailActivity.class);
    starter.putExtra("property_id", propertyId);
    starter.putExtra("room_count", roomCount);
    starter.putExtra("adult_count", adultCount);
    starter.putParcelableArrayListExtra("children", children);
    starter.putParcelableArrayListExtra("selected_days",
        (ArrayList<? extends Parcelable>) selectedDays);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_property_detail);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    String propertyId = intent.getStringExtra("property_id");
    int roomCount = intent.getIntExtra("room_count", -1);
    int adultCount = intent.getIntExtra("adult_count", -1);
    List<Child> children = intent.getParcelableArrayListExtra("children");
    List<CalendarDay> selectedDates = intent.getParcelableArrayListExtra("selected_days");

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));

    setScrollListener();
    setUpRecyclerView();

    presenter.setInitValues(propertyId, roomCount, adultCount, children, selectedDates);
    ivMap.setOnClickListener(view -> presenter.collectLatLng());
    setFavStatus();
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    presenter.searchRooms();
  }

  @Inject
  @Override
  public void injectPresenter(PropertyDetailPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.tv_check_in_date)
  void onCheckInDateClicked() {
    presenter.collectSelectedDates();
  }

  @OnClick(R.id.tv_check_out_date)
  void onCheckOutDateClicked() {
    presenter.collectSelectedDates();
  }

  @OnClick(R.id.tv_occupancy)
  void onOccupancyClicked() {
    presenter.collectOccupancyValues();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.getUiSettings().setAllGesturesEnabled(false);

    try {
      googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
    } catch (Resources.NotFoundException e) {
      Timber.e(e);
    }

    presenter.onMapReady();
  }

  @Override
  public void renderMap(String address, double lat, double lng) {
    tvAddress.setText(address);
    tvAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf"));

    llMarker.post(() -> {

      llMarker.setDrawingCacheEnabled(true);
      llMarker.buildDrawingCache();
      Bitmap marker =
          Bitmap.createBitmap(llMarker.getMeasuredWidth(), llMarker.getMeasuredHeight(),
              Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(marker);
      llMarker.draw(canvas);

      final LatLng latLng = new LatLng(lat, lng);

      mMap.addMarker(
          new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(marker)));
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    });
  }

  @Override
  public void showCancellationPolicy(CancelPenalty cancelPenalty, String roomAndType,
                                     String propertyName) {
    CancellationPolicyDialogFragment.newInstance(cancelPenalty, roomAndType,
        propertyName,null,null)
        .show(getSupportFragmentManager(), "PropertyDetail");
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    finish();
  }

  @OnClick(R.id.ivNavigation_toolbar_white)
  void onClickBackWhite() {
    finish();
  }

  @OnClick(R.id.llToogleDescription)
  void onClickToogleDescription() {
    final String readMore = getString(R.string.read_more);
    final String readLess = getString(R.string.read_less);
    if (tvToogleDescription.getText().toString().equals(readMore)) {
      tvExcerpt.setVisibility(View.GONE);
      tvDescription.setVisibility(View.VISIBLE);
      AnimationUtil.expand(tvDescription, new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          tvToogleDescription.setText(readLess);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
    } else if (tvToogleDescription.getText().toString().equals(readLess)) {
      AnimationUtil.collapse(tvDescription, new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          tvDescription.setVisibility(View.GONE);
          tvExcerpt.setVisibility(View.VISIBLE);
          tvToogleDescription.setText(readMore);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getCartCount();
  }

  @Override
  public void render(@NonNull String startDate, @NonNull String endDate) {
    tvCheckInDate.setText(startDate);

    if (TextUtils.isEmpty(endDate)) {
      tvCheckOutDate.setText(R.string.all_not_selected_label);
    } else {
      tvCheckOutDate.setText(endDate);
    }
  }

  @Override
  public void clearDateRange() {
    tvCheckOutDate.setText(R.string.all_not_selected_label);
    tvCheckInDate.setText(R.string.all_not_selected_label);
  }

  @Override
  public void showOccupancyPickerDialog(int roomCount, int adultCount, List<Child> children) {
    OccupancyPickerDialogFragment fragment =
        OccupancyPickerDialogFragment.newInstance(roomCount, adultCount,
            children);
    fragment.setOnDoneClickListener(
        (int roomCount1, int adultCount1, List<Child> children1) -> {
          presenter.setOccupancyValues(roomCount1,
              adultCount1, (ArrayList<Child>) children1);
          hideRoomSearchResult();
        });
    fragment.show(getSupportFragmentManager(), "OccupancyPicker");
  }

  private void hideRoomSearchResult() {
    tvRoomSearchResult.setText("");
    rvRoom.setVisibility(View.GONE);
  }

  @Override
  public void render(int roomCount, int adultCount, List<Child> children) {
    Resources resources = getResources();
    String occupancy = resources.getQuantityString(R.plurals.adults, adultCount, adultCount)
        + ", "
        + resources.getQuantityString(R.plurals.children, children.size(), children.size())
        + ", "
        + resources.getQuantityString(R.plurals.rooms, roomCount, roomCount);
    tvOccupancy.setText(occupancy);
  }

  @Override
  public void render(Property property, int roomCount, int adultCount, CalendarDay checkInDate,
                     CalendarDay checkOutDate, int totalDays, List<Child> children) {
    bindProperty(property);
    bindRooms(property);
    render(roomCount, adultCount, children);
    setupRoomBookData(checkInDate, checkOutDate);
  }

  @Override
  public void goToMap(Double lat, Double lng, String address) {
    String uri =
        "geo:0,0?q=" + lat + "," + lng + "(" + address + ")";
    Timber.e("Uri " + uri);
    Uri gmmIntentUri = Uri.parse(uri);
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    if (mapIntent.resolveActivity(getPackageManager()) != null) {
      startActivity(mapIntent);
    }
  }

  @Override
  public void goToBookingSummary(Property property, Room room, int roomCount, int adultCount,
                                 ArrayList<Child> children, CalendarDay checkInDate, CalendarDay checkOutDate, int totalDays) {
    BookingSummaryActivity.start(PropertyDetailActivity.this, property, room, roomCount,
        adultCount, children, checkInDate, checkOutDate, totalDays);
  }

  @Override
  public void showDateRangePicker(List<CalendarDay> selectedDates) {
    DateRangePickerDialogFragment fragment =
        DateRangePickerDialogFragment.newInstance((ArrayList<CalendarDay>) selectedDates);
    fragment.setOnDateRangeSelectListener(
        dates -> {
          presenter.changeDateRange(dates);
          hideRoomSearchResult();
        });
    fragment.show(getSupportFragmentManager(), "DateRangePicker");
  }

  @Override
  public void renderRoomSearchError(Throwable error) {
    rvRoom.setVisibility(View.GONE);
    tvRoomSearchResult.setText(errorMessageFactory.getErrorMessage(
        new Exception(getString(R.string.property_detail_search_room_error))));
  }

  @Override
  public void updateCartCount(String cartCount) {
    if (cartCount.equals("")) {
      lyCartCount.setVisibility(View.VISIBLE);
      lyCartCountWhite.setVisibility(View.VISIBLE);
      tvCartCount.setVisibility(View.INVISIBLE);
      tvCartCountWhite.setVisibility(View.INVISIBLE);
    } else {
      if (cartCount.equalsIgnoreCase("0")) {
        lyCartCount.setVisibility(View.INVISIBLE);
        lyCartCountWhite.setVisibility(View.INVISIBLE);
      } else {
        lyCartCount.setVisibility(View.VISIBLE);
        lyCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setVisibility(View.VISIBLE);
        tvCartCountWhite.setVisibility(View.VISIBLE);
        tvCartCount.setText(cartCount);
        tvCartCountWhite.setText(cartCount);
      }
    }
  }

  @OnClick(R.id.btn_search_rooms)
  void onSearchRoomsClicked() {
    presenter.searchRooms();
  }

  @Override
  public void showLoading() {
    if (isDestroyed()) return;

    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    if (progressDialog.isShowing()) progressDialog.dismiss();
  }

  private void setupRoomBookData(CalendarDay checkInDate, CalendarDay checkOutDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
        Locale.getDefault());
    String formattedCheckIn = dateFormat.format(checkInDate.getDate());
    String formattedCheckOut = dateFormat.format(checkOutDate.getDate());

    tvCheckInDate.setText(formattedCheckIn);
    tvCheckOutDate.setText(formattedCheckOut);
  }

  private void bindProperty(Property property) {
    tvTitle_toolbar.setText(property.name());
    tvName.setText(property.name());
    tvAddress.setText(property.address());
    String type = property.type() + " - " + property.city();
    tvType.setText(type);

    tvExcerpt.setText(Html.fromHtml(property.description()));
    tvDescription.setText(Html.fromHtml(property.description()));

    if (property.carouselImages() != null && property.carouselImages().size() > 0) {
      vpg.setAdapter(
          new ImagePagerAdapter(this, getSupportFragmentManager(), property.carouselImages()));
      fpi.setViewPager(vpg);
      if (property.carouselImages().size() == 1) {
        fpi.setVisibility(View.INVISIBLE);
      }
    }
  }

  private void bindRooms(Property property) {
    if (property.rooms() != null && property.rooms().size() > 0) {
      String size = String.valueOf(property.roomCount());
      String text = getString(R.string.property_detail_room_search_result_label);
      text = size + " " + text;
      SpannableString spannableString = new SpannableString(text);
      spannableString.setSpan(new StyleSpan(Typeface.BOLD),
          0, size.length(), 0);

      tvRoomSearchResult.setText(spannableString);
      rvRoom.setVisibility(View.VISIBLE);
      roomAdapter.setRooms(property.rooms());
    } else {
      rvRoom.setVisibility(View.GONE);
      tvRoomSearchResult.setText(R.string.property_detail_empty_room_result);
    }
  }

  private void setScrollListener() {

    calculateDisplay();
    llImageHolder.getLayoutParams().height = height;
    final int min_height =
        ((width / 16) * 9) + MetricsUtils.convertDpToPixel(112, getApplicationContext());
    final int max_height = height - min_height;

    nsv.setOnScrollChangeListener(
        (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

          if (scrollY < max_height) {
            vImage.getLayoutParams().height = scrollY;
            vpg.requestLayout();
          }

          if (scrollY > (height - MetricsUtils.convertDpToPixel(112, getApplicationContext()))) {
            if (toolbar_white.getVisibility() != View.VISIBLE) {
              vStatusBar.setVisibility(View.VISIBLE);
              toolbar_white.setVisibility(View.VISIBLE);
              toolbar.setVisibility(View.GONE);
              setFavStatus();
            }
          } else if (toolbar.getVisibility() != View.VISIBLE) {
            vStatusBar.setVisibility(View.GONE);
            toolbar_white.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            setFavStatus();
          }
        });
  }

  private void calculateDisplay() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;
  }

  private void setUpRecyclerView() {
    roomAdapter = new RoomAdapter();
    roomAdapter.setActionListener(new RoomAdapter.ActionListener() {
      @Override
      public void onShowDetailClicked(Room room) {
        RoomDetailDialogFragment.newInstance(room)
            .show(getSupportFragmentManager(), "PropertyDetail");
      }

      @Override
      public void onBookNowClicked(Room room) {
        presenter.collectBookingValues(room);
      }

      @Override
      public void onShowCancellationPolicyClicked(Room room) {
        presenter.collectNamesToShowCancellationPolicy(room);
      }
    });
    rvRoom.setAdapter(roomAdapter);
    HorizontalSpacesItemDecoration itemDecoration =
        new HorizontalSpacesItemDecoration(MetricsUtils.convertDpToPixel(16, this), false);
    rvRoom.addItemDecoration(itemDecoration);
    rvRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
  }

  private void setFavStatus() {
    boolean isFavourite = false;
    int favIcon;
    int cartIcon;

    if (toolbar_white.getVisibility() == View.VISIBLE) {
      favIcon = isFavourite ? R.drawable.ic_favourite_fill_gold
          : R.drawable.ic_favourite_stroke_gold;
      cartIcon = R.drawable.ic_cart_accent;
    } else {
      favIcon = isFavourite ? R.drawable.ic_favourite_fill_white
          : R.drawable.ic_favourite_stroke_white;
      cartIcon = R.drawable.ic_cart;
    }
    imgFav.setImageResource(favIcon);
    ivCart.setImageResource(cartIcon);
  }

  @OnClick(R.id.iv_cart)
  void onCartClicked() {
    ShoppingCartActivity.start(this);
  }
}
