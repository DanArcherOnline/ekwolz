package com.danarcheronline.ekwolz.Utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.danarcheronline.ekwolz.R;

public class UIUtils {

    private static final String TAG = UIUtils.class.getName();

    /**
     * Makes the passed in activity full screen, which lets the bg go behind the status and nav bar
     *
     * @param activity   that you want to be full screen
     * @param resourceId for the container view that wraps your content container view
     */
    public static void setSystemUIBarsToTransparent(Activity activity, int resourceId) {
        setSystemUIToFullScreen(activity.getWindow());
        adjustOrientationMargins(activity, resourceId);
    }

    /**
     * Checks which orientation the device is currently in from the following:
     * - landscape
     * - portrait
     * - landscape reversed
     * - portrait reversed
     * It checks whether the device is a mobile device or tablet.
     * Then the same amount of margin as the height of the nav bar is added to the appropriate side
     *
     * @param activity
     * @param resourceId
     */
    public static void adjustOrientationMargins(Activity activity, int resourceId) {
        Display display = activity.getWindowManager().getDefaultDisplay();

        int rotation = display.getRotation();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) activity.findViewById(resourceId).getLayoutParams();

        if (!getDeviceType(activity)) {
            //is a phone
            switch (rotation) {
                case Surface.ROTATION_0:
                    //degrees: 0 (Portrait)
                    params.setMargins(0, 0, 0, getNavigationBarHeight(activity.getResources()));
                    break;
                case Surface.ROTATION_90:
                    //degrees: 90 (Landscape)
                    params.setMargins(0, 0, getNavigationBarHeight(activity.getResources()), 0);
                    break;
                case Surface.ROTATION_180:
                    //degrees: 180 (Portrait Reversed)
                    params.setMargins(0, getNavigationBarHeight(activity.getResources()), 0, 0);
                    break;
                case Surface.ROTATION_270:
                    //degrees: 270 (Landscape Reversed)
                    params.setMargins(getNavigationBarHeight(activity.getResources()), 0, 0, 0);
                    break;
            }
        } else {
            //is a tablet
            params.setMargins(0, 0, 0, getNavigationBarHeight(activity.getResources()));
        }
    }

    /**
     * Uses a boolean resource that changes for devices wider the 600dp (which means its a tablet)
     *
     * @param activity
     * @return the boolean which shows if the device is a tablet or not
     */
    public static boolean getDeviceType(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    /**
     * Gets the height of the devices navigation bar
     *
     * @param resources
     * @return an integer representing the dp's of the nav bars height
     */
    private static int getNavigationBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Sets the window to full screen
     *
     * @param window that will be made full screen
     */
    private static void setSystemUIToFullScreen(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

}
