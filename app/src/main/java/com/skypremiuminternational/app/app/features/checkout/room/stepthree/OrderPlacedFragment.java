package com.skypremiuminternational.app.app.features.checkout.room.stepthree;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.booking.summary.RoomPriceAdapter;
import com.skypremiuminternational.app.app.features.booking.summary.TourismFeeRecyclerAdapter;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutPresenter;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.property.AmenityAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.CustomNoUnderLineLinkUtils;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.data.model.ean.availability.CancelPenaltiesItem;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class OrderPlacedFragment extends BaseFragment<OrderPlacedPresenter>
    implements OrderPlacedView<OrderPlacedPresenter> {

  private static final int REQUEST_CODE_WRITE_EXTERNAL = 122;
  @BindView(R.id.rvTourismFees)
  RecyclerView rvTourismFees;
  @BindView(R.id.tv_done)
  TextView tvDone;
  @BindView(R.id.tv_manage)
  TextView tvManage;
  @BindView(R.id.layout_root)
  ViewGroup layoutRoot;
  @BindView(R.id.tv_confirmation_notice)
  TextView tvConfirmationNotice;
  @BindView(R.id.rv_guest_detail)
  RecyclerView rvGuestDetail;
  @BindView(R.id.rv_amenity)
  RecyclerView rvAmenity;
  @BindView(R.id.rv_room_price)
  RecyclerView rvRoomPrice;
  @BindView(R.id.tv_contact_us)
  TextView tvContactUs;
  @BindView(R.id.tv_day_count)
  TextView tvNightCount;
  @BindView(R.id.tv_booking_id)
  TextView tvBookingId;
  @BindView(R.id.tv_order_status)
  TextView tvBookingStats;
  @BindView(R.id.tv_hotel_name)
  TextView tvHotelName;
  @BindView(R.id.tv_booking_date_range)
  TextView tvDateRange;
  @BindView(R.id.tv_credit_card)
  TextView tvCreditCard;
  @BindView(R.id.img_visa)
  ImageView imgVisa;
  @BindView(R.id.tv_processing_state)
  TextView tvProcessingState;
  @BindView(R.id.tv_hotel_area)
  TextView tvHotelArea;
  @BindView(R.id.tv_room_type)
  TextView tvRoomType;
  @BindView(R.id.tv_bed_type)
  TextView tvBedType;
  @BindView(R.id.iv_room)
  ImageView ivRoom;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;
  @BindView(R.id.tv_occupancy_detail)
  TextView tvOccupancyDetail;
  @BindView(R.id.tv_subtotal)
  TextView tvSubtotal;
  @BindView(R.id.tv_label_not_included_in_price)
  TextView tvNotIncludedInPrice;
  @BindView(R.id.tv_grand_total)
  TextView tvGrandTotal;
  @BindView(R.id.tv_sky_dollar)
  TextView tvSkyDollar;

  private TourismFeeRecyclerAdapter tourismFeeRecyclerAdapter;
  private AmenityAdapter amenityAdapter;
  private RoomPriceAdapter roomPriceAdapter;
  private GuestDetailAdapter guestDetailAdapter;
  private BookingDetail bookingDetail;
  private String email;
  private String propertyName;
  private RoomCheckoutPresenter.Params params;

  public static OrderPlacedFragment newInstance(
          BookingDetail bookingDetail, String email, String propertyName, RoomCheckoutPresenter.Params params) {
    OrderPlacedFragment fragment = new OrderPlacedFragment();
    fragment.bookingDetail = bookingDetail;
    fragment.email = email;
    fragment.propertyName = propertyName;
    fragment.params = params;
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setNoticeTextBeforeSubmit();
    setUpRecyclerView();
    render(bookingDetail);
  }

  private void render(BookingDetail value) {

    List<CancelPenalty> cancelsPenaltiesList = new ArrayList<>();
    if(value.roomsItems().get(0).getRate().getCancelPenalties() != null) {
      for (CancelPenaltiesItem cancelPenaltiesItem1 : value.roomsItems().get(0).getRate().getCancelPenalties()) {
        CancelPenalty cancelPenalty1 = CancelPenalty.builder()
                .start(cancelPenaltiesItem1.getStart())
                .end(cancelPenaltiesItem1.getEnd())
                .percent(cancelPenaltiesItem1.getPercent())
                .amount(cancelPenaltiesItem1.getAmount())
                .nights(cancelPenaltiesItem1.getNights())
                .isRefundable(value.roomsItems().get(0).getRate().isRefundable())
                .currency(cancelPenaltiesItem1.getCurrency())
                .build();
        cancelsPenaltiesList.add(cancelPenalty1);

      }
      amenityAdapter.cancelPenaltyList = cancelsPenaltiesList;
    }else{
      amenityAdapter.cancelPenaltyList = null;
    }



    amenityAdapter.setActionListener(amenity -> {

        String roomAndBed = value.roomType() + " - " + value.bedType();
        CancellationPolicyDialogFragment.newInstance(amenity.cancelPenalty(), roomAndBed,
            value.hotelName(),null,cancelsPenaltiesList)
            .show(getChildFragmentManager(), "BookingSummary");

    });

    int cardType = getCardType(value.creditType());

    if (cardType != 0) {
      imgVisa.setImageResource(cardType);
    }

    if (value.creditNumber() != null && value.creditNumber().length() > 0) {
      String cardNumber = "XXXX XXXX XXXX " + value.creditNumber()
          .substring(value.creditNumber().length() - 4, value.creditNumber().length());
      tvCreditCard.setText(cardNumber);
    }

    String notice = getString(R.string.order_place_confirmation_notice, email);
    tvConfirmationNotice.setText(notice);

    String text = "Booking ID: " + value.itineraryId();
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new StyleSpan(Typeface.BOLD), text.lastIndexOf(":") + 1
        , text.length(), 0);

    tvBookingId.setText(spannableString);
    tvBookingStats.setText(StringUtil.toTitleCase(value.bookingStatus().split(" ")));
    tvHotelName.setText(value.hotelName());
    tvHotelArea.setText(value.city());
    tvRoomType.setText(value.roomType());
    tvBedType.setText(value.bedType());
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(getContext())
        .load(value.roomImage())
        .apply(requestOptions)
        .into(ivRoom);

    tvDateRange.setText(params.nightrange());
    tvNightCount.setText(params.nightcount());
    int guestCont = value.adultCount() + value.childCount();
    Resources res = getResources();
    tvOccupancy.setText(res.getQuantityString(R.plurals.guests, guestCont, guestCont));

    tvOccupancyDetail.setText(value.totalGuest());
    guestDetailAdapter.setGuestDetails(value.guestDetails());
    roomPriceAdapter.setGuestDetails(value.guestDetails());
    roomPriceAdapter.setNight(params.nightcount());
    if(value.amenities().size()>0) {
      if (amenityAdapter.room == null && amenityAdapter.cancelsPenaltiesList == null && !value.amenities().get(value.amenities().size() - 1).description().equalsIgnoreCase("")) {
        value.amenities().add(new Amenity() {
          @Override
          public String description() {
            return "";
          }

          @Override
          public int id() {
            return 0;
          }

          @Override
          public boolean isCancellationPolicy() {
            return true;
          }

          @Nullable
          @Override
          public CancelPenalty cancelPenalty() {
            return null;
          }

          @Override
          public int describeContents() {
            return 0;
          }

          @Override
          public void writeToParcel(Parcel dest, int flags) {

          }
        });
      }
    }else{
      if (amenityAdapter.room == null && amenityAdapter.cancelsPenaltiesList == null ) {
        value.amenities().add(new Amenity() {
          @Override
          public String description() {
            return "";
          }

          @Override
          public int id() {
            return 0;
          }

          @Override
          public boolean isCancellationPolicy() {
            return true;
          }

          @Nullable
          @Override
          public CancelPenalty cancelPenalty() {
            return null;
          }

          @Override
          public int describeContents() {
            return 0;
          }

          @Override
          public void writeToParcel(Parcel dest, int flags) {

          }
        });
      }
    }
    amenityAdapter.setAmenities(value.amenities());
    tvGrandTotal.setText(String.format("S$%s", DecimalUtil.roundTwoDecimals(value.grandTotal())));
    tvSubtotal.setText(String.format("S$%s", DecimalUtil.roundTwoDecimals(value.subTotal())));
    tvSkyDollar.setText(String.valueOf(DecimalUtil.roundTwoDecimals(Double.parseDouble(value.skyDollar()))));

    if (params.fees() != null && params.fees().size() > 0) {
      tvNotIncludedInPrice.setVisibility(View.VISIBLE);
      rvTourismFees.setVisibility(View.VISIBLE);
      tourismFeeRecyclerAdapter.setTourismFees(params.fees());
    } else {
      tvNotIncludedInPrice.setVisibility(View.GONE);
      rvTourismFees.setVisibility(View.GONE);
    }
  }

  private int getCardType(String creditType) {
    switch (creditType.trim()) {
      case GetPaymentOptions.CARD_TYPE_AMERICAN_EXPRESS:
        return R.drawable.ic_american_express_blue_background;

      case GetPaymentOptions.CARD_TYPE_VISA:
        return R.drawable.ic_visa_blue_background;

      case GetPaymentOptions.CARD_TYPE_MASTER_CARD:
        return R.drawable.ic_master_card_with_text;
    }
    return 0;
  }

  private void setNoticeTextBeforeSubmit() {
    String phone = "tel:" + "+6565330000";
    String email = "mailto:" + "membersg@skypremium.com.sg";

    String notice = "<html>For enquiries regarding your booking, you may contact us at ";
    notice += "<a href=" + phone + ">+65 6533 0000</a>";
    notice += " or email ";
    notice += "<a href=" + email + ">membersg@skypremium.com.sg</a>";
    notice += " from 9am to 6pm on Mondays to Fridays, except public holidays.</html>";
    Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
        Html.fromHtml(notice));
    Spannable processedText = CustomNoUnderLineLinkUtils.removeUnderlines(spannedText);
    tvContactUs.setText(processedText);
    tvContactUs.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void setUpRecyclerView() {
    amenityAdapter = new AmenityAdapter();

    rvAmenity.setAdapter(amenityAdapter);
    rvAmenity.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    roomPriceAdapter = new RoomPriceAdapter();
    rvRoomPrice.setHasFixedSize(true);
    rvRoomPrice.setNestedScrollingEnabled(false);
    rvRoomPrice.setAdapter(roomPriceAdapter);
    rvRoomPrice.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    guestDetailAdapter = new GuestDetailAdapter();
    rvGuestDetail.setHasFixedSize(true);
    rvGuestDetail.setNestedScrollingEnabled(false);
    rvGuestDetail.setAdapter(guestDetailAdapter);
    rvGuestDetail.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    tourismFeeRecyclerAdapter = new TourismFeeRecyclerAdapter();
    rvTourismFees.setHasFixedSize(true);
    rvTourismFees.setNestedScrollingEnabled(false);
    rvTourismFees.setLayoutManager(new LinearLayoutManager(getContext()));
    rvTourismFees.setAdapter(tourismFeeRecyclerAdapter);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_order_placed;
  }

  @Inject
  @Override
  public void injectPresenter(OrderPlacedPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.tv_done)
  void onDoneClicked() {
    getActivity().setResult(Activity.RESULT_OK);
    getActivity().finish();
  }

  @OnClick(R.id.tv_manage)
  void onManageClicked() {
    BookingsHistoryActivity.start(getContext());
    getActivity().setResult(Activity.RESULT_OK);
    getActivity().finish();
  }

  public boolean isPermissionGranted() {
    return ContextCompat.checkSelfPermission(getContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  @OnClick(R.id.tv_print)
  void onClickedPrint() {
    if (isPermissionGranted()) {
      takeScreenshot();
    } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION)) {
      showRationaleAndRequestPermission();
    } else {
      requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
          REQUEST_CODE_WRITE_EXTERNAL);
    }
  }

  private void takeScreenshot() {
    Date now = new Date();
    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

    try {
      String mPath = null;
      tvDone.setVisibility(View.GONE);
      tvManage.setVisibility(View.GONE);
      Bitmap bitmap = loadBitmapFromView(layoutRoot, layoutRoot.getWidth(), layoutRoot.getHeight());
      if (bitmap != null) {
        mPath =
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "title",
                null);
      }
      tvDone.setVisibility(View.VISIBLE);
      tvManage.setVisibility(View.VISIBLE);

      openScreenshot(mPath);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private Bitmap loadBitmapFromView(View v, int width, int height) {
    Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    v.draw(c);
    return b;
  }

  private void openScreenshot(String path) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    Uri uri = Uri.parse(path);
    intent.setData(uri);
    startActivity(intent);
  }

  private void showRationaleAndRequestPermission() {
    Toast.makeText(getContext(), "Please grant permission", Toast.LENGTH_SHORT).show();
    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
        REQUEST_CODE_WRITE_EXTERNAL);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_CODE_WRITE_EXTERNAL: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          takeScreenshot();
        }
      }
    }
  }
}
