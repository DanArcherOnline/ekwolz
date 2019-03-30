package com.danarcheronline.ekwolz;

import android.util.Log;

public class Calculator {

    private static final String TAG = Calculator.class.getName();

    public static final String OPERATOR_PLUS = "plus";
    public static final String OPERATOR_MINUS = "minus";
    public static final String OPERATOR_MULTIPLY = "multiply";
    public static final String OPERATOR_DIVIDE = "divide";


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
                Log.e(TAG, "calculate: operator was not found");
                return 0;
        }
    }

}
