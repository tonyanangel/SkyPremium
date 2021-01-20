package com.skypremiuminternational.app.app.features.booking.history;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BookHistoryAdapter extends RecyclerView.Adapter<BookHistoryViewHolder> {
  private List<BookingHistory> bookingHistoryList = new ArrayList<>();
  private ActionListener actionListener;

  public void setHotelHistories(List<BookingHistory> bookingHistoryList) {
    this.bookingHistoryList.clear();
    this.bookingHistoryList.addAll(bookingHistoryList);
    notifyDataSetChanged();
  }

  @Override
  public BookHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_bookings_history, parent, false);
    BookHistoryViewHolder viewHolder = new BookHistoryViewHolder(view);
    viewHolder.itemView.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        BookingHistory bookingHistory = bookingHistoryList.get(viewHolder.getAdapterPosition());
        actionListener.onItemClicked(bookingHistory.id(), bookingHistory.rooms(),viewHolder.getAdapterPosition());
      }
    });

    viewHolder.tvCancel.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        BookingHistory bookingHistory = bookingHistoryList.get(viewHolder.getAdapterPosition());
        //if (bookingHistory.status().equalsIgnoreCase(Constants.BOOKING_STATUS_CONFIRMED)) {
          actionListener.onCancelClicked(
              bookingHistoryList.get(viewHolder.getAdapterPosition()).id(),bookingHistory,bookingHistory.rooms(),viewHolder.getAdapterPosition());
        //}
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(BookHistoryViewHolder holder, int position) {
    holder.bind(bookingHistoryList.get(position));
  }

  @Override
  public int getItemCount() {
    return bookingHistoryList == null ? 0 : bookingHistoryList.size();
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public interface ActionListener {
    void onItemClicked(String bookingId,
                       List<BookingHistory.Room> rooms,int position);

    void onCancelClicked(String bookingId,BookingHistory bookingHistory,List<BookingHistory.Room> rooms,int position);
  }
}
