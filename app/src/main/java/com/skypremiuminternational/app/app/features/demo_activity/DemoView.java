package com.skypremiuminternational.app.app.features.demo_activity;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

public interface DemoView<T extends Presentable> extends Viewable<T>  {
    void renderA();
    void renderB(String str);
    void renderC();
    void renderD();
}
