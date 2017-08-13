package com.sauce.sheets.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sauce.sheets.R;
import com.sauce.sheets.SheetsApp;
import com.sauce.sheets.managers.NavigationManager;

import javax.inject.Inject;


public class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().toString();

    @Inject
    NavigationManager mNavigationManager;


    private ProgressDialog progressDialog;
    protected boolean isDestroyed;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        // assign singleton instances to fields
        // We need to cast to `MyApp` in order to get the right method
        ((SheetsApp) getApplication()).getComponent().inject(this);

        // Lock portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationManager.resetCurrentLauncher(this);

    }

    @Override
    protected void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }

    public void showProgressDialog(int resId) {
        if (progressDialog != null) {
            dismissProgressDialog();
            progressDialog = null;
        }
        progressDialog = ProgressDialog.show(this, "", getString(resId), true);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public void showErrorAlertDialog(String title, String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(errorMessage)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showAlertDialogWithButton(String title, String message, String posButton) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posButton.length() > 1 ? posButton : getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public boolean isDying() {
        return isFinishing() || isDestroyed;
    }

    protected void onFinish() {
        finish();
    }

}
