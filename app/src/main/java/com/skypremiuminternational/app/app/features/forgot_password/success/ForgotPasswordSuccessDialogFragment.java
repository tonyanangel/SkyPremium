package com.skypremiuminternational.app.app.features.forgot_password.success;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;

import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class ForgotPasswordSuccessDialogFragment
    extends BaseDialogFragment<ForgotPasswordSuccessPresenter>
    implements ForgotPasswordSuccessView<ForgotPasswordSuccessPresenter> {

  @BindView(R.id.tvMessage)
  TextView tvMessage;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  String email;

  public static ForgotPasswordSuccessDialogFragment newInstance(String email) {
    ForgotPasswordSuccessDialogFragment fragment = new ForgotPasswordSuccessDialogFragment();
    fragment.email = email;
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setCancelable(false);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    //return super.onCreateDialog(savedInstanceState);
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
    ButterKnife.bind(view);
    tvMessage.setText("A link has been sent to "
        + email
        + ". Please follow the instructions in the email to reset your password.");
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }

  @OnClick(R.id.tvContinue)
  void onClickContinue() {
    dismiss();
    getActivity().finish();
    //ResetPasswordActivity.startMe(getActivity());
  }

  @Inject
  @Override
  public void injectPresenter(ForgotPasswordSuccessPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_forgot_password_success;
  }

  @Override
  public void render(ForgotPasswordSuccessViewState viewState) {
    if (viewState.error() != null) {
      Toast.makeText(getActivity(), errorMessageFactory.getErrorMessage(viewState.error()),
          Toast.LENGTH_SHORT).show();
      //edtScan.setText("");
    }
  }
}
