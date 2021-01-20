package com.skypremiuminternational.app.app.features.forgot_password;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.forgot_password.success.ForgotPasswordSuccessDialogFragment;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.exception.signin.InvalidEmailException;
import com.andrognito.kerningview.KerningTextView;


import android.util.Patterns;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordPresenter>
    implements ForgotPasswordView<ForgotPasswordPresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.stilEmail)
  SkyTextInputLayout stilEmail;
  @BindView(R.id.tvSubmit)
  KerningTextView tvSubmit;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private ProgressDialog progressDialog;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, ForgotPasswordActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    ButterKnife.bind(this);

    if (tvSubmit != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        tvSubmit.setLetterSpacing(0.0f);
      } else {
        tvSubmit.setKerningFactor(0.0f);
      }
    }
    tvTitle_toolbar.setText("Forgot Password");

    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage(getString(R.string.submitting));
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
  public void injectPresenter(ForgotPasswordPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickBack() {
    finish();
  }

  @OnClick(R.id.tvSubmit)
  void onClickSubmit() {
    stilEmail.hideError();
    presenter.onSubmit(stilEmail.getText());
  }

  @Override
  public void render(ForgotPasswordViewState viewState) {
    {
      if (viewState.isLoading()) {
        progressDialog.show();
      } else {
        progressDialog.dismiss();
        if (viewState.isSuccess()) {
          ForgotPasswordSuccessDialogFragment.newInstance(stilEmail.getText())
              .show(getSupportFragmentManager(), "ForgotPasswordSuccess");
          /*finish();
          LandingActivity.startMe(this);*/
        } else {
          stilEmail.showError();
          if (viewState.error() instanceof InvalidEmailException) {
            Toast.makeText(this, "Invalid Email!", Toast.LENGTH_SHORT).show();
            // tilEmail.getEditText().setError(viewState.error().getLocalizedMessage());
          } else {
            String emailInput = stilEmail.getText().toString().trim();

             if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
               Toast.makeText(this, "Invalid Email!",
                       Toast.LENGTH_SHORT).show();
            } else {
               Toast.makeText(this, "An account with this email does not exist!",
                       Toast.LENGTH_SHORT).show();
            }

          }
        }
      }
    }
  }

}
