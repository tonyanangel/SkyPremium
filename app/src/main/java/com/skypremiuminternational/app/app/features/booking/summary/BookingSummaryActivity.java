package com.skypremiuminternational.app.app.features.booking.summary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutActivity;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutPresenter;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.property.AmenityAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import dagger.android.AndroidInjection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

public class BookingSummaryActivity extends BaseActivity<BookingSummaryPresenter> implements
    BookingSummaryView<BookingSummaryPresenter> {

  private static final int REQUEST_CODE_CHECKOUT = 123;

  @BindView(R.id.rv_amenity)
  RecyclerView rvAmenity;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.tvTitle_toolbar_amount)
  TextView tvTitleToolbarAmount;
  @BindView(R.id.tv_hotel_name)
  TextView tvHotelName;
  @BindView(R.id.tv_hotel_area)
  TextView tvHotelArea;
  @BindView(R.id.tv_room_type)
  TextView tvRoomType;
  @BindView(R.id.tv_bed_type)
  TextView tvBedType;
  @BindView(R.id.iv_room)
  ImageView ivRoom;
  @BindView(R.id.tv_booking_date_range)
  TextView tvBookingDateRange;
  @BindView(R.id.tv_day_count)
  TextView tvDayCount;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;
  @BindView(R.id.tv_occupancy_detail)
  TextView tvOccupancyDetail;
  @BindView(R.id.rv_room_price)
  RecyclerView rvRoomPrice;
  @BindView(R.id.tv_subtotal)
  TextView tvSubtotal;
  @BindView(R.id.tv_grand_total)
  TextView tvGrandTotal;
  @BindView(R.id.tv_not_inlcude_in_price)
  TextView tvNotIncludeInPrice;
  @BindView(R.id.rvTourismFees)
  RecyclerView rvTourismFees;
  @BindView(R.id.tv_sky_dollar)
  TextView tvSkyDollar;

  private Property property;
  private Room room;
  private AmenityAdapter amenityAdapter;
  private RoomPriceAdapter roomPriceAdapter;
  private TourismFeeRecyclerAdapter tourismFeeRecyclerAdapter;
  private int roomCount;
  private int adultCount;
  private List<Child> children;
  private CalendarDay checkOutDate;
  private CalendarDay checkInDate;
  private int totalDays;
  private List<GuestDetail> guestDetailsforplacedlist = new ArrayList<>();
  private List<TourismFee> fees = new ArrayList<>();
  @Inject
  OccupancyArranger occupancyArranger;
  @Inject
  ErrorMessageFactory errorMessageFactory;
  private ProgressDialog progressDialog;

  public static void start(Context context, Property property, Room room, int roomCount,
                           int adultCount, ArrayList<Child> children, CalendarDay checkInDate,
                           CalendarDay checkOutDate, int totalDays) {
    Intent starter = new Intent(context, BookingSummaryActivity.class);
    starter.putExtra("property", property);
    starter.putExtra("room", room);
    starter.putExtra("room_count", roomCount);
    starter.putExtra("adult_count", adultCount);
    starter.putParcelableArrayListExtra("children", children);
    starter.putExtra("check_in_date", checkInDate);
    starter.putExtra("check_out_date", checkOutDate);
    starter.putExtra("total_days", totalDays);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_summary);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    room = intent.getParcelableExtra("room");
    property = intent.getParcelableExtra("property");
    roomCount = intent.getIntExtra("room_count", -1);
    adultCount = intent.getIntExtra("adult_count", -1);
    children = intent.getParcelableArrayListExtra("children");
    checkInDate = intent.getParcelableExtra("check_in_date");
    checkOutDate = intent.getParcelableExtra("check_out_date");
    totalDays = intent.getIntExtra("total_days", -1);
    tvTitleToolbar.setText(R.string.booking_summary_toolbar_title);

    setUpRecyclerView();

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));

    render();
    String range = formatDateRange(checkInDate,checkOutDate);
    Resources res = getResources();
    String totalNights = res.getQuantityString(R.plurals.nights, totalDays - 1, totalDays - 1);

    presenter.setValues(room, roomCount, adultCount,
        children, property, checkInDate, checkOutDate,range,totalNights);

    presenter.checkPrice();
  }

  public void render() {
    tvBookingDateRange.setText(formatDateRange(checkInDate, checkOutDate));
    tvHotelName.setText(property.name());
    tvHotelArea.setText(property.address());
    if (room.name() != null) {
      tvRoomType.setText(room.name());
    }
    String bedType = getResources().getQuantityString(R.plurals.rooms, roomCount, roomCount);
    bedType = room.type() + " (" + bedType + ")";
    tvBedType.setText(bedType);
    Resources res = getResources();
    String totalNights = res.getQuantityString(R.plurals.nights, totalDays - 1, totalDays - 1);
    int guestCount = adultCount + children.size();
    String totalGuests = res.getQuantityString(R.plurals.guests, guestCount, guestCount);

    String occupancyDetail = occupancyArranger.arrangeTotalGuest(adultCount, children);

    tvDayCount.setText(totalNights);
    tvOccupancy.setText(totalGuests);
    tvOccupancyDetail.setText(occupancyDetail);

    if (room.image() != null && !TextUtils.isEmpty(room.image())) {

      RequestOptions requestOptions = new RequestOptions();
      requestOptions.placeholder(R.drawable.placeholder);
      requestOptions.dontAnimate();
      requestOptions.centerCrop();
      requestOptions.error(R.drawable.white);
      Glide.with(this)
          .load(room.image())
          .apply(requestOptions)
          .into(ivRoom);
    }

    amenityAdapter.room = room;
    amenityAdapter.setAmenities(room.amenities());
  }

  private String formatDateRange(CalendarDay checkInDate, CalendarDay checkOutDate) {

    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_RANGE_FORMAT,
        Locale.getDefault());
    SimpleDateFormat dayFormat = new SimpleDateFormat(Constants.DAY_RANGE_FORMAT,
        Locale.getDefault());
    final String formattedCheckIn = dateFormat.format(checkInDate.getDate());
    final String formattedCheckOut = dateFormat.format(checkOutDate.getDate());

    if (checkInDate.getYear() != checkOutDate.getYear()) {
      return formattedCheckIn + " - " + formattedCheckOut;
    } else if (checkInDate.getMonth() != checkOutDate.getMonth()) {
      return dayFormat.format(checkInDate.getDay())
          + " "
          + formattedCheckIn.split(" ")[1]
          + " - "
          + formattedCheckOut;
    } else {
      if(String.valueOf(checkInDate.getDay()).length() > 1){
        return String.valueOf(checkInDate.getDay()) + " - " + formattedCheckOut;
      }else {
        return "0"+String.valueOf(checkInDate.getDay()) + " - " + formattedCheckOut;
      }
    }

  }

  private void setUpRecyclerView() {
    amenityAdapter = new AmenityAdapter();
    amenityAdapter.setActionListener(amenity -> {
      if (amenity.isCancellationPolicy()) {
        String roomAndType = "";
        if (room.name() != null) {
          roomAndType = room.name() + " - ";
        }
        roomAndType += room.type();
        CancellationPolicyDialogFragment.newInstance(room.cancelPenalty(), roomAndType,
            property.name(),null,null)
            .show(getSupportFragmentManager(), "BookingSummary");
      }
    });
    rvAmenity.setAdapter(amenityAdapter);
    rvAmenity.setLayoutManager(new LinearLayoutManager
        (this, LinearLayoutManager.VERTICAL, false));
    roomPriceAdapter = new RoomPriceAdapter();
    rvRoomPrice.setHasFixedSize(true);
    rvRoomPrice.setNestedScrollingEnabled(false);
    rvRoomPrice.setAdapter(roomPriceAdapter);
    rvRoomPrice.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    tourismFeeRecyclerAdapter = new TourismFeeRecyclerAdapter();
    rvTourismFees.setHasFixedSize(true);
    rvTourismFees.setNestedScrollingEnabled(false);
    rvTourismFees.setLayoutManager(new LinearLayoutManager(this));
    rvTourismFees.setAdapter(tourismFeeRecyclerAdapter);
  }

  @Inject
  @Override
  public void injectPresenter(BookingSummaryPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void onNavigationToolbarClicked() {
    finish();
  }

  @OnClick(R.id.tv_checkout)
  public void onCheckoutClicked() {
    presenter.collectValues(tvBookingDateRange.getText().toString(),tvDayCount.getText().toString() ,fees);
  }

  @Override
  public void render(PriceCheckResult result, List<String> roomNameOccupancies,
                     List<String> groupedOccupancies) {
    tvSubtotal.setText(String.format("S$%s", roundTwoDecimals(result.summary.subTotal())));
    tvGrandTotal.setText(String.format("S$%s", roundTwoDecimals(result.summary.grandTotal())));
    tvSkyDollar.setText(roundTwoDecimals(Double.parseDouble(result.summary.skyDollar())));
    String totalNights =
        getResources().getQuantityString(R.plurals.nights, totalDays - 1, totalDays - 1);
    List<GuestDetail> guestDetails = new ArrayList<>();
    for (int i = 0; i < roomCount; i++) {
      String salutation = "";
      boolean isSmoking = false;
      String contactNumber = "";
      String specialRequest = "";

      GuestDetail guestDetail =
          new GuestDetail(salutation, isSmoking, contactNumber, specialRequest,
              "", "Room #" + (i + 1) + ": " + roomNameOccupancies.get(i), totalNights,
              result.priceNTaxesMap.get(groupedOccupancies.get(i)).roomPrice,
              result.priceNTaxesMap.get(groupedOccupancies.get(i)).taxes, property.isEurope());
      guestDetails.add(guestDetail);
    }

    guestDetailsforplacedlist = guestDetails;



    roomPriceAdapter.setGuestDetails(guestDetails);

    roomPriceAdapter.setNight(totalNights); /**  20200312 WIKI Nhat Nguyen - fix The price in My Booking summary calculated incorrectly  **/

    if (result.summary.fees() != null && result.summary.fees().size() > 0) {
      tvNotIncludeInPrice.setVisibility(View.VISIBLE);
      rvTourismFees.setVisibility(View.VISIBLE);
      tourismFeeRecyclerAdapter.setTourismFees(result.summary.fees());
      fees.addAll(result.summary.fees());
    } else {
      tvNotIncludeInPrice.setVisibility(View.GONE);
      rvTourismFees.setVisibility(View.GONE);
    }

    tourismFeeRecyclerAdapter.setTourismFees(result.summary.fees());
  }

  @Override
  public void render(Throwable error) {
    Toast.makeText(this, errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void goToRoomCheckout(RoomCheckoutPresenter.Params params) {
    RoomCheckoutActivity.start(this, params, REQUEST_CODE_CHECKOUT);
  }

  @Override
  public void showLoading() {
    if (isFinishing()) {
      return;
    }
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isFinishing()) {
      return;
    }
    progressDialog.dismiss();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHECKOUT) {
      finish();
    }
  }
}
