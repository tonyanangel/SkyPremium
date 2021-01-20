package com.skypremiuminternational.app.app.features.demo_two_activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.travel.TravelProductViewHolder;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.demo.Item;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class DemoTwoActivity extends BaseActivity<DemoTwoPresenter> implements DemoTwoView<DemoTwoPresenter>,
ProductActionListener {

    @BindView(R.id.rvDemo)
    RecyclerView recyclerView;
    @BindView(R.id.checkBoxDemo)
    CheckBox cbDemo;

    ProgressDialog progressDialog;


    DemoTwoAdapter adapter;



    public static void startMe(Context context) {
        Intent intent = new Intent(context, DemoTwoActivity.class);
        context.startActivity(intent);
    }


    @Inject
    @Override
    public void injectPresenter(DemoTwoPresenter presenter) {
        super.injectPresenter(presenter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//====
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_two);
        ButterKnife.bind(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        setupRecyclerView();

        presenter.getProduct();




    }

    private void setupRecyclerView() {
        adapter = new DemoTwoAdapter();
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }


    @Override
    public void onItemClicked(Object item, int position) {
        //TODO do something
        Intent intent = new Intent(this, DemoTwoDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFavItemClicked(Object item, int position) {

    }

    @Override
    public void onReserveButton(Object item, int position, TravelProductViewHolder holder) {

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
    public void renderProduct(DemoRes value) {

        adapter.setItemList(value.getItemList());

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
