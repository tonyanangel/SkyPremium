package com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.j256.ormlite.stmt.query.In;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation.ConfirmReservationDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker.DateSinglePickerDialogFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.internal.mvp.BaseBottomDialogFragment;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.data.network.request.ReservationRequest;
import com.skypremiuminternational.app.domain.models.hunry_go_where.OutletItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveTimeSlotItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation.ConfirmReservationDialogFragment.CONFIRM_DIALOG;

/**
 *   WIKI - create by Toan Tran 20201126
 *
 */
public class MakeAReservationDialogFragment extends BaseBottomDialogFragment<MakeAReservationPresenter>
    implements MakeAReservationView<MakeAReservationPresenter>, DateSinglePickerDialogFragment.DateSinglePickerListener{

  public static final String TAG = "MAKE_RESERVATION_FORM";
  public static final int ACTION_NEW = 0;
  public static final int ACTION_EDIT = 1;
  public static final int ACTION_BOOK_AGAIN = 2;


  @BindView(R.id.ck_policy)
  CheckBox ckPolicy;
  @BindView(R.id.tv_policy)
  TextView tvPolicy;
  @BindView(R.id.tv_save)
  TextView tvSave;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.tv_sub_restaurant)
  TextView tvSubRestaurant;
  @BindView(R.id.tv_message_retaurant)
  TextView tvMessageRetaurant;
  @BindView(R.id.cl_policy)
  ConstraintLayout clPolicyTerm;
  @BindView(R.id.input_outlet)
  SkyTextInputSignLayout tvsOutlet;
  @BindView(R.id.input_reservation_date)
  SkyTextInputSignLayout tvsReservationDate;
  @BindView(R.id.input_reservation_time)
  SkyTextInputSignLayout tvsReservationTime;
  @BindView(R.id.input_adults_number)
  SkyTextInputSignLayout tvsAdultsNumber;
  @BindView(R.id.input_children_number)
  SkyTextInputLayout tvsChildrenNumber;
  @BindView(R.id.input_special_instruction)
  SkyTextInputLayout tvsSpecialInstruction;


  ProgressDialog progressDialog;




  private static List<OutletItem> outletItemList;
  private int outletSelectedPos = 0 ;
  private int timeSlotSelectedPos = -1 ;
  private int timeSlotSelectedId = -1 ;
  private String outletSelectedId = "" ;
  SimpleDateFormat df =  new SimpleDateFormat(DateSinglePickerDialogFragment.RESERVATION_DATE_PARTNER);
  String selectedDate = "";
  ReservationTimeResponse reservationTime;
  List<ReserveTimeSlotItem> reservationTimeList;
  ReserveTimeSlotItem reserveTimeSelected ;
  String[] arrTimeSlotName;
  Integer[] arrTimeSlotId;
  Integer productId;
  String restaurantName;
  int action = 0;
  ReserveHistoryItem reserveHistoryItem;
  String validMessage = "";

  public static MakeAReservationDialogFragment newInstance(List<OutletItem> outletItemList,Integer productId,
                                                           String restaurantName) {
    MakeAReservationDialogFragment fragment = new MakeAReservationDialogFragment();
    fragment.outletItemList =  outletItemList;
    fragment.productId =  productId;
    fragment.restaurantName =  restaurantName;
    return fragment;
  }

  public static MakeAReservationDialogFragment newInstance(
                                                List<OutletItem> outletItemList,
                                                ReserveHistoryItem reserveHistoryItem,
                                                int action
                                            ) {
    MakeAReservationDialogFragment fragment = new MakeAReservationDialogFragment();
    fragment.outletItemList = outletItemList;
    fragment.reserveHistoryItem = reserveHistoryItem;
    fragment.restaurantName = reserveHistoryItem.getRestaurantName();
    fragment.productId = Integer.parseInt(reserveHistoryItem.getProductId());
    fragment.action = action;
    fragment.outletSelectedId = reserveHistoryItem.getReservationId();
    return fragment;
  }



  @Inject
  ErrorMessageFactory errorMessageFactory;

  @Inject
  @Override
  public void injectPresenter(MakeAReservationPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_make_a_reservation;
  }

