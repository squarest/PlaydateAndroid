package com.make.playdate.dagger;

import com.make.playdate.auth.di.LoginComponent;
import com.make.playdate.auth.di.LoginModule;
import com.make.playdate.chat.di.ChatComponent;
import com.make.playdate.chat.di.ChatModule;
import com.make.playdate.dagger.modules.AppModule;
import com.make.playdate.dagger.modules.DataModule;
import com.make.playdate.dagger.modules.NetModule;
import com.make.playdate.main.di.MainComponent;
import com.make.playdate.main.di.MainModule;
import com.make.playdate.playdate.di.PlaydateComponent;
import com.make.playdate.playdate.di.PlaydateModule;
import com.make.playdate.preference.di.PreferenceComponent;
import com.make.playdate.preference.di.PreferenceModule;
import com.make.playdate.questions.di.QAComponent;
import com.make.playdate.questions.di.QAModule;

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

    PreferenceComponent plusPreferenceModule(PreferenceModule preferenceModule);

    QAComponent plusQAComponent(QAModule qaModule);
}
