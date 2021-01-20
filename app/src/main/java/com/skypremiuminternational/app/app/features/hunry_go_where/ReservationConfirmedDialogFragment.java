package com.skypremiuminternational.app.app.features.hunry_go_where;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.data.network.request.ReservationRequest;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.skypremiuminternational.app.app.utils.Constants.PATTERN_TIME_SHORT;

public class ReservationConfirmedDialogFragment extends DialogFragment {

  public static final String RESERVATION_CONFIRMED_DIALOG = "RESERVATION_CONFIRMED_DIALOG";


  @BindView(R.id.tv_under_name)
  TextView tvUnderName;
  @BindView(R.id.tv_content_reservation)
  TextView tvContentReservation;
  @BindView(R.id.tv_booking_number)
  TextView tvBookingNumber;
  @BindView(R.id.tv_sky_dollar_redeemed_policy_2)
  TextView tvSkyDollarRedeemedPolicy;




  ReservationResultResponse reservationResult ;
  ReservationRequest reservationRequest;
  public static ReservationConfirmedDialogFragment newInstance(ReservationResultResponse reservationResult , ReservationRequest reservationRequest){
    ReservationConfirmedDialogFragment fragment = new ReservationConfirmedDialogFragment();
    fragment.reservationResult = reservationResult;
    fragment.reservationRequest = reservationRequest;

    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_reservation_confirmed, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    tvUnderName.setText(getResources().getString(R.string.your_reservation_is_confirmed_nunder)
        +" "+reservationResult.getReserveResultDataItem().get(0).getDiner().getFullName());
    tvBookingNumber.setText(getResources().getString(R.string.booking_number)
        +" "+reservationResult.getReserveResultDataItem().get(0).getBookingNumber());
    tvContentReservation.setText(createContentQuestion());
    tvSkyDollarRedeemedPolicy.setText(Html.fromHtml(createCancelPolicy()));

  }

  private String createCancelPolicy() {
    String string = "";

    string =
        " <font color='#CCA255'><b>" + reservationResult.getReserveResultDataItem().get(0).getSkyDollarEarn() +
        " " + res(R.string.sky_dollars) +
        " </b></font>" + res(R.string.msg_redden_sky_2) +
        " " + reservationResult.getReserveResultDataItem().get(0).getLoyaltyExpired() +
        " " + res(R.string.msg_redden_sky_3);


    return  string;
  }

  String createContentQuestion() {
    String question = "";
    String sDate = "";
    try {
      sDate = DateParser.changeFormatDate(Constants.PATTERN_DATE_SHORT,Constants.PATTERN_DATE_FULL_SP_REV,reservationRequest.getDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }


    String timeSlot = String.format("%04d",reservationRequest.getTime());
    String hour =  timeSlot.substring(0,2);
    String min  = timeSlot.substring(2);

    String reserveTime = hour+"."+min;

    try {
      reserveTime = DateParser.changeFormatDate(Constants.PATTERN_TIME,PATTERN_TIME_SHORT,reserveTime);
      String marker = reserveTime.substring(4);
      reserveTime = reserveTime.substring(0,4);
      marker =  marker.toLowerCase();
      reserveTime = reserveTime+marker;

    } catch (ParseException e) {
      e.printStackTrace();
    }
    question =
        reservationRequest.getAdult()
            + " " + res(R.string.q_re_1)
            + " " + reservationRequest.getChild()
            + " " + res(R.string.q_re_2) +"\n"
            + " " + sDate
            + " " + res(R.string.at)
            + " " + reserveTime +"\n"
            + " " + reservationRequest.getRestaurantName();


    return question;
  }

  String res(int res){
    return getResources().getString(res);
  }



  @OnClick(R.id.img_close)
  void onClickClose(){
    dismiss();
  }

  @OnClick(R.id.tv_done)
  void onClickDone(){
    dismiss();
  }
}
