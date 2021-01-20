package com.skypremiuminternational.app.app.features.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.app.utils.listener.NotificationItemClickListener;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationViewHolder>{

  public static final boolean READ = true;
  public static final boolean UNREAD = false;

  Context context;
  static List<NotificationItem> list;

  boolean isActionMode = false;
  boolean isCheckAll = false;
  NotificationItemClickListener<NotificationItem> itemClickListener;

  public NotificationAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);

    return new NotificationViewHolder(view);
  }


  public void setItemClickListener(NotificationItemClickListener<NotificationItem> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
  @Override
  public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
    holder.bind(this.list.get(position),isActionMode);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        itemClickListener.onItemClicked(list.get(position),position);
      }
    });
  }

  void setActionMode(boolean flag){
    this.isActionMode = flag;
    for(NotificationItem payload : this.list){
      payload.setChecked(false);
    }
    notifyDataSetChanged();
  }



  @Override
  public int getItemCount() {
    if(list!=null)
      return list.size();
    else
      return 0;
   // return 12;
  }

  void setData(List<NotificationItem> list){
    this.list =new ArrayList<>();
    this.list = list;
    notifyDataSetChanged();
  }
  List<NotificationItem> getData(){
    return this.list;
  }

  void toggleSelectAll(){
    if(isCheckAll){
      isCheckAll = false;
    }else {
      isCheckAll = true;

    }

    for(NotificationItem item : this.list){
      item.setChecked(isCheckAll);
    }
    notifyDataSetChanged();
  }

  boolean isCheckAll(){
    return  isCheckAll;
  }

  void setCheckAllFlag(boolean flag){
    this.isCheckAll = flag;
  }

  void removeListChecked(){
    for(int i = 0 ; i < list.size() ; i++){
      if(list.get(i).isChecked()){
        list.remove(i);
        i--;
      }
    }
    notifyDataSetChanged();
  }

  void removeItem(int position){
    list.remove(position);
    notifyItemRemoved(position);
  }

}
