package com.skypremiuminternational.app.app.features.checkout.room.stepone;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.listener.OnTextChangeListener;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import java.util.ArrayList;
import java.util.List;

public class BookerInfoAdapter extends RecyclerView.Adapter<BookerInfoViewHolder> {

  private List<BookerInfo> bookerInfos = new ArrayList<>();
  private final String[] phones;
  private final String[] codes;

  public BookerInfoAdapter(List<PhoneCode.PhoneCode_> phoneCodes) {
    phones = new String[phoneCodes.size()];
    codes = new String[phoneCodes.size()];

    for (int i = 0; i < phoneCodes.size(); i++) {
      PhoneCode.PhoneCode_ phoneCode = phoneCodes.get(i);
      phones[i] = phoneCode.getCountryName();
      codes[i] = phoneCode.getDiallingCode();
    }
  }

  @Override
  public BookerInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_booker_info, viewGroup, false);
    BookerInfoViewHolder viewHolder = new BookerInfoViewHolder(view);

    viewHolder.edtFirstName.setOnTextChangedListener(
        text -> {
          if (hasValidPosition(viewHolder)) {
            getItem(viewHolder).firstName = text;
          }
        });

    viewHolder.edtLastName.setOnTextChangedListener(
        text -> {
          if (hasValidPosition(viewHolder)) {
            getItem(viewHolder).lastName = text;
          }
        });

    viewHolder.edtPhoneNumber.setOnTextChangedListener(
        text -> {
          if (hasValidPosition(viewHolder)) {
            getItem(viewHolder).phoneNumber = text;
          }
        });

    viewHolder.edtSpecialQuest.addTextChangedListener(new OnTextChangeListener() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasValidPosition(viewHolder)) {
          getItem(viewHolder).specialRequest = s.toString();
        }
      }
    });

    viewHolder.cbSmoking.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (hasValidPosition(viewHolder)) {
        getItem(viewHolder).isSmoking = isChecked;
      }
    });
    viewHolder.tvPhoneCode.setOnClickListener(v -> showPhoneDialog(viewHolder));

    viewHolder.salutationOverlay.setOnClickListener(v -> showSalutationDialog(viewHolder));
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(BookerInfoViewHolder bookerInfoViewHolder, int positioni) {
    bookerInfoViewHolder.bind(bookerInfos.get(positioni));
  }

  @Override
  public int getItemCount() {
    return bookerInfos == null ? 0 : bookerInfos.size();
  }

  public void setBookerInfos(List<BookerInfo> bookerInfos) {
    this.bookerInfos = bookerInfos;
    notifyDataSetChanged();
  }

  private boolean hasValidPosition(BookerInfoViewHolder viewHolder) {
    return viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION;
  }

  private BookerInfo getItem(BookerInfoViewHolder viewHolder) {
    return bookerInfos.get(viewHolder.getAdapterPosition());
  }

  private void showPhoneDialog(BookerInfoViewHolder viewHolder) {

    if (hasValidPosition(viewHolder)) {
      BookerInfo bookerInfo = getItem(viewHolder);

      new AlertDialog.Builder(viewHolder.itemView.getContext()).setTitle("Choose dial code")
          .setSingleChoiceItems(phones, bookerInfo.phoneCodePosition, (dialog, position) -> {
            viewHolder.tvPhoneCodeError.setVisibility(View.INVISIBLE);
            bookerInfo.phoneCode = codes[position];
            bookerInfo.phoneCodePosition = position;
            viewHolder.tvPhoneCode.setText(String.format("+%s", bookerInfo.phoneCode));
            dialog.dismiss();
          })
          .setPositiveButton(R.string.btn_label_cancel, null)
          .show();
    }
  }

  private void showSalutationDialog(BookerInfoViewHolder viewHolder) {
    if (hasValidPosition(viewHolder)) {
      final BookerInfo bookerInfo = getItem(viewHolder);

      new AlertDialog.Builder(viewHolder.itemView.getContext()).setTitle("Choose salutation")
          .setSingleChoiceItems(Constants.SALUTATIONS, bookerInfo.salutationPosition,
              (dialog, item) -> {
                bookerInfo.salutationPosition = item;
                String salutation = Constants.SALUTATIONS[item];
                bookerInfo.salutation = salutation.substring(0, salutation.length() - 1);
                viewHolder.edtSalutation.setText(Constants.SALUTATIONS[item]);
                dialog.dismiss();
              })
          .show();
    }
  }

  public List<BookerInfo> getItems() {
    return bookerInfos;
  }
}
