package com.sauce.sheets.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class MainModule {

    private final Application mRivalRecipeApp;


    public MainModule(Application application) {
        this.mRivalRecipeApp = application;
    }

    @Provides
    @Singleton
    Application provideAppContext() {
        return mRivalRecipeApp;
    }


}


