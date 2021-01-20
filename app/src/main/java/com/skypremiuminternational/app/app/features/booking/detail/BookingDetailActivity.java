package com.skypremiuminternational.app.app.features.booking.detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcel;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;
import com.skypremiuminternational.app.app.features.booking.summary.RoomPriceAdapter;
import com.skypremiuminternational.app.app.features.booking.summary.TourismFeeRecyclerAdapter;
import com.skypremiuminternational.app.app.features.checkout.room.stepthree.GuestDetailAdapter;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogfordetailFragment;
import com.skypremiuminternational.app.app.features.travel.ean.detail.property.AmenityAdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CustomNoUnderLineLinkUtils;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Room;

import dagger.android.AndroidInjection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

public class BookingDetailActivity extends BaseActivity<BookingDetailPresenter>
    implements BookingDetailView<BookingDetailPresenter> {

  private static final int REQUEST_CODE_WRITE_EXTERNAL = 123;
  @BindView(R.id.layout_root)
  ViewGroup layoutRoot;
  @BindView(R.id.tv_not_inlcude_in_price)
  TextView tvNotIncludedInPriceNotice;
  @BindView(R.id.tv_day_count)
  TextView tvNightCount;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvOrderId;
  @BindView(R.id.tv_order_status)
  TextView tvOrderStatus;
  @BindView(R.id.tv_hotel_name)
  TextView tvHotelName;
  @BindView(R.id.tv_booking_date_range)
  TextView tvDateRange;
  @BindView(R.id.tv_contact)
  TextView tvContact;
  @BindView(R.id.rv_guest_detail)
  RecyclerView rvGuestDetail;
  @BindView(R.id.rv_amenity)
  RecyclerView rvAmenity;
  @BindView(R.id.rv_room_price)
  RecyclerView rvRoomPrice;
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
  @BindView(R.id.rvTourismFees)
  RecyclerView rvTourismFees;
  @BindView(R.id.tv_grand_total)
  TextView tvGrandTotal;
  @BindView(R.id.tv_sky_dollar)
  TextView tvSkyDollar;
  @BindView(R.id.tv_cancel)
  TextView tvCancel;
  @BindView(R.id.tv_skyinfo)
  TextView tv_skyinfo;



  private ProgressDialog progressDialog;
  @Inject
  ErrorMessageFactory errorMessageFactory;

  private AmenityAdapter amenityAdapter;
  private RoomPriceAdapter roomPriceAdapter;
  private GuestDetailAdapter guestDetailAdapter;
  private TourismFeeRecyclerAdapter tourismFeeRecyclerAdapter;
  private AlertDialog dialog;
  private int selectedSorting = 0;
  private int selectedCategory = 0;
  private String bookingId;
  private String hotelName;
  private ArrayList<BookingHistory.Room> rooms;
  private int position;
  private List<CancelsPenalties> cancelsPenaltiesList;
  private boolean isDisplaycancel;
  private String bookingstatus;

  public static void start(Context context, String bookingId,
                           List<BookingHistory.Room> rooms,String selectedCategory,String selectedSorting,String position,String isdisplayCancel,String bookingstatus) {
    Intent starter = new Intent(context, BookingDetailActivity.class);
    starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    starter.putExtra("booking_id", bookingId);
    starter.putExtra("selectedCategory", selectedCategory);
    starter.putExtra("selectedSorting", selectedSorting);
    starter.putExtra("displaycancel",isdisplayCancel);
    starter.putExtra("bookingstatus",bookingstatus);
    starter.putExtra("position", position);
    starter.putParcelableArrayListExtra("rooms", (ArrayList<BookingHistory.Room>) rooms);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_detail);
    ButterKnife.bind(this);
    setNoticeTextBeforeSubmit();
    setUpRecyclerView();

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));


    bookingId = getIntent().getStringExtra("booking_id");
    bookingstatus = getIntent().getStringExtra("bookingstatus");
    isDisplaycancel = Boolean.parseBoolean(getIntent().getStringExtra("displaycancel"));
    selectedSorting = Integer.parseInt(getIntent().getStringExtra("selectedCategory"));
    selectedCategory = Integer.parseInt(getIntent().getStringExtra("selectedSorting"));
    position = Integer.parseInt(getIntent().getStringExtra("position"));

    rooms = getIntent().getParcelableArrayListExtra("rooms");
    presenter.getBookingDetailvalue(bookingId,rooms);
    presenter.getBookingDetail(bookingId, rooms);
    progressDialog.show();

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
    tvContact.setText(processedText);
    tvContact.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void setUpRecyclerView() {
    amenityAdapter = new AmenityAdapter();
    rvAmenity.setAdapter(amenityAdapter);
    rvAmenity.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    roomPriceAdapter = new RoomPriceAdapter();
    rvRoomPrice.setHasFixedSize(true);
    rvRoomPrice.setNestedScrollingEnabled(false);
    rvRoomPrice.setAdapter(roomPriceAdapter);
    rvRoomPrice.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    guestDetailAdapter = new GuestDetailAdapter();
    rvGuestDetail.setHasFixedSize(true);
    rvGuestDetail.setNestedScrollingEnabled(false);
    rvGuestDetail.setAdapter(guestDetailAdapter);
    rvGuestDetail.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    tourismFeeRecyclerAdapter = new TourismFeeRecyclerAdapter();
    rvTourismFees.setHasFixedSize(true);
    rvTourismFees.setNestedScrollingEnabled(false);
    rvTourismFees.setLayoutManager(new LinearLayoutManager(this));
    rvTourismFees.setAdapter(tourismFeeRecyclerAdapter);
  }

  @Inject
  @Override
  public void injectPresenter(BookingDetailPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void onClickHome() {
    finish();
  }

  @Override
  public void render(Throwable error) {
    new AlertDialog.Builder(this)
        .setMessage("Error when fetching required booking detail. Please contact for support.")
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> {
          dialog.dismiss();
          finish();
        })
        .show();
  }



  /**
   * 20200310 WIKI Nhat Nguyen - render booking detail
   * @param bookingData
   */
  @Override
  public void render(BookingData bookingData) {

    if(bookingData != null) {
      this.cancelsPenaltiesList = bookingData.getCancelsPenaltiesList();
      amenityAdapter.cancelsPenaltiesList = bookingData.getCancelsPenaltiesList();
      if (bookingData.getDisplayCancelsButton() && !bookingData.getLabel_status().equalsIgnoreCase("Cancelled")) {
        tvCancel.setText(R.string.txt_cancelbookingbutton);
      } else {
        tvCancel.setText("View");
      }
    }
  }

  @Override
  public void render(List<BookingHistory> value, boolean success) {

    if(success){
      if(value.get(position).bookingDataList().get(0).getDisplayCancelsButton()){

      }else{
        if(getBookingStatus(value.get(position).status()).toLowerCase().equalsIgnoreCase("booked")){
          tv_skyinfo.setVisibility(View.VISIBLE);
        }else{
          tvSkyDollar.setText("-");
          tv_skyinfo.setVisibility(View.GONE);
        }

        tvCancel.setText("View");
        tvOrderStatus.setText(value.get(position).bookingDataList().get(0).getLabel_status());
        int white = ContextCompat.getColor(BookingDetailActivity.this, R.color.white);
        tvOrderStatus.setTextColor(white);
        tvOrderStatus.setBackground(
                ContextCompat.getDrawable(BookingDetailActivity.this, R.drawable.bg_grey_rectangle));
        dialog.hide();
      }

    }else{
      dialog.hide();
    }
  }

  @Override
  public void render(BookingDetail value) {

    amenityAdapter.setActionListener(amenity -> {

        String roomAndBed = value.roomType() + " - " + value.bedType();
        CancellationPolicyDialogFragment.newInstance(amenity.cancelPenalty(), roomAndBed,
                value.hotelName(), cancelsPenaltiesList, null)
                .show(getSupportFragmentManager(), "BookingSummary");

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
    String text = "Booking ID: " + value.itineraryId();
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new StyleSpan(Typeface.BOLD), text.lastIndexOf(":") + 1
        , text.length(), 0);

    tvSubtotal.setText(String.format("S$%s", roundTwoDecimals(value.subTotal())));
    tvGrandTotal.setText(String.format("S$%s", roundTwoDecimals(value.grandTotal())));
    tvSkyDollar.setText(String.valueOf(roundTwoDecimals(Double.parseDouble(value.skyDollar()))));
    tvOrderId.setText(spannableString);



    tvOrderStatus.setText(StringUtil.toTitleCase(bookingstatus.split(" ")));
  /** <<START>> 20200310 WIKI Nhat Nguyen - set booking status <<START>> **/

    if(!bookingstatus.equalsIgnoreCase("Cancelled") && isDisplaycancel){

    }else{
      int white = ContextCompat.getColor(BookingDetailActivity.this, R.color.white);
      tvOrderStatus.setTextColor(white);
      tvOrderStatus.setBackground(
              ContextCompat.getDrawable(BookingDetailActivity.this, R.drawable.bg_grey_rectangle));
    }
  /** <<END>> 20200310 WIKI Nhat Nguyen - set booking status <<END>> **/

    hotelName = value.hotelName();
    tvHotelName.setText(value.hotelName());
    tvHotelArea.setText(value.city());
    tvRoomType.setText(value.roomType());
    tvBedType.setText(value.bedType());
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.white);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.white);
    Glide.with(this)
        .load(value.roomImage())
        .apply(requestOptions)
        .into(ivRoom);

    tvDateRange.setText(value.bookingPeriod());
    tvNightCount.setText(getResources().getQuantityString(R.plurals.nights, value.nightCount(),
        value.nightCount()));
    int guestCont = value.adultCount() + value.childCount();
    Resources res = getResources();
    tvOccupancy.setText(res.getQuantityString(R.plurals.guests, guestCont, guestCont));

    tvOccupancyDetail.setText(value.totalGuest());
    guestDetailAdapter.setGuestDetails(value.guestDetails());
    roomPriceAdapter.setGuestDetails(value.guestDetails());
  /** <<START>> 20200310 WIKI Nhat Nguyen - set Cancellation & Refund Policy Label <<START>> **/
    roomPriceAdapter.setNight(getResources().getQuantityString(R.plurals.nights, value.nightCount(),
            value.nightCount()));
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
  /** <<END>> 20200310 WIKI Nhat Nguyen - set Cancellation & Refund Policy Label <<END>> **/
    amenityAdapter.setAmenities(value.amenities());
    if (value.fees() != null && value.fees().size() > 0) {
      tvNotIncludedInPriceNotice.setVisibility(View.VISIBLE);
      rvTourismFees.setVisibility(View.VISIBLE);
      tourismFeeRecyclerAdapter.setTourismFees(value.fees());
    } else {
      tvNotIncludedInPriceNotice.setVisibility(View.GONE);
      rvTourismFees.setVisibility(View.GONE);
    }

    if(!bookingstatus.equalsIgnoreCase("Cancelled") && isDisplaycancel){
      tvCancel.setText("Cancel This Booking");
    }else{
      tvCancel.setText("View");
    }
  /** <<START>> 20200310 WIKI Nhat Nguyen - set Cancel booking button  <<START>> **/
    tvCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(isDisplaycancel && tvCancel.getText().toString().toLowerCase().equalsIgnoreCase(getString(R.string.txt_cancelbookingbutton).toLowerCase())){
            showConfirmationDialog(value.itineraryId());
          }else{
            finish();
            BookingDetailActivity.start(getApplicationContext(), bookingId, rooms,String.valueOf(selectedCategory),String.valueOf(selectedCategory),String.valueOf(position),String.valueOf(isDisplaycancel),String.valueOf(bookingstatus));
          }

      }
    });
  /** <<END>> 20200310 WIKI Nhat Nguyen - set Cancel booking button  <<END>> **/
  /** <<START>> 20200310 WIKI Nhat Nguyen - Sky Dollars Earned value should be displayed as "-" for cancelled bookings  <<START>> **/
    if(isDisplaycancel && !bookingstatus.toLowerCase().equalsIgnoreCase("Cancelled") ){
      tv_skyinfo.setVisibility(View.VISIBLE);
    }else{
      tvSkyDollar.setText("-");
      tv_skyinfo.setVisibility(View.GONE);
    }
    if(amenityAdapter.getItemCount() >0){
      hideLoading();
    }
  /** <<END>> 20200310 WIKI Nhat Nguyen - Sky Dollars Earned value should be displayed as "-" for cancelled bookings  <<END>> **/

  }

  /**
   * 20200310 WIKI Nhat Nguyen - Cancel Booking Close Pop-Up
   * @param bookingId
  */
  private void showConfirmationDialog(String bookingId ) {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookingDetailActivity.this);
    View mView = getLayoutInflater().inflate(R.layout.dialog_cancelbooking, null);
    final ImageView img_close = (ImageView) mView.findViewById(R.id.img_close);
    final TextView tvheader = (TextView) mView.findViewById(R.id.tv_cancelbookingheader);
    final TextView tv_elm12 = (TextView) mView.findViewById(R.id.tv_eml12);
    final TextView tv_elm6 = (TextView) mView.findViewById(R.id.tv_elm06);
    final TextView tv_elm7 = (TextView) mView.findViewById(R.id.tv_elm07);
    final TextView tv_elm8 = (TextView) mView.findViewById(R.id.tv_elm08);
    final TextView tv_elm9 = (TextView) mView.findViewById(R.id.tv_elm09);
    final TextView tv_elm10 = (TextView) mView.findViewById(R.id.tv_elm10);
    final TextView btn_cancel = (TextView) mView.findViewById(R.id.btn_cancelbooking);
    final CheckBox checkbot = (CheckBox) mView.findViewById(R.id.checkbox_eml10);
    int white = ContextCompat.getColor(BookingDetailActivity.this, R.color.white);
    btn_cancel.setTextColor(white);
    btn_cancel.setBackground(
            ContextCompat.getDrawable(BookingDetailActivity.this, R.drawable.rounded_cornercancelbuttongrey));
    btn_cancel.setEnabled(false);


    tv_elm12.setPaintFlags(tv_elm12.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);




    mBuilder.setView(mView);
    AlertDialog fistdialog = mBuilder.create();
    fistdialog.show();

    img_close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fistdialog.hide();
      }
    });

    checkbot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {
        if ( isChecked )
        {

          btn_cancel.setEnabled(true);
          btn_cancel.setBackground(
                  ContextCompat.getDrawable(BookingDetailActivity.this,
                          R.drawable.rounded_cornercancelbutton));

        }else{

          btn_cancel.setBackground(
                  ContextCompat.getDrawable(BookingDetailActivity.this, R.drawable.rounded_cornercancelbuttongrey));
          btn_cancel.setEnabled(false);
        }

      }
    });

    tv_elm12.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fistdialog.hide();
      }
    });


    btn_cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fistdialog.hide();
        showcancelprogressDialog(bookingId);
      }
    });


    //setvalue for textview
    if(cancelsPenaltiesList != null && cancelsPenaltiesList.size()>0 ){
      //if(cancelPenaltyList.get(0))

      for(CancelsPenalties cancelsPenalties : cancelsPenaltiesList){
        String daytime = "";
        String startTimes = "";
        String formattedDate = "";
        String value = "";
        try {
          SimpleDateFormat dateFormat =
                  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
          dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

          SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm a (z)", Locale.US);
          df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

          SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);
          dfShort.setTimeZone(TimeZone.getTimeZone("GMT+8"));

          String startTime = cancelsPenalties.getStart();
          String startTimeShort = cancelsPenalties.getStart();
          String endTime = cancelsPenalties.getEnd();
          Date start = dateFormat.parse(startTime);

          startTime = df.format(start).replace("GMT+08:00", "GMT+8").replace(",","");
          startTimeShort = dfShort.format(start);
          startTimes = startTime;

          daytime = startTimeShort;


          String s = cancelsPenalties.getStart();
          s = s.substring(s.lastIndexOf("+") + 1);


          int iend = s.indexOf(":");
          s= s.substring(0 , iend);

          int iend2 = s.indexOf(s);
          if(s.substring(0,iend2).equalsIgnoreCase("0")){
            s = s.substring(0,0);
          }

          try {
            String dateStr1 = startTimes.replace("(GMT+8)","");
            SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH);
            df1.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
            Date date1 = df1.parse(dateStr1);
            df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String formattedDate1 = df1.format(date1);
            formattedDate = df1.format(date1);
            value = df1.format(date1);
          } catch (ParseException e) {
            e.printStackTrace();
          }




          Date end = dateFormat.parse(cancelsPenalties.getEnd());
          if(endTime.equalsIgnoreCase("2020-03-25T23:59:00.000Z")){
            endTime = "26 March 2020 07:59 AM(GMT+8)";
          }else {
            endTime = df.format(end).replace("GMT+08:00", "GMT+8");
          }
        } catch (ParseException e) {
          e.printStackTrace();
        }





          if (Validator.isTextValid(cancelsPenalties.getAmount())){
          tv_elm6.setText("This booking is refundable. "
                  +"Free cancellation until "
                  + formattedDate
                  +".\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + cancelsPenalties.getAmount()
                  + cancelsPenalties.getCurrency()
                  + " fee."
          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //old
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  +"\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenalties.getAmount()
                  + cancelsPenalties.getCurrency()
                  + " fee."
                  +"\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +"\n\n"
          );*/
          tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."

          );
        }
        if (Validator.isTextValid(cancelsPenalties.getNights())){
          tv_elm6.setText("This booking is refundable. "
                  +"Free cancellation until "
                  + formattedDate
                  + ".\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + cancelsPenalties.getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenalties.getCurrency() +"."

          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //ELM8 OLD
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  + "\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenalties.getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenalties.getCurrency()
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value." +
                  "\n\n"
          );*/
          tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
          );
        }
        if (Validator.isTextValid(cancelsPenalties.getPercent())){
          String percent="";
          if(cancelsPenalties.getPercent().equalsIgnoreCase("1.0000%")) {

            percent = cancelsPenalties.getPercent().replace("1.0000%", "100");
          }else{
            percent = cancelsPenalties.getPercent().replace("%", "");
          }
          tv_elm6.setText("This booking is refundable. "
                  +"Free cancellation until "
                  + formattedDate
                  +".\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenalties.getCurrency()+"."

          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //ELM8 OLD
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  +"\n\n"
                  + "The property "
                  + hotelName
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenalties.getCurrency()
                  +"\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +"\n\n"
          );*/

          tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."

          );
        }


      }




    }else{


      tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.");
      tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
      tv_elm6.setText("This booking is non- refundable. If you fail to check-in for this reservation, or if you cancel or change this reservation after check- in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value.");

    }
    tv_elm9.setText("Do you wish to proceed with the cancellation?");
    tv_elm10.setText("I have read and I agree to the refund and cancellation policies, terms and conditions.");



  }
  /**
   * 20200310 WIKI Nhat Nguyen - Processing Cancellation Pop-Up
   * @param bookingId
   */
  private void showcancelprogressDialog(String bookingId) {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookingDetailActivity.this);
    View mView = getLayoutInflater().inflate(R.layout.dialog_cancelbookingprogress, null);

    final TextView tvprogress = (TextView) mView.findViewById(R.id.tvprogress);
    final ImageView imageView = (ImageView) mView.findViewById(R.id.splashSpinner);
    Typeface customFont =  Typeface.createFromAsset(getAssets(),"fonts/PlayfairDisplay-Regular.otf");
    tvprogress.setTypeface(customFont);


    imageView.post(new Runnable()
    {
      @Override
      public void run()
      {
        AnimationDrawable spinnerAnim = (AnimationDrawable) imageView.getBackground();
        if (!spinnerAnim.isRunning())
        {
          spinnerAnim.start();
        }
      }
    });


    mBuilder.setView(mView);
    dialog = mBuilder.create();
    dialog.show();

    presenter.cancelBooking(bookingId, Constants.categoryValueBooking[selectedCategory],
            Constants.sortingFieldBooking[selectedSorting],
            Constants.sortingDirectionBooking[selectedSorting]);
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

  private String getBookingStatus(String bookingStatus) {
    switch (bookingStatus) {
      case "confirmed":
        return "Booked";
      case "cancellation":
        return "Cancellation Under Review";
      default:
        return bookingStatus;
    }
  }

  @Override
  public void showLoading() {
    if (isDestroyed()) return;
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    progressDialog.dismiss();
  }

  public boolean isPermissionGranted() {
    return ContextCompat.checkSelfPermission(this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  @OnClick(R.id.tv_print)
  void onPrintClicked() {
    if (isPermissionGranted()) {
      takeScreenshot();
    } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
        Manifest.permission.ACCESS_FINE_LOCATION)) {
      showRationaleAndRequestPermission();
    } else {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
          REQUEST_CODE_WRITE_EXTERNAL);
    }
  }

  private void takeScreenshot() {
    Date now = new Date();
    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

    try {
      String mPath = null;
      Bitmap bitmap = loadBitmapFromView(layoutRoot, layoutRoot.getWidth(), layoutRoot.getHeight());
      if (bitmap != null) {
        mPath =
            MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "title",
                null);
      }

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
    Toast.makeText(this, "Plz grant permission", Toast.LENGTH_SHORT).show();
    ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
