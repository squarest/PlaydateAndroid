package com.prince.logan.playdate.dagger;

import com.prince.logan.playdate.auth.di.LoginComponent;
import com.prince.logan.playdate.auth.di.LoginModule;
import com.prince.logan.playdate.dagger.modules.AppModule;
import com.prince.logan.playdate.dagger.modules.DataModule;
import com.prince.logan.playdate.dagger.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Chudofom on 28.07.17.
 */
@Component(modules = {AppModule.class, DataModule.class, NetModule.class})
@Singleton
public interface AppComponent {
    LoginComponent plusLoginComponent(LoginModule loginModule);
}
