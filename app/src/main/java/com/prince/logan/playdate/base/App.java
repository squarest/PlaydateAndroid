package com.prince.logan.playdate.base;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.prince.logan.playdate.auth.di.LoginComponent;
import com.prince.logan.playdate.auth.di.LoginModule;
import com.prince.logan.playdate.chat.di.ChatComponent;
import com.prince.logan.playdate.chat.di.ChatModule;
import com.prince.logan.playdate.dagger.AppComponent;
import com.prince.logan.playdate.dagger.DaggerAppComponent;
import com.prince.logan.playdate.dagger.modules.AppModule;
import com.prince.logan.playdate.main.di.MainComponent;
import com.prince.logan.playdate.main.di.MainModule;
import com.prince.logan.playdate.playdate.di.PlaydateComponent;
import com.prince.logan.playdate.playdate.di.PlaydateModule;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static LoginComponent loginComponent;
    private static MainComponent mainComponent;
    private static ChatComponent chatComponent;
    private static PlaydateComponent playdateComponent;

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }


    public static ChatComponent getChatComponent() {
        return chatComponent;
    }

    public static PlaydateComponent getPlaydateComponent() {
        return playdateComponent;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        appComponent = buildAppComponent();
        loginComponent = buildLoginComponent();
        mainComponent = buildMainComponent();
        chatComponent = buildChatComponent();
        playdateComponent = buildPlaydateComponent();
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

    private PlaydateComponent buildPlaydateComponent() {
        if (playdateComponent == null) {
            playdateComponent = appComponent.plusPlaydateComponent(new PlaydateModule());
        }
        return playdateComponent;
    }
}
