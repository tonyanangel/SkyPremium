package com.skypremiuminternational.app.app.features.profile.invite_friend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.ViewRatioUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;



public class InviteFriendActivity extends AppCompatActivity {

  @BindView(R.id.tv_description)
  TextView tvDescription;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.ivBanner)
  ImageView iv;
  @Inject
  ErrorMessageFactory errorMessageFactory;
  private String referralCode;

  private String memberReferrerSalutation;
  private String memberRefererFirstName;
  private String memberRefererLastName;
  private String description;
  private String imageBannerUrl;

  public static void startMe(Context context, String referralCode, String memberReferrerSalutation,
                             String memberRefererFirstName, String memberRefererLastName, String description,String imageBannerUrl) {
    Intent intent = new Intent(context, InviteFriendActivity.class);
    intent.putExtra("referral_code", referralCode);
    intent.putExtra("memberReferrerSalutation", memberReferrerSalutation);
    intent.putExtra("memberRefererFirstName", memberRefererFirstName);
    intent.putExtra("memberRefererLastName", memberRefererLastName);
    intent.putExtra("description", description);
    //20200403 WIKI Toan Tran - get url image banner
    intent.putExtra("image",imageBannerUrl);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_invite_friend);
    ButterKnife.bind(this);
    tvTitle_toolbar.setText(getResources().getString(R.string.invite_friend));
    ViewRatioUtils.set3_1(iv, 0, this);

    Intent intent = getIntent();
    if (intent != null) {
      referralCode = intent.getStringExtra("referral_code");
      memberRefererLastName = intent.getStringExtra("memberRefererLastName");
      memberRefererFirstName = intent.getStringExtra("memberRefererFirstName");
      memberReferrerSalutation = intent.getStringExtra("memberReferrerSalutation");
      description = intent.getStringExtra("description");
      //20200403 WIKI Toan Tran - get url image banner
      imageBannerUrl = intent.getStringExtra("image");
      tvDescription.setText(Html.fromHtml(description));
    }

    //20200403 WIKI Toan Tran - show image banner form api
    Picasso.get()
            .load(imageBannerUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.background_invite_friend)
            .into(iv);
  }

  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    finish();
  }

  @OnClick(R.id.tvInvite)
  void onClickInvite() {
    if (referralCode == null || TextUtils.isEmpty(referralCode.trim())) {
      showNoReferralCodeWarning();
    } else {
      String referralText =
          String.format(getString(R.string.referral_text), memberReferrerSalutation,
              memberRefererFirstName, memberRefererLastName,
              BuildConfig.REFERRAL_LINK + referralCode);

      Intent sendIntent = new Intent();
      sendIntent.setAction(Intent.ACTION_SEND);
      sendIntent.putExtra(Intent.EXTRA_TEXT, referralText);
      sendIntent.setType("text/plain");
      startActivity(Intent.createChooser(sendIntent, ""));
    }
  }

  public void showNoReferralCodeWarning() {
    new AlertDialog.Builder(this).setMessage("No referral code found!")
        .setCancelable(false)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            finish();
          }
        })
        .show();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
