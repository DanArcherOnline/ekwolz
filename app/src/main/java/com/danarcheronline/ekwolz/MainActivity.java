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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        initUIState();

        logImportantInfo();

    }

    private void initUIState() {
        updateExpression();
        if(mViewModel.getOperator() != null) {
            switch (mViewModel.getOperator()) {
                case Calculator.OPERATOR_PLUS:
                    displayPlusButtonPressed();
                    break;
                case Calculator.OPERATOR_MINUS:
                    displayMinusButtonPressed();
                    break;
                case Calculator.OPERATOR_MULTIPLY:
                    displayMultiplyButtonPressed();
                    break;
                case Calculator.OPERATOR_DIVIDE:
                    displayDivideButtonPressed();
                    break;
                default:
                    deselectOperators();
                    break;
            }
        }
        displayCorrectClearButton();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button0:
                clickNumberButton("0");
                break;
            case R.id.button1:
                clickNumberButton("1");
                break;
            case R.id.button2:
                clickNumberButton("2");
                break;
            case R.id.button3:
                clickNumberButton("3");
                break;
            case R.id.button4:
                clickNumberButton("4");
                break;
            case R.id.button5:
                clickNumberButton("5");
                break;
            case R.id.button6:
                clickNumberButton("6");
                break;
            case R.id.button7:
                clickNumberButton("7");
                break;
            case R.id.button8:
                clickNumberButton("8");
                break;
            case R.id.button9:
                clickNumberButton("9");
                break;
            case R.id.all_clear_button:
                clickClearButton();
                break;
            case R.id.equals_button:
                clickEqualsButton();
                break;
            case R.id.plus_button:
                clickOperatorButton(Calculator.OPERATOR_PLUS);
                break;
            case R.id.minus_button:
                clickOperatorButton(Calculator.OPERATOR_MINUS);
                break;
            case R.id.multiply_button:
                clickOperatorButton(Calculator.OPERATOR_MULTIPLY);
                break;
            case R.id.divide_button:
                clickOperatorButton(Calculator.OPERATOR_DIVIDE);
                break;
            default:
                Log.e(TAG, "onClick: View that caused the click event could not be found");
                break;
        }
    }

    private void setViewOnClickListeners() {
        mBinding.button0.setOnClickListener(this);
        mBinding.button1.setOnClickListener(this);
        mBinding.button2.setOnClickListener(this);
        mBinding.button3.setOnClickListener(this);
        mBinding.button4.setOnClickListener(this);
        mBinding.button5.setOnClickListener(this);
        mBinding.button6.setOnClickListener(this);
        mBinding.button7.setOnClickListener(this);
        mBinding.button8.setOnClickListener(this);
        mBinding.button9.setOnClickListener(this);
        mBinding.plusButton.setOnClickListener(this);
        mBinding.minusButton.setOnClickListener(this);
        mBinding.multiplyButton.setOnClickListener(this);
        mBinding.divideButton.setOnClickListener(this);
        mBinding.equalsButton.setOnClickListener(this);
        mBinding.allClearButton.setOnClickListener(this);
    }

    private void clickClearButton() {
        mViewModel.clear();
        updateExpression();
        displayCorrectClearButton();
        deselectOperators();

        logImportantInfo();
    }

    private void clickEqualsButton() {
        mViewModel.equate();
        updateExpression();
        displayCorrectClearButton();

        logImportantInfo();
    }

    private void updateExpression() {
        mBinding.expressionTv.setText(mViewModel.getExpressionText());
    }

    private void displayPlusButtonPressed() {
        mBinding.plusButton.setSelected(true);
        mBinding.minusButton.setSelected(false);
        mBinding.multiplyButton.setSelected(false);
        mBinding.divideButton.setSelected(false);
    }

    private void displayMinusButtonPressed() {
        mBinding.plusButton.setSelected(false);
        mBinding.minusButton.setSelected(true);
        mBinding.multiplyButton.setSelected(false);
        mBinding.divideButton.setSelected(false);
    }

    private void displayMultiplyButtonPressed() {
        mBinding.plusButton.setSelected(false);
        mBinding.minusButton.setSelected(false);
        mBinding.multiplyButton.setSelected(true);
        mBinding.divideButton.setSelected(false);
    }

    private void displayDivideButtonPressed() {
        mBinding.plusButton.setSelected(false);
        mBinding.minusButton.setSelected(false);
        mBinding.multiplyButton.setSelected(false);
        mBinding.divideButton.setSelected(true);
    }

    private void deselectOperators() {
        mBinding.plusButton.setSelected(false);
        mBinding.minusButton.setSelected(false);
        mBinding.multiplyButton.setSelected(false);
        mBinding.divideButton.setSelected(false);
    }

    private void displayCorrectClearButton() {
        if(mViewModel.isClearAll()) {
            mBinding.allClearButton.setBackground(getDrawable(R.drawable.button_all_clear_selector));
        }
        else {
            mBinding.allClearButton.setBackground(getDrawable(R.drawable.button_clear_selector));
        }

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


    private void clickOperatorButton(String operator) {
        mViewModel.operate(operator);
        updateExpression();

        if(mViewModel.getOperator() != null) {
            switch (mViewModel.getOperator()) {
                case Calculator.OPERATOR_PLUS:
                    displayPlusButtonPressed();
                    break;
                case Calculator.OPERATOR_MINUS:
                    displayMinusButtonPressed();
                    break;
                case Calculator.OPERATOR_MULTIPLY:
                    displayMultiplyButtonPressed();
                    break;
                case Calculator.OPERATOR_DIVIDE:
                    displayDivideButtonPressed();
                    break;
                default:
                    deselectOperators();
                    break;
            }
        }
        displayCorrectClearButton();

        logImportantInfo();
    }

    private void clickNumberButton(String numberString) {
        mViewModel.saveInput(numberString);
        updateExpression();
        if(mViewModel.getOperator() != null) {
            deselectOperators();
        }
        displayCorrectClearButton();

        logImportantInfo();
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


