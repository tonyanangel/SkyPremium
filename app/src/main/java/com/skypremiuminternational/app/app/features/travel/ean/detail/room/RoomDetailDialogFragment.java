package com.skypremiuminternational.app.app.features.travel.ean.detail.room;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.domain.models.ean.Room;

import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class RoomDetailDialogFragment extends BaseDialogFragment<RoomDetailPresenter>
    implements RoomDetailView<RoomDetailPresenter> {

  @BindView(R.id.tv_instruction)
  TextView tvInstruction;
  @BindView(R.id.tv_min_check_in_age)
  TextView tvMinCheckInAge;
  @BindView(R.id.webView)
  WebView webView;
  @BindView(R.id.tv_name)
  TextView tvRoomName;
  @BindView(R.id.txtCheckInBeginTime)
  TextView txtCheckInBeginTime;
  @BindView(R.id.txtCheckInEndTime)
  TextView tvCheckOutTime;
  @BindView(R.id.tv_special_instruction)
  TextView tvSpecialInstruction;
  private Room room;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_room_detail;
  }

  public RoomDetailDialogFragment() {

  }

  public static RoomDetailDialogFragment newInstance(Room room) {
    RoomDetailDialogFragment fragment = new RoomDetailDialogFragment();
    fragment.room = room;
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(true);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    tvRoomName.setText(room.name());
    formatTextWebView(webView);


    webView.loadData(room.detail(), "text/html", null);
    if (Validator.isTextValid(room.minCheckInAge())) {
      tvMinCheckInAge.setVisibility(View.VISIBLE);
      tvMinCheckInAge.setText("Minimum check-in age is " + room.minCheckInAge());
    } else {
      tvMinCheckInAge.setVisibility(View.GONE);
    }
    txtCheckInBeginTime.setText(
        getString(R.string.txt_checkin_begin) + " " + room.checkInBeginTime());

    tvCheckOutTime.setText("Check-out time is " + room.checkoutTime());

    if (Validator.isTextValid(room.checkInInstructions())) {
      String instruction = "Check-in Instructions: ";
      CharSequence charSequence =
          instruction + Html.fromHtml(room.checkInInstructions());
      SpannableString spannableInstruction = new SpannableString(charSequence);
      spannableInstruction.setSpan(new StyleSpan(Typeface.BOLD), 0, instruction.length(), 0);
      tvInstruction.setVisibility(View.VISIBLE);
      tvInstruction.setText(spannableInstruction);
    } else {
      tvInstruction.setVisibility(View.GONE);
    }
    if (Validator.isTextValid(room.specialCheckInInstructions())) {
      tvSpecialInstruction.setVisibility(View.VISIBLE);

      String specialInstruction = "Special Check-in Instructions: ";
      CharSequence charSequence2 =
          specialInstruction + Html.fromHtml(room.specialCheckInInstructions());
      SpannableString spannableSpeInstruction = new SpannableString(charSequence2);
      spannableSpeInstruction.setSpan(new StyleSpan(Typeface.BOLD), 0, specialInstruction.length(), 0);
      tvSpecialInstruction.setText(spannableSpeInstruction);
    } else {
      tvSpecialInstruction.setVisibility(View.GONE);
    }
  }

  private void formatTextWebView(WebView webView) {
    WebSettings webSettings = webView.getSettings();
    WebViewClient webViewClient =  new WebViewClient(){
      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

         handler.cancel();
      }
    };
    webView.setWebViewClient(webViewClient);
    webSettings.setDefaultFontSize(12);
  }

  @Inject
  @Override
  public void injectPresenter(RoomDetailPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @OnClick(R.id.iv_close)
  void onCloseClicked() {
    dismiss();
  }
}
