package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class DialogOnClickListenerInterface implements DialogInterface.OnClickListener {

    private final Activity activity;

    public DialogOnClickListenerInterface(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
        activity.startActivity(intent);
    }
}
