package com.skypremiuminternational.app.app.features.reset_password;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.PasswordTransformationMethod;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter>
    implements ResetPasswordView<ResetPasswordPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.tilNewPassword)
  TextInputLayout tilNewPassword;
  @BindView(R.id.tilConfirmNewPassword)
  TextInputLayout tilConfirmNewPassword;
  @BindView(R.id.tvSubmit)
  TextView tvSubmit;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ProgressDialog progressDialog;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, ResetPasswordActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    ButterKnife.bind(this);

    tvTitle_toolbar.setText("Reset Password");

    tilNewPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
    tilConfirmNewPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    //progressDialog.setMessage(getString(R.string.loggingIn));
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }

  @Inject
  @Override
  public void injectPresenter(ResetPasswordPresenter presenter) {
    super.injectPresenter(presenter);
  }

  /*@OnClick(R.id.btnShowPassword) void onShowHidePassword() {
    passwordIsVisible = !passwordIsVisible;
    if (passwordIsVisible) {
      btnShowPassword.setText("Hide Password");
      tilPassword.getEditText().setTransformationMethod(null);
    } else {
      btnShowPassword.setText("Show Password");
      tilPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    tilPassword.getEditText().setSelection(tilPassword.getEditText().getText().length());
  }*/

  @OnClick(R.id.tvSubmit)
  void onClickSubmit() {
    /*presenter.onLogin(tilStaffID.getEditText().getText().toString(),
        tilPassword.getEditText().getText().toString());*/
    LandingActivity.startMe(this);
  }

  @Override
  public void render(ResetPasswordViewState viewState) {
    {
      if (viewState.isLoading()) {
        progressDialog.show();
      } else {
        /*progressDialog.dismiss();
        if (viewState.isSuccess()) {
          finish();
          LandingActivity.startMe(this);
        } else {
          if (viewState.error() instanceof InvalidEmailException) {
            tilStaffID.getEditText().setError(viewState.error().getLocalizedMessage());
          } else if (viewState.error() instanceof InvalidPasswordException) {
            tilPassword.getEditText().setError(viewState.error().getLocalizedMessage());
          } else {
            Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                Toast.LENGTH_SHORT).show();
          }
        }*/
      }
    }
  }
}
