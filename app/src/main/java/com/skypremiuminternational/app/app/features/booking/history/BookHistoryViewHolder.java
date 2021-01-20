package com.skypremiuminternational.app.app.features.booking.history;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.skypremiuminternational.app.app.utils.DecimalUtil.roundTwoDecimals;

public class BookHistoryViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.tv_booking_id)
  TextView tvBookingId;
  @BindView(R.id.tv_cancel)
  TextView tvCancel;
  @BindView(R.id.tv_booking_date_value)
  TextView tvBookingDate;
  @BindView(R.id.tv_booking_hotel_value)
  TextView tvBookingHotel;
  @BindView(R.id.tv_booking_hotel_value_city)
  TextView tvBookingHotelCity;
  @BindView(R.id.tv_date_of_booking_value)
  TextView tvBookingDateRange;
  @BindView(R.id.tv_booking_days)
  TextView tvCountNights;
  @BindView(R.id.tv_booking_total_cost_value)
  TextView tvTotalCost;
  @BindView(R.id.tv_booking_sky_dollar_earned)
  TextView tvSkyDollarEarned;
  @BindView(R.id.tv_status_value)
  TextView tvStatusValue;

  public BookHistoryViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(BookingHistory bookingHistory) {
    tvBookingId.setText(
            itemView.getContext().getString(R.string.booking_id_label) + " " + bookingHistory.itineraryId());
    if (bookingHistory.status().equalsIgnoreCase(Constants.BOOKING_STATUS_CONFIRMED)) {
      tvCancel.setBackground(
              ContextCompat.getDrawable(itemView.getContext(),
                      R.drawable.booking_history_cancel_btn_bg_enable));
      int accent = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
      tvCancel.setTextColor(accent);
    } else {
      int white = ContextCompat.getColor(itemView.getContext(), R.color.white);
      tvCancel.setTextColor(white);
      tvCancel.setBackground(
              ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_grey_rectangle));
    }
    tvBookingDate.setText(format(bookingHistory.bookingDate()));
    tvBookingHotel.setText(bookingHistory.hotelName());
    tvBookingHotelCity.setVisibility(View.GONE);
    tvBookingDateRange.setText(bookingHistory.bookingPeriod());
    tvCountNights.setText(itemView.getResources()
            .getQuantityString(R.plurals.nights, bookingHistory.nightCount(),
                    bookingHistory.nightCount()));

    if (Validator.isTextValid(bookingHistory.totalCost())) {
      tvTotalCost.setVisibility(View.VISIBLE);
      tvTotalCost.setText(String.format("S$%s", roundTwoDecimals(Double.parseDouble(bookingHistory.totalCost()))));
    } else {
      tvTotalCost.setVisibility(View.INVISIBLE);
    }

    tvSkyDollarEarned.setText(String.format(roundTwoDecimals(Double.parseDouble(bookingHistory.skyDollar()))));

    String bookingStatus = bookingHistory.status();
    String bookingStatusValue = bookingHistory.bookingDataList().get(0).getLabel_status();
    tvStatusValue.setText(StringUtil.toTitleCase(bookingStatusValue.split(" ")));


    if (bookingHistory.bookingDataList().size() > 0) {
        if ((!bookingStatus.equalsIgnoreCase("Canceled") && bookingHistory.bookingDataList().get(0).getDisplayCancelsButton()) || bookingStatus.equalsIgnoreCase("Booked")) {

          tvCancel.setText("Cancel");
        } else {
          tvCancel.setText("View");
          tvSkyDollarEarned.setText("-");
          tvCancel.setBackground(
                  ContextCompat.getDrawable(itemView.getContext(),
                          R.drawable.booking_history_cancel_btn_bg_enable));
          int accent = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
          tvCancel.setTextColor(accent);
        }
    }
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

  private String format(String date) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    try {
      Date date0 = df.parse(date);
      SimpleDateFormat df1 =
          new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT, Locale.getDefault());
      return df1.format(date0);
    } catch (ParseException e) {
      e.printStackTrace();
      return date;
    }
  }
}