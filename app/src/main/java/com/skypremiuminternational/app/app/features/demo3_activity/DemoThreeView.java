package com.skypremiuminternational.app.app.features.demo3_activity;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.demo.DemoResponse;

public interface DemoThreeView<T extends Presentable> extends Viewable<T> {
    void renderA();
    void renderB(String str);
    void renderC();
    void renderD();


    void renderProduct(DemoResponse value);

    void showDialog();
    void hideDialog();
}
