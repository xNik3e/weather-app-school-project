package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowManager;

public class ScreenHelper {
    private static int displayCutout;

    public static int getHeightInPixels(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int convertToPixels(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void setWindowAttributes(Activity activity) {
        DisplayCutout displayCutout;
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(1280);
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            activity.getWindow().setAttributes(attributes);
            if (window.getDecorView().getRootWindowInsets() != null && (displayCutout = window.getDecorView().getRootWindowInsets().getDisplayCutout()) != null) {
                ScreenHelper.displayCutout = displayCutout.getSafeInsetTop();
            }
        }
    }

    public static int getHeightSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels + displayCutout;
    }

    public static int getWidthInPixels(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
