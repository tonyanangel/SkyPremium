package com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DateParser;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveResultDataItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReservationViewHolder extends RecyclerView.ViewHolder {


  @BindView(R.id.tvRestaurantName)
  public TextView tvRestaurantName;
  @BindView(R.id.tvReservationNumber)
  public TextView tvReservationNumber;
  @BindView(R.id.tvReservationDate)
  public TextView tvReservationDate;
  @BindView(R.id.tvFirstName)
  public TextView tvFirstName;
  @BindView(R.id.tvLastName)
  public TextView tvLastName;
  @BindView(R.id.tvStatus)
  public TextView tvStatus;
  @BindView(R.id.tv_sky_dollar_redeemed)
  public TextView tvSkyDollarRedeemed;
  @BindView(R.id.tvReservationTime)
  public TextView tvReservationTime;


  public void bind(ReserveHistoryItem reserveHistoryItem) {
    String reservationData = reserveHistoryItem.getReservationData();
    Gson gson = new Gson();
    ReserveResultDataItem reserveResultDataItem = gson.fromJson(reservationData, ReserveResultDataItem.class);
    String status;

    tvRestaurantName.setText(reserveHistoryItem.getRestaurantName());

    tvReservationTime.setText(reserveHistoryItem.getReservationDate()
        .substring(reserveHistoryItem.getReservationDate().lastIndexOf(" ")+1, reserveHistoryItem.getReservationDate().lastIndexOf(" ") + 6));

    try {
      tvReservationDate.setText(DateParser.changeFormatDate(Constants.PATTERN_DATE_SHORT, "dd/MM/yyyy", reserveHistoryItem.getReservationDate()));
    } catch (ParseException e) {
      tvReservationDate.setText(reserveHistoryItem.getReservationDate());
      e.printStackTrace();
    }

    tvReservationNumber.setText("Reservation Number: " + reserveHistoryItem.getReservationId());
    tvFirstName.setText(reserveResultDataItem.getDiner().getFirstName());
    tvLastName.setText(reserveResultDataItem.getDiner().getLastName());


    tvStatus.setText(toStatusStr(reserveHistoryItem.getReservationStatus()));
    tvSkyDollarRedeemed.setText(TextUtils.isEmpty(reserveHistoryItem.getReservationSkyDollarEarn()) ? "0" :  reserveHistoryItem.getReservationSkyDollarEarn());
  }

  String toStatusStr(String s){
    if(s.equalsIgnoreCase("1")){
      return this.itemView.getResources().getString(R.string.confirmed);
    }else if(s.equalsIgnoreCase("4")){
      return this.itemView.getResources().getString(R.string.cancelled);
    }else {
      return "-";
    }
  }

  public MyReservationViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
