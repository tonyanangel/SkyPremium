package com.skypremiuminternational.app.app.features.profile.my_favourites;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<MyFavouriteViewHolder> {

  private List<FavouriteListResponse> dataList = new ArrayList<>();
  private ActionListener actionListener;

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public List<FavouriteListResponse> getDataList() {
    return dataList;
  }

  public void setDataList(List<FavouriteListResponse> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  public FavouriteListResponse getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public MyFavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
    final MyFavouriteViewHolder viewHolder = new MyFavouriteViewHolder(view);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onClickedItem(dataList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    viewHolder.ivFav.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onClickedFavourite(dataList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(MyFavouriteViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public void addAll(List<FavouriteListResponse> dataObj) {
    dataList.addAll(dataObj);
    notifyDataSetChanged();
  }

  public void set(List<FavouriteListResponse> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  interface ActionListener {
    void onClickedItem(FavouriteListResponse item);

    void onClickedFavourite(FavouriteListResponse item);
  }
}
