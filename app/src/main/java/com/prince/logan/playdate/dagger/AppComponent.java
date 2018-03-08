package com.prince.logan.playdate.dagger;

import com.prince.logan.playdate.auth.di.LoginComponent;
import com.prince.logan.playdate.auth.di.LoginModule;
import com.prince.logan.playdate.chat.di.ChatComponent;
import com.prince.logan.playdate.chat.di.ChatModule;
import com.prince.logan.playdate.dagger.modules.AppModule;
import com.prince.logan.playdate.dagger.modules.DataModule;
import com.prince.logan.playdate.dagger.modules.NetModule;
import com.prince.logan.playdate.main.di.MainComponent;
import com.prince.logan.playdate.main.di.MainModule;
import com.prince.logan.playdate.playdate.di.PlaydateComponent;
import com.prince.logan.playdate.playdate.di.PlaydateModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Chudofom on 28.07.17.
 */
@Component(modules = {AppModule.class, DataModule.class, NetModule.class})
@Singleton
public interface AppComponent {
    LoginComponent plusLoginComponent(LoginModule loginModule);

    MainComponent plusMainComponent(MainModule mainModule);

    ChatComponent plusChatComponent(ChatModule chatModule);

    PlaydateComponent plusPlaydateComponent(PlaydateModule playdateModule);
}
