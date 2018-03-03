package com.prince.logan.playdate.base;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.prince.logan.playdate.auth.di.LoginComponent;
import com.prince.logan.playdate.auth.di.LoginModule;
import com.prince.logan.playdate.chat.di.ChatComponent;
import com.prince.logan.playdate.chat.di.ChatModule;
import com.prince.logan.playdate.dagger.AppComponent;
import com.prince.logan.playdate.main.di.MainComponent;
import com.prince.logan.playdate.main.di.MainModule;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static LoginComponent loginComponent;
    private static MainComponent mainComponent;
    private static ChatComponent chatComponent;

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static ChatComponent getChatComponent() {
        return chatComponent;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        appComponent = buildAppComponent();
        loginComponent = buildLoginComponent();
        mainComponent = buildMainComponent();
        chatComponent = buildChatComponent();
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

    private MainComponent buildMainComponent() {
        if (mainComponent == null) {
            mainComponent = appComponent.plusMainComponent(new MainModule());
        }
        return mainComponent;
    }

    private ChatComponent buildChatComponent() {
        if (chatComponent == null) {
            chatComponent = appComponent.plusChatComponent(new ChatModule());
        }
        return chatComponent;
    }
}
