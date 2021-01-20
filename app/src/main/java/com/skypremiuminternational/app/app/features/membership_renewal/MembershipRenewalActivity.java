package com.skypremiuminternational.app.app.features.membership_renewal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.info.InfoActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;
import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import okhttp3.ResponseBody;
import rx.Subscription;

/**
 *  Created by WIKI Toan Tran 20200428
 */
public class MembershipRenewalActivity extends BaseActivity<MembershipRenewalPresenter>
        implements MembershipRenewalView<MembershipRenewalPresenter>  {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvTitle_toolbar)
    TextView titleToolbar;
    Context context ;
    public static void startMe(Context context) {
        Intent intent = new Intent(context, MembershipRenewalActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_membership_renewal);
        ButterKnife.bind(this);

        titleToolbar.setText(R.string.renewal_payment);

        setWebViewEvent();
        presenter.getUserDetail();

    }


    @Inject
    @Override
    public void injectPresenter(MembershipRenewalPresenter presenter) {
        super.injectPresenter(presenter);
    }


    void setWebViewEvent(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                 handler.cancel();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (url.contains( BuildConfig.RENEWAL_DOMAIN+"/membership/renew-fail")) {
                    finish();
                }else if(url.contains( BuildConfig.RENEWAL_DOMAIN+"/membership/renew-success")){
                    EditProfileActivity.startMe(MembershipRenewalActivity.this);
                    finish();
                }
                else
                    view.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(wvc);
    }

    @Override
    public void renderWebView(UserDetailResponse response) {
        String memberNumber="";
        String firstName="";
        String lastName="";
        String token ="";

        /*  <<START>>   20200428 WIKI Toan Tran -  get info to encrypt*/
        for(int i=0 ; i <response.getCustomAttributes().size();i++){
            if (response.getCustomAttributes().get(i).getAttributeCode().equals("member_number")) {
                memberNumber = response.getCustomAttributes().get(i).getValue();
            }else if (response.getCustomAttributes().get(i).getAttributeCode().equals("firstname")) {
                firstName = response.getCustomAttributes().get(i).getValue();
            }else  if (response.getCustomAttributes().get(i).equals("lastname")) {
                lastName = response.getCustomAttributes().get(i).getValue();
            }
        }
        /*  <<START>>   20200428 WIKI Toan Tran -  get info get info to encrypt */


        // 20200428 WIKI Toan Tran -  encrypt and load WebView
        token = memberNumber+"|"+firstName+"|"+lastName;
        presenter.getRenewalToken(token);


    }

    @Override
    public void loadWebView(ResponseBody response) {
        String token ="";
        try {
            token  =  response.string().replace("\"","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView.loadUrl(URL.RENEWAL+token+"&type=renew");
        Log.d("RENEWALMEM",URL.RENEWAL+""+token+"&type=renew");
    }


    @OnClick(R.id.ivNavigation_toolbar)
    void onClickMenu() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
