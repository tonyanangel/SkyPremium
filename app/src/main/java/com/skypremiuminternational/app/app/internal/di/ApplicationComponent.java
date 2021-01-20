package com.skypremiuminternational.app.app.internal.di;

import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.internal.di.modules.DatabaseModule;
import com.skypremiuminternational.app.app.internal.di.modules.NetworkModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

/**
 * Created by hein on 5/9/17.
 */
@Singleton
@Component(modules = {
    AndroidSupportInjectionModule.class, ApplicationModule.class, BuildersModule.class,
    DatabaseModule.class, NetworkModule.class
})
public interface ApplicationComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(App application);

    ApplicationComponent build();
  }

  void inject(App app);
}
