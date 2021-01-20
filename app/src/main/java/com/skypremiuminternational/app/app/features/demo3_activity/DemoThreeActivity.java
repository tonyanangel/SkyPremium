package com.skypremiuminternational.app.app.features.demo3_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.demo_activity.DemoPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DemoThreeActivity extends BaseActivity<DemoThreePresenter> implements DemoThreeView<DemoThreePresenter> {

    public static void startMe(Context context){
        Intent intent = new Intent(context,DemoThreeActivity.class);
        context.startActivity(intent);

    }

    ProgressDialog progressDialog;

    @Inject
    @Override
    public void injectPresenter(DemoThreePresenter presenter) {
        super.injectPresenter(presenter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        presenter.getProduct();

    }

    @Override
    public void renderA() {

    }

    @Override
    public void renderB(String str) {

    }

    @Override
    public void renderC() {

    }

    @Override
    public void renderD() {

    }

    @Override
    public void renderProduct(DemoResponse value) {

        Log.d("PRODUCT_TEST","size : " + value.getItems().size());
        Log.d("PRODUCT_TEST","Name[0] : " + value.getItems().get(0).getName());
        Log.d("PRODUCT_TEST","Price[0] : " + value.getItems().get(0).getPrice());
    }

    @Override
    public void showDialog() {
        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
