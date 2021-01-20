package com.skypremiuminternational.app.app.features.checkout.steptwo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.skypremiuminternational.app.R;

/**
 * Created by aeindraaung on 2/13/18.
 */

public class ProcessingOrderFragment extends DialogFragment {

  public static final int DIALOG_TYPE_PROCESSING_ORDER = 1;
  public static final int DIALOG_TYPE_PROCESSING_BOOKING = 3;
  public static final int DIALOG_TYPE_ERROR = 2;

  private int dialogType;

  private ActionListener actionListener;

  public ProcessingOrderFragment() {

  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public interface ActionListener {
    void onCheckoutClick();
  }

  public static ProcessingOrderFragment newInstance(int dialogType) {
    ProcessingOrderFragment fragment = new ProcessingOrderFragment();
    fragment.dialogType = dialogType;
    return fragment;
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_fragment_processing);
    ViewGroup layoutError = dialog.findViewById(R.id.layout_error);
    TextView title = dialog.findViewById(R.id.tvTitle_toolbar);
    TextView tvStatus = dialog.findViewById(R.id.txt_processing);
    if (dialogType == DIALOG_TYPE_PROCESSING_BOOKING) {
      tvStatus.setText(R.string.txt_processing_booking);
    }
    title.setText(getString(R.string.title_order_error));
    if (dialogType == DIALOG_TYPE_ERROR) {
      layoutError.setVisibility(View.VISIBLE);
    } else {
      layoutError.setVisibility(View.GONE);
    }

    ImageView imgClose = dialog.findViewById(R.id.img_close);
    TextView tvCheckout = dialog.findViewById(R.id.tvCheckout);

    imgClose.setOnClickListener(v -> dismiss());
    tvCheckout.setOnClickListener(v -> {
      if (actionListener != null) {
        actionListener.onCheckoutClick();
      }
    });
    return dialog;
  }

  @Override public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }
}
