package com.skypremiuminternational.app.app.features.notifications.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skypremiuminternational.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteNotificationDialog extends Dialog  {



  @BindView(R.id.tv_yes)
  TextView tvYes;
  @BindView(R.id.tv_no)
  TextView tvNo;
  @BindView(R.id.tv_message)
  TextView tvMessage;


  Context context;
  OnClickConfirmDeleteListener listener;
  String message;



  public DeleteNotificationDialog(@NonNull Context context, String message, OnClickConfirmDeleteListener listener ) {
    super(context,R.style.DialogThemeNull);
    this.context  = context;
    this.listener = listener;
    this.message = message;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_delete_single_notification);
    ButterKnife.bind(this);
    tvMessage.setText(message);
  }

  @OnClick(R.id.tv_yes)
  void clickYes(){
    listener.onClickYes(this);
    dismiss();
  }

  @OnClick(R.id.tv_no)
  void clickNo(){
    listener.onClickNo(this);
    dismiss();
  }

  public interface OnClickConfirmDeleteListener {
    void onClickNo(DeleteNotificationDialog dialog);

    void onClickYes(DeleteNotificationDialog dialog);
  }

}
