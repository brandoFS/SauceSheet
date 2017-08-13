package com.sauce.sheets.di.component;


import com.sauce.sheets.di.modules.DataModule;
import com.sauce.sheets.di.modules.MainModule;
import com.sauce.sheets.ui.activity.BaseActivity;
import com.sauce.sheets.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {MainModule.class, DataModule.class})

public interface ApplicationComponent {

    //Activities
    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);




}
