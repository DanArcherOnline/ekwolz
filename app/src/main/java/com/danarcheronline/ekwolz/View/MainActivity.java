package com.danarcheronline.ekwolz.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.danarcheronline.ekwolz.Model.Calculator;
import com.danarcheronline.ekwolz.R;
import com.danarcheronline.ekwolz.Utils.UIUtils;
import com.danarcheronline.ekwolz.ViewModel.MainViewModel;
import com.danarcheronline.ekwolz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    //Full Screen BG Helpers
    DisplayManager.DisplayListener mDisplayListener;
    int mAppContainerResourceId;

    //View Helpers
    MainViewModel mViewModel;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getExpressionOutput().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //update the text shown on the calculator
                updateExpression(s);
            }
        });

        //get a global handle for the layouts container view to adjust margins (for full screen bg)
        mAppContainerResourceId = R.id.appContainer;

//        make the status bar and navigation bar transparent
        UIUtils.setSystemUIBarsToTransparent(this, mAppContainerResourceId);

//        have the margins adjust appropriately when the devices orientation changes
        enableMarginAdjustmentDetection();

        setViewOnClickListeners();

        initUIState();
    }

    /**
     * Make sure the UI elements remain in the same state after device config changes
     */
    private void initUIState() {
        if (mViewModel.getOperator() != null) {
            //if the operator has already been chosen
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                clickNumberButton(getString(R.string.zero));
                break;
            case R.id.button1:
                clickNumberButton(getString(R.string.one));
                break;
            case R.id.button2:
                clickNumberButton(getString(R.string.two));
                break;
            case R.id.button3:
                clickNumberButton(getString(R.string.three));
                break;
            case R.id.button4:
                clickNumberButton(getString(R.string.four));
                break;
            case R.id.button5:
                clickNumberButton(getString(R.string.five));
                break;
            case R.id.button6:
                clickNumberButton(getString(R.string.six));
                break;
            case R.id.button7:
                clickNumberButton(getString(R.string.seven));
                break;
            case R.id.button8:
                clickNumberButton(getString(R.string.eight));
                break;
            case R.id.button9:
                clickNumberButton(getString(R.string.nine));
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


    //BUTTON LOGIC METHODS
    private void clickOperatorButton(String operator) {
        mViewModel.operate(operator);

        if (mViewModel.getOperator() != null) {
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

    private void clickNumberButton(String numberString) {
        mViewModel.saveInput(numberString);
        if (mViewModel.getOperator() != null) {
            deselectOperators();
        }
        displayCorrectClearButton();

    }

    private void clickClearButton() {
        mViewModel.clear();
        displayCorrectClearButton();
        deselectOperators();
    }

    private void clickEqualsButton() {
        mViewModel.equate();
        displayCorrectClearButton();
    }


    //UI METHODS
    private void updateExpression(String expression) {
        mBinding.expressionTv.setText(expression);
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
        if (mViewModel.isClearAll()) {
            mBinding.allClearButton.setBackground(getDrawable(R.drawable.button_all_clear_selector));
        } else {
            mBinding.allClearButton.setBackground(getDrawable(R.drawable.button_clear_selector));
        }

    }


    //FULL SCREEN BG
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisplayManager displayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        displayManager.unregisterDisplayListener(mDisplayListener);

    }

    /**
     * Listens for when orientation changes in the display
     */
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


