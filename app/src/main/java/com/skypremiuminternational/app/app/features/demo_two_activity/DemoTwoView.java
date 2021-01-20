package com.skypremiuminternational.app.app.features.demo_two_activity;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.democheckapi.DemoRes;

public interface DemoTwoView<T extends Presentable> extends Viewable<T> {
    void renderA();
    void renderB(String str);
    void renderC();
    void renderD();

    void renderProduct(DemoRes value);

    void showDialog();
    void hideDialog();
}