/*  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    View contentView = View.inflate(getContext(), R.layout.dialog_make_a_reservation, null);
    ButterKnife.bind(this, contentView);


  }*/


  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(@NonNull Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    View contentView = View.inflate(getContext(), R.layout.dialog_make_a_reservation, null);
    ButterKnife.bind(this, contentView);
    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    int height = displayMetrics.heightPixels;
    int maxHeight = (int) (height * 0.88);
    dialog.setContentView(contentView);
    BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) contentView.getParent());
    mBehavior.setPeekHeight(maxHeight);
    ((View) contentView.getParent()).setBackgroundColor(
        getResources().getColor(R.color.transparent));






    setupProgressLoading();
  }

  private void setupProgressLoading() {
    progressDialog = new ProgressDialog(getActivity());
    progressDialog.setMessage(getResources().getString(R.string.loading));
    progressDialog.setCancelable(false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
        Html.fromHtml(getString(R.string.by_confirming_this_reserva_on_i_agree_to_sky_premium_s_privacy_policy_and_terms_condi_ons)));
    //Spannable processedText = CustomNoUnderLineLinkUtils.removeUnderlines(spannedText);
    setTextViewHTML(tvPolicy,getString(R.string.by_confirming_this_reserva_on_i_agree_to_sky_premium_s_privacy_policy_and_terms_condi_ons));
    tvPolicy.setText(spannedText);
    setupDefaultReservation();

  }

  private void setupDefaultReservation() {
    if (action == ACTION_EDIT) {
      setupFormEdit();
    }
    if (action == ACTION_BOOK_AGAIN) {
      setupFormMakeAgain();
    } else if (action == ACTION_NEW) {
      setupFormMakeNew();
    }

  }

  @OnCheckedChanged(R.id.ck_policy)
  void onCheckChanged(CompoundButton view, boolean isChecked){
    if(isChecked){
      tvSave.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
      tvSave.setEnabled(true);

    }else {

      tvSave.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
      tvSave.setEnabled(false);
    }
  }

  private void setupFormEdit(){
    for(int i = 0 ; i < outletItemList.size() ; i++){
      if(outletItemList.get(i).getId().equalsIgnoreCase(outletSelectedId)){
        outletSelectedPos = i;
      }
    }



    if(!ObjectUtil.isNull(outletItemList)
        &&outletItemList.size()>0){
      outletSelectedId =  outletItemList.get(outletSelectedPos).getId();
      tvsOutlet.setText(outletItemList.get(outletSelectedPos).getValue());
    }
    try {
      selectedDate  = DateParser.changeFormatDate(Constants.PATTERN_DATE_TIME,Constants.PATTERN_DATE_SHORT,reserveHistoryItem.getReservationDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    presenter.getReservationTime(selectedDate,outletSelectedId);
    tvsReservationDate.setText(selectedDate.trim());

    tvsAdultsNumber.setText(reserveHistoryItem.getAdultGuests());
    tvsChildrenNumber.setText(reserveHistoryItem.getChildGuests());
    tvsSpecialInstruction.setText(reserveHistoryItem.getExtensionData().get(0).getNote());
    tvSubRestaurant.setVisibility(View.GONE);
    title.setText(getResources().getString(R.string.edit_reservation));
    tvMessageRetaurant.setVisibility(View.GONE);
    clPolicyTerm.setVisibility(View.GONE);
    tvSave.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
    tvSave.setEnabled(true);

  }
  private void setupFormMakeNew(){
    if(!ObjectUtil.isNull(outletItemList)
        &&outletItemList.size()>0){
      outletSelectedId =  outletItemList.get(outletSelectedPos).getId();
      tvsOutlet.setText(outletItemList.get(outletSelectedPos).getValue());
    }
    selectedDate  = df.format(CalendarDay.today().getDate());

    //presenter.getReservationTime(selectedDate,outletSelectedId);
    //tvsReservationDate.setText(selectedDate.trim());

    tvMessageRetaurant.setVisibility(View.VISIBLE);
    tvSubRestaurant.setVisibility(View.GONE);
    presenter.getGetRestaurantMsg(outletSelectedId);
    tvSave.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
    tvSave.setEnabled(false);

  }
  private void setupFormMakeAgain(){
    if(!ObjectUtil.isNull(outletItemList)
        &&outletItemList.size()>0){
      outletSelectedId =  outletItemList.get(outletSelectedPos).getId();
      tvsOutlet.setText(outletItemList.get(outletSelectedPos).getValue());
    }
    selectedDate  = df.format(CalendarDay.today().getDate());
    //presenter.getReservationTime(selectedDate,outletSelectedId);
    //tvsReservationDate.setText(selectedDate.trim());
    tvSubRestaurant.setVisibility(View.VISIBLE);
    tvMessageRetaurant.setVisibility(View.VISIBLE);

    tvSubRestaurant.setText(getResources().getString(R.string.with)+" "+reserveHistoryItem.getRestaurantName());
    presenter.getGetRestaurantMsg(outletSelectedId);
    tvSave.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
    tvSave.setEnabled(false);

  }


  protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
  {
    int start = strBuilder.getSpanStart(span);
    int end = strBuilder.getSpanEnd(span);
    int flags = strBuilder.getSpanFlags(span);
    ClickableSpan clickable = new ClickableSpan() {
      public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(span.getURL()));
        getActivity().startActivity(browserIntent);
      }
    };
    strBuilder.setSpan(clickable, start, end, flags);
    strBuilder.removeSpan(span);
  }

  protected void setTextViewHTML(TextView text, String html)
  {
    CharSequence sequence = Html.fromHtml(html);
    SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
    URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
    for(URLSpan span : urls) {
      makeLinkClickable(strBuilder, span);
    }
    text.setText(strBuilder);
    text.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @OnClick(R.id.tv_save)
  void onClickSave(){


    // log test
    logInformationReservation();

    // <<START>WIKI - Toan Tran  -  create request

    if(!isValid()) {
      if(!TextUtils.isEmpty(validMessage)){
        new AlertDialog.Builder(getActivity())
            .setMessage(validMessage).show();
      }
      return;
    }
    if(action!=ACTION_EDIT){
      if(!ckPolicy.isChecked()){
        new AlertDialog.Builder(getActivity())
            .setMessage("Please confirm Privacy Policy and Terms & Conditions.").show();
        return;
      }
    }


    ReservationRequest reservationRequest  = new ReservationRequest();
    reservationRequest.setOutletId(outletSelectedId);
    reservationRequest.setDate(selectedDate);
    reservationRequest.setTime( reserveTimeSelected.getTimeSlotId());
    reservationRequest.setAdult(Integer.parseInt(tvsAdultsNumber.getText().trim()));
    reservationRequest.setChild((TextUtils.isEmpty(tvsChildrenNumber.getText().trim()) ? 0 : Integer.parseInt(tvsChildrenNumber.getText().trim())));
    reservationRequest.setNote(tvsSpecialInstruction.getText().trim());
    reservationRequest.setProductId(productId);
    reservationRequest.setShiftId(reserveTimeSelected.getShiftId());
    reservationRequest.setRestaurantName(restaurantName);



    // <<END>WIKI - Toan Tran  -  create request


    if(action==ACTION_EDIT){
      reservationRequest.setReservationId(reserveHistoryItem.getReservationId());
      reservationRequest.setVerificationKey(reserveHistoryItem.getExtensionData().get(0).getVerificationKey());
    }

    //show confirm form
    if(getActivity() instanceof  DetailsResevationActivity){
      ConfirmReservationDialogFragment
          .newInstance(reservationRequest,tvsOutlet.getText().trim(),this,action,((DetailsResevationActivity)getActivity()))
          .show(getChildFragmentManager(),CONFIRM_DIALOG);
    }else {
      ConfirmReservationDialogFragment
          .newInstance(reservationRequest,tvsOutlet.getText().trim(),this,action,null)
          .show(getChildFragmentManager(),CONFIRM_DIALOG);
    }

  }

  boolean isValid(){
    boolean isValid = true;

    // input isValid

    if(TextUtils.isEmpty(outletSelectedId)){
      tvsReservationTime.showError();
      isValid = false;
    }

    if(TextUtils.isEmpty(selectedDate)){
      tvsReservationDate.showError();
      isValid = false;
    }

    if(TextUtils.isEmpty(tvsReservationDate.getText().trim())){
      tvsReservationDate.showError();
      isValid = false;
    }

    if(TextUtils.isEmpty(tvsReservationTime.getText().trim())){
      tvsReservationTime.showError();
      isValid = false;
    }

    if(reserveTimeSelected==null){
      tvsReservationTime.showError();
      isValid = false;
    }

    if(TextUtils.isEmpty(tvsAdultsNumber.getText().trim())){
      tvsAdultsNumber.showError();
      isValid = false;
    }



    if(!isValid){
      return isValid;
    }

    // value isValid
    int numAdult = 0 ;
    int numChild = 0 ;
    int sumPeople = 0;
    int min = reserveTimeSelected.getMinNumberOfSeatsForSingleReservation();
    int max = reserveTimeSelected.getMaxNumberOfSeatsForSingleReservation();
    /*String fullTimeCode  = String.format("%04d", reserveTimeSelected.getCutOffTime());
    String hour = fullTimeCode.substring(0,2);
    String minute = fullTimeCode.substring(2);
    String revTime = tvSubRestaurant.getText().toString().trim()+" "+String.format("%s:%s:%s",hour,minute,"00" );
    Date revDate = null;
    try {
      revDate  =  new SimpleDateFormat(Constants.PATTERN_DATE_TIME).parse(revTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Date currentDate = Calendar.getInstance().getTime();
    if(revDate!=null){
      if(currentDate.after(revDate)){
        validMessage = "Please select another reservation time.";
        isValid = false;
      }
    }*/

    numAdult = Integer.parseInt(TextUtils.isEmpty(tvsAdultsNumber.getText().toString().trim()) ? "0" : tvsAdultsNumber.getText().toString().trim());
    numChild = Integer.parseInt(TextUtils.isEmpty(tvsChildrenNumber.getText().toString().trim()) ? "0" : tvsChildrenNumber.getText().toString().trim());
    sumPeople = numAdult + numChild ;


    if(numAdult <= 0){
      validMessage = "The number adult(s) should be greater than zero.";
      isValid =  false;
    }

    if(sumPeople<=0  || sumPeople < min || sumPeople > max  ){
      validMessage = "The number of people should be between "+min+" and "+max+".";
      isValid =  false;
    }


    return isValid;
  }



  private void logInformationReservation() {
    Log.d("RESERVATION_LOG_FORM" ,
             "\noutlet: "                 +outletSelectedId
            +"\nselectedDate:  "          +selectedDate
            +"\ntvsAdultsNumber:  "       +tvsAdultsNumber.getText()
            +"\ntvsChildrenNumber:  "     +tvsChildrenNumber.getText()
            +"\ntvsSpecialInstruction:  " +tvsSpecialInstruction.getText()
            +"\nProduct:  "               +productId
            +"\nreserveTimeSelected: "    + (ObjectUtil.isNull(reserveTimeSelected) ? "null" : reserveTimeSelected.getTimeSlotId()+""));
  }

  @OnClick(R.id.img_close)
  void onClickClose(){
    dismiss();
  }

  @OnClick(R.id.vDateSinglePicker)
  void onClickReservationDate(){
    Date date = null;
    try {
      date = df.parse(selectedDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    DateSinglePickerDialogFragment.newInstance(CalendarDay.from(date),this).show(getChildFragmentManager(),"SINGLE_DATE_PICKER");
  }

  @OnClick(R.id.vOutlet)
  void onClickOutlet(){

    String[] outItems =  new String[outletItemList.size()];
    outItems = getOutletList(outletItemList);
    new AlertDialog.Builder(getActivity()).setTitle("Outlet :")
        .setItems(outItems, (dialog, position) -> {
          outletSelectedPos =  position;
          outletSelectedId =  outletItemList.get(outletSelectedPos).getId();
          tvsOutlet.setText(outletItemList.get(outletSelectedPos).getValue());
          if(!TextUtils.isEmpty(tvsReservationDate.getText().toString().trim())){
            presenter.getReservationTime(selectedDate,outletSelectedId);
          }
        })
        .setNegativeButton("Cancel", null)
        .show();
  }
  @OnClick(R.id.vReservationTime)
  void onClickvReservationTime(){



    if(reservationTime != null && arrTimeSlotName !=null){
      new AlertDialog.Builder(getActivity()).setTitle("Reservation Time :")
          .setItems(arrTimeSlotName, (dialog, position) -> {
            timeSlotSelectedPos =  position;
            timeSlotSelectedId =  arrTimeSlotId[timeSlotSelectedPos];
            reserveTimeSelected =  reservationTimeList.get(timeSlotSelectedPos);
            tvsReservationTime.setText(""+arrTimeSlotName[timeSlotSelectedPos]);
          })
          .setNegativeButton("Cancel", null)
          .show();
    }else {

    }

  }

  private String[] getOutletList(List<OutletItem> outletItemList ) {
    List<String> items = new ArrayList<>();
    for(OutletItem item  : outletItemList){
      items.add(item.getValue());
    }
    return (String[]) items.toArray(new String[items.size()]);
  }


  @Override
  public void onSelectDateDone(String date) {
    selectedDate =  date;
    try {
      selectedDate  = DateParser.changeFormatDate(Constants.PATTERN_DATE_TIME,Constants.PATTERN_DATE_SHORT,selectedDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    tvsReservationDate.setText(selectedDate);
    presenter.getReservationTime(selectedDate,outletSelectedId);
  }

  @Override
  public void renderReservationTimeFailed() {
    arrTimeSlotName = null;
    arrTimeSlotId = null;
    tvsReservationTime.setText("");
    timeSlotSelectedId = -1;
    reserveTimeSelected = null;
  }

  @Override
  public void renderReservationTime(ReservationTimeResponse reservationTimeResponse) {
    reservationTime = reservationTimeResponse;
    if(reservationTime != null){
      reservationTimeList = reservationTime.getReserveTime().getData();
      reserveTimeSelected =  reservationTimeList.get(0);

      String timeSlot = String.format("%04d",reserveTimeSelected.getTimeSlotId());
      String hour =  timeSlot.substring(0,2);
      String min  = timeSlot.substring(2);
      tvsReservationTime.setText(String.format("%s:%s",hour,min));
      intitTimeSlotList(reservationTimeList);
      timeSlotSelectedPos = 0;
      if(action == ACTION_EDIT){
        setupEditTime();
      }
    }

  }
  private void  setupEditTime(){
    for(int i  = 0 ; i < reservationTimeList.size() ; i++){
      if(reservationTimeList.get(i).getTimeSlotId().equals(reserveHistoryItem.getExtensionData().get(0).getTime())){
        reserveTimeSelected =  reservationTimeList.get(i);
        timeSlotSelectedPos = i;
        String timeSlot = String.format("%04d",reserveTimeSelected.getTimeSlotId());
        String hour =  timeSlot.substring(0,2);
        String min  = timeSlot.substring(2);
        tvsReservationTime.setText(String.format("%s:%s",hour,min));
        break;
      }
    }
  }


  void intitTimeSlotList(List<ReserveTimeSlotItem> reservationTimeList){
    String[] itemSlotsName = new String[reservationTimeList.size()];
    Integer[] itemSlotsId = new Integer[reservationTimeList.size()];
    String timeSlot;
    String hour;
    String min;
    for(int i = 0 ; i < reservationTimeList.size() ; i++){
      timeSlot = String.format("%04d",reservationTimeList.get(i).getTimeSlotId());
      hour = timeSlot.substring(0,2);
      min  = timeSlot.substring(2);
      itemSlotsName[i] = ""+hour+":"+min;
      itemSlotsId[i] = reservationTimeList.get(i).getCutOffTime();
    }
    arrTimeSlotName =  itemSlotsName;
    arrTimeSlotId =  itemSlotsId;

  }


  @Override
  public void showLoading() {
    if(progressDialog !=null){
      progressDialog.show();
    }
  }
  @Override
  public void hideLoading() {
    if(progressDialog.isShowing()){
      progressDialog.dismiss();
    }
  }

  @Override
  public void renderRestaurantMsg(String msg) {
    if(TextUtils.isEmpty(msg)){
      tvMessageRetaurant.setVisibility(View.GONE);
    }else {
      tvMessageRetaurant.setText(getResources().getString(R.string.message_from_restaurant)+" "+msg);
    }
  }
}
