package com.skypremiuminternational.app.app.features.demo_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationPresenter;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationView;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DemoActvity extends BaseActivity<DemoPresenter>  implements DemoView<DemoPresenter>{



    //===
    public static void startMe(Context context){
        Intent intent = new Intent(context,DemoActvity.class);
        intent.putExtra("asd","sad");
        context.startActivity(intent);
    }
//====
    @Inject
    @Override
    public void injectPresenter(DemoPresenter presenter) {
        super.injectPresenter(presenter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//====
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        presenter.action("sadasd");
    }

    @Override
    public void renderA() {
        //do action A
    }

    @Override
    public void renderB(String str) {
        //do action B

    }

    @Override
    public void renderC() {
        //do action C
    }

    @Override
    public void renderD() {
        //do action D
    }

}
