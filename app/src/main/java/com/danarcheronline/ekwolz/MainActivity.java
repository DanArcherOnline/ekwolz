package com.danarcheronline.ekwolz;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

        setViewOnClickListeners();

        logImportantInfo();

    }

    private void setViewOnClickListeners() {
        mBinding.button0.setOnClickListener(new NumberPadButtonOnClickListener("0"));
        mBinding.button1.setOnClickListener(new NumberPadButtonOnClickListener("1"));
        mBinding.button2.setOnClickListener(new NumberPadButtonOnClickListener("2"));
        mBinding.button3.setOnClickListener(new NumberPadButtonOnClickListener("3"));
        mBinding.button4.setOnClickListener(new NumberPadButtonOnClickListener("4"));
        mBinding.button5.setOnClickListener(new NumberPadButtonOnClickListener("5"));
        mBinding.button6.setOnClickListener(new NumberPadButtonOnClickListener("6"));
        mBinding.button7.setOnClickListener(new NumberPadButtonOnClickListener("7"));
        mBinding.button8.setOnClickListener(new NumberPadButtonOnClickListener("8"));
        mBinding.button9.setOnClickListener(new NumberPadButtonOnClickListener("9"));

        mBinding.plusButton.setOnClickListener(new OperatorButtonOnClickListener(Calculator.OPERATOR_PLUS));
        mBinding.minusButton.setOnClickListener(new OperatorButtonOnClickListener(Calculator.OPERATOR_MINUS));
        mBinding.multiplyButton.setOnClickListener(new OperatorButtonOnClickListener(Calculator.OPERATOR_MULTIPLY));
        mBinding.divideButton.setOnClickListener(new OperatorButtonOnClickListener(Calculator.OPERATOR_DIVIDE));

        mBinding.equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.equate();

                logImportantInfo();
            }
        });

        mBinding.allClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.clear();

                logImportantInfo();
            }
        });
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

    private class NumberPadButtonOnClickListener implements View.OnClickListener {

        String mNumber;

        public NumberPadButtonOnClickListener(String number) {
            this.mNumber = number;
        }

        @Override
        public void onClick(View v) {
            mViewModel.saveInput(mNumber);

            logImportantInfo();
        }
    }

    private class OperatorButtonOnClickListener implements View.OnClickListener {

        String mOperator;

        public OperatorButtonOnClickListener(String operator) {
            this.mOperator = operator;
        }

        @Override
        public void onClick(View v) {
            mViewModel.operate(mOperator);

            logImportantInfo();
        }
    }

    private void logImportantInfo() {
        Log.d(TAG, "=====================================");
        Log.d(TAG, "initInputState: " + mViewModel.isInitInputState());
        Log.d(TAG, "repeatEquation: " + mViewModel.isRepeatEquation());
        Log.d(TAG, "clearAll: " + mViewModel.isClearAll());
        Log.d(TAG, "operator: " + mViewModel.getOperator());
        Log.d(TAG, "resultString: " + mViewModel.getResultString());
        Log.d(TAG, "result: " + mViewModel.getResult());
        Log.d(TAG, "inputString: " + mViewModel.getInputString());
        Log.d(TAG, "input: " + mViewModel.getInput());
        Log.d(TAG, "RESULT OUTPUT: " + mViewModel.decimalFormat.format(mViewModel.getResult()));
        Log.d(TAG, "=====================================");
    }
}


