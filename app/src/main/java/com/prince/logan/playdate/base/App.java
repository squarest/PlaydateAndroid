package com.prince.logan.playdate.base;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.prince.logan.playdate.auth.di.LoginComponent;
import com.prince.logan.playdate.auth.di.LoginModule;
import com.prince.logan.playdate.dagger.AppComponent;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static LoginComponent loginComponent;

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        appComponent = buildAppComponent();
        loginComponent = buildLoginComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private LoginComponent buildLoginComponent() {
        if (loginComponent == null)
            loginComponent = appComponent.plusLoginComponent(new LoginModule());
        return loginComponent;
    }
}
