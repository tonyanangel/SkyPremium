package com.skypremiuminternational.app.app.features.profile.my_favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;

import java.util.ArrayList;
import java.util.List;

public class FavouritePartnerAdapter extends RecyclerView.Adapter<MyFavouritePartnerViewHolder>{

        private List<FavouriteListResponse> dataList = new ArrayList<>();
        private com.skypremiuminternational.app.app.features.profile.my_favourites.FavouritePartnerAdapter.ActionListener actionListener;

        public void setActionListener(com.skypremiuminternational.app.app.features.profile.my_favourites.FavouritePartnerAdapter.ActionListener actionListener) {
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
        public MyFavouritePartnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourites_partners, parent, false);
            final MyFavouritePartnerViewHolder viewHolder = new MyFavouritePartnerViewHolder(view);
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
        public void onBindViewHolder(MyFavouritePartnerViewHolder holder, int position) {
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
