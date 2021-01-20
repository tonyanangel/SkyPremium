package com.skypremiuminternational.app.app.features.notifications;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.utils.DateFormatter;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.SkyPayload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ck_notification)
  public CheckBox ckNotification;
  @BindView(R.id.img_unread)
  public ImageView imgUnread;
  @BindView(R.id.tv_title_notification)
  public TextView tvTitle;
  @BindView(R.id.tv_content)
  public TextView tvContent;
  @BindView(R.id.tv_date)
  public TextView tvDate;
  @BindView(R.id.img_arrow)
  public ImageView imgArrow;


  public void bind(NotificationItem notification, boolean isActionMode) {


    //set content
    tvTitle.setText(notification.getTitle());
    tvContent.setText(notification.getBody());
    ckNotification.setChecked(notification.isChecked());


    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date date =null ;
    try {
      date  = dateFormatter.parse(notification.getReceiveTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String displayDate = "";
    if(date !=null) {
      SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
      displayDate  = format.format(date);
      tvDate.setText(displayDate);
    }else {
      tvDate.setText(displayDate);
    }
    //setOnCheckChange(notification);
    setupActionMode(isActionMode);
    // check read
    if (!notification.getIsRead()) {
      if (isActionMode) {
        imgUnread.setVisibility(View.INVISIBLE);
      } else {
        imgUnread.setVisibility(View.VISIBLE);
      }

    } else {
      imgUnread.setVisibility(View.INVISIBLE);
    }


     // logic hide arrow
    if((TextUtils.isEmpty(notification.getLaunchUrl())
        &&TextUtils.isEmpty(notification.getAdditionalData().getRedirect()))
        &&TextUtils.isEmpty(notification.getAdditionalData().getKeyword())) {
      imgArrow.setVisibility(View.INVISIBLE);
    }else {
      imgArrow.setVisibility(View.VISIBLE);
    }
  }


  public NotificationViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  void setupActionMode(boolean isActionMode) {
    if (isActionMode) {
      this.ckNotification.setVisibility(View.VISIBLE);
      this.imgUnread.setVisibility(View.INVISIBLE);
    } else {
      this.ckNotification.setVisibility(View.INVISIBLE);
      this.imgUnread.setVisibility(View.VISIBLE);
    }

  }

  void setOnCheckChange(NotificationItem notification) {
    ckNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        notification.setChecked(b);
      }
    });
  }
}
