package com.danarcheronline.ekwolz;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class UIUtils {

    private static final String TAG = UIUtils.class.getName();

    public static void setSystemUIBarsToTransparent(Activity activity, int resourceId) {
        setSystemUIToFullScreen(activity.getWindow());
        adjustOrientationMargins(activity, resourceId);
    }

    public static void adjustOrientationMargins(Activity activity, int resourceId) {
        Display display = activity.getWindowManager().getDefaultDisplay();

        int rotation = display.getRotation();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) activity.findViewById(resourceId).getLayoutParams();

        if(!getDeviceType(activity)) {
//            is a phone
            switch (rotation) {
                case Surface.ROTATION_0:
//                degrees: 0 (Portrait)
                    params.setMargins(0,0,0, getNavigationBarHeight(activity.getResources()));
                    break;
                case Surface.ROTATION_90:
//                degrees: 90 (Landscape)
                    params.setMargins(0,0,getNavigationBarHeight(activity.getResources()),0);
                    break;
                case Surface.ROTATION_180:
//                degrees: 180 (Portrait Reversed)
                    params.setMargins(0,getNavigationBarHeight(activity.getResources()),0,0);
                    break;
                case Surface.ROTATION_270:
//                degrees: 270 (Landscape Reversed)
                    params.setMargins(getNavigationBarHeight(activity.getResources()),0,0,0);
                    break;
            }
        }
        else {
//            is a tablet
            params.setMargins(0,0,0, getNavigationBarHeight(activity.getResources()));
        }
    }

    public static boolean getDeviceType(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }


    private static int getNavigationBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void setSystemUIToFullScreen(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

}
