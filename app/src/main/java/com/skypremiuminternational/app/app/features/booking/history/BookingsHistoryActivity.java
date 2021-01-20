package com.skypremiuminternational.app.app.features.booking.history;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.booking.detail.BookingDetailActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;

import org.w3c.dom.Text;

import dagger.android.AndroidInjection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

public class BookingsHistoryActivity extends BaseActivity<BookingHistoryPresenter>
    implements BookingHistoryView<BookingHistoryPresenter> {

  @BindView(R.id.layout_no_history)
  ViewGroup layoutEmpty;
  @BindView(R.id.tvCategory_filter)
  TextView tvCategoryFilter;
  @BindView(R.id.tvSort_filter)
  TextView tvSortFilter;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvToolBarTitle;
  @BindView(R.id.tvTitle_toolbar_amount)
  TextView tvToolBarTitleAmount;
  @BindView(R.id.rv_booking_histories)
  RecyclerView rvBookingHistories;
  @BindView(R.id.rl_noticecancel)
  RelativeLayout rl_noticecancel;
  @BindView(R.id.img_close)
  ImageView img_close;
  @BindView(R.id.img_cancelsuccess)
  ImageView img_cancelsuccess;
  @BindView(R.id.tv_cancelnotice)
  TextView tv_cancelnotice;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  private ProgressDialog progressDialog;
  private BookHistoryAdapter adapter;
  private int selectedSorting = 0;
  private int selectedCategory = 0;
  private AlertDialog dialog;
  private List<BookingHistory> bookingHistorieslist = new ArrayList<>();
  private BookingData bookingData;

  public static void start(Context context) {
    Intent starter = new Intent(context, BookingsHistoryActivity.class);
    context.startActivity(starter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bookings_history);
    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));

    tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrBooking[0]));
    tvCategoryFilter.setText(String.format("Refine: %s", Constants.categoryArrBooking[0]));
    tvToolBarTitle.setText(getString(R.string.profile_my_bookings_label));
    setupRecyclerView();

    fetchBookingHistory();


    img_close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          rl_noticecancel.setVisibility(View.GONE);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(adapter!=null){
      fetchBookingHistory();
    }
  }

  private void setupRecyclerView() {
    adapter = new BookHistoryAdapter();
    adapter.setActionListener(new BookHistoryAdapter.ActionListener() {
      @Override
      public void onItemClicked(String bookingId,
                                List<BookingHistory.Room> rooms,int position) {
        ArrayList<BookingHistory.Room> room = new ArrayList<BookingHistory.Room>(rooms);
        presenter.getBookingDetailvalueview(bookingId,room,position);
      }

      @Override
      public void onCancelClicked(String bookingId,BookingHistory bookingHistory,List<BookingHistory.Room> rooms,int position) {
        if ((!bookingHistory.status().equalsIgnoreCase("Canceled") && bookingHistory.bookingDataList().get(0).getDisplayCancelsButton()) || bookingHistory.status().equalsIgnoreCase("Booked")) {
          ArrayList<BookingHistory.Room> room = new ArrayList<BookingHistory.Room>(rooms);
          presenter.getBookingDetailvalue(bookingId,room,position);
        } else {
          ArrayList<BookingHistory.Room> room = new ArrayList<BookingHistory.Room>(rooms);
          presenter.getBookingDetailvalueview(bookingId,room,position);
        }
        /*if(getBookingStatus(bookingHistory.status()).equalsIgnoreCase("Booked")) {
          ArrayList<BookingHistory.Room> room = new ArrayList<BookingHistory.Room>(rooms);
          presenter.getBookingDetailvalue(bookingId,room,position);
        }else{
          ArrayList<BookingHistory.Room> room = new ArrayList<BookingHistory.Room>(rooms);
          presenter.getBookingDetailvalueview(bookingId,room,position);
        }*/
      }
    });
    rvBookingHistories.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    rvBookingHistories.setHasFixedSize(true);
    rvBookingHistories.setAdapter(adapter);
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
  /**
   * 20200310 WIKI Nhat Nguyen - Cancel Booking  Pop-Up
   * @param bookingId
   */
  private void showConfirmationDialog(String bookingId ,int pos,List<CancelsPenalties> cancelsPenaltiesList) {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookingsHistoryActivity.this);
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
    int white = ContextCompat.getColor(BookingsHistoryActivity.this, R.color.white);
    btn_cancel.setTextColor(white);
    btn_cancel.setBackground(
            ContextCompat.getDrawable(BookingsHistoryActivity.this, R.drawable.rounded_cornercancelbuttongrey));
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
          btn_cancel.setBackground(
                  ContextCompat.getDrawable(BookingsHistoryActivity.this,
                          R.drawable.rounded_cornercancelbutton));
          btn_cancel.setEnabled(true);
        }else{
          btn_cancel.setBackground(
                  ContextCompat.getDrawable(BookingsHistoryActivity.this, R.drawable.rounded_cornercancelbuttongrey));
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
                  + ".\n\n"
                  + "The property "
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + cancelsPenalties.getAmount()
                  + cancelsPenalties.getCurrency()
                  + " fee."
          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //Old
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  + "\n\n"
                  + "The property "
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenalties.getAmount()
                  + cancelsPenalties.getCurrency()
                  + " fee."
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +"\n"
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
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + cancelsPenalties.getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenalties.getCurrency()+"."
                  +""

          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //old
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  + "\n\n"
                  + "The property "
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + cancelsPenalties.getNights()
                  + " night penalty plus taxes and fees in "
                  + cancelsPenalties.getCurrency()
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +"\n"
          );*/
          tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +""
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
                  + ".\n\n"
                  + "The property "
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on: "
                  + "any cancellations made after "
                  + formattedDate
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenalties.getCurrency()+"."

          );

          tv_elm7.setText("You will not be entitled to any Sky Dollars for this booking once cancelled.");
          //old
          /*tv_elm8.setText("This booking is refundable. "
                  + "Free cancellation until "
                  + startTime
                  + "\n\n"
                  + "The property "
                  + bookingHistorieslist.get(pos).hotelName()
                  + " imposes a penalty to its customers that we are required to pass on:” "
                  + "any cancellations made after "
                  + startTime
                  + " will result in a "
                  + Math.round(Float.parseFloat(percent)) + "%"
                  + " penalty of the stay charges and fees in "
                  + cancelsPenalties.getCurrency()
                  + "\n\n"
                  + "If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +"\n"
          );*/
          tv_elm8.setText("If you fail to check-in for this reservation, or if you cancel or change this reservation after check-in, you may incur penalty charges at the discretion of the property of up to 100% of the booking value."
                  +""
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
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookingsHistoryActivity.this);
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

  @Inject
  @Override
  public void injectPresenter(BookingHistoryPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void onClickHome() {
    finish();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    progressDialog.dismiss();
  }

  @Override
  public void showLoading() {
    if (isDestroyed()) return;
    progressDialog.show();
  }



  @Override
  public void render(Throwable error) {
    Toast.makeText(this, errorMessageFactory.getErrorMessage(error), Toast.LENGTH_SHORT).show();
  }

  /**
   * 20200310 WIKI Nhat Nguyen - Cancellation Results
   * @param isSuccess
   * @param status
   */
  @Override
  public void render(boolean isSuccess,String status) {
    if(isSuccess == true){
      rl_noticecancel.setVisibility(View.VISIBLE);

     if(status.equalsIgnoreCase("cancelled")) {
       tv_cancelnotice.setText("You cancelled the booking");
       img_cancelsuccess.setVisibility(View.VISIBLE);
     }

     else if(status.equalsIgnoreCase("unsuccessful")) {
        tv_cancelnotice.setText("Your cancellation is unsuccessful. Please contact our membership services team if you need any assistance.");
       img_cancelsuccess.setVisibility(View.GONE);
      }
     else {

       tv_cancelnotice.setText("Your cancellation has been submitted and is currently under review. Please check back on the status of booking again after fifteen (15) minutes. Please contact our membership services team if you need any assistance.");
       img_cancelsuccess.setVisibility(View.GONE);
     }
      dialog.hide();
    }else{
      dialog.hide();
    }
  }

  @Override
  public void render(List<BookingHistory> bookingHistories) {
    bookingHistorieslist = bookingHistories;
    if (bookingHistories != null && bookingHistories.size() > 0) {
      rvBookingHistories.setVisibility(View.VISIBLE);
      layoutEmpty.setVisibility(View.GONE);
      adapter.setHotelHistories(bookingHistories);
    } else {
      layoutEmpty.setVisibility(View.VISIBLE);
      rvBookingHistories.setVisibility(View.GONE);
    }
  }

  /**
   * 20200310 WIKI Nhat Nguyen - get booking detail for cancel booking pop-up
   * @param bookingData
   * @param success
   * @param bookingId
   * @param position
   */
  @Override
  public void render(BookingData bookingData, boolean success, String bookingId, int position) {
    this.bookingData = bookingData;
    showConfirmationDialog(bookingId,position,bookingData.getCancelsPenaltiesList());
  }

  /**
   * 20200310 WIKI Nhat Nguyen - pass data to bookingdetailactivity
   * @param bookingHistories
   * @param success
   * @param bookingid
   * @param position
   * @param rooms
   */
  @Override
  public void render(BookingData bookingHistories, boolean success, String bookingid,int position, ArrayList<BookingHistory.Room> rooms) {

    BookingDetailActivity.start(BookingsHistoryActivity.this, bookingid, rooms,String.valueOf(selectedCategory),String.valueOf(selectedSorting),String.valueOf(position),String.valueOf(bookingHistories.getDisplayCancelsButton()),String.valueOf(bookingHistories.getLabel_status()));

  }

  @OnClick(R.id.tvSort_filter)
  public void onClickSort() {
    new AlertDialog.Builder(this).setTitle("SORT BY:")
        .setItems(Constants.sortingArrBooking, (dialog, item) -> {
          selectedSorting = item;
          tvSortFilter.setText(String.format("Sort By: %s", Constants.sortingArrBooking[item]));
          fetchBookingHistory();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }

  private void fetchBookingHistory() {
    presenter.getBookingHistories(Constants.categoryValueBooking[selectedCategory],
        Constants.sortingFieldBooking[selectedSorting],
        Constants.sortingDirectionBooking[selectedSorting]);
  }

  @OnClick(R.id.tvCategory_filter)
  public void onClickCategory() {
    new AlertDialog.Builder(this).setTitle("REFINE: ")
        .setItems(Constants.categoryArrBooking, (dialog, item) -> {
          selectedCategory = item;
          tvCategoryFilter.setText(String.format("Refine: %s", Constants.categoryArrBooking[item]));
          fetchBookingHistory();
        })
        .setNegativeButton("Cancel", null)
        .show();
  }
}
