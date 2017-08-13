package com.sauce.sheets.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sauce.sheets.ui.activity.BaseActivity;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NavigationManager {

    private String currentLauncher;


    @Inject
    public NavigationManager() {

    }

    public void startActivity(Context context, Class activity) {
        setupActivityAndRun(context, activity, null, false, 0);
    }

    public void startActivity(Context context, Class activity, Bundle bundle) {
        setupActivityAndRun(context, activity, bundle, false, 0);
    }

    public void startActivityForResult(Context context, Class activity, Bundle bundle, int code) {
        setupActivityAndRun(context, activity, bundle, false, code);
    }

    public void startActivityAndCloseCurrent(Context context, Class activity) {
        setupActivityAndRun(context, activity, null, true, 0);
    }

    public void startActivityAndCloseCurrent(Context context, Class activity, Bundle bundle) {
        setupActivityAndRun(context, activity, bundle, true, 0);
    }


    public void resetCurrentLauncher(Context context) {
        if (isSameLauncher(context)) {
            currentLauncher = null;
        }
    }


    private void setupActivityAndRun(Context context, Class activity, Bundle bundle, Boolean finish, int code) {
        if (!setCurrentLauncher(context)) {
            return;
        }

        if (finish) {
            ((Activity) context).finish();
        }

        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtra("Bundle", bundle);
        }

        if (code != 0) {
            ((BaseActivity) context).startActivityForResult(intent, code);
        } else {
            context.startActivity(intent);
        }
    }

    private boolean setCurrentLauncher(Context context) {
        if (context == null || isSameLauncher(context)) {
            return false;
        }
        currentLauncher = context.toString();
        return true;
    }

    private boolean isSameLauncher(Context context) {
        return (currentLauncher != null && currentLauncher.equals(context.toString()));
    }

}
