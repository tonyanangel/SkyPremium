package com.skypremiuminternational.app.app.features.hunry_go_where.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation.ConfirmReservationDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation.MyReservationApdapter;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.ImageUtils;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.data.network.request.ReservationRequest;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveResultDataItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import static com.skypremiuminternational.app.app.utils.Constants.PATTERN_TIME_SHORT;

public class DetailsResevationActivity extends BaseActivity<DetailsResevationPresenter>
    implements DetailsResevationView<DetailsResevationPresenter>, ConfirmReservationDialogFragment.ConfirmListener {

  protected static final String RESERVE_HISTORY_ITEM = "reserveHistoryItem";

  public static final int ACTION_NEW = 0;
  public static final int ACTION_EDIT = 1;
  public static final int ACTION_BOOK_AGAIN = 2;

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.tv_num)
  TextView tvNum;
  @BindView(R.id.tvLeft)
  TextView tvEdit;
  @BindView(R.id.tvRight)
  TextView tvCancel;
  @BindView(R.id.tv_restaurant_name)
  TextView tvRestaurantName;
  @BindView(R.id.tv_booking_number)
  TextView tvBookingNumber;
  @BindView(R.id.tv_booking_date)
  TextView tvBookingDate;
  @BindView(R.id.tv_book_again)
  TextView tvBookAgain;
  @BindView(R.id.tv_email)
  TextView tvEmail;
  @BindView(R.id.tv_reservation_name)
  TextView tvReservationName;
  @BindView(R.id.tv_reserve_date)
  TextView tvReserveDate;
  @BindView(R.id.tv_reserve_time)
  TextView tvReserveTime;
  @BindView(R.id.tv_sky_dollar_earn)
  TextView tvSkyDollarEarn;
  @BindView(R.id.tv_adults)
  TextView tvAdults;
  @BindView(R.id.tv_status_label)
  TextView tvStatusLabel;
  @BindView(R.id.tv_childs)
  TextView tvChilds;
  @BindView(R.id.tv_phone)
  TextView tvPhone;
  @BindView(R.id.tv_address)
  TextView tvAddress;
  @BindView(R.id.tv_open_hour)
  TextView tvOpenHour;
  @BindView(R.id.tv_website)
  TextView tvWebsite;
  @BindView(R.id.tv_note_sky_dollar_redemed)
  TextView tvNoteSkyDollarRedemed;
  @BindView(R.id.tv_special_instruction)
  TextView tvSpecialInstruction;
  @BindView(R.id.img_food)
  ImageView imgThumb;
  @BindView(R.id.ll_parent_top_action)
  LinearLayout llParentTopAction;

  ProgressDialog progressDialog;


  ReserveHistoryItem reserveHistoryItem;

  public static void startMe(Context context, ReserveHistoryItem reserveHistoryItem) {
    Intent intent = new Intent(context, DetailsResevationActivity.class);
    intent.putExtra(RESERVE_HISTORY_ITEM,reserveHistoryItem);
    context.startActivity(intent);
  }

  @Inject
  @Override
  public void injectPresenter(DetailsResevationPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_reservation);
    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);

    // 20201126 WIKI - Toan Tran - Get reservationHistoryItem
    initData();
    initTopAction();

    setTileToolbar(getResources().getString(R.string.reservation_id));
    setToolbarBookingId(reserveHistoryItem.getReservationId());
    tvEdit.setText(res(R.string.edit_reservation));
    tvCancel.setText(res(R.string.cancel_reservation));
    setupContent();
  }

  private void initTopAction() {

  }

  private void initData() {
      if(getIntent()!=null){
        reserveHistoryItem = (ReserveHistoryItem) getIntent().getSerializableExtra(RESERVE_HISTORY_ITEM);
      }

      if(reserveHistoryItem == null){
        finish();
        return;
      }
  }

  void setTileToolbar(String titleToolbar){
    tvTitleToolbar.setText(titleToolbar);
  }

  void setToolbarBookingId(String bookingId){
    tvNum.setText(bookingId);

  }

  String res(int res){
    return getResources().getString(res);
  }


  @OnClick(R.id.tvLeft)
  void onClickEdit(){
   presenter.getOutletResevation(reserveHistoryItem.getProductId(),ACTION_EDIT);
  }

  @OnClick(R.id.tvRight)
  void onClickCancel(){
    /*String outletId;
    String date;
    Integer time;
    Integer adult;
    Integer child;
    String note;
    Integer shiftId;
    Integer productId;

    //for edit
    String reservationId;
    String verificationKey;
*/

    ReservationRequest reservationRequest = new ReservationRequest();
    reservationRequest.setVerificationKey(reserveHistoryItem.getExtensionData().get(0).getVerificationKey());
    reservationRequest.setReservationId(reserveHistoryItem.getExtensionData().get(0).getReservationId());

    reservationRequest.setCancelPolicy(reserveHistoryItem.getExtensionData().get(0).getCancelledPolicy());

    reservationRequest.setRestaurantName(reserveHistoryItem.getRestaurantName());

    reservationRequest.setOutletId(reserveHistoryItem.getExtensionData().get(0).getOutletId());
    reservationRequest.setDate(reserveHistoryItem.getExtensionData().get(0).getDate());
    reservationRequest.setTime(reserveHistoryItem.getExtensionData().get(0).getTime());
    reservationRequest.setAdult(Integer.parseInt(reserveHistoryItem.getExtensionData().get(0).getAdult()));
    reservationRequest.setChild(Integer.parseInt(reserveHistoryItem.getExtensionData().get(0).getChild()));
    reservationRequest.setNote(reserveHistoryItem.getExtensionData().get(0).getNote());
    reservationRequest.setShiftId(Integer.parseInt(reserveHistoryItem.getExtensionData().get(0).getShiftId()));
    reservationRequest.setProductId(Integer.parseInt(reserveHistoryItem.getProductId()));


    ConfirmReservationDialogFragment.newInstance(reservationRequest,
        reserveHistoryItem.getRestaurantName(),
        null,
        ConfirmReservationDialogFragment.ACTION_CANCEL,this).show(getSupportFragmentManager(),ConfirmReservationDialogFragment.CONFIRM_DIALOG);
  }


  @OnClick(R.id.tv_book_again)
  void onClicktBookAgain(){
   presenter.getOutletResevation(reserveHistoryItem.getProductId(),ACTION_BOOK_AGAIN);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClicktBack(){
   onBackPressed();
  }

  @Override
  public void renderGetOutletSuccess(List<OutletItem> list,int action) {
    MakeAReservationDialogFragment.newInstance(list,reserveHistoryItem,
        action).show(getSupportFragmentManager(),MakeAReservationDialogFragment.TAG);

  }

  @Override
  public void renderGetOutletFailed() {

  }

  @Override
  public void renderError(String msg) {
    new AlertDialog.Builder(this).setMessage(msg).show();
  }

  @Override
  public void renderBookingDetail(ReserveHistoryItem reserveHistoryItem) {
    this.reserveHistoryItem =  reserveHistoryItem;

    setupContent();
  }

  private void setupContent() {
   /*   @BindView(R.id.tv_email)
  TextView tvEmail;
  @BindView(R.id.tv_reservation_name)
  TextView tv_reservation_name;
  @BindView(R.id.tv_reserve_date)
  TextView tv_reserve_date;
  @BindView(R.id.tv_reserve_time)
  TextView tv_reserve_time;
  @BindView(R.id.tv_adults)
  TextView tv_adults;
  @BindView(R.id.tv_status_label)
*/
    String countryCode;
    String phoneValue;
    String address;
    String localAddress;
    String city;
    String localCity;
    String postalCode;
    String country;
    String website;
    String openHour;
    String reserveTime = null;
    String bookingMakeOn = null;
    String skyDollarEarn = null;
    String skyDollarEx = null;
    String note = null;
    String reserveDate = null;
    Date currentDate = Calendar.getInstance().getTime();
    Date reserverDateTime = null;

    try {
      reserverDateTime = new SimpleDateFormat(Constants.PATTERN_DATE_TIME).parse(reserveHistoryItem.getReservationDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    String reservationData = reserveHistoryItem.getReservationData();
    Gson gson = new Gson();
    ReserveResultDataItem reserveResultDataItem = gson.fromJson(reservationData, ReserveResultDataItem.class);

    countryCode = reserveResultDataItem.getDiner().getPrimaryPhone().getCountryCode();
    phoneValue = reserveResultDataItem.getDiner().getPrimaryPhone().getValue();
    country = reserveResultDataItem.getRestaurant().getCountry();
    postalCode = reserveResultDataItem.getRestaurant().getPostalCode();
    city = reserveResultDataItem.getRestaurant().getCity();
    localAddress = reserveResultDataItem.getRestaurant().getLocalAddress();
    address = reserveResultDataItem.getRestaurant().getAddress();
    localCity = reserveResultDataItem.getRestaurant().getLocalCity();
    website = reserveResultDataItem.getRestaurant().getWebsite();
    openHour = reserveResultDataItem.getRestaurant().getHoursOfOperation();
    skyDollarEarn = reserveHistoryItem.getReservationSkyDollarEarn();
    skyDollarEx = reserveHistoryItem.getExtensionData().get(0).getLoyalExpiried();
    note = reserveHistoryItem.getExtensionData().get(0).getNote();
    try {
      reserveTime = DateParser.changeFormatDate(Constants.PATTERN_DATE_TIME,PATTERN_TIME_SHORT,reserveHistoryItem.getReservationDate());
      String marker = reserveTime.substring(4);
      reserveTime = reserveTime.substring(0,4);
      marker =  marker.toLowerCase();
      reserveTime = reserveTime+marker;

    } catch (ParseException e) {
      e.printStackTrace();
    }
    try {
      reserveDate = DateParser.changeFormatDate(Constants.PATTERN_DATE_TIME,Constants.PATTERN_DATE_FULL_SP_REV,reserveHistoryItem.getReservationDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    try {
      bookingMakeOn =  DateParser.changeFormatDate(Constants.PATTERN_DATE_SHORT,
          Constants.PATTERN_DATE_SEMI_DAY_NEM_SP_REV,
          reserveHistoryItem.getReservationOnDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }


    tvRestaurantName.setText(reserveHistoryItem.getRestaurantName());
    tvBookingNumber.setText(res(R.string.booking_number) + ": " + reserveHistoryItem.getReservationId());
    tvBookingDate.setText(res(R.string.booking_made_on) + " " + (bookingMakeOn == null ? reserveHistoryItem.getReservationOnDate() :bookingMakeOn));
    if (reserveHistoryItem.getReservationStatus().equalsIgnoreCase("4")) {
      tvStatusLabel.setText(res(R.string.this_reservation_is_cancelled));
      tvStatusLabel.setVisibility(View.VISIBLE);
    } else {
      tvStatusLabel.setVisibility(View.INVISIBLE);
    }

    tvReservationName.setText(reserveResultDataItem.getDiner().getFirstName() + " " + reserveResultDataItem.getDiner().getLastName());
    tvReserveDate.setText(reserveDate);
    tvReserveTime.setText(reserveTime == null ? reserveHistoryItem.getReservationDate() : reserveTime);
    tvAdults.setText(reserveHistoryItem.getAdultGuests());
    tvChilds.setText(reserveHistoryItem.getChildGuests());
    tvEmail.setText(reserveResultDataItem.getDiner().getPrimaryEmail().getValue());
    tvPhone.setText(String.format("(%s) %s",countryCode,phoneValue));
    tvAddress.setText(String.format("%s,\n" +
                                    "%s,\n" +
                                    "%s %s",
        TextUtils.isEmpty(address)      ? "" : address+"",
        TextUtils.isEmpty(city)         ? "" : city+"",
        TextUtils.isEmpty(country)      ? "" : country+"",
        TextUtils.isEmpty(postalCode)   ? "" : postalCode+""
    ));
    tvSkyDollarEarn.setText(TextUtils.isEmpty(skyDollarEarn) ? "-" :  skyDollarEarn);
    tvWebsite.setText(TextUtils.isEmpty(website) ? "-" : website);
    tvOpenHour.setText(TextUtils.isEmpty(openHour) ? "-" : openHour);
    tvSpecialInstruction.setText(TextUtils.isEmpty(note) ? "-" : note );
    //ImageUtils.glideImgDefaulSky(imgThumb, URLUtils.hgwImageURL("4f6192f1e4b09b39fd0e5519"));
    ImageUtils.glideImgDefaulSky(imgThumb, reserveHistoryItem.getExtensionData().get(0).getImage_url());


    if (reserveHistoryItem.getReservationStatus().equalsIgnoreCase("4")) {
      tvStatusLabel.setText(res(R.string.this_reservation_is_cancelled));
      tvStatusLabel.setVisibility(View.VISIBLE);

      ImageDataUtils.grayScale(imgThumb);
      tvBookAgain.setVisibility(View.VISIBLE);
    } else {
      ImageDataUtils.grayScaleRev(imgThumb);
      tvStatusLabel.setVisibility(View.INVISIBLE);
      tvBookAgain.setVisibility(View.INVISIBLE);
    }

    if(reserveHistoryItem.getReservationStatus().equalsIgnoreCase("4")){
      tvNoteSkyDollarRedemed.setVisibility(View.GONE);
    }else {
      if(TextUtils.isEmpty(skyDollarEarn)){
        tvNoteSkyDollarRedemed.setVisibility(View.GONE);
      }else {
        tvNoteSkyDollarRedemed.setVisibility(View.VISIBLE);
        tvNoteSkyDollarRedemed.setText(res(R.string.note_sky_dollar_redemed_1) +
            " "+(TextUtils.isEmpty(skyDollarEx) ? "7" : skyDollarEx) +
            " "+res(R.string.note_sky_dollar_redemed_2));
      }
    }

    if (reserveHistoryItem.getReservationStatus().equalsIgnoreCase("4")||
        (currentDate.after(reserverDateTime))) {
      llParentTopAction.setVisibility(View.GONE);
      tvBookAgain.setVisibility(View.VISIBLE);
    }else {
      llParentTopAction.setVisibility(View.VISIBLE);
      tvBookAgain.setVisibility(View.INVISIBLE);
    }




  }

  @Override
  public void onReloadHistoryDetail() {
    presenter.getBookingDetail(reserveHistoryItem.getHgwId());
  }

  @Override
  public void showLoading() {
    if(progressDialog !=null&& !progressDialog.isShowing()){
      progressDialog.setMessage(getResources().getString(R.string.loading));
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if(progressDialog !=null&& progressDialog.isShowing()){
      progressDialog.dismiss();
    }
  }
}
