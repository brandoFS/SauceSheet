package com.sauce.sheets;

import android.app.Application;
import android.content.Context;

import com.sauce.sheets.di.component.ApplicationComponent;
import com.sauce.sheets.di.component.DaggerApplicationComponent;
import com.sauce.sheets.di.modules.MainModule;

/**
 * Created by brandomadden on 8/4/17.
 */

public class SheetsApp extends Application {

    private ApplicationComponent applicationComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SheetsApp.context = getApplicationContext();

        applicationComponent = DaggerApplicationComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }


    public static Context getAppContext() {
        return SheetsApp.context;
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public ApplicationComponent updateComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .mainModule(new MainModule(this))
                .build();
        return applicationComponent;
    }

}
