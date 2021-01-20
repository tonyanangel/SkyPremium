package com.skypremiuminternational.app.app.features.forgot_password.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.andrognito.kerningview.KerningTextView;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.PasswordTransformationMethod;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.domain.exception.signin.PasswordDoNotMatchException;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class UpdatePasswordActivity extends BaseActivity<UpdatePasswordPresenter>
    implements UpdatePasswordView<UpdatePasswordPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.stilCurrentPassword)
  SkyTextInputLayout stilCurrentPassword;
  @BindView(R.id.stilNewPassword)
  SkyTextInputLayout stilNewPassword;
  @BindView(R.id.stilConfirmPassword)
  SkyTextInputLayout stilConfirmPassword;
  @BindView(R.id.tvSubmit)
  KerningTextView tvSubmit;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ProgressDialog progressDialog;

  UserDetailResponse userDetailResponse;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, UpdatePasswordActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_password);
    ButterKnife.bind(this);

    if (tvSubmit != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        tvSubmit.setLetterSpacing(0.0f);
      } else {
        tvSubmit.setKerningFactor(0.0f);
      }
    }
    tvTitle_toolbar.setText("Change Password");

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage(getString(R.string.submitting));

    stilCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    stilNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    stilConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    stilCurrentPassword.setTransformationMethod(new PasswordTransformationMethod());
    stilNewPassword.setTransformationMethod(new PasswordTransformationMethod());
    stilConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

    presenter.getUserDetail();
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
  public void injectPresenter(UpdatePasswordPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    finish();
  }

  @OnClick(R.id.tvSubmit)
  void onClickSubmit() {
    if (!Validator.isPasswordValid(stilCurrentPassword.getText())) {
      stilCurrentPassword.showError();
      Toast.makeText(this, "Invalid Current Password!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (!Validator.isPasswordValid(stilNewPassword.getText())) {
      stilNewPassword.showError();
      stilConfirmPassword.showError();
      Toast.makeText(this, "Invalid New Password!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (!Validator.isPasswordValid(stilConfirmPassword.getText())) {
      stilNewPassword.showError();
      stilConfirmPassword.showError();
      Toast.makeText(this, "Invalid Confirm Password!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (!stilNewPassword.getText().equals(stilConfirmPassword.getText())) {
      stilNewPassword.showError();
      stilConfirmPassword.showError();
      Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
      return;
    }
    stilCurrentPassword.hideError();
    stilNewPassword.hideError();
    stilConfirmPassword.hideError();
    presenter.getPasswordHash(stilNewPassword.getText(), userDetailResponse);
  }

  @Override
  public void render(UpdatePasswordViewState viewState) {
    {
      if (viewState.isLoading()) {
        progressDialog.show();
      } else {
        progressDialog.dismiss();
        if (viewState.isSuccess()) {
          Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
          finish();
        } else {

          if (viewState.userDetailResponse() != null) {
            this.userDetailResponse = viewState.userDetailResponse();
          }

          if (viewState.error() != null) {
            if (viewState.error() instanceof PasswordDoNotMatchException) {
              stilNewPassword.showError();
              stilConfirmPassword.showError();
              Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                  Toast.LENGTH_SHORT).show();
            } else {
              stilCurrentPassword.showError();
              stilNewPassword.showError();
              stilConfirmPassword.showError();
              if(viewState.message().isEmpty()){
                Toast.makeText(this, "Fail to load information due to missing Member Number",
                        Toast.LENGTH_SHORT).show();
              }
              else{
                Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
                        Toast.LENGTH_SHORT).show();
              }
            }
          }
        }
      }
    }
  }
}
