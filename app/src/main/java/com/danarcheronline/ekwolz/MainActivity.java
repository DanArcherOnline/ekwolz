package com.danarcheronline.ekwolz;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danarcheronline.ekwolz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    DisplayManager.DisplayListener mDisplayListener;
    int mAppContainerResourceId;
    MainViewModel mViewModel;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mAppContainerResourceId = R.id.appContainer;

//        make the status bar and navigation bar transparent
        UIUtils.setSystemUIBarsToTransparent(this, mAppContainerResourceId);

//        have the margins adjust appropriately when the devices orientation changes
        enableMarginAdjustmentDetection();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisplayManager displayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        displayManager.unregisterDisplayListener(mDisplayListener);

    }

    private void enableMarginAdjustmentDetection() {
        mDisplayListener = new DisplayManager.DisplayListener() {

            @Override
            public void onDisplayAdded(int displayId) {
            }

            @Override
            public void onDisplayRemoved(int displayId) {
            }

            @Override
            public void onDisplayChanged(int displayId) {
                UIUtils.adjustOrientationMargins(MainActivity.this, mAppContainerResourceId);
            }
        };

        DisplayManager displayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        displayManager.registerDisplayListener(mDisplayListener, new Handler());
    }
}


