package com.sauce.sheets.di.modules;

import android.app.Application;

import com.sauce.sheets.managers.PersistentDataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DataModule {

    public DataModule() {
    }

    @Provides
    @Singleton
    public PersistentDataManager persistentDataManager(Application app) {
        return new PersistentDataManager(app);
    }


}


