package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by codigo on 23/4/18.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.iv_room)
  ImageView ivRoom;
  @BindView(R.id.ly_root)
  ViewGroup lyRoot;
  @BindView(R.id.tv_book_now)
  TextView tvBookNow;
  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;
  @BindView(R.id.tv_show_room_detail)
  TextView tvShowRoomDetails;
  @BindView(R.id.tv_amenity0)
  TextView tvAmenity0;
  @BindView(R.id.tv_amenity1)
  TextView tvAmenity1;
  @BindView(R.id.tv_amenity2)
  TextView tvAmenity2;
  @BindView(R.id.tv_amenity3)
  TextView tvAmenity3;
  @BindView(R.id.tv_amenity4)
  TextView tvAmenity4;
  @BindView(R.id.tv_cancellation_policy)
  TextView tvCancellationPolicy;
  @BindView(R.id.tv_show_more_amenities)
  TextView tvShowMoreAmenities;
  @BindView(R.id.tv_nightly_price)
  TextView tvNightlyPrice;

  public RoomViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
  public String ConverDay(String startday,String endday){
    String daytime = "";
    String startTimes = "";
    String formattedDate = "";
    String value = "";
    String getday = "";
    try {
      SimpleDateFormat dateFormat =
              new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm a (z)", Locale.US);
      df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);
      dfShort.setTimeZone(TimeZone.getTimeZone("GMT+8"));

      String startTime = startday;
      String startTimeShort = startday;
      String endTime = endday;
      Date start = dateFormat.parse(startTime);

      startTime = df.format(start).replace("GMT+08:00", "GMT+8").replace(",","");
      startTimeShort = dfShort.format(start);
      startTimes = startTime;

      daytime = startTimeShort;


      String s = startday;
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
        df1.setTimeZone(TimeZone.getTimeZone("GMT+"+s));
        Date date1 = df1.parse(dateStr1);
        df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String formattedDate1 = df1.format(date1);
        formattedDate = df1.format(date1);
        value = df1.format(date1);
      } catch (ParseException e) {
        e.printStackTrace();
      }




      Date end = dateFormat.parse(endday);
      if(endTime.equalsIgnoreCase("2020-03-25T23:59:00.000Z")){
        endTime = "26 March 2020 07:59 AM(GMT+8)";
      }else {
        endTime = df.format(end).replace("GMT+08:00", "GMT+8");
      }
    } catch (ParseException e) {
      e.printStackTrace();

      try {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm a (z)", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        dfShort.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        String startTime = startday;
        String startTimeShort = startday;
        String endTime = endday;

        Date start = dateFormat.parse(startTime);

        start.setTime(start.getTime());

        startTime = df.format(start).replace("GMT+08:00", "GMT+8").replace(",","");
        startTimeShort = dfShort.format(start);
        daytime = startTimeShort;
        startTimes = startTime;


        String s = startday;
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
          df1.setTimeZone(TimeZone.getTimeZone("GMT+0"));
          Date date1 = df1.parse(dateStr1);
          df1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
          String formattedDate1 = df1.format(date1);
          formattedDate = df1.format(date1);
          value = df1.format(date1);
        } catch (ParseException eq) {
          eq.printStackTrace();
        }

        Date end = dateFormat.parse(endday);
        if(endTime.equalsIgnoreCase("2020-03-25T23:59:00.000Z")){
          endTime = "26 March 2020 07:59 AM(GMT+8)";
        }else {
          endTime = df.format(end).replace("GMT+08:00", "GMT+8");
        }


      } catch (ParseException eq) {
        eq.printStackTrace();
      }
    }



    try {

      SimpleDateFormat dfShort = new SimpleDateFormat("dd MMM yyyy", Locale.US);

      Date start = dfShort.parse(value);
     getday = dfShort.format(start);

    } catch (ParseException e) {
      e.printStackTrace();
    }






    return getday;
  }

  public void bind(Room room, int itemCount) {


    if(room.cancelPenalty() != null && room.cancelPenalty().cancelPenaltyList() != null){

      tvCancellationPolicy.setText("Free Cancellation (Before "+ConverDay(room.cancelPenalty().start(),room.cancelPenalty().end())+")");
    }else{
      tvCancellationPolicy.setText("Non-Refundable");
    }

    if (itemCount == 1) {
      ViewRatioUtils.setRoom(lyRoot, ivRoom, itemView.getContext());
    } else {
      ViewRatioUtils.setRooms(lyRoot, ivRoom, itemView.getContext());
    }
    String name = "";
    if (room.name() != null) {
      name = room.name() + " - ";
    }
    name += room.type();
    tvName.setText(name);
    tvOccupancy.setText(room.occupancy());
    tvNightlyPrice.setText(room.nightlyPrice());
    if (room.image() != null && !TextUtils.isEmpty(room.image())) {
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.placeholder(R.drawable.placeholder);
      requestOptions.dontAnimate();
      requestOptions.centerCrop();
      requestOptions.error(R.drawable.white);
      Glide.with(itemView.getContext())
          .load(room.image())
          .apply(requestOptions)
          .into(ivRoom);
    }

    checkPolicyAndShow(room.amenities());

    tvShowMoreAmenities.setVisibility(
        View.GONE);// TODO: 5/22/2018 remove this once show more flow is confirmed
  }

  private void checkPolicyAndShow(List<Amenity> amenities) {
    tvAmenity0.setVisibility(View.GONE);
    tvAmenity1.setVisibility(View.GONE);
    tvAmenity2.setVisibility(View.GONE);
    tvAmenity3.setVisibility(View.GONE);
    tvAmenity4.setVisibility(View.GONE);
    tvShowMoreAmenities.setVisibility(View.GONE);
    tvCancellationPolicy.setVisibility(View.GONE);

    if (amenities != null) {
      for (int i = 0; i < amenities.size(); i++) {
        Amenity amenity = amenities.get(i);

        if (i == 0) {
          if (!amenity.isCancellationPolicy()) {
            tvAmenity0.setVisibility(View.VISIBLE);
            tvAmenity0.setText(amenity.description());
          } else {
            tvCancellationPolicy.setVisibility(View.VISIBLE);
          }
        }

        if (i == 1) {
          if (!amenity.isCancellationPolicy()) {
            tvAmenity1.setVisibility(View.VISIBLE);
            tvAmenity1.setText(amenity.description());
          } else {
            tvCancellationPolicy.setVisibility(View.VISIBLE);
          }
        }

        if (i == 2) {
          if (!amenity.isCancellationPolicy()) {
            tvAmenity2.setVisibility(View.VISIBLE);
            tvAmenity2.setText(amenity.description());
          } else {
            tvCancellationPolicy.setVisibility(View.VISIBLE);
          }
        }

        if (i == 3) {
          if (!amenity.isCancellationPolicy()) {
            tvAmenity3.setVisibility(View.VISIBLE);
            tvAmenity3.setText(amenity.description());
          } else {
            tvCancellationPolicy.setVisibility(View.VISIBLE);
          }
        }

        if (i == 4) {
          if (tvCancellationPolicy.getVisibility() != View.VISIBLE) {
            tvCancellationPolicy.setVisibility(View.VISIBLE);
          }
        }
      }
    }
  }
}
