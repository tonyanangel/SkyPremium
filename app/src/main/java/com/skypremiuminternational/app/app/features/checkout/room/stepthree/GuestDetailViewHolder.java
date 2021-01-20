package com.skypremiuminternational.app.app.features.checkout.room.stepthree;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;

public class GuestDetailViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.tv_contact_number)
  TextView tvContactNumber;
  @BindView(R.id.tv_special_request)
  TextView tvSpecialRequest;
  @BindView(R.id.tv_smoking)
  TextView tvSmoking;
  @BindView(R.id.tv_room_name)
  TextView tvRoomName;
  @BindView(R.id.tv_guest_name)
  TextView tvGuestName;

  public GuestDetailViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(GuestDetail guestDetail) {
    tvRoomName.setText(guestDetail.roomName);
    String guestName = guestDetail.salutation;
    if (!guestDetail.salutation.endsWith(".")) {
      guestName += ". ";
    }
    guestName += guestDetail.guestName;

    tvGuestName.setText(guestName);
    tvContactNumber.setText(String.format("Contact Number: %s", guestDetail.contactNumber));
    String smoking = itemView.getContext().getString(R.string.guest_detail_smoking_label);
    smoking += " ";
    smoking +=
        guestDetail.isSmoking ? itemView.getContext().getString(R.string.guest_detail_yes_label)
            : itemView.getContext().getString(
            R.string.guest_detail_no_label);

    tvSmoking.setText(smoking);
    if (TextUtils.isEmpty(guestDetail.specialRequest)) {
      tvSpecialRequest.setVisibility(View.GONE);
    } else {
      tvSpecialRequest.setVisibility(View.VISIBLE);
      tvSpecialRequest.setText(
          String.format(itemView.getContext().getString(R.string.guest_detail_special_request),
              guestDetail.specialRequest));
    }
  }
}
