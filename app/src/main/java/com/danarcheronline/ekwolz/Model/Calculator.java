package com.danarcheronline.ekwolz.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.text.DecimalFormat;

public class Calculator {

    private static final String TAG = Calculator.class.getName();

    public static final String OPERATOR_PLUS = "plus";
    public static final String OPERATOR_MINUS = "minus";
    public static final String OPERATOR_MULTIPLY = "multiply";
    public static final String OPERATOR_DIVIDE = "divide";

    //formatter for mExpressionOutput
    private DecimalFormat decimalFormat;

    //text the user sees on the calculator
    private MutableLiveData<String> mExpressionOutput;

    //states of the calculator
    private boolean mInitInputState = true;
    private boolean mRepeatEquation = false;
    private boolean mClearAll = true;

    //calculation related data
    private String mResultString = "";
    private String mInputString = "";
    private double mResult = Double.NaN;
    private double mInput = Double.NaN;
    private String mOperator;


    public Calculator() {
        this.decimalFormat = new DecimalFormat("###,###,###.##############");
        this.mExpressionOutput = new MutableLiveData<>();
    }

    /**
     * Takes 2 inputs and an operator and returns the answer of the calculation
     *
     * @param input1   the first input. Often already stored as the result of the last equation
     * @param input2   the second input. Always the most recently typed input from the user
     * @param operator the operand the user has chosen
     * @return the result of the calculation as a double
     */
    public double calculate(double input1, double input2, String operator) {
        switch (operator) {
            case OPERATOR_PLUS:
                return input1 + input2;
            case OPERATOR_MINUS:
                return input1 - input2;
            case OPERATOR_MULTIPLY:
                return input1 * input2;
            case OPERATOR_DIVIDE:
                return input1 / input2;
            default:
                Log.e(TAG, "calculate: mOperator was not found");
                return 0;
        }
    }


    /**
     * Decides whether to display the result or what the user is currently typing
     */
    private void chooseOutput() {
        String expression;
        if (!Double.isNaN(mInput)) {
            if (!mClearAll) {
                expression = decimalFormat.format(mInput);
            } else {
                expression = decimalFormat.format(mResult);
            }
        } else {
            if (Double.isNaN(mResult)) {
                expression = "";
            } else {
                expression = decimalFormat.format(mResult);
            }
        }
        mExpressionOutput.setValue(expression);
    }

    /**
     * updates the input data for what the user is typing
     *
     * @param inputToAppend the number to add on to the already existing typed numbers
     */
    public void saveInput(String inputToAppend) {
        if (mRepeatEquation) {
            mInputString = "";
            mInput = Double.NaN;
        }
        if (mInitInputState) {
            if (mResultString.length() <= 8) {
                mResultString += inputToAppend;
                mResult = Double.parseDouble(mResultString);
            }
        } else {
            if (mInputString.length() <= 8) {
                mInputString += inputToAppend;
                mInput = Double.parseDouble(mInputString);
            }
        }
        if (mClearAll) {
            mClearAll = false;
        }

        chooseOutput();

        logDebugInfo();
    }

    /**
     * Sets the operator to the chosen operand and also calculates the current equation
     *
     * @param operator
     */
    public void operate(String operator) {

        if (mRepeatEquation) {
            this.mInputString = "";
            this.mInput = Double.NaN;
            mRepeatEquation = false;
        }

        if (mInitInputState) {
            if (!Double.isNaN(mResult)) {
                this.mOperator = operator;
                mInitInputState = false;
            }
        } else {
            if (!Double.isNaN(mInput)) {
                mResult = calculate(mResult, mInput, mOperator);
                this.mInputString = "";
                this.mInput = Double.NaN;
            }
            this.mOperator = operator;
        }
        mClearAll = true;

        chooseOutput();

        logDebugInfo();
    }

    /**
     * calculates the current equation
     */
    public void equate() {
        if (!Double.isNaN(mInput)) {
            mResult = calculate(mResult, mInput, mOperator);
            mRepeatEquation = true;
            mClearAll = true;
        }

        chooseOutput();

        logDebugInfo();
    }

    /**
     * Clears both input, stored result, and chosen operator if in 'allClear' state.
     * Clears just the recently input number displayed if in 'clear' state.
     */
    public void clear() {
        if (mClearAll) {
            mInitInputState = true;
            mRepeatEquation = false;
            mOperator = null;
            mResultString = "";
            mResult = Double.NaN;
            mInputString = "";
            mInput = Double.NaN;
        } else {
            if (mInitInputState) {
                mResultString = "";
                mResult = Double.NaN;
            } else {
                if (!Double.isNaN(mInput)) {
                    mInputString = "";
                    mInput = Double.NaN;
                } else {
                    mResultString = "";
                    mResult = Double.NaN;
                }
            }
            mClearAll = true;
        }

        chooseOutput();

        logDebugInfo();
    }


    //Data getters
    public LiveData<String> getExpressionOutput() {
        return mExpressionOutput;
    }

    public String getOperator() {
        return mOperator;
    }

    public boolean isAllClear() {
        return mClearAll;
    }


    //Debugging logs to easily check the state of the calculator when outside of debug mode
    private void logDebugInfo() {
        Log.d(TAG, "=====================================");
        Log.d(TAG, "initInputState: " + mInitInputState);
        Log.d(TAG, "repeatEquation: " + mRepeatEquation);
        Log.d(TAG, "clearAll: " + mClearAll);
        Log.d(TAG, "operator: " + mOperator);
        Log.d(TAG, "resultString: " + mResultString);
        Log.d(TAG, "result: " + mResult);
        Log.d(TAG, "inputString: " + mInputString);
        Log.d(TAG, "input: " + mInput);
        Log.d(TAG, "RESULT OUTPUT: " + getExpressionOutput().getValue());
        Log.d(TAG, "=====================================");
    }

}
