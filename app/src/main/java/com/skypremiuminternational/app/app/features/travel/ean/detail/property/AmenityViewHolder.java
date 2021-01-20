package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AmenityViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.tv_amenity)
  TextView tvAmenity;

  public AmenityViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);

  }
  /**
   * 20200310 WIKI Nhat Nguyen - Format penalty dates and set cancellation policy label(booking detail)
   * @param amenity
   * @param room
   * @param cancelsPenaltiesList
   * @param cancelPenaltyList
   * @param  size
   * @param position
   */

  public void bind(Amenity amenity, List<CancelsPenalties> cancelsPenaltiesList, Room room,
                   Integer size, Integer position, List<CancelPenalty> cancelPenaltyList) {
    if(room != null) {


      tvAmenity.setText(amenity.description());
      if (amenity.isCancellationPolicy()) {
          if (!TextUtils.isEmpty(room.cancelPenalty().start())) {
            tvAmenity.setText("Free Cancellation (Before " + ConverDay(room.cancelPenalty().start(),room.cancelPenalty().end()) + ")");
          } else {
            tvAmenity.setText("Non-Refundable");
          }
        tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                R.drawable.ic_book_help_grey_19dp, 0);
      } else {
        if(TextUtils.isEmpty(amenity.description())){
          tvAmenity.setText("Non-Refundable");
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);
        }else {
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  0, 0);
        }
      }
    }else if(cancelPenaltyList != null){

      if(cancelPenaltyList.size() > 1) {

        tvAmenity.setText(amenity.description());
        if (amenity.isCancellationPolicy()) {
          if (!TextUtils.isEmpty(cancelPenaltyList.get(0).start())) {
            tvAmenity.setText("Free Cancellation (Before " + ConverDay(cancelPenaltyList.get(0).start(),cancelPenaltyList.get(0).end()) + ")");
          } else {
            tvAmenity.setText("Non-Refundable");
          }

          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);

        } else {
          if(TextUtils.isEmpty(amenity.description())){
            tvAmenity.setText("Non-Refundable");
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    R.drawable.ic_book_help_grey_19dp, 0);
          }else {
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    0, 0);
          }
        }


      }else{

        tvAmenity.setText(amenity.description());
        if (amenity.isCancellationPolicy()) {
          if (!TextUtils.isEmpty(cancelPenaltyList.get(0).start())) {

            tvAmenity.setText("Free Cancellation (Before " + ConverDay(cancelPenaltyList.get(0).start(),cancelPenaltyList.get(0).end()) + ")");
          } else {
            tvAmenity.setText("Non-Refundable");
          }

          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);

        } else {
          if(TextUtils.isEmpty(amenity.description())){
            tvAmenity.setText("Non-Refundable");
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    R.drawable.ic_book_help_grey_19dp, 0);
          }else {
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    0, 0);
          }
        }
      }
    }


    else if (cancelsPenaltiesList != null) {


      if(cancelsPenaltiesList.size() > 1) {


        tvAmenity.setText(amenity.description());
        if (amenity.isCancellationPolicy()) {
          if (!TextUtils.isEmpty(cancelsPenaltiesList.get(0).getStart())) {
            tvAmenity.setText("Free Cancellation (Before " + ConverDay(cancelsPenaltiesList.get(0).getStart(),cancelsPenaltiesList.get(0).getEnd()) + ")");
          } else {
            tvAmenity.setText("Non-Refundable");
          }

          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);

        } else {
          if(TextUtils.isEmpty(amenity.description())){
            tvAmenity.setText("Non-Refundable");
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    R.drawable.ic_book_help_grey_19dp, 0);
          }else {
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    0, 0);
          }
        }


      }else{

        tvAmenity.setText(amenity.description());
        if (amenity.isCancellationPolicy()) {
            if (!TextUtils.isEmpty(cancelsPenaltiesList.get(0).getStart())) {
              tvAmenity.setText("Free Cancellation (Before " + ConverDay(cancelsPenaltiesList.get(0).getStart(),cancelsPenaltiesList.get(0).getEnd()) + ")");
            } else {
              tvAmenity.setText("Non-Refundable");
            }

            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    R.drawable.ic_book_help_grey_19dp, 0);

        } else {
          if(TextUtils.isEmpty(amenity.description())){
            tvAmenity.setText("Non-Refundable");
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    R.drawable.ic_book_help_grey_19dp, 0);
          }else {
            tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                    0, 0);
          }
        }
      }
    }

    else if(cancelsPenaltiesList == null && room == null && cancelPenaltyList == null){

      tvAmenity.setText(amenity.description());
      if (amenity.isCancellationPolicy()) {

        tvAmenity.setText("Non-Refundable");

        tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                R.drawable.ic_book_help_grey_19dp, 0);
      } else {
        if(TextUtils.isEmpty(amenity.description())){
          tvAmenity.setText("Non-Refundable");
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);
        }else {
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  0, 0);
        }
      }
    }

    else{
      tvAmenity.setText(amenity.description());
      if (amenity.isCancellationPolicy()) {
        tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                R.drawable.ic_book_help_grey_19dp, 0);
      } else {
        if(TextUtils.isEmpty(amenity.description())){
          tvAmenity.setText("Non-Refundable");
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  R.drawable.ic_book_help_grey_19dp, 0);
        }else {
          tvAmenity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_black_4dp, 0,
                  0, 0);
        }
      }
    }
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

}
