package com.danarcheronline.ekwolz;


import android.arch.lifecycle.ViewModel;

import java.text.DecimalFormat;


public class MainViewModel extends ViewModel {

    Calculator calculator;
    DecimalFormat decimalFormat;

    private boolean initInputState = true;
    private boolean repeatEquation = false;
    private boolean clearAll = true;

    private String resultString = "";
    private String inputString = "";
    private double result = Double.NaN;
    private double input = Double.NaN;
    private String operator;

    public MainViewModel() {
        this.calculator = new Calculator();
        this.decimalFormat = new DecimalFormat("###,###,###.##############");
    }

    public void saveInput(String inputToAppend) {
        if (initInputState) {
            if (resultString.length() <= 8) {
                resultString += inputToAppend;
                result = Double.parseDouble(resultString);
            }
        } else {
            if (inputString.length() <= 8) {
                inputString += inputToAppend;
                input = Double.parseDouble(inputString);
            }
        }
        if(clearAll) {
            clearAll = false;
        }
    }

    public void operate(String operator) {

        if (repeatEquation) {
            this.inputString = "";
            this.input = Double.NaN;
            repeatEquation = false;
        }

        if (initInputState) {
            if (!Double.isNaN(result)) {
                this.operator = operator;
                initInputState = false;
            }
        } else {
            if (!Double.isNaN(input)) {
                calculate();
                this.inputString = "";
                this.input = Double.NaN;
            }
            this.operator = operator;
        }
        clearAll = true;
    }

    public void equate() {
        if(!Double.isNaN(input)) {
            calculate();
            repeatEquation = true;
            clearAll = true;
        }
    }

    public void clear() {
        if(clearAll) {
            initInputState = true;
            repeatEquation = false;
            operator = null;
            resultString = "";
            result = Double.NaN;
            inputString = "";
            input = Double.NaN;
        }
        else {
            if(initInputState) {
                resultString = "";
                result = Double.NaN;
            }
            else {
                if(!Double.isNaN(input)) {
                    inputString = "";
                    input = Double.NaN;
                }
                else {
                    resultString = "";
                    result = Double.NaN;
                }
            }
            clearAll = true;
        }
    }

    private void calculate() {
        result = calculator.calculate(result, input, operator);
    }


    public boolean isInitInputState() {
        return initInputState;
    }

    public void setInitInputState(boolean initInputState) {
        this.initInputState = initInputState;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isRepeatEquation() {
        return repeatEquation;
    }

    public void setRepeatEquation(boolean repeatEquation) {
        this.repeatEquation = repeatEquation;
    }

    public boolean isClearAll() {
        return clearAll;
    }

    public void setClearAll(boolean clearAll) {
        this.clearAll = clearAll;
    }

}
