package com.make.playdate.base;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.make.playdate.auth.di.LoginComponent;
import com.make.playdate.auth.di.LoginModule;
import com.make.playdate.chat.di.ChatComponent;
import com.make.playdate.chat.di.ChatModule;
import com.make.playdate.dagger.AppComponent;
import com.make.playdate.dagger.DaggerAppComponent;
import com.make.playdate.dagger.modules.AppModule;
import com.make.playdate.main.di.MainComponent;
import com.make.playdate.main.di.MainModule;
import com.make.playdate.playdate.di.PlaydateComponent;
import com.make.playdate.playdate.di.PlaydateModule;
import com.make.playdate.preference.di.PreferenceComponent;
import com.make.playdate.preference.di.PreferenceModule;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static LoginComponent loginComponent;
    private static MainComponent mainComponent;
    private static ChatComponent chatComponent;
    private static PlaydateComponent playdateComponent;
    private static PreferenceComponent preferenceComponent;

    public static PreferenceComponent getPreferenceComponent() {
        return preferenceComponent;
    }

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
        preferenceComponent = buildPreferenceComponent();
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

    private PreferenceComponent buildPreferenceComponent() {
        if (preferenceComponent == null) {
            preferenceComponent = appComponent.plusPreferenceModule(new PreferenceModule());
        }
        return preferenceComponent;
    }

}
